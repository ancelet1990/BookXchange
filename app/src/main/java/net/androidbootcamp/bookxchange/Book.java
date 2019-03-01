package net.androidbootcamp.bookxchange;

class Book {

    String isbn, title, edition, author, condition, price;


    public Book() {

    }

    public Book(String isbn, String title, String edition, String author, String condition, String price){
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.author = author;
        this.condition = condition;
        this.price = price;
    }
}
