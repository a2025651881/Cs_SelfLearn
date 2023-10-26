
public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    public T[] arrayT = (T[]) new Object[8];

    public ArrayDeque() {
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    private void resize(int sz, int flage) {
        T[] newarrayT = (T[]) new Object[sz];
        if (nextFirst == arrayT.length - 1)
            System.arraycopy(arrayT, 0, newarrayT, 3, size);
        else if (flage == 2) {
            System.arraycopy(arrayT, nextFirst + 1, newarrayT, 3, size - nextFirst);
            System.arraycopy(arrayT, 0, newarrayT, size - nextFirst + 3, nextFirst + 1);
        } else if (flage == 3) {
            if (nextFirst < nextLast) {
                System.arraycopy(arrayT, nextFirst + 1, newarrayT, 3, nextLast - nextFirst - 1);
            } else {
                System.arraycopy(arrayT, nextFirst + 1, newarrayT, 3, arrayT.length - nextFirst - 1);
                System.arraycopy(arrayT, 0, newarrayT, arrayT.length - nextFirst + 1, nextLast);
            }
        } else {
            System.arraycopy(arrayT, nextFirst, newarrayT, 3, size - nextFirst + 1);
            System.arraycopy(arrayT, 0, newarrayT, size - nextFirst + 4, nextFirst);
        }
        nextFirst = 2;
        nextLast = size + 4;
        arrayT = newarrayT;
    }

    public void addFirst(T item) {
        arrayT[nextFirst] = item;
        if (nextFirst == nextLast) {
            resize(size * 2, 1);
        } else {
            if (nextFirst == 0) {
                nextFirst = arrayT.length - 1;
            } else
                nextFirst -= 1;
        }
        size += 1;
    }

    public void addLast(T item) {
        arrayT[nextLast] = item;
        if (nextFirst == nextLast) {
            resize(size * 2, 2);
        } else {
            if (nextLast == arrayT.length - 1) {
                nextLast = 0;
            } else
                nextLast += 1;
        }
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
    }

    public int size() {
        return arrayT.length;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.println("The array deque is empty!!");
        } else {
            int temp;
            if (nextFirst != arrayT.length - 1)
                temp = nextFirst + 1;
            else
                temp = 0;
            for (int i = 0; i < size; i++) {
                System.out.println(arrayT[temp]);
                if (temp == arrayT.length - 1) {
                    temp = 0;
                } else {
                    temp += 1;
                }
            }
        }
    }

    private void proportionalMemory(int flage) {
        if (arrayT.length * 0.25 > size) {
            resize(((int) (size / 8) + 2) * 8, flage);
        }
    }

    public T removeFirst() {
        proportionalMemory(3);
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            if (nextFirst == arrayT.length - 1) {
                nextFirst = 0;
            } else {
                nextFirst += 1;
            }
            return arrayT[nextFirst];
        }
    }

    public T removeLast() {
        proportionalMemory(3);
        if (size == 0) {
            return null;
        } else {
            size -= 1;
            if (nextLast == 0) {
                nextLast = arrayT.length - 1;
            } else {
                nextLast -= 1;
            }
            return arrayT[nextLast];
        }
    }

    public T get(int index) {
        if (index + 1 > size) {
            return null;
        } else {
            int temp = index + nextFirst + 1;
            if (temp > arrayT.length - 1) {
                return arrayT[temp - arrayT.length];
            } else {
                return arrayT[temp];
            }
        }
    }
}
