package aiws2015.exc3

import aiws2015.exc2.Parser
import java.io.ByteArrayInputStream
import aiws2015.exc2.Parity
import java.util.HashMap
import aiws2015.exc2.Instruction
import aiws2015.exc2.Bottom
import aiws2015.exc2.INC
import aiws2015.exc2.DEC
import aiws2015.exc2.ZERO
import aiws2015.exc2.Even
import aiws2015.exc2.Top

/**
 * Parity analysis for http://janmidtgaard.dk/aiws15/exercises3.pdf
 *
 * @author Oleg Shpynov
 * @date 2/5/15
 */

data class TP(val x: Parity, val y: Parity, val z: Parity) {
    override fun toString(): String = "{x:$x, y:$y, z:$z}"
    fun join(other: TP): TP = TP(Parity().join(x, other.x), Parity().join(y, other.y), Parity().join(z, other.z))
}

fun apply(s: HashMap<Int, TP>, instructions: List<Instruction>): HashMap<Int, TP> {
    var result = HashMap(s)
    for (pc in 1..instructions.size()) {
        val instruction = instructions.get(pc - 1)
        val tp = result.get(pc)
        when (instruction) {
            is INC -> result.put(pc + 1, tp.join(fvar(tp, instruction.v, { p -> Parity().incr(p) })))
            is DEC -> result.put(pc + 1, tp.join(fvar(tp, instruction.v, { p -> Parity().decr(p) })))
            is ZERO -> {
                result.put(instruction.p1, tp.join(fvar(tp, instruction.v, { p -> Parity().iszero(p) })))
                result.put(instruction.p2, tp.join(fvar(tp, instruction.v, { p -> Parity().notzero(p) })))
            }
        }
    }
    return result
}

fun fvar(p: TP, v: Char, f: Function1<Parity, Parity>): TP =
        when (v) {
            'x' -> p.copy(x = f(p.x))
            'y' -> p.copy(y = f(p.y))
            'z' -> p.copy(z = f(p.z))
            else -> throw RuntimeException("Illegal var $v")
        }

fun main(args: Array<String>) {
    /*    analyze(Parser(ByteArrayInputStream(
                """
                inc y
                zero y 1 else 1
                stop
                """.toByteArray())).parse())

        analyze(Parser(ByteArrayInputStream(
                """
                inc z
                zero z 3 else 4
                inc y
                dec z
                stop
                """.toByteArray())).parse())*/
    analyze(Parser(ByteArrayInputStream(
            """
            zero x 6 else 2
            inc y
            inc y
            dec x
            zero x 1 else 1
            stop""".toByteArray())).parse())
}

private fun analyze(instructions: List<Instruction>) {
    val MAX_ITERATIONS = 100

    // Init
    var s = hashMapOf(Pair(1, TP(Top, Even, Even)))
    for (i in 2..instructions.size()) {
        s.put(i, TP(Bottom, Bottom, Bottom))
    }


    for (i in 1..MAX_ITERATIONS) {
        val sNext = apply(s, instructions)
        if (s.equals(sNext)) {
            println("Approximation in Parity domain found in $i steps")
            pp(instructions, s)
            return
        } else {
            s = sNext;
        }
    }
    println("NO Approximation in Parity domain found in $MAX_ITERATIONS steps")
    pp(instructions, s)
}

fun pp(instructions: List<Instruction>, s: Map<Int, TP>) {
    val builder = StringBuilder()
    for (i in 1..instructions.size()) {
        builder.append("%3d: %-20s %s\n".format(i, instructions.get(i - 1), s.get(i)))
    }
    print(builder.toString())
}
