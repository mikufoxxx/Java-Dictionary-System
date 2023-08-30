package server;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.util.TimerTask;

public class ServerWindow {
    public JFrame frame;
    private JTextPane countWords;
    private JTextArea countOnlineClients;
    private JTextPane onlineClients;
    private JTextArea serverLog;

    public ServerWindow() {
        this.init();
    }

    public void init() {
        this.frame = new JFrame();
        this.frame.setTitle("🎆DictionarySystem - Server👾");
        this.frame.setBounds(600, 200, 600, 460);
        this.frame.setDefaultCloseOperation(3);
        this.frame.getContentPane().setLayout((LayoutManager)null);

        String Title = "<html>" +
                "<body>" +
                "<p>" +
                "Welcome to the DictionarySystem!😊" +
                "</p>" +
                "</body>" +
                "</html>";

        JLabel textTitle = new JLabel(Title);
        textTitle.setFont(new Font("Arial Black", Font.PLAIN, 18));
        textTitle.setBounds(25, 20, 550, 30);
        this.frame.getContentPane().add(textTitle);

        JLabel numWords = new JLabel("Number of Words in Dictionary:");
        numWords.setFont(new Font("Arial", Font.PLAIN, 14));
        numWords.setBounds(25, 55, 550, 30);
        this.frame.getContentPane().add(numWords);

        this.countWords = new JTextPane();
        this.countWords.setFont(new Font("Arial", Font.PLAIN, 14));
        this.countWords.setEditable(false);
        this.countWords.setBounds(230, 60, 80, 20);
        this.frame.getContentPane().add(this.countWords);

        JLabel onlineClient = new JLabel("Online Client:");
        onlineClient.setFont(new Font("Arial", Font.PLAIN, 14));
        onlineClient.setBounds(25, 90, 550, 30);
        this.frame.getContentPane().add(onlineClient);

        this.countOnlineClients = new JTextArea();
        this.countOnlineClients.setFont(new Font("Arial", Font.PLAIN, 14));
        this.countOnlineClients.setEditable(false);
        this.countOnlineClients.setBounds(230, 95, 80, 20);
        this.countOnlineClients.setText("0");
        this.frame.getContentPane().add(this.countOnlineClients);

        JLabel OnClients = new JLabel("Online List:");
        OnClients.setFont(new Font("Arial", Font.PLAIN, 14));
        OnClients.setBounds(25, 120, 550, 30);
        this.frame.getContentPane().add(OnClients);

        JScrollPane sPClients = new JScrollPane();
        sPClients.setBounds(25, 160, 285, 90);
        this.frame.getContentPane().add(sPClients);
        this.onlineClients = new JTextPane();
        sPClients.setViewportView(this.onlineClients);

        JLabel sLog = new JLabel("System Log:");
        sLog.setFont(new Font("Arial", Font.PLAIN, 14));
        sLog.setBounds(25, 260, 550, 30);
        this.frame.getContentPane().add(sLog);
        JScrollPane sPLog = new JScrollPane();
        sPLog.setBounds(25, 300, 535, 90);
        this.frame.getContentPane().add(sPLog);
        this.serverLog = new JTextArea();
        this.serverLog.setFont(new Font("Arial", Font.PLAIN, 14));
        sPLog.setViewportView(this.serverLog);

        JLabel copyright = new JLabel("©Yifan Shi - 1449015");
        copyright.setFont(new Font("Arial", Font.PLAIN, 14));
        copyright.setBounds(25, 390, 535, 30);
        this.frame.getContentPane().add(copyright);
    }

    public synchronized void setcountWords(String numWords) {
        this.countWords.setText(numWords);
    }

    public synchronized void setcountOnlineClients(String numOnlineClients) {
        this.countOnlineClients.setText(numOnlineClients);
    }

    public synchronized void setonlineClients(String onlineClients) {
        this.onlineClients.setText(onlineClients);
    }

    public synchronized void appendserverLog(String stringToAppend) {
        this.serverLog.append(stringToAppend);
    }
}
