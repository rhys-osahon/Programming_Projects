import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] backingArray;
    private int size;
    public int indFirst;
    public int nxtIndFirst;
    public int indLast;
    public int nxtIndLast;
    public ArrayDeque61B()
    {
        backingArray = (T[]) new Object[8];
        size = 0;
        indFirst = 0;
        nxtIndFirst = 0;
        indLast = 1;
        nxtIndLast = 1;
    }
    public class AD61BIterator<T> implements Iterator<T>
    {
        int wizPos;
        ArrayDeque61B<T> holder = new ArrayDeque61B<>();
        public AD61BIterator(ArrayDeque61B<T> holderHolder)
        {
            wizPos = 0;
            holder = holderHolder;
        }
        public boolean hasNext()
        {
            if (wizPos >= size)
            {
                return false;
            }
            return true;
        }
        public T next()
        {
            T result = holder.get(wizPos);
            wizPos += 1;
            return result;
        }
    }
    public int wrapInd(int ind)
    {
        if (ind < 0)
        {
            return backingArray.length - 1 - ((-ind - 1) % backingArray.length);
        }
        else if (ind >= backingArray.length)
        {
            return ind % backingArray.length;
        }
        else
        {
            return ind;
        }
    }
    public void resizeUp()
    {
        int geometricFactor = 2;
        if (size == backingArray.length)
        {
            T[] newArray = (T[]) new Object[backingArray.length * geometricFactor];
            for (int i = 0; i < size; i++)
            {
                newArray[i] = get(i);
            }
            backingArray = newArray;
            indFirst = 0;
            nxtIndFirst = wrapInd(indFirst - 1);
            indLast = size;
            nxtIndLast = wrapInd(indLast + 1);
        }
    }
    public void resizeDown()
    {
        double usageFactor = 0.25;
        if (backingArray.length < 16 && (usageFactor / 5) > (size * 1.0) / backingArray.length)
        {
            T[] newArray = (T[]) new Object[backingArray.length / 2];
            for (int i = 0; i < size; i++)
            {
                newArray[i] = get(i);
            }
            backingArray = newArray;
            indFirst = 0;
            nxtIndFirst = wrapInd(indFirst - 1);
            indLast = size;
            nxtIndLast = wrapInd(indLast + 1);
        }
        else if (backingArray.length >= 16 && usageFactor >= (size * 1.0) / backingArray.length)
        {
            T[] newArray = (T[]) new Object[backingArray.length / 2];
            for (int i = 0; i < size; i++)
            {
                newArray[i] = get(i);
            }
            backingArray = newArray;
            indFirst = 0;
            nxtIndFirst = wrapInd(indFirst - 1);
            indLast = size;
            nxtIndLast = wrapInd(indLast + 1);
        }
    }
    public int space()
    {
        return backingArray.length;
    }
    @Override
    public Iterator<T> iterator()
    {
        return new AD61BIterator<T>(this);
    }
    @Override
    public boolean equals(Object other)
    {
        boolean result = false;
        if (other instanceof ArrayDeque61B array) // can do if (other instanceof ArrayDeque61B) or if (other instanceof ArrayDeque61B name)
        {
            if (array.size == this.size)
            {
                for (int i = 0; i < size; i++)
                {
                    if (array.get(i) != this.get(i))
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        return result;
    }
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++)
        {
            result.append(get(i).toString());
            result.append(", ");
        }
        result.append(getLast().toString());
        result.append("]");
        return result.toString();
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */

    @Override
    public void addFirst(T x)
    {
        resizeUp();
        backingArray[nxtIndFirst] = x;
        indFirst = nxtIndFirst;
        nxtIndFirst = wrapInd(indFirst - 1);
        size++;
        if (size == 1)
        {
            indLast = indFirst;
        }
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x)
    {
        resizeUp();
        backingArray[nxtIndLast] = x;
        indLast = nxtIndLast;
        nxtIndLast = wrapInd(indLast + 1);
        size++;
        if (size == 1)
        {
            indFirst = indLast;
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList()
    {
        List<T> result = new ArrayList<>();
        for(int i = 0; i < size; i ++)
        {
            result.add(get(i));
        }
        return result;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst()
    {
        if (size == 0)
        {
            return null;
        };
        return backingArray[indFirst];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast()
    {
        if (size == 0)
        {
            return null;
        }
        return backingArray[indLast];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst()
    {
        resizeDown();
        if (size == 0)
        {
            return null;
        }
        T result = backingArray[indFirst];
        backingArray[indFirst] = null;
        size --;
        if (size > 0)
        {
            nxtIndFirst = indFirst;
            indFirst = wrapInd(indFirst + 1);
        }
        return result;
   }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast()
    {
        resizeDown();
        if (size == 0)
        {
            return null;
        }
        T result = backingArray[indLast];
        backingArray[indLast] = null;
        size --;
        if (size > 0)
        {
            nxtIndLast = indLast;
            indLast = wrapInd(indLast - 1);
        }
        return result;
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
    public T get(int index)
    {
        if (index > size || index < 0  || size == 0)
        {
            return null;
        }
        index = wrapInd(indFirst + index);
        return backingArray[index];
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    public T removeMiddle()
    {
        if (isEmpty())
        {
            return null;
        }
        int removeInd = 0;
        if (size % 2 == 1)
        {
            removeInd = size / 2;
        }
        else
        {
            removeInd = size / 2 - 1;
        }
        T taken = get(removeInd);
        ArrayDeque61B<T> replacement = new ArrayDeque61B<T>();
        for (int i = 0; i < size(); i ++)
        {
            if(!get(i).equals(taken))
            {
                replacement.addLast(get(i)); // did wrong and put addFirst
            }
        }
        backingArray = replacement.backingArray;
        indLast = replacement.indLast;
        nxtIndLast = replacement.nxtIndLast;
        indFirst = replacement.indFirst;
        nxtIndFirst = replacement.nxtIndFirst;

        return taken;
    }
}
