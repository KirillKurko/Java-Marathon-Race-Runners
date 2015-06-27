/**
 * 
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main App : This application sets a marathon race between runners .The user selects the input from which the data has to be read from
 * 
 * @author mamta-prashant
 *
 */
public class MarathonRaceApp {
	
	static boolean raceFlag ;
	static Scanner input = new Scanner(System.in);
	static ArrayList<RunnerThread> runners = null;
	/**
	 * Main program starts here
	 * @param args - not used
	 */
	
	public static void main(String[] args) {		    	
		System.out.println("Welcome to the Marathon Race Runner Program");
		System.out.println("");
        while (true) {
        	raceFlag = false;
        	boolean flag = showMenu(); 
        	if (flag == true) {
        		break;
        	}
        	else {
        		System.out.println(" ");
        		System.out.println("Press any key to continue . . .");
  		        String enter = input.nextLine();
  		        if (enter.equals("")) {
  			       System.out.println("");
  		        }
        	}       	
        }
        System.out.println("Thank you for using my Marathon Race Program");
	}
	    
	/**
	 * Method     : showMenu()
	 * Description: This method displays the options available for the user to select.Based on the user choice the further processing is done
	 * @return true - when user chooses to exit the program
	 *         false - if user selects an option of input 
	 */
	public static boolean showMenu() {

		DataSource datasource;
		int index = 0;
		int choice;
	    while (index == 0) {		
	    	showOptions();
		   	choice = Validator.getValue(input);
		   	switch(choice) {
		   		case 1:
		   			datasource  = new DerbyDatabase();
		   			runners = datasource.getRunners();
		   			index = 1;
	    			break;
	    		case 2:		    	
	    			datasource = new XMLFile(input);
		    		runners = datasource.getRunners();
		    		index = 1;
		    		break;
		    	case 3:
		    		datasource = new TextFile(input);
		   			runners = datasource.getRunners();
		   			index = 1;
		   			break;
		   		case 4:
	    			runners = new ArrayList<RunnerThread>();
	    			runners.add(new RunnerThread("Tortoise",10,0));
	    			runners.add(new RunnerThread("Hare",100,90));
		    		index = 1;
		    		break;
		   		case 5:
		   			return true;
		   	}
		   	if (runners.size() == 0) {
	    		System.out.println("There are no runners");
	    	}
		   	else {
		   		startrace(runners);
		   	}
		   }
		return false;
	}	        

	/**
	 * Method : showOptions
	 * Description: Displays the options avaialable for user to select from.
	 * @returns - none
	 */
	public static void showOptions(){
		System.out.println("Select your data source:");
		System.out.println("");
		System.out.println("1. Derby database");
		System.out.println("2. XML file");
		System.out.println("3. Text file");
		System.out.println("4. Default two runners");
		System.out.println("5. Exit");
		System.out.println("");
		System.out.println("Enter your choice");
	}
	
	/**
	 * Method     : startrace
	 * Description: This method starts all the threads created from the input source
	 * @param runners - Arraylist of type ThreadRunner
	 */
	public static void startrace(ArrayList<RunnerThread> runners) {	
		for (RunnerThread rt : runners) {
			RunnerThread runner = rt;
			runner.setName(runner.getRunnerName());
			runner.start();
		} 
		 try {
			 for (RunnerThread rt : runners) {
				 RunnerThread r = rt;
                 r.join();
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
	}
	
	/**
	 * Method      : finished
	 * Description : Sets the raceflag to true indicating that the race is finished.Displays the winner's name
	 * @param runner - The ThreadRunner object which is the winner.
	 */
    public static synchronized void finished(RunnerThread runner){
    	if (!raceFlag) {
    		System.out.println(runner.getRunnerName() + " : I finished!");
    		System.out.println(" ");
    		System.out.println("The race is over!The " +  runner.getName() + " is the winner.\n");
    		for (RunnerThread rt : runners) {
    			if (rt.getName().equals(runner.getName())) {
    				continue;
    			}
    			else {
    				System.out.println(rt.getRunnerName() + " : You beat me fair and square!");
    			}
    		}
    	}
        raceFlag = true;
    }	
}
