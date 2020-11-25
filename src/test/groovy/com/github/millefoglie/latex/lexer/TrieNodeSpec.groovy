package com.github.millefoglie.latex.lexer

import spock.lang.Specification

class TrieNodeSpec extends Specification {

    def "Filled trie contains correct values"() {
        given:
        def node = new TrieNode()

        when:
        node.add("This")
        node.add("That")

        then:
        verifyAll(node) {
            contains("This")
            contains("That")
            containsPrefix("T")
            containsPrefix("Th")
            containsPrefix("Thi")
            containsPrefix("Tha")
            !containsPrefix("The")
            !containsPrefix("Thats")
            !contains("Those")
        }
    }
}
