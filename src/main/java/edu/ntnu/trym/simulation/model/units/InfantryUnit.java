package edu.ntnu.trym.simulation.model.units;

import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.UnitType;

/**
 * This class represents an infantry unit. An infantry unit has medium base stats for both attack (15) and armor
 * (10). The class shows the different offensive and defensive advantages that an infantry unit would have in a battle.
 *
 * @author Trym Hamer Gudvangen
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
        this.setUnitType(UnitType.INFANTRY);
    }

    /**
     * This is a simplified constructor with a pre-set attack (15) and armor (10) value for the InfantryUnit. It uses
     * the Unit constructor in order to instantiate an object {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the InfantryUnit, represented as a String
     * @param health The health value of the InfantryUnit, represented as an int
     */
    public InfantryUnit(String name, int health) {
        super(name, health, 15, 10);
        this.setUnitType(UnitType.INFANTRY);
        this.setDefaultUnit(true);
    }

    /**
     * {@inheritDoc}
     * The InfantryUnit's attack bonus is 12 when in a FOREST terrain, otherwise it is 2.
     * @return The attack bonus of the infantry unit, represented as an int.
     */
    @Override
    public int getAttackBonus() {
        if(this.getCurrentTerrain() == TerrainType.FOREST){
            return 12;
        }
        return 2;
    }

    /**
     * {@inheritDoc}
     * The InfantryUnit's resistance bonus is 11 when in a FOREST terrain, otherwise it is 1.
     * @return The resistance bonus of the infantry unit, represented as an int.
     */
    @Override
    public int getResistBonus() {
        if(this.getCurrentTerrain() == TerrainType.FOREST){
            return 11;
        }
        return 1;
    }
}
