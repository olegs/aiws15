package aiws2015.exc2

/**
 * Lexer for 3CM machine https://github.com/jmid/3CounterMach
 *
 * @author Oleg Shpynov
 * @date 03/02/15
 */

import java.util.regex.Pattern
import java.io.InputStream


public class Lexer(val inputStream: InputStream) {
    trait Token
    data class INC : Token
    data class DEC : Token
    data class ZERO : Token
    data class ELSE : Token
    data class STOP : Token
    data class EOF : Token
    data class NUMBER(val number: Int) : Token
    data class VAR(val v: Char) : Token


    val bytes = inputStream.readBytes()
    var index = 0

    private fun nextChar() = index++

    private fun peekChar(): Char = bytes[index].toChar()

    private fun isDone(): Boolean = index >= bytes.size()

    fun isWhiteSpace(c: Char): Boolean {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private fun lexeme(): String {
        // Skip whitespace
        while (!isDone() && isWhiteSpace(peekChar())) {
            nextChar()
        }

        var lexemeBuilder = StringBuilder()
        while (!isDone() && !isWhiteSpace(peekChar())) {
            lexemeBuilder.append(peekChar())
            nextChar()
        }
        val lexeme = lexemeBuilder.toString()
        return lexeme
    }

    fun nextToken(): Token {
        val lexeme = lexeme()
        if (Pattern.compile("\\d+").matcher(lexeme).matches()) {
            return NUMBER(java.lang.Integer.parseInt(lexeme))
        }

        return when (lexeme) {
            "x", "y", "z" -> VAR(lexeme.charAt(0))
            "inc" -> INC()
            "dec" -> DEC()
            "zero" -> ZERO()
            "else" -> ELSE()
            "stop" -> STOP()
            "" -> EOF()
            else -> throw RuntimeException("Unknown lexeme $index: $lexeme")
        }
    }

}
