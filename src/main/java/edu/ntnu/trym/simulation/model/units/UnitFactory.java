package edu.ntnu.trym.simulation.model.units;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * This class represents a factory for producing Unit objects. It, therefore, has methods for instantiating a single
 * object from the information provided and methods for mass-producing a given unit.
 *
 * @author Trym Hamer Gudvangen
 */
public class UnitFactory {


    /**
     * This method takes in a UnitType and the simplified information for a unit in order to retrieve an instance of
     * the unit.
     * @param type          The type of unit, represented as a UnitType enumeration
     * @param name          The name of the unit, given as a String
     * @param healthValue   The health of the unit, represented as an integer
     * @return              A Unit object with the information specified
     */
    public static Unit getUnit(UnitType type, String name, int healthValue){
        return switch (type) {
            case RANGED -> new RangedUnit(name, healthValue);
            case CAVALRY -> new CavalryUnit(name, healthValue);
            case INFANTRY -> new InfantryUnit(name, healthValue);
            case COMMANDER -> new CommanderUnit(name, healthValue);
        };
    }

    /**
     * This method takes in a UnitType and the additional information for a unit in order to retrieve an instance of
     * the specialized unit.
     * @param type          The type of unit, represented as a UnitType enumeration
     * @param name          The name of the unit, given as a String
     * @param healthValue   The health of the unit, represented as an integer
     * @param attackValue   The attack value of the unit, represented as an integer
     * @param armorValue    The armor value of the unit, represented as an integer
     * @return              A Unit object with the information specified
     */
    public static Unit getUnit(UnitType type, String name, int healthValue, int attackValue, int armorValue) {
        return switch (type) {
            case RANGED -> new RangedUnit(name, healthValue, attackValue, armorValue);
            case CAVALRY -> new CavalryUnit(name, healthValue, attackValue, armorValue);
            case INFANTRY -> new InfantryUnit(name, healthValue, attackValue, armorValue);
            case COMMANDER -> new CommanderUnit(name, healthValue, attackValue, armorValue);
        };
    }

    /**
     * This method takes a default unit object and uses the factory methods to produce a deep copied version of it
     * {@link #getUnit(UnitType, String, int)}.
     * @param unit                      The unit to be copied, represented using a Unit object.
     * @return                          The new and copied unit, represented using a Unit object.
     * @throws NullPointerException This exception is thrown if the unit input is null
     */
    public static Unit getDeepCopiedDefaultUnit(Unit unit) throws NullPointerException{
        if(unit == null) throw new NullPointerException("Unit cannot be null");
        return getUnit(unit.getUnitType(), unit.getName(), unit.getHealth());
    }

    /**
     * This method takes a special unit object and uses the factory methods to produce a deep copied version of it
     * {@link #getUnit(UnitType, String, int)}.
     * @param unit                      The unit to be copied, represented using a Unit object.
     * @return                          The new and copied unit, represented using a Unit object.
     * @throws NullPointerException This exception is thrown if the unit input is null.
     */
    public static Unit getDeepCopiedSpecialUnit(Unit unit) throws NullPointerException{
        if(unit == null) throw new NullPointerException("Unit cannot be null");
        return getUnit(unit.getUnitType(), unit.getName(), unit.getHealth(), unit.getAttack(), unit.getArmor());
    }

    /**
     * This method creates a list of simplified units with the information below. The list will contain the amount
     * specified in the parameters of the same unit.
     * @param numberOfUnits     The number of units desired, given as an integer
     * @param type              The type of unit, represented as a UnitType enumeration
     * @param name              The name of the unit, given as a String
     * @param healthValue       The health of the unit, represented as an integer
     * @return                  An arrayList filled with the same unit with the given length
     */
    public static List<Unit> getMultipleUnits(int numberOfUnits, UnitType type, String name, int healthValue){
        if(numberOfUnits < 0) throw new IllegalArgumentException("The desired amount of units is invalid.");
        List<Unit> listToBeReturned = new ArrayList<>();
        IntStream.range(0, numberOfUnits).forEach(i -> listToBeReturned.add(getUnit(type, name, healthValue)));
        return listToBeReturned;
    }

    /**
     * This method creates a list of specialized units with the information below. The list will contain the amount
     * specified in the parameters of the same unit.
     * @param numberOfUnits     The number of units desired, given as an integer
     * @param type              The type of unit, represented as a UnitType enumeration
     * @param name              The name of the unit, given as a String
     * @param healthValue       The health of the unit, represented as an integer
     * @param attackValue       The attack value of the unit, represented as an integer
     * @param armorValue        The armor value of the unit, represented as an integer
     * @return                  An arrayList filled with the same unit with the given length
     */
    public static List<Unit> getMultipleUnits(int numberOfUnits, UnitType type, String name, int healthValue,
                                              int attackValue, int armorValue){
        if(numberOfUnits < 0) throw new IllegalArgumentException("The desired amount of units is invalid.");
        List<Unit> listToBeReturned = new ArrayList<>();
        IntStream.range(0, numberOfUnits).forEach(i -> listToBeReturned.add(getUnit(type, name, healthValue,
                attackValue, armorValue)));
        return listToBeReturned;
    }

    //TODO: test that the getUnit works with the armyFileHandler

}
