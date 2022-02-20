package edu.ntnu.trym.simulation;

import java.util.List;
import java.util.Objects;

public class Army {
    private String name;
    private List<Unit> units;

    public Army(String name) {
        this.name = name;
    }

    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void add(Unit unit){

    }

    public void addAll(List<Unit> units){

    }

    public void remove(Unit unit){

    }

    public boolean hasUnits(){
        return true;
    }

    public List<Unit> getAllUnits(){
        //Do we care about deep copies here?
        return units; //Wrong but just to have correct
    }

    public Unit getRandom(){
        //Returns a random unit from the list
        //Use java.util.Random to generate the random index
        return units.get(0); //Wrong but just to have correct
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Army)) return false;

        Army army = (Army) object;

        if (!Objects.equals(name, army.name)) return false;
        return Objects.equals(units, army.units);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }
}
