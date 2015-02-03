package aiws2015.exc2

/**
 * Implement parity domain for http://janmidtgaard.dk/aiws15/exercises2.pdf
 * @author Oleg Shpynov
 * @date 03/02/15
 */

trait Parity {

    fun bot(): Parity = Bottom()

    fun leq(p1: Parity, p2: Parity): Boolean = try {
        p1 <= p2
    } catch (_ : NotComparableException) {
        false
    }

    fun join(p1: Parity, p2: Parity): Parity {
        if (p1 < p2) {
            return p1;
        }
        if (p2 < p1) {
            return p2;
        }
        if (p1 is Odd && p2 is Even || p1 is Even && p2 is Odd) {
            return Bottom();
        }
        return p1;
    }

    fun iszero(p: Parity): Parity =
            when (p) {
                is Bottom -> Bottom()
                is Odd -> Bottom()
                is Even -> Even()
                is Top -> Even()
                else -> throw RuntimeException()
            }

    fun notzero(p: Parity): Parity = p;

    fun incr(p: Parity): Parity =
            when (p) {
                is Bottom -> Bottom()
                is Odd -> Even()
                is Even -> Odd()
                is Top -> Top()
                else -> throw RuntimeException()
            }

    fun decr(p: Parity): Parity =
            when (p) {
                is Bottom -> Bottom()
                is Odd -> Even()
                is Even -> Odd()
                is Top -> Top()
                else -> throw RuntimeException()
            }

    fun compareTo(other: Parity): Int;
}

class NotComparableException(msg:String) : Exception(msg)

data class Bottom : Parity {
    override fun compareTo(other: Parity): Int = if (other is Bottom) 0 else -1
}

data class Top : Parity {
    override fun compareTo(other: Parity): Int = if (other is Top) 0 else 1
}

data class Odd : Parity {
    override fun compareTo(other: Parity): Int =
            if (other is Odd) 0
            else if (other !is Even) -other.compareTo(this)
            else throw NotComparableException("$this and $other are not comparable")
}

data class Even : Parity {
    override fun compareTo(other: Parity): Int =
            if (other is Even) 0
            else if (other !is Odd) -other.compareTo(this)
            else throw NotComparableException("$this and $other are not comparable")
}

