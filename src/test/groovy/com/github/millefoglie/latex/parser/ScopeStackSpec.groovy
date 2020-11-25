package com.github.millefoglie.latex.parser

import com.github.millefoglie.latex.node.CompoundLatexNode
import com.github.millefoglie.latex.node.LatexNodeType
import com.github.millefoglie.latex.node.MathLatexNode
import spock.lang.Shared
import spock.lang.Specification

class ScopeStackSpec extends Specification {

    @Shared ScopeStack scopeStack
    @Shared CompoundLatexNode root

    def setup() {
        scopeStack = new ScopeStack()
        root = new CompoundLatexNode(LatexNodeType.ROOT)
        scopeStack.openScope(root)
    }

    def "Close a braces scope"() {
        when:
        def bracesNode = new CompoundLatexNode(LatexNodeType.BRACES)
        scopeStack.openScope(bracesNode)
        scopeStack.closeScope(LatexNodeType.BRACES)
        scopeStack.closeScope(LatexNodeType.ROOT)

        then:
        root.getChildren().contains(bracesNode)
    }

    def "Close a brackets scope"() {
        when:
        def bracketsNode = new CompoundLatexNode(LatexNodeType.BRACKETS)
        scopeStack.openScope(bracketsNode)
        scopeStack.closeScope(LatexNodeType.BRACKETS)
        scopeStack.closeScope(LatexNodeType.ROOT)

        then:
        root.getChildren().contains(bracketsNode)
    }

    def "Flatten brackets and close a braces scope"() {
        when:
        CompoundLatexNode bracesNode = new CompoundLatexNode(LatexNodeType.BRACES)
        scopeStack.openScope(bracesNode)
        scopeStack.openScope(new CompoundLatexNode(LatexNodeType.BRACKETS))
        scopeStack.openScope(new CompoundLatexNode(LatexNodeType.BRACKETS))
        scopeStack.closeScope(LatexNodeType.BRACES)
        scopeStack.closeScope(LatexNodeType.ROOT)

        then:
        verifyAll {
            root.getChildren().size() == 1
            root.getChildren().contains(bracesNode)
            bracesNode.getChildren().size() == 2
            bracesNode.getChildren().every {
                it.getContent() == "["
            }
        }
    }

    def "Exception when closing root scope"() {
        when:
        scopeStack.closeScope(LatexNodeType.BRACES)

        then:
        Exception e = thrown()
        e.message.contains("scope")
    }

    def "Exception when closing non-matching scope"() {
        when:
        scopeStack.openScope(new MathLatexNode(LatexNodeType.INLINE_MATH))
        scopeStack.closeScope(LatexNodeType.BRACES)

        then:
        Exception e = thrown()
        e.message.contains("scope")
    }
}
