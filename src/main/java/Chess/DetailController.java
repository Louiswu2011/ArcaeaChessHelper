package Chess;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class DetailController {

    public void showDetail(ArrayList<InfoStack> infoStack, String lookupName) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/detailFrame.fxml"));
        Scene scene = new Scene(root, 250, 310);
        primaryStage.setTitle("Score Detail");
        primaryStage.setScene(scene);

        ProgressBar healthbar = (ProgressBar) scene.lookup("#healthbar");
        Button exitButton = (Button) scene.lookup("#close");
        Label name = (Label) scene.lookup("#nameholder");
        Label potential = (Label) scene.lookup("#potentialholder");
        Label songid = (Label) scene.lookup("#idholder");
        Label pure = (Label) scene.lookup("#pureholder");
        Label far = (Label) scene.lookup("#farholder");
        Label lost = (Label) scene.lookup("#lostholder");
        Label bpotential = (Label) scene.lookup("#bpholder");
        Label type = (Label) scene.lookup("#typeholder");
        Label time = (Label) scene.lookup("#timeholder");
        Label skillstate = (Label) scene.lookup("#lockstate");
        Label healthpoint = (Label) scene.lookup("#healthpoint");

        Boolean skill;
        String rawJSON = "";
        String formatRating;
        String songinfo;
        int rawRating;
        int diffcode;
        int clearcode;
        int health;
        long timestamp;
        Date playtime;
        LocalDateTime displaytime;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#.##");

        for (InfoStack info : infoStack) {
            if (info.getName().equals(lookupName)) {
                rawJSON = info.getRawjson();
                break;
            }
        }

        JSONObject j = new JSONObject(rawJSON);
        JSONArray r = j.getJSONArray("recent_score");
        rawRating = j.getInt("rating");
        formatRating = String.valueOf(rawRating).substring(0, 2) + "." + String.valueOf(rawRating).substring(2, 4);
        diffcode = r.getJSONObject(0).getInt("difficulty");
        songinfo = r.getJSONObject(0).getString("song_id");
        clearcode = r.getJSONObject(0).getInt("clear_type");
        timestamp = r.getJSONObject(0).getLong("time_played");
        playtime = new Date(timestamp);
        displaytime = playtime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        skill = j.getBoolean("is_skill_sealed");
        health = r.getJSONObject(0).getInt("health");

        healthbar.setProgress((double) health / 100.0);

        switch (diffcode) {
            case 0:
                songinfo = songinfo + " [PST]";
                break;
            case 1:
                songinfo = songinfo + " [PRS]";
                break;
            case 2:
                songinfo = songinfo + " [FTR]";
                break;
        }

        switch (clearcode) {
            case 0:
                type.setText("TRACK LOST");
                type.setTextFill(Color.RED);
                break;
            case 1:
                type.setText("TRACK COMPLETE");
                break;
            case 2:
                type.setText("FULL RECALL");
                type.setTextFill(Color.DEEPPINK);
                break;
            case 3:
                type.setText("PURE MEMORY");
                type.setTextFill(Color.ROYALBLUE);
                break;
        }

        skillstate.setVisible(skill);
        name.setText(lookupName);
        potential.setText(formatRating);
        songid.setText(songinfo);
        pure.setText(r.getJSONObject(0).getInt("perfect_count") + " (" + r.getJSONObject(0).getInt("shiny_perfect_count") + ")");
        far.setText(String.valueOf(r.getJSONObject(0).getInt("near_count")));
        lost.setText(String.valueOf(r.getJSONObject(0).getInt("miss_count")));
        bpotential.setText(df.format(r.getJSONObject(0).getDouble("rating")));
        time.setText("Played on " + dtf.format(displaytime));
        healthpoint.setText(health + "%");

        exitButton.setOnAction(event -> {
            primaryStage.close();
        });

        primaryStage.show();
    }

}
