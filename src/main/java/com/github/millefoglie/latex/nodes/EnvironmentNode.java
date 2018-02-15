package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for environment blocks.
 */
public class EnvironmentNode extends AbstractNode {

    private static final String BEGIN_LATEX_CMD = "\\begin";
    private static final String END_LATEX_CMD = "\\end";

    private String environmentName;

    public EnvironmentNode() {}

    /**
     * Gets the environment name.
     *
     * @return the environment name
     */
    public String getEnvironmentName() {
        return environmentName;
    }

    /**
     * Sets the environment name.
     *
     * @param environmentName
     *            the environment name
     */
    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public String stringify() {
        return BEGIN_LATEX_CMD + "{" + environmentName + "}" + super.stringify()
                + END_LATEX_CMD + "{" + environmentName + "}";
    }
}
