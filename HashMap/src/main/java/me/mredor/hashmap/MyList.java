package me.mredor.hashmap;

/** Linked List containing pairs */

public class MyList<K, V> {
    public Node begin;
    public Node end;
    private int size = 0;

    /**
     *    Gets size of the list
     *
     *    @return the number of elements in the list
     *    */
    public int size() {
        return size;
    }

    /**    Adds new element with key 'key' and value 'value' to the list. */
    public void put(K key, V value) {
        if (begin == null) {
            begin = new Node(new Pair<K, V>(key, value));
            end = begin;
        } else {
            end.next = new Node(new Pair<K, V>(key, value));
            end = end.next;
        }
        size++;
    }

    /** Finds the pair with the key 'key' */
    public Pair<K, V> get(K key) {
        var current = begin;
        while (current != null) {
            if (current.data.getKey().equals(key)) {
                return current.data;
            }
            current = current.next;
        }

        return null;
    }

    /**  Deletes the element with the key 'key' (or does nothing if there wasn't such element)  */
    public Pair<K, V> remove(K key) {
        var current = begin;
        Node found = null;
        var previous = begin;
        while (current != null) {
            if (current.data.getKey().equals(key)) {
                found = current;
                break;
            }
            previous = current;
            current = current.next;
        }
        if (found == null) {
            return null;
        }
        size--;
        if (found == begin) {
            begin = begin.next;
            return found.data;
        }
        previous.next = found.next;
        return found.data;
    }

    /**  Checks existing of element in the list  */
    public boolean contains(K key) {
        var current = begin;
        while (begin != null) {
            if (current.data.getKey().equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean containsValue(V value) {
        var current = begin;
        while (current != null) {
            if (current.data.getValue().equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /** Clears the list */
    public void clear() {
        begin = null;
        end = null;
        size = 0;
    }

    public class Node {
        private Node next;
        private Pair<K, V> data;

        private Node(Pair<K, V> data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public Pair<K, V> getData() {
            return data;
        }
    }
}
