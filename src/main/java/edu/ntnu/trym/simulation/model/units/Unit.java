package edu.ntnu.trym.simulation.model.units;

import edu.ntnu.trym.simulation.model.TerrainType;

import java.util.Objects;

/**
 * This is a class which creates the general structure of a militant unit. It, therefore, contains the vital information
 * such as name, health, attack, and armor stats. Additionally, the class outlines how attacks against another Unit are
 * calculated and allows for a description in the form of a String to be created.
 *
 * @author Trym Hamer Gudvangen
 */
public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;
    private TerrainType currentTerrain = TerrainType.DEFAULT;

    /**
     * This constructor allows for a Unit Object to be initialized with the information intrinsic to a military Unit.
     * @param name                      The name of the Unit, represented as a String.
     * @param health                    The health of the Unit, represented as an int.
     * @param attack                    The attack value (damage) of the Unit, represented as an int.
     * @param armor                     The armor value (resistance) of the Unit, represented as an int.
     * @throws IllegalArgumentException If health, attack, or armor are below 0
     */
    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        if(name.isBlank() || name.isEmpty()) throw new IllegalArgumentException("Name is invalid");
        this.name = name;
        if(health <= 0) throw new IllegalArgumentException("Health cannot be below 0");
        this.health = health;
        if(attack < 0) throw new IllegalArgumentException("Attack cannot be below 0");
        this.attack = attack;
        if(armor < 0) throw new IllegalArgumentException("Attack cannot be below 0");
        this.armor = armor;
    }

    /**
     * This method simulates the unit attacking another opponent. Therefore, the purpose of this method is to show how
     * this Unit damages/affects the health of the opponent Unit.
     * @param opponent This is a Unit of the opposing side.
     */
    public void attack(Unit opponent){
        int thisDamage = this.getAttack() + this.getAttackBonus();
        int opponentResistance = opponent.getArmor() + opponent.getResistBonus();
        opponent.setHealth(opponent.getHealth() - thisDamage + opponentResistance);
    }

    /**
     * This method retrieves the name of the given Unit.
     * @return String containing the name of the Unit.
     */
    public String getName() {
        return name;
    }

    /**
     * This method retrieves the health value of the given Unit.
     * @return Health value represented as int.
     */
    public int getHealth() {
        return health;
    }

    /**
     * This method retrieves the attack value of the given Unit.
     * @return Attack value represented as int.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * This method retrieves the armor value of the given Unit.
     * @return Armor value represented as an int.
     */
    public int getArmor() {
        return armor;
    }

    /**
     * This method changes this Unit's health value to the input value.
     * @param health The new health of the Unit, represented as an int.
     */
    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    /**
     * This method retrieves the Unit's current terrain.
     * @return Unit's current terrain, given as a TerrainType enum.
     */
    public TerrainType getCurrentTerrain() {
        return currentTerrain;
    }

    /**
     * This method changes this Unit's current terrain to the input value.
     * @param currentTerrain The new terrain of the unit, represented as a TerrainType enum.
     */
    public void setCurrentTerrain(TerrainType currentTerrain) {
        this.currentTerrain = currentTerrain;
    }

    /**
     * This method gives a description of the Unit, containing useful information/stats.
     * @return Description in the form of a String.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("'s Stats:\nUnit Type: ").append(this.getClass().getSimpleName())
                .append("\nHealth: ").append(this.health).append("\nAttack: ").append(this.attack)
                .append("\nAttack Bonus: ").append(this.getAttackBonus()).append("\nArmor: ").append(this.armor)
                .append("\nResistance Bonus: ").append(this.getResistBonus()).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit unit)) return false;

        if (health != unit.health) return false;
        if (attack != unit.attack) return false;
        if (armor != unit.armor) return false;
        return Objects.equals(name, unit.name);
    }

    /**
     * This method will return the attack bonus when a Unit attacks an opposing Unit.
     * @return Attack bonus represented as an int.
     */
    public abstract int getAttackBonus();

    /**
     * This method will return the resistance bonus when a Unit is attacked by an opposing Unit.
     * @return Resistance bonus represented as an int.
     */
    public abstract int getResistBonus();

}