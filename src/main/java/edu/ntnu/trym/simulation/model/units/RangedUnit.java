package edu.ntnu.trym.simulation.model.units;

import edu.ntnu.trym.simulation.model.TerrainType;

/**
 * This class represents a ranged unit. A ranged unit has medium-low base stats for attack (15) and low for armor (12).
 * A ranged unit excels in distanced-attacks, which is shown through the additional attack bonus (3) that the unit gets.
 * The ranged unit is also good at defending incoming (distanced) attacks. Therefore, the initial defense gives the
 * ranged unit a higher resistance bonus of 6, decreasing to 4 and then 2 as the Opponent comes closer.
 *
 * @author Trym Hamer Gudvangen
 */
public class RangedUnit extends Unit {
    private int numAttackedByOpponent = 0;

    /**
     * This constructor allows for a RangedUnit Object to be initialized with the information that defines a
     * ranged unit. It uses the Unit constructor in order to instantiate an object
     * {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the RangedUnit, represented as a String
     * @param health The health value of the RangedUnit, represented as an int
     * @param attack The attack value of the RangedUnit, represented as an int
     * @param armor  The armor value of the RangedUnit, represented as an int
     */
    public RangedUnit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        super(name, health, attack, armor);
    }

    /**
     * This is a simplified constructor with a pre-set attack (15) and armor (8) value for the RangedUnit. It uses
     * the Unit constructor in order to instantiate an object {@link Unit#Unit(String, int, int, int)}.
     * @param name   The name of the RangedUnit, represented as a String
     * @param health The health value of the RangedUnit, represented as an int
     */
    public RangedUnit(String name, int health) throws IllegalArgumentException {
        super(name, health, 15, 8);
        this.setDefaultUnit(true);
    }

    /**
     * {@inheritDoc}
     * The RangedUnit has the advantage of fighting from a distance. Therefore, they have a medium-high attack bonus of
     * three as default. In the Hill terrain, they have an attack bonus of 13, while in the Forest terrain they only
     * have 1 attack bonus.
     * @return The attack bonus of the ranged unit, represented as an int.
     */
    @Override
    public int getAttackBonus() {
        if(this.getCurrentTerrain() == TerrainType.HILL) return 13;
        else if(this.getCurrentTerrain() == TerrainType.FOREST) return 1;
        return 3;
    }

    /**
     * {@inheritDoc}
     * The RangedUnit has the advantage of defending from a distance at first. Therefore, the default resistance bonus
     * starts at a high value of 6 (first defense). For the next attack, the Opponent is closer and so the resistance
     * bonus drops to 4 (second defense) and eventually there is no longer a distance, and it remains at 2 (the rest).
     * @return The resistance bonus of the ranged unit, represented as an int.
     */
    @Override
    public int getResistBonus() {
        this.numAttackedByOpponent++;
        if(this.numAttackedByOpponent == 1){
            return 6;
        }
        else if(this.numAttackedByOpponent == 2){
            return 4;
        }
        return 2;
    }
}
