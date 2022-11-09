import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class Tests {

    private fun createTestTree(): Tree {
        val tree = Tree()
        tree.insert(50)
        tree.insert(40)
        tree.insert(60)
        tree.insert(20)
        tree.insert(5)
        tree.insert(15)
        tree.insert(90)
        tree.insert(85)
        tree.insert(70)
        tree.insert(75)
        tree.insert(52)
        tree.insert(43)
        tree.insert(61)
        tree.insert(29)
        tree.insert(9)
        tree.insert(17)
        tree.insert(96)
        tree.insert(86)
        tree.insert(76)
        tree.insert(34)
        return tree
    }

    private fun createEqualTestTree(): Tree {
        val tree = Tree()
        for (i in 0 until 20) tree.insert(10)
        return tree
    }

    private fun checkBalance(tree: Tree, root: Node?): Boolean {
        if (root == null) return true
        var x: Boolean = checkBalance(tree, root.leftChild)
        if (!x) return false
        x = checkBalance(tree, root.rightChild)
        if (!x) return false
        if (kotlin.math.abs(tree.getBalanceFactor(tree.getRoot())) > 1) return false
        return true
    }

    @Test
    fun testInsert() {
        val tree = Tree()
        for (i in 200 downTo 0 step 10) tree.insert(i)
        val a = tree.search(200)
        val b = tree.search(100)
        assertNotEquals(a, null)
        assertNotEquals(b, null)
        assertEquals(a!!.key, 200)
        assertEquals(b!!.key, 100)
        assertTrue { checkBalance(tree, tree.getRoot()) }
    }

    @Test
    fun testSearch() {
        val tree = createTestTree()
        val a = tree.search(15)
        val b = tree.search(5)
        val c = tree.search(90)
        val d = tree.search(200)
        assertNotEquals(a, null)
        assertNotEquals(b, null)
        assertNotEquals(c, null)
        assertEquals(a!!.key, 15)
        assertEquals(b!!.key, 5)
        assertEquals(c!!.key, 90)
        assertEquals(d, null)
        assertTrue { checkBalance(tree, tree.getRoot()) }
    }

    @Test
    fun testDelete() {
        val tree = createTestTree()
        tree.delete(20)
        tree.delete(90)
        tree.delete(75)
        tree.delete(5)
        assertTrue { checkBalance(tree, tree.getRoot()) }
    }

    @Test
    fun testDeleteEqual() {
        val tree = createEqualTestTree()
        tree.delete(10)
        tree.delete(10)
        tree.delete(10)
        tree.delete(10)
        assertTrue { checkBalance(tree, tree.getRoot()) }
    }

}
