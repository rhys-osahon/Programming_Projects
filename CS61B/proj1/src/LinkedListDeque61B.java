import java.util.List;
import java.util.ArrayList; // imports the ArrayList class

public class LinkedListDeque61B <T> implements Deque61B<T>{
    private Node sentinel;
    private int size;
    public class Node
    {
        public Node prev;
        public T value;
        public Node next;
    }
    public LinkedListDeque61B()
    {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.value = null;
        sentinel.next = sentinel;
        size = 0;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node first = new Node();
        first.value = x;
        first.prev = sentinel;
        first.next = sentinel.next;
        sentinel.next.prev = first;
        sentinel.next = first;
        size ++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node last = new Node();
        last.value = x;
        last.next =  sentinel;
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        size ++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node atNode = sentinel.next;
        while(atNode.value != null)
        {
            returnList.add(atNode.value);
            atNode = atNode.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        if (sentinel.next.value == null)
        {
            return true;
        }
        return false;

        /* could also, now that there is a size parameter
            if (size == 0)
            {
                return true;
            }
            return false;
        */
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        return sentinel.next.value;
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        return sentinel.prev.value;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        Node holder = sentinel.next;
        sentinel.next = holder.next;
        holder.next.prev = sentinel.next; // maybe bug, I think should be just sentinel
        return holder.value;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        Node holder = sentinel.prev;
        sentinel.prev = holder.prev;
        holder.prev.next = sentinel;
        return holder.value;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size || size == 0) // can remove size == 0 and would still work but would be less efficient
        {
            return null;
        }
        Node atNode = sentinel.next;
        while (index > 0)
        {
            atNode = atNode.next;
            index --;
        }
        return atNode.value;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size || size == 0)
        {
            return null;
        }
        if (index == 0)
        {
            return sentinel.next.value;
        }
        LinkedListDeque61B<T> downTheList = new LinkedListDeque61B<>();
        downTheList.sentinel = this.sentinel.next;
        downTheList.size = this.size;
        IO.println(downTheList.toList());
        IO.println(index);
        return downTheList.getRecursive(index - 1);
    }

    public static void main(String[] args)
    {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
