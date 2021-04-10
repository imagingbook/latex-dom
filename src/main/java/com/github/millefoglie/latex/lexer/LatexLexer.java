package com.github.millefoglie.latex.lexer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.IntPredicate;

public class LatexLexer {
    private final InputStreamReader source;
    private final StringBuilder stringBuilder = new StringBuilder();
    private final TrieNode specialPunctuationTrie = new TrieNode();
    private final Queue<Integer> codepointQueue = new LinkedList<>();
    private LatexTokenType previousTokenType;

    {
        specialPunctuationTrie.add("--");
        specialPunctuationTrie.add("---");
        specialPunctuationTrie.add("<<");
        specialPunctuationTrie.add(">>");
    }

    public LatexLexer(InputStreamReader source) {
        this.source = source;
    }

    public LatexToken next() throws IOException {
        int c = readNextCodepoint();

        if (c == -1) {
            return null;
        }

        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.appendCodePoint(c);

        if (Character.isWhitespace(c)) {
            if (previousTokenType == LatexTokenType.BACKSLASH) {
                return emitToken(LatexTokenType.WHITESPACE);
            }

            if (c == '\n') {
                readWhile(ch -> ch == '\n');
            } else {
                readWhile(ch -> Character.isWhitespace(ch) && (ch != '\n'));
            }
            return emitToken(LatexTokenType.WHITESPACE);
        }

        if (Character.isLetter(c)) {
            readWhile(Character::isLetter);
            return emitToken(LatexTokenType.LETTERS);
        }

        if (Character.isDigit(c)) {
            if (previousTokenType == LatexTokenType.BACKSLASH) {
                return emitToken(LatexTokenType.DIGITS);
            }

            readWhile(Character::isDigit);
            return emitToken(LatexTokenType.DIGITS);
        }

        if (specialPunctuationTrie.containsPrefix(stringBuilder)) {
            if (previousTokenType == LatexTokenType.BACKSLASH) {
                return emitToken(LatexTokenType.PUNCTUATION);
            }

            readSpecialPunctuation();

            if (stringBuilder.length() > 1) {
                return emitToken(LatexTokenType.PUNCTUATION);
            }
        }

        LatexTokenType type = switch (c) {
            case '\\' -> LatexTokenType.BACKSLASH;
            case '{' -> LatexTokenType.OPENING_BRACE;
            case '}' -> LatexTokenType.CLOSING_BRACE;
            case '[' -> LatexTokenType.OPENING_BRACKET;
            case ']' -> LatexTokenType.CLOSING_BRACKET;
            case '(' -> LatexTokenType.OPENING_PARENTHESIS;
            case ')' -> LatexTokenType.CLOSING_PARENTHESIS;
            case '$' -> LatexTokenType.DOLLAR;
            case '^' -> LatexTokenType.CARET;
            case '_' -> LatexTokenType.UNDERSCORE;
            case '%' -> LatexTokenType.PERCENT;
            case '&' -> LatexTokenType.AMPERSAND;
            case '@' -> LatexTokenType.AT;
            default -> LatexTokenType.PUNCTUATION;
        };

        return emitToken(type);
    }

    private int readNextCodepoint() throws IOException {
        return codepointQueue.isEmpty() ? source.read() : codepointQueue.poll();
    }

    private void readWhile(IntPredicate charPredicate) throws IOException {
        int c;

        while (((c = readNextCodepoint()) != -1)
                && charPredicate.test(c)) {
            stringBuilder.appendCodePoint(c);
        }

        codepointQueue.offer(c);
    }

    private void readSpecialPunctuation() throws IOException {
        int c;
        Queue<Integer> queue = new LinkedList<>();

        while ((c = readNextCodepoint()) != -1) {
            stringBuilder.appendCodePoint(c);

            if (!specialPunctuationTrie.containsPrefix(stringBuilder)) {
                break;
            }
        }

        while ((!specialPunctuationTrie.contains(stringBuilder) && (stringBuilder.length() > 1))) {
            int lastPos = stringBuilder.length() - 1;
            c = stringBuilder.charAt(lastPos);

            stringBuilder.deleteCharAt(lastPos);
            queue.offer(c);
        }

        queue.forEach(codepointQueue::offer);
    }

    private LatexToken emitToken(LatexTokenType type) {
        String value = (type == LatexTokenType.LETTERS)
                || (type == LatexTokenType.DIGITS)
                || (type == LatexTokenType.PUNCTUATION)
                || (type == LatexTokenType.WHITESPACE)
                ? stringBuilder.toString() : null;

        LatexToken token = value == null
                ? LatexToken.ofType(type) : new LatexToken(type, value);
        previousTokenType = token.getType();
        return token;
    }
}
