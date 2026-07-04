public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> oldRoot = node;
        RBTreeNode<T> newRoot = node.left;
        RBTreeNode<T> parent = findParent(oldRoot);
        RBTreeNode<T> formerChild = newRoot.right;
        boolean oldIsBlack = oldRoot.isBlack;
        if (parent != null && parent.item.compareTo(oldRoot.item) > 0) {
            parent.left = newRoot;
        } else if (parent != null && parent.item.compareTo(oldRoot.item) < 0) {
            parent.right = newRoot;
        } else {
            root = newRoot;
        }
        newRoot.right = oldRoot;
        oldRoot.left = formerChild;
        oldRoot.isBlack = newRoot.isBlack;
        newRoot.isBlack = oldIsBlack;
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> oldRoot = node;
        RBTreeNode<T> newRoot = node.right;
        RBTreeNode<T> parent = findParent(oldRoot);
        RBTreeNode<T> formerChild = newRoot.left;
        boolean oldIsBlack = oldRoot.isBlack;
        if (parent != null && parent.item.compareTo(oldRoot.item) > 0) {
            parent.left = newRoot;
        } else if (parent != null && parent.item.compareTo(oldRoot.item) < 0) {
            parent.right = newRoot;
        } else {
            root = newRoot;
        }
        newRoot.left = oldRoot;
        oldRoot.right = formerChild;
        oldRoot.isBlack = newRoot.isBlack;
        newRoot.isBlack = oldIsBlack;
        return newRoot;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }
    public RBTreeNode<T> findParent(RBTreeNode<T> node) {
        if (root == node || root == null) {
            return null;
        }
        RBTreeNode<T> potentialParent = root;
        boolean foundP = false;
        while (!foundP) {
            int compari = potentialParent.item.compareTo(node.item);
            if (compari > 0) {
                if (potentialParent.left == null || potentialParent.left == node) {
                    foundP = true;
                } else {
                    potentialParent = potentialParent.left;
                }
            } else if (compari < 0) {
                if (potentialParent.right == null || potentialParent.right == node) {
                    foundP = true;
                } else {
                    potentialParent = potentialParent.right;
                }
            }
        }
        return potentialParent;
    }
    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        RBTreeNode<T> insertNode = new RBTreeNode<>(false, item);
        if (node == null) {
            insertNode.isBlack = true;
            return insertNode;
        }
        RBTreeNode<T> parent = node;
        boolean inserted = false;
        while (!inserted) {
            int compari = parent.item.compareTo(item);
            if (compari == 0) {
                return node;
            } else if (compari > 0) {
                if (parent.left == null) {
                    parent.left = insertNode;
                    inserted = true;
                } else {
                    parent = parent.left;
                }
            } else {
                if (parent.right == null) {
                    parent.right = insertNode;
                    inserted = true;
                } else {
                    parent = parent.right;
                }
            }
        }
        boolean flippedRoot = false;
        while (!flippedRoot) {
            if (!isRed(parent.left) && isRed(parent.right)) { // i.e. insertNode is red, the only child of parent,
                // and is the right child
                parent = rotateLeft(parent);
            } else if (isRed(parent) && isRed(parent.left)) { // do not need to check if insertNode is left child
                // as the above conditional will ensure it is
                parent = rotateRight(findParent(parent)); // will never be null from having root as parent
                // since root can never be red
            } else if (isRed(parent.left) && isRed(parent.right)) {
                flipColors(parent);
                if (findParent(parent) == null) {
                    flippedRoot = true;
                } else {
                    parent = findParent(parent);
                }
            } else { // no invariants found
                if (findParent(parent) == null) {
                    return parent;
                } else {
                    parent = findParent(parent);
                }
            }
        }
        return parent;
    }

}
