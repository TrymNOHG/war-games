package edu.ntnu.trym.simulation;

public class RangedUnit extends Unit{
    private int numAttackedByOpponent = 0;

    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    public RangedUnit(String name, int health) {
        super(name, health, 15, 8);
    }

    @Override
    public int getAttackBonus() {
        return 3;
    }

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
