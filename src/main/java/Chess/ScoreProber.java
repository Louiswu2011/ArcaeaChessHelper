package Chess;


import com.neovisionaries.ws.client.WebSocketException;
import javafx.scene.paint.Paint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ScoreProber {
    private static String probeURL = "https://lab.zl-studio.cn/potential/search.php";


    public static String readString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream into = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        for (int n; 0 < (n = inputStream.read(buf)); ) {
            into.write(buf, 0, n);
        }
        into.close();
        return new String(into.toByteArray(), StandardCharsets.UTF_8);
    }

    RecentInfo probe(String arcid, ArrayList<InfoStack> infoStacks, Paint customFill) {
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
            System.out.println(json);
            infoStacks.add(new InfoStack(name, json));
            infoStacks.get(getIndexByName(infoStacks, name)).setColor(customFill);
            return new RecentInfo(name, score, song);
        } catch (IOException e) {
            return new RecentInfo("sb", "get yourself INTERNET!!!!!", "oops!");
        } catch (JSONException e2) {
            return new RecentInfo("sb", "invalid user id!!!!!", "oops!");
        }
    }

    RecentInfo probeAlternative(String arcid, ArrayList<InfoStack> infoStacks, Paint customFill) {
        try {
            ProbeClient probeClient = new ProbeClient();
            Future<String> probeFuture = probeClient.getUserRecent(arcid);
            while (!probeFuture.isDone()) {
                // Do nothing and wait for return
            }
            Thread.sleep(100);
            String json = probeFuture.get();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name");
            JSONArray payload = data.getJSONArray("recent_score");
            JSONObject recent = payload.getJSONObject(0);
            String song = recent.getString("song_id");
            int scoreint = recent.getInt("score");
            String score = Integer.toString(scoreint);
            infoStacks.add(new InfoStack(name, json));
            infoStacks.get(getIndexByName(infoStacks, name)).setColor(customFill);
            System.out.println(name);
            return new RecentInfo(name, score, song);
        } catch (JSONException e2) {
            return new RecentInfo("sb", "invalid user id!!!!!", "oops!");
        } catch (WebSocketException | IOException | InterruptedException e) {
            e.printStackTrace();
            return new RecentInfo("sb", "invalid user id!!!!!", "oops!");
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new RecentInfo("sb", "invalid user id!!!!!", "oops!");
        }
    }

    private int getIndexByName(ArrayList<InfoStack> infoStacks, String name) {
        for (InfoStack infoStack : infoStacks) {
            if (infoStack.getName().equals(name)) {
                return infoStacks.indexOf(infoStack);
            }
        }
        return -1;
    }

}
