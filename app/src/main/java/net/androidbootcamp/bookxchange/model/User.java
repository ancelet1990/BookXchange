package net.androidbootcamp.bookxchange.model;

public class User
{
    private String firstName;
    private String lastName;
    private String email;
    private String school;
    private String id;
    private String imageURL;
    private String status;

    public User()
    {

    }

    public User(String firstName, String lastName, String email, String school, String id, String imageURL, String status)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
        this.id = id;
        this.imageURL = imageURL;
        this.status = status;
    }

    public User(String firstName, String lastName, String email, String school, String id, String imageURL)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
        this.id = id;
        this.imageURL = imageURL;
    }

    public User(String firstName, String lastName, String email, String school, String id)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
        this.id = id;
    }

    public User(String firstName, String lastName, String email, String school)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.school = school;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
