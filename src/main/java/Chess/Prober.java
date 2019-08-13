package Chess;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Prober extends Thread {
    private String arcid = "123456789";
    private ListView messageBox;
    private RecentInfo ri;

    Prober(String arcid, ListView messageBox) {
        this.arcid = arcid;
        this.messageBox = messageBox;
    }

    @Override
    public void run() throws IllegalStateException {
        super.run();
        ScoreProber sp = new ScoreProber();
        ri = sp.probe(this.arcid);
        ObservableList ol = messageBox.getItems();
        if (ri.getName() == "sb") {
            ol.add("Error. User ID: " + arcid + " not found.");
        } else {
            ol.add("[" + ri.getName() + "] [" + ri.getSong() + "] [" + ri.getScore() + "]");
        }
        messageBox.setItems(ol);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Thread started.");
    }
}
