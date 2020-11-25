package com.github.millefoglie.latex.parser;

import com.github.millefoglie.latex.document.LatexDocument;

import java.io.IOException;
import java.io.InputStream;

public interface LatexParser {
    LatexDocument parse(InputStream inputStream) throws LatexParserException, IOException;
}
