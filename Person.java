abstract public class Person {

    String name, surname,password;
    String pesel;
    String favouriteColor;
    Person(String name, String surname, String pesel, String password, String color){
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.password = password;
        this.favouriteColor = color;
    }

    public String getName() {
        return name;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }
}
