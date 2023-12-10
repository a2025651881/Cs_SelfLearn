import java.util.ArrayDeque;
import java.util.Deque;

public class leetcode_239 {
    private static class MonotonousDeque {
        private Deque<Integer> mDeque;
        private int size;

        public MonotonousDeque() {
            mDeque = new ArrayDeque<Integer>();
            size = 0;
        }

        // 当移除
        public void pop(int x) {
            if (x == mDeque.peekFirst()) {
                size--;
                mDeque.getFirst();
            }
        }

        public void push(int x) {
            while (size != 0 && mDeque.peekLast() < x) {
                mDeque.getLast();
                size--;
            }
            size++;
            mDeque.offerLast(x);
        }

        public int front() {
            return mDeque.peekFirst();
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        MonotonousDeque deq = new MonotonousDeque();
        int[] ans = new int[nums.length - k + 1];
        for (int j = 0; j < k; j++) {
            deq.push(nums[j]);
        }
        for (int i = 0; i <= nums.length - k; i++) {
            ans[i] = deq.front();
            if (i == nums.length - k)
                continue;
            deq.pop(nums[i]);
            deq.push(nums[i + k]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int k = 3;
        int[] nums = { 1, 3, -1, -3, 5, 3, 6, 7 };
        System.out.println(maxSlidingWindow(nums, k));
    }
}
