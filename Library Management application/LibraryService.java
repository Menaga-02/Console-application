import java.util.*;
class LibraryService{
    ArrayList<User> users=new ArrayList<>();
    ArrayList<Book> books=new ArrayList<>();
    ArrayList<Borrow> borrows=new ArrayList<>();
    Scanner sc=new Scanner(System.in);
    User login(){
        System.out.println("Enter email:");
        String email=sc.next();
        System.out.println("Enter paasword:");
        String password=sc.next();
        for(User u:users){
            if(u.email.equals(email)&&u.password.equals(password)){
                return u;
            }
        }
        return null;
    }
    void addBook(){
        System.out.println("Book Name: ");
        String name=sc.next();
        System.out.println("Author: ");
        String author=sc.next();
        System.out.println("ISBN: ");
        String isbn=sc.next();  
        System.out.println("Quantity: ");
        int quantity=sc.nextInt();
        System.out.println("Price: ");
        double price=sc.nextDouble();
        books.add(new Book(books.size()+1,name,author,isbn,quantity,price));
        System.out.println("Book added successfully");

    }

    Book searchByISBN(String isbn){
        for(Book b:books){
            if(b.isbn.equals(isbn)){
                return b;
            }
        }
        return null;
    }

    void borrowBook(User user){
        if(user.deposit<500){
            System.out.println("Deposit is less than 500. Please recharge your account.");
            return;
        }
        int count=0;
        for(Borrow b:borrows){
            if(b.userId==user.id&&!b.returned){
                count++;
            }
        }
        if(count>=3){
            System.out.println("You have already borrowed 3 books. Please return some books before borrowing new ones.");
            return;
        }
        System.out.println("Enter ISBN of the book you want to borrow:");
        String isbn=sc.next();
        Book book=searchByISBN(isbn);
        if(book==null || book.quantity==0){
            System.out.println("Book not available.");
            return;
        }

        for(Borrow b:borrows){
            if(b.userId==user.id&&b.bookId==book.id&&!b.returned){
                System.out.println("Already borrowed this book.");
                return;
            }
        }
        System.out.println("Enter days: ");
        int days=sc.nextInt();
        borrows.add(new Borrow(user.id,book.id,days));
        book.quantity--;
        book.borrowedCount++;
        System.out.println("Book Borrowed");
    }
    void returnBook(User user) {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        for (Borrow b : borrows) {
            if (b.userId == user.id && b.bookId == bookId && !b.returned) {

                Book book = books.get(bookId - 1);

                double fine = calculateFine(b.days, book.price);
                user.fine += fine;

                b.returned = true;
                book.quantity++;

                System.out.println("Returned. Fine: " + fine);
                return;
            }
        }

        System.out.println("No record found!");
    }

    
    double calculateFine(int days, double price) {
        if (days <= 15) return 0;

        int extra = days - 15;
        double fine = extra * 2;

        return Math.min(fine, price * 0.8);
    }

     void lowStock() {
        System.out.println("Low Stock Books:");
        for (Book b : books) {
            if (b.quantity < 2) {
                System.out.println(b.name);
            }
        }
    }

    void popularBooks() {
        System.out.println("Popular Books:");
        for (Book b : books) {
            if (b.borrowedCount > 3) {
                System.out.println(b.name);
            }
        }
    }
}