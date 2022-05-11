import java.util.Scanner;
import java.io.*;

/**************************************************************************************  
 * Service Broker Class
 ************************************************************************************
 * Function: Makes external calls to other services 
 *--------------------------------------------------------------------------------------    
 * @author Rohan Nair
 * @version 03/27/2017   CMCS 355 
 * *************************************************************************************/

public class ServiceBroker {

	 /**************************************************************************************
	  * Service Broker Module
	  *
	  * Component: Orchestration Module
	  ***************************************************************************************
	  * Function:
	  *   main - Main entry point for Service Broker to make calls to other services
	  *----------------------------------------------------------------------------------------------------------------------------------------
	  *    Input:
	  *          Parameters - service code and array of arguments to other services
	  *    Output:
	  *          Return – None
	  *----------------------------------------------------------------------------------------------------------------------------------------
	  *    Author: Rohan Nair
	  *    Version 04/21/2022   CMCS 355 
	  **************************************************************************************/ 
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		/*********************************************************
		 * Declare Variables: 
		 * scan - scanner to read service.txt file
		 * serviceCode - service code for service
		 * flag - boolean flag to indicate whether or not service exists
		 *********************************************************/
		Scanner scan = new Scanner(new File("service.txt"));
		String serviceCode = args[0];
		boolean flag = false;
		
		/*********************************************************
		 * @code: 
		 * 1) scanner goes through each line in service.txt file
		 * 
		 *    a) initialize an array of strings currentLine which contains strings
		 *    in the current line scanner is reading separated by a comma
		 *    	i) currentLine[0] = service code
		 *      ii)currentLine[1] = execution command
		 *    
		 *    b) if the first string is equal to the service code, flip flag to true
		 *    to indicate the service was found, then loop through the args list after
		 *    the service code and append the args to the execution command of the external
		 *    service
		 *    
		 *    c) call run method on execution command
		 *  
		 *  2) if the flag was flipped, run execution command for error service to indicate service wasn't found
		 *********************************************************/
		while (scan.hasNextLine()) {
			
			String[] currentLine = scan.nextLine().split(",");
			
			if (currentLine[0].equals(serviceCode)) {
				flag = true;
				
				for (int i = 1; i < args.length; i++)
					currentLine[1] += " " + args[i];
				
				run(currentLine[1]);
			}
		}
		
		if (!flag) {
			run("java -jar msg.jar 404");
		}
	}
	
	/**************************************************************************************
	  * Service Broker Module
	  *
	  * Component: Orchestration Module
	  ***************************************************************************************
	  * Function:
	  *    run - executes external service and retrieves its output
	  *----------------------------------------------------------------------------------------------------------------------------------------
	  *    Input:
	  *          command - execution command for external service
	  *    Output:
	  *          Return – doesn't actually return a value but should retrieve output from stdout of external service
	  *----------------------------------------------------------------------------------------------------------------------------------------
	  *    Author: Rohan Nair
	  *    Version 04/21/2022   CMCS 355 
	  **************************************************************************************/
	public static void run(String command) throws IOException, InterruptedException {
		
		/*********************************************************
		 * Declare Variables:
		 *    proc -- stores process information about external service call
		 *    in -- stores output stream from external service execution
		 *    err -- stores error stream from external service execution
		 *    bri -- reads contents of in
		 *    bre -- reads contents of err
		 *********************************************************/
		
		/*********************************************************
		 *@code:
		 *1) execute execution command and transfer control over to that process
		 *2) store output and error stream
		 *3) use buffered reader objects to print out output and error from external process 
		 *********************************************************/
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
		
		InputStream in = proc.getInputStream();
		InputStream err = proc.getErrorStream();
		
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

}
