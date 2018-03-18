package com.github.millefoglie.latex.nodes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentNodeTest {

    private EnvironmentNode node;

    @Before
    public void setUp() {
        node = new EnvironmentNode();
    }

    /**
     * {@link EnvironmentNode#getEnvironmentName()}
     * {@link EnvironmentNode#setEnvironmentName(String)}
     */
    @Test
    public void environmentNameAccessors() {
        Assert.assertNull("Environment name must be empty initially",
                          node.getEnvironmentName());
        node.setEnvironmentName("theorem");
        Assert.assertEquals("Environment name must be set",
                            "theorem", node.getEnvironmentName());
    }

    @Test
    public void stringify() {
        node.setContent("test");
        node.setEnvironmentName("theorem");
        Assert.assertEquals("Stringify must work correctly",
                            "\\begin{theorem}test\\end{theorem}",
                            node.stringify());
    }
}
