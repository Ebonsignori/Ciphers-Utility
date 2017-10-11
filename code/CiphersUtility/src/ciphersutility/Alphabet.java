/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphersutility;

public class Alphabet {
    char[] alphabet = new char[26];
    char[] cipherbet = new char[26];
    
    public Alphabet() {
        // Populate alphabet with its 26 letters
        for (int i = 0, letter = 97; letter < 122; letter++, i++) {
            alphabet[i] = (char)letter;
        }
    }
    
    /* Takes positive or negative n for right or left shift respectively */
    public void shift(int n) {
        if (n < 0) {
            n = this.alphabet.length + (n-1);
        }
        System.arraycopy(this.alphabet, n, this.cipherbet, 0, this.alphabet.length-n);
        System.arraycopy(this.alphabet, 0, this.cipherbet, this.alphabet.length-n, n);
    }
    
    public int indexInAlphabet(char c) {
        for (int i = 0; i < this.alphabet.length; i ++) {
            if (c == (char)this.alphabet[i]) {
                return i;
            }
        }
        return -1;
    }
    
    /* Returns index of the character in the Cigpher Alphabet */
    public int indexInCipherbet(char c) {
        for (int i = 0; i < this.cipherbet.length; i ++) {
            if (c == (char)this.cipherbet[i]) {
                return (int)i;
            }
        }
        return -1;
    }
    
    /* Returns index of the character in the Cipher Alphabet */
    public char charAtCipherbet(int x) {
        return (char)this.cipherbet[x];
    }
    
    /* Returns index of the character in the Regular Alphabet */
    public char charAtAlphabet(int x) {
        return (char)this.alphabet[x];
    }
        
    public char[] getChipherbet() {
        return this.cipherbet;
    }
    
    public char[] getAlphabet() {
        return this.alphabet;
    }
    
    public String stringOf(char[] alphabet) {
        return new String(alphabet);
    }
}
