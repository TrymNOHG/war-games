package edu.ntnu.trym.simulation.units;


import edu.ntnu.trym.simulation.TerrainType;

/**
 * This class represents a cavalry unit. A cavalry unit has medium-high base stats for both attack (20) and armor (12).
 * A cavalry unit excels in charges, which is shown through the additional attack bonus that the unit gets during the
 * first attack. Other than that, the class shows the general offensive and defensive advantages that a cavalry unit
 * would have.
 *
 * @author Trym Hamer Gudvangen
 */
public class CavalryUnit extends Unit {
    private int numAttack = 0;

    /**
     * This constructor allows for a CavalryUnit Object to be initialized with the information that defines a
     * cavalry unit. It uses the Unit constructor in order to instantiate an object
     * {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the CavalryUnit, represented as a String
     * @param health The health value of the CavalryUnit, represented as an int
     * @param attack The attack value of the CavalryUnit, represented as an int
     * @param armor  The armor value of the CavalryUnit, represented as an int
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * This is a simplified constructor with a pre-set attack (20) and armor (12) value for the CavalryUnit. It uses
     * the Unit constructor in order to instantiate an object {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the CavalryUnit, represented as a String
     * @param health The health value of the CavalryUnit, represented as an int
     */
    public CavalryUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    /**
     * {@inheritDoc}
     * Since the CavalryUnit is good at charging, its default, initial attack bonus is 6; however, as the fighting
     * continues, the default CavalryUnit's attack bonus drops to 2. When the terrain is Plains, a 10 point bonus
     * is added to the attack bonus, regardless of when.
     * @return The attack bonus of the cavalry unit, represented as an int.
     */
    @Override
    public int getAttackBonus() {
        int terrainBonus = 0;
        if(this.getCurrentTerrain() == TerrainType.PLAINS) terrainBonus = 10;
        this.numAttack++;
        if(numAttack == 1){
            return 6 + terrainBonus;
        }
        return 2 + terrainBonus;
    }

    /**
     * {@inheritDoc}
     * The CavalryUnit's default resistance bonus is 1, since it only has a small advantage in defense. When the
     * cavalry unit fights in the Forest terrain, it has no resistance bonus.
     * @return The resistance bonus of the cavalry unit, represented as an int.
     */
    @Override
    public int getResistBonus() {
        if(this.getCurrentTerrain() == TerrainType.FOREST) return 0;
        return 1;
    }
}
