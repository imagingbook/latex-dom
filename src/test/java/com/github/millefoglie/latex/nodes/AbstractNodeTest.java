package com.github.millefoglie.latex.nodes;

import com.github.millefoglie.latex.visitors.Visitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AbstractNodeTest {

    private AbstractNode node;

    @Before
    public void setUp() {
        node = new AbstractNode() {};
    }

    /**
     * {@link AbstractNode#AbstractNode(String)}
     */
    @Test
    public void constructor() {
        class AbstractNodeMock extends AbstractNode {
            private AbstractNodeMock(String content) {
                super(content);
            }
        }

        String expected = "test";
        node = new AbstractNodeMock(expected);

        Assert.assertEquals("Content must be initialised", expected, node.getContent());
    }

    /**
     * {@link AbstractNode#getContent()}
     * {@link AbstractNode#setContent(String)}
     */
    @Test
    public void contentAccessors() {
        String expected = "test content";

        Assert.assertNull("Content must be empty initially", node.getContent());
        node.setContent(expected);
        Assert.assertEquals("Content must be set", expected, node.getContent());
    }

    /**
     * {@link AbstractNode#getChildren()}
     * {@link AbstractNode#setChildren(List)}
     */
    @Test
    public void childrenAccessors() {
        Node node1 = new AbstractNode() {};
        Node node2 = new AbstractNode() {};
        List<Node> expected = Arrays.asList(node1, node2);

        Assert.assertTrue("Children must be empty initially", node.getChildren().isEmpty());
        node.setChildren(expected);
        Assert.assertEquals("Children must be set", expected, node.getChildren());
        node.setChildren(null);
        Assert.assertTrue("Children must be empty if set to null", node.getChildren().isEmpty());
    }

    /**
     * {@link AbstractNode#append(Node)}
     */
    @Test
    public void append() {
        Node node1 = new AbstractNode() {};
        Node node2 = new AbstractNode() {};
        List<Node> expected1 = Collections.singletonList(node1);
        List<Node> expected2 = Arrays.asList(node1, node2);

        Assert.assertTrue("Children must be empty initially", node.getChildren().isEmpty());
        node.append(node1);
        Assert.assertEquals("First child must be appended", expected1, node.getChildren());
        node.append(node2);
        Assert.assertEquals("Second child must be appended", expected2, node.getChildren());
    }

    /**
     * {@link AbstractNode#getParent()}
     * {@link AbstractNode#setParent(Node)}
     */
    @Test
    public void parentAccessors() {
        Node parent = new AbstractNode() {};
        Node child = new AbstractNode() {};

        Assert.assertTrue("Parent must have no children initially", parent.getChildren().isEmpty());
        Assert.assertNull("Child must have no parent initially", child.getParent());
        child.setParent(parent);
        Assert.assertTrue("Parent must still have no children", parent.getChildren().isEmpty());
        Assert.assertEquals("Child parent must be set", parent, child.getParent());
    }

    /**
     * {@link AbstractNode#stringify()}
     */
    @Test
    public void stringify() {
        Node node1 = new AbstractNode() {};
        Node node2 = new AbstractNode() {};

        node1.setContent("_ch1");
        node2.setContent("_ch2");
        node.append(node1);
        node.append(node2);
        node.setContent("par");

        Assert.assertEquals("Conversion to string must work correctly",
                            "par_ch1_ch2", node.stringify());
    }

    /**
     * {@link AbstractNode#accept(Visitor)}
     */
    @Test
    public void accept() {
        Visitor visitor = Mockito.mock(Visitor.class);

        node.accept(visitor);
        Mockito.verify(visitor).visit(node);
    }
}
