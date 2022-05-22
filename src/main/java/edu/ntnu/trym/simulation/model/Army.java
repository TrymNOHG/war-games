package edu.ntnu.trym.simulation.model;

import edu.ntnu.trym.simulation.model.units.Unit;
import edu.ntnu.trym.simulation.model.units.UnitFactory;
import edu.ntnu.trym.simulation.model.units.UnitType;

import java.util.*;

/**
 * This is a class forming the structure of an army. It, therefore, contains the vital information
 * such as name of the army and the List which holds the militant units in the army. Additionally, the class allows
 * for the manipulation of the list and units within it.
 *
 * @author Trym Hamer Gudvangen
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
        //If there are null units in the list, it could also break. Should I throw exception for that?
        this.units = Objects.requireNonNullElseGet(units, ArrayList::new); //Should this be a deep copy so the lists aren't linked?
    }


    /**
     * This constructor creates a new Army object {@link #Army(String, List)} from an already existing one.
     * This is in order to create a deep copy of an army object.
     * @param army                      The army that will be copied, represented using an Army object
     * @throws NullPointerException     This exception is thrown if the army input is null.
     */
    public Army(Army army) throws NullPointerException{
        this(army.name, army.deepCopyArmyUnits());
    }

    /**
     * This method adds a new Unit to the army list, if it is not null.
     * @param unit A new militant unit, given as a Unit object
     */
    public void add(Unit unit){
        if(unit != null){
            this.units.add(unit);
        }
    }

    /**
     * This method takes a list of units as an input and places all of them within the Army's list, as long as the
     * list is not empty. It does not add null units.
     * @param units List of Units
     */
    public void addAll(List<Unit> units){
        if(units != null) {
            for (Unit unit : units) {
                if (unit != null) {
                    this.units.add(unit);
                }
            }
        }
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

    /**
     * This method sets the current terrain of all the units in the army {@link Unit#setCurrentTerrain(TerrainType)}.
     * @param terrain The current terrain, given as a TerrainType enumeration.
     */
    public void setAllUnitsTerrain(TerrainType terrain){
        this.units.forEach(unit -> unit.setCurrentTerrain(terrain));
    }

    /**
     * This method returns a random unit, if the units List is not empty/
     * @return A random unit, represented as a Unit object
     */
    public Unit getRandom(){
        if(this.units.size() == 0){
            return null;
        }
        int randomIndex = new Random().nextInt(this.units.size());
        return this.units.get(randomIndex);
    }

    /**
     * This method uses the filtering method {@link #getUnitsByType(UnitType)} to create a list of InfantryUnits.
     * @return The filtered list of solely InfantryUnits, represented as a List of Units
     */
    public List<Unit> getInfantryUnits(){
        return getUnitsByType(UnitType.INFANTRY);
    }

    /**
     * This method uses the filtering method {@link #getUnitsByType(UnitType)} to create a list of CavalryUnits.
     * @return The filtered list of solely CavalryUnits, represented as a List of Units
     */
    public List<Unit> getCavalryUnits(){
        return getUnitsByType(UnitType.CAVALRY);
    }

    /**
     * This method uses the filtering method {@link #getUnitsByType(UnitType)} to create a list of RangedUnits.
     * @return The filtered list of solely RangedUnits, represented as a List of Units
     */
    public List<Unit> getRangedUnits(){
        return getUnitsByType(UnitType.RANGED);
    }

    /**
     * This method uses the filtering method {@link #getUnitsByType(UnitType)} to create a list of CommanderUnits.
     * @return The filtered list of solely CommanderUnits, represented as a List of Units
     */
    public List<Unit> getCommanderUnits(){
        return getUnitsByType(UnitType.COMMANDER);
    }

    /**
     * This method uses a stream to filter through all the units in the list and based on the class to create
     * a new list containing solely the unit desired.
     * @param unitType  The type of unit, represented as a UnitType enumeration
     * @return          A list of all the units of the given type found in the Army's unit list
     */
    public List<Unit> getUnitsByType(UnitType unitType){
        return this.units.stream().filter(unit -> unit.getUnitType() == unitType).toList();
    }

    /**
     * This method deep copies every unit from an Army's unit list and distinguishes between
     * default units {@link UnitFactory#getDeepCopiedDefaultUnit(Unit)} and special units
     * {@link UnitFactory#getDeepCopiedSpecialUnit(Unit)}.
     * @return A list of deep copied units, represented using a List{@code <Unit>} object.
     */
    public List<Unit> deepCopyArmyUnits(){
        List<Unit> copiedUnitList = new ArrayList<>();

        this.units.forEach(unit -> {
            if(unit.isDefaultUnit()) copiedUnitList.add(UnitFactory.getDeepCopiedDefaultUnit(unit));
            else copiedUnitList.add(UnitFactory.getDeepCopiedSpecialUnit(unit));
        });

        return copiedUnitList;
    }

    /**
     * This method retrieves the name of the Army.
     * @return Name of the army, represented as a String
     */
    public String getName() {
        return name;
    }

    /**
     * This method changes the name of the Army.
     * @param name New name of the army, represented as a String
     */
    public void setName(String name) throws IllegalArgumentException{
        if(name.isEmpty() || name.isBlank()) throw new IllegalArgumentException("The Army name cannot be blank or empty.");
        this.name = name;
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
        for(Unit unit : units){
            if(army.units.stream().noneMatch(unit::equals)) return false;
        }
        return units.size() == army.getAllUnits().size();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }
}
