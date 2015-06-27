import java.util.InputMismatchException;
import java.util.Scanner;

/** 
 * @author Mamta Patil
 * Description : This class handles all the validations to be done on the user inputs.

 */

public class Validator {
 
	/**
	 * This method checks if the user has entered either of the acceptable choice
	 * @param input -  input enetered by the user
	 * @return User entered acceptable choice
	 */
	public static int getValue(Scanner input) {
	    int index = 1;
	    int choice = 0;
	    while (index == 1) {
		   try {
			   choice = input.nextInt();
			   String strbuffer = input.nextLine();
			   if (choice <= 0 || choice > 5) {
		    	   System.out.println("Please enter a value between 1 and 5. ");
		    	   continue;
		       }
			   else {
				   index = 0;
			   }
			   if (!strbuffer.equals("")) {
				   System.out.println("You have entered an invalid choice,please re-enter your choice.");
				   index=1;
				   continue;
			   }
		   } catch (final InputMismatchException e) {
			   System.out.println("You have entered an invalid choice,please re-enter your choice.");
			   input.nextLine();
			   continue;
		   }
	   }
	    return choice;
	}
}