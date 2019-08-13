package Chess;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ScoreProber {
    private static String probeURL = "https://lab.zl-studio.cn/potential/search.php";

    public static String readString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream into = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        for (int n; 0 < (n = inputStream.read(buf)); ) {
            into.write(buf, 0, n);
        }
        into.close();
        return new String(into.toByteArray(), "UTF-8");
    }

    RecentInfo probe(String arcid) {
        try {
            URLConnection c = new URL(probeURL + "?Aid=" + arcid).openConnection();
            c.connect();
            InputStream is = c.getInputStream();
            String json = readString(is);
            JSONObject p = new JSONObject(json);
            String name = p.getString("name");
            JSONArray recent = p.getJSONArray("recent_score");
            int scoreint = recent.getJSONObject(0).getInt("score");
            String song = recent.getJSONObject(0).getString("song_id");
            String score = Integer.toString(scoreint);
            return new RecentInfo(name, score, song);
        } catch (IOException e) {
            return new RecentInfo("sb", "get yourself INTERNET!!!!!", "oops!");
        } catch (JSONException e2) {
            return new RecentInfo("sb", "invalid user id!!!!!", "oops!");
        }
    }

    String probeTest() throws MalformedURLException {
        try {
            URLConnection c = new URL(probeURL + "?Aid=" + "892842548").openConnection();
            c.connect();
            InputStream is = c.getInputStream();
            String result = readString(is);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "fuck";
        }
    }
}
