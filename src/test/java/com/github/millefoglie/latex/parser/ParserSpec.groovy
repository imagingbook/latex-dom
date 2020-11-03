package com.github.millefoglie.latex.parser


import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class ParserSpec extends Specification {

    def "Complex re-serialized document matches the original one"() {
        given:
        def path = Paths.get("src/test/resources/intro.tex")
        def is = Files.newInputStream(path)
        def parser = new LatexParser()

        when:
        def root = parser.parse(is).getRoot()
        def str = root.stringify()
        def inputBytes = Files.readAllBytes(path)
        def outputBytes = str.getBytes()

        then:
        inputBytes == outputBytes
    }
}
