package com.github.millefoglie.latex.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract LaTeX DOM node that contains children nodes.
 * <p>
 * This kind of node corresponds to LaTeX syntactic structures, which enclose
 * some pieces of code.
 */
public abstract class AbstractParentNode extends AbstractNode {

    List<Node> children = new ArrayList<>(0);

    public AbstractParentNode() {}

    public AbstractParentNode(String content) {
        super(content);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public boolean addChild(Node child) {
        return children.add(child);
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();

        for (Node node : children) {
            sb.append(node.stringify());
        }

        return sb.toString();
    }

}
