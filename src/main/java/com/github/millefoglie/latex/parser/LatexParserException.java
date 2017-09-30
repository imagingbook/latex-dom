package com.github.millefoglie.latex.parser;

public class LatexParserException extends Exception {

    private final String message;

    public LatexParserException(String message) {
	this.message = message;
    }

    @Override
    public String getMessage() {
	return this.message;
    }

}
