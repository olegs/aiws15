package aiws2015.exc3

import aiws2015.exc2.Parser
import java.io.ByteArrayInputStream
import aiws2015.exc2.TripleLattice
import aiws2015.exc2.Parity
import aiws2015.exc2.Interval

/**
 * Parity analysis for http://janmidtgaard.dk/aiws15/exercises3.pdf
 *
 * @author Oleg Shpynov
 * @date 2/5/15
 */


fun main(args: Array<String>) {
    analyze(Parser(ByteArrayInputStream(
            """
                inc y
                zero y 1 else 1
                stop
                """.toByteArray())).parse(), TripleLattice(Parity.Top, Parity.Even, Parity.Even))
    analyze(Parser(ByteArrayInputStream(
            """
                inc y
                zero y 1 else 1
                stop
                """.toByteArray())).parse(), TripleLattice(Interval.Top, Interval.Range(0, 0), Interval.Range(0, 0)))

    /*    analyze(Parser(ByteArrayInputStream(
            """
                inc z
                zero z 3 else 4
                inc y
                dec z
                stop
                """.toByteArray())).parse(), TripleLattice(Parity.Top, Parity.Even, Parity.Even))
    analyze(Parser(ByteArrayInputStream(
            """
            zero x 6 else 2
            inc y
            inc y
            dec x
            zero x 1 else 1
            stop""".toByteArray())).parse(), TripleLattice(Parity.Top, Parity.Even, Parity.Even))
*/
}

