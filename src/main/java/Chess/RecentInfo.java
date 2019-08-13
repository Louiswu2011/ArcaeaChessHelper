package Chess;

public class RecentInfo {
    String name;
    String score;
    String song;

    RecentInfo(String name, String score, String song) {
        this.name = name;
        this.score = score;
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getSong() {
        return song;
    }
}
