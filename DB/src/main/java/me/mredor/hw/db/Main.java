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
        DataBase dataBase = new DataBase("PhoneBook");
        Scanner input = new Scanner(System.in);

        while(true) {
            int command = input.nextInt();
            if (command == 0) {
                break;
            }
            if (command == 1) {
                String name = input.next();
                String number = input.next();
                dataBase.addRecord(name, number);
            }
            if (command == 2) {
                String name = input.next();
                List<Record> numbers = dataBase.getByName(name);
                for (Record current: numbers) {
                    System.out.println(current.getNumber());
                }
            }
            if (command == 3) {
                String number = input.next();
                List<Record> names = dataBase.getByNumber(number);
                for (Record current : names) {
                    System.out.println(current.getName());
                }
            }
            if (command == 4) {
                String name = input.next();
                String number = input.next();
                dataBase.deleteRecord(name, number);
            }
            if (command == 5) {
                String name = input.next();
                String number = input.next();
                String newName = input.next();
                dataBase.changeName(name, number, newName);
            }
            if (command == 6) {
                String name = input.next();
                String number = input.next();
                String newNumber = input.next();
                dataBase.changeNumber(name, number, newNumber);
            }
            if (command == 7) {
                List<Record> records = dataBase.getAllRecords();
                for (Record current: records) {
                    current.print();
                }
            }
        }
    }
}
