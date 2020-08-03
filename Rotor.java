
public class Rotor {
    
    private Character[] alphaperm;
    
    private int pos;

    public Rotor(Character[] alphaperm, char startpos) {

        //convert starting position into a int to use to keep track of rotor
        int pos = (int) startpos;
        pos = pos - 97;
        this.pos = pos;

        // checks to see if object array alphaperm is of teh specifed length
        if(alphaperm.length != 26) {
            System.out.print("MUST HAVE 26 LETTERS IN PERMUTATION: ");
            System.exit(1);
        }
        else if(alphaperm.length == 26)
            this.alphaperm = alphaperm;

    }

    // encode one character, ch, according to the rotor "wiring"
    public char encode(char ch) {
        //convert ch into a int
        int input = (int) ch;

        input = input - 97;
        input = (pos + input) % 26; // this needs to be done with mod so it can wrap around
        char enc_ch = alphaperm[input];

        return enc_ch;   // CHANGE THIS, JUST HERE TO COMPILE
    }
    
    public char decode(char ch) {

        int index = 0;

        //find the index of the given ch in the object array alphaperm
        for(int i = 0; i < alphaperm.length; i++) {

            if (alphaperm[i].equals(ch)) {
                index = i;
                break;
            }
        }

        //java doesnt return the modulus it returns remainder this messes up the deseirred wraping feature i was looking for with mod
        int spot = index - pos;
        spot = spot % 26;

        // this is done for if spot is negative
        if(spot < 0)
            spot += 26;

        // adds ascii value of 'a' back to spot to get ascii of orgianl char
        index = spot + 97;

        char dec_ch = (char) index;


        return dec_ch;   // CHANGE THIS, JUST HERE TO COMPILE
    }

    public boolean advance() {
        // every time this method is called the postion is moved once and if the rotor makes a full rotation it returns a bool of true
        pos++;
        if(pos == 26) {
            pos = 0;
            return true;
        }
        return false;   // CHANGE THIS, JUST HERE TO COMPILE
    }

    // reset the starting position for the rotor
    public void reset(char startpos) {
        // a simple reset method to reset the rotor to the given starting position
        int ascii = (int) startpos;
        // subtrack the ascii value of 'a' to get the desired index postion
        pos = ascii - 97;
    }
}
