
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


/**
 * This class reads the data from the xmlfile and parses the data to create ThreadRunner object
 * @author mamta-prashant
 *
 */
public class XMLFile implements DataSource{
	
	private FileReader filereader;
	private XMLStreamReader reader;
	
	/**
	 * Constructor - Fetches the file name from the user and checks if the file exists
	 * @param input
	 */
    public XMLFile(Scanner input)  {
    	System.out.println("Enter your XMLFile name: ");
    	String xmlFileName = input.nextLine(); 
    	try {
    		XMLInputFactory inputFactory = XMLInputFactory.newFactory();
    		filereader = new FileReader(xmlFileName);
			reader  = inputFactory.createXMLStreamReader(filereader);
		} catch (FileNotFoundException e) {
			System.out.println("The " + xmlFileName + " file was not found");
			System.exit(0);
		} catch (XMLStreamException e) {
			System.out.println("An error occured while reading the " + xmlFileName + " file");
			System.exit(1);
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
    	String name = null;
    	String speed = null;
    	String rest = null;
    	try {
			while (reader.hasNext()) {
				int eventType = reader.getEventType();
				switch (eventType) {
				case XMLStreamConstants.START_ELEMENT :
					String elementName = reader.getLocalName();
					if (elementName.equals("Runner")) {
						name = reader.getAttributeValue(0);
					}
					if (elementName.equals("RunnersMoveIncrement")) {
						speed = reader.getElementText();
					}
					if (elementName.equalsIgnoreCase("RestPercentage")) {
						rest = reader.getElementText();
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					elementName = reader.getLocalName();
					if (elementName.equals("Runner")) {
						RunnerThread runner = createThreadRunner(name, speed, rest);
						if (runner != null){
							runners.add(runner);
						}
					}
					break;
				default:
					break;
				}
				reader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println("An error occured while reading the data from the file");
			System.exit(1);
		}   	
		return runners;
    }
 
    /**
     * Returns a ThreadRunner object fro the details passed
     * @param name - the runners name
     * @param speedString - the runners speed
     * @param restString - the runners rest percentage
     * @return ThreadRunner object
     */
    private RunnerThread createThreadRunner(String name, String speedString, String restString){
    	try 
    	{
    		int speed = Integer.parseInt(speedString);
    	    int rest = Integer.parseInt(restString);
    	    RunnerThread tr = new RunnerThread(name,speed,rest);
    	    return tr;
    	}
    	catch (NumberFormatException num) {
    		System.out.println("The data entered for the speed/rest is not correct for the runner : " + name);
    		System.exit(1);
    	}
		return null;
    }
}
