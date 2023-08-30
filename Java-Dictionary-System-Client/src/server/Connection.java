package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection extends Thread{
    private Socket clientSocket;
    private String clientInfo;
    private Dictionary dictionary;
    private Server server;

    Connection(Socket clientSocket, String clientInfo, Dictionary dictionary, Server server) {
        this.clientSocket = clientSocket;
        this.clientInfo = clientInfo;
        this.dictionary = dictionary;
        this.server = server;
    }

    public Connection() {
    }

    public void run() {
        super.run();
        String clientMsg = null;
        String msgClient = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            while ((clientMsg = in.readLine()) != null) {
                System.out.println("[Info] Receive Client Message" + this.clientInfo + ": " + clientMsg);
                this.server.sw.appendserverLog("[Info] Receive Client Message " + this.clientInfo + ": " + clientMsg + "\n");
                msgClient = "[Info] Server Acknowledgement " + clientMsg + "\n";
                String[] lst = clientMsg.split("-");
                if (lst.length == 1) {
                    msgClient = "[Error]Word cannot be empty.⚠️\n";
                } else {
                    String meaing, command = lst[0];
                    String word = lst[1];
                    String str1;
                    switch (str1 = command) {
                        case "delete":
                            msgClient = this.dictionary.delete(word);
                            if (msgClient.charAt(0) == 'S') {
                                this.server.sw.setcountWords(Integer.toString(this.dictionary.getDictionarySize()));
                                this.server.writeDictionary(this.dictionary.getDictionary());
                            }
                            break;
                        case "search":
                            msgClient = this.dictionary.search(word);
                            break;
                        case "update":
                            if (lst.length == 2) {
                                msgClient = "[Error]Meaning cannot be empty.⚠️\n";
                                break;
                            }
                            meaing = lst[2];
                            msgClient = this.dictionary.update(word, meaing);
                            if (msgClient.charAt(0) == 'S')
                                this.server.writeDictionary(this.dictionary.getDictionary());
                            break;
                        case "add":
                            if (lst.length == 2) {
                                msgClient = "[Error]Meaning cannot be empty.⚠️\n";
                                break;
                            }
                            meaing = lst[2];
                            msgClient = this.dictionary.add(word, meaing);
                            if (msgClient.charAt(0) == 'S') {
                                this.server.sw.setcountWords(Integer.toString(this.dictionary.getDictionarySize()));
                                this.server.writeDictionary(this.dictionary.getDictionary());
                            }
                            break;
                    }
                }
                System.out.print("[Info] Response sent: " + msgClient);
                this.server.sw.appendserverLog("[Info] Response sent " + msgClient);
                out.write(msgClient);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("[Info] client " + this.clientInfo + " closed the connection");
            String clientInfo = "[" + this.clientSocket.getInetAddress().getHostName() + ", " + this.clientSocket.getLocalPort() + "]";
            this.server.minusOnlineClients(clientInfo);
            this.server.sw.appendserverLog("[Info] client " + clientInfo + " closed the connection\n");
        }
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
