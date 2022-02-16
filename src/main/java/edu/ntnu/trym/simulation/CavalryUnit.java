package edu.ntnu.trym.simulation;

/**
 * This class represents a cavalry unit. A cavalry unit has medium-high base stats for both attack (20) and armor (12).
 * A cavalry unit excels in charges, which is shown through the additional attack bonus that the unit gets during the
 * first attack. Other than that, the class shows the general offensive and defensive advantages that a cavalry unit
 * would have.
 *
 * @author Trym
 */
public class CavalryUnit extends Unit{
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
     * Since the CavalryUnit is good at charging, its initial attack bonus is 6; however, as the fighting continues,
     * the CavalryUnit's attack bonus drops to 2.
     * @return Attack bonus, which is 6 during first attack(charge) and 2 thereafter.
     */
    @Override
    public int getAttackBonus() {
        this.numAttack++;
        if(numAttack == 1){
            return 6;
        }

        return 2;
    }

    /**
     * {@inheritDoc}
     * The CavalryUnit's resistance bonus is 1, since it only has a small advantage in defense.
     * @return Resistance bonus, which is equal to 1.
     */
    @Override
    public int getResistBonus() {
        return 1;
    }
}
