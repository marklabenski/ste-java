package ste;
import ste.crypto.CryptoBackend;

import java.util.Scanner;

import static java.lang.System.*;

/**
 * Created by marklabenski on 09.05.17.
 */
public class SecureTextEditor {
    public static void main(String[] args) throws Exception
    {
        CryptoBackend cryptoBackend = CryptoBackend.getInstance();

        Scanner scan = new Scanner(System.in);

        String next = scan.nextLine();

        out.println(next);
    }
}
