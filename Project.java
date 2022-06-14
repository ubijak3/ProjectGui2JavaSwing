import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

enum ProjectStatus{ONGOING,PLANNED,FINISHED};

public class Project {

    ProjectStatus projectStatus;
    String projectName, comment;
    Manager projectManager;
    LinkedList<Developer> projectDeveloperTeam = new LinkedList<>();
    static LinkedList<Project> projectList = new LinkedList<>();
    Project(String projectName, String comment, ProjectStatus status){
        this.projectName=projectName;
        this.comment = comment;
        projectList.add(this);
        projectStatus=status;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
        Project.writeProjects();
    }

    public String getProjectName() {
        return projectName;
    }
    public void setProjectManager(Manager projectManager) {
        this.projectManager = projectManager;
    }
    public void addComment(String txt, String who){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        this.comment +=who+ " "+formatter.format(date)+ ": "  + txt;
        writeProjects();

    }
    public String getComment() {
        return comment;
    }
    public static LinkedList<Project> getProjectList() {
        return projectList;
    }
    public void addDeveloperToProject(Developer developer){
        projectDeveloperTeam.add(developer);
        writeDeveloperToProject();
    }

    public static LinkedList<Project> readProjects(){
        LinkedList<Project> p = new LinkedList<Project>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("projektGui2/projects"));
            String line;
            while((line = reader.readLine()) != null) {
                if(line.equals("PROJECT")){
                        String pData = reader.readLine();
                        String[] pArr = pData.split(";");
                        String pName = pArr[0];
                        String pComment = pArr[1];
                        String pStatus = pArr[2];
                        switch (pStatus){
                            case "PLANNED":
                                p.add(new Project(pName, pComment, ProjectStatus.PLANNED));
                                break;
                            case "ONGOING":
                                p.add(new Project(pName, pComment, ProjectStatus.ONGOING));
                                break;
                            case "FINISHED":
                                p.add(new Project(pName, pComment, ProjectStatus.FINISHED));
                                break;
                        }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
    public static LinkedList<Project> readProjectsForDev(){
        LinkedList<Project> p = new LinkedList<Project>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("projektGui2/projects"));
            String line;
            while((line = reader.readLine()) != null) {
                if(line.equals("PROJECT")){
                    String pData = reader.readLine();
                    String[] pArr = pData.split(";");
                    String pName = pArr[0];
                    String pComment = pArr[1];
                    String pStatus = pArr[2];
                    switch (pStatus){
                        case "PLANNED":
                            p.add(new Project(pName, pComment, ProjectStatus.PLANNED));
                            break;
                        case "ONGOING":
                            p.add(new Project(pName, pComment, ProjectStatus.ONGOING));
                            break;
                        case "FINISHED":
                            p.add(new Project(pName, pComment, ProjectStatus.FINISHED));
                            break;
                    }
                }
            }

            Project.readDeveloperInProject();
            LinkedList<Project> devProjects = new LinkedList<>();
            if (LogWindow.loggedPerson instanceof Developer) {
                Developer dev = (Developer) LogWindow.loggedPerson;
                for (Project project : p) {
                    if (project.projectDeveloperTeam.contains(dev)) {
                        devProjects.add(project);
                    }
                }
                p.clear();
                p.addAll(devProjects);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public static void writeProjects(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("projektGui2/projects"));
            for (Project p : projectList) {
                writer.write("PROJECT\n");
                String status = "";
                switch (p.projectStatus){
                    case ONGOING -> status = "ONGOING";
                    case PLANNED -> status = "PLANNED";
                    case FINISHED -> status = "FINISHED";
                }
                writer.write(p.projectName+";"+p.comment+";"+status+ "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeDeveloperToProject(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("projektGui2/developerInProject"));
            for (Project p : projectList) {
                for (Developer d : p.projectDeveloperTeam) {
                    writer.write("DEVINPROJ\n");
                    writer.write(d.pesel+";"+p.projectName+"\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readDeveloperInProject(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("projektGui2/developerInProject"));
            String line;
            while ((line = reader.readLine()) != null){
                if(line.equals("DEVINPROJ")){
                    String Data = reader.readLine();
                    String[] Arr = Data.split(";");
                    String dPesel = Arr[0];
                    String pName = Arr[1];
                    Project proj = findProject(pName);
                    Developer toAdd = findDeveloper(dPesel);
                    proj.addDeveloperToProject(toAdd);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Project findProject(String name){
        Project find = null;
        for (Project p : projectList) {
            if(p.projectName.equals(name))
                find = p;
        }
        return find;
    }
    public static Developer findDeveloper(String pesel){
        Developer find = null;
        for (Developer d : Developer.developers) {
            if(d.pesel.equals(pesel))
                find = d;
        }
        return find;
    }

    @Override
    public String toString() {
        return "Project: " + projectName;
    }
}
