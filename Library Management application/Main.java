import java.util.*;

public class Main {
    
    public static void main(String[] args) {

        LibraryService lib = new LibraryService();

       
        lib.users.add(new User(1,"Admin","admin@mail.com","123","admin"));
        lib.users.add(new User(2,"User","user@mail.com","123","borrower"));

        lib.books.add(new Book(1,"Java","James","111",5,500));

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n==== LIBRARY SYSTEM ====");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = sc.nextInt();

            if(choice == 1) {
                User user = lib.login();

                if(user == null) {
                    System.out.println("Invalid login!");
                    continue;
                }

                
                if(user.role.equals("admin")) {
                    while(true) {
                        System.out.println("\n--- ADMIN MENU ---");
                        System.out.println("1. Add Book");
                        System.out.println("2. Low Stock Report");
                        System.out.println("3. Popular Books");
                        System.out.println("4. Logout");

                        int ch = sc.nextInt();

                        if(ch==1) lib.addBook();
                        else if(ch==2) lib.lowStock();
                        else if(ch==3) lib.popularBooks();
                        else break;
                    }
                }

                
                else {
                    while(true) {
                        System.out.println("\n--- BORROWER MENU ---");
                        System.out.println("1. Borrow Book");
                        System.out.println("2. Return Book");
                        System.out.println("3. Logout");

                        int ch = sc.nextInt();

                        if(ch==1) lib.borrowBook(user);
                        else if(ch==2) lib.returnBook(user);
                        else break;
                    }
                }
            }
            else {
                break;
            }
        }
    }
}