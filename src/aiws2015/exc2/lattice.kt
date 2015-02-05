package aiws2015.exc2

/**
 * @author Oleg Shpynov
 * @date 2/5/15
 */

trait Lattice<T> {
    fun bottom() : T
    fun name() : String
    fun leq(other: T): Boolean
    fun join(other: T): T
    fun iszero(): T
    fun notzero(): T
    fun incr(): T
    fun decr(): T
}

data class TripleLattice<T : Lattice<T>>(val x: T, val y: T, val z: T) {
    override fun toString(): String = "{x:$x, y:$y, z:$z}"
    fun join(other: TripleLattice<T>): TripleLattice<T> =
            TripleLattice<T>(x.join(other.x), y.join(other.y), z.join(other.z))
}
