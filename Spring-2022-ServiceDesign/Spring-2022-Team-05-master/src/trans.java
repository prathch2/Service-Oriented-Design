/**************************************************************************************
 * Translator Module
 * Component: Task Manager
 * ************************************************************************************
 * Function:  Translates given word into specified language.
 *----------------------------------------------------------------------------------------
 * Input:   Parameters – Language Word
 * Output:  Return – Translated Word
 *----------------------------------------------------------------------------------------
 * @author: Edwin Constancia
 * @viewer: Pratham Choksi, Abtin Ghaffari, Rohan Nair, Joel John
 * @Version 04/20/2022   CMSC 355
 * ************************************************************************************/

import java.io.*;
import java.util.Scanner;

public class trans {

    public static void main(String[] args) throws InterruptedException, IOException {

        /****************************************************************************
         *   Declare Variables:
         *   language        -  language the user wants the word to be translated in
         *   word            -  english word
         *   languageLookup  -  text file that we want to get from textBroker
         *   proc2           -  stores process information about external service call
         *   in              -  stores output stream from external service execution
         *   err             -  stores error stream from external service execution
         *   brInput         -  reads contents of in
         *   brError         -  reads contents of err
         *****************************************************************************/
        String language = args[0];
        String word = args[1];
        String languageLookup = language + ".txt";
        String trans = null;
        Process proc2 = null;
        String line = null;

        /*********************************************************
         * @code:
         * 1) Parameter equals the tb and wanted textFile and word
         *
         * 2) call the textBroker through the serviceBroker
         *
         * 3) if when checked, there are errors returned from the textBroker
         *
         *
         *    a) IF "error" is found then we will call error message handle to print out correct message according to 805
         *    b) IF "error" is found then we will call error message handle to print out correct message according to 813
         * 4) Output of the error message
         * 5) ELSE print out the translation of the word.
         *********************************************************/


        //Look up at 1)
        String[] tbArgs = {"tb", languageLookup, word};

        /*********************************************************
         *@code
         *1) execute execution command and transfer control over to that process
         *2) store output and error stream
         *3) use buffered reader objects to print out output and error from external process
         *********************************************************/


        //Look up at 2)
        Process proc = Runtime.getRuntime().exec("java -jar service.jar", tbArgs);
        proc.waitFor();


        InputStream in = proc.getInputStream();
        InputStream err = proc.getErrorStream();


        BufferedReader brInput = new BufferedReader(new InputStreamReader(in));
        BufferedReader brError = new BufferedReader(new InputStreamReader(err));

        while ((line = brInput.readLine()) != null) {
            trans = line;
        }

        while ((line = brError.readLine()) != null) {
            System.out.println(line);

        }


        //Look at 3a)
        if (trans == "805") {
            String[] errorArgs = {"error", "805"};
            proc2 = Runtime.getRuntime().exec("java -jar service.jar", errorArgs);
            proc2.waitFor();
        }

        //Look at 3b)
        if(trans == "813") {
            String[] errorArgs = {"error", "813"};
            proc2 = Runtime.getRuntime().exec("java -jar service.jar", errorArgs);
            proc2.waitFor();
        }


        //Look at 4
            InputStream in2 = proc2.getInputStream();
            InputStream err2 = proc2.getErrorStream();
            BufferedReader br2Input = new BufferedReader(new InputStreamReader(in2));
            BufferedReader br2Error = new BufferedReader(new InputStreamReader(err2));


            String line2;
            while ((line2 = br2Input.readLine()) != null) {
                System.out.println(line2);
            }

            while ((line2 = br2Error.readLine()) != null) {
                System.out.println(line2);

            }

            //Look at 5
        System.out.println(trans);

    }


}
