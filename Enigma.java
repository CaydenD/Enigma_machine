import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
                                // NOTE: we seed to be able to repeat results

public class Enigma {
	
    private boolean check_for_self_mapping(Character[] alphaperm)
    {
	for (int i = 0; i < alphaperm.length; i++) {
            if (alphaperm[i] == i + 'a') {
		return true;
	    }
	}
	return false;
    }

    // instance variable for engima
    private Rotor R;
    private Rotor M;
    private Rotor L;
    private Reflector U;
    private Plugboard P;


    public Enigma(Character[] plugboard_settings, String rotor_settings,
                  int rseed) {

        Character[] l = new Character[26];  // "wrapper" class for char
	
        for (int i = 0; i < l.length; i++) {
            l[i] = (char) ('a' + i);
        }


	// "shuffle" will permute the list l
        Collections.shuffle(Arrays.asList(l), new Random(rseed));
	

        R = new Rotor(l, rotor_settings.charAt(0));

        M = new Rotor(l, rotor_settings.charAt(1));

        L = new Rotor(l, rotor_settings.charAt(2));

	// construct the reflector object

        U = new Reflector(l);

	// construct the plugboard object

        P = new Plugboard(plugboard_settings);
    }
	
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

    public void reset(String rotor_settings) {
        //simple reset method to put all the rotors to the given postion of the given argument

        R.reset(rotor_settings.charAt(0));
        M.reset(rotor_settings.charAt(1));
        L.reset(rotor_settings.charAt(2));
	
    }
}
