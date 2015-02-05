package aiws2015.exc2

/**
 * @author Oleg Shpynov
 * @date 2/5/15
 */

trait Lattice<T> {
    fun leq(p1: T, p2: T): Boolean
    fun join(p1: T, p2: T): T
    fun iszero(p: T): T
    fun notzero(p: T): T
    fun incr(p: T): T
    fun decr(p: T): T
}
