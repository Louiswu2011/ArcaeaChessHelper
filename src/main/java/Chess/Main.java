package Chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main extends Application {
    final static String ver = "0.9";

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

        // Test Arcaea IDs
        String[] ids = {"892842548", "962264581", "123573477", "149193501", "499137943"};

        ToggleGroup exportOptionGroup = new ToggleGroup();
        RadioButton exportTXT = (RadioButton) scene.lookup("#txt");
        RadioButton exportXLS = (RadioButton) scene.lookup("#xls");
        Slider mapsize = (Slider)scene.lookup("#mapsize");
        ComboBox levelSelector = (ComboBox) scene.lookup("#level");
        Label version = (Label) scene.lookup("#version");
        Label sizetext = (Label)scene.lookup("#sizetext");
        Label counter = (Label)scene.lookup("#counter");
        Label avglabel = (Label)scene.lookup("#avgdiff");
        Label warning = (Label) scene.lookup("#warning");
        Label cstate = (Label) scene.lookup("#cstate");
        TreeView songtree = (TreeView)scene.lookup("#songtree");
        Button addbutton = (Button)scene.lookup("#add");
        Button removebutton = (Button)scene.lookup("#remove");
        Button random = (Button)scene.lookup("#random");
        Button generate = (Button)scene.lookup("#generate");
        Button quickgen = (Button) scene.lookup("#quickgen");
        Button troll = (Button) scene.lookup("#troll");
        Button probe = (Button) scene.lookup("#probe");
        Button addid = (Button) scene.lookup("#addid");
        Button clear = (Button) scene.lookup("#clear");
        Button cstart = (Button) scene.lookup("#cstart");
        TextField idfield = (TextField) scene.lookup("#idfield");
        TextField cfield = (TextField) scene.lookup("#cfield");
        ListView selectedlist = (ListView)scene.lookup("#selected");
        final ListView idlist = (ListView) scene.lookup("#idlist");
        final ListView message = (ListView) scene.lookup("#message");
        SongTreeHelper sth = new SongTreeHelper();
        MapGenerator mg = new MapGenerator();
        ArrayList<Double> selecteddiff = new ArrayList<>();

        TreeItem songlist = new TreeItem("Songs");

        ScoreProber s = new ScoreProber();
        // System.out.println(s.probeTest());
        /*for(String id : ids){
            System.out.println(s.probe(id));
        }*/
        /*ObservableList testlist = FXCollections.observableArrayList(Arrays.asList(ids));
        idlist.setItems(testlist);*/


        // Display version
        version.setText("Version " + ver + " by \"Galgame BOT\" LouiS");


        songlist.getChildren().addAll(sth.getSongs());
        songtree.setRoot(songlist);
        exportTXT.setToggleGroup(exportOptionGroup);
        exportXLS.setToggleGroup(exportOptionGroup);
        exportXLS.setSelected(true);
        counter.setAccessibleText("0");

        ObservableList<String> level = FXCollections.observableArrayList("All", "<9.0", "9.0-9.8", ">9.8");
        levelSelector.setItems(level);
        levelSelector.setValue("All");

        mapsize.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(!mapsize.isValueChanging()){
                    String now = String.valueOf((int)mapsize.getValue());
                    sizetext.setText(now + "x" + now);
                    checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                    if ((int) mapsize.getValue() >= 7) {
                        warning.setVisible(true);
                    } else {
                        warning.setVisible(false);
                    }
                }
            }
        });

        addbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem<String> selected = (TreeItem<String>) songtree.getSelectionModel().getSelectedItem();
                if (!songtree.getSelectionModel().isEmpty() && songtree.getSelectionModel().getSelectedIndex() > 1) {
                    String name = selected.getValue();
                    SongTreeHelper sth = new SongTreeHelper();
                    if (!Arrays.asList(sth.packs).contains(name)) {
                        if (!selectedlist.getItems().contains(selected.getValue())) {
                            int num = Integer.valueOf(counter.getAccessibleText());
                            selectedlist.getItems().add(selected.getValue());
                            num++;
                            counter.setAccessibleText(Integer.toString(num));
                            counter.setText("Song selected: " + num);
                            // System.out.println(songtree.getSelectionModel().getSelectedIndex());
                            selecteddiff.add(matchSong(selected.getValue()));
                            checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                            updateAvg(avglabel, selecteddiff);
                        }
                    }
                } else {

                }
            }
        });

        songtree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    TreeItem<String> selected = (TreeItem<String>) songtree.getSelectionModel().getSelectedItem();
                    if (!songtree.getSelectionModel().isEmpty() && songtree.getSelectionModel().getSelectedIndex() > 1) {
                        String name = selected.getValue();
                        SongTreeHelper sth = new SongTreeHelper();
                        if (!Arrays.asList(sth.packs).contains(name)) {
                            if (!selectedlist.getItems().contains(selected.getValue())) {
                                int num = Integer.valueOf(counter.getAccessibleText());
                                selectedlist.getItems().add(selected.getValue());
                                num++;
                                counter.setAccessibleText(Integer.toString(num));
                                counter.setText("Song selected: " + num);
                                // System.out.println(songtree.getSelectionModel().getSelectedIndex());
                                selecteddiff.add(matchSong(selected.getValue()));
                                checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                                updateAvg(avglabel, selecteddiff);
                            }
                        }
                    } else {

                    }
                }
            }
        });

        selectedlist.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    SelectionModel model = selectedlist.getSelectionModel();
                    String selected = (String) selectedlist.getSelectionModel().getSelectedItem();
                    if (selected != null) {
                        selectedlist.getItems().remove(model.getSelectedIndex());
                        System.out.println(model.getSelectedIndex());
                        int num = Integer.valueOf(counter.getAccessibleText());
                        num--;
                        counter.setAccessibleText(Integer.toString(num));
                        counter.setText("Song selected: " + num);
                        selecteddiff.remove(matchSong(selected));
                        checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                        updateAvg(avglabel, selecteddiff);
                    }
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
                    selecteddiff.remove(matchSong(selected));
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
                    mg.generateMapAny(songs, getDesiredSize(mapsize), getDesiredOutputMethod(exportTXT));
                }else{
                    showGenerateAlert();
                }
            }
        });

        random.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Random r = new Random();
                int a = r.nextInt(6) + 1;
                int b = r.nextInt(6) + 1;
                Alert g = new Alert(Alert.AlertType.INFORMATION);
                g.setContentText(a + " , " + b);
                g.show();
            }
        });

        quickgen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String[] rlist = sth.customGenerateSongList(getDesiredSize(mapsize), getDesiredDiff(levelSelector));
                if (rlist != null) {
                    mg.generateMapAny(rlist, getDesiredSize(mapsize), getDesiredOutputMethod(exportTXT));
                } else {

                }
            }
        });

        // SURPRISE!!
        troll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SongTreeHelper troll0 = new SongTreeHelper();
                MapGenerator troll3 = new MapGenerator();
                String[] troll2 = troll0.troll1(10000);
                troll3.generateMapAny(troll2, 10000, 0);
            }
        });

        addid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (idfield.getLength() == 9) {
                    if (!idlist.getItems().contains(idfield.getText())) {
                        String newid = idfield.getText();
                        idlist.getItems().add(newid);
                        idfield.setText("");
                    }
                }
            }
        });

        probe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<String> ids = idlist.getItems();
                for (String id : ids) {
                    Prober worker = new Prober(id, message);
                    worker.start();
                }
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                message.getItems().clear();
            }
        });

        idlist.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    SelectionModel model = idlist.getSelectionModel();
                    String selected = (String) idlist.getSelectionModel().getSelectedItem();
                    if (selected != null) {
                        idlist.getItems().remove(model.getSelectedIndex());
                    }
                }
            }
        });

        cstart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cstate.getText().equals("Running")) {
                    // Countdown running
                    cstate.setText("Cancelled");
                    cstate.setTextFill(Color.RED);
                    cstart.setDisable(true);
                } else {
                    // Countdown not running
                    try {
                        int target = Integer.parseInt(cfield.getText());
                        if (target > 600 || target < 1) {
                            throw new NullPointerException();
                        }
                        cstart.setText("Cancel");
                        cstate.setText("Running");
                        cstate.setTextFill(Color.GREEN);
                        cfield.setDisable(true);
                        Thread t = new Thread(new CountdownTimer(target, cfield, cstate));
                        t.start();
                        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                            @Override
                            public void uncaughtException(Thread t, Throwable e) {
                                if (e instanceof NullPointerException) {
                                    // Exit normally
                                    Platform.runLater(() -> {
                                        cstart.setText("Go");
                                        cstate.setText("Idle");
                                        cstate.setTextFill(Color.BLACK);
                                        cfield.setDisable(false);
                                        cfield.setText("");
                                    });
                                    cstart.setDisable(false);
                                    ObservableList<String> ids = idlist.getItems();
                                    for (String id : ids) {
                                        Prober worker = new Prober(id, message);
                                        worker.start();
                                    }
                                } else if (e instanceof NumberFormatException) {
                                    // User cancelling
                                    Platform.runLater(() -> {
                                        cstart.setText("Go");
                                        cstate.setText("Idle");
                                        cstate.setTextFill(Color.BLACK);
                                        cfield.setDisable(false);
                                        cfield.setText("");
                                    });
                                    cstart.setDisable(false);
                                }
                            }
                        });
                    } catch (NumberFormatException e) {
                        // Error: not integer
                        cfield.setText("");
                        Alert n = new Alert(Alert.AlertType.ERROR);
                        n.setContentText("Please enter a valid number!");
                        n.show();
                    } catch (NullPointerException e) {
                        // Error: target too big or is negative
                        cfield.setText("");
                        Alert n = new Alert(Alert.AlertType.ERROR);
                        n.setContentText("Pleas enter a valid value(1-600)!");
                        n.show();
                    }
                }
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
        ArrayList<SongTreeHelper.Pack> packs = sth.getSongPacks();
        for (SongTreeHelper.Pack pack : packs) {
            if (Arrays.stream(pack.getPackContent()).anyMatch(title::equals)) {
                int index = Arrays.asList(pack.getPackContent()).indexOf(title);
                if (index != -1) {
                    double[] diffinfo = pack.getDiffInfo();
                    return diffinfo[index];
                }
            }
        }
        return 0.0;
    }

    private int getDesiredOutputMethod(RadioButton txt) {
        if (txt.isSelected()) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getDesiredDiff(ComboBox cb) {
        String selected = (String) cb.getValue();
        switch (selected) {
            case "All":
                return 0;
            case "<9.0":
                return 1;
            case "9.0-9.8":
                return 2;
            case ">9.8":
                return 3;
            default:
                return -1;
        }
    }

}
