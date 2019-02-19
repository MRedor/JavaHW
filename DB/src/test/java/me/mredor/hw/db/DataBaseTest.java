package me.mredor.hw.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    DataBase dataBase;

    @BeforeEach
    public void init() {
        dataBase = new DataBase("TelephoneNumbers");
        dataBase.clear();
    }

    @Test
    public void addOne() {
        dataBase.addRecord("Mary", "1");
    }

    @Test
    public void add() {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.addRecord("Mary", "2");
        List<Record> records = dataBase.getAllRecords();
        List<String> names = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        for (Record current: records) {
            names.add(current.getName());
            numbers.add(current.getNumber());
        }
        assertArrayEquals(new String[] {"Mary", "Ivan", "Mary"}, names.toArray());
        assertArrayEquals(new String[] {"1", "1", "2"}, numbers.toArray());
    }

    @Test
    public void delete() {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.addRecord("Mary", "2");
        dataBase.deleteRecord("Mary", "2");
        List<Record> records = dataBase.getAllRecords();
        List<String> names = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        for (Record current: records) {
            names.add(current.getName());
            numbers.add(current.getNumber());
        }
        assertArrayEquals(new String[] {"Mary", "Ivan"}, names.toArray());
        assertArrayEquals(new String[] {"1", "1"}, numbers.toArray());
    }

    @Test void deleteBad() {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.deleteRecord("Mary", "2");
        List<Record> records = dataBase.getAllRecords();
        List<String> names = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        for (Record current: records) {
            names.add(current.getName());
            numbers.add(current.getNumber());
        }
        assertArrayEquals(new String[] {"Mary", "Ivan"}, names.toArray());
        assertArrayEquals(new String[] {"1", "1"}, numbers.toArray());
    }

    @Test
    public void getByName() throws Exception {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.addRecord("Mary", "2");
        List<Record> records = dataBase.getByName("Mary");
        List<String> numbers = new ArrayList<>();
        for(Record current: records) {
            numbers.add(current.getNumber());
            //System.out.println(current.getNumber());
        }
        assertArrayEquals(new String[] {"1", "2"}, numbers.toArray());
    }

    @Test
    public void getByNumber() {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.addRecord("Mary", "2");
        List<Record> records = dataBase.getByNumber("1");
        List<String> names = new ArrayList<>();
        for(Record current: records) {
            names.add(current.getName());
            //System.out.println(current.getNumber());
        }
        assertArrayEquals(new String[] {"Mary", "Ivan"}, names.toArray());
    }


    @Test
    public void changeName() {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.addRecord("Mary", "2");
        dataBase.changeName("Mary", "2", "Nana");
        List<Record> records = dataBase.getAllRecords();
        List<String> names = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        for (Record current: records) {
            names.add(current.getName());
            numbers.add(current.getNumber());
        }
        assertArrayEquals(new String[] {"Mary", "Ivan", "Nana"}, names.toArray());
        assertArrayEquals(new String[] {"1", "1", "2"}, numbers.toArray());
    }

    @Test
    public void changeNumber() {
        dataBase.addRecord("Mary", "1");
        dataBase.addRecord("Ivan", "1");
        dataBase.addRecord("Mary", "2");
        dataBase.changeNumber("Mary", "2", "3");
        List<Record> records = dataBase.getAllRecords();
        List<String> names = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        for (Record current: records) {
            names.add(current.getName());
            numbers.add(current.getNumber());
        }
        assertArrayEquals(new String[] {"Mary", "Ivan", "Mary"}, names.toArray());
        assertArrayEquals(new String[] {"1", "1", "3"}, numbers.toArray());

    }
}