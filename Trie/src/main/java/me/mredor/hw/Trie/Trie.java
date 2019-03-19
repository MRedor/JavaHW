package me.mredor.hw.Trie;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**    Serializable trie. Contains unique strings.    */
public class Trie implements Serializable {
    Node root = new Node();

    /**
     *    Adds new element to te trie.
     *    Works in O(|element|).
     *
     *    @return 'true' if there wasn't such string in trie and 'false' otherwise    */
    public boolean add(@NotNull String element) {
        var exists = contains(element);
        if (!exists) {
            root.add(element);
            return true;
        }
        return false;
    }

    /**
     *    Checks existing of element in trie.
     *    Works in O(|element|).
     *
     *    @return 'true' if there is string 'element' in trie and 'false' otherwise    */
    public boolean contains(@NotNull String element) {
        var found = root.getNodeByString(element);
        return (found != null) && found.isTerminal;
    }

    /**
     *    Removes string 'element' from trie.
     *    Works in O(|element|).
     *
     *    @return 'false' if there wasn't such string in the trie and 'true' otherwise
     * */
    public boolean remove(@NotNull String element) {
        var exists = contains(element);
        if (exists) {
            root.delete(element);
        }
        return exists;
    }

    /**
     *    Counts the number of elements in trie.
     *    Works in O(1).
     *
     *    @return size of the trie    */
    public int size() {
        return root.numberOfStrings;
    }

    /**    Counts how many elements in the trie starts with such prefix. Works in O(|prefix|).    */
    public int howManyStartWithPrefix(@NotNull String prefix) {
        var found = root.getNodeByString(prefix);
        return (found == null) ? 0 : found.numberOfStrings;
    }

    /**    Converts the trie into byte sequence.
     *     @throws IOException if couldn't write properly to the given output stream.  */
    public void serialize(OutputStream out) throws IOException {
        try (var dataStream = new DataOutputStream(out)) {
            root.serialize(dataStream);
        }
    }

    /**    Decodes new trie from byte sequenсe and replace current with new.
     *     @throws IOException if couldn't read properly to the given output stream. */
    public void deserialize(InputStream in) throws IOException {
        try (var dataStream = new DataInputStream(in)) {
            root.deserialize(dataStream);
        }
    }

    private class Node implements Serializable {
        private HashMap<Character, Node> next = new HashMap<>();
        private int numberOfStrings = 0;
        private boolean isTerminal = false;

        private void serialize(DataOutputStream stream) throws IOException {
            stream.writeInt(numberOfStrings);
            System.out.println(numberOfStrings);
            stream.writeBoolean(isTerminal);
            System.out.println(isTerminal);
            stream.writeInt(next.size());
            System.out.println(next.size());
            stream.flush();
            for (var current : next.entrySet()) {
                stream.writeChar(current.getKey());
                System.out.println(current.getKey());
                stream.flush();
                current.getValue().serialize(stream);
            }
            stream.flush();
        }

        private void deserialize(DataInputStream stream) throws IOException {
            numberOfStrings = stream.readInt();
            isTerminal = stream.readBoolean();
            int count = stream.readInt();
            for (int i = 0; i < count; i++) {
                Character letter = stream.readChar();
                Node node = new Node();
                node.deserialize(stream);
                next.put(letter, node);
            }
        }

        private Node getNodeByString(String string) {
            var current = root;
            for (int i = 0; i < string.length(); i++) {
                var letter = string.charAt(i);
                if (current.next.containsKey(letter)) {
                    current = current.next.get(letter);
                } else {
                    return null;
                }
            }
            return current;
        }

        private void delete(String string) {
            var current = root;
            current.numberOfStrings--;
            for (int i = 0; i < string.length(); i++) {
                var letter = string.charAt(i);
                current = current.next.get(letter);
                current.numberOfStrings--;
            }
            current.isTerminal = false;
        }

        private void add(String string) {
            var current = root;
            current.numberOfStrings++;
            for (int i = 0; i < string.length(); i++) {
                var letter = string.charAt(i);
                if (!current.next.containsKey(letter)) {
                    current.next.put(letter, new Node());
                }
                current = current.next.get(letter);
                current.numberOfStrings++;
            }
            current.isTerminal = true;
        }

    }
}
