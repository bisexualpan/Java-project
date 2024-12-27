import java.sql.*;
import java.util.List;

class DataBase {
	private Connection connection;
    private static final String dbUrl = "jdbc:sqlite:SportsmansDB.db";

    public DataBase() {
        try {
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Sportsmans (" +
                "name TEXT, " +
                "team TEXT, " +
                "position TEXT, " +
                "height INTEGER, " +
                "weight INTEGER, " +
                "age DOUBLE)";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSportsman(List<Sportsman> sportsmans) {
        String insertSQL = "INSERT INTO Sportsmans (name, team, position, height, weight, age) VALUES (?, ?, ?, ?, ?, ?)";
        
        for (Sportsman sportsman : sportsmans) {
	        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
	            pstmt.setString(1, sportsman.getName());
	            pstmt.setString(2, sportsman.getTeam());
	            pstmt.setString(3, sportsman.getPosition());
	            pstmt.setInt(4, sportsman.getHeight());
	            pstmt.setInt(5, sportsman.getWeight());
	            pstmt.setDouble(6, sportsman.getAge());
	            
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
        }
    }
}