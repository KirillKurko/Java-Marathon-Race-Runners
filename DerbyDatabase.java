
import java.sql.*;
import java.util.ArrayList;
/**
 * This class reads the data from the derby database and created ThreadRunner object for
 *  each runner present in the database
 * @author mamta-prashant
 *
 */
public class DerbyDatabase implements DataSource{
	
	/**
	 * Sets the connection with the databse
	 * @return connection
	 */
    private Connection getConnection() {
        Connection connection = null;
        try {
            String dbDirectory = "Resources";
            System.setProperty("derby.system.home", dbDirectory);

            String url = "jdbc:derby:FinalDB";
            String user = "";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e)
        {
            System.err.println("Error loading database driver: " + e);
            System.exit(1);
        }
        return connection;
    }

    /**
     * Override the method declared in the interface.This method reads the data from the file and creates
     * ThreadRunner objects for each runner present in the file
     * @return Arraylist of type ThreadRunner
     */
	@Override
    public ArrayList<RunnerThread> getRunners() {
    	ArrayList<RunnerThread> runners = new ArrayList<RunnerThread>();
        String query = "SELECT * FROM Runners";
	    try (Connection connection = getConnection();
	         PreparedStatement ps = connection.prepareStatement(query);
	         ResultSet rs = ps.executeQuery())                
	    {
	    	while(rs.next()) {
	    		String name = rs.getString("Name");
	            int speed = rs.getInt("RunnersSpeed");
	            int rest = rs.getInt("RestPercentage");
	            RunnerThread runner = new RunnerThread(name, speed, rest);
	            runners.add(runner);
	        }
	    	disconnect();
	        return runners;
	    } 
	    catch(SQLException sqle) {
	    	 System.out.println("Some error has occured while reading the data from database.");
	    	 disconnect();
	    	 return null;
	    }
    }
	
	public void disconnect() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
                if (!e.getMessage().equals("Derby system shutdown.")) {
                    System.out.println("Database did not get disconnected.");
                }
        }
	}
}