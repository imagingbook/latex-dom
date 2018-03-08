package com.github.millefoglie.latex.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.millefoglie.latex.documents.BasicLatexDocument;
import com.github.millefoglie.latex.documents.LatexDocument;
import com.github.millefoglie.latex.nodes.Node;
import com.github.millefoglie.latex.nodes.RootNode;
import com.github.millefoglie.latex.nodes.TextNode;

/**
 * LaTeX DOM parser.
 */
public class LatexParser {

    private static final Logger LOG = LoggerFactory.getLogger(LatexParser.class);

    private RootNode root;

    private Deque<Node> nodeStack;
    private ParserState state;
    private StringBuilder buffer = new StringBuilder();

    private int linePos;
    private int columnPos;

    public LatexParser() {
        super();
    }

    private void init() {
        root = new RootNode();
        nodeStack = new ArrayDeque<>();
        state = TextState.getInstance();

        linePos = 1;
        columnPos = 1;

        nodeStack.push(root);
        nodeStack.push(new TextNode());
        clearBuffer();
    }

    public LatexDocument parse(InputStream inputStream) throws LatexParserException {
        try (InputStreamReader in = new InputStreamReader(inputStream)) {
            int next;
            String chr;

            init();

            while ((next = in.read()) != -1) {
                chr = String.valueOf((char) next);

                process(chr);

                if (chr.matches("[\\r\\n]")) {
                    linePos++;
                    columnPos = 1;
                } else {
                    columnPos++;
                }
            }

            // hack to correctly finish states processing and complete the DOM
            process("");
            flushAndCollapse();

            if (nodeStack.peek() != root) {
		throw new LatexParserException(String.format("Unmatched node %s.",
		        peek().getClass().getSimpleName()));
            }
        } catch (IOException e) {
            LOG.error("Could not parse.", e);
        }

        LatexDocument document = new BasicLatexDocument(root);

        return document;
    }

    void process(String chr) throws IOException {
        state.process(this, chr);
    }

    void setState(ParserState state) {
        this.state = state;
    }

    String getBufferContent() {
        return buffer.toString();
    }

    void clearBuffer() {
        buffer.delete(0, buffer.length());
    }

    void appendToBuffer(String str) {
        buffer.append(str);
    }

    int getBufferLength() {
        return buffer.length();
    }

    /**
     * Flush the buffer to the top node content.
     */
    void flush() {
        Node current = nodeStack.peek();

        assert (current != null) :
            String.format("Tried to flush to null node at %s", getPosition());
        assert (current != root) :
            String.format("Tried to flush to root at %s", getPosition());

        String content = getBufferContent().intern();

        current.setContent(getBufferContent().intern());
        clearBuffer();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Flushed {}", content);
        }
    }

    /**
     * Collapse the top node and append it as a child to the next one.
     */
    void collapse() {
        Node child = nodeStack.poll();
        Node parent = nodeStack.peek();

        assert (child != root) :
            String.format("Tried to collapse root at %s", getPosition());

        if (child instanceof TextNode) {
            String content = child.getContent();

            if (content == null || content.length() == 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Popped {}", child.getClass().getSimpleName());
                }

                return;
            }
        }

        parent.append(child);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Collapsed {}", child.getClass().getSimpleName());
        }
    }

    /**
     * @see LatexParser#flush()
     * @see LatexParser#collapse()
     */
    void flushAndCollapse() {
        if (nodeStack.peek() != root) {
            flush();
            collapse();
        }
    }

    void push(Node node) {
        nodeStack.push(node);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Pushed {}", node.getClass().getSimpleName());
        }
    }

    Node pop() {
        Node popped = nodeStack.poll();

        assert (popped != root) : "Tried to pop root";

        if (LOG.isDebugEnabled()) {
            LOG.debug("Popped: {}.", popped.getClass().getSimpleName());
        }

        return popped;
    }

    Node peek() {
        return nodeStack.peek();
    }

    Position getPosition() {
        return new Position(linePos, columnPos);
    }

}
