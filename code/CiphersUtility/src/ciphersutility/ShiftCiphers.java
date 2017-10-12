package ciphersutility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/* Base class for shift ciphers */
public class ShiftCiphers {
    protected Alphabet cipherbet = new Alphabet();
    protected static String[] commonEnglishDict = null;
    protected List<Occurance> plaintextPossibilities = new ArrayList<Occurance>();
    protected String plaintext;
    protected String ciphertext;
    
    protected ShiftCiphers(String plaintext, String ciphertext) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.initDict(); // Initialize frequent words dictionary
    }
    
    protected String getPlaintext() {
        return plaintext.toUpperCase();
    }

    protected String getCiphertext() {
        return ciphertext.toUpperCase();
    }

    /* Initialize dictionary of 10000 most common English words */
    private void initDict() {
        // Words are line by line in file "google-10000-english.java"
        File file = new File("./common_words/google-10000-english.java");
        this.commonEnglishDict = new String[10000];
        try {
            Scanner in = new Scanner(file);
            int i = 0;
            while (in.hasNextLine()) {
                  String line = in.nextLine();
                  commonEnglishDict[i] = (String)line;
                  i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("Error: English dictionary not found.");
        }
    }
    
    /* Returns true if passed word is in 10000 most frequent word dictionary */
    protected boolean isFrequent(String word) {
        for (int i = 0; i < commonEnglishDict.length-1; i++) {
            if (commonEnglishDict[i].equals(word)) {
                return true;
            }
        }
        return false;
    }
    
    /* Generate each subset of string and count occurrences of subsets in common English word dictionary */
    protected int countMatches(String word) {
        int occurances = 0;
        String subWord;
        Set<String> substrings = new HashSet<String>();
        // Add substrings to the hash set, removing duplicates
        for (int i = 0; i < word.length(); ++i) {
          for (int j = 0; j < (word.length() - i); ++j) {
            subWord = word.substring(j, i + j + 1);
            if (subWord.length() >= 3) {
                substrings.add(subWord);
            }
          }
        }
        for (String substring : substrings) {
            if (isFrequent(substring)) {
                occurances++;
            }
        }
        return occurances;
    }
    
    /* Prints 'x' number of possible decryptions of ciphertext */
    protected void printXPossibleMatches(int x) {
        System.out.println("--- Possible plaintext(s): --- ");
        // Remove duplicates from possibilities 
        Set<Occurance> possibilitesSet = new HashSet<>();
        for (int i = 0; i < plaintextPossibilities.size(); i++) {
            possibilitesSet.add(plaintextPossibilities.get(i));
        } 
        // Add duplicates removed to new list and sort it
        List<Occurance> possibilitesList = new ArrayList<>();
        possibilitesList.addAll(possibilitesSet);
        Collections.sort(possibilitesList);
        
        // Print x number of possible plaintexts
        for (int i = 0; i < possibilitesList.size(); i++) {
           if (i < 5) {
               Occurance occ = possibilitesList.get(i);
               System.out.println("#" + Integer.toString(i+1) + ": " +
                                  occ.toString() + ", key = " + occ.getKeyStr());
           }
        } 
        System.out.println("-------------------------------");
    }  
}

/* 
* Class for representing a plaintext, the key used to decrypt it and the number
* of times it occurred in the common English word dictionary
*/ 
class Occurance implements Comparable<Occurance> {
    String plaintext;
    int occurance;
    int key;
    
    public Occurance(int key, String plaintext, int occurance) {
        this.plaintext = plaintext;
        this.occurance = occurance;
        this.key = key;
    }
    // For the sort method used to rank likely plaintexts on occurrences
    @Override
    public int compareTo(Occurance o) {
        return occurance > o.occurance ? -1 : occurance < o.occurance ? 1 : 0;
    }
    
    @Override
    public String toString() {
        return this.plaintext.toUpperCase();
    }
  
    public String getKeyStr() {
        return Integer.toString(this.key);
    }
}



/* Single alphabetic shift and substitution */
class ShiftOne extends ShiftCiphers {
    int key;

    // Constructor with key, for manual decryption and encryption
    public ShiftOne(String plaintext, String ciphertext, int key) {
        super(plaintext, ciphertext);
        this.key = key;
    }
    
    // Constructor without key, for automatic decryption
    public ShiftOne(String plaintext, String ciphertext) {
        super(plaintext, ciphertext);
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
        this.cipherbet.shift(this.key);
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.ciphertext.length(); i++) {
            int indexCipherbet = cipherbet.indexInCipherbet(this.ciphertext.charAt(i));
            this.plaintext += cipherbet.charAtAlphabet(indexCipherbet);
        }
    }
    
     /* Decrypt with given key and return decrypted text */
    public String decryptHelper(int key) {
        Alphabet cipherbetTmp = new Alphabet(); 
        String plaintextTmp = "";
        // Shift cipher alphabet
        cipherbetTmp.shift(key);
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.ciphertext.length(); i++) {
            int indexCipherbet = cipherbetTmp.indexInCipherbet(this.ciphertext.charAt(i));
            plaintextTmp += cipherbetTmp.charAtAlphabet(indexCipherbet);
        }
        return plaintextTmp;
    }
       
    /* Brute force automatic decryption using English word matching on plaintext */
    public void decryptAutomatic(int x) {
        // x is the number of possibilities to be printed. -1 means print all
        if (x == -1) { System.out.println("--- All 26 Decrypted Possibilities: ---"); }
        for (int n = 0; n < 26; n++) {
            String decrypted = this.decryptHelper(n);
            if (x == -1) { 
                    System.out.println("key: " + Integer.toString(n) + ": \n" + 
                                       decrypted.toUpperCase()); 
                }
            int occurances = countMatches(decrypted);
            if (occurances > -1) { 
                this.plaintextPossibilities.add(new Occurance(n, decrypted, occurances)); 
            }
        }
        if (x > -1) { this.printXPossibleMatches(x); } 
        else { System.out.print("--------------------------------------"); }
    }
    
}

/* Double alphabetic shift and substitution */
class ShiftTwo extends ShiftCiphers {
    int key1;
    int key2;
    String intermediateCipher = ""; // The partially encrypted ciphertext after first shift
    String intermediatePlain = ""; // The partially decrypted plaintext after first shift

    // Constructor with keys, for manual decryption and encryption
    public ShiftTwo(String plaintext, String ciphertext, int key1, int key2) {
        super(plaintext, ciphertext);
        this.key1 = key1;
        this.key2 = key2;
    }
   
    // Constructor without keys, for automatic decryption
    public ShiftTwo(String plaintext, String ciphertext) {
        super(plaintext, ciphertext);
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
        // Shift cipher alphabet second time
        this.cipherbet.shift(this.key1);
        
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.intermediatePlain.length(); i++) {
            int indexCipherbet = cipherbet.indexInCipherbet(this.intermediatePlain.charAt(i));
            this.plaintext += cipherbet.charAtAlphabet(indexCipherbet);
        }
    } 

    /* Decrypt with given keys and return decrypted text */
    public String decryptHelper(int key1, int key2) {
        Alphabet cipherbetTmp = new Alphabet(); 
        String intermediatePlainTmp = "";
        String plaintextTmp = "";

        // Shift cipher alphabet first time
        cipherbetTmp.shift(key2);
        
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < this.ciphertext.length(); i++) {
            int indexCipherbet = cipherbetTmp.indexInCipherbet(this.ciphertext.charAt(i));
            intermediatePlainTmp += cipherbetTmp.charAtAlphabet(indexCipherbet);
        }
        // Shift cipher alphabet second time
        cipherbetTmp.shift(key1);
        
        // Substitute ciphertext into plaintext using shifted alphabet
        for (int i = 0; i < intermediatePlainTmp.length(); i++) {
            int indexCipherbet = cipherbetTmp.indexInCipherbet(intermediatePlainTmp.charAt(i));
            plaintextTmp += cipherbetTmp.charAtAlphabet(indexCipherbet);
        }
        return plaintextTmp;
    }
    
    /* Brute force automatic decryption using English word matching on plaintext */
    public void decryptAutomatic(int x) {
        // X is the number of possibilities to be printed. -1 means print all
        if (x == -1) { System.out.println("--- All 676 Decrypted Possibilities: ---"); }
        for (int n1 = 0; n1 < 26; n1++) {
            for (int n2 = 0; n2 < 26; n2++) {
                String decrypted = this.decryptHelper(n1, n2);
                if (x == -1) { 
                    System.out.println("key1: " + Integer.toString(n1) + ": " + 
                                       " key2: " + Integer.toString(n2) + ":\n" + 
                                       decrypted.toUpperCase()); 
                }
                int occurances = countMatches(decrypted);
                if (occurances > -1) { 
                    this.plaintextPossibilities.add(new Occurance(n1 + n2, decrypted, occurances)); 
                }
            }
        }
        if (x > -1) { this.printXPossibleMatches(x); } 
         else { System.out.print("--------------------------------------"); }
    }
}