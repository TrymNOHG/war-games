package edu.ntnu.trym.simulation.model.units;

/**
 * This class represents a commander unit, which is a subclass or "promotion" from the cavalry unit.
 * A commander unit has high base stats for both attack (25) and armor (15). The commander unit inherits the same
 * advantages as the cavalry unit in battle, with increased attack bonus in charges for example.
 *
 * @author Trym Hamer Gudvangen
 */
public class CommanderUnit extends CavalryUnit {

    /**
     * This constructor allows for a CommanderUnit Object to be initialized with the information that defines a
     * commander unit. It uses the CavalryUnit constructor in order to instantiate an object
     * {@link CavalryUnit#CavalryUnit(String, int, int, int)}.
     * @param name   The name of the CommanderUnit, represented as a String
     * @param health The health value of the CommanderUnit, represented as an int
     * @param attack The attack value of the CommanderUnit, represented as an int
     * @param armor  The armor value of the CommanderUnit, represented as an int
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * This is a simplified constructor with a pre-set attack (25) and armor (15) value for the CommanderUnit. It uses
     * the CavalryUnit constructor in order to instantiate an object
     * {@link CavalryUnit#CavalryUnit(String, int, int, int)}.
     * @param name   The name of the CommanderUnit, represented as a String
     * @param health The health value of the CommanderUnit, represented as an int
     */
    public CommanderUnit(String name, int health) {
        super(name, health, 25, 15);
    }

}
