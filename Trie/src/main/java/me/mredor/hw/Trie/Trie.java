package me.mredor.hw.Trie;

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
    public boolean add (String element) {
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
    public boolean contains (String element) {
        var found = root.getNodeByString(element);
        return (found != null) && found.terminal;
    }

    /**
     *    Removes string 'element' from trie.
     *    Works in O(|element|).
     *
     *    @return 'false' if there wasn't such string in the trie and 'true' otherwise
     * */
    public boolean remove(String element) {
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
    public int howManyStartWithPrefix(String prefix) {
        var found = root.getNodeByString(prefix);
        return (found == null) ? 0 : found.numberOfStrings;
    }

    /**    Converts the trie into byte sequence.
     *     @throws IOException if couldn't write properly to the given output stream.  */
    public void serialize(OutputStream out) throws IOException {
        root.serialize(out);
    }

    /**    Decodes new trie from byte sequenсe and replace current with new.
     *     @throws IOException if couldn't read properly to the given output stream. */
    public void deserialize(InputStream in) throws IOException {
        root.deserialize(in);
    }

    private class Node implements Serializable {
        private HashMap<Character, Node> next = new HashMap<>();;
        private int numberOfStrings = 0;
        private boolean terminal = false;

        private void serialize(OutputStream out) throws IOException {
            ObjectOutputStream stream = new ObjectOutputStream(out);
            stream.writeInt(numberOfStrings);
            stream.writeBoolean(terminal);
            stream.writeInt(next.size());
            stream.flush();
            for (Map.Entry<Character, Node> current : next.entrySet()) {
                stream.writeChar(current.getKey());
                stream.flush();
                current.getValue().serialize(out);
            }
            stream.flush();
        }

        private void deserialize(InputStream in) throws IOException {
            var stream = new ObjectInputStream(in);
            numberOfStrings = stream.readInt();
            terminal = stream.readBoolean();
            int count = stream.readInt();
            for (int i = 0; i < count; i++) {
                Character letter = stream.readChar();
                Node node = new Node();
                node.deserialize(in);
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
            current.terminal = false;
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
            current.terminal = true;
        }

    }
}