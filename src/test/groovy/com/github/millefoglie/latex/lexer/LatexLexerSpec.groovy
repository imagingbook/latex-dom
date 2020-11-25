package com.github.millefoglie.latex.lexer

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class LatexLexerSpec extends Specification {

    def "First token of string '#str' has type #type"() {
        given:
        def inputStream = new ByteArrayInputStream(str.getBytes())
        def inputStreamReader = new InputStreamReader(inputStream)
        LatexLexer lexer = new LatexLexer(inputStreamReader)

        when:
        def token = lexer.next()

        then:
        token.type == type

        where:
        str         | type
        ' \t\ntest' | LatexTokenType.WHITESPACE
        '123test'   | LatexTokenType.TEXT
        'test'      | LatexTokenType.TEXT
        'test123'   | LatexTokenType.TEXT
        '\\test123' | LatexTokenType.BACKSLASH
        '{test'     | LatexTokenType.OPENING_BRACE
        '}test'     | LatexTokenType.CLOSING_BRACE
        '[test'     | LatexTokenType.OPENING_BRACKET
        ']test'     | LatexTokenType.CLOSING_BRACKET
        '(test'     | LatexTokenType.OPENING_PARENTHESIS
        ')test'     | LatexTokenType.CLOSING_PARENTHESIS
        '$test'     | LatexTokenType.DOLLAR
        '$$test'    | LatexTokenType.DOLLAR
        '^test'     | LatexTokenType.CARET
        '_test'     | LatexTokenType.UNDERSCORE
        '%test'     | LatexTokenType.PERCENT
        '&test'     | LatexTokenType.AMPERSAND
        '@test'     | LatexTokenType.AT
    }

    def "First token of string '#str' has correct value"() {
        given:
        def inputStream = new ByteArrayInputStream(str.getBytes())
        def inputStreamReader = new InputStreamReader(inputStream)
        LatexLexer lexer = new LatexLexer(inputStreamReader)

        when:
        def token = lexer.next()

        then:
        token.value == expectedValue

        where:
        str        | expectedValue
        '123test'  | '123'
        'test'     | 'test'
        'test123'  | 'test'
        '"test'    | '"'
        '<<test'   | '<<'
        '>>test'   | '>>'
        '>>>test'  | '>>'
        '----test' | '---'
        '---test'  | '---'
        '--test'   | '--'
        ' \t\n'    | ' \t'
        '\n\n\t'   | '\n\n'
    }

    def "Tokenization of '#str' yields correct tokens"() {
        given:
        def inputStream = new ByteArrayInputStream(str.getBytes())
        def inputStreamReader = new InputStreamReader(inputStream)
        LatexLexer lexer = new LatexLexer(inputStreamReader)

        when:
        def tokens = []
        def token
        while ((token = lexer.next()) != null) {
            tokens << token
        }

        then:
        tokens == expectedTokens

        where:
        str         | expectedTokens
        '123test'   | [new LatexToken(LatexTokenType.TEXT, '123'), new LatexToken(LatexTokenType.TEXT, 'test')]
        '123 test'  | [new LatexToken(LatexTokenType.TEXT, '123'), new LatexToken(LatexTokenType.WHITESPACE, ' '), new LatexToken(LatexTokenType.TEXT, 'test')]
        'test{'     | [new LatexToken(LatexTokenType.TEXT, 'test'), LatexToken.ofType(LatexTokenType.OPENING_BRACE)]
        'test}'     | [new LatexToken(LatexTokenType.TEXT, 'test'), LatexToken.ofType(LatexTokenType.CLOSING_BRACE)]
        'test['     | [new LatexToken(LatexTokenType.TEXT, 'test'), LatexToken.ofType(LatexTokenType.OPENING_BRACKET)]
        'test]'     | [new LatexToken(LatexTokenType.TEXT, 'test'), LatexToken.ofType(LatexTokenType.CLOSING_BRACKET)]
        '\\('       | [LatexToken.ofType(LatexTokenType.BACKSLASH), LatexToken.ofType(LatexTokenType.OPENING_PARENTHESIS)]
        ' )'        | [new LatexToken(LatexTokenType.WHITESPACE, ' '), LatexToken.ofType(LatexTokenType.CLOSING_PARENTHESIS)]
        '$$'        | [LatexToken.ofType(LatexTokenType.DOLLAR), LatexToken.ofType(LatexTokenType.DOLLAR)]
        '$x$'       | [LatexToken.ofType(LatexTokenType.DOLLAR), new LatexToken(LatexTokenType.TEXT, 'x'), LatexToken.ofType(LatexTokenType.DOLLAR)]
        'x^'        | [new LatexToken(LatexTokenType.TEXT, 'x'), LatexToken.ofType(LatexTokenType.CARET)]
        'x_'        | [new LatexToken(LatexTokenType.TEXT, 'x'), LatexToken.ofType(LatexTokenType.UNDERSCORE)]
        '% comment' | [LatexToken.ofType(LatexTokenType.PERCENT), new LatexToken(LatexTokenType.WHITESPACE, ' '), new LatexToken(LatexTokenType.TEXT, 'comment')]
        '& y'       | [LatexToken.ofType(LatexTokenType.AMPERSAND), new LatexToken(LatexTokenType.WHITESPACE, ' '), new LatexToken(LatexTokenType.TEXT, 'y')]
        '@space'    | [LatexToken.ofType(LatexTokenType.AT), new LatexToken(LatexTokenType.TEXT, 'space')]
        '\\test'    | [LatexToken.ofType(LatexTokenType.BACKSLASH), new LatexToken(LatexTokenType.TEXT, 'test')]
        '\\123'     | [LatexToken.ofType(LatexTokenType.BACKSLASH), new LatexToken(LatexTokenType.TEXT, '1'), new LatexToken(LatexTokenType.TEXT, '23')]
        '\\---'     | [LatexToken.ofType(LatexTokenType.BACKSLASH), new LatexToken(LatexTokenType.TEXT, '-'), new LatexToken(LatexTokenType.TEXT, '--')]
        '\\<<'      | [LatexToken.ofType(LatexTokenType.BACKSLASH), new LatexToken(LatexTokenType.TEXT, '<'), new LatexToken(LatexTokenType.TEXT, '<')]
        ' \n\n'     | [new LatexToken(LatexTokenType.WHITESPACE, ' '), new LatexToken(LatexTokenType.WHITESPACE, '\n\n')]
    }
}
