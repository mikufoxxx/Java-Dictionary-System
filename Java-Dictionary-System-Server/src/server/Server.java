package server;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    int numWords;
    int numOnlineClients;
    public String filename;
    Dictionary dictionary;
    ServerWindow sw;

    ArrayList<String> onlineClients = new ArrayList<>();

    public Server(Dictionary dictionary, String filename) {
        this.dictionary = dictionary;
        this.filename = filename;
    }

    public static Dictionary initDictionary(String filename) {
        try {
            HashMap<String, String> d = new HashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String receive = null;
            while ((receive = reader.readLine()) != null) {
                String[] lst = receive.split("-");
                String word = lst[0];
                String meaning = lst[1];
                d.put(word, meaning);
            }
            Dictionary dictionary = new Dictionary(d);
            reader.close();
            return dictionary;
        } catch (FileNotFoundException e) {
            System.out.println("[Error] file not found.⚠️\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[Error]Something wrong when reading the dictionary file.");
            System.exit(1);
        }
        return null;
    }

    public synchronized void writeDictionary(HashMap<String, String> d) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./" + this.filename));
            for (Map.Entry<String, String> e : d.entrySet())
                writer.write(String.valueOf(e.getKey()) + "-" + (String)e.getValue() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumOnlineClients() {
        return this.numOnlineClients;
    }

    public synchronized void addOnlineClients(String clientInfo) {
        this.numOnlineClients++;
        this.onlineClients.add(clientInfo);
        this.sw.setcountOnlineClients(Integer.toString(this.numOnlineClients));
        String clients = "";
        for (int i = 0; i < this.onlineClients.size(); i++)
            clients = String.valueOf(clients) + "Client " + (i + 1) + ": " + (String)this.onlineClients.get(i) + "\n";
        this.sw.setonlineClients(clients);
    }

    public synchronized void minusOnlineClients(String clientInfo) {
        this.numOnlineClients--;
        this.onlineClients.remove(clientInfo);
        this.sw.setcountOnlineClients(Integer.toString(this.numOnlineClients));
        String clients = "";
        for (int i = 0; i < this.onlineClients.size(); i++)
            clients = String.valueOf(clients) + "Client " + (i + 1) + ": " + (String)this.onlineClients.get(i) + "\n";
        this.sw.setonlineClients(clients);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("How to open the Client:");
            System.out.println("$java -jar DictionaryServer.jar <port[10000-65535]> <dictionary-file>");
            System.exit(1);
        }
        int sPort = 0;
        String file = null;
        try {
            sPort = Integer.parseInt(args[0]);
            file = args[1];
            if (sPort < 10000 || sPort > 65535) {
                System.out.println("Please enter the correct port in [10000-65535]");
                System.exit(1);
            }
            File f = new File(file);
            if (!f.exists()) {
                System.out.println("File not found.⚠️");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid server-port. Please try again!⚠️");
            System.exit(1);
        }
        Dictionary dictionary = initDictionary("./" + file);
        final Server server = new Server(dictionary, file);
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(sPort);
            server.sw = new ServerWindow();
            server.sw.setcountWords(Integer.toString(dictionary.getDictionarySize()));
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        server.sw.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            while (true) {
                System.out.println("[Info] Server open a port on " + sPort + "  for a connection");
                server.sw.appendserverLog("[Info] Server open a port on " + sPort + "  for a connection\n");
                Socket clientSocket = welcomeSocket.accept();
                String clientInfo = "[" + clientSocket.getInetAddress().getHostName() + ", " + clientSocket.getLocalPort() + "]";
                server.addOnlineClients(clientInfo);
                Connection ConnectionT = new Connection(clientSocket, clientInfo, dictionary, server);
                ConnectionT.start();
                System.out.println("[Info] Client " + clientInfo + "  conection accepted:");
                server.sw.appendserverLog("[Info] Client " + clientInfo + "  conection accepted\n");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Address already in use. Please try again!⚠️");
        } finally {
            if (welcomeSocket != null)
                try {
                    welcomeSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
