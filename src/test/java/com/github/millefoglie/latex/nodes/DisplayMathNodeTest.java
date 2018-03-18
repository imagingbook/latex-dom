package com.github.millefoglie.latex.nodes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DisplayMathNodeTest {

    private DisplayMathNode node;

    @Before
    public void setUp() {
        node = new DisplayMathNode();
    }

    /**
     * {@link DisplayMathNode#getContentOpening()}
     * {@link DisplayMathNode#setContentOpening(String)}
     */
    @Test
    public void contentOpeningAccessors() {
        Assert.assertEquals("ContentOpening must have initial value",
                            "\\[", node.getContentOpening());
        node.setContentOpening("$$");
        Assert.assertEquals("ContentOpening must be set",
                            "$$", node.getContentOpening());
    }

    /**
     * {@link DisplayMathNode#getContentClosing()}
     * {@link DisplayMathNode#setContentClosing(String)}
     */
    @Test
    public void contentClosingAccessors() {
        Assert.assertEquals("ContentClosing must have initial value",
                            "\\]", node.getContentClosing());
        node.setContentClosing("$$");
        Assert.assertEquals("ContentClosing must be set",
                            "$$", node.getContentClosing());
    }

    /**
     * {@link DisplayMathNode#stringify()}
     */
    @Test
    public void stringifyWithDefaultOpenings() {
        node.setContent("test");
        Assert.assertEquals("Stringify must work correctly",
                            "\\[test\\]", node.stringify());
    }

    /**
     * {@link DisplayMathNode#stringify()}
     */
    @Test
    public void stringifyWithDollarOpenings() {
        node.setContent("test");
        node.setContentOpening("$$");
        node.setContentClosing("$$");
        Assert.assertEquals("Stringify must work correctly",
                            "$$test$$", node.stringify());
    }

}
