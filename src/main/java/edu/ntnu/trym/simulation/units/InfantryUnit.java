package edu.ntnu.trym.simulation.units;

import edu.ntnu.trym.simulation.units.Unit;

/**
 * This class represents an infantry unit. An infantry unit has medium base stats for both attack (15) and armor
 * (10). The class shows the different offensive and defensive advantages that an infantry unit would have in a battle.
 *
 * @author Trym
 */
public class InfantryUnit extends Unit {

    /**
     * This constructor allows for an InfantryUnit Object to be initialized with the information that defines an
     * infantry unit. It uses the Unit constructor in order to instantiate an object
     * {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the InfantryUnit, represented as a String
     * @param health The health value of the InfantryUnit, represented as an int
     * @param attack The attack value of the InfantryUnit, represented as an int
     * @param armor  The armor value of the InfantryUnit, represented as an int
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * This is a simplified constructor with a pre-set attack (15) and armor (10) value for the InfantryUnit. It uses
     * the Unit constructor in order to instantiate an object {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the InfantryUnit, represented as a String
     * @param health The health value of the InfantryUnit, represented as an int
     */
    public InfantryUnit(String name, int health) {
        super(name, health, 15, 10);
    }

    /**
     * {@inheritDoc}
     * The InfantryUnit's attack bonus is 2.
     * @return Attack bonus, which is equal to 2.
     */
    @Override
    public int getAttackBonus() {
        return 2;
    }

    /**
     * {@inheritDoc}
     * The InfantryUnit's resistance bonus is 1.
     * @return Resistance bonus, which is equal to 1.
     */
    @Override
    public int getResistBonus() {
        return 1;
    }
}
