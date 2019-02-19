package me.mredor.hw.db;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

/** Class directly working with database */
public class DataBase {
    private Morphia morphia;
    private Datastore datastore;

    /** Removes everything. */
    public void clear() {
        datastore.getDB().dropDatabase();
    }

    /** Creates new wmpty database */
    public DataBase(String name) {
        morphia = new Morphia();
        morphia.mapPackage("me.mredor.hw.db");
        datastore = morphia.createDatastore(new MongoClient(), name);
    }

    /** Adds pair (name, number) to the database. */
    public void addRecord(String name, String number) {
        datastore.save(new Record(name, number));
    }

    /** Deletes pair (name, number) from database. */
    public void deleteRecord(String name, String number) {
        List<Record> record = datastore.createQuery(Record.class).field("name").equal(name).field("number").equal(number).asList();
        if (record == null || record.size() == 0) {
            return;
        }
        datastore.delete(record.get(0));
    }

    /** Finds all pairs with the given name. */
    public List<Record> getByName(String name) {
        return datastore.createQuery(Record.class).field("name").equal(name).asList();
    }

    /** Finds all pairst with the given number */
    public List<Record> getByNumber(String number) {
        return datastore.createQuery(Record.class).field("number").equal(number).asList();
    }

    /** Returns all content of the book. */
    public List<Record> getAllRecords() {
        return datastore.createQuery(Record.class).asList();
    }

    /** Finds pair with given name and number and changes name in it.*/
    public void changeName(String name, String number, String newName) {
        deleteRecord(name, number);
        addRecord(newName, number);
    }

    /** Finds pair with given name and number and changes number in it. */
    public void changeNumber(String name, String number, String newNumber) {
        deleteRecord(name, number);
        addRecord(name, newNumber);
    }
}
