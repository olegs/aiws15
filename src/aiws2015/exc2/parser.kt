package aiws2015.exc2

import java.io.InputStream
import java.util.ArrayList

/**
 * Parser for 3CM machine https://github.com/jmid/3CounterMach
 * @author Oleg Shpynov
 * @date 03/02/15
 */

data class State(val ps: Int, val xv: Int, val yv: Int, val zv: Int)

trait Instruction
data class INC(val v: Char) : Instruction
data class DEC(val v: Char) : Instruction
data class ZERO(val v: Char, val p1: Int, val p2: Int) : Instruction
data class STOP() : Instruction


public class Parser(val inputStream: InputStream) {
    fun parse(): List<Instruction> {

        val bytes = inputStream.readBytes()
        var index = 0

        fun nextChar() = index++

        fun peekChar(): Char = bytes[index].toChar()

        fun isDone(): Boolean = index >= bytes.size()

        fun isWhiteSpace(c: Char): Boolean = c == ' ' || c == '\t' || c == '\n' || c == '\r'

        fun lexeme(): String {
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
        fun error(lexeme: String) {
            throw RuntimeException("Wrong lexeme $lexeme")
        }

        fun v(lexeme: String): Char? = when (lexeme) {
            "x", "y", "z" -> lexeme.charAt(0)
            else -> null
        }

        var instructions = ArrayList<Instruction>();
        while (true) {
            val lexeme = lexeme()
            when (lexeme) {
                "inc" -> {
                    val v = v(lexeme())
                    if (v != null) instructions.add(INC(v)) else error("Wrong var")
                }
                "dec" -> {
                    val v = v(lexeme())
                    if (v != null) instructions.add(DEC(v)) else error("Wrong var")
                }
                "zero" -> {
                    val v = v(lexeme())
                    val p1 = Integer.valueOf(lexeme())
                    val ele = lexeme()
                    val p2 = Integer.valueOf(lexeme())
                    if (v != null &&  "else".equals(ele))
                        instructions.add(ZERO(v, p1, p2))
                    else
                        throw RuntimeException("Error in ZERO parsing $v $p1 $ele $p2")
                }
                "stop" -> {
                    instructions.add(STOP())
                    return instructions;
                }
                else -> throw RuntimeException("Enexpected $lexeme")
            }
        }
    }
}