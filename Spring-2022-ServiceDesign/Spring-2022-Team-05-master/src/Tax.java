package com.company;
        /*********************************************************************************** *
        *   Tax Calculator Module
        *
        * Component: Task Manager
        * *********************************************************************************** *
        * Function:  This process calculates the tax due depending on the total wages of the user
        and the tax year. By taking the total wages of the user and the Respective year,
        it returns the user's tax due.
        *
        *----------------------------------------------------------------------------------------------------------------------------------------
        *    Input: Total Wages, Tax Year
        *
        *    Output: Tax Due
        *
        *----------------------------------------------------------------------------------------------------------------------------------------
        *    Author: Abtin Ghaffari
        *    Review: Edwin, Rohan, Pratham, Joel
        *    Version 04/20/2022   CMCS 355
        * ************************************************************************************/

import java.io.*;
import java.util.Scanner;

public class Tax {


    /**************************************************************************************
     *    Tax class
     ************************************************************************************
     * Function:
     *    This class calculates the income after tax has been considered
     *--------------------------------------------------------------------------------------
     *
     *    @author Abtin Ghaffari
     *    @version 04/22/2018   CMCS 355 *************************************************************************************/


    public static void main(String[] args) throws InterruptedException, IOException {
        /*********************************************************
         * Declare Variables:
         *    proc -- stores process information about external service call
         *    in -- stores output stream from external service execution
         *    err -- stores error stream from external service execution
         *    brInput -- reads contents of in
         *    brError -- reads contents of err
         *********************************************************/


        String year = args[0];
        String status = args[1];
        String income = args[2];
        String tax = null;
        String line = null;

        //Append argument 2 onto argument 1
        String yearStatus = year + status + ".txt";
        args[0] = yearStatus;
        args[1] = income;
        String[] tbArgs = {"tb", yearStatus, income};
        Process proc = Runtime.getRuntime().exec("java -jar service.jar", tbArgs);
        proc.waitFor();
        InputStream in = proc.getInputStream();
        InputStream err = proc.getErrorStream();
        BufferedReader brInput = new BufferedReader(new InputStreamReader(in));
        BufferedReader brError = new BufferedReader(new InputStreamReader(err));

        //System.out.println(yearStatus);
        while ((line = brInput.readLine()) != null) {
            tax = line;
        }

        while ((line = brError.readLine()) != null) {
            System.out.println(line);

        }
        //check for error and if year contains error, throw error command.
        if (!tax.contains(".")) {
            String[] errorArgs = {"error", "903"};
            Process proc2 = Runtime.getRuntime().exec("java -jar service.jar", errorArgs);
            proc2.waitFor();

            InputStream in2 = proc2.getInputStream();
            InputStream err2 = proc2.getErrorStream();
            BufferedReader br2Input = new BufferedReader(new InputStreamReader(in2));
            BufferedReader br2Error = new BufferedReader(new InputStreamReader(err2));

            //System.out.println(yearStatus);
            String line2;
            while ((line2 = br2Input.readLine()) != null) {
                System.out.println(line2);
            }

            while ((line2 = br2Error.readLine()) != null) {
                System.out.println(line2);

            }
        }

        System.out.println(taxCalculator(yearStatus, income, tax));

    }

    public static double taxCalculator(String yearStatus, String income, String tax) throws IOException, InterruptedException {
        //This method calculates the income after tax.
        double doubleTax = Double.parseDouble(tax);
        double doubleIncome = Double.parseDouble(income);
        double taxIncome = doubleTax * doubleIncome;
        return taxIncome;
    }
}