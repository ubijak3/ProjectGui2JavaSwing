import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDeveloperToProjectWindow extends JFrame{

    JSplitPane jsp = new JSplitPane();
    JList developerList = new JList();
    DefaultListModel developerListModel = new DefaultListModel();
    JList projectsList = new JList();
    DefaultListModel projectListModel = new DefaultListModel();
    JButton addDevToProjButton = new JButton("ADD DEV TO PROJECT");
    Developer selectedDev;
    Project selectedProj;

    public void kill(){
        this.dispose();
    }
    AddDeveloperToProjectWindow(){
        setLayout(new BorderLayout());
        jsp.setLeftComponent(new JScrollPane(projectsList));
        projectListModel.addAll(Project.readProjects());
        projectsList.setModel(projectListModel);
        projectsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                SessionTimer.resetTime();
                selectedProj = (Project) projectsList.getSelectedValue();
            }
        });


        jsp.setRightComponent(new JScrollPane(developerList));
        developerListModel.addAll(Developer.readDevelopers());
        developerList.setModel(developerListModel);
        developerList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                SessionTimer.resetTime();
                selectedDev = (Developer) developerList.getSelectedValue();
            }
        });

        addDevToProjButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionTimer.resetTime();
                if(selectedProj.projectDeveloperTeam.size() >= 2){
                    JOptionPane.showMessageDialog(null, "Max number of developers in team is 2", null, JOptionPane.WARNING_MESSAGE);
                }else {
                    selectedProj.addDeveloperToProject(selectedDev);
                    Log.writeLog(LogWindow.loggedPerson, "Added " + selectedDev.toString() + " to " + selectedProj.toString());
                    kill();
                }
            }
        });


        add(jsp);
        add(addDevToProjButton,BorderLayout.SOUTH);
        setSize(500,500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
