import java.util.Collections;   // for shuffle
import java.util.Arrays;        // also for shuffle (need asList)
import java.util.Random;        // for seeding the random number for shuffle

public class TestEnigma {
 
    private static int check_for_self_mapping(Character[] alphaperm)
    {
	for (int i = 0; i < alphaperm.length; i++) {
            if (alphaperm[i] == i + 'a') {
		return i;
	    }
	}
	return -1;
    }
	
    public static void test_rotor(char startpos, char ch, int rseed)
    {
		System.out.println("Rotor Test");
		System.out.println();

	Character[] l = new Character[26];  // "wrapper" class for char
	for (int i = 0; i < l.length; i++) {
	    l[i] = (char) ('a' + i);
	}

	Collections.shuffle(Arrays.asList(l), new Random(rseed));
	Rotor R = new Rotor(l, startpos);

	R.count++;
	R.advance();

	char enc_ch = R.encode(ch);
	char dec_ch = R.decode(enc_ch);


	System.out.println("rotor: " + ch + " " + enc_ch + " " + dec_ch);
	
	R.advance();

	enc_ch = R.encode(ch);
	dec_ch = R.decode(enc_ch);

	System.out.println("rotor: " + ch + " " + enc_ch + " " + dec_ch);
    }

    public static void test_reflector(char ch, int rseed)
    {
		System.out.println("Reflector Test");
		System.out.println();

	Character[] l = new Character[26];  // "wrapper" class for char
	for (int i = 0; i < l.length; i++) {
	    l[i] = (char) ('a' + i);
	}

	do {
	    // can't use the same seed, else possible infinite loop
	    //       so increment rseed each time through the loop
	    Collections.shuffle(Arrays.asList(l), new Random(rseed++));
	} while (check_for_self_mapping(l) >= 0);

	Reflector U = new Reflector(l);

	char enc_ch = U.encode(ch);
	char dec_ch = U.decode(enc_ch);


	System.out.println("reflector: " + ch + " " + enc_ch + " " + dec_ch);
    }

    public static void test_plugboard(char ch, String s)
    {
		System.out.println("Plugboard Test");
		System.out.println();

	char[] temp = s.toCharArray();
	Character[] swaps = new Character[temp.length];
	for (int i = 0; i < temp.length; i++) {
	    swaps[i] = temp[i];
	}

	Plugboard P = new Plugboard(swaps);

	char enc_ch = P.encode(ch);
	char dec_ch = P.decode(enc_ch);


	System.out.println("plugboard: " + ch + " " + enc_ch + " " + dec_ch);
    }

    public static void test_enigma(String psettings, String enigma_setting,
				   String plaintext, int rseed)
    {
		System.out.println("Full Engima Test");
		System.out.println();
	char[] temp = psettings.toCharArray();
	Character[] plugboard_settings = new Character[temp.length];
	for (int i = 0; i < temp.length; i++) {
	    plugboard_settings[i] = temp[i];
	}

	// construct enigma machine
	Enigma e = new Enigma(plugboard_settings, enigma_setting, rseed);

	String ciphertext = "";
	for (char ch : plaintext.toCharArray()) {
	    ciphertext += e.encode(ch);  // encode using enigma machine
	}

	e.reset(enigma_setting);   // reset to initial orientation settings

	String dec_ciphertext = "";
	for (char cipher_ch : ciphertext.toCharArray()) {
	    dec_ciphertext += e.decode(cipher_ch);
	}


	System.out.println("Plaintext : " + plaintext);
	System.out.println("Ciphertext: " + ciphertext);
	System.out.println("Dec cipher: " + dec_ciphertext);
	System.out.println();
    }
    
    public static void main(String[] args) {
	if (args.length != 3) {
	    System.out.println("USAGE: java TestEnigma <enigma_setting_string> <plaintext string> <random seed>");
	    return;
	}
	// get command-line arguments, convert to int, and store
	String enigma_setting = new String(args[0]);
	String plaintext = new String(args[1]);
	int rseed = Integer.parseInt(args[2]);

	// should have no duplicates in string for testing plugboard
	test_plugboard(enigma_setting.charAt(0), "qwerty");
		System.out.println();
	test_plugboard(enigma_setting.charAt(1), "aassddff");
		System.out.println();
	test_plugboard(enigma_setting.charAt(2), "zxcvbnmlkjhgfd");
		System.out.println();
	
	test_rotor(enigma_setting.charAt(0), plaintext.charAt(0), rseed);
		System.out.println();
	test_reflector(plaintext.charAt(0), rseed);
		System.out.println();
	test_enigma("qwerty", enigma_setting, plaintext, rseed);
		System.out.println();
	test_enigma("aassddff", enigma_setting, plaintext, rseed);
		System.out.println();
	test_enigma("zxcvbnmlkjhgfd", enigma_setting, plaintext, rseed);
		System.out.println();
    }
}
