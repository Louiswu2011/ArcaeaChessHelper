package Chess;

import com.neovisionaries.ws.client.WebSocketException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/*
CANCELLED: PROTECT THE EXPORT BUTTON WHEN PROBING
TODO: [NEW FEATURE] LOAD / COLOR / SHRINK THE MAP [LONG TERM]
TODO: [NEW FEATURE] FINISH UP THE RANDOM TAP [LONG TERM]
FINISHED: [NEW FEATURE] CUSTOM COLOR POPUP
FINISHED: [NEW FEATURE] EXPORT THE LATEST "SCORE ONLY" DATA TO EXCEL
FINISHED: [BUG FIX] DOESN'T OVERWRITE THE MAP SHEET
FINISHED: [NEW FEATURE] OPEN EXCEL AUTOMATICALLY AFTER EXPORT
FINISHED: [NEW FEATURE] Export the scores each round
FINISHED: [NEW FEATURE] Add options to set the color of ids
TODO: GET MORE TODO TO DO
*/

/*
Changelog
- Quickgen: Added a new quick generate range (9.5-10.3)
- Quickgen: Only add free songs in range 9.0-9.8
- Quickgen: Add main story pack to range 9.0-9.8 pool
- Add search bar
- Prober revived
- Accuracy query
*/


public class Main extends Application {
    private final static String ver = "0.14";

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
        String[] ids = {"892842548", "962264581", "123573477", "149193501", "499137943", "074259709", "540375575", "787846847", "443927228", "283672910"};
        String[] idshort = {"892842548", "962264581", "123573477"};

        ArrayList<InfoStack> infoStacks = new ArrayList<>();
        AtomicReference<Boolean> isSingleProbing = new AtomicReference<>(false);
        ExcelOperation eo = new ExcelOperation();
        CustomColorPicker customColorPicker = new CustomColorPicker();

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
        Label accstatus = (Label) scene.lookup("#accstat");
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
        Button exportscore = (Button) scene.lookup("#scoreexport");
        Button test = (Button) scene.lookup("#test");
        Button accbutton = (Button) scene.lookup("#accbutton");
        TextField idfield = (TextField) scene.lookup("#idfield");
        TextField cfield = (TextField) scene.lookup("#cfield");
        TextField search = (TextField) scene.lookup("#search");
        TextField accfield = (TextField) scene.lookup("#accfield");
        ListView selectedlist = (ListView)scene.lookup("#selected");
        final ListView idlist = (ListView) scene.lookup("#idlist");
        final ListView message = (ListView) scene.lookup("#message");
        SongTreeHelper sth = new SongTreeHelper();
        MapGenerator mg = new MapGenerator();
        ArrayList<Double> selecteddiff = new ArrayList<>();

        TreeItem songlist = new TreeItem("Songs");

        ScoreProber s = new ScoreProber();
        // DEBUG ONLY: probe when startup
        // System.out.println(s.probeTest());
        /*for(String id : ids){
            System.out.println(s.probe(id));
        }*/

        // DEBUG ONLY: Fill the id list with template
        /*ObservableList testlist = FXCollections.observableArrayList(Arrays.asList(ids));
        idlist.setItems(testlist);*/

        // Display version
        version.setText("Version " + ver + " by \"Galgame BOT\" LouiS");

        // DEBUG ONLY: open the map window
        /*try{
            openMapWindow();
        } catch (IOException e){
            e.printStackTrace();
        }*/

        // DEBUG ONLY: open the detail window
        /*try{
            DetailController dc = new DetailController();
            dc.showDetail(null);
        }catch (IOException e){
            e.printStackTrace();
        }*/

        // DEBUG ONLY: write score to excel
        /*eo.writeCurrentScore(System.getProperty("user.dir") + "\\game.xlsx", infoStacks);*/

        test.setOnAction(event -> {

        });


        songlist.getChildren().addAll(sth.getSongs());
        songtree.setRoot(songlist);
        exportTXT.setToggleGroup(exportOptionGroup);
        exportXLS.setToggleGroup(exportOptionGroup);
        exportXLS.setSelected(true);
        counter.setAccessibleText("0");

        ObservableList<String> level = FXCollections.observableArrayList("All", "<9.0", "9.0-9.8", ">9.8", "9.5-10.3");
        levelSelector.setItems(level);
        levelSelector.setValue("All");


        final ContextMenu probeMenu = new ContextMenu();
        MenuItem menu_detail = new MenuItem("Detail...");
        MenuItem menu_copy = new MenuItem("Copy");
        menu_detail.setOnAction(event -> {
            try {
                DetailController dc = new DetailController();
                String lookupData = getCellFromSelected(message, message.getSelectionModel().getSelectedIndex()).getText();
                dc.showDetail(infoStacks, getUsernameFromCell(lookupData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menu_copy.setOnAction(event -> {
            String lookupName = getUsernameFromCell(getCellFromSelected(message, message.getSelectionModel().getSelectedIndex()).getText());
            String rawJSON = "";
            for (InfoStack info : infoStacks) {
                if (info.getName().equals(lookupName)) {
                    rawJSON = info.getRawjson();
                    break;
                }
            }
            JSONObject j = new JSONObject(rawJSON);
            String score = Integer.toString(j.getJSONArray("recent_score").getJSONObject(0).getInt("score")); // TODO: Change this
            StringSelection stringSelection = new StringSelection(score);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        probeMenu.getItems().addAll(menu_copy, menu_detail);
        message.setOnContextMenuRequested(event -> {
            if (getCellFromSelected(message, message.getSelectionModel().getSelectedIndex()) != null) {
                probeMenu.show(message, event.getScreenX(), event.getScreenY());
            }
        });
        message.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> probeMenu.hide());

        final ContextMenu cMenu = new ContextMenu();
        Menu menu_colors = new Menu("Color");
        MenuItem item_probe = new MenuItem("Probe");
        SeparatorMenuItem item_upperline = new SeparatorMenuItem();
        MenuItem item_kill = new MenuItem("Kill");
        MenuItem item_revive = new MenuItem("Revive");
        SeparatorMenuItem item_bottomline = new SeparatorMenuItem();
        MenuItem item_delete = new MenuItem("Delete");
        MenuItem item_red = new MenuItem("Red");
        MenuItem item_orange = new MenuItem("Orange");
        MenuItem item_blue = new MenuItem("Blue");
        MenuItem item_purple = new MenuItem("Purple");
        MenuItem item_yellow = new MenuItem("Yellow");
        MenuItem item_pink = new MenuItem("Pink");
        MenuItem item_green = new MenuItem("Green");
        MenuItem item_gray = new MenuItem("Gray");
        SeparatorMenuItem item_colorseperator = new SeparatorMenuItem();
        MenuItem item_custom = new MenuItem("Custom...");
        MenuItem item_colorclear = new MenuItem("Clear");
        item_probe.setOnAction(event -> {
            if (message.getItems() != null) {
                message.getItems().clear();
                infoStacks.clear();
            }
            isSingleProbing.set(true);
            // exportscore.setDisable(true);
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Prober p = new Prober(selected, message, getCellFromSelected(idlist, model.getSelectedIndex()).getTextFill(), infoStacks, 1, exportscore);
                p.start();
            }
        });
        item_delete.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                idlist.getItems().remove(model.getSelectedIndex());
            }
        });
        item_red.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.RED);
                cell.setAccessibleText("red");
            }
        });
        item_blue.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.ROYALBLUE);
                cell.setAccessibleText("blue");
            }
        });
        item_green.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.GREEN);
                cell.setAccessibleText("green");
            }
        });
        item_gray.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.GRAY);
                cell.setAccessibleText("gray");
            }
        });
        item_orange.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.ORANGE);
                cell.setAccessibleText("orange");
            }
        });
        item_pink.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.DEEPPINK);
                cell.setAccessibleText("pink");
            }
        });
        item_purple.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.PURPLE);
                cell.setAccessibleText("purple");
            }
        });
        item_yellow.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.YELLOW);
                cell.setAccessibleText("yellow");
            }
        });
        item_custom.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                customColorPicker.open(cell);
            }
        });
        item_colorclear.setOnAction(event -> {
            SelectionModel model = idlist.getSelectionModel();
            String selected = (String) idlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int index = model.getSelectedIndex();
                Cell cell = getCellFromSelected(idlist, index);
                cell.setTextFill(Color.BLACK);
                cell.setAccessibleText("black");
            }
        });
        item_kill.setOnAction(event -> {
            infoStacks.get(idlist.getSelectionModel().getSelectedIndex()).setLive(false);
        });
        item_revive.setOnAction(event -> {
            infoStacks.get(idlist.getSelectionModel().getSelectedIndex()).setLive(true);
        });
        menu_colors.getItems().addAll(item_red, item_blue, item_green, item_gray, item_orange, item_pink, item_purple, item_yellow, item_colorseperator, item_custom, item_colorclear);
        cMenu.getItems().addAll(item_probe, item_delete, item_upperline, item_kill, item_revive, item_bottomline, menu_colors);

        idlist.setOnContextMenuRequested(event -> {
            if (getCellFromSelected(idlist, idlist.getSelectionModel().getSelectedIndex()) != null) {
                if (infoStacks.size() == 0 || isSingleProbing.get()) {
                    item_kill.setDisable(true);
                    item_revive.setDisable(true);
                } else if (infoStacks.get(idlist.getSelectionModel().getSelectedIndex()).isLive()) {
                    item_kill.setDisable(false);
                    item_revive.setDisable(true);
                } else {
                    item_kill.setDisable(true);
                    item_revive.setDisable(false);
                }
                cMenu.show(idlist, event.getScreenX(), event.getScreenY());
            }
        });

        idlist.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> cMenu.hide());

        idfield.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addid.fire();
            }
        });

        idlist.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                item_delete.fire();
            }
        });

        search.setOnKeyReleased(event -> {
            if (search.getText().equals("")) {
                System.out.println("Search cancelled.");
                songtree.setRoot(songlist);
            } else {
                songtree.setRoot(null);
                String keyword = search.getText();
                TreeItem<String> result = new TreeItem("Result");
                for (String[] listarray : sth.getSongList()) {
                    for (String song : listarray) {
                        if (song.contains(keyword)) {
                            result.getChildren().add(new TreeItem<>(song));
                            System.out.println(song);
                        }
                    }
                }
                songtree.setRoot(result);
            }
        });

        exportscore.setOnAction(event -> {
            if (isSingleProbing.get()) {
                Alert singleProbed = new Alert(Alert.AlertType.ERROR);
                singleProbed.setContentText("Please do a full probe before exporting!");
                singleProbed.show();
            } else if (!infoStacks.isEmpty()) {
                eo.writeCurrentScore(System.getProperty("user.dir") + "\\game.xlsx", infoStacks);
            } else {
                Alert singleProbed = new Alert(Alert.AlertType.ERROR);
                singleProbed.setContentText("Please do a full probe before exporting!");
                singleProbed.show();
            }
        });

        mapsize.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!mapsize.isValueChanging()) {
                String now = String.valueOf((int) mapsize.getValue());
                sizetext.setText(now + "x" + now);
                checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                if ((int) mapsize.getValue() >= 7) {
                    warning.setVisible(true);
                } else {
                    warning.setVisible(false);
                }
            }
        });

        addbutton.setOnAction(event -> {
            TreeItem<String> selected = (TreeItem<String>) songtree.getSelectionModel().getSelectedItem();
            if (!songtree.getSelectionModel().isEmpty() && songtree.getSelectionModel().getSelectedIndex() > 1) {
                String name = selected.getValue();
                SongTreeHelper sth1 = new SongTreeHelper();
                if (!Arrays.asList(sth1.packs).contains(name)) {
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
        });

        songtree.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<String> selected = (TreeItem<String>) songtree.getSelectionModel().getSelectedItem();
                if (!songtree.getSelectionModel().isEmpty() && songtree.getSelectionModel().getSelectedIndex() > 1) {
                    String name = selected.getValue();
                    SongTreeHelper sth12 = new SongTreeHelper();
                    if (!Arrays.asList(sth12.packs).contains(name)) {
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

        selectedlist.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                SelectionModel model = selectedlist.getSelectionModel();
                String selected = (String) selectedlist.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selectedlist.getItems().remove(model.getSelectedIndex());
                    System.out.println(model.getSelectedIndex());
                    int num = Integer.parseInt(counter.getAccessibleText());
                    num--;
                    counter.setAccessibleText(Integer.toString(num));
                    counter.setText("Song selected: " + num);
                    selecteddiff.remove(matchSong(selected));
                    checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                    updateAvg(avglabel, selecteddiff);
                }
            }
        });

        removebutton.setOnAction(event -> {
            SelectionModel model = selectedlist.getSelectionModel();
            String selected = (String) selectedlist.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selectedlist.getItems().remove(model.getSelectedIndex());
                System.out.println(model.getSelectedIndex());
                int num = Integer.parseInt(counter.getAccessibleText());
                num--;
                counter.setAccessibleText(Integer.toString(num));
                counter.setText("Song selected: " + num);
                selecteddiff.remove(matchSong(selected));
                checkCount(counter, Integer.toString(getDesiredSize(mapsize)));
                updateAvg(avglabel, selecteddiff);
            }
        });

        generate.setOnAction(event -> {
            // mg.generateMapAny(testPack49, 49);
            if (checkCount(counter, Integer.toString(getDesiredSize(mapsize)))) {
                MapGenerator mg1 = new MapGenerator();
                Object[] array = selectedlist.getItems().toArray();
                String[] songs = Arrays.copyOf(array, array.length, String[].class);
                mg1.generateMapAny(songs, getDesiredSize(mapsize), getDesiredOutputMethod(exportTXT));
            } else {
                showGenerateAlert();
            }
        });

        random.setOnAction(event -> {
            Random r = new Random();
            int a = r.nextInt(6) + 1;
            int b = r.nextInt(6) + 1;
            Alert g = new Alert(Alert.AlertType.INFORMATION);
            g.setContentText(a + " , " + b);
            g.show();
        });

        quickgen.setOnAction(event -> {
            String[] rlist = sth.customGenerateSongList(getDesiredSize(mapsize), getDesiredDiff(levelSelector));
            if (rlist != null) {
                mg.generateMapAny(rlist, getDesiredSize(mapsize), getDesiredOutputMethod(exportTXT));
            }
        });

        // SURPRISE!!
        troll.setOnAction(event -> {
            SongTreeHelper troll0 = new SongTreeHelper();
            MapGenerator troll3 = new MapGenerator();
            String[] troll2 = troll0.troll1(10000);
            troll3.generateMapAny(troll2, 10000, 0);
        });

        addid.setOnAction(event -> {
            if (idfield.getLength() == 9) {
                if (!idlist.getItems().contains(idfield.getText())) {
                    String newid = idfield.getText();
                    idlist.getItems().add(newid);
                    idfield.setText("");
                }
            }
        });

        probe.setOnAction(event -> {
            // exportscore.setDisable(true);
            if (message.getItems() != null) {
                message.getItems().clear();
                infoStacks.clear();
            }
            isSingleProbing.set(false);
            int iterIndex = 0;
            ObservableList<String> ids1 = idlist.getItems();
            for (String id : ids1) {
                Prober worker = new Prober(id, message, getCellFromSelected(idlist, iterIndex).getTextFill(), infoStacks, ids1.size(), exportscore);
                worker.start();
                iterIndex++;
            }
            iterIndex = 0;
        });

        clear.setOnAction(event -> {
            message.getItems().clear();
            infoStacks.clear();
        });

        idlist.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                SelectionModel model = idlist.getSelectionModel();
                String selected = (String) idlist.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    idlist.getItems().remove(model.getSelectedIndex());
                }
            }
        });

        cstart.setOnAction(event -> {
            if (cstate.getText().equals("Running")) {
                // Countdown running
                cstate.setText("Cancelled");
                cstate.setTextFill(Color.RED);
                cstart.setDisable(true);
            } else {
                // Countdown not running
                try {
                    if (message.getItems() != null) {
                        message.getItems().clear();
                        infoStacks.clear();
                    }
                    // exportscore.setDisable(true);
                    int target = Integer.parseInt(cfield.getText());
                    if (target > 600 || target < 1) {
                        throw new NullPointerException();
                    }
                    isSingleProbing.set(false);
                    cstart.setText("Cancel");
                    cstate.setText("Running");
                    cstate.setTextFill(Color.GREEN);
                    cfield.setDisable(true);
                    Thread t = new Thread(new CountdownTimer(target, cfield, cstate));
                    t.start();
                    t.setUncaughtExceptionHandler((t1, e) -> {
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
                            ObservableList<String> ids12 = idlist.getItems();
                            int iterIndex = 0;
                            for (String id : ids12) {
                                Prober worker = new Prober(id, message, getCellFromSelected(idlist, iterIndex).getTextFill(), infoStacks, ids12.size(), exportscore);
                                worker.start();
                                iterIndex++;
                            }
                            iterIndex = 0;
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
        });

        accbutton.setOnAction(event -> {
            try {
                accstatus.setText("Probing...");
                ProbeClient probeClient = new ProbeClient();
                Future<String> futureAcc = probeClient.getUserAcc(accfield.getText());
                while (!futureAcc.isDone()) {
                    // Do nothing
                }
                Thread.sleep(100);
                String result = futureAcc.get();
                String[] r = result.split("/");
                Double shiny = Double.parseDouble(r[0]);
                Double perfect = Double.parseDouble(r[1]);
                accstatus.setText(new DecimalFormat("##.##").format((shiny / perfect * 100)) + "%" + " @ " + r[2] + " songs");
                System.out.println(result);
            } catch (WebSocketException | IOException | InterruptedException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                // invalid id
                accstatus.setText("Invalid id!");
                accstatus.setTextFill(Color.RED);
                Alert invalid = new Alert(Alert.AlertType.ERROR);
                invalid.setContentText("Invalid Arcaea id!");
                invalid.show();
                accstatus.setText("Idle");
                accstatus.setTextFill(Color.BLACK);
            } catch (ExecutionException e) {
                e.printStackTrace();
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
            case "9.5-10.3":
                return 4;
            default:
                return -1;
        }
    }

    private Cell getCellFromSelected(ListView lv, int index) {
        try {
            Object[] cells = lv.lookupAll(".cell").toArray();
            return (Cell) cells[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private void openMapWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mapFrame.fxml"));
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Map View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getUsernameFromCell(String lookupData) {
        int endingPos = 0;
        int nowPos = 0;
        for (char i : lookupData.toCharArray()) {
            if (i == ']') {
                endingPos = nowPos;
                break;
            }
            nowPos++;
        }
        return lookupData.substring(1, endingPos);
    }

}
