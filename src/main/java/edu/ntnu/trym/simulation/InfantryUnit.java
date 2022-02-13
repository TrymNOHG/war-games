package edu.ntnu.trym.simulation;

public class InfantryUnit extends Unit{

    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    public InfantryUnit(String name, int health) {
        super(name, health, 0, 0);
    }

    @Override
    public int getAttackBonus() {
        return 0;
    }

    @Override
    public int getResistBonus() {
        return 0;
    }
}
