package edu.ntnu.trym.simulation;

public class CavalryUnit extends Unit{
    private int numAttack = 0;

    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    public CavalryUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    @Override
    public int getAttackBonus() {
        this.numAttack++;
        if(numAttack == 1){
            return 6;
        }

        return 2;
    }

    @Override
    public int getResistBonus() {
        return 1;
    }
}
