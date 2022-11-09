import java.lang.Integer.max

class Tree {

    private var root: Node? = null

    init {
        this.root = null
    }

    fun getRoot(): Node? {
        return root
    }

    fun search(key: Int): Node? {
        var currentNode: Node? = root ?: return null
        while (currentNode!!.key != key) {
            currentNode = if (key < currentNode.key) currentNode.leftChild
            else currentNode.rightChild
            if (currentNode == null) return null
        }
        return currentNode
    }

    // right rotation
    private fun rotateRight(y: Node?): Node {
        val x: Node? = y!!.leftChild
        val b: Node? = x!!.rightChild
        //rotation
        x.rightChild = y
        y.leftChild = b
        // updating heights
        y.height = max(getHeight(y.leftChild), getHeight(y.rightChild)) + 1
        x.height = max(getHeight(x.leftChild), getHeight(x.rightChild)) + 1
        // return new root
        return x
    }

    // left rotation
    private fun rotateLeft(x: Node?): Node {
        val y: Node? = x!!.rightChild
        val b: Node? = y!!.leftChild
        //rotation
        y.leftChild = x
        x.rightChild = b
        // updating heights
        x.height = max(getHeight(x.leftChild), getHeight(x.rightChild)) + 1
        y.height = max(getHeight(y.leftChild), getHeight(y.rightChild)) + 1
        // return new root
        return y
    }

    private fun balance(root: Node): Node {
        val bf = getBalanceFactor(root)
        when {
            bf > 1 && getBalanceFactor(root.leftChild) >= 0 ->
                return rotateRight(root) // LL case
            bf > 1 && getBalanceFactor(root.leftChild) < 0 -> {
                root.leftChild = rotateLeft(root.leftChild)
                return rotateRight(root) // LR case
            }
            bf < -1 && getBalanceFactor(root.rightChild) <= 0 ->
                return rotateLeft(root) // RR case
            bf < -1 && getBalanceFactor(root.rightChild) > 0 -> {
                root.rightChild = rotateRight(root.rightChild)
                return rotateLeft(root) // RL case
            }
        }
        return root
    }

    // recursive function to insert a node with the given key in the tree with given root, returns the new root of the subtree.
    private fun insertInner(root: Node?, inputKey: Int): Node {
        return if (root == null) Node(inputKey)
        else {
            if (inputKey < root.key) root.leftChild = insertInner(root.leftChild, inputKey)
            else root.rightChild = insertInner(root.rightChild, inputKey)
            // update height
            root.height = 1 + max(getHeight(root.leftChild), getHeight(root.rightChild))
            balance(root)
        }
    }

    fun insert(inputKey: Int) {
        root = insertInner(root, inputKey)
    }

    private fun minValueNode(node: Node): Node {
        var current: Node = node
        // loop down to find the leftmost leaf
        while (current.leftChild != null) current = current.leftChild!!
        return current
    }

    /** Recursive function to delete a node with given inputKey
     * from subtree with given root. It returns root of
     * the modified subtree */
    private fun deleteInner(node: Node?, inputKey: Int): Node? {
        var root: Node? = node
        if (root == null) return root
        // if inputKey < key our node if in the left subtree
        when {
            inputKey < root.key -> root.leftChild = deleteInner(root.leftChild, inputKey)
            inputKey > root.key -> root.rightChild = deleteInner(root.rightChild, inputKey)
            else -> { // if (inputKey == key) we found node to be deleted
                when {
                    root.leftChild == null && root.rightChild == null -> root = null
                    root.leftChild == null || root.rightChild == null -> {
                        val helper: Node? = if (root.leftChild != null) root.leftChild
                        else root.rightChild
                        root = helper
                    }
                    else -> {
                        val helper: Node = minValueNode(root.rightChild!!)
                        root.key = helper.key
                        root.rightChild = deleteInner(root.rightChild, helper.key)
                    }
                }
            }
        }
        if (root == null) return root
        root.height = max(getHeight(root.leftChild), getHeight(root.rightChild))
        return balance(root)
    }

    fun delete(inputKey: Int) {
        root = deleteInner(root, inputKey)
    }

    private fun printTreeInner(root: Node?, height: Int) {
        if (root != null) {
            printTreeInner(root.leftChild, height + 1)
            for (i in 0 until height) print("    ")
            print("${root.key}\n")
            printTreeInner(root.rightChild, height + 1)
        }
    }

    fun printTree() {
        printTreeInner(root, 0)
    }

    /** get the height of the tree (height of the highest subtree)*/
    private fun getHeight(node: Node?): Int = node?.height ?: 0

    /** gets balance factor of input node
     * -2 => right subtree is too high
     * -1, 0, 1 => balanced
     * 2 => left subtree is too high */
    fun getBalanceFactor(node: Node?): Int =
        if (node == null) 0
        else getHeight(node.leftChild) - getHeight(node.rightChild)

}

class Node(key: Int) {

    var key: Int
    var leftChild: Node?
    var rightChild: Node?
    var height: Int

    init {
        this.key = key
        this.leftChild = null
        this.rightChild = null
        this.height = 1
    }

}