package aiws2015.exc2

/**
 * Instructions for 3CM machine https://github.com/jmid/3CounterMach
 * @author Oleg Shpynov
 * @date 03/02/15
 */


class INC(val v: Char) : Instruction {
    override fun eval(s: State): State? {
        return when (v) {
            'x' -> State(s.ps + 1, s.xv + 1, s.yv, s.zv)
            'y' -> State(s.ps + 1, s.xv, s.yv + 1, s.zv)
            'z' -> State(s.ps + 1, s.xv, s.yv, s.zv + 1)
            else -> null;
        }
    }
}

class DEC(val v: Char) : Instruction {
    override fun eval(s: State): State? {
        return when (v) {
            'x' -> if (s.xv > 0) State(s.ps + 1, s.xv - 1, s.yv, s.zv) else null
            'y' -> if (s.yv > 0) State(s.ps + 1, s.xv, s.yv - 1, s.zv) else null
            'z' -> if (s.zv > 0) State(s.ps + 1, s.xv, s.yv, s.zv - 1) else null
            else -> null;
        }
    }
}

class ZERO(val v: Char, val p1: Int, val p2: Int) : Instruction {
    override fun eval(s: State): State? {
        return when (v) {
            'x' -> if (s.xv == 0) State(p1, s.xv, s.yv, s.zv) else State(p2, s.xv, s.yv, s.zv)
            'y' -> if (s.yv == 0) State(p1, s.xv, s.yv, s.zv) else State(p2, s.xv, s.yv, s.zv)
            'z' -> if (s.zv == 0) State(p1, s.xv, s.yv, s.zv) else State(p2, s.xv, s.yv, s.zv)
            else -> null;
        }
    }
}

class STOP() : Instruction {
    override fun eval(s: State): State? = null
}
