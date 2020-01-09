import core.Line;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONParse {

    private static String dataFile = "./src/main/resources/moscowMetro.json";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = null;
        try {
            jsonData = (JSONObject) parser.parse(getJsonFile());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Line> moscowMetroLines = parseLines(jsonData);
        moscowMetroLines.forEach(line -> {
            System.out.println("На " + line + " " + line.getStationsCount() + " станций");
        });
    }


    private static ArrayList<Line> parseLines(JSONObject lines)
    {
        ArrayList<Line> lineList = new ArrayList<>();
        lines.keySet().forEach(lineName -> {
            Line line = new Line(lineName.toString());
            JSONArray stations = (JSONArray) lines.get(lineName);
            stations.forEach(station -> line.stations.add(station.toString()));
            lineList.add(line);
        });
        return lineList;
    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(builder::append);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
