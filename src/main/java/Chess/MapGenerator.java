package Chess;

import javafx.scene.control.Alert;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MapGenerator {
    /*public void generateMap4(String[] songs){
        // Initialize the variables
        ArrayList<Song> songlist = new ArrayList<>();
        SongTreeHelper sth = new SongTreeHelper();
        ArrayList<Double> difflist = new ArrayList<>();

        // Find the corresponding base potential
        int k = 0;
        for(String each : songs){
            try{
                int index = Arrays.asList(sth.songlistFreePack).indexOf(each);
                double diff = sth.songlistFreePackDiff[index];
                difflist.add(diff);
            } catch (ArrayIndexOutOfBoundsException e){
                try {
                    int index = Arrays.asList(sth.songlistVLPack).indexOf(each);
                    double diff = sth.songlistVLPackDiff[index];
                    difflist.add(diff);
                } catch (ArrayIndexOutOfBoundsException e2){
                    try{
                        int index = Arrays.asList(sth.songlistLSPack).indexOf(each);
                        double diff = sth.songlistLSPackDiff[index];
                        difflist.add(diff);
                    }catch(ArrayIndexOutOfBoundsException e3){
                        int index = Arrays.asList(sth.songlistECPack).indexOf(each);
                        double diff = sth.songlistECPackDiff[index];
                        difflist.add(diff);
                    }
                }
            }
            Song i = new Song(each, difflist.get(k));
            songlist.add(i);
            k++;
        }

        // Sort the songs by their base potential
        ArrayList<Song> sortedsonglist = new ArrayList<>();
        Collections.sort(difflist);
        for(int i = 0; i < difflist.size(); i++){
            for(int j = 0; j < songlist.size(); j++){
                if(difflist.get(i) == songlist.get(j).getBasePotential()){
                    if(songlist.get(j).getSongTitle() != "null"){
                        sortedsonglist.add(new Song(songlist.get(j).getSongTitle(), difflist.get(i)));
                        difflist.set(i, 1000.0);
                        songlist.set(j, new Song("null", 1000.0));
                    }
                }
            }
        }

        // Calculate the position of each song
            // Create a 4x4 map
        String[][] map = new String[4][4];
            // Split the songs into 4/8/4 group
        ArrayList<Song> g1 = new ArrayList<>();
        ArrayList<Song> g2 = new ArrayList<>();
        ArrayList<Song> g3 = new ArrayList<>();
        for(int m = 0; m < sortedsonglist.size(); m++){
            if(m <4){
                g1.add(sortedsonglist.get(m));
            }else if (m >=4 && m <12){
                g2.add(sortedsonglist.get(m));
            }else{
                g3.add(sortedsonglist.get(m));
            }
        }
            // Randomize the song list
        Collections.shuffle(g1);
        Collections.shuffle(g2);
        Collections.shuffle(g3);
            // Fill the map
                // Fill the corner
        map[0][0] = g1.get(0).getSongTitle();
        map[0][3] = g1.get(1).getSongTitle();
        map[3][0] = g1.get(2).getSongTitle();
        map[3][3] = g1.get(3).getSongTitle();
                // Fill the outer line
        map[0][1] = g2.get(0).getSongTitle();
        map[0][2] = g2.get(1).getSongTitle();
        map[1][0] = g2.get(2).getSongTitle();
        map[1][3] = g2.get(3).getSongTitle();
        map[2][0] = g2.get(4).getSongTitle();
        map[2][3] = g2.get(5).getSongTitle();
        map[3][1] = g2.get(6).getSongTitle();
        map[3][2] = g2.get(7).getSongTitle();
                // Fill the inner core
        map[1][1] = g3.get(0).getSongTitle();
        map[1][2] = g3.get(1).getSongTitle();
        map[2][1] = g3.get(2).getSongTitle();
        map[2][2] = g3.get(3).getSongTitle();

        // Print out the final result to the command line
        for(int i = 0; i <=3; i++){
            for(int j = 0; j <=3; j++){
                if(j == 3){
                    System.out.print("[" + map[i][j] + "]");
                    System.out.print("\n");
                }else{
                    System.out.print("[" + map[i][j] + "]  ");
                }
            }
        }

        // Print out the map to txt file
        File txt = new File(System.getProperty("user.dir") + "\\map.txt");
        BufferedWriter bw = null;
        try {
            if(!txt.exists()){
                txt.createNewFile();
            }
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("Successfully generated map!");
            FileWriter fw = new FileWriter(txt);
            bw = new BufferedWriter(fw);
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    if (j == 3) {
                        bw.write("[" + map[i][j] + "]");
                        bw.write("\r\n");
                    } else {
                        bw.write("[" + map[i][j] + "]  ");
                    }
                }
            }
            success.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert ioerror = new Alert(Alert.AlertType.ERROR);
            ioerror.setContentText("Error creating text file!");
            ioerror.show();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert closeerror = new Alert(Alert.AlertType.ERROR);
                closeerror.setContentText("Error closing the BufferedWriter!");
                closeerror.show();
            }
        }

        // Test only
        // System.out.println(g1 + "/" + g2 + "/" + g3);
    }*/

    public void generateMapAny(String[] songs, int size, int outputMethod) { // 0 is excel file, 1 is txt file
        // Check the number of songs
        if(size==songs.length){
            // Create a map
            int bound = (int)Math.sqrt(size);
            String[][] map = new String[bound][bound];
            // Get the base potential and store to Song list
            System.out.println("List of songs:");
            ArrayList<Double> difflist = new ArrayList<>();
            ArrayList<Song> songlist = new ArrayList<>();
            Main mm = new Main();
            for(String song : songs){
                Double bp = mm.matchSong(song);
                songlist.add(new Song(song, bp));
                difflist.add(bp);
                System.out.println(song + " " + bp);
            }
            // Sort up the base potential list
            Collections.sort(difflist);
            ArrayList<Song> sortedsonglist = new ArrayList<>();
            for(int i = 0; i < difflist.size(); i++){
                for(int j = 0; j < songlist.size(); j++){
                    if(difflist.get(i) == songlist.get(j).getBasePotential()){
                        if(songlist.get(j).getSongTitle() != "null"){
                            sortedsonglist.add(new Song(songlist.get(j).getSongTitle(), difflist.get(i)));
                            difflist.set(i, 1000.0);
                            songlist.set(j, new Song("null", 1000.0));
                        }
                    }
                }
            }
            // Fill the map
                // Split up the map
            /*
            Splitting Method
            ...
            4x4 -> Outer: 4+3+3+2 Inner: 2+1+1+0 Core: 0+0+0+0 as 12/4
            5x5 -> Outer: 5+4+4+3 Inner: 3+2+2+1 Core: 1+0+0+0 as 16/8/1
            6x6 -> Outer: 6+5+5+4 Inner: 4+3+3+2 Core: 2+1+1+0 as 20/12/4
            ...
             */
            int pos = 0;
            int group = (int)Math.ceil(Math.sqrt(size) / 2);
            ArrayList<Integer> groupCount = new ArrayList<>();
            int n = group;  // Group count
            int m = (int)Math.sqrt(size);  // Group base
            while(n > 0){
                int result = 4*m-4;
                if(result == 0){
                    groupCount.add(1);
                }else{
                    groupCount.add(result);
                }
                n-=1;
                m-=2;
            }
                // Split groups into corner and line
            /*
            Splitting Method
            ...
            4x4 -> 12/4 -> 4/8//4
            5x5 -> 16/8/1 -> 4/12//4/4//1
            6x6 -> 20/12/4 -> 4/16//4/8//4
            ...
             */
            ArrayList<ArrayList<Song>> arrangedlist = new ArrayList<>();
            for(int gn : groupCount){
                if(gn == 4){
                    ArrayList<Song> g4 = new ArrayList<>();
                    g4.add(sortedsonglist.get(pos));
                    g4.add(sortedsonglist.get(pos+1));
                    g4.add(sortedsonglist.get(pos+2));
                    g4.add(sortedsonglist.get(pos+3));
                    arrangedlist.add(g4);
                }else if(gn == 1){
                    ArrayList<Song> g1 = new ArrayList<>();
                    g1.add(sortedsonglist.get(pos));
                    arrangedlist.add(g1);
                }else{
                    ArrayList<Song> front = new ArrayList<>();
                    ArrayList<Song> back = new ArrayList<>();
                    for(int i = 0; i<4; i++){
                        front.add(sortedsonglist.get(pos));
                        pos++;
                    }
                    for(int j = 0; j<gn-4; j++){
                        back.add(sortedsonglist.get(pos));
                        pos++;
                    }
                    arrangedlist.add(front);
                    arrangedlist.add(back);
                }
            }
                // Shuffle the groups
            for(ArrayList<Song> shufflegroup : arrangedlist){
                Collections.shuffle(shufflegroup);
            }
                // Fill the map
            int mappos = 0;
            int currentlayer = 0;
            int currentlayerbound = (int)Math.sqrt(size) - 1;
            int linechunksize = 0;
            int groupcount = 1;
            for(ArrayList<Song> sgroup : arrangedlist){
                int mid = (int)Math.ceil(Math.sqrt(size) / 2) - 1;
                if(checkOdd(groupcount)){
                    // Fill the corner
                    // If only one remained then fill the center block
                    if(sgroup.size() == 1){
                        map[mid][mid] = sgroup.get(0).getSongTitle();
                    }else{
                        map[currentlayer][currentlayer] = sgroup.get(0).getSongTitle();
                        map[currentlayer][currentlayerbound] = sgroup.get(1).getSongTitle();
                        map[currentlayerbound][currentlayer] = sgroup.get(2).getSongTitle();
                        map[currentlayerbound][currentlayerbound] = sgroup.get(3).getSongTitle();
                    }
                }else{
                    // Fill the line
                    // After filling the line, push in one layer.
                    // If only chunk size = 1 then fill the four block manually
                    linechunksize = sgroup.size() / 4;
                    if(linechunksize != 1){
                        int groupPos = 0;
                        // Fill upper row
                        for (int i = currentlayer; i < currentlayerbound - 1; i++) {
                            map[currentlayer][i + 1] = sgroup.get(groupPos).getSongTitle();
                            groupPos++;
                        }
                        // Fill bottom row
                        for (int j = currentlayer; j < currentlayerbound - 1; j++) {
                            map[currentlayerbound][j + 1] = sgroup.get(groupPos).getSongTitle();
                            groupPos++;
                        }
                        // Fill left line
                        for (int k = currentlayer; k < currentlayerbound - 1; k++) {
                            map[k + 1][currentlayer] = sgroup.get(groupPos).getSongTitle();
                            groupPos++;
                        }
                        // Fill right line
                        for (int l = currentlayer; l < currentlayerbound - 1; l++) {
                            map[l + 1][currentlayerbound] = sgroup.get(groupPos).getSongTitle();
                            groupPos++;
                        }
                        currentlayerbound--;
                        currentlayer++;
                    }else{
                        map[mid][mid-1] = sgroup.get(0).getSongTitle();
                        map[mid][mid+1] = sgroup.get(1).getSongTitle();
                        map[mid-1][mid] = sgroup.get(2).getSongTitle();
                        map[mid+1][mid] = sgroup.get(3).getSongTitle();
                    }
                }
                groupcount++;
            }

            if (outputMethod == 0) {
                // Print out the map to Excel file
                ExcelOperation eo = new ExcelOperation();
                eo.writeExcelFile(System.getProperty("user.dir") + "\\map.xlsx", map, size);
                // Open the Excel file
                try {
                    Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "\\map.xlsx"));
                } catch (IOException e) {
                    Alert failed = new Alert(Alert.AlertType.ERROR);
                    failed.setContentText("Failed to open Excel!");
                    failed.show();
                    e.printStackTrace();
                }
            } else {
                // Print out the map to txt file
                File txt = new File(System.getProperty("user.dir") + "\\map.txt");
                BufferedWriter bw = null;
                try {
                    if (!txt.exists()) {
                        txt.createNewFile();
                    }
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    FileWriter fw = new FileWriter(txt);
                    bw = new BufferedWriter(fw);
                    for (int i = 0; i <= bound - 1; i++) {
                        for (int j = 0; j <= bound - 1; j++) {
                            if (j == bound - 1) {
                                bw.write("[" + map[i][j] + "]");
                                bw.write("\r\n");
                            } else {
                                bw.write("[" + map[i][j] + "]  ");
                            }
                        }
                    }
                    success.setContentText("Successfully generated map!");
                    success.showAndWait();
                    Process p = Runtime.getRuntime().exec("notepad " + System.getProperty("user.dir") + "\\map.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert ioerror = new Alert(Alert.AlertType.ERROR);
                    ioerror.setContentText("Error creating text file!");
                    ioerror.show();
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Alert closeerror = new Alert(Alert.AlertType.ERROR);
                        closeerror.setContentText("Error closing the BufferedWriter!");
                        closeerror.show();
                    }
                }
            }


            // Test Only
            /*for (int i = 0; i <= 4; i++) {
                for (int j = 0; j <= 4; j++) {
                    if (j == 4) {
                        System.out.print("[" + map[i][j] + "]");
                        System.out.print("\n");
                    } else {
                        System.out.print("[" + map[i][j] + "]  ");
                    }
                }
            }*/

        }
    }

    public class Song {
        String songTitle;
        double basePotential;

        public Song(String title, double potential){
            this.songTitle = title;
            this.basePotential = potential;
        }

        public String getSongTitle() {
            return songTitle;
        }

        public void setSongTitle(String songTitle) {
            this.songTitle = songTitle;
        }

        public double getBasePotential() {
            return basePotential;
        }

        public void setBasePotential(double basePotential) {
            this.basePotential = basePotential;
        }
    }


    private boolean checkOdd(int num){
        if(num % 2 ==0){
            return false;
        }else{
            return true;
        }
    }

}
