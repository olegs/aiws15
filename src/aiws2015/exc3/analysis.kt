package aiws2015.exc3

import aiws2015.exc2.Lattice
import java.util.HashMap
import aiws2015.exc2.Instruction
import aiws2015.exc2.INC
import aiws2015.exc2.DEC
import aiws2015.exc2.ZERO
import aiws2015.exc2.TripleLattice

/** Abstract analysis using given lattice for 3cm machine
 * @author Oleg Shpynov
 * @date 2/5/15
 */

/**
 * Main function, performs analysis on abstract TP - triple of Lattice
 */
fun <T: Lattice<T>>analyze(instructions: List<Instruction>, start: TripleLattice<T>) {
    val MAX_ITERATIONS = 100

    // Init
    var s = hashMapOf(Pair(1, start))
    for (i in 2..instructions.size()) {
        s.put(i, TripleLattice(start.x.bottom(), start.x.bottom(), start.x.bottom()))
    }


    for (i in 1..MAX_ITERATIONS) {
        val sNext = apply(s, instructions)
        if (s.equals(sNext)) {
            println("Approximation in ${start.x.name()} found in $i steps")
            pretty_print(instructions, s)
            return
        } else {
            s = sNext;
        }
    }
    println("NO Approximation in ${start.x.name()} found in $MAX_ITERATIONS steps")
    pretty_print(instructions, s)
}

private fun <T : Lattice<T>> apply(s: HashMap<Int, TripleLattice<T>>, instructions: List<Instruction>):
        HashMap<Int, TripleLattice<T>> {
    var result = HashMap(s)
    for (pc in 1..instructions.size()) {
        val instruction = instructions.get(pc - 1)
        when (instruction) {
            is INC -> result.put(pc + 1, result.get(pc + 1).join(var_apply(s.get(pc), instruction.v, { p -> p.incr() })))
            is DEC -> result.put(pc + 1, result.get(pc + 1).join(var_apply(s.get(pc), instruction.v, { p -> p.decr() })))
            is ZERO -> {
                result.put(instruction.p1, result.get(instruction.p1).join(var_apply(s.get(pc), instruction.v, { p -> p.iszero() })))
                result.put(instruction.p2, result.get(instruction.p2).join(var_apply(s.get(pc), instruction.v, { p -> p.notzero() })))
            }
        }
    }
    return result
}

private fun <T : Lattice<T>> var_apply(p: TripleLattice<T>, v: Char, f: Function1<T, T>): TripleLattice<T> =
        when (v) {
            'x' -> p.copy(x = f(p.x))
            'y' -> p.copy(y = f(p.y))
            'z' -> p.copy(z = f(p.z))
            else -> throw RuntimeException("Illegal var $v")
        }

private fun <T: Lattice<T>>pretty_print(instructions: List<Instruction>, s: Map<Int, TripleLattice<T>>) {
    val builder = StringBuilder()
    for (i in 1..instructions.size()) {
        builder.append("%3d: %-20s %s\n".format(i, instructions.get(i - 1), s.get(i)))
    }
    print(builder.toString())
}