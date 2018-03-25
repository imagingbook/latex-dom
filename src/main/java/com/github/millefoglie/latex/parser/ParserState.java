package com.github.millefoglie.latex.parser;

import java.io.IOException;

/**
 * A parser state that defines how to treat the currently read character.
 */
interface ParserState {
    
    /**
     * Process character.
     *
     * @param parser parser having this state
     * @param chr character to be processed (should be a string of length 1)
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void process(LatexParser parser, char chr) throws IOException;

}
