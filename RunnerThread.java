import java.util.ArrayList;

/**
 * 
 */

/**
 * This class stores all the details for the  runners.
 * @author mamta-prashant
 *
 */
public class RunnerThread extends Thread{

	private final String name;
	private final int rest;
	private final int speed;
	private int distance = 0;
	ArrayList<String> rank = new ArrayList<String>();
	
	/**
	 * Constructor
	 * @param RunnerName - Runners name
	 * @param RunnerSpeed - Runners speed
	 * @param RestPercentage - Rest Percentage
	 */
	public RunnerThread(String RunnerName, int RunnerSpeed, int RestPercentage) {
		name = RunnerName;
		speed = RunnerSpeed;
		rest = RestPercentage;
	}
	
	/**
	 * Based on a random number created , decides if the runner has to sleep or run.If the distance covered is more than 1000
	 * it declares the thread the winner based on the flag condition
	 */
	public void run() {

		while ((distance < 1000) && (!MarathonRaceApp.raceFlag))  {
			int randomNumber = (int)(Math.random() * 100);
			if (randomNumber > rest) {
				distance += speed;
			}
			System.out.println(this.getRunnerName() + " : " + distance);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}	
		if ((distance >= 1000) && (!MarathonRaceApp.raceFlag)){
			MarathonRaceApp.finished(this);
		}
		
	}
		
	/**
	 * Returns the runner's name
	 * @return name of the runner
	 */
    public String getRunnerName()
    {
        return name;
    }   
}
