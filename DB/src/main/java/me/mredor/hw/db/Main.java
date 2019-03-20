package me.mredor.hw.db;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/** Console application. Interactive phone book. Using mongodb.
 *
 *  Possible commands:
 *          "0"               - close the app
 *     "1 name number"        - add new pair (name, number)
 *        "2 name"            - find all telephone numbers with given name
 *       "3 number"           - find all users with given number
 *     "4 name number"        - delete pair from book
 *   "5 name number newName"  - change name in pair
 *  "6 name number newNumber" - change number in pair
 *          "7"               - print all pairs in the book
 * */
public class Main {

    public static void main(String[] args) throws IOException {
        PhoneBook book = new PhoneBook();
        Scanner input = new Scanner(System.in);
        int command = -1;
        System.out.println("Hello, it's a PhoneBook application! \n Available actions: ");
        System.out.println("         \"0\"               - close the app ");
        System.out.println("     \"1 name number\"        - add new pair (name, number)");
        System.out.println("       \"2 name\"            - find all telephone numbers with given name");
        System.out.println("       \"3 number\"           - find all users with given number");
        System.out.println("     \"4 name number\"        - delete pair from book");
        System.out.println("   \"5 name number newName\"  - change name in pair");
        System.out.println("  \"6 name number newNumber\" - change number in pair");
        System.out.println("          \"7\"               - print all pairs in the book");
        while (command != 0) {
            command = input.nextInt();
            switch (command) {
                case 1: {
                    String name = input.next();
                    String number = input.next();
                    book.add(name, number);
                    break;
                }
                case 2: {
                    String name = input.next();
                    book.printAllNumbersByName(name);
                    break;
                }
                case 3: {
                    String number = input.next();
                    book.printAllNamesByNumber(number);
                    break;
                }
                case 4: {
                    String name = input.next();
                    String number = input.next();
                    book.delete(name, number);
                    break;
                }
                case 5: {
                    String name = input.next();
                    String number = input.next();
                    String newName = input.next();
                    book.changeName(name, number, newName);
                    break;
                }
                case 6: {
                    String name = input.next();
                    String number = input.next();
                    String newNumber = input.next();
                    book.changeNumber(name, number, newNumber);
                    break;
                }
                case 7: {
                    book.printAll();
                    break;
                }
            }
        }
    }
}
