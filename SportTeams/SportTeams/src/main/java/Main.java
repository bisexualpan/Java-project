import java.sql.SQLException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws SQLException {
        List<Sportsman> sportsmans = CsvParser.parse("SportTeams.csv");
        
        DataBase dataBase = new DataBase();
        dataBase.createTable();
        dataBase.insertSportsman(sportsmans);
        
        Tasks.averageAgeTeam();
        System.out.println(Tasks.topFivePlayersFromHighestTeam());
        System.out.println(Tasks.Task3());
    }
}
