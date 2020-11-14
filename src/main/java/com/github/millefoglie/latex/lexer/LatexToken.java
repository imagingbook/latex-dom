package com.github.millefoglie.latex.lexer;

import java.util.Objects;

public class LatexToken {
    static final LatexToken BACKSLASH = new LatexToken(LatexTokenType.BACKSLASH);
    static final LatexToken OPENING_BRACE = new LatexToken(LatexTokenType.OPENING_BRACE);
    static final LatexToken CLOSING_BRACE = new LatexToken(LatexTokenType.CLOSING_BRACE);
    static final LatexToken OPENING_BRACKET = new LatexToken(LatexTokenType.OPENING_BRACKET);
    static final LatexToken CLOSING_BRACKET = new LatexToken(LatexTokenType.CLOSING_BRACKET);
    static final LatexToken OPENING_PARENTHESIS = new LatexToken(LatexTokenType.OPENING_PARENTHESIS);
    static final LatexToken CLOSING_PARENTHESIS = new LatexToken(LatexTokenType.CLOSING_PARENTHESIS);
    static final LatexToken DOLLAR = new LatexToken(LatexTokenType.DOLLAR);
    static final LatexToken CARET = new LatexToken(LatexTokenType.CARET);
    static final LatexToken UNDERSCORE = new LatexToken(LatexTokenType.UNDERSCORE);
    static final LatexToken PERCENT = new LatexToken(LatexTokenType.PERCENT);
    static final LatexToken AMPERSAND = new LatexToken(LatexTokenType.AMPERSAND);
    static final LatexToken AT = new LatexToken(LatexTokenType.AT);

    private final LatexTokenType type;
    private final String value;

    public LatexToken(LatexTokenType type) {
        Objects.requireNonNull(type);

        this.type = type;
        value = null;
    }

    public LatexToken(LatexTokenType type, String value) {
        Objects.requireNonNull(type);
        
        this.type = type;
        this.value = value;
    }

    public LatexTokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LatexToken token = (LatexToken) o;
        return type == token.type &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}

