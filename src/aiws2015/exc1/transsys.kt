package aiws2015.exc1

import java.util.ArrayList
import java.util.HashSet
import java.util.Arrays

/**
 * Sample implementation of Transitional Semantics
 * @author Oleg Shpynov
 * @date 02/02/15
 */

trait Transsys<State> {
    val initStates: List<State>;
    fun next(x: State): List<State>;
}

class WhileProg : Transsys<Int> {
    override val initStates: List<Int>
        get() = Arrays.asList(1)

    override fun next(x: Int): List<Int> {
        return if (x < 100) Arrays.asList(x + 1) else emptyList()
    }
}

class Reachstates<State>(t: Transsys<State>) {
    val myT = t

    fun f(s : Set<State>): Set<State> {
        val set = HashSet<State>(myT.initStates)
        val list = s.flatMap { x -> myT.next(x) }
        return set.union(list);
    }
}
val ITERATIONS = 1000

fun main(args: Array<String>) {
    findMinimalFixedPoint(WhileProg())
}

private fun findMinimalFixedPoint(whileProg: WhileProg): Boolean {
    var reachableStates = setOf<Int>()
    for (i in 1..ITERATIONS) {
        val newStates = Reachstates(whileProg).f(reachableStates)
        if (reachableStates.equals(newStates)) {
            println("Minimal Fixed Point\n$reachableStates")
            return true
        }
        reachableStates = newStates
    }
    println("No Fixed point found in $ITERATIONS iterations\n$reachableStates")
    return false
}


