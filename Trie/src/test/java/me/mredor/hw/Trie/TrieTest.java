package me.mredor.hw.Trie;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
    void serializeWithDeserialize() throws IOException {
        trie.add("MRedor");
        trie.add("Mary");
        trie.add("Pasha");

        var stream = new ByteArrayOutputStream();
        assertDoesNotThrow(() -> trie.serialize(stream));
        var newTrie = new Trie();
        newTrie.deserialize(new ByteArrayInputStream(stream.toByteArray()));
        assertEquals(3, newTrie.size());
        assertTrue(newTrie.contains("MRedor"));
        assertTrue(newTrie.contains("Mary"));
        assertTrue(newTrie.contains("Pasha"));
    }

    @Test
    void serializeOnly() throws IOException {
        trie.add("MR");
        trie.add("Ma");
        var stream = new ByteArrayOutputStream();
        assertDoesNotThrow(() -> trie.serialize(stream));
        var output = new ByteArrayOutputStream();
        var dataOutput = new DataOutputStream(output);
        dataOutput.writeInt(2);
        dataOutput.writeBoolean(false);
        dataOutput.writeInt(1);
        dataOutput.writeChar('M');
        dataOutput.writeInt(2);
        dataOutput.writeBoolean(false);
        dataOutput.writeInt(2);
        dataOutput.writeChar('a');
        dataOutput.writeInt(1);
        dataOutput.writeBoolean(true);
        dataOutput.writeInt(0);
        dataOutput.writeChar('R');
        dataOutput.writeInt(1);
        dataOutput.writeBoolean(true);
        dataOutput.writeInt(0);
        assertEquals(stream.toString(), output.toString());
    }

    @Test
    void deserializeOnly() throws IOException {
        trie.add("MR");
        trie.add("Ma");
        var output = new ByteArrayOutputStream();
        var dataOutput = new DataOutputStream(output);
        dataOutput.writeInt(2);
        dataOutput.writeBoolean(false);
        dataOutput.writeInt(1);
        dataOutput.writeChar('M');
        dataOutput.writeInt(2);
        dataOutput.writeBoolean(false);
        dataOutput.writeInt(2);
        dataOutput.writeChar('R');
        dataOutput.writeInt(1);
        dataOutput.writeBoolean(true);
        dataOutput.writeInt(0);
        dataOutput.writeChar('a');
        dataOutput.writeInt(1);
        dataOutput.writeBoolean(true);
        dataOutput.writeInt(0);
        var newTrie = new Trie();
        newTrie.deserialize(new ByteArrayInputStream(output.toByteArray()));
        assertTrue(newTrie.contains("MR"));
        assertTrue(newTrie.contains("Ma"));
        assertFalse(newTrie.contains("Pasha"));
    }

}