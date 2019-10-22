package dao;

public class User {
   private int id;
    String name;
    String surname;
    String email;
    String numberOfPhone;
    String login;
    String password;
    String typeOfUser;

    public User(String name, String surname, String email, String numberOfPhone, String login, String password, String typeOfUser) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.numberOfPhone = numberOfPhone;
        this.login = login;
        this.password = password;
        this.typeOfUser = typeOfUser;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
       if (this.id==0){
           this.id = id;
       }else{
           System.out.println("Cannot change ID");
       }

    }

    @Override
    public String toString() {
        return "dao.User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", numberOfPhone='" + numberOfPhone + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", typeOfUser='" + typeOfUser + '\'' +
                '}';
    }
}



