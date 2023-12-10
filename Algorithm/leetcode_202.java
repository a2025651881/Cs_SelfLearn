import java.util.HashSet;

public class leetcode_202 {
    private static int happyNum(int n) {
        int happy = 0, high, low, div = 10;
        while (true) {
            low = n % div / (div / 10);
            high = n / div;
            happy += Math.pow(low, 2);
            if (high == 0)
                break;
            div *= 10;
        }
        return happy;
    }

    public static boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet();
        int temp = n, flage = 0;
        while (temp != 1) {
            if (set.contains(temp))
                flage++;
            if (flage == 2)
                return false;
            set.add(temp);
            temp = happyNum(temp);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isHappy(1403479280));

    }
}
