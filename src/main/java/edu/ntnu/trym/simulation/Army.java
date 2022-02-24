package edu.ntnu.trym.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This is a class forming the structure of an army. It, therefore, contains the vital information
 * such as name of the army and the List which holds the militant units in the army. Additionally, the class allows
 * for the manipulation of the list and units within it.
 *
 * @author Trym
 */
public class Army {
    private String name;
    private List<Unit> units;

    /**
     * This constructor creates an Army object which represents a list over what units are contained. Here,
     * the Army is only named and there are no Units in the List. It is instantiated through the use of the two-argument
     * constructor {@link #Army(String, List)}.
     * @param name Name of the army, represented as a String
     */
    public Army(String name){
        this(name, null);
    }

    /**
     * This constructor creates an Army object which represents a list over what units are contained. The two instance
     * variables, name and units, are set according to the parameters.
     * @param name                      Name of the army, represented as a String
     * @param units                     List of militant units that create the army
     * @throws IllegalArgumentException If the name, String, is empty or blank, this exception is thrown.
     */
    public Army(String name, List<Unit> units) throws IllegalArgumentException{
        if(name.isEmpty() || name.isBlank()) throw new IllegalArgumentException("The Army name is invalid");
        this.name = name;
        if(units == null){
            this.units = new ArrayList<>();
        }
        this.units = units;
    }

    /**
     * This method retrieves the name of the Army.
     * @return Name of the army, represented as a String
     */
    public String getName() {
        return name;
    }

    /**
     * This method adds a new Unit to the army list.
     * @param unit A new militant unit, given as a Unit object
     */
    public void add(Unit unit){
        this.units.add(unit);
    }

    /**
     * This method takes a list of units as an input and places all of them within the Army's list.
     * @param units List of Units
     */
    public void addAll(List<Unit> units){
        this.units.addAll(units);
    }

    /**
     * This method removes the unit given as input from the Army list, if it exists within the list.
     * @param unit A militant unit, given as a Unit object
     */
    public void remove(Unit unit){
        this.units.removeIf(unitInQuestion -> unitInQuestion.equals(unit));
    }

    /**
     * This method checks whether the Army list has any units in it.
     * @return A boolean representing the status of the Army list, true if it has units and false if empty.
     */
    public boolean hasUnits(){
        return this.units.size() > 0;
    }

    /**
     * This method returns the Army list of units.
     * @return Army's list of units, represented through a List
     */
    public List<Unit> getAllUnits(){
       return this.units;
    }

    public Unit getRandom(){
        int randomIndex = new Random().nextInt(this.units.size());
        return this.units.get(randomIndex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("'s Army:\n");
        for (Unit unit:this.units) {
            sb.append(unit.toString()).append("\n");

        }
        return sb.toString();
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
