
public class Reflector {

    //instance varibales
    private Character[] alphaperm;

    private static int check_for_self_mapping(Character[] alphaperm) {

        for (int i = 0; i < alphaperm.length; i++) {
            if (alphaperm[i] == i + 'a') {
                return i;
            }
        }
        return -1;
    }

    public Reflector(Character[] alphaperm) {

        //make the argument alphaperm an instance variable
        this.alphaperm = alphaperm;

        if(check_for_self_mapping(alphaperm) == -1) {
            System.out.print("ERROR SELF MAPPING FOUND IN REFLECTOR: ");
        }

    }

    // encode one character through the reflector
    public char encode(char ch) {

        //maps every object in the array to one and only one other object and never to itself
        for(int i = 0; i < alphaperm.length; i++){

            if (alphaperm[i].equals(ch)) {

                if (i % 2 == 0) {
                    ch = alphaperm[i + 1];
                    return ch;

                }
                if (i % 2 != 0) {
                    ch = alphaperm[i - 1];
                    return ch;
                }
            }
        }
        return '\0';
    }
    // decode one character through the reflector
    public char decode(char ch) {

        //returns back the starting object from encode process
        for(int i = 0; i < alphaperm.length; i++){

            if (alphaperm[i].equals(ch)) {

                if (i % 2 == 0) {
                    ch = alphaperm[i + 1];
                    return ch;

                }
                if (i % 2 != 0) {
                    ch = alphaperm[i - 1];
                    return ch;

                }
            }
        }
        return '\0';
    }
}
