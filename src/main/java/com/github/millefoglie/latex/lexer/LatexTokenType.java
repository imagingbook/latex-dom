package com.github.millefoglie.latex.lexer;

public enum LatexTokenType {
    LETTERS(""),
    DIGITS(""),
    PUNCTUATION(""),
    WHITESPACE(" "),
    BACKSLASH("\\"),
    OPENING_BRACE("{"),
    CLOSING_BRACE("}"),
    OPENING_BRACKET("["),
    CLOSING_BRACKET("]"),
    OPENING_PARENTHESIS("("),
    CLOSING_PARENTHESIS(")"),
    DOLLAR("$"),
    CARET("^"),
    UNDERSCORE("_"),
    PERCENT("%"),
    AMPERSAND("&"),
    AT("@");

    private final String defaultValue;

    LatexTokenType(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
