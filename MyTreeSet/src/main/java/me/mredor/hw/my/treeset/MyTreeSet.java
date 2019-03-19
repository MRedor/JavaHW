package me.mredor.hw.my.treeset;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;


/** Set using binary search tree. Contains elements of type E. */
public class MyTreeSet<E> extends AbstractSet<E> implements MyTreeSetInterface<E> {
    private BinaryTree<E> tree;
    private boolean isDescendingOrder = false;

    /** Constructs a new, empty tree set, sorted according to the natural ordering of its elements. */
    public MyTreeSet() {
        this((o1, o2) -> ((Comparable) o1).compareTo(o2));
    }

    /** Constructs a new, empty tree set, sorted according to the specified comparator. */
    public MyTreeSet(Comparator<? super E> comparator) {
        tree = new BinaryTree<>(comparator);
    }

    private MyTreeSet(MyTreeSet<E> treeSet, boolean isNewOrder) {
        this.tree = treeSet.tree;
        this.isDescendingOrder = treeSet.isDescendingOrder ^ isNewOrder;
    }

    /** Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element to this set if the set contains no element2 such that Objects.equals(element, element2).
     * If this set already contains the element, the call leaves the set unchanged and returns false. */
    @Override
    public boolean add(@NotNull E element) {
        return tree.add(element);
    }

    /** Returns true if this set contains the specified element.
     * More formally, returns true if and only if this set contains an element2 such that Objects.equals(element, element2). */
    @Override
    public boolean contains(@NotNull Object element) {
        return tree.contains(element);
    }

    /** Removes the specified element from this set if it is present.
     * More formally, removes an element2 such that Objects.equals(element, element2), if this set contains such an element.
     * Returns true if this set contained the element (or equivalently, if this set changed as a result of the call).
     * (This set will not contain the element once the call returns.)
     */
    @Override
    public boolean remove(@NotNull Object element) {
        return tree.remove(element);
    }

    /** Returns the number of elements in this set (its cardinality) */
    @Override
    public int size() {
        return tree.size();
    }

    /** Returns an iterator over the elements in this set in ascending order. */
    @Override
    public Iterator<E> iterator() {
        return (!isDescendingOrder) ? tree.iterator() : tree.descendingIterator();
    }

    /** Returns an iterator over the elements in this set in descending order. */
    @Override
    public Iterator<E> descendingIterator() {
        return (!isDescendingOrder) ? tree.descendingIterator() : tree.iterator();
    }

    /** Returns a reverse order view of the elements contained in this set. */
    @Override
    public MyTreeSetInterface<E> descendingSet() {
        return new MyTreeSet<>(this, true);
    }

    /** Returns the first (lowest) element currently in this set. */
    @Override
    public E first() {
        return (!isDescendingOrder) ? tree.first() : tree.last();
    }

    /** Returns the last (highest) element currently in this set. */
    @Override
    public E last() {
        return (!isDescendingOrder) ? tree.last() : tree.first();
    }

    /** Returns the greatest element in this set strictly less than the given element, or null if there is no such element. */
    @Override
    public E lower(@NotNull E element) {
        return (!isDescendingOrder) ? tree.lower(element) : tree.higher(element);
    }

    /** Returns the greatest element in this set less than or equal to the given element, or null if there is no such element. */
    @Override
    public E floor(@NotNull E element) {
        return (!isDescendingOrder) ? tree.floor(element) : tree.ceiling(element);
    }

    /** Returns the least element in this set greater than or equal to the given element, or null if there is no such element. */
    @Override
    public E ceiling(@NotNull E element) {
        return (isDescendingOrder) ? tree.floor(element) : tree.ceiling(element);
    }

    /** Returns the least element in this set strictly greater than the given element, or null if there is no such element. */
    @Override
    public E higher(@NotNull E element) {
        return (isDescendingOrder) ? tree.lower(element) : tree.higher(element);
    }
}
