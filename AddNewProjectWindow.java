import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddNewProjectWindow extends JFrame {

    JPanel addPPanel = new JPanel();
    JLabel pName = new JLabel("Project name: ");
    final JTextField jtfPName = new JTextField("Type in project name",20);
    JLabel pComment = new JLabel("Comment: ");
    final JTextField jtfPComment = new JTextField(20);
    JButton addNewProjectButton = new JButton("Add");
    DefaultListModel projectsList;
    public void kill(){
        this.dispose();
    }

    public void addNewProject(String pNameC, String pCommentC){
        try {
            SessionTimer.resetTime();
            BufferedWriter writer = new BufferedWriter(new FileWriter("projects",true));
            writer.write("PROJECT\n");
            writer.write(pNameC+";"+pCommentC+";"+"PLANNED\n");
            writer.close();
//            new Project(pNameC,pCommentC,ProjectStatus.FINISHED);
            projectsList.clear();
            projectsList.addAll(Project.getProjectList());
            Log.writeLog(LogWindow.loggedPerson, "Added new project " + pNameC);
            kill();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    AddNewProjectWindow(DefaultListModel projectsList){
        //panel setup
        this.projectsList = projectsList;
        addPPanel.setLayout(new GridLayout(0,1));
        addPPanel.setPreferredSize(new Dimension(300,200));
        addPPanel.setMinimumSize(new Dimension(300,200));
        addPPanel.setMaximumSize(new Dimension(300,200));
        //pName
        pName.setHorizontalAlignment(JLabel.CENTER);
        addPPanel.add(pName);
        pName.setLabelFor(jtfPName);
        addPPanel.add(jtfPName);
        //pComment
        pComment.setHorizontalAlignment(JLabel.CENTER);
        addPPanel.add(pComment);
        add(addPPanel);
        pComment.setLabelFor(jtfPComment);
        addPPanel.add(jtfPComment);
        addPPanel.add(new JPanel());
        //add button
        addPPanel.add(addNewProjectButton);
        addNewProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewProject(jtfPName.getText(),jtfPComment.getText());
            }
        });

        jtfPComment.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    addNewProject(jtfPName.getText(),jtfPComment.getText());
                }
            }
        });
        jtfPName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    addNewProject(jtfPName.getText(),jtfPComment.getText());
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
