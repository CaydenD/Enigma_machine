// Remember, you can't change the interface, nor add import statements

public class Plugboard {
    // decide on your instance variables to represent the plugboard
    
    // NOTE:  you need to be able to map and reverse-map
    //        (remember the direction of flow when discussed in class)

    //array of swaps
    private Character[] swaps;
    
    // swaps is a character array with upto 10 pairs of characters to
    // swap (eg. ['a', 'j', 'q', 'z', 'b', 'w'])
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
    // (you may assume that ch is a valid character in range ['a', 'z']
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

    // decode one character through the plugboard
    // (you may assume that ch is a valid character in range ['a', 'z']
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
