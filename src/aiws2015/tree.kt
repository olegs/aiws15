// Algebraic type Tree
trait Tree<T>
data object Empty : Tree<Void>
data class Leaf<T>(val value: T) : Tree<T>
data class Node<T>(val left: Tree<T>, val right: Tree<T>) : Tree<T>

fun max(x:Int, y:Int):Int = if (x > y) x else y

fun <T> depth(t: Tree<T>): Int = when (t) {
    is Empty -> 0
    is Leaf  -> 1
    is Node  -> 1 + max(depth(t.left), depth(t.right))
// unfortunately, the else branch is required
    else     -> throw Throwable()
}

fun main(args: Array<String>) {
    println(depth(Node(Node(Leaf(1), Leaf(2)), Leaf(3))))
}