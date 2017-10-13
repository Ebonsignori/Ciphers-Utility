# Shift Ciphers Utility

An application that can encrypt plaintext or decrypt ciphertext using shift ciphers.

![Alt text](./documentation/GUI_v1.0_decrypt.PNG?raw=true "Decryption Example")   ![Alt text](./documentation/GUI_v1.0_encrypt.PNG?raw=true "Encryption Example")
	
Inputs for the program change based on the ciphers used, but each cipher supports the option for both manual or automatic decryption.
Automatic decryption requires English messages, as decrypted messages are pattern matched against [10000 common English words](https://github.com/first20hours/google-10000-english). 

## Ciphers Supported

* Shift (Caesar) Cipher
* Double Shift Cipher

## Shift Cipher

Takes a key 'n' and shifts the alphabet by n, then performs a simple substitution using the shifted alphabet.

* Key: n 
* Key Range: Integers in range -25 to 25
* Combinations: 26
* Security: Not secure.

**Manual Decryption:** Takes key and ciphertext from user and performs an alphabet shift by n. Ciphertext is then substituted and returned to user.

**Automatic Decryption:** Keys are iterated through using a brute force approach until English word matches are found. 'X' number of specified possibilities are returned for each matching letter in the decrypted plaintext. There is also a check-box option to return each possible plaintext for a total of 26 possibilities.

## Double Shift Cipher

![Alt text](./documentation/double_shift_cipher_diagram.PNG?raw=true "Optional Title")

Takes two keys 'n1' and 'n2' and shifts the alphabet by n1, then performs a simple substitution using the first shifted alphabet. Next the the shifted alphabet is shifted again, but by n2, then a simple substitution is performed using the second shifted alphabet.

* Key(s): n1, n2
* Key Range: Integers in range -25 to 25
* Combinations: 26 * 26 = 676
* Security: Not very secure.

**Manual Decryption:** Takes keys and ciphertext from user and performs alphabet shift by n1 and then n2. Ciphertext is substituted once for each alphabet and returned to user.

**Automatic Decryption:** Both Keys are iterated through using a brute force approach until English word matches are found in the second substitution. Possibilities are returned for each matching letter in the decrypted plaintext. There is also a check-box option to return each possible plaintext for a total of 676 possibilities.