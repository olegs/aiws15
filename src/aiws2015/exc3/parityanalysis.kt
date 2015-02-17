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
//    analyze(Parser(ByteArrayInputStream(
//            """
//                inc y
//                zero y 1 else 1
//                stop
//                """.toByteArray())).parse(), TripleLattice(Parity.Top, Parity.Even, Parity.Even))
/*I get the following error here:
Exception in thread "main" java.lang.VerifyError: Bad type on operand stack
Exception Details:
  Location:
    aiws2015/exc2/Interval.incr()Laiws2015/exc2/Interval; @14: invokevirtual
  Reason:
    Type 'aiws2015/exc2/Interval' (current frame, stack[2]) is not assignable to 'aiws2015/exc2/Interval$Range'
  Current Frame:
    bci: @14
    flags: { }
    locals: { 'aiws2015/exc2/Interval', 'aiws2015/exc2/Interval' }
    stack: { uninitialized 9, uninitialized 9, 'aiws2015/exc2/Interval' }
  Bytecode:
    0x0000000: 2a4c 2bc1 0018 9900 1cbb 0018 592a b600
    0x0000010: 4404 602a b600 4104 60b7 0055 c000 02a7
    0x0000020: 0038 2bc1 0012 9900 16bb 0012 592a b600
    0x0000030: 4004 60b7 0061 c000 02a7 001e 2bc1 0015
    0x0000040: 9900 16bb 0015 592a b600 4504 60b7 0062
    0x0000050: c000 02a7 0004 2ab0
  Stackmap Table:
    append_frame(@34,Object[#2])
    same_frame(@60)
    same_frame(@86)
    same_locals_1_stack_item_frame(@87,Object[#2])

	at aiws2015.exc3.Exc3Package$parityanalysis$9f1eb4c8.main(parityanalysis.kt:24)
	at aiws2015.exc3.Exc3Package.main(Unknown Source)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:134)
*/
    analyze(Parser(ByteArrayInputStream(
            """
                zero x 4 else 2
                dec x
                zero x 4 else 2
                inc y
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

