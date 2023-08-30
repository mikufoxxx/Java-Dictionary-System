package client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public ClientWindow cw;
    private Socket clientSocket = null;
    BufferedReader in = null;
    BufferedWriter out = null;


    public Client(String sAddress, int sPort) {
        try {
            this.clientSocket = new Socket(sAddress, sPort);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream(), "UTF-8"));
            this.out = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream(), "UTF-8"));
            System.out.println("[Info]Connection established!😎");
        } catch (UnknownHostException e_h) {
            System.out.println("[Info]Unknown host. Please try again!⚠️");
            System.exit(1);
        } catch (IOException e_socket) {
            System.out.println("[Info]The server is not reachable!😢");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("How to open the Client:");
            System.out.println("$java -jar DictionaryClient.jar <server-address> <server-port[10000-65535]>");
            System.exit(1);
        }
        String sAddress = args[0];
        int sPort = 0;
        try {
            sPort = Integer.parseInt(args[1]);
            if (sPort < 10000 || sPort > 65535) {
                System.out.println("Please enter the correct port in [10000-65535]");
                System.exit(1);
            }
        } catch (NumberFormatException e_p) {
            System.out.println("Invalid server-port. Please try again!⚠️");
            System.exit(1);
        }
        final Client client = new Client(sAddress, sPort);
        client.cw = new ClientWindow(client);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    client.cw.frame.setVisible(true);
                }catch (Exception e_r){
                    e_r.printStackTrace();
                }
            }
        });
    }

    public String delete(String dWord) {
        try {
            String request = "delete-" + dWord + "-";
            this.out.write(request + "\n");
            this.out.flush();

            System.out.println("[Info] Delete Request Sent.✅");
            String responseServer = this.in.readLine();
            System.out.println("[Info] Delete Request Received: " + responseServer);
            return responseServer;
        } catch (IOException e_d) {
            return "Server is not there, please come back later.\n";
        }
    }

    public String add(String dWord, String dMeans) {
        try {
            String request = "add-" + dWord + "-" + dMeans;
            this.out.write(request + "\n");
            this.out.flush();

            System.out.println("[Info] Add Request Sent.✅");
            String responseServer = this.in.readLine();
            System.out.println("[Info] Add Request Received: " + responseServer);
            return responseServer;
        } catch (IOException e_a) {
            return "Server is not reachable, please try it again later.❎\n";
        }
    }

    public String update(String dWord, String dMeans) {
        try {
            String request = "update-" + dWord + "-" + dMeans;
            this.out.write(request + "\n");
            this.out.flush();

            System.out.println("[Info] Update Request Sent.✅");
            String responseServer = this.in.readLine();
            System.out.println("[Info] Update Request Received: " + responseServer);
            return responseServer;
        } catch (IOException e_u) {
            return "Server is not reachable, please try it again later.❎\n";
        }
    }

    public String search(String dWord) {
        try {
            String request = "search-" + dWord + "-";
            this.out.write(request + "\n");
            this.out.flush();

            System.out.println("[Info] Search Request Sent.✅");
            String responseServer = this.in.readLine();
            System.out.println("[Info] Search Request Received: " + responseServer);
            return responseServer;
        } catch (IOException e_s) {
            return "Server is not reachable, please try it again later.❎\n";
        }
    }

    public void closeClientSocket() {
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
