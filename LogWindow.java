import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;

class LogWindow extends JFrame {

    JPanel logPanel = new JPanel();
    JLabel username = new JLabel("Username: ");
    final JTextField jtfUsername = new JTextField("Type in your username",20);
    JLabel password = new JLabel("Password: ");
    final JPasswordField jtfPassword = new JPasswordField(20);
    JButton logInButton = new JButton("log in");
//    ManagerWindow mw = new ManagerWindow();
//    ManagerWindow mw;
    LinkedList<Person> users = new LinkedList<>();
    SessionTimer timer = new SessionTimer();
    static boolean czyManager = false;
    static Person loggedPerson;

    public void kill(){
        this.dispose();
    }

    public void logInAction(){
        boolean isright = false;
        String user = jtfUsername.getText();
        String pass = jtfPassword.getText();
        for (Person p: users) {
            if(user.equals(p.name) && pass.equals(p.password)){
                if(p instanceof Manager){
                    loggedPerson=p;
                    czyManager=true;
                    ManagerWindow mw = new ManagerWindow();
                }
                if(p instanceof Developer){
                    loggedPerson=p;
                    ManagerWindow mw = new ManagerWindow();
                }
//                mw.setVisible(true);
                    timer.start();
                    kill();
                    isright = true;
            }
        }
        if(!isright){
            JOptionPane.showMessageDialog(null, "Wrong username or password", null, JOptionPane.WARNING_MESSAGE);
        }
    }
    public void setPass(){
        users.addAll(Developer.developers);
        users.addAll(Manager.managers);
    }


    public static boolean getCzyManager(){
        return czyManager;
    }

    LogWindow(){
//        mw.setVisible(false);
        setTitle("Log in");
        Developer.readDevelopers();
        Manager.readManagers();
//        Project.readDeveloperInProject();
        setPass();

        //panel setup
        logPanel.setLayout(new GridLayout(0,1));
        logPanel.setPreferredSize(new Dimension(300,200));
        logPanel.setMinimumSize(new Dimension(300,200));
        logPanel.setMaximumSize(new Dimension(300,200));
        //username
        username.setHorizontalAlignment(JLabel.CENTER);
        logPanel.add(username);
        username.setLabelFor(jtfUsername);
        logPanel.add(jtfUsername);
        //password
        password.setHorizontalAlignment(JLabel.CENTER);
        logPanel.add(password);
        add(logPanel);
        password.setLabelFor(jtfPassword);
        logPanel.add(jtfPassword);
        logPanel.add(new JPanel());
        //login button
        logPanel.add(logInButton);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInAction();
            }
        });
        //keyadapter do entera przy logowaniu
        jtfPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    logInAction();
                }
            }
        });
        jtfUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    logInAction();
                }
            }
        });


        //frame setup
        setSize(500,500);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0,1));
    }

}
