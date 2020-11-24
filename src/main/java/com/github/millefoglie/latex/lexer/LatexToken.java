package com.github.millefoglie.latex.lexer;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class LatexToken {
    private static final Map<LatexTokenType, LatexToken> TOKEN_POOL = new EnumMap<>(LatexTokenType.class);

    static {
        TOKEN_POOL.put(LatexTokenType.BACKSLASH , new LatexToken(LatexTokenType.BACKSLASH));
        TOKEN_POOL.put(LatexTokenType.OPENING_BRACE , new LatexToken(LatexTokenType.OPENING_BRACE));
        TOKEN_POOL.put(LatexTokenType.CLOSING_BRACE , new LatexToken(LatexTokenType.CLOSING_BRACE));
        TOKEN_POOL.put(LatexTokenType.OPENING_BRACKET , new LatexToken(LatexTokenType.OPENING_BRACKET));
        TOKEN_POOL.put(LatexTokenType.CLOSING_BRACKET , new LatexToken(LatexTokenType.CLOSING_BRACKET));
        TOKEN_POOL.put(LatexTokenType.OPENING_PARENTHESIS , new LatexToken(LatexTokenType.OPENING_PARENTHESIS));
        TOKEN_POOL.put(LatexTokenType.CLOSING_PARENTHESIS , new LatexToken(LatexTokenType.CLOSING_PARENTHESIS));
        TOKEN_POOL.put(LatexTokenType.DOLLAR , new LatexToken(LatexTokenType.DOLLAR));
        TOKEN_POOL.put(LatexTokenType.CARET , new LatexToken(LatexTokenType.CARET));
        TOKEN_POOL.put(LatexTokenType.UNDERSCORE , new LatexToken(LatexTokenType.UNDERSCORE));
        TOKEN_POOL.put(LatexTokenType.PERCENT , new LatexToken(LatexTokenType.PERCENT));
        TOKEN_POOL.put(LatexTokenType.AMPERSAND , new LatexToken(LatexTokenType.AMPERSAND));
        TOKEN_POOL.put(LatexTokenType.AT , new LatexToken(LatexTokenType.AT));
    }

    private final LatexTokenType type;
    private final String value;

    private LatexToken(LatexTokenType type) {
        Objects.requireNonNull(type);

        this.type = type;
        value = type.getDefaultValue();
    }

    public LatexToken(LatexTokenType type, String value) {
        Objects.requireNonNull(type);
        
        this.type = type;
        this.value = value;
    }

    public static LatexToken ofType(LatexTokenType type) {
        return TOKEN_POOL.computeIfAbsent(type, t -> new LatexToken(type));
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

