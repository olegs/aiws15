package aiws2015.exc2

/**
 * Implement interval domain for http://janmidtgaard.dk/aiws15/exercises2.pdf
 * @author Oleg Shpynov
 * @date 06/02/15
 */

open class Interval() : Lattice<Interval> {
    data object Bottom : Interval() {
        override fun toString() = "bot"
    }

    data object Top : Interval() {
        override fun toString() = "top"
    }

    data class NegInf(val b: Int) : Interval() {
        override fun toString() = "[-Inf; $b]"
    }

    data class PosInf(val a: Int) : Interval() {
        override fun toString() = "[$a; +Inf]"
    }

    data class Range(val a: Int, val b: Int) : Interval() {
        override fun toString() = "[$a; $b]"
    }

    override fun name(): String = "Interval domain"

    override fun bottom(): Interval = Bottom

    override fun leq(other: Interval): Boolean {
        if (this is Bottom) {
            return true
        }
        if (other is Top) {
            return true
        }
        return when (this) {
            is NegInf -> other is NegInf && this.b <= other.b
            is Range -> when (other) {
                is NegInf -> b <= other.b
                is Range -> a >= other.a && this.b <= other.b
                is PosInf -> a >= other.a
                else -> false
            }
            is PosInf -> other is PosInf && this.a >= other.a
            else -> false
        }

    }

    public override fun join(other: Interval): Interval =
            if (this.leq(other)) other
            else if (other.leq(this)) this
            else Top

    override fun iszero(): Interval =
            when (this) {
                is Bottom -> Bottom
                is Top -> Range(0, 0)
                is NegInf -> if (b >= 0) Range(0, 0) else Bottom
                is PosInf -> if (a <= 0) Range(0, 0) else Bottom
                is Range -> if (a <= 0 && 0 <= b) Range(0, 0) else Bottom
                else -> throw RuntimeException()
            }

    override fun notzero(): Interval =
            when (this) {
                is Range ->
                    if (a == 0 && b == 0) Bottom
                    else if (a == 0) Range(1, b)
                    else if (b == 0) Range(a, -1)
                    else this
                is NegInf -> if (b == 0) NegInf(-1) else this
                is PosInf -> if (a == 0) PosInf(0) else this
                else -> this
            }

    override fun incr(): Interval =
            when (this) {
                is Range -> Range(a + 1, b + 1)
                is NegInf -> NegInf(b + 1)
                is PosInf -> PosInf(a + 1)
                else -> this
            }

    override fun decr(): Interval = when (this) {
        is Range -> Range(a - 1, b - 1)
        is NegInf -> NegInf(b - 1)
        is PosInf -> PosInf(a - 1)
        else -> this
    }

    fun normalize() : Interval = if (this is Range && a > b) Bottom else this
}


