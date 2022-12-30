import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String input1 = "A";
        assertTrue(palindrome.isPalindrome(input1));

        String input2 = "cat";
        assertFalse(palindrome.isPalindrome(input2));

        String input3 = "noon";
        assertTrue(palindrome.isPalindrome(input3));

        String input4 = "";
        assertTrue(palindrome.isPalindrome(input4));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        OffByOne offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertFalse(palindrome.isPalindrome("cat", offByOne));
    }


}
