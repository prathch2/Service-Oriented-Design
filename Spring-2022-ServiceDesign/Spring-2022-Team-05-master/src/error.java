// Component: Utility
// Function: Prints error
// Input: Message code, param lst
// Output: Error Message


/**************************************************************************************
 * Error Broker Module
 *
 * Component: Orchestration Module
 ***************************************************************************************
 * Function:
 *   Main - Receives the error, determines the language and sends it to text broker to
 *   translate the error code
 ---------------------------------------------------------------------------------------
 *    Input:
 *          Parameters - service code and array of arguments to other services
 *    Output:
 *          Return – Error description
 *--------------------------------------------------------------------------------------
 *    Author: Pratham Choksi
 *    Version 04/22/2022   CMCS 355
 **************************************************************************************/

import java.io.*;

public class error {
    public static void main(String[] args) throws IOException, InterruptedException {


        String code = args[0];

/**************************************************************************************
 * reads the option text file
 ***************************************************************************************/
        FileReader reader = new FileReader("option.txt");
        BufferedReader br = new BufferedReader(reader);
        String data;
        String language = "MSGEnglish.txt";
        while ((data = br.readLine()) != null) {
            language = data;
        }
        br.close();


        String[] pram = new String[]{"tb", language, code};
        Process output = Runtime.getRuntime().exec("java -jar service.jar", pram);
        output.waitFor();

/**************************************************************************************
 * reads the info from the text broker
 ***************************************************************************************/
        InputStream in = output.getInputStream(); //Retrieves outputs from the service broker
        InputStream err = output.getErrorStream(); //Receives error from the service broker

        BufferedReader bri = new BufferedReader(new InputStreamReader(in)); //analyzed the info received from service broker
        BufferedReader bre = new BufferedReader(new InputStreamReader(err));


        String line;
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
        }

        while ((line = bre.readLine()) != null) {
            System.out.println(line);

        }
    }
}
