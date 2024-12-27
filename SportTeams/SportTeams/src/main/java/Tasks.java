import java.sql.*;
import javax.swing.JFrame;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Tasks {
	public static void averageAgeTeam() {
		String averageAgeTeamSQL = "SELECT team, AVG(age) AS AverageAge FROM Sportsmans GROUP BY team";
		
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:SportsmansDB.db");
			Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(averageAgeTeamSQL)) {
			
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	          
	        while (rs.next()) {
	        	dataset.addValue(rs.getDouble("AverageAge"), "networth", rs.getString("team"));
	        }
	            
	        JFreeChart barChart = ChartFactory.createBarChart(
	        		"Средний возраст в команде",
	        		"Команда",
	        		"Возраст",
	                dataset,
	                PlotOrientation.VERTICAL,
	                true, true, false
	        	);
	        
	        displayChart(barChart);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static String topFivePlayersFromHighestTeam() {
		String topFivePlayersFromHighestTeamSQL = "SELECT name, height " +
				"FROM Sportsmans " +
				"WHERE team = " +
				"(SELECT team FROM " +
				"(SELECT team, AVG(height) AS AverageHeight " +
				"FROM Sportsmans GROUP BY team) " +
				"ORDER BY AverageHeight DESC LIMIT 1) " +
				"ORDER BY height DESC LIMIT 5";
		String result = "";
		
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:SportsmansDB.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(topFivePlayersFromHighestTeamSQL)) {

            while (rs.next()) {
            	result += rs.getString("name") + '\n';
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return result;
	}
	
	public static String Task3() {
		String sqlQuery = "SELECT team FROM " +
				"(SELECT team, AVG(height) AS avgHeight, AVG(weight) AS avgWeight, AVG(age) AS avgAge " +
				"FROM Sportsmans GROUP BY team) " +
				"WHERE avgHeight BETWEEN 74 AND 78 AND avgWeight BETWEEN 190 AND 210 " +
				"ORDER BY avgAge DESC LIMIT 1";
		String result = "";
		
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:SportsmansDB.db");
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sqlQuery)) {

	            if (rs.next()) {
	            	result = rs.getString("team");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
		return result;
	}
	
	private static void displayChart(JFreeChart chart) {
		ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new javax.swing.JFrame();
            
        frame.setContentPane(chartPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
}
