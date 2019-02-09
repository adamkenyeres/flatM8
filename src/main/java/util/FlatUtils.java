package util;

import model.flat.Address;

public class FlatUtils {

    public static Address addressFromStringArray(String[] args) {
        return new Address(args[0], args[1],
                Integer.parseInt(args[2]),
                Integer.parseInt(args[3]),
                Integer.parseInt(args[4]),
                Integer.parseInt(args[5]));
    }
}
