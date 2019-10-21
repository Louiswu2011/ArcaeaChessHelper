package Chess;

import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class SongTreeHelper {
    String[] packs = {"Memory Archive", "Arcaea", "Sunset Radiance", "Adverse Prelude", "Vicious Labyrinth", "Luminous Sky", "Eternal Core", "Absolute Reason", "Binary Enfold", "Ambivalent Vision", "Crimson Solace", "CHUNITHM", "Groove Coaster", "Tone Sphere", "Lanota", "Dynamix", "Stellights"};

    String[] songlistFreePack = {"Rise", "Sayonara Hatsukoi", "Fairytale", "Lucifer", "Snow White", "Vexaria", "Lost Civilization", "qualia -ideaesthsia-", "GOODTEK(Arcaea Edit)", "Shades of Light in a Transcendent Realm", "Babaroque", "Dement ~after legend~", "Dandelion", "Anokumene", "Infinity Heaven", "Brand new world", "Chronostasis", "Kanagawa Cyber Culvert", "Clotho and the stargazer", "Ignotus", "Harutopia ~Utopia of Spring~", "Rabbit In The Black Room", "Red and Blue", "One Last Drive", "Dreamin' Attraction!!", "Syro", "Reinvent", "Blaster", "Cybernecia Catharsis", "inkar-usi", "Illegal Paradise", "Bookmaker (2D Version)", "Suomi", "Nhelv", "LunarOrbit -believe in the Espebranch road-", "Purgatorium", "Rugie", "ReviXy", "Grimheart", "SUPERNOVA", "VECTOR", "Diode", "FREEF4LL"};
    double[] songlistFreePackDiff = {7.7, 6.0, 7.2, 8.3, 8.6, 7.4, 9.5, 9.2, 9.3, 8.4, 8.7, 7.9, 8.6, 9.2, 7.6, 7.9, 9.1, 9.0, 7.5, 9.7, 8.6, 8.5, 9.7, 8.0, 9.8, 9.5, 8.6, 9.5, 9.8, 7.8, 9.8, 8.3, 7.7, 9.8, 9.4, 8.4, 9.0, 8.9, 8.6, 9.7, 9.5, 8.2, 8.7};

    String[] songlistVLPack = {"SOUNDWiTCH", "Iconoclast", "trappola bewitching", "conflict", "Axium Crisis", "Grievous Lady"};
    double[] songlistVLPackDiff = {9.9, 9.4, 10.0, 10.2, 10.6, 11.2};

    String[] songlistLSPack = {"The Message", "Sulfur", "Maze No.9", "Halcyon", "Ether Strike", "Fracture Ray"};
    double[] songlistLSPackDiff = {9.7, 9.8, 8.9, 10.7, 10.2, 11.1};

    String[] songlistECPack = {"cry of viyella", "I've heard it said", "Relentless", "memoryfactory.lzh", "Essence of Twilight", "Lumia", "Sheriruth", "PRAGMATISM", "Solitary Dream"};
    double[] songlistECPackDiff = {8.7, 8.2, 8.1, 8.8, 9.0, 8.5, 10.6, 10.1, 8.4};

    String[] songlistARPack = {"Antithese", "Corruption", "Black Territory", "Vicious Heroism", "Cyaegha"};
    double[] songlistARPackDiff = {8.8, 9.7, 9.5, 10.0, 10.8};

    String[] songlistBEPack = {"Memory Forest", "next to you", "Silent Rush", "Strongholds", "Singularity"};
    double[] songlistBEPackDiff = {9.7, 8.7, 8.4, 9.1, 10.4};

    String[] songlistAVPack = {"Blossoms", "Romance Wars", "Moonheart", "Lethaeus", "Genesis"};
    double[] songlistAVPackDiff = {7.6, 7.4, 8.5, 9.5, 8.4};

    String[] songlistAPPack = {"Heavensdoor", "Particle Arts", "Ringed Genesis", "Vindication"};
    double[] songlistAPPackDiff = {10.0, 8.8, 10.7, 9.6};

    String[] songlistCSPack = {"Paradise", "Flashback", "Flyburg and Endroll", "Party Vinyl", "Nirv lucE", "GLORY: ROAD"};
    double[] songlistCSPackDiff = {7.8, 8.5, 9.2, 9.8, 10.3, 10.5};

    String[] songlistCHUNITHMPack = {"Garakuta Doll Play", "Ikazuchi", "World Vanquisher"};
    double[] songlistCHUNITHMPackDiff = {10.3, 10.4, 10.6};

    String[] songlistGCPack = {"MERLIN", "OMAKENO Stroke", "DX Choseinou Full Metal Shojo", "Scarlet Lance", "ouroboros -twin stroke of the end-"};
    double[] songlistGCPackDiff = {8.5, 9.6, 9.7, 10.3, 10.6};

    String[] songlistTSPack = {"Hall of Mirrors", "Hikari", "Linear Accelerator", "STAGER (ALL STAGE CLEAR)", "Tiferet"};
    double[] songlistTSPackDiff = {8.2, 8.3, 9.6, 9.2, 10.5};

    String[] songlistLAPack = {"Dream goes on", "Journey", "Specta", "cyanine", "Quon"};
    double[] songlistLAPackDiff = {7.5, 8.6, 9.5, 10.5, 9.7};

    String[] songlistSPack = {"Surrender", "Yosakura Fubuki"};
    double[] songlistSPackDiff = {8.8, 9.6};

    String[] songlistDYPack = {"Moonlight of Sand Castle", "REconstruction", "Evoltex(poppi'n mix)", "Oracle", "αterlβus"};
    double[] songlistDYPackDiff = {7.7, 8.7, 8.9, 9.2, 10.5};

    String[] songlistSRPack = {"Chelsea", "AI[UE]OON", "A Wandering Melody of Love", "Tie me down gently", "Valhalla:0"};
    double[] songlistSRPackDiff = {8.6, 9.4, 9.6, 8.4, 10.5};

    String[] songlistMAPack = {"Mirzam", "DataErr0r", "CROSS the SOUL", "Your voice so... feat. Such", "Impure Bird", "Auxesia", "Modelista", "Metallic Punisher", "carmine:scythe", "γuarδina", "Be There", "Call My Name feat. Yukacco", "Fallensquare", "dropdead(prs)", "Alexandrite", "Astral tale", "Phantasia", "Empire of Winter", "Libertas", "Dot to Dot feat. shully", "Dreadnought"};
    double[] songlistMAPackDiff = {9.9, 9.5, 9.6, 9.4, 9.5, 9.4, 10.0, 10.3, 9.6, 10.5, 9.5, 8.8, 9.8, 9.1, 9.9, 9.4, 9.2, 9.0, 9.2, 8.7, 9.7};

    public ArrayList<Pack> getSongPacks() {
        ArrayList<Pack> songPacks = new ArrayList<>();
        songPacks.add(new Pack(songlistECPack, songlistECPackDiff));
        songPacks.add(new Pack(songlistFreePack, songlistFreePackDiff));
        songPacks.add(new Pack(songlistLSPack, songlistLSPackDiff));
        songPacks.add(new Pack(songlistVLPack, songlistVLPackDiff));
        songPacks.add(new Pack(songlistARPack, songlistARPackDiff));
        songPacks.add(new Pack(songlistAVPack, songlistAVPackDiff));
        songPacks.add(new Pack(songlistBEPack, songlistBEPackDiff));
        songPacks.add(new Pack(songlistCHUNITHMPack, songlistCHUNITHMPackDiff));
        songPacks.add(new Pack(songlistCSPack, songlistCSPackDiff));
        songPacks.add(new Pack(songlistDYPack, songlistDYPackDiff));
        songPacks.add(new Pack(songlistGCPack, songlistGCPackDiff));
        songPacks.add(new Pack(songlistLAPack, songlistLAPackDiff));
        songPacks.add(new Pack(songlistSPack, songlistSPackDiff));
        songPacks.add(new Pack(songlistTSPack, songlistTSPackDiff));
        songPacks.add(new Pack(songlistAPPack, songlistAPPackDiff));
        songPacks.add(new Pack(songlistSRPack, songlistSRPackDiff));
        songPacks.add(new Pack(songlistMAPack, songlistMAPackDiff));
        return songPacks;
    }

    public ArrayList<String[]> getSongList() {
        ArrayList<String[]> list = new ArrayList<>();
        list.add(songlistECPack);
        list.add(songlistFreePack);
        list.add(songlistLSPack);
        list.add(songlistVLPack);
        list.add(songlistARPack);
        list.add(songlistAVPack);
        list.add(songlistBEPack);
        list.add(songlistCHUNITHMPack);
        list.add(songlistCSPack);
        list.add(songlistDYPack);
        list.add(songlistGCPack);
        list.add(songlistLAPack);
        list.add(songlistSPack);
        list.add(songlistTSPack);
        list.add(songlistAPPack);
        list.add(songlistSRPack);
        list.add(songlistMAPack);
        return list;
    }

    public ArrayList<TreeItem> getSongs() {
        ArrayList<TreeItem> songs = new ArrayList<>();

        TreeItem mapack = new TreeItem("Memory Archive");
        TreeItem freepack = new TreeItem("Arcaea");
        TreeItem srpack = new TreeItem("Sunset Radiance");
        TreeItem appack = new TreeItem("Adverse Prelude");
        TreeItem vlpack = new TreeItem("Vicious Labyrinth");
        TreeItem lspack = new TreeItem("Luminous Sky");
        TreeItem ecpack = new TreeItem("Eternal Core");
        TreeItem arpack = new TreeItem("Absolute Reason");
        TreeItem bepack = new TreeItem("Binary Enfold");
        TreeItem avpack = new TreeItem("Ambivalent Vision");
        TreeItem cspack = new TreeItem("Crimson Solace");
        TreeItem chupack = new TreeItem("CHUNITHM");
        TreeItem gcpack = new TreeItem("Groove Coaster");
        TreeItem tspack = new TreeItem("Tone Sphere");
        TreeItem lapack = new TreeItem("Lanota");
        TreeItem spack = new TreeItem("Stellights");
        TreeItem dypack = new TreeItem("Dynamix");


        mapack.getChildren().addAll(getMAPackSongs());
        freepack.getChildren().addAll(getFreePackSongs());
        srpack.getChildren().addAll(getSRPackSongs());
        vlpack.getChildren().addAll(getVLPackSongs());
        lspack.getChildren().addAll(getLSPackSongs());
        ecpack.getChildren().addAll(getECPackSongs());
        arpack.getChildren().addAll(getARPackSongs());
        bepack.getChildren().addAll(getBEPackSongs());
        avpack.getChildren().addAll(getAVPackSongs());
        cspack.getChildren().addAll(getCSPackSongs());
        chupack.getChildren().addAll(getCHUNITHMPackSongs());
        gcpack.getChildren().addAll(getGCPackSongs());
        tspack.getChildren().addAll(getTSPackSongs());
        lapack.getChildren().addAll(getLAPackSongs());
        spack.getChildren().addAll(getSPackSongs());
        dypack.getChildren().addAll(getDYPackSongs());
        appack.getChildren().addAll(getAPPackSongs());

        songs.add(mapack);
        songs.add(freepack);
        songs.add(srpack);
        songs.add(appack);
        songs.add(vlpack);
        songs.add(lspack);
        songs.add(ecpack);
        songs.add(arpack);
        songs.add(bepack);
        songs.add(avpack);
        songs.add(cspack);
        songs.add(chupack);
        songs.add(gcpack);
        songs.add(tspack);
        songs.add(lapack);
        songs.add(spack);
        songs.add(dypack);

        return songs;
    }

    private ArrayList<TreeItem> getMAPackSongs() {
        ArrayList<TreeItem> mapack = new ArrayList<>();
        for (String songTitle : songlistMAPack) {
            TreeItem song = new TreeItem(songTitle);
            mapack.add(song);
        }
        return mapack;
    }

    private ArrayList<TreeItem> getFreePackSongs() {
        ArrayList<TreeItem> freepack = new ArrayList<>();
        for (String songTitle : songlistFreePack) {
            TreeItem song = new TreeItem(songTitle);
            freepack.add(song);
        }
        return freepack;
    }

    private ArrayList<TreeItem> getSRPackSongs() {
        ArrayList<TreeItem> srpack = new ArrayList<>();
        for (String songTitle : songlistSRPack) {
            TreeItem song = new TreeItem(songTitle);
            srpack.add(song);
        }
        return srpack;
    }

    private ArrayList<TreeItem> getVLPackSongs() {
        ArrayList<TreeItem> vlpack = new ArrayList<>();
        for (String songTitle : songlistVLPack) {
            TreeItem song = new TreeItem(songTitle);
            vlpack.add(song);
        }
        return vlpack;
    }

    private ArrayList<TreeItem> getLSPackSongs() {
        ArrayList<TreeItem> lspack = new ArrayList<>();
        for (String songTitle : songlistLSPack) {
            TreeItem song = new TreeItem(songTitle);
            lspack.add(song);
        }
        return lspack;
    }

    private ArrayList<TreeItem> getECPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistECPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getARPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistARPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getBEPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistBEPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getAVPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistAVPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getCSPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistCSPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getCHUNITHMPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistCHUNITHMPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getGCPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistGCPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getTSPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistTSPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getLAPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistLAPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getSPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistSPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getDYPackSongs() {
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for (String songTitle : songlistDYPack) {
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getAPPackSongs() {
        ArrayList<TreeItem> appack = new ArrayList<>();
        for (String songTitle : songlistAPPack) {
            TreeItem song = new TreeItem(songTitle);
            appack.add(song);
        }
        return appack;
    }

    String[] quickGenerateSongList(int size) {
        Random r = new Random();
        ArrayList<String[]> sl = getSongList();
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> tempresult = new ArrayList<>();
        String[] result;
        for (String[] pack : sl) {
            temp.addAll(Arrays.asList(pack));
        }
        for (int i = 0; i < size; i++) {
            int n = r.nextInt(temp.size());
            tempresult.add(temp.get(n));
            temp.remove(n);
        }
        result = tempresult.toArray(new String[0]);
        return result;
    }

    String[] customGenerateSongList(int size, int desiredDiff) {
        ArrayList<String> validSongList = new ArrayList<>();
        ArrayList<Pack> allSongPack = getSongPacks();
        int index = 0;
        switch (desiredDiff) {
            case 0:
                // All diff
                return quickGenerateSongList(size);
            case 1:
                // <9.0
                for (Pack pack : allSongPack) {
                    for (String song : pack.getPackContent()) {
                        if (pack.getDiffInfo()[index] < 9.0) {
                            // Qualified Song
                            validSongList.add(song);
                        }
                        index++;
                    }
                    index = 0;
                }
                break;
            case 2:
                // 9.0-9.8
                // Only pick free songs / main story pack in this range
                ArrayList<Pack> matchPack = new ArrayList<>();
                Pack freePack = new Pack(songlistFreePack, songlistFreePackDiff);
                Pack ap = new Pack(songlistAPPack, songlistAPPackDiff);
                Pack vl = new Pack(songlistVLPack, songlistVLPackDiff);
                Pack ls = new Pack(songlistLSPack, songlistLSPackDiff);
                Pack ec = new Pack(songlistECPack, songlistECPackDiff);
                matchPack.add(freePack);
                matchPack.add(vl);
                matchPack.add(ls);
                matchPack.add(ap);
                matchPack.add(ec);
                for (Pack mPack : matchPack) {
                    for (String song : mPack.getPackContent()) {
                        if (mPack.getDiffInfo()[index] >= 9.0 && mPack.getDiffInfo()[index] <= 9.8) {
                            // Qualified Song
                            validSongList.add(song);
                        }
                        index++;
                    }
                    index = 0;
                }
                index = 0;
                break;
            case 3:
                // >9.8
                for (Pack pack : allSongPack) {
                    for (String song : pack.getPackContent()) {
                        if (pack.getDiffInfo()[index] > 9.8) {
                            // Qualified Song
                            validSongList.add(song);
                        }
                        index++;
                    }
                    index = 0;
                }
                break;
            case 4:
                // 9.5-10.3
                for (Pack pack : allSongPack) {
                    for (String song : pack.getPackContent()) {
                        if (pack.getDiffInfo()[index] >= 9.5 && pack.getDiffInfo()[index] <= 10.3) {
                            // Qualified Song
                            validSongList.add(song);
                        }
                        index++;
                    }
                    index = 0;
                }
                break;
            case -1:
                // Unknown.
                break;
        }
        if (validSongList.size() < size) {
            Alert notenough = new Alert(Alert.AlertType.ERROR);
            notenough.setContentText("Not enough songs for the map!");
            notenough.show();
            return null;
        } else {
            return randomPick(size, validSongList);
        }
    }

    private String[] randomPick(int desiredSize, ArrayList<String> originList) {
        Random r = new Random();
        ArrayList<String> pickedList = new ArrayList<>();
        for (int i = 0; i < desiredSize; i++) {
            int randomNumber = r.nextInt(originList.size());
            pickedList.add(originList.get(randomNumber));
            originList.remove(randomNumber);
        }
        return pickedList.toArray(new String[0]);
    }

    String[] troll1(int size) {
        Random r = new Random();
        ArrayList<String[]> sl = getSongList();
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> tempresult = new ArrayList<>();
        String[] result;
        for (String[] pack : sl) {
            temp.addAll(Arrays.asList(pack));
        }
        for (int i = 0; i < size; i++) {
            int n = r.nextInt(temp.size());
            tempresult.add(temp.get(n));
        }
        result = tempresult.toArray(new String[0]);
        return result;
    }

    public class Pack {
        String[] packContent;
        double[] diffInfo;

        public Pack(String[] pc, double[] di) {
            this.packContent = pc;
            this.diffInfo = di;
        }

        public double[] getDiffInfo() {
            return diffInfo;
        }

        public String[] getPackContent() {
            return packContent;
        }
    }
}

