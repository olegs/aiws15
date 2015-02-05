package aiws2015.exc2

import java.io.ByteArrayInputStream

/** Runtime for for 3CM machine https://github.com/jmid/3CounterMach
 *
 * @author Oleg Shpynov
 * @date 03/02/15
 */

// TODO[oleg] add cmdline parser
fun main(args: Array<String>) {
    execute(42, Parser(ByteArrayInputStream(
            """
            zero x 5 else 2
            inc y
            dec x
            zero x 1 else 1
            stop
            """.toByteArray())).parse(), true)
    //    execute(5, Parser(FileInputStream("/home/oleg/work/3CounterMach/tests/test5-double.3cm")).parse(), true )
}




fun execute(input: Int, instructions: List<Instruction>, debug: Boolean): Int {

    val MAX_INSTRUCTIONS = 100

    fun eval(instruction: Instruction, s: State): State? {
        return when (instruction) {
            is INC -> when (instruction.v) {
                'x' -> State(s.ps + 1, s.xv + 1, s.yv, s.zv)
                'y' -> State(s.ps + 1, s.xv, s.yv + 1, s.zv)
                'z' -> State(s.ps + 1, s.xv, s.yv, s.zv + 1)
                else -> null;
            }
            is DEC -> when (instruction.v) {
                'x' -> if (s.xv > 0) State(s.ps + 1, s.xv - 1, s.yv, s.zv) else null
                'y' -> if (s.yv > 0) State(s.ps + 1, s.xv, s.yv - 1, s.zv) else null
                'z' -> if (s.zv > 0) State(s.ps + 1, s.xv, s.yv, s.zv - 1) else null
                else -> null;
            }
            is ZERO -> when (instruction.v) {
                'x' -> if (s.xv == 0) State(instruction.p1, s.xv, s.yv, s.zv) else State(instruction.p2, s.xv, s.yv, s.zv)
                'y' -> if (s.yv == 0) State(instruction.p1, s.xv, s.yv, s.zv) else State(instruction.p2, s.xv, s.yv, s.zv)
                'z' -> if (s.zv == 0) State(instruction.p1, s.xv, s.yv, s.zv) else State(instruction.p2, s.xv, s.yv, s.zv)
                else -> null;
            }
            else -> null
        }
    }
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
        val newState = eval(instruction, state)
        if (newState == null) {
            throw RuntimeException("3CM stuck")
        }
        state = newState
    }

    throw RuntimeException("NOT finished in in $MAX_INSTRUCTIONS steps, result: ${state.yv}")
}


