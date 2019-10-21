package Chess;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Prober extends Thread {
    private String arcid;
    private ListView messageBox;
    private RecentInfo ri;
    private Paint customFill;
    private ArrayList<InfoStack> infoStacks;
    private int proberCount;
    private Button export;

    Prober(String arcid, ListView messageBox, Paint customFill, ArrayList<InfoStack> infoStacks, int proberCount, Button export) {
        this.arcid = arcid;
        this.messageBox = messageBox;
        this.customFill = customFill;
        this.infoStacks = infoStacks;
        this.proberCount = proberCount;
        this.export = export;
    }

    @Override
    public void run() throws IllegalStateException {
        super.run();
        ScoreProber sp = null;
        sp = new ScoreProber();

        ri = sp.probeAlternative(this.arcid, this.infoStacks, customFill);
        ObservableList ol = messageBox.getItems();
        String result = "[" + ri.getName() + "] [" + ri.getSong() + "] [" + ri.getScore() + "]";
        Platform.runLater(() -> {
            if (ri.getName().equals("sb")) {
                ol.add("Error. User ID: " + arcid + " not found.");
            } else {
                ol.add(result);
            }
        });
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object[] cells = messageBox.lookupAll(".cell").toArray();
        for (Object cell : cells) {
            Cell nowCell = (Cell) cell;
            if (nowCell.getText().equals(result)) {
                if (customFill.toString().equals("0xffffffff")) {
                    nowCell.setTextFill(Color.BLACK);
                } else {
                    nowCell.setTextFill(customFill);
                }
                break;
            }
        }
        /*int cellCount = 0;
        for(Object cell : cells){
            Cell checkCell = (Cell)cell;
            if(checkCell.getText().startsWith("[")){
                cellCount++;
            }
        }

        if(cellCount == proberCount){
            Platform.runLater(() -> {
                export.setDisable(false);
            });
        }*/
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Thread started.");
    }
}
