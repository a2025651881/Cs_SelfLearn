class leetcode_209 {
    public static int minSubArrayLen(int target, int[] nums) {
        // 使用两个数组如何暴力实现 -> 如何用双指针实现
        int size = nums.length, start = 0, end = 0, sum = nums[0], temp = size;

        if (nums[0] >= target) {
            return 1;
        } else if (size == 1) {
            return 0;
        }

        while (end < size - 1) {
            end++;
            sum += nums[end];
            if (nums[end] >= target)
                return 1;
            while (sum >= target) { // 当 sum > target 时，记录此刻 length 值, 缩减字符串,直到小于target
                temp = (end - start + 1) < temp ? (end - start + 1) : temp;
                sum -= nums[start];
                start++;
            }
        }
        if (sum < target && start == 0 && end == (size - 1))
            return 0;
        else
            return temp;
    }

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[] { 2, 3, 1, 2, 4, 3 }));

    }
}