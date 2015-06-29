package se.kentor.designdemo.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Ilya Klyukin.
 */
public class Country implements java.io.Serializable {

    private String name;
    private String population;
    private int rank;
    private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        //notice the order of read and write should be same
        setName((String) ois.readObject());
        setPopulation((String) ois.readObject());
        setFlag((String) ois.readObject());
        setRank(ois.readInt());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();

        oos.writeObject(getName());
        oos.writeObject(getPopulation());
        oos.writeObject(getFlag());
        oos.writeObject(getRank());
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", population='" + population + '\'' +
                ", rank=" + rank +
                ", flag='" + flag + '\'' +
                '}';
    }
}
