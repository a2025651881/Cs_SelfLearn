package synthesizer;
public interface BoundedQueue<T> extends Iterable<T>{
    int capacity();
    int fillCount();
    void enqueue(T x);
    T dequeue();
    T peek();
    default boolean isEmpty(){
        return fillCount() == 0;
    }       // is the buffer empty (fillCount equals zero)?
    default boolean isFull(){
        return fillCount() == capacity();
    }        // is the buffer full (fillCount is same as capacity)?

}
