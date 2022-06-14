import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class ManagerWindow extends JFrame {

    JPanel projectTab = new JPanel();
    JPanel projectPanel = new JPanel();
    JPanel rightComponentPanel = new JPanel();
    JPanel settings = new JPanel();

    JLabel nameLabel = new JLabel();
    JLabel managerLabel = new JLabel();
    JLabel developerTeamLabel = new JLabel();
    JTextArea commentLabel = new JTextArea();
    JLabel statusLabel = new JLabel();


    JList projectsList = new JList();
    DefaultListModel projectsListModel = new DefaultListModel();

    JButton addCommentButton = new JButton("ADD COMMENT");
    JButton workOnProjectButton = new JButton("WORK ON PROJECT");
    JTabbedPane jtp = new JTabbedPane();//sprobuj zamienic na jMenuBar
    JSplitPane jsp = new JSplitPane();
    Project selectedProject;

    ManagerWindow(){
        setVisible(true);
        setLayout(new BorderLayout());
        add(jtp);
        //dodanie pierwszej zakladki
        jtp.add("Projects", projectTab);
        // dodanie drugiej zakladki
        jtp.add("Settings",settings);


        //edycja pierwszej zakladki
        //Lewa Strona pierwszej zakladki - PROJECTS
        projectTab.add(projectPanel);
        jsp.setLeftComponent(new JScrollPane(projectsList));

        if(LogWindow.loggedPerson instanceof Developer){
            projectsListModel.addAll(Project.readProjectsForDev());
        }else {
            projectsListModel.addAll(Project.readProjects());
            Project.readDeveloperInProject();
        }
        projectsList.setModel(projectsListModel);
        projectsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                SessionTimer.resetTime();
                selectedProject = (Project) projectsList.getSelectedValue();
                Project p = (Project) projectsList.getSelectedValue();
                nameLabel.setText("Project name: " + p.getProjectName());
                nameLabel.setFont(new Font("Serif",Font.PLAIN, 40));
                managerLabel.setText(" Manager: ");
                developerTeamLabel.setText(p.projectDeveloperTeam.toString());
                commentLabel.setText(" Comments: " + p.getComment());
                statusLabel.setText(" Status: " + p.getProjectStatus());
            }
        });


        //Prawa strona Pierwszej zakladki - PROJECTS
        jsp.setRightComponent(rightComponentPanel);
        rightComponentPanel.setPreferredSize(new Dimension(1000, 750));
        rightComponentPanel.setMaximumSize(new Dimension(1000, 750));
        rightComponentPanel.setMinimumSize(new Dimension(1000, 750));
        rightComponentPanel.setLayout(new GridLayout(0,1));
        rightComponentPanel.add(nameLabel);
        rightComponentPanel.add(developerTeamLabel);
        rightComponentPanel.add(managerLabel);
        rightComponentPanel.add(commentLabel);
        commentLabel.setLineWrap(true);
        commentLabel.setEditable(false);
        rightComponentPanel.add(statusLabel);
        JPanel bottom = new JPanel(new GridLayout(0,2));
        bottom.add(workOnProjectButton);
        //workOnOprojectButton
        workOnProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(LogWindow.loggedPerson instanceof Manager){
                    JOptionPane.showMessageDialog(null,"nie jestes developerem!");
                }else{
                    Log.writeLog(LogWindow.loggedPerson, "Started working on " + selectedProject.toString());
                    selectedProject.setProjectStatus(ProjectStatus.ONGOING);
                }
            }
        });


        //addComentButton
        bottom.add(addCommentButton);
        rightComponentPanel.add(bottom);
        addCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                JTextArea comment = new JTextArea();
                String commenting = JOptionPane.showInputDialog(null, "ADD COMMENT" , "Set comment", JOptionPane.QUESTION_MESSAGE);
                String whoPostedComment = LogWindow.loggedPerson.toString();
                selectedProject.addComment(commenting, whoPostedComment);
                Log.writeLog(LogWindow.loggedPerson, "Added new comment to " + selectedProject.toString());

            }
        });
        projectPanel.add(jsp);


        //edycja drugiej zakladki
        // addNewProjectButton
        JButton addNewProjectButton = new JButton("ADD NEW PROJECT");
        addNewProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                if(!LogWindow.czyManager){
                    JOptionPane.showMessageDialog(null, "Nie jestes managerem", null, JOptionPane.WARNING_MESSAGE);
                }else {
                    AddNewProjectWindow addNewProjectWindow = new AddNewProjectWindow(projectsListModel);
                }
            }
        });
        settings.add(addNewProjectButton);


        //addDeveloperToProject
        JButton addDeveloperToProject = new JButton("ADD DEVELOPER TO PROJECT");
        addDeveloperToProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                if(!LogWindow.getCzyManager()) {
                    JOptionPane.showMessageDialog(null, "Nie jestes managerem", null, JOptionPane.WARNING_MESSAGE);
                }else {
                    AddDeveloperToProjectWindow adtpw = new AddDeveloperToProjectWindow();
                }
            }
        });
        settings.add(addDeveloperToProject);


        //EditYourAccount
        JButton editYourAccountButton = new JButton("EDIT YOUR ACCOUNT");
        editYourAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                EditYourAccountWindow eyaw = new EditYourAccountWindow();
            }
        });
        settings.add(editYourAccountButton);
        //
        JButton addDeveloperButton = new JButton("ADD NEW DEVELOPER");
        addDeveloperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                AddNewDeveloper and = new AddNewDeveloper();
            }
        });
        settings.add(addDeveloperButton);


        settings.setLayout(new GridLayout(0,2));
        setSize(1500,1000);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
