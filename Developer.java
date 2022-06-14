import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.io.*;
import java.util.LinkedList;

public class Developer extends Person {

    static LinkedList<Developer> developers = new LinkedList<>();

    Developer(String name, String surname, String pesel,String password, String color) {
        super(name, surname, pesel,password, color);
        developers.add(this);
    }


    @Override
    public String toString() {
        return "Developer - Name: " + getName() + " " + getSurname();
    }

    public static void writeDevelopers(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("projektGui2/developers"));
            for (Developer d: developers) {
                writer.write("DEVELOPER\n");
                writer.write(d.name+";"+d.surname+";"+d.pesel+";"+d.password+";"+ d.favouriteColor+"\n");

            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static LinkedList<Developer> readDevelopers() {
        LinkedList<Developer> d = new LinkedList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("projektGui2/developers"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("DEVELOPER")) {
                    String dData = reader.readLine();
                    String[] dArr = dData.split(";");
                    String dName = dArr[0];
                    String dSurname = dArr[1];
                    String dPesel = dArr[2];
                    String dPass = dArr[3];
                    String dColor = dArr[4];
                    d.add(new Developer(dName, dSurname, dPesel, dPass,dColor));
                }
            }
            return d;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }

