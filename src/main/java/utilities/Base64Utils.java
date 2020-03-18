package utilities;

import java.util.Base64;

public class Base64Utils {

    /**
     *
     * Method responsible for encoding a String into a Base64
     *
     * @param stringToBeEncoded String to be encoded in Base64
     * @return Base64 encoded String
     * @author mgarcdia - Matheus Garcia Dias
     */
    //08232019 - Created | mgarcdia
    public String encode(String stringToBeEncoded) {
        System.out.println("Method: Base64 | Encode");

        if (stringToBeEncoded != null && !stringToBeEncoded.equals("")) {
            String encodedValue = Base64.getEncoder().encodeToString(stringToBeEncoded.getBytes());
            return encodedValue;
        } else {
            System.out.println("Inputed value null or empty");
            return "";
        }

    }

    /**
     *
     * Method responsible for decoding a Base64 String
     *
     * @param stringToBeDecoded String in Base64 to be decoded
     * @return Base64 decoded String
     * @author mgarcdia - Matheus Garcia Dias
     */
    //08232019 - Created | mgarcdia
    public String decode(String stringToBeDecoded) {
        System.out.println("Method: Base64 | Decode");

        if (stringToBeDecoded != null && !stringToBeDecoded.equals("")) {
            byte[] decodedBytes = Base64.getDecoder().decode(stringToBeDecoded);
            String decodedString = new String(decodedBytes);
            return decodedString;
        } else {
            System.out.println("Inputed value null or empty");
            return "";
        }

    }

}
