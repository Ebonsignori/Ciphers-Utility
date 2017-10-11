package ciphersutility;

public class Ciphers {
    protected Alphabet alphabet = new Alphabet(); 
    protected Alphabet cipherbet = new Alphabet(); 
    protected String plaintext;
    protected String ciphertext;
    
    protected String getPlaintext() {
            return this.plaintext;
        }

    public String getCiphertext() {
        return this.ciphertext;
    }
    
    public void encrypt() {
            return;
        }

    public void decrypt() {
        return;
    }

}

class ShiftOne extends Ciphers {
    int key;

    public ShiftOne(String plaintext, String ciphertext, int key) {
        super.plaintext = plaintext;
        super.ciphertext = ciphertext;
        this.key = key;
    }
    public void encrypt() {
        super.cipherbet = new Alphabet(); 
        super.cipherbet.shift(this.key);
        for (int i = 0; i < super.plaintext.length(); i++) {
            plaintext[i] 
        }
        System.out.print("Key: " + Integer.toString(key) + "\n");
        System.out.println(alp.getChipherbet());
    }

    public void decrypt() {
        return;
    }

}


class ShiftTwo extends Ciphers {
    int key1;
    int key2;

    public ShiftTwo(String plaintext, String ciphertext, int key1, int key2) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.key1 = key1;
        this.key2 = key2;
    }

    public void encrypt() {
        return;
    }

    public void decrypt() {
        return;
    }      

}