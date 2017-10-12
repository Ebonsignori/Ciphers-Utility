package ciphersutility;

import java.util.Arrays;

public class Alphabet {
    char[] alphabet = new char[26]; // Regular (A-Z) alphabet
    char[] cipherbet = new char[26]; // Cipher (Shifted) alphabet
    
    public Alphabet() {
        // Initialize regular alphabet with its 26 default letters (A-Z)
        for (int i = 0, letter = 97; letter <= 122; letter++, i++) {
            alphabet[i] = (char)letter;
        }
    }
    
    /* Takes n in range (-25, 25) for right or left shift respectively */
    public void shift(int n) {
        if (n < 0) {
            n = this.alphabet.length + (n-1);
        }
        System.arraycopy(this.alphabet, n, this.cipherbet, 0, this.alphabet.length-n);
        System.arraycopy(this.alphabet, 0, this.cipherbet, this.alphabet.length-n, n);
        
    }
    
    /* Returns index of character 'c' in the regular (A-Z alphabet) */
    public int indexInAlphabet(char c) {
        for (int i = 0; i < this.alphabet.length; i ++) {
            if (c == (char)this.alphabet[i]) {
                return i;
            }
        }
        return -1;
    }
    
    /* Returns index of character 'c' in the cipher (shifted) alphabet */
    public int indexInCipherbet(char c) {
        for (int i = 0; i < this.cipherbet.length; i++) {
            if (c == (char)this.cipherbet[i]) {
                return (int)i;
            }
        }
        return -1;
    }
    
    /* Returns char at index 'x' in cipher (shifted) alphabet */
    public char charAtCipherbet(int x) {
        return (char)this.cipherbet[x];
    }
    
    /* Returns char at index 'x' in regular (A-Z) alphabet */
    public char charAtAlphabet(int x) {
        return (char)this.alphabet[x];
    }
    
    /* Returns Cipher (sifted) Alphabet */
    public char[] getChipherbet() {
        return this.cipherbet;
    }

    /* Returns Regular (A-Z) Alphabet */
    public char[] getAlphabet() {
        return this.alphabet;
    }
    
    public String stringOf(char[] alphabet) {
        return new String(alphabet);
    }
}
