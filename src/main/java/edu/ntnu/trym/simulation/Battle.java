package edu.ntnu.trym.simulation;

/**
 * This class represents a battle between two armies. It, therefore, thoroughly defines a simulation what
 * this battle would look like and who the winner is.
 */
public class Battle {
    private Army armyOne;
    private Army armyTwo;
    private Army[] armyArr;
    private int armyFightCount;

    /**
     * This constructor creates a Battle object, with the necessary information such as what two armies are
     * participating and who starts the battle {@link #randomStartArmy()}.
     * @param armyOne An army participating in the battle, represented as the object Army
     * @param armyTwo Another army participating in the battle, represented as the object Army
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.armyArr = new Army[]{armyOne, armyTwo};
        this.armyFightCount = randomStartArmy();
    }

    /**
     * This method chooses a random army to start the battle, between 0-1.
     * @return An int representing which army attacks first
     */
    private int randomStartArmy(){
        return (int) Math.round(Math.random());
    }

    /**
     * This method enacts a battle between two armies. The armies take turns fighting and the two opponents are chosen
     * at random from both armies. Once a Unit's health goes below 0, it is removed from its respective army. Once an
     * army has lost all of its Units, the other army is declared the winner.
     * @return The winning army, represented as an Army object
     */
    public Army simulate(){
        //Checks that both sides have units to begin with
        while(armyOne.hasUnits() && armyTwo.hasUnits()) {
            //Choosing fighters
            Unit fightingOpponent = armyArr[armyFightCount % 2].getRandom();
            Unit defendingOpponent = armyArr[(armyFightCount + 1) % 2].getRandom();

            //The fight
            fightingOpponent.attack(defendingOpponent);

            //Checking health of defender
            if (defendingOpponent.getHealth() == 0) {
                armyArr[(armyFightCount + 1) % 2].remove(defendingOpponent);
            }
            this.armyFightCount++;
        }
        
            //Check if armyOne has units
            if (armyOne.hasUnits()) {
                return armyOne;
            }
            //If armyOne doesn't have any, check armyTwo
            else if (armyTwo.hasUnits()) {
                return armyTwo;
            }
            //Return null if neither has any units, this would mean no battle was engaged.
            return null;
    }

    @Override
    public String toString() {
        return "\tBattle of the ages\n" +
                "Army One:\n" + armyOne +
                "\n Army Two:\n" + armyTwo;
    }
}
