package kalen.app.example.netrequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kalen on 2017/4/13.
 */

public class HttpUtils {
    static String TestGet() {
        try {
            URL url = new URL("https://www.baidu.com");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");

            if(connection.getResponseCode() == 200 ){
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String result = "", line = null;

                while ((line = reader.readLine()) != null) {
                    result += line;
                }

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Error";
    }

}
