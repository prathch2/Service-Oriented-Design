
/* *********************************************************************************** *
 *   TextBroker Module
 *
 * Component:
 * *********************************************************************************** *
 * Function:  Looks up the given word in text file and provides the output from the text file / error
 *
 *----------------------------------------------------------------------------------------------------------------------------------------
 *    Input:   Parameters – text file, key
 *
 *    Output:  Return – output from text file / error
 *
 *----------------------------------------------------------------------------------------------------------------------------------------
 *    Author: Joel John
 *    Co-Author: Rohaan Nair, Pratham Choksi
 *    Review: Abtin Ghaffari, Edwin Constancia, Rohaan Nair, Pratham Choksi
 *    Version 04/22/2021   CMCS 355
 * *********************************************************************************** */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class TextBroker
{
    public static void main (String []args) throws IOException, InterruptedException {



        String filename = args[0];
        String key = args[1];
        String ser = args[2];


        boolean flag = false;

        Scanner sc = new Scanner(new FileInputStream(filename));


            while (sc.hasNextLine())
            {
                String[] currentLine = sc.nextLine().split(",");

                if(ser.equalsIgnoreCase("=")) { /*the condition for translate */
                    if (currentLine[0].equalsIgnoreCase(key))
                    {
                        flag = true;
                        System.out.println(currentLine[1]);
                        break;
                    }
                }

                else if (ser.equalsIgnoreCase("<=")){ /*condition fo tax calculator*/
                    if(currentLine[0].compareTo(key)<= 0){
                        flag = true;
                        System.out.println(currentLine[1]);
                        break;
                    }
                }


            }
            if(flag == false){
                /* sends an error to error broker through service broker */
                String[] pram = new String[]{"msg", "404"};
                Process output = Runtime.getRuntime().exec("java -jar service.jar", pram);
                output.waitFor();
                InputStream in = output.getInputStream();
                InputStream err = output.getErrorStream();

                BufferedReader bri = new BufferedReader(new InputStreamReader(in));
                BufferedReader bre = new BufferedReader(new InputStreamReader(err));


                String line;
                while ((line = bri.readLine()) != null) {
                System.out.println(line);
                }

                while ((line = bre.readLine()) != null) {
                    System.out.println(line);
                }

                bri.close();
            }



        sc.close();


    }




    }


