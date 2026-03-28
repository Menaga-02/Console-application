class Book{
    int id;
    String name;
    String author;
    String isbn;
    int quantity;
    double price;
    int borrowedCount=0;
    Book(int id,String name,String author,String isbn,int quantity,double price){
        this.id=id;
        this.name=name;
        this.author=author;
        this.isbn=isbn;
        this.quantity=quantity;
        this.price=price;
    }   

}