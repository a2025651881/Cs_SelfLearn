public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> arrayDeque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            arrayDeque.addLast(ch);
            System.out.println(ch);
        }
        return arrayDeque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1)
            return true;
        else {
            Deque d = new ArrayDeque<Character>();
            d = wordToDeque(word);
            for (int i = 0; i < word.length(); i++) {
                if (d.get(i) != d.get(word.length() - i - 1)) {
                    return false;
                }
                System.out.println(d.get(i));
            }
            return true;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1)
            return true;
        else {
            Deque<Character> d = new ArrayDeque<Character>();
            d = wordToDeque(word);
            for (int i = 0; i < word.length(); i++) {
                if (!cc.equalChars(d.get(i), d.get(word.length() - i - 1))) {
                    return false;
                }
                System.out.println(d.get(i));
            }
            return true;
        }
    }
}
