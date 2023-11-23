import java.util.ArrayList;
import java.util.List;

public class leetcode1002 {
    public static List<String> commonChars(String[] words) {
        int[] hashTable = new int[26];
        List<String> result = new ArrayList<String>();
        for (String i : words) {
            for (int j = 0; j < i.length(); j++) {
                int index = (int) i.charAt(j) - 97;
                hashTable[index]++;
            }
        }
        int n = 0;
        for (int i = 0; i < 26; i++) {
            if (hashTable[i] == words.length) {
                String ch = Character.toString((97 + i));
                result.add(n, ch);
                n++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] words = new String[] { "bella", "label", "roller" };
        // list.next.next = new ListNode(3, null);
        // list.next.next.next = new ListNode(4, null);
        // list.next.next.next.next = new ListNode(5, null);
        System.out.println(commonChars(words));

    }
}
