import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewDeveloper extends JFrame {

    JPanel addPanel = new JPanel();
    JLabel dName = new JLabel("Developer name");
    JTextField jftDName = new JTextField(20);
    JLabel dSurname = new JLabel("Surname");
    JTextField jtfDSurname = new JTextField(20);
    JLabel dPesel = new JLabel("Pesel");
    JTextField jftDPesel = new JTextField(20);
    JLabel dPassword = new JLabel("Password");
    JTextField jtfDPassword = new JTextField(20);
    JLabel dColour = new JLabel("Favourite Colour");
    JTextField jtfDColour = new JTextField(20);
    JButton addNewDeveloper = new JButton("Add");

    AddNewDeveloper(){
        addPanel.setLayout(new GridLayout(0,1));
        addPanel.setPreferredSize(new Dimension(300,200));
        addPanel.setMaximumSize(new Dimension(300,200));
        addPanel.setMinimumSize(new Dimension(300,200));

        //dName
        dName.setHorizontalAlignment(JLabel.CENTER);
        addPanel.add(dName);
        dName.setLabelFor(jftDName);
        addPanel.add(jftDName);
        //dSurname
        dSurname.setHorizontalAlignment(JLabel.CENTER);
        addPanel.add(dSurname);
        dSurname.setLabelFor(jtfDSurname);
        addPanel.add(jtfDSurname);
        //dPesel
        dPesel.setHorizontalAlignment(JLabel.CENTER);
        addPanel.add(dPesel);
        dPesel.setLabelFor(jftDPesel);
        addPanel.add(jftDPesel);
        //dPassword
        dPassword.setHorizontalAlignment(JLabel.CENTER);
        addPanel.add(dPassword);
        dName.setLabelFor(jtfDPassword);
        addPanel.add(jtfDPassword);
        //dColour
        dColour.setHorizontalAlignment(JLabel.CENTER);
        addPanel.add(dColour);
        dColour.setLabelFor(jtfDColour);
        addPanel.add(jtfDColour);
        //add Button
        addPanel.add(addNewDeveloper);
        addNewDeveloper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Developer(jftDName.getText(),jtfDSurname.getText(),jftDPesel.getText(),jtfDPassword.getText(),jtfDColour.getText());
                Developer.writeDevelopers();
                Log.writeLog(LogWindow.loggedPerson, "Added new developer: " + jftDName.getText() + " " + jtfDSurname.getText());
                kill();
            }
        });

        add(addPanel);
        setSize(500,500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0,1));
    }

    private void kill() {
        this.dispose();
    }
}
