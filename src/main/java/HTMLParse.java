import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;

class HTMLParse {
    public static void main(String[] args) {

        String strURL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
        String path = "./src/main/resources/moscowMetro.json";

        JSONObject moscowMetro = HTMLParse.parseHTML(strURL);
        HTMLParse.writeJSONObjToFile(moscowMetro,path);

    }

    @SuppressWarnings("unchecked")
    public static JSONObject parseHTML(String strURL){
        JSONObject moscowMetro = new JSONObject();

        try {
            Document document = Jsoup
                    .connect(strURL)
                    .timeout(10 * 1000)
                    .get();
            Elements rows = document.select("table.standard.sortable tbody tr");
            for (Element row : rows){
                String lineName = row.select("td:eq(0) span a").attr("title");
                String station = row.select("td:eq(1) span a").text();

                if (moscowMetro.containsKey(lineName)){
                    JSONArray stationArray = (JSONArray) moscowMetro.get(lineName);
                    stationArray.add(station);
                } else {
                    JSONArray stationArray = new JSONArray();
                    stationArray.add(station);
                    moscowMetro.put(lineName, stationArray);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return moscowMetro;
    }

    public static void writeJSONObjToFile(JSONObject obj,String path){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(obj.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

