package edu.ntnu.trym.simulation;

public class Battle {
    private Army armyOne;
    private Army armyTwo;

    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;

    }

    public Army simulate(){

    }

    @Override
    public String toString() {
        return "\tBattle of the ages\n" +
                "Army One:\n" + armyOne +
                "\n Army Two:\n" + armyTwo;
    }
}
