package Chess;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainFrame.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Arcaea Chess Helper");
        primaryStage.setScene(scene);

        // Test packs.
        String[] testPack9 = {"Rise","Sayonara Hatsukoi","Fairytale","Lucifer","Snow White","Vexaria","Lost Civilization","qualia -ideaesthsia-","GOODTEK(Arcaea Edit)"};
        String[] testPack16 = {"Rise","Sayonara Hatsukoi","Fairytale","Lucifer","Snow White","Vexaria","Lost Civilization","qualia -ideaesthsia-","GOODTEK(Arcaea Edit)","Shades of Light in a Transcendent Realm","Babaroque","Dement ~after legend~","Dandelion","Anokumene","Infinity Heaven","Brand new world"};
        String[] testPack25 = {"Rise","Sayonara Hatsukoi","Fairytale","Lucifer","Snow White","Vexaria","Lost Civilization","qualia -ideaesthsia-","GOODTEK(Arcaea Edit)","Shades of Light in a Transcendent Realm","Babaroque","Dement ~after legend~","Dandelion","Anokumene","Infinity Heaven","Brand new world","Chronostasis","Kanagawa Cyber Culvert","Clotho and the stargazer","Ignotus","Harutopia ~Utopia of Spring~","Rabbit In The Black Room","Red and Blue","One Last Drive","Dreamin' Attraction!!"};
        String[] testPack36 = {"Rise","Sayonara Hatsukoi","Fairytale","Lucifer","Snow White","Vexaria","Lost Civilization","qualia -ideaesthsia-","GOODTEK(Arcaea Edit)","Shades of Light in a Transcendent Realm","Babaroque","Dement ~after legend~","Dandelion","Anokumene","Infinity Heaven","Brand new world","Chronostasis","Kanagawa Cyber Culvert","Clotho and the stargazer","Ignotus","Harutopia ~Utopia of Spring~","Rabbit In The Black Room","Red and Blue","One Last Drive","Dreamin' Attraction!!","MERLIN","OMAKENO Stroke","DX Choseinou Full Metal Shojo","Scarlet Lance","ouroboros -twin stroke of the end-","Paradise","Flashback","Flyburg and Endroll","Party Vinyl","Antithese","Corruption"};
        String[] testPack49 = {"Rise","Sayonara Hatsukoi","Fairytale","Lucifer","Snow White","Vexaria","Lost Civilization","qualia -ideaesthsia-","GOODTEK(Arcaea Edit)","Shades of Light in a Transcendent Realm","Babaroque","Dement ~after legend~","Dandelion","Anokumene","Infinity Heaven","Brand new world","Chronostasis","Kanagawa Cyber Culvert","Clotho and the stargazer","Ignotus","Harutopia ~Utopia of Spring~","Rabbit In The Black Room","Red and Blue","One Last Drive","Dreamin' Attraction!!","MERLIN","OMAKENO Stroke","DX Choseinou Full Metal Shojo","Scarlet Lance","ouroboros -twin stroke of the end-","Paradise","Flashback","Flyburg and Endroll","Party Vinyl","Antithese","Corruption","Hall of Mirrors","Hikari","Linear Accelerator","STAGER (ALL STAGE CLEAR)","Tiferet","Moonlight of Sand Castle","REconstruction","Evoltex(poppi'n mix)","Oracle","αterlβus","Surrender","Yosakura Fubuki","Garakuta Doll Play"};


        Slider mapsize = (Slider)scene.lookup("#mapsize");
        Label sizetext = (Label)scene.lookup("#sizetext");
        Label counter = (Label)scene.lookup("#counter");
        Label avglabel = (Label)scene.lookup("#avgdiff");
        TreeView songtree = (TreeView)scene.lookup("#songtree");
        Button addbutton = (Button)scene.lookup("#add");
        Button removebutton = (Button)scene.lookup("#remove");
        Button random = (Button)scene.lookup("#random");
        Button generate = (Button)scene.lookup("#generate");
        ListView selectedlist = (ListView)scene.lookup("#selected");
        SongTreeHelper sth = new SongTreeHelper();
        MapGenerator mg = new MapGenerator();
        ArrayList<Double> selecteddiff = new ArrayList<>();

        TreeItem songlist = new TreeItem("Songs");




        songlist.getChildren().addAll(sth.getSongs());

        songtree.setRoot(songlist);

        mapsize.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(!mapsize.isValueChanging()){
                    String now = String.valueOf((int)mapsize.getValue());
                    sizetext.setText(now + "x" + now);
                    checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                }
            }
        });

        addbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<String> selected = (TreeItem<String>) songtree.getSelectionModel().getSelectedItem();
                if(!songtree.getSelectionModel().isEmpty() && songtree.getSelectionModel().getSelectedIndex()>1){
                    String name = selected.getValue();
                    SongTreeHelper sth = new SongTreeHelper();
                    if(!Arrays.asList(sth.packs).contains(name)){
                        if(!selectedlist.getItems().contains(selected.getValue())){
                            int num = Integer.valueOf(counter.getAccessibleText());
                            selectedlist.getItems().add(selected.getValue());
                            num++;
                            counter.setAccessibleText(Integer.toString(num));
                            counter.setText("Song selected: " + num);
                            // System.out.println(songtree.getSelectionModel().getSelectedIndex());
                            selecteddiff.add(mg.getBasePotential(selected.getValue()));
                            checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                            updateAvg(avglabel, selecteddiff);
                        }
                    }
                }else{

                }
            }
        });

        removebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SelectionModel model = selectedlist.getSelectionModel();
                String selected = (String)selectedlist.getSelectionModel().getSelectedItem();
                if(selected!=null){
                    selectedlist.getItems().remove(model.getSelectedIndex());
                    System.out.println(model.getSelectedIndex());
                    int num = Integer.valueOf(counter.getAccessibleText());
                    num--;
                    counter.setAccessibleText(Integer.toString(num));
                    counter.setText("Song selected: " + num);
                    selecteddiff.remove(mg.getBasePotential(selected));
                    checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                    updateAvg(avglabel, selecteddiff);
                }
            }
        });

        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // mg.generateMapAny(testPack49, 49);
                if(checkCount(counter, Integer.toString(getDesiredSize(mapsize)))){
                    MapGenerator mg = new MapGenerator();
                    Object[] array = selectedlist.getItems().toArray();
                    String[] songs = Arrays.copyOf(array, array.length, String[].class);
                    mg.generateMapAny(songs, getDesiredSize(mapsize));
                }else{
                    showGenerateAlert();
                }
            }
        });

        random.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Random r = new Random();
                int a = r.nextInt(7);
                int b = r.nextInt(7);
                Alert g = new Alert(Alert.AlertType.INFORMATION);
                g.setContentText(a + " , " + b);
                g.show();
            }
        });

        primaryStage.show();
    }

    private boolean checkCount(Label counter, String target){
        if(counter.getAccessibleText().equals(target)){
            counter.setTextFill(Color.GREEN);
            return true;
        }else{
            counter.setTextFill(Color.RED);
            return false;
        }
    }

    private void updateAvg(Label avglabel, ArrayList<Double> difflist){
        if(difflist.size()!=0){
            Double sum = 0.0;
            Double avg = 0.0;
            String avgstr = "";
            for(Double diff : difflist){
                sum += diff;
            }
            avg = sum / difflist.size();
            avgstr = String.format("%.2f", avg);
            avglabel.setText("Average Base Potential: " + avgstr);
        }else{
            avglabel.setText("Average Base Potential: 0.00");
        }
    }

    private void showGenerateAlert(){
        Alert ne = new Alert(Alert.AlertType.ERROR);
        ne.setContentText("Incorrect number of songs selected!");
        ne.show();
    }

    private int getDesiredSize(Slider slider){
        int size = (int)slider.getValue();
        return size*size;
    }

    public double matchSong(String title){
        SongTreeHelper sth = new SongTreeHelper();
        ArrayList<String[]> list = sth.getSongList();
        for(String[] pack : list){
            try {
                int index = Arrays.asList(sth.songlistFreePack).indexOf(title);
                double diff = sth.songlistFreePackDiff[index];
                return diff;
            } catch (ArrayIndexOutOfBoundsException e){

            }
        }
        return 0.0;
    }
}
