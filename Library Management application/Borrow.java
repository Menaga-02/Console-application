class Borrow{
    int userId;
    int bookId;
    int days;
    boolean returned=false;
    int extendCount=0;
    Borrow(int userId,int bookId,int days){
        this.userId=userId;
        this.bookId=bookId;
        this.days=days;
    }
}