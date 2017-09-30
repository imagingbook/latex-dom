package com.github.millefoglie.latex.documents;

import com.github.millefoglie.latex.nodes.RootNode;

public class BasicLatexDocument implements LatexDocument {

    RootNode root;

    public BasicLatexDocument(RootNode root) {
        this.root = root;
    }

    public void setRoot(RootNode root) {
        this.root = root;
    }

    @Override
    public RootNode getRoot() {
        return root;
    }

}
