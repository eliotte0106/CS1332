import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Jihong Park
 * @userid jpark3027
 * @GTID 903665760
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        size = 0;
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        root = addHelper(data, root);
    }

    /**
     *
     * @param data to be added
     * @param node root of a tree
     * @return node to be balanced
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        int val = data.compareTo(node.getData());
        if (val < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (val > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else {
            return node;
        }
        findBfHeight(node);
        return balance(node);
    }

    /**
     * to find height and balance factor
     * @param node used to find height and balance factor
     */
    private void findBfHeight(AVLNode<T> node) {
        int lHeight = heightHelper(node.getLeft());
        int rHeight = heightHelper(node.getRight());
        node.setHeight(Math.max(lHeight, rHeight) + 1);
        node.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * rotate right
     * @param node needed to be rotated right
     * @return right rotated node
     */
    private AVLNode<T> rotateR(AVLNode<T> node) {
        AVLNode<T> child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);
        findBfHeight(node);
        findBfHeight(child);
        return child;
    }

    /**
     * to rotate left
     * @param node needed to be rotated left
     * @return left rotated node
     */
    private AVLNode<T> rotateL(AVLNode<T> node) {
        AVLNode<T> child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
        findBfHeight(node);
        findBfHeight(child);
        return child;
    }
    /**
     * to balance the tree
     * @param node needed to be balanced
     * @return balanced node
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateR(node.getRight()));
            }
            node = rotateL(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateL(node.getLeft()));
            }
            node = rotateR(node);
        }
        return node;
    }
    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }

    /**
     * remove helper method
     * @param data to find
     * @param node root node
     * @param removed the node contains removed data
     * @return removed node's parent node
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        int val = data.compareTo(node.getData());
        if (val < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removed));
        } else if (val > 0) {
            node.setRight(removeHelper(data, node.getRight(), removed));
        } else {
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> child = new AVLNode<>(null);
                node.setRight(successorHelper(node.getRight(), child));
                node.setData(child.getData());
            }
            size--;
        }
        findBfHeight(node);
        return balance(node);
    }
    /**
     * to find successor
     * @param node node to use
     * @param child child of the node that will be removed
     * @return successor node of the node that will be removed
     */
    private AVLNode<T> successorHelper(AVLNode<T> node, AVLNode<T> child) {
        if (node.getLeft() == null) {
            child.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(successorHelper(node.getLeft(), child));
            findBfHeight(node);
            return balance(node);
        }
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        T value = getHelper(data, root);
        return value;
    }

    /**
     * get helper method
     * @param data to be found
     * @param node root of the tree
     * @return data to be found
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        int val = data.compareTo(node.getData());
        if (val > 0) {
            return getHelper(data, node.getRight());
        } else if (val < 0) {
            return getHelper(data, node.getLeft());
        } else {
            return node.getData();
        }
    }
    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        AVLNode<T> node = new AVLNode<>(null);
        AVLNode<T> pre = predecessorHelper(root, node, data);
        if (pre != null) {
            return pre.getData();
        } else {
            throw new NoSuchElementException("data is not in the tree");
        }
    }

    /**
     * to find maximum value in the tree
     * @param node root of the tree
     * @return max value node
     */
    private AVLNode<T> findMax(AVLNode<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /**
     * predecessor helper method
     * @param node root node
     * @param pre null node
     * @param data data to be used to find predecessor
     * @return predecessor node
     */
    private AVLNode<T> predecessorHelper(AVLNode<T> node, AVLNode<T> pre, T data) {
        if (node == null) {
            return pre;
        }
        if (node.getData() == data) {
            if (node.getLeft() != null) {
                return findMax(node.getLeft());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            return predecessorHelper(node.getLeft(), pre, data);
        } else {
            pre = node;
            return predecessorHelper(node.getRight(), pre, data);
        }
        return pre;
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k should be > 0 and < size");
        }
        List<T> list = new ArrayList<>();
        return kSmallestHelper(k, list, root);
    }

    /**
     * kSmallestHelper (used inorder traversal to be always sorted)
     * @param k data to be compared
     * @param list list
     * @param node root of the tree
     * @return list containing K elements from ascending order
     */
    private List<T> kSmallestHelper(int k, List<T> list, AVLNode<T> node) {
        if (node == null || list.size() >= k) {
            return list;
        } else {
            kSmallestHelper(k, list, node.getLeft());
            if (list.size() < k) {
                list.add(node.getData());
            }
            kSmallestHelper(k, list, node.getRight());
        }
        return list;
    }
    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }
    /**
     * heightHelper used for findBFHeight method
     * @param node needed to be calculated
     * @return height of a node
     */
    private int heightHelper(AVLNode<T> node) {
        if (node != null) {
            return node.getHeight();
        } else {
            return -1;
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}