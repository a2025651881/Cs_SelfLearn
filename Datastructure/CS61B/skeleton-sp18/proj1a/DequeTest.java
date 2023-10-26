public class DequeTest {
    public static void main(String[] arg) {
        ArrayDeque<Integer> Deque = new ArrayDeque<Integer>();
        System.out.println(Deque.isEmpty());
        for (int i = 0; i < 100; i++) {
            Deque.addFirst(i);
        }
        System.out.println(Deque.size());
        for (int i = 0; i < 90; i++) {
            Deque.removeFirst();
        }
        Deque.printDeque();
        System.out.println(Deque.get(8));
    }
}
