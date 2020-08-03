import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
                                // NOTE: we seed to be able to repeat results

public class Enigma {
    /*
      An Enigma object represents the venerable, Enigma Machine, which
      was used by the Nazi's to encrypt communication messages during
      WWII.  Please see https://en.wikipedia.org/wiki/Enigma_machine.

      An Enigma machine consists of three part types: Rotor,
      Plugboard, and Reflector.  You will find for this assignnment
      the respective class for these three part types:

           Rotor.java, Plugboard.java, and Reflector.java

      Read the comments in each provided class and implement the
      required constructors and methods.

      In total, there are five class for this assignment, this class
      and the three mentioned above plus the class, TestEnigma.java,
      which contains the main method and will "put everything
      together" and test our Enigma machine.
      
      All the interfaces have been written for you, and you CANNOT
      change these.  In addition, any code already provided for you
      must be used UNCHANGED.  You may add other private methods as
      "helper" methods.  You CANNOT import any other classes.
      Violating any of the rules will incur a severe point penalty!

      Each class is worth 4 points, and the assignment is worth 20
      points total.

      I have provided some useful comments to help you implement your
      constructors and methods.  Please read the comments.
    */

    // decide on what instance variables you need for an Enigma Machine
    // (ie.  What "parts" make up an Enigma Machine?)

    /*
       The following method will check if a given character "maps" to
       itself in alphaperm
    
       NOTE: you may use this function before/when constructing the
       reflector, U (which cannot have a self mapping).
    */
    private boolean check_for_self_mapping(Character[] alphaperm)
    {
	for (int i = 0; i < alphaperm.length; i++) {
            if (alphaperm[i] == i + 'a') {
		return true;
	    }
	}
	return false;
    }

    /*
      To "construct" an Enigma machine, you will need to specify the
      plugboard settings (a String of even length with up to 10 unique
      pairs of "swap" characters).  For example, "qwerty". The rotor
      (aka scrambler) orientation settings is a String of length three
      (eg. "qcw" or "abc").  This String indicates the initial
      positions of the three respective rotors in our Enigma machine,
      which I will call R, M, and L (as was done in the "Mathematical
      analysis" section of the wikipedia article).

      You must decide what instance variables you need to represent
      the Enigma object, and declare these instance variables just
      before the contructor.  They should be private.

      See the comments in the constructor for guidance on implemention.
    */

    // instance variable for engima
    private Rotor R;
    private Rotor M;
    private Rotor L;
    private Reflector U;
    private Plugboard P;


    public Enigma(Character[] plugboard_settings, String rotor_settings,
                  int rseed) {

        Character[] l = new Character[26];  // "wrapper" class for char
	
	// you must build an array of characters, l, in order
	// (ie. ['a','b',...'z'])
        for (int i = 0; i < l.length; i++) {
            l[i] = (char) ('a' + i);
        }

	// NOTE: we are using the wrapper class, Character, for our
	// array of characters because we want to use the Collections
	// class to "shuffle" our array

	// "shuffle" will permute the list l
        Collections.shuffle(Arrays.asList(l), new Random(rseed));
	
	// you must construct rotor object, R, which permutes
	// (encodes) according to l
        R = new Rotor(l, rotor_settings.charAt(0));

	// do the same thing to construct other two scramblers, M and L

        M = new Rotor(l, rotor_settings.charAt(1));

        L = new Rotor(l, rotor_settings.charAt(2));

	// construct the reflector object


        U = new Reflector(l);

	// construct the plugboard object

        P = new Plugboard(plugboard_settings);
    }

    /*
      write a method, encode(), which will receive a plaintext
      character and encode it into a ciphertext character.  The term
      plaintext simply means the character or String we want to
      encrypt, and ciphtertext means the resulting encrypted character
      or String. The complete encryption formula for the Enigma
      machine is given in the wikipedia article:

          E = P R M L U L-1 M-1 R-1 P-1

      The above formula means, we first encode the character through
      the plugboard object, then through rotor R, then through rotor
      M, then through rotor L, then through the reflector object, U,
      and the back through the objects in reverse (a reverse operation
      is called decode).

      NOTE: you will also need to perform the "odometer"-like process
      of stepping (aka advancing) respective rotor positions.  Each
      character that is encoded/decoded will advance the R rotor by
      1/26th of a revolution.  Once this rotor reaches its "last"
      position, it cycles around to the first position and causes the
      M rotor to advance one position.  A similar process occurs
      between the M rotor and the L rotor.
    */
    public char encode(char ch) {

        char pass = ch;

        //go thru plugboard
        pass = P.encode(ch);


        //go thru rotor R
        pass = R.encode(pass);


        //go thru rotor M
        pass = M.encode(pass);


        //go thru rotor L
        pass = L.encode(pass);

        //go thru refloctor
        pass = U.encode(pass);

        //go reverse direction thru rotor R
        pass = L.decode(pass);

        //go reverse direction thru rotor M
        pass = M.decode(pass);

        //go reverse direction thru rotor L
        pass = R.decode(pass);

        //go thru plugboard
        pass = P.decode(pass);

        //advancing of rotors
        R.advance();
        if(R.advance())
            M.advance();
        if(M.advance())
            L.advance();

        char enc_ch = pass;


        return enc_ch;       // return encoded character
    }

    /*
      write the method, decode().  The Enigma is "self-reciprocal".
      This means that the decryption process is the same as the
      encryption process, only using the ciphertext character as
      input.  Also, note, that this prevents any letter from being
      encrypted to itself.  This method returns the plaintext character.
    */
    public char decode(char ch) {

        char pass = ch;

        //go thru plugboard
        pass = P.encode(ch);

        //go thru rotor R
        pass = R.encode(pass);

        //go thru rotor M
        pass = M.encode(pass);

        //go thru rotor L
        pass = L.encode(pass);

        //go thru refloctor
        pass = U.decode(pass);

        //go reverse direction thru rotor R
        pass = L.decode(pass);

        //go reverse direction thru rotor M
        pass = M.decode(pass);

        //go reverse direction thru rotor L
        pass = R.decode(pass);

        //go thru plugboard
        pass = P.decode(pass);

        // advances all the rotors when required
        R.advance();
        if(R.advance())
            M.advance();
        if(M.advance())
            L.advance();

        char dec_ch = pass; // will hold the result of the decoded character

        return dec_ch;      // return decode character
    }

    /*
      write the method, reset().  This method will "reset" the Enigma
      machine to a specified rotor orientation settings.
      Nothing is returned.
    */
    public void reset(String rotor_settings) {
        //simple reset method to put all the rotors to the given postion of the given argument

        R.reset(rotor_settings.charAt(0));
        M.reset(rotor_settings.charAt(1));
        L.reset(rotor_settings.charAt(2));
	
    }
}
