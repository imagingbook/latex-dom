package com.github.millefoglie.latex.nodes;

/**
 * A LaTeX DOM node for display math code.
 */
public class DisplayMathNode extends AbstractNode {

    private String contentOpening = "\\[";
    private String contentClosing = "\\]";

    public DisplayMathNode() {}

    /**
     * Gets the content opening string before the actual content, like "\\[" or
     * "$$".
     *
     * @return the content opening string
     */
    public String getContentOpening() {
        return contentOpening;
    }

    /**
     * Sets the content opening string before the actual content, like "\\[" or
     * "$$".
     *
     * @param contentOpening  the content opening string
     */
    public void setContentOpening(String contentOpening) {
        this.contentOpening = contentOpening;
    }

    /**
     * Gets the content closing string after the actual content, like "\\[" or
     * "$$"
     *
     * @return the content closing string
     */
    public String getContentClosing() {
        return contentClosing;
    }

    /**
     * Sets the content closing string after the actual content, like "\\[" or "$$".
     *
     * @param contentClosing  the content closing string
     */
    public void setContentClosing(String contentClosing) {
        this.contentClosing = contentClosing;
    }

    @Override
    public String stringify() {
        return contentOpening + super.stringify() + contentClosing;
    }

}
