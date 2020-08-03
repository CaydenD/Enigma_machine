

public class Plugboard {

    //array of swaps
    private Character[] swaps;
    
    public Plugboard(Character[] swaps) {  // construct a Plugboard

        this.swaps = swaps;

        //checks to ensure that the object array swaps is of the specifed length
        if(swaps.length / 2 > 5) {
            System.out.print("ERROR IN PLUG SWAPS: ");
        }
        else if(swaps.length %2 != 0) {
            System.out.print("ERROR UNEVEN NUMBER OF SWAPS: ");
        }

    }

    // encode one character through the plugboard
    public char encode(char ch) {

        // if the given ch is in the swaps object array it encodes it to the given char
        // if ch is not in the given object array it simply returns that character
        for(int i = 0; i < swaps.length; i++){

            //checks to see if i is odd or even
            if (swaps[i].equals(ch)) {

                if (i % 2 == 0) {
                    ch = swaps[i + 1];
                    return ch;
                }
                if (i % 2 != 0) {
                    ch = swaps[i - 1];
                    return ch;
                }
            }
        }
	return ch;   // CHANGE THIS, JUST HERE IN ORDER TO COMPILE
    }

    public char decode(char ch) {

        // if the given ch is in the swaps object array it decodes it to the given char
        // if ch is not in the given object array it simply returns that character
        for(int i = 0; i < swaps.length; i++){

            if (swaps[i].equals(ch)) {

                //checks to see if i is odd or even
                if (i % 2 == 0) {
                    ch = swaps[i + 1];
                    return ch;
                }
                if (i % 2 != 0) {
                    ch = swaps[i - 1];
                    return ch;
                }
            }
        }
        return ch;   // CHANGE THIS, JUST HERE IN ORDER TO COMPILE
    }
}
