package net.androidbootcamp.bookxchange;

class Book
{

    String isbn, title, edition, author, condition, price, photoURL, uid;
    Boolean bookIsSold;

    public Book()
    {

    }

    public Book(String isbn, String title, String edition, String author, String condition, String price, String photoURL, String uid, Boolean bookIsSold)
    {
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.author = author;
        this.condition = condition;
        this.price = price;
        this.photoURL = photoURL;
        this.uid = uid;
        this.bookIsSold = bookIsSold;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return this.condition;
    }
}
