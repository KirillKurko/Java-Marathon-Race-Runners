/**
 * 
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class reads the data from the textfile and parses the data to create ThreadRunner object
 * @author mamta-prashant
 *
 */
public class TextFile implements DataSource{

	private File textFile = null;
	private BufferedReader data = null;
	
	
	/**
	 * Constructor - Fetches the textfile name from the user and checks if it is valid
	 * @param input
	 */
    public TextFile(Scanner input) {
    	System.out.println("Enter your Textfile name: ");
    	String textfilename = input.nextLine(); 
    	textFile = new File(textfilename);
    	this.checkFile();
    }

    
    /**
     * Checks if the file exists 
     */
    private void checkFile() {
    	try {
    		data = new BufferedReader(
    				       new FileReader(textFile));
    		
    	}
    	catch(FileNotFoundException e)
    	{
    		System.out.println("The mentioned File is not found.");
            System.out.println("");
			System.exit(1);
		} catch (Exception ex) {
			System.out.println("The following error occured while reading the file.");
			ex.printStackTrace();
			System.exit(2);
		}
    }
    
    
    /**
     * Override the method declared in the interface.This method reads the data from the file and creates
     * ThreadRunner objects for each runner present in the file
     * @return Arraylist of type ThreadRunner
     */
    @Override
    public ArrayList<RunnerThread> getRunners() {
    	ArrayList<RunnerThread> runners = new ArrayList<RunnerThread>();
    	try {
    		String line = data.readLine();
    		while(line != null) {
    			RunnerThread runner = getData(line);
    			runners.add(runner);  
    			line = data.readLine();
    		}
    	}
    	catch (IOException e) {
    		System.out.println("The following error occured while reading the file.");
    		e.printStackTrace();
    		System.exit(3);
    	}
		return runners;
    	}
    
    
    /**
     * This method parses the data passed to it and fetches the runner name, speed and the rest percentage
     * @param line - the data from the text file
     * @return - ThreadRunner object
     */
    private RunnerThread getData(String line) {
    	String name = null;
    	try 
    	{
    		String[] lineParse = line.split("\t");
    		name = lineParse[0];
    		String speedString = lineParse[1];
    		String restString = lineParse[2];
    		int speed = Integer.parseInt(speedString);
    		int rest = Integer.parseInt(restString);
    		return (new RunnerThread(name,speed,rest));
    	}
    	catch (NumberFormatException num) {
    		System.out.println("The data entered for the speed/rest is not correct for the runner : " + name);	
    		System.exit(1);
    	}
    	catch (Exception ex) {
			System.out.println("An error occured while reading the data from the file");
			System.exit(1);
    	}
		return null;   	
    }
}
