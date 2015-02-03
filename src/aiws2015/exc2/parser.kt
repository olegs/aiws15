package aiws2015.exc2

import java.io.InputStream
import java.util.ArrayList

/**
 * Parser for 3CM machine https://github.com/jmid/3CounterMach
 * @author Oleg Shpynov
 * @date 03/02/15
 */

data class State(val ps: Int, val xv: Int, val yv: Int, val zv: Int)

trait Instruction {
    fun eval(s: State): State?
}

public class Parser(val inputStream: InputStream) {
    val lexer = Lexer(inputStream)

    fun parse(): List<Instruction> {

        fun error(token: Lexer.Token) {
            throw RuntimeException("Wrong token $token")
        }

        var instructions = ArrayList<Instruction>();
        while (true) {
            val token = lexer.nextToken()
            when (token) {
                is Lexer.INC -> {
                    val v = lexer.nextToken()
                    when (v) {
                        is Lexer.VAR -> instructions.add(INC(v.v))
                        else -> error(v)
                    }
                }
                is Lexer.DEC -> {
                    val v = lexer.nextToken()
                    when (v) {
                        is Lexer.VAR -> instructions.add(DEC(v.v))
                        else -> error(v)
                    }
                }
                is Lexer.ZERO -> {
                    val v = lexer.nextToken()
                    val p1 = lexer.nextToken()
                    val ele = lexer.nextToken()
                    val p2 = lexer.nextToken()
                    if (v is Lexer.VAR && p1 is Lexer.NUMBER && ele is Lexer.ELSE && p2 is Lexer.NUMBER)
                        instructions.add(ZERO(v.v, p1.number, p2.number))
                    else
                        throw RuntimeException("Error in ZERO parsing $v $p1 $ele $p2")
                }
                is Lexer.STOP -> {
                    instructions.add(STOP())
                    return instructions;
                }
                is Lexer.EOF -> throw RuntimeException("Enexpected EOF")
            }
        }
    }
}