package me.mredor.hw.my.treeset;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {
    private BinaryTree<Integer> tree;

    @BeforeEach
    void createTree() {
        tree = new BinaryTree<>(Integer::compareTo);
    }

    @Test
    void сontainsEmpty() {
        assertFalse(tree.contains(17));
    }

    @Test
    void contains() {
        tree.add(17);
        tree.add(19);
        assertFalse(tree.contains(42));
        assertTrue(tree.contains(17));
    }

    @Test
    void removeEmpty() {
        assertFalse(tree.remove(42));
    }

    @Test
    void remove() {
        tree.add(19);
        tree.add(17);
        tree.add(42);
        assertTrue(tree.remove(19));
        assertFalse(tree.remove(19));
    }
}