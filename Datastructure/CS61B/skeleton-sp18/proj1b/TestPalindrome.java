import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public static void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        d.printDeque();
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        CharacterComparator cc = new OffByOne();
        System.out.println(palindrome.isPalindrome("abcb", cc));
    }
    // ncomment this

    public static void main(String[] arg) {
        testWordToDeque();
    }
    // class once you've created your Palindrome class.
}
