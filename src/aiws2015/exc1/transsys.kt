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

    override fun next(x: Int): List<Int> =
            if (x < 100) Arrays.asList(x + 1) else emptyList()
}


class Prog11 : Transsys<Any> {
    override val initStates: List<Any>
        get() = emptyList()

    override fun next(x: Any): List<Any> {
        return emptyList()
    }
}


class Prog12 : Transsys<Int> {
    override val initStates: List<Int>
        get() = Arrays.asList(1)

    override fun next(x: Int): List<Int> {
        when (x) {
            1 -> return Arrays.asList(2)
            2 -> return Arrays.asList(1)
            3 -> return emptyList()
            else -> return Arrays.asList(3)
        }
    }
}


class Prog13 : Transsys<Int> {
    override val initStates: List<Int>
        get() = Arrays.asList(1)

    override fun next(x: Int): List<Int> = Arrays.asList(x + 1);
}

class Reachstates<State>(t: Transsys<State>) {
    val myT = t
    fun f(s: Set<State>): Set<State> =
            HashSet(myT.initStates).union(s.flatMap { x -> myT.next(x) });
}

val ITERATIONS = 1000

fun main(args: Array<String>) {
    println("WhileProg")
    lfp(WhileProg())
    println("1.1 Program, which finishes and takes finite number of steps to compute reachable set")
    lfp(Prog11())
    println("1.2 Program, which doesn't finish and takes finite number of steps to compute reachable set")
    lfp(Prog12())
    println("1.3 Program, which doesn't finish and takes infinite number of steps to compute reachable set")
    lfp(Prog13())
}

/**
 * Least fixed point
 */
private fun <State> lfp(prog: Transsys<State>): Boolean {
    var reachableStates = setOf<State>()
    for (i in 1..ITERATIONS) {
        val newStates = Reachstates(prog).f(reachableStates)
        if (reachableStates.equals(newStates)) {
            println("Least fixed point found in $i iterations\n$reachableStates")
            return true
        }
        reachableStates = newStates
    }
    println("No least fixed point found in $ITERATIONS iterations\n$reachableStates")
    return false
}


