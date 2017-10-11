package ciphersutility;

/* Base class for shift ciphers */
public class ShiftCiphers {
    protected Alphabet cipherbet = new Alphabet(); 
    protected String plaintext;
    protected String ciphertext;
    
    protected ShiftCiphers(String plaintext, String ciphertext) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
    }
    
    protected String getPlaintext() {
        return plaintext;
    }

    protected String getCiphertext() {
        return ciphertext;
    }
    
}

/* Single alphabetic shifit and substitution */
class ShiftOne extends ShiftCiphers {
    int key;

    public ShiftOne(String plaintext, String ciphertext, int key) {
        super(plaintext, ciphertext);
        this.key = key;
    }
    
    /* Single shift and substitution encryption using the key for the shift */
    public void encrypt() {
        this.cipherbet = new Alphabet(); 
        // Shift cipher alphabet
        this.cipherbet.shift(this.key);
        // Substitute plaintext into ciphertext using shifted alphabet
        for (int i = 0; i < this.plaintext.length(); i++) {
            int indexAlphabet = cipherbet.indexInAlphabet(this.plaintext.charAt(i));
            this.ciphertext += cipherbet.charAtCipherbet(indexAlphabet);
        }
    }
    
    /* Manual because it requires key to decrypt */
    public void decryptManual() {
        this.cipherbet = new Alphabet(); 
        // Shift cipher alphabet
        this.cipherbet.shift(key);
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.ciphertext.length(); i++) {
            int indexCipherbet = cipherbet.indexInCipherbet(this.ciphertext.charAt(i));
            this.plaintext += cipherbet.charAtAlphabet(indexCipherbet);
        }
    }
    
    /* Brute force automatic decryption using English word matching on plaintext */
//    public void decryptAutomatic() {
//        for (int n = 0; n < 26; n++) {
//            shiftDecrypt(n);
//            System.out.println(this.plaintext);
//        }
//    }
    
}

/* Double alphabetic shifit and substitution */
class ShiftTwo extends ShiftCiphers {
    int key1;
    int key2;
    String intermediateCipher = ""; // The partially encrypted ciphertext after first shift
    String intermediatePlain = ""; // The partially decrypted plaintext after first shift

    public ShiftTwo(String plaintext, String ciphertext, int key1, int key2) {
        super(plaintext, ciphertext);
        this.key1 = key1;
        this.key2 = key2;
    }
    
    /* Double shift substitution encryption using key1 and key2 */
    public void encrypt() {
        this.cipherbet = new Alphabet(); 
        // Shift cipher alphabet first time
        this.cipherbet.shift(this.key1);
        
        // Substitute plaintext into intermediate ciphertext
        for (int i = 0; i < this.plaintext.length(); i++) {
            int indexAlphabet = cipherbet.indexInAlphabet(this.plaintext.charAt(i));
            intermediateCipher += cipherbet.charAtCipherbet(indexAlphabet);
        }
        // Shift cipher alphabet second time
        this.cipherbet.shift(this.key2);
        
        // Substitute intermediate ciphertext into final ciphertext
        for (int i = 0; i < this.intermediateCipher.length(); i++) {
            int indexAlphabet = cipherbet.indexInAlphabet(this.intermediateCipher.charAt(i));
            this.ciphertext += cipherbet.charAtCipherbet(indexAlphabet);
        }
    }
    
    /* Manual because it requires key1 and key2 for decryption */
    public void decryptManual() {
        this.cipherbet = new Alphabet(); 
        // Shift cipher alphabet first time
        this.cipherbet.shift(this.key2);
        
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.ciphertext.length(); i++) {
            int indexCipherbet = cipherbet.indexInCipherbet(this.ciphertext.charAt(i));
            this.intermediatePlain += cipherbet.charAtAlphabet(indexCipherbet);
        }
        System.out.println(this.intermediatePlain);
        // Shift cipher alphabet second time
        this.cipherbet.shift(this.key1);
        
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.intermediatePlain.length(); i++) {
            int indexCipherbet = cipherbet.indexInCipherbet(this.intermediatePlain.charAt(i));
            this.plaintext += cipherbet.charAtAlphabet(indexCipherbet);
        }
    }      
    
    /* Brute force automatic decryption using English word matching on plaintext */

}