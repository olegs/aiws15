package aiws2015.exc2

/**
 * Implement parity domain for http://janmidtgaard.dk/aiws15/exercises2.pdf
 * @author Oleg Shpynov
 * @date 03/02/15
 */

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


open class Parity() : Lattice<Parity> {
    override fun leq(p1: Parity, p2: Parity): Boolean = when (p1) {
        is Bottom -> true
        is Top -> if (p2 is Top) true else false
        is Even -> if (p2 is Even || p2 is Top) true else false
        is Odd -> if (p2 is Odd || p2 is Top) true else false
        else -> false
    }

    public override fun join(p1: Parity, p2: Parity): Parity = when (p1) {
        is Bottom -> p2
        is Top -> Top
        is Even -> if (p2 is Even || p2 is Bottom) Even else Top
        is Odd -> if (p2 is Odd || p2 is Bottom ) Odd else Top
        else -> throw RuntimeException("Unexpected: $p1")
    }

    override fun iszero(p: Parity): Parity =
            when (p) {
                is Bottom -> Bottom
                is Odd -> Bottom
                is Even -> Even
                is Top -> Even
                else -> throw RuntimeException()
            }

    override fun notzero(p: Parity): Parity = p;

    override fun incr(p: Parity): Parity =
            when (p) {
                is Bottom -> Bottom
                is Odd -> Even
                is Even -> Odd
                is Top -> Top
                else -> throw RuntimeException()
            }

    override fun decr(p: Parity): Parity = incr(p)
}


