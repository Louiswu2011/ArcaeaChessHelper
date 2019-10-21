package Chess;

import com.neovisionaries.ws.client.*;
import org.brotli.dec.BrotliInputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProbeClient {
    private WebSocketFactory webSocketFactory = new WebSocketFactory();
    private WebSocket webSocket;
    private WebSocketListener webSocketListener;
    private int opcode = 0;
    private String returnMsg = "";
    private String uid;
    private volatile Boolean dataReceived = false;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile Boolean returnReady = false;
    private int shinycount = 0;
    private int perfectcount = 0;
    private int queried = 0;
    private int temp = 0;
    private ArrayList<String> payloads = new ArrayList<>();

    ProbeClient() throws IOException, WebSocketException {
        webSocket = webSocketFactory.createSocket("wss://arc.estertion.win:616/");
        webSocketListener = new WebSocketListener() {
            @Override
            public void onStateChanged(WebSocket webSocket, WebSocketState webSocketState) throws Exception {

            }

            @Override
            public void onConnected(WebSocket webSocket, Map<String, List<String>> map) throws Exception {
                // System.out.println("Connected to server.");
                switch (opcode) {
                    case 0:
                        break;
                    case 1:
                        webSocket.sendText(uid + " 10 10");
                        break;
                    case 2:
                        webSocket.sendText(uid + " 6 12");
                }
            }

            @Override
            public void onConnectError(WebSocket webSocket, WebSocketException e) throws Exception {

            }

            @Override
            public void onDisconnected(WebSocket webSocket, WebSocketFrame webSocketFrame, WebSocketFrame webSocketFrame1, boolean b) throws Exception {
                webSocket.flush();
            }

            @Override
            public void onFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onContinuationFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onTextFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onBinaryFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onCloseFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onPingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onPongFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onTextMessage(WebSocket webSocket, String s) throws Exception {
                // System.out.println(s);
                if (s.equals("invalid id")) {
                    returnMsg = "invalid";
                    returnReady = true;
                } else if (s.equals("bye")) {
                    if (opcode == 2) {
                        // start analyzing
                        for (String payload : payloads) {
                            calculateAccuracy(payload);
                        }
                        returnMsg = shinycount + "/" + perfectcount + "/" + queried;
                        returnReady = true;
                    }
                }
            }

            @Override
            public void onTextMessage(WebSocket webSocket, byte[] bytes) throws Exception {

            }

            @Override
            public void onBinaryMessage(WebSocket webSocket, byte[] bytes) throws Exception {
                switch (opcode) {
                    case 0:
                        // System.out.println(brotliDecompress(bytes));
                        break;
                    case 1:
                        // System.out.println("Received Binary Data.");
                        returnMsg = getRecentInfo(brotliDecompress(bytes));
                        break;
                    case 2:
                        // System.out.println(brotliDecompress(bytes));
                        if (temp > 1) {
                            payloads.add(brotliDecompress(bytes));
                            System.out.println("Payload added.");
                        }
                        temp++;
                }
            }

            @Override
            public void onSendingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onFrameSent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onFrameUnsent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onThreadCreated(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {

            }

            @Override
            public void onThreadStarted(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {

            }

            @Override
            public void onThreadStopping(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {

            }

            @Override
            public void onError(WebSocket webSocket, WebSocketException e) throws Exception {

            }

            @Override
            public void onFrameError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onMessageError(WebSocket webSocket, WebSocketException e, List<WebSocketFrame> list) throws Exception {

            }

            @Override
            public void onMessageDecompressionError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {

            }

            @Override
            public void onTextMessageError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {

            }

            @Override
            public void onSendError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {

            }

            @Override
            public void onUnexpectedError(WebSocket webSocket, WebSocketException e) throws Exception {

            }

            @Override
            public void handleCallbackError(WebSocket webSocket, Throwable throwable) throws Exception {

            }

            @Override
            public void onSendingHandshake(WebSocket webSocket, String s, List<String[]> list) throws Exception {

            }
        };
        webSocket.addListener(webSocketListener);
    }

    public Future<String> getUserRecent(String uid) throws WebSocketException, InterruptedException {
        opcode = 1;
        this.uid = uid;
        webSocket.connect();
        while (!dataReceived) {
            // Do nothing and wait for return
        }
        Thread.sleep(100);
        // System.out.println(returnMsg);
        return executor.submit(() -> returnMsg);
    }

    public Future<String> getUserAcc(String uid) throws WebSocketException, InterruptedException, NumberFormatException {
        opcode = 2;
        this.uid = uid;
        webSocket.connect();
        while (!returnReady) {
            // Wait for analyze
        }
        Thread.sleep(100);
        if (returnMsg.equals("invalid")) {
            throw new NumberFormatException();
        } else {
            return executor.submit(() -> returnMsg);
        }
    }


    private String brotliDecompress(byte[] data) throws IOException {
        byte[] outarray = decompress(data, true);
        return new String(outarray);
    }

    private byte[] decompress(byte[] data, boolean byByte) throws IOException {
        byte[] buffer = new byte[65536];
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BrotliInputStream brotliInput = new BrotliInputStream(input);
        if (byByte) {
            byte[] oneByte = new byte[1];
            while (true) {
                int next = brotliInput.read();
                if (next == -1) {
                    break;
                }
                oneByte[0] = (byte) next;
                output.write(oneByte, 0, 1);
            }
        } else {
            while (true) {
                int len = brotliInput.read(buffer, 0, buffer.length);
                if (len <= 0) {
                    break;
                }
                output.write(buffer, 0, len);
            }
        }
        brotliInput.close();
        return output.toByteArray();
    }

    private String getRecentInfo(String json) {
        String ret = "";
        JSONObject jsonObject = new JSONObject(json);
        String cmd = jsonObject.getString("cmd");
        if (cmd.equals("songtitle")) {
            // Throw
        } else if (cmd.equals("userinfo")) {
            // get info
            ret = json;
            this.dataReceived = true;
            // System.out.println("completed.");
            webSocket.disconnect();
        } else {
            // tbf
        }
        return ret;
    }

    private void calculateAccuracy(String json) {
        int i = 0;
        JSONObject o = new JSONObject(json);
        JSONArray data = o.getJSONArray("data");
        while (true) {
            try {
                JSONObject payload = data.getJSONObject(i);
                if (payload.getInt("difficulty") == 2) {
                    shinycount += payload.getInt("shiny_perfect_count");
                    perfectcount += payload.getInt("perfect_count");
                    queried++;
                }
                i++;
            } catch (JSONException e) {
                break;
            }
        }
        System.out.println(shinycount + "/" + perfectcount);
    }
}
