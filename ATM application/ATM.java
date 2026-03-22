import java.util.*;
import java.io.*;

class Account {
    String name;
    int pin;
    int balance;
    ArrayList<String> history = new ArrayList<>();
    boolean locked = false;
    int attempts = 0;

    Account(String name, int pin, int balance) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    void checkBalance() {
        System.out.println("Balance: " + balance);
    }

    void deposit(int amt) {
        if (amt > 0) {
            balance += amt;
            history.add("Deposited: " + amt);
            System.out.println("Deposited");
        } else {
            System.out.println("Invalid amount");
        }
    }

    void withdraw(int amt) {
        if (amt <= 0) {
            System.out.println("Invalid amount");
        } else if (amt > balance) {
            System.out.println("Insufficient balance");
        } else {
            balance -= amt;
            history.add("Withdrawn: " + amt);
            System.out.println("Withdrawn");
        }
    }

    void showHistory() {
        if (history.size() == 0) {
            System.out.println("No transactions");
        } else {
            for (String h : history) {
                System.out.println(h);
            }
        }
    }
}

public class ATM {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        ArrayList<Account> users = new ArrayList<>();

        File file = new File("accounts.txt");
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                users.add(new Account(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }
            br.close();
        } else {
            users.add(new Account("user1", 1234, 1000));
            users.add(new Account("user2", 1111, 2000));
        }

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Admin Panel");
            System.out.println("3. Exit");

            int mainChoice = sc.nextInt();

            if (mainChoice == 1) {
                System.out.println("Enter username:");
                String uname = sc.next();

                Account current = null;

                for (Account a : users) {
                    if (a.name.equals(uname)) {
                        current = a;
                    }
                }

                if (current == null) {
                    System.out.println("User not found");
                    continue;
                }

                if (current.locked) {
                    System.out.println("Account locked");
                    continue;
                }

                while (current.attempts < 3) {
                    System.out.println("Enter PIN:");
                    int p = sc.nextInt();

                    if (p == current.pin) {
                        current.attempts = 0;

                        while (true) {
                            System.out.println("\n1. Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Withdraw");
                            System.out.println("4. History");
                            System.out.println("5. Logout");

                            int ch = sc.nextInt();

                            if (ch == 1) current.checkBalance();
                            else if (ch == 2) {
                                int amt = sc.nextInt();
                                current.deposit(amt);
                            }
                            else if (ch == 3) {
                                int amt = sc.nextInt();
                                current.withdraw(amt);
                            }
                            else if (ch == 4) current.showHistory();
                            else if (ch == 5) break;
                            else System.out.println("Invalid");
                        }
                        break;
                    } else {
                        current.attempts++;
                        System.out.println("Wrong PIN");
                    }
                }

                if (current.attempts >= 3) {
                    current.locked = true;
                    System.out.println("Account locked due to 3 failed attempts");
                }

            } 
            else if (mainChoice == 2) {
                System.out.println("Enter admin password:");
                int adminPass = sc.nextInt();

                if (adminPass != 9999) {
                    System.out.println("Wrong password");
                    continue;
                }

                while (true) {
                    System.out.println("\n1. View Users");
                    System.out.println("2. Unlock User");
                    System.out.println("3. Save Data");
                    System.out.println("4. Back");

                    int ch = sc.nextInt();

                    if (ch == 1) {
                        for (Account a : users) {
                            System.out.println(a.name + " | Balance: " + a.balance + " | Locked: " + a.locked);
                        }
                    } 
                    else if (ch == 2) {
                        String uname = sc.next();
                        for (Account a : users) {
                            if (a.name.equals(uname)) {
                                a.locked = false;
                                a.attempts = 0;
                                System.out.println("Unlocked");
                            }
                        }
                    } 
                    else if (ch == 3) {
                        BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt"));
                        for (Account a : users) {
                            bw.write(a.name + "," + a.pin + "," + a.balance);
                            bw.newLine();
                        }
                        bw.close();
                        System.out.println("Saved");
                    } 
                    else if (ch == 4) break;
                    else System.out.println("Invalid");
                }
            } 
            else if (mainChoice == 3) {
                System.out.println("Exiting...");
                break;
            } 
            else {
                System.out.println("Invalid choice");
            }
        }
    }
}