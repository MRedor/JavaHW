package MRedor.HashTable;

public class MyList {

    private int size;
    private Node begin, end;

    private static class Node {
        private String key, value;
        private Node next, prev;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     *    finds the element with key 'key'
     *
     *    @return the Node with the key 'key' or null if there no such Node in the list
     *
     *    */
    private Node searchByKey(String key) {
        if (begin == null) {
            return null;
        }
        Node cur = begin;
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    /**    @return size of the list    */
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
        newNode.prev = end;
        end = newNode;
        size++;
    }

    /**    adds new element with key 'key' and value 'value' to the list
     *       if there was element with the same key, replace the value in this elemet with 'value'
     * */
    public String put(String key, String value) {
        Node found = searchByKey(key);
        if (found == null) {
            add(new Node(key, value));
            return null;
        }
        String preVal = found.value;
        found.value = value;
        return preVal;
    }


    /**    makes the list empty    */
    public void clear() {
        size = 0;
        begin = null;
        end = null;
    }

    /**    deletes the Node 'node' from the list    */
    private void delete(Node node) {
        if (node.prev == null) {
            begin = node.next;
            if (begin == null) {
                return;
            }
            begin.prev = null;
            return;
        }
        if (node.next == null) {
            node.prev.next = null;
            end = node.prev;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }


    /**    deletes the element with the key 'key' (or does nothing if there wasn't such element)
     *
     *     @return the value of deleting element (or 'null')
     *
     * */
    public String remove(String key) {
        Node found = searchByKey(key);
        if (found == null) {
            return null;
        }
        String val = found.value;
        delete(found);
        return  val;
    }

    /**    @return 'true' if there is an element with the key 'key' and 'false' otherwise    */
    public boolean contains(String key) {
        if (searchByKey(key) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *    @param id index of the element in the list
     *    @return the key of the 'id'-th element in the list
     *
     *    */
    public String getKey(int id) {
        if ( (id >= size) || (id < 0) ) {
            return null;
        }
        Node cur = begin;
        for (int i = 0; i < id; i++) {
            cur = cur.next;
        }
        return cur.key;
    }


    /**    @return the value of element with key 'key'    */
    public String getValue(String key) {
        Node found = searchByKey(key);
        if (found == null) {
            return null;
        } else {
            return found.value;
        }
    }

}
