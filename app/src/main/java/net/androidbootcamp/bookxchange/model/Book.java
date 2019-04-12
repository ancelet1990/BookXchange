package net.androidbootcamp.bookxchange.model;

public class Book
{
    private String bookID, isbn, title, author, condition, price, photoURL, uid;
    private String search;
    private String bookPoster;
    private Boolean bookIsSold;

    public Book()
    {

    }

//    public Book(String bookID, String isbn, String title, String author, String condition, String price, String photoURL, String uid, String search, Boolean bookIsSold)
//    {
//        this.bookID = bookID;
//        this.isbn = isbn;
//        this.title = title;
//        this.author = author;
//        this.condition = condition;
//        this.price = price;
//        this.photoURL = photoURL;
//        this.uid = uid;
//        this.search = search;
//        this.bookIsSold = bookIsSold;
//    }

    public void setBookID(String bookID) { this.bookID = bookID; }

    public String getBookID() { return bookID; }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String getCondition()
    {
        return this.condition;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getPhotoURL()
    {
        return photoURL;
    }

    public void setPhotoURL(String photoURL)
    {
        this.photoURL = photoURL;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public Boolean getBookIsSold()
    {
        return bookIsSold;
    }

    public void setBookIsSold(Boolean bookIsSold)
    {
        this.bookIsSold = bookIsSold;
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    public String getBookPoster()
    {
        return bookPoster;
    }

    public void setBookPoster(String bookPoster)
    {
        this.bookPoster = bookPoster;
    }
}
