package com.github.millefoglie.latex.node;

public enum LatexNodeType {

    // simple node types
    TEXT,
    WHITESPACE,
    COMMAND,
    COMMENT,
    CARET,
    UNDERSCORE,
    AMPERSAND,
    AT,

    // compound node types
    ROOT,
    BRACES,
    BRACKETS,

    // math node types
    DISPLAY_MATH,
    INLINE_MATH,

    // environment node type
    ENVIRONMENT,

    CUSTOM
}
