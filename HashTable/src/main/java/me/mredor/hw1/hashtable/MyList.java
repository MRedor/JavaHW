package me.mredor.hw1.hashtable;

/**  Linked List containing pairs (String key, String value)  */
public class MyList {
    private int size;
    private Node begin;
    private Node end;

    /**
     *    Finds the element with key 'key'
     *
     *    @return the Node with the key 'key' or null if there no such Node in the list
     *    */
    private Node searchByKey(String key) {
        if (begin == null) {
            return null;
        }
        var current = begin;
        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     *    Gets size of the list
     *
     *    @return the number of elements in the list
     *    */
    public int size() {
        return size;
    }

    /**  adds new Node in the end of the list  */
    private void add(Node newNode) {
        if (begin == null) {
            begin = newNode;
            end = newNode;
            size = 1;
            return;
        }
        end.next = newNode;
        newNode.previous = end;
        end = newNode;
        size++;
    }

    /**    Adds new element with key 'key' and value 'value' to the list.
     *       If there was element with the same key, replace the value in this elemet with 'value'
     * */
    public String put(String key, String value) {
        var found = searchByKey(key);
        if (found == null) {
            add(new Node(key, value));
            return null;
        }
        String previousValue = found.value;
        found.value = value;
        return previousValue;
    }


    /**    Makes the list empty    */
    public void clear() {
        size = 0;
        begin = null;
        end = null;
    }

    /**    Deletes the Node 'node' from the list    */
    private void delete(Node node) {
        size--;
        if (node.previous == null) {
            begin = node.next;
            if (begin == null) {
                return;
            }
            begin.previous = null;
            return;
        }
        if (node.next == null) {
            node.previous.next = null;
            end = node.previous;
            return;
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    /**    Deletes the element with the key 'key' (or does nothing if there wasn't such element)
     *
     *     @return the value of deleting element (or 'null')
     * */
    public String remove(String key) {
        var found = searchByKey(key);
        if (found == null) {
            return null;
        }
        var value = found.value;
        delete(found);
        return  value;
    }

    /**
     *    Checks existing of element in the list
     *
     *    @return 'true' if there is an element with the key 'key' and 'false' otherwise
     *    */
    public boolean contains(String key) {
        return searchByKey(key) != null;
    }

    /**
     *    Gets element by index
     *
     *    @param index index of the element in the list
     *    @return the key of the 'index'-th element in the list
     *    */
    public String getKey(int index) {
        if ( (index >= size) || (index < 0) ) {
            return null;
        }
        var current = begin;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.key;
    }

    /**
     *    Gets value of element
     *
     *    @return the value of element with key 'key'    */
    public String getValue(String key) {
        var found = searchByKey(key);
        if (found == null) {
            return null;
        } else {
            return found.value;
        }
    }

    private static class Node {
        private String key;
        private String value;
        private Node next;
        private Node previous;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}

