package aiws2015.exc2

import java.io.ByteArrayInputStream

/** Runtime for for 3CM machine https://github.com/jmid/3CounterMach
 *
 * @author Oleg Shpynov
 * @date 03/02/15
 */

// TODO[oleg] add cmdline parser
fun main(args: Array<String>) {

    eval(42, Parser(ByteArrayInputStream(
            """
            zero x 5 else 2
            inc y
            dec x
            zero x 1 else 1
            stop
            """.toByteArray())).parse(), true)
    //    eval(5, Parser(FileInputStream("/home/oleg/work/3CounterMach/tests/test5-double.3cm")).parse(), true )
}

val MAX_INSTRUCTIONS = 1000

fun eval(input: Int, instructions: List<Instruction>, debug: Boolean) : Int {
    // Init state contract
    var state = State(1, input, 0, 0)

    for (i in 1..MAX_INSTRUCTIONS) {
        if (debug) {
            println("$i: $state")
        }
        if (state.ps < 1 || state.ps > instructions.size()) {
            throw RuntimeException("Out of bounds $i $state")
        }
        val instruction = instructions.get(state.ps - 1) // Minus 1 for natural ordering
        if (instruction is STOP) {
            println("Successfully finished in $i steps, result: ${state.yv}")
            return state.yv
        }

        val newState = instruction.eval(state)
        if (newState == null) {
            throw RuntimeException("3CM stuck")
        }
        state = newState
    }

    throw RuntimeException("NOT finished in in $MAX_INSTRUCTIONS steps, result: ${state.yv}")
}
