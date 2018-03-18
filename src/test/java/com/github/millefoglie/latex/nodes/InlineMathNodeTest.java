package com.github.millefoglie.latex.nodes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InlineMathNodeTest {

    private InlineMathNode node;

    @Before
    public void setUp() {
        node = new InlineMathNode();
    }

    /**
     * {@link InlineMathNode#getContentOpening()}
     * {@link InlineMathNode#setContentOpening(String)}
     */
    @Test
    public void contentOpeningAccessors() {
        Assert.assertEquals("ContentOpening must have initial value",
                            "$", node.getContentOpening());
        node.setContentOpening("\\(");
        Assert.assertEquals("ContentOpening must be set",
                            "\\(", node.getContentOpening());
    }

    /**
     * {@link InlineMathNode#getContentClosing()}
     * {@link InlineMathNode#setContentClosing(String)}
     */
    @Test
    public void contentClosingAccessors() {
        Assert.assertEquals("ContentClosing must have initial value",
                            "$", node.getContentClosing());
        node.setContentClosing("\\)");
        Assert.assertEquals("ContentClosing must be set",
                            "\\)", node.getContentClosing());
    }

    /**
     * {@link InlineMathNode#stringify()}
     */
    @Test
    public void stringifyWithDefaultOpenings() {
        node.setContent("test");
        Assert.assertEquals("Stringify must work correctly",
                            "$test$", node.stringify());
    }

    /**
     * {@link InlineMathNode#stringify()}
     */
    @Test
    public void stringifyWithParenthesesOpenings() {
        node.setContent("test");
        node.setContentOpening("\\(");
        node.setContentClosing("\\)");
        Assert.assertEquals("Stringify must work correctly",
                            "\\(test\\)", node.stringify());
    }

}
