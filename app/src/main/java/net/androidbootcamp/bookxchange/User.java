package net.androidbootcamp.bookxchange;

public class User {
    String firstName, lastName, email, school;


    public User() {

    }
    public User(String firstName, String lastName, String email, String school) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
    }

    public String getFirstName() {return firstName;}
    public void setName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getSchool() {return school;}
    public void setSchool(String school) {this.school = school;}
}
