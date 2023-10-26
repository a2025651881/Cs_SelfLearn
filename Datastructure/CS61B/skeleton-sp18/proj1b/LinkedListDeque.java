public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private listNode sentinel_firstNode;
    private listNode sentinel_laNode;

    public class listNode {
        public T item;
        public listNode next;
        public listNode pre;

        public listNode(listNode t, T i, listNode h) {
            pre = t;
            item = i;
            next = h;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel_firstNode = new listNode(sentinel_laNode, null, sentinel_laNode);
        sentinel_laNode = new listNode(sentinel_firstNode, null, sentinel_firstNode);
    }

    @Override
    public void addFirst(T item) {
        if (size == 0) {
            listNode newnode = new listNode(sentinel_firstNode, item, sentinel_laNode);
            sentinel_firstNode.next = newnode;
            sentinel_laNode.pre = newnode;
        } else {
            listNode newnode = new listNode(sentinel_firstNode, item, sentinel_firstNode.next);
            sentinel_firstNode.next.pre = newnode;
            sentinel_firstNode.next = newnode;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            listNode newnode = new listNode(sentinel_firstNode, item, sentinel_laNode);
            sentinel_firstNode.next = newnode;
            sentinel_laNode.pre = newnode;
        } else {
            listNode newnode = new listNode(sentinel_laNode.pre, item, sentinel_laNode);
            sentinel_laNode.pre.next = newnode;
            sentinel_laNode.pre = newnode;
        }
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (sentinel_firstNode.next != sentinel_laNode) {
            listNode p = sentinel_firstNode.next;
            while (p != sentinel_laNode) {
                System.out.println(p.item);
                p = p.next;
            }
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T valueT = sentinel_firstNode.next.item;
            sentinel_firstNode.next = sentinel_firstNode.next.next;
            sentinel_firstNode.next.pre = sentinel_firstNode;
            size -= 1;
            return valueT;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T valueT = sentinel_laNode.pre.item;
            sentinel_laNode.pre = sentinel_laNode.pre.pre;
            sentinel_laNode.pre.next = sentinel_laNode;
            size -= 1;
            return valueT;
        }
    }

    @Override
    public T get(int index) {
        if (index + 1 > size) {
            return null;
        } else {
            listNode p = sentinel_firstNode.next;
            while (index != 0) {
                p = p.next;
                index -= 1;
            }
            return p.item;
        }
    }

    private T getRecursiveValue(int index, listNode p) {
        if (index == 0)
            return p.item;
        else
            return getRecursiveValue(index - 1, p.next);
    }

    public T getRecursive(int index) {
        if (index + 1 > size) {
            return null;
        } else {
            return getRecursiveValue(index, sentinel_firstNode.next);
        }
    }
}
