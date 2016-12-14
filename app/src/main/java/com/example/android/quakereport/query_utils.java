package com.example.android.quakereport;

        import android.util.Log;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.nio.charset.Charset;
        import java.text.DecimalFormat;
        import java.util.ArrayList;
        import java.util.List;

public class query_utils {

    static String LOG_TAG="query_utils";
    private query_utils() {

    }

    public static List<Earthquake> extractEarthquakes(String jsonResponse) {

        ArrayList<Earthquake> earthquakes = new ArrayList<>();


        try {

            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");

            for(int i=0;i<features.length();i++){
                JSONObject current = features.getJSONObject(i);
                JSONObject properties=current.getJSONObject("properties");
                Double lmag = properties.getDouble("mag");
                DecimalFormat dformatter= new DecimalFormat("0.0");
                String mag = dformatter.format(lmag);
                String place= properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                Earthquake earthquake = new Earthquake(mag,place,time,url);
                earthquakes.add(earthquake);

            }

        } catch (JSONException e) {

            Log.e("query_utils", "Problem parsing the earthquake JSON results", e);
        }


        return earthquakes;
    }

    public static List<Earthquake> fetchData(String requestUrl){
        String jsonResponse="";
        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOexception", e);
        }

        List<Earthquake> earthquake = query_utils.extractEarthquakes(jsonResponse);

        return earthquake;
    }



    private static String makeHttpRequest(String url) throws IOException {
        String jsonResponse = "";
        URL myUrl = createURL(url);

        if (myUrl == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream=null;

        try {
            urlConnection = (HttpURLConnection) myUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "error response code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOexception");
        } finally {
            if (urlConnection != null) urlConnection.disconnect();

            if (inputStream != null) inputStream.close();
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }

        return output.toString();

    }


    private static URL createURL(String stringurl) {
        URL url = null;
        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed url exception", e);
        }
        return url;
    }
}
