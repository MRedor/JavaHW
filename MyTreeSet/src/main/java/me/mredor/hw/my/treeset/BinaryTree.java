package me.mredor.hw.my.treeset;

import java.util.Comparator;
import java.util.Iterator;

public class BinaryTree<E> {
    private Comparator<? super E> comparator;
    private Node top;
    private int size = 0;

    /** Constructs a new, empty tree, sorted according to the specified comparator. */
    public BinaryTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /** Returns the number of elements in the tree */
    public int size() {
        return size;
    }

    private Node find(E element) {
        var current = top;
        while (current != null) {
            if (comparator.compare(current.value, element) == 0) {
                return current;
            } else {
                if (comparator.compare(current.value, element) > 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
        }
        return null;
    }

    /** Returns true if the tree contains the element and false otherwise. */
    public boolean contains(Object element) {
        return find((E) element) != null;
    }

    private Node lowerBound(Object element) {
        var current = top;
        Node result = null;

        while (current != null) {
            if (comparator.compare(current.value, (E) element) > 0) {
                result = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return result;
    }

    /** Adds element to the tree.
     * Returns true if the tree already contains the element and false otherwise. */
    public boolean add(E element) {
        if (contains(element)) {
            return false;
        }
        if (top == null) {
            top = new Node(element, null);
            size++;
            return true;
        }
        var node = lowerBound(element);
        if (node == null || node.left != null) {
            if (node != null) {
                node = last(node.left);
            } else {
                node = last(top);
            }
            node.right = new Node(element, node);
        } else {
            node.left = new Node(element, node);
        }
        size++;
        return true;
    }

    /** Removes element from tree.
     * Returns true if the tree contained element and false otherwise. */
    public boolean remove(Object element) {
        if (!contains(element)) {
            return false;
        }
        var node = find((E) element);
        removeNode(node);
        size--;
        return true;
    }

    private void removeNode(Node node) {
        if (node.left == null) {
            if (node.parent == null) {
                top = node.right;
                if (top != null) {
                    top.parent = null;
                }
                return;
            }
            if (node.parent.left == node) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }

            if (node.right != null) {
                node.right.parent = node.parent;
            }
            return;
        }
        if (node.left.right == null) {
            if (node.parent != null) {
                if (node.parent.left == node) {
                    node.parent.left = node.left;
                } else {
                    node.parent.right = node.left;
                }
            }
            node.left.parent = node.parent;
            node.left.right = node.right;
            if (node.right != null) {
                node.right.parent = node.left;
            }
            if (node.left.parent == null) {
                top = node.left;
            }
            return;
        }
        var newNode = node.left;
        while (newNode.right != null) {
            newNode = newNode.right;
        }
        if (newNode.left != null) {
            newNode.parent.right = newNode.left;
            newNode.left.parent = newNode.parent;
        } else {
            newNode.parent.right = null;
        }
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = newNode;
            } else {
                node.parent.right = newNode;
            }
        }
        newNode.parent = node.parent;
        newNode.left = node.left;
        node.left.parent = newNode;
        newNode.right = node.right;
        if (node.right != null) {
            node.right.parent = newNode;
        }
        if (newNode.parent == null) {
            top = newNode;
        }
    }

    /** Returns the first (lowest) element currently in this tree. */
    public E first() {
        var first = first(top);
        return (first == null) ? null : first.value;
    }

    /** Returns Node with the first (lowest) value currently in this tree. */
    private Node first(Node node) {
        if (node == null) {
            return null;
        }
        var current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /** Returns the last (highest) element currently in this tree. */
    public E last() {
        var last = last(top);
        return (last == null) ? null : last.value;
    }

    /** Returns Node with the last (highest) value currently in this tree. */
    private Node last(Node node) {
        if (node == null) {
            return null;
        }
        var current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /** Returns the greatest element in this tree strictly less than the given element, or null if there is no such element. */
    public E lower(E element) {
        var current = top;
        Node result = null;
        while (current != null) {
            if (comparator.compare(element, current.value) > 0) {
                if (result == null || comparator.compare(result.value, current.value) < 0 ) {
                    result = current;
                }
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return (result == null) ? null : result.value;
    }

    /** Returns the greatest element in this tree less than or equal to the given element, or null if there is no such element. */
    public E floor(E element) {
        if (contains(element)) {
            return element;
        }
        return lower(element);
    }

    /** Returns the least element in this tree greater than or equal to the given element, or null if there is no such element. */
    public E ceiling(E element) {
        if (contains(element)) {
            return element;
        }
        return higher(element);
    }

    /** Returns the least element in this tree strictly greater than the given element, or null if there is no such element. */
    public E higher(E element) {
        var current = top;
        Node result = null;
        while (current != null) {
            if (comparator.compare(element, current.value) < 0) {
                if (result == null || comparator.compare(result.value, current.value) > 0 ) {
                    result = current;
                }
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (result == null) {
            return null;
        }
        return result.value;
    }

    /** Returns an iterator over the elements in this tree in ascending order. */
    public Iterator<E> iterator() {
        return new BinaryTreeIterator(false);
    }

    /** Returns an iterator over the elements in this tree in descending order. */
    public Iterator<E> descendingIterator() {
        return new BinaryTreeIterator(true);
    }

    private class BinaryTreeIterator implements Iterator<E> {
        private Node current = null;
        private Node last = null;
        private boolean order;

        private BinaryTreeIterator(boolean order) {
            this.order = order;
            if (top != null) {
                last = top;
                while (getRight(last) != null) {
                    last = getRight(last);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return current != last;
        }

        private Node getLeft(Node node) {
            if (!order) {
                return node.left;
            } else {
                return node.right;
            }
        }

        private Node getRight(Node node) {
            if (!order) {
                return node.right;
            } else {
                return node.left;
            }
        }

        @Override
        public E next() {
            if (current == null) {
                current = top;
                while (getLeft(current) != null) {
                    current = getLeft(current);
                }
                return current.value;
            }
            if (getRight(current) == null) {
                while (getLeft(current.parent) != current) {
                    current = current.parent;
                }
                current = current.parent;
            } else {
                current = getRight(current);
                while (getLeft(current) != null) {
                    current = getLeft(current);
                }
            }
            return current.value;
        }
    }

    private class Node {
        private E value;
        private Node left;
        private Node right;
        private Node parent;

        private Node(E value, Node parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}
