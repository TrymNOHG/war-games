package edu.ntnu.trym.simulation;

public class Battle {
    private Army armyOne;
    private Army armyTwo;
    private Army[] armyArr;
    private int armyFightCount;

    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.armyArr = new Army[]{armyOne, armyTwo};
        this.armyFightCount = randomStartArmy();
    }

    private int randomStartArmy(){
        return (int) Math.round(Math.random());
    }

    public Army simulate(){
        //Choosing fighters
        Unit fightingOpponent = armyArr[armyFightCount % 2].getRandom();
        Unit defendingOpponent = armyArr[(armyFightCount + 1) % 2].getRandom();

        //The fight
        fightingOpponent.attack(defendingOpponent);

        //Checking health of defender
        if(defendingOpponent.getHealth() == 0){
            armyArr[(armyFightCount + 1) % 2].remove(defendingOpponent);
        }

        //Check if the defendingOpponent Army still has units
        if(armyArr[(armyFightCount + 1) % 2].hasUnits()){
            //Allowing next army to fight
            armyFightCount++;
            return simulate();
        }
        else{
            System.out.println("Army" + ((armyFightCount % 2) + 1) + " won!");
            return armyArr[armyFightCount % 2];
        }
    }

    @Override
    public String toString() {
        return "\tBattle of the ages\n" +
                "Army One:\n" + armyOne +
                "\n Army Two:\n" + armyTwo;
    }
}
