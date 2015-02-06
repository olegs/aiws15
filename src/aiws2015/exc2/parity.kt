package aiws2015.exc2

/**
 * Implement parity domain for http://janmidtgaard.dk/aiws15/exercises2.pdf
 * @author Oleg Shpynov
 * @date 03/02/15
 */

open class Parity() : Lattice<Parity> {
    // TODO[oleg] why aren't they generated
    data object Bottom : Parity() {
        override fun toString() = "bot"
    }

    data object Top : Parity() {
        override fun toString() = "top"
    }

    data object Odd : Parity() {
        override fun toString() = "odd"
    }

    data object Even : Parity() {
        override fun toString() = "even"
    }


    override fun name(): String = "Parity domain"

    override fun bottom(): Parity = Bottom

    override fun leq(other: Parity): Boolean = when (this) {
        is Bottom -> true
        is Top -> if (other is Top) true else false
        is Even -> if (other is Even || other is Top) true else false
        is Odd -> if (other is Odd || other is Top) true else false
        else -> false
    }

    public override fun join(other: Parity): Parity =
            if (this.leq(other)) other
            else if (other.leq(this)) this
            else Top

    override fun iszero(): Parity =
            when (this) {
                is Bottom -> Bottom
                is Odd -> Bottom
                is Even -> Even
                is Top -> Even
                else -> throw RuntimeException()
            }

    override fun notzero(): Parity = this;

    override fun incr(): Parity =
            when (this) {
                is Bottom -> Bottom
                is Odd -> Even
                is Even -> Odd
                is Top -> Top
                else -> throw RuntimeException()
            }

    override fun decr(): Parity = incr()
}


