import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


class Main {

    final static String IMAGE_DESTINATION_FOLDER = "/Users/maksimstarsev/Downloads/images";

    public static void main(String[] args) {

        String strURL = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

        try {
            Document document = Jsoup
                    .connect(strURL)
                    .timeout(10 * 1000)
                    .get();

            Elements rows = document.select("table.standard.sortable tbody tr");
            for (Element row : rows){

                System.out.println(row.select("td:eq(0) span a").attr("title")
                        + " - " + row.select("td:eq(1) span a").text() + "\n");
//                System.out.print();
//                String connections = row.select("td:eq(3) span a").attr("title");
//                System.out.print(" - :" + connections.substring(connections.indexOf('ю') + 1) + ":\n");

            }




//
//            Elements lines = document.select("table.standard.sortable tbody tr td:eq(0) span a");
//            Elements stations = document.select("table.standard.sortable tbody tr td:eq(1) span a");
//            Elements connections = document.select("table.standard.sortable tbody tr td:eq(3) span a");

//            for (int i = 0; i < stations.size(); i++){
//                System.out.println(lines.get(i).attr("title") + " - " + stations.get(i).text());
//            }
//            for (Element row : rows) {
//                String lineName = row.text();
//                System.out.println(lineName);
//            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }











    private static void downloadImage(String strImageURL){

        //get file name from image path
        String strImageName =
                strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );

        System.out.println("Saving: " + strImageName + ", from: " + strImageURL);

        try {

            //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n;
//            = -1;

            OutputStream os =
                    new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );

            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }

            //close the stream
            os.close();

            System.out.println("Image saved");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
