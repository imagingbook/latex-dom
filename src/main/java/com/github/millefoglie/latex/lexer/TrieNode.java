package com.github.millefoglie.latex.lexer;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    private boolean leafNode;
    private final Map<Integer, TrieNode> children = new HashMap<>();

    public TrieNode() {}

    public void add(CharSequence charSequence) {
        if ((charSequence == null) || charSequence.length() == 0) {
            return;
        }

        add(this, charSequence, 0);
    }

    private void add(TrieNode node, CharSequence charSequence, int pos) {
        if (pos == charSequence.length()) {
            node.leafNode = true;
            return;
        }

        int c = charSequence.charAt(pos);
        TrieNode child = node.children.computeIfAbsent(c, i -> new TrieNode());

        add(child, charSequence, pos + 1);
    }

    public boolean containsPrefix(CharSequence charSequence) {
        TrieNode node = this;
        int pos = 0;

        while ((node != null) && (!node.leafNode) && (pos < charSequence.length())) {
            int c = charSequence.charAt(pos++);
            node = node.children.get(c);
        }

        return node != null;
    }

    public boolean contains(CharSequence charSequence) {
        TrieNode node = this;
        int pos = 0;

        while ((node != null) && (pos < charSequence.length())) {
            int c = charSequence.charAt(pos++);
            node = node.children.get(c);
        }

        return node != null && node.leafNode;
    }
}
