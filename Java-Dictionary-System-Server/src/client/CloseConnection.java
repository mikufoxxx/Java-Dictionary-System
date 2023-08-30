package client;

import java.util.TimerTask;

public class CloseConnection extends TimerTask {
    Client client;

    public CloseConnection(Client client) {
        this.client = client;
    }

    public void run() {
        this.client.cw.setTextPane("[Info]Connection closed.\n");
        System.out.println("[Info] Client connection closed.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
