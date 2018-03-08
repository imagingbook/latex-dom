package com.github.millefoglie.latex.nodes;

import org.junit.Assert;
import org.junit.Test;

public class BracesNodeTest {

    /**
     * {@link BracesNode#stringify()}
     */
    @Test
    public void stringify() {
        Node node = new BracesNode();

        node.append(new TextNode("te"));
        node.append(new TextNode("st"));
        Assert.assertEquals("Stringify must work correctly", "{test}", node.stringify());
    }

}
