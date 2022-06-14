import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditYourAccountWindow extends JFrame {
    JPanel editPanel = new JPanel();
    JLabel data = new JLabel();
    JLabel yourPassword = new JLabel();
    JPasswordField yourNewPassword = new JPasswordField( 20);
    JButton changePasswordButton = new JButton("CHANGE PASSWORD");

    public void kill(){
        this.dispose();
    }

    public void changePassword(String password){
            Person person = LogWindow.loggedPerson;
            person.setPassword(password);
            Developer.writeDevelopers();
            Manager.writeManagers();
            Log.writeLog(LogWindow.loggedPerson, "Changed password to: " + password );
        kill();
    }

    EditYourAccountWindow(){
        //panel setup
        editPanel.setLayout(new GridLayout(0,1));
        editPanel.setPreferredSize(new Dimension(300,200));
        editPanel.setMinimumSize(new Dimension(300,200));
        editPanel.setMaximumSize(new Dimension(300,200));
        setTitle("SET NEW PASSWORD");

        //info o zalogowanym devie
        data.setText("name: " +LogWindow.loggedPerson.name + " " + LogWindow.loggedPerson.surname + " " + LogWindow.loggedPerson.pesel);
        data.setHorizontalAlignment(JLabel.CENTER);
        editPanel.add(data);

        //label z obecnym haslem
        yourPassword.setText("Your current password is: " + LogWindow.loggedPerson.password);
        yourPassword.setHorizontalAlignment(JLabel.CENTER);
        editPanel.add(yourPassword);
        yourPassword.setLabelFor(yourNewPassword);
        editPanel.add(yourNewPassword);

        //Change button
        editPanel.add(changePasswordButton);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                changePassword(yourNewPassword.getText());
            }
        });
        yourNewPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    SessionTimer.resetTime();
                    changePassword(yourNewPassword.getText());
                }
            }
        });


        //frame setup
        add(editPanel);
        setSize(500,500);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0,1));
    }
}
