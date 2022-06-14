import java.io.*;
import java.util.LinkedList;

public class Manager extends Person{

    static LinkedList<Manager> managers = new LinkedList<>();
    Manager(String name, String surname, String pesel, String password,String color) {
        super(name, surname, pesel, password,color);
        managers.add(this);
    }

    @Override
    public String toString() {
        return "Manager - Name: " + getName() + " " + getSurname();
    }

    public static void writeManagers(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("managers"));
            for (Manager m: managers) {
                writer.write("MANAGER\n");
                writer.write(m.name+";"+m.surname+";"+m.pesel+";"+m.password+";"+m.favouriteColor);

            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static LinkedList<Manager> readManagers() {
        LinkedList<Manager> m = new LinkedList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("managers"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("MANAGER")) {
                    String mData = reader.readLine();
                    String[] mArr = mData.split(";");
                    String mName = mArr[0];
                    String mSurname = mArr[1];
                    String mPesel = mArr[2];
                    String mPass = mArr[3];
                    String mColor = mArr[4];
                    m.add(new Manager(mName, mSurname, mPesel, mPass,mColor));
                }
            }
            return m;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
