package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    public static  void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<Integer>(10);
        System.out.println(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        System.out.println(arb.dequeue());
        System.out.println(arb.dequeue());
        System.out.println(arb.dequeue());
        System.out.println(arb.fillCount());

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        someTest();
    }
} 
