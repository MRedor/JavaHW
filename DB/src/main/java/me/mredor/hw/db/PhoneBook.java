package me.mredor.hw.db;

import java.util.List;

public class PhoneBook {
    DataBase dataBase;
    public PhoneBook() {
        dataBase = new DataBase("PhoneBook");
    }
    public void add(String name, String number) {
        dataBase.addRecord(name, number);
    }
    public void printAllNumbersByName(String name) {
        List<Record> numbers = dataBase.getByName(name);
        for (Record current: numbers) {
            System.out.println(current.getNumber());
        }
    }
    public void printAllNamesByNumber(String number) {
        List<Record> names = dataBase.getByNumber(number);
        for (Record current : names) {
            System.out.println(current.getName());
        }
    }
    public void delete(String name, String number) {
        dataBase.deleteRecord(name, number);
    }
    public void changeName(String name, String number, String newName) {
        dataBase.changeName(name, number, newName);
    }
    public void changeNumber(String name, String number, String newNumber) {
        dataBase.changeNumber(name, number, newNumber);
    }
    public void printAll() {
        List<Record> records = dataBase.getAllRecords();
        for (Record current: records) {
            current.print();
        }
    }
}
