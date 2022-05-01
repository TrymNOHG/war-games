package edu.ntnu.trym.simulation;

import edu.ntnu.trym.simulation.units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UnitFactory {


    //Use this when file handling for example

//    public static Unit getUnit(UnitType type, String name, int healthValue){
//        switch(type){
//            case RANGED: return new RangedUnit(name, healthValue, );
//            case CAVALRY: return new CavalryUnit(name, healthValue);
//            case INFANTRY: return new InfantryUnit(name, healthValue);
//            case COMMANDER: return new CommanderUnit(name, healthValue);
//        }
//        return null;
//    }

    //Use this when file handling for example
    public static Unit getUnit(UnitType type, String name, int healthValue, int attackValue, int armorValue){
        return switch (type) {
            case RANGED -> new RangedUnit(name, healthValue, attackValue, armorValue);
            case CAVALRY -> new CavalryUnit(name, healthValue, attackValue, armorValue);
            case INFANTRY -> new InfantryUnit(name, healthValue, attackValue, armorValue);
            case COMMANDER -> new CommanderUnit(name, healthValue, attackValue, armorValue);
        };
    }

    //Use this when actually generating the army

//    public static List<Unit> getMultipleUnits(int numberOfUnits, UnitType type, String name, int healthValue){
//        List<Unit> listToBeReturned = new ArrayList<>();
//        IntStream.range(0, numberOfUnits).forEach(i -> listToBeReturned.add(getUnit(type, name, healthValue)));
//        return listToBeReturned;
//    }

    //Use this when actually generating the army
    public static List<Unit> getMultipleUnits(int numberOfUnits, UnitType type, String name, int healthValue,
                                              int attackValue, int armorValue){
        List<Unit> listToBeReturned = new ArrayList<>();
        IntStream.range(0, numberOfUnits).forEach(i -> listToBeReturned.add(getUnit(type, name, healthValue,
                attackValue, armorValue)));
        return listToBeReturned;
    }

    /*TODO: test that the getUnit works with the armyFileHandler
        Check that multipleUnits actually returns a list the correct size
        Check that multipleUnits returns the correct units
     */

}
