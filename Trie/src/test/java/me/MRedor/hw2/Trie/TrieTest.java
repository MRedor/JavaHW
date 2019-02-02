package me.MRedor.hw2.Trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {
    private Trie trie;

    @BeforeEach
    void createTrie() {
        trie = new Trie();
    }

    @Test
    void containsEmpty() {
        assertFalse(trie.contains("MRedor"));
    }

    @Test
    void contains() {
        trie.add("MRedor");
        assertFalse(trie.contains("Mary"));
        assertTrue(trie.contains("MRedor"));
    }

    @Test
    void remove() {
        trie.add("MRedor");
        trie.add("Mary");
        trie.remove("Mary");
        assertFalse(trie.contains("Mary"));
        assertTrue(trie.contains("MRedor"));
    }

    @Test
    void sizeEmpty() {
        assertEquals(0, trie.size());
    }

    @Test
    void size() {
        trie.add("MRedor");
        assertEquals(1, trie.size());
        trie.add("Mary");
        assertEquals(2, trie.size());
    }

    @Test
    void howManyStartWithPrefix() {
        trie.add("MRedor");
        trie.add("Mary");
        assertEquals(2, trie.howManyStartWithPrefix("M"));
        assertEquals(1, trie.howManyStartWithPrefix("MR"));
        assertEquals(0, trie.howManyStartWithPrefix("Pasha"));
    }

    @Test
    void serialize() throws IOException {
        trie.add("MRedor");
        trie.add("Mary");
        trie.add("Pasha");

        var stream = new ByteArrayOutputStream();
        trie.serialize(stream);
        var newTrie = new Trie();
        newTrie.deserialize(new ByteArrayInputStream(stream.toByteArray()));
        assertEquals(3, newTrie.size());
        assertTrue(newTrie.contains("MRedor"));
        assertTrue(newTrie.contains("Mary"));
        assertTrue(newTrie.contains("Pasha"));
    }
}