package Chess;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class InfoStack {
    String name;
    String rawjson;
    boolean live = true;
    Paint color = Color.BLACK;

    InfoStack(String id, String rawjson) {
        this.name = id;
        this.rawjson = rawjson;
    }

    public String getName() {
        return name;
    }

    public void setName(String id) {
        this.name = id;
    }

    public String getRawjson() {
        return rawjson;
    }

    public void setRawjson(String rawjson) {
        this.rawjson = rawjson;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
