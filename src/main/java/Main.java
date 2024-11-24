import com.github.millefoglie.latex.document.LatexDocument;
import com.github.millefoglie.latex.node.CompoundLatexNode;
import com.github.millefoglie.latex.node.LatexChildNode;
import com.github.millefoglie.latex.node.LatexNode;
import com.github.millefoglie.latex.parser.DefaultLatexParser;
import com.github.millefoglie.latex.parser.LatexParser;
import com.github.millefoglie.latex.parser.LatexParserException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {

    static String texfile = "main.tex";
    // static String texfile = "test-article.tex";

    static private String makeIndentString(int level) {
        char[] charArray = new char[4 * level];
        Arrays.fill(charArray, ' ');
        return new String(charArray);
    }

    static void printDOM(LatexNode start, int level) {
        printNode(start, level);
        for (LatexNode child : start.getChildrenWB()) {
            printDOM(child, level + 1);
        }
    }

    static void printNode(LatexNode node, int level) {
        System.out.println(makeIndentString(level) + escapeNewlines(node.getString()));
    }

    static String escapeNewlines(String input) {
        return input.replace("\n", "\\n");
    }

    public static void main(String[] args) throws IOException, LatexParserException {
        // Path path = Path.of("C:\\_GITHUB\\latex-dom\\latex-tests\\main.tex");
        Path path = Path.of("latex-tests", texfile);

        InputStream is = Files.newInputStream(path);
        LatexParser parser = new DefaultLatexParser();
        LatexDocument doc = parser.parse(is);
        CompoundLatexNode root = (CompoundLatexNode) doc.getRoot();

        // --------------------------------------------------

        // System.out.println("root = " + root.getString());
        // System.out.println("  siblings " + root.getSiblings().size());
        //
        // LatexNode child = root.getFirstChild();
        // if (child != null) {
        //     System.out.println("child = " + child.getString());
        // }
        // List<LatexChildNode> children = root.getChildren();
        // System.out.println("children: " + children.size());
        // for (LatexChildNode c : children) {
        //     System.out.println("   child = " + c.getString());
        // }

        printDOM(root, 0);

        is.close();
    }
}
