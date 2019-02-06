package me.MRedor.test1.SmartList;

import java.util.*;

/** Implementation of array list that optimally stores arrays of small size. */
public class SmartList<E> extends AbstractList<E> implements List<E> {

    private int size = 0;
    private Object data = null;


    /** empty constructor */
    public SmartList() {
    }

    /** Construct new list from collection  */
    public SmartList(Collection<? extends E> collection) {
        size = collection.size();
        if (size == 0) {
            data = null;
            return;
        }
        if (size == 1) {
            data = collection.iterator().next();
        } else {
            if (size > 5) {
                data = new ArrayList<>(collection);
            } else {
                data = new Object[5];
                int current = 0;
                for (E element : collection) {
                    ((E[]) data)[current] = element;
                    current++;
                }
            }
        }
    }


    /**  Gets element at the 'index'-th position.  */
    @Override
    public E get(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            return (E) data;
        }
        if (size <= 5) {
            return ((E[]) data)[index];
        }

        return ((ArrayList<E>) data).get(index);
    }


    /** Gets size of the List */
    @Override
    public int size() {
        return size;
    }

    /** Replaces elment on 'index'-th position with new one */
    @Override
    public E set(int index, E element) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            var was = (E) data;
            data = element;
            return was;
        }
        if (size <= 5) {
            var was = ((E[]) data)[index];
            ((E[]) data)[index] = element;
            return was;
        }
        return  ((ArrayList<E>) data).set(index, element);
    }

    /**  Inserts new element on the 'index'-th position  */
    @Override
    public void add(int index, E element) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        size++;
        if (size == 1) {
            data = element;
        }
        if (size == 2) {
            var was = (E) data;
            data = new Object[5];
            if (index == 1) {
                ((E[]) data)[0] = was;
                ((E[]) data)[1] = element;
            } else {
                ((E[]) data)[0] = element;
                ((E[]) data)[1] = was;
            }
        }
        if (size < 6) {
            size--;
            System.arraycopy(((E[]) data), index, ((E[]) data), index + 1, size - index);
            ((E[]) data)[index] = element;
            size++;
        }
        if (size == 6) {
            ArrayList<E> arrayList = new ArrayList<>(Arrays.asList( (E[]) data ));
            arrayList.add(index, element);
            data = arrayList;
        }
        if (size > 6) {
            ((ArrayList<E>) data).add(index, element);
        }
    }

    /** Removes element from the 'index'-th position  */
    public E remove(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            var was = (E) data;
            data = null;
            size = 0;
            return was;
        }
        if (size == 2) {
            var was = ((E[]) data)[index];
            if (index == 0) {
                data = ((E[]) data)[1];
            } else {
                data = ((E[]) data)[0];
            }
            size = 1;
            return was;
        }
        if (size <= 5) {
            var array = (E[]) data;
            var was = array[index];
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            size--;
            return was;
        }
        if (size == 6) {
            var was = ((ArrayList<E>) data).remove(index);
            data = ((ArrayList<E>) data).toArray();
            size = 5;
            return was;
        }

        size--;
        return ((ArrayList<E>) data).remove(index);
    }

}
