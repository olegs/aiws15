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
        override fun toString() = "[-Inf; ${this.b}]"
    }

    data class PosInf(val a: Int) : Interval() {
        override fun toString() = "[${this.a}; +Inf]"
    }

    data class Range(val a: Int, val b: Int) : Interval() {
        override fun toString() = "[${this.a}; ${this.b}]"
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
                is NegInf -> this.b <= other.b
                is Range -> this.a >= other.a && this.b <= other.b
                is PosInf -> this.a >= other.a
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
                is NegInf -> if (this.b >= 0) Range(0, 0) else Bottom
                is PosInf -> if (this.a <= 0) Range(0, 0) else Bottom
                is Range -> if (this.a <= 0 && 0 <= this.b) Range(0, 0) else Bottom
                else -> throw RuntimeException()
            }

    override fun notzero(): Interval =
            when (this) {
                is Range ->
                    if (this.a == 0 && this.b == 0) Bottom
                    else if (this.a == 0) Range(1, this.b)
                    else if (this.b == 0) Range(this.a, -1)
                    else this
                is NegInf -> if (this.b == 0) NegInf(-1) else this
                is PosInf -> if (this.a == 0) PosInf(0) else this
                else -> this
            }

    override fun incr(): Interval =
            when (this) {
                is Range -> Range(this.a + 1, this.b + 1)
                is NegInf -> NegInf(this.b + 1)
                is PosInf -> PosInf(this.a + 1)
                else -> this
            }

    override fun decr(): Interval = when (this) {
        is Range -> Range(this.a - 1, this.b - 1)
        is NegInf -> NegInf(this.b - 1)
        is PosInf -> PosInf(this.a - 1)
        else -> this
    }

    fun normalize() : Interval = if (this is Range && this.a > this.b) Bottom else this
}


