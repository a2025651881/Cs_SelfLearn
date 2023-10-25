package Datastructure;

/**
 * KMP
 */
public class KMP {
    /*
     * getNext(): 获取 Sting 的 next 数组
     * 这里与《大话数据结构》不同的是，初始标志为 -1 并且 next 从 0 开始
     */
    private static int[] getNext(char[] String) {
        int[] next = new int[String.length];
        int k = -1;
        int i = 0;
        next[0] = -1;
        while (i != String.length - 1) {
            /*
             * k = 当前重复 最小子串 +1 也就是不同处
             * i = 计算 next 的位置
             */
            if (k == -1 || String[k] == String[i]) {
                ++k;
                ++i;
                next[i] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /*
     * KMP_Sreach(): 寻找最小子串的下标
     * 临界条件：两数相等时 i 和 j 都向后移
     * j == 0 时， j不动，i后移
     * 返回值为 开始的下标 或者 -1
     */
    private static int KMP_Sreach(char[] String, char[] findString) {
        int[] next = getNext(findString);
        int i = 0, j = 0;
        while (i < String.length && j < findString.length) {
            if (String[i] == findString[j]) {
                i++;
                j++;
            } else {
                if (j != 0)
                    j = next[j];
                else
                    i++;
            }
        }
        return j == findString.length ? i - findString.length : -1;
    }

    public static void main(String[] arg) {
        char[] String = new char[] { 'c', 'b', 'b', 'c', 'a', 'a' };
        char[] find = new char[] { 'c', 'a', 'a' };
        for (int i : getNext(find)) {
            System.out.println(i);
        }
        System.out.println(KMP_Sreach(String, find));
    }

}