package client;

import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class ClientWindow {
    public JFrame frame;
    private JTextField dWord;
    private JTextField dMeans;
    private JTextPane textPane;

    Client client;
    Timer timer;

    public ClientWindow(Client client) {
        this.init();
        this.client = client;
        this.timer = new Timer();
        TimerTask task = new CloseConnection(client);
        this.timer.schedule(task, 60000);
    }

    private void init() {
        this.frame = new JFrame();
        this.frame.getContentPane().setFont(new Font("Arial Black", Font.PLAIN, 13));
        this.frame.setTitle("🎆DictionarySystem - Client👾");
        this.frame.setBounds(600, 200, 600, 460);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(null);

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

        JLabel instructionTitle = new JLabel("<html><body><p>1. Input a word <br/>2. Input meaning(s) of the word <br/>3. Click a command which you want to do <br/>4. Check the log in ServerLog.</p></body></html>");
        instructionTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionTitle.setBounds(25, 55, 550, 100);
        this.frame.getContentPane().add(instructionTitle);

        JLabel inputWord = new JLabel("Input word here:");
        inputWord.setFont(new Font("Arial", Font.PLAIN, 14));
        inputWord.setBounds(25, 162, 128, 14);
        this.frame.getContentPane().add(inputWord);

        this.dWord = new JTextField();
        this.dWord.setBounds(130, 160, 161, 20);
        this.frame.getContentPane().add(this.dWord);
        this.dWord.setColumns(10);

        JLabel inputMeans = new JLabel("Add meaning(s) here (You can use ';' to separate meanings): ");
        inputMeans.setFont(new Font("Arial", Font.PLAIN, 14));
        inputMeans.setBounds(25, 200, 550, 30);
        this.frame.getContentPane().add(inputMeans);

        this.dMeans = new JTextField();
        this.dMeans.setBounds(25, 232, 535, 30);
        this.frame.getContentPane().add(this.dMeans);
        this.dMeans.setColumns(10);

        JLabel operateButton = new JLabel("Operate Button");
        operateButton.setFont(new Font("Arial", Font.PLAIN, 14));
        operateButton.setBounds(440, 30, 150, 20);
        this.frame.getContentPane().add(operateButton);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            ClientWindow.this.timer.cancel();
            ClientWindow.this.timer = new Timer();
            TimerTask search = new CloseConnection(ClientWindow.this.client);
            ClientWindow.this.timer.schedule(search, 60000);
            String word = ClientWindow.this.dWord.getText();
            String request;
            if (word.equals("")) {
                request = "[Error]Word cannot be empty!⚠️";
            } else if (word.matches("[a-zA-Z]+")) {
                request = "[Search]Request send!✅\n" + ClientWindow.this.client.search(word);
            } else {
                request = "[Error]The word is invalid!⚠️";
            }

            ClientWindow.this.textPane.setText(request);
        });
        btnSearch.setBounds(440, 60, 100, 25);
        this.frame.getContentPane().add(btnSearch);
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            ClientWindow.this.timer.cancel();
            ClientWindow.this.timer = new Timer();
            TimerTask add = new CloseConnection(ClientWindow.this.client);
            ClientWindow.this.timer.schedule(add, 60000);
            String word = ClientWindow.this.dWord.getText();
            String meaning = ClientWindow.this.dMeans.getText();
            String response;
            if (word.equals("")) {
                response = "[Error]Word cannot be empty!⚠️";
            } else if (meaning.equals("")) {
                response = "[Error]Meaning cannot be empty!⚠️";
            } else if (!word.matches("[a-zA-Z]+")) {
                response = "[Error]Please input a valid word!⚠️";
            } else if (!meaning.matches("([a-zA-Z]( )?+(; |;)?)+")) {
                response = "[Error]Please input a valid meaning!⚠️";
            } else {
                response = "[Add]Request send!✅\n" + ClientWindow.this.client.add(word, meaning);
            }

            ClientWindow.this.textPane.setText(response);
        });
        btnAdd.setBounds(440, 95, 100, 25);
        this.frame.getContentPane().add(btnAdd);
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            ClientWindow.this.timer.cancel();
            ClientWindow.this.timer = new Timer();
            TimerTask update = new CloseConnection(ClientWindow.this.client);
            ClientWindow.this.timer.schedule(update, 60000);
            String word = ClientWindow.this.dWord.getText();
            String meaning = ClientWindow.this.dMeans.getText();
            String response;
            if (word.equals("")) {
                response = "[Error]Word cannot be empty!⚠️";
            } else if (meaning.equals("")) {
                response = "[Error]Meaning cannot be empty!⚠️";
            } else if (!word.matches("[a-zA-Z]+")) {
                response = "[Error]Please input a valid word!⚠️";
            } else if (!meaning.matches("([a-zA-Z]( )?+(; |;)?)+")) {
                response = "[Error]Please input a valid meaning!⚠️";
            } else {
                response = "[Update]Request send!✅\n" + ClientWindow.this.client.update(word, meaning);
            }

            ClientWindow.this.textPane.setText(response);
        });
        btnUpdate.setBounds(440, 130, 100, 25);
        this.frame.getContentPane().add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> {
            ClientWindow.this.timer.cancel();
            ClientWindow.this.timer = new Timer();
            TimerTask delete = new CloseConnection(ClientWindow.this.client);
            ClientWindow.this.timer.schedule(delete, 60000);
            String word = ClientWindow.this.dWord.getText();
            String response;
            if (word.equals("")) {
                response = "[Error]Word cannot be empty!⚠️";
            } else if (word.matches("[a-zA-Z]+")) {
                response = "[Delete]Request send!✅\n" + ClientWindow.this.client.delete(word);
            } else {
                response = "[Error]Please input a valid word!⚠️";
            }

            ClientWindow.this.textPane.setText(response);
        });
        btnDelete.setBounds(440, 165, 100, 25);
        this.frame.getContentPane().add(btnDelete);

        JLabel displayLog = new JLabel(" ServerLog:");
        displayLog.setFont(new Font("Arial", Font.PLAIN, 14));
        displayLog.setBounds(25, 265, 535, 30);
        this.frame.getContentPane().add(displayLog);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 295, 535, 90);
        this.frame.getContentPane().add(scrollPane);
        this.textPane = new JTextPane();
        this.textPane.setEditable(false);
        this.textPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setViewportView(this.textPane);

        JLabel copyright = new JLabel("©Yifan Shi - 1449015");
        copyright.setFont(new Font("Arial", Font.PLAIN, 14));
        copyright.setBounds(25, 390, 535, 30);
        this.frame.getContentPane().add(copyright);

    }

    public synchronized void setTextPane(String str) {
        this.textPane.setText(str);
    }
}
