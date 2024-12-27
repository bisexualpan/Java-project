import java.util.*;
import java.io.FileReader;
import com.opencsv.CSVReader;

public class CsvParser {
	public static List<Sportsman> parse(String filePath) {
		List<Sportsman> sportsmans = new ArrayList<Sportsman>();
		
		try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine = csvReader.readNext();
            
            while ((nextLine = csvReader.readNext()) != null) {
            	Sportsman sportsman = new Sportsman(
            			nextLine[0],
            			nextLine[1],
            			nextLine[2],
            			Integer.parseInt(nextLine[3]),
            			Integer.parseInt(nextLine[4]),
            			Double.parseDouble(nextLine[5])
            	);
                
            	sportsmans.add(sportsman);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        return sportsmans;
	}
}
