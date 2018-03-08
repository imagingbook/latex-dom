package com.github.millefoglie.latex.nodes;

import com.github.millefoglie.latex.visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract LaTeX DOM node.
 * <p>
 * Represents some textual content, which can have different semantic meaning
 * depending on the node sub-type.
 */
public abstract class AbstractNode implements Node {

    private static final int CHILDREN_SIZE = 0;

    private String content;
    private Node parent;
    private List<Node> children = new ArrayList<>(CHILDREN_SIZE);

    public AbstractNode() {}

    public AbstractNode(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<Node> children) {
        this.children = (children == null)
                ? new ArrayList<>(CHILDREN_SIZE) : children;
    }

    @Override
    public void append(Node child) {
        child.setParent(this);
        children.add(child);
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    /**
     * Produces a LaTeX string corresponding to this node.
     *
     * @return a LaTeX string corresponding to this node
     */
    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();

        if (content != null) {
            sb.append(content);
        }

        if (children != null) {
            for (Node node : children) {
                sb.append(node.stringify());
            }
        }

        return sb.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
