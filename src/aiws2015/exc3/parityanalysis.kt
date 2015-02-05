package aiws2015.exc3

import aiws2015.exc2.Parser
import java.io.ByteArrayInputStream
import aiws2015.exc2.Even
import aiws2015.exc2.Top
import aiws2015.exc2.TripleLattice

/**
 * Parity analysis for http://janmidtgaard.dk/aiws15/exercises3.pdf
 *
 * @author Oleg Shpynov
 * @date 2/5/15
 */


fun main(args: Array<String>) {
    /*    analyze(Parser(ByteArrayInputStream(
                """
                inc y
                zero y 1 else 1
                stop
                """.toByteArray())).parse())

        analyze(Parser(ByteArrayInputStream(
                """
                inc z
                zero z 3 else 4
                inc y
                dec z
                stop
                """.toByteArray())).parse())*/
    analyze(Parser(ByteArrayInputStream(
            """
            zero x 6 else 2
            inc y
            inc y
            dec x
            zero x 1 else 1
            stop""".toByteArray())).parse(), TripleLattice(Top, Even, Even))
}

