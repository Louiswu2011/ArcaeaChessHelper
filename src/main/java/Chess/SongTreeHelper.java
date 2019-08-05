package Chess;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;


public class SongTreeHelper {
    public String[] packs = {"Arcaea","Vicious Labyrinth","Luminous Sky","Eternal Core","Absolute Reason","Binary Enfold","Ambivalent Vision","Crimson Solace","CHUNITHM","Groove Coaster","Tone Sphere","Lanota","Dynamix","Stellights"};

    String[] songlistFreePack = {"Rise","Sayonara Hatsukoi","Fairytale","Lucifer","Snow White","Vexaria","Lost Civilization","qualia -ideaesthsia-","GOODTEK(Arcaea Edit)","Shades of Light in a Transcendent Realm","Babaroque","Dement ~after legend~","Dandelion","Anokumene","Infinity Heaven","Brand new world","Chronostasis","Kanagawa Cyber Culvert","Clotho and the stargazer","Ignotus","Harutopia ~Utopia of Spring~","Rabbit In The Black Room","Red and Blue","One Last Drive","Dreamin' Attraction!!","Syro","Reinvent","Blaster","Cybernecia Catharsis","inkar-usi","Illegal Paradise","Bookmaker (2D Version)","Suomi","Nhelv","LunarOrbit -believe in the Espebranch road-","Purgatorium","Rugie","ReviXy","Grimheart","SUPERNOVA","VECTOR"};
    public double[] songlistFreePackDiff = {7.7,6.0,7.2,8.3,8.6,7.4,9.5,9.2,9.3,8.4,8.7,7.9,8.6,9.2,7.6,7.9,9.1,9.0,7.5,9.7,8.6,8.5,9.7,8.0,9.8,9.5,8.6,9.5,9.8,7.8,9.8,8.3,7.7,9.8,9.4,8.4,9.0,8.9,8.6,9.7,9.5};

    String[] songlistVLPack = {"SOUNDWiTCH","Iconoclast","trappola bewitching","conflict","Axium Crisis","Grievous Lady"};
    public double[] songlistVLPackDiff = {9.9,9.4,10.0,10.2,10.6,11.2};

    String[] songlistLSPack = {"The Message","Sulfur","Maze No.9","Halcyon","Ether Strike","Fracture Ray"};
    public double[] songlistLSPackDiff = {9.7,9.8,8.9,10.7,10.2,11.1};

    String[] songlistECPack = {"cry of viyella","I've heard it said","Relentless","memoryfactory.lzh","Essence of Twilight","Lumia","Sheriruth","PRAGMATISM","Solitary Dream"};
    public double[] songlistECPackDiff = {8.7,8.2,8.1,8.8,9.0,8.5,10.6,10.1,8.4};

    String[] songlistARPack = {"Antithese","Corruption","Black Territory","Vicious Heroism","Cyaegha"};
    public double[] songlistARPackDiff = {8.8,9.7,9.5,10.0,10.8};

    String[] songlistBEPack = {"Memory Forest","next to you","Silent Rush","Strongholds","Singularity"};
    public double[] songlistBEPackDiff = {9.7,8.7,8.4,9.1,10.4};

    String[] songlistAVPack = {"Blossoms","Romance Wars","Moonheart","Lethaeus","Genesis"};
    public double[] songlistAVPackDiff = {7.6,7.4,8.5,9.5,8.4};

    String[] songlistCSPack = {"Paradise","Flashback","Flyburg and Endroll","Party Vinyl","Nirv lucE"};
    public double[] songlistCSPackDiff = {7.8,8.5,9.2,9.8,10.3};

    String[] songlistCHUNITHMPack = {"Garakuta Doll Play","Ikazuchi","World Vanquisher"};
    public double[] songlistCHUNITHMPackDiff = {10.3,10.4,10.6};

    String[] songlistGCPack = {"MERLIN","OMAKENO Stroke","DX Choseinou Full Metal Shojo","Scarlet Lance","ouroboros -twin stroke of the end-"};
    public double[] songlistGCPackDiff = {8.5,9.6,9.7,10.3,10.6};

    String[] songlistTSPack = {"Hall of Mirrors","Hikari","Linear Accelerator","STAGER (ALL STAGE CLEAR)","Tiferet"};
    public double[] songlistTSPackDiff = {8.2,8.3,9.6,9.2,10.5};

    String[] songlistLAPack = {"Dream goes on","Journey","Specta","cyanine","Quon"};
    public double[] songlistLAPackDiff = {7.5,8.6,9.5,10.5,9.7};

    String[] songlistSPack = {"Surrender","Yosakura Fubuki"};
    public double[] songlistSPackDiff = {8.8,9.6};

    String[] songlistDYPack = {"Moonlight of Sand Castle","REconstruction","Evoltex(poppi'n mix)","Oracle","αterlβus"};
    public double[] songlistDYPackDiff = {7.7,8.7,8.9,9.2,10.5};

    public ArrayList<String[]> getSongList(){
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
        return list;
    }

    public ArrayList<TreeItem> getSongs(){
        ArrayList<TreeItem> songs = new ArrayList<>();

        TreeItem freepack = new TreeItem("Arcaea");
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

        freepack.getChildren().addAll(getFreePackSongs());
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

        songs.add(freepack);
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

    private ArrayList<TreeItem> getFreePackSongs(){
        ArrayList<TreeItem> freepack = new ArrayList<>();
        for(String songTitle : songlistFreePack){
            TreeItem song = new TreeItem(songTitle);
            freepack.add(song);
        }
        return freepack;
    }

    private ArrayList<TreeItem> getVLPackSongs(){
        ArrayList<TreeItem> vlpack = new ArrayList<>();
        for(String songTitle : songlistVLPack){
            TreeItem song = new TreeItem(songTitle);
            vlpack.add(song);
        }
        return vlpack;
    }

    private ArrayList<TreeItem> getLSPackSongs(){
        ArrayList<TreeItem> lspack = new ArrayList<>();
        for(String songTitle : songlistLSPack){
            TreeItem song = new TreeItem(songTitle);
            lspack.add(song);
        }
        return lspack;
    }

    private ArrayList<TreeItem> getECPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistECPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getARPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistARPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getBEPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistBEPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getAVPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistAVPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getCSPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistCSPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getCHUNITHMPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistCHUNITHMPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getGCPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistGCPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getTSPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistTSPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getLAPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistLAPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getSPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistSPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }

    private ArrayList<TreeItem> getDYPackSongs(){
        ArrayList<TreeItem> ecpack = new ArrayList<>();
        for(String songTitle : songlistDYPack){
            TreeItem song = new TreeItem(songTitle);
            ecpack.add(song);
        }
        return ecpack;
    }
}