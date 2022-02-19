import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Jihong Park
 * @version 1.0
 * @userid jpark3027
 * @GTID 903665760
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("data cannot be null");
        }
        size = 0;
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Helper method for add()
     * @param data the data to add
     * @param node root of the tree
     */
    private void addHelper(T data, BSTNode<T> node) {
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addHelper(data, node.getLeft());
            }
        } else if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<>(data));
                size++;
            } else {
                addHelper(data, node.getRight());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        BSTNode<T> removed = new BSTNode<>(null);
        root = removeHelper(data, root, removed);
        if (removed.getData() == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        return removed.getData();
    }

    /**
     * Helper method for remove
     * @param data data to remove
     * @param node root of the tree
     * @param removed removed data
     * @return removed node
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> node, BSTNode<T> removed) {
        if (node == null) {
            return null;
        } else {
            int val = data.compareTo(node.getData());
            if (val < 0) {
                node.setLeft(removeHelper(data, node.getLeft(), removed));
            } else if (val > 0) {
                node.setRight(removeHelper(data, node.getRight(), removed));
            } else {
                removed.setData(node.getData());
                size--;
                if (node.getRight() == null) {
                    return node.getLeft();
                } else if (node.getLeft() == null) {
                    return node.getRight();
                } else {
                    BSTNode<T> child = new BSTNode<>(null);
                    node.setRight(successorFind(node.getRight(), child));
                    node.setData(child.getData());
                }
            }
        }
        return node;
    }

    /**
     * to find successor
     * @param node current node
     * @param child child node
     * @return successor node
     */
    private BSTNode<T> successorFind(BSTNode<T> node, BSTNode<T> child) {
        if (node.getLeft() == null) {
            child.setData(node.getData());
            return node.getRight();
        }
        node.setRight(successorFind(node.getLeft(), child));
        return node;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        T temp = getHelper(data, root);
        if (temp == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        return temp;
    }

    /**
     * Helper method for get()
     * @param data the data to search for
     * @param node root of the tree
     * @return data from the tree matching the given parameter
     */
    private T getHelper(T data, BSTNode<T> node) {
        if (node == null) {
            return null;
        }
        int val = data.compareTo(node.getData());
        if (data.equals(node.getData())) {
            return node.getData();
        } else if (val < 0) {
            return getHelper(data, node.getLeft());
        } else {
            return getHelper(data, node.getRight());
        }
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> result = new ArrayList<>();
        preorderHelper(result, root);
        return result;
    }

    /**
     * Helper method for preorder
     * @param result the list containing each data
     * @param node root of the tree
     */
    private void preorderHelper(List<T> result, BSTNode<T> node) {
        if (node == null) {
            return;
        }
        result.add(node.getData());
        preorderHelper(result, node.getLeft());
        preorderHelper(result, node.getRight());
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorderHelper(result, root);
        return result;
    }

    /**
     * Helper method of inorder
     * @param result The list containing each data
     * @param node root of the tree
     */
    private void inorderHelper(List<T> result, BSTNode<T> node) {
        if (node == null) {
            return;
        }
        inorderHelper(result, node.getLeft());
        result.add(node.getData());
        inorderHelper(result, node.getRight());
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> result = new ArrayList<>();
        postorderHelper(result, root);
        return result;
    }

    /**
     * Helper method for postorder
     * @param result the list containing each data
     * @param node root of the tree
     */
    private void postorderHelper(List<T> result, BSTNode<T> node) {
        if (node == null) {
            return;
        }
        postorderHelper(result, node.getLeft());
        postorderHelper(result, node.getRight());
        result.add(node.getData());
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> result = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.poll();
            result.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return result;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for height
     * @param node root of the tree
     * @return height
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int l = heightHelper(node.getLeft());
        int r = heightHelper(node.getRight());
        if (l > r) {
            return l + 1;
        } else {
            return r + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("data cannot be null");
        } else if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("data is not in the tree");
        }
        List<T> result = new ArrayList<T>();
        BSTNode<T> common = findCommon(root, data1, data2);
        findPath(common, data1, result);
        findPath(common, data2, result);
        result.remove(result.size() - 1);
        if (result.size() < 1) {
            throw new NoSuchElementException("data is not in the tree");
        }
        return result;
    }

    /**
     * find common node
     * @param node root of the node
     * @param data1 given data
     * @param data2 given data
     * @return the common node
     */
    private BSTNode<T> findCommon(BSTNode<T> node, T data1, T data2) {
        if (node == null) {
            return null;
        }
        int val1 = node.getData().compareTo(data1);
        int val2 = node.getData().compareTo(data2);
        if (val1 > 0 && val2 > 0) {
            return findCommon(node.getLeft(), data1, data2);
        } else if (val1 < 0 && val2 < 0) {
            return findCommon(node.getRight(), data1, data2);
        } else {
            return node;
        }
    }

    /**
     * Find path between nodes
     * @param node given node
     * @param data data
     * @param result list to store data
     */
    private void findPath(BSTNode<T> node, T data, List<T> result) {
        if (node.getData().equals(data)) {
            result.add(node.getData());
        }
        if (node.getData().compareTo(data) > 0) {
            findPath(node.getLeft(), data, result);
            result.add(node.getData());
        }
        if (node.getData().compareTo(data) < 0) {
            findPath(node.getRight(), data, result);
            result.add(node.getData());
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
