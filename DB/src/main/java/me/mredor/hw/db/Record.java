package me.mredor.hw.db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/** Entry in a phone book database. */
@Entity
public class Record {
    @Id private ObjectId id;

    private String name;
    private String number;

    public Record() {}

    public Record(String name, String number) {
        this.id = new ObjectId();
        this.name = name;
        this.number = number;
    }

    public void print() {
        System.out.println(name + ": " + number);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
