package edu.ntnu.trym.simulation.model.battle;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.units.Unit;

/**
 * This class represents a battle between two armies. It, therefore, thoroughly defines a simulation what
 * this battle would look like and who the winner is.
 *
 * @author Trym Hamer Gudvangen
 */
public class Battle {
    private Army armyOne;
    private Army armyTwo;
    private Army[] armyArr;
    private Army winnerArmy;
    private int armyFightCount;
    private TerrainType battleTerrain;

    /**
     * This constructor creates a Battle object, with the necessary information such as what two armies are
     * participating and who starts the battle {@link #randomStartArmy()}.
     * @param armyOne                       An army participating in the battle, represented as the object Army
     * @param armyTwo                       Another army participating in the battle, represented as the object Army
     * @throws NullPointerException         This exception is thrown if an army is null
     */
    public Battle(Army armyOne, Army armyTwo, TerrainType battleTerrain) throws NullPointerException{
        if(armyOne == null || armyTwo == null) throw new NullPointerException("No army can be null!");
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;

        this.armyOne.setAllUnitsTerrain(battleTerrain);
        this.armyTwo.setAllUnitsTerrain(battleTerrain);

        this.armyArr = new Army[]{armyOne, armyTwo};
        this.battleTerrain = battleTerrain;
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
     * army has lost all of its Units, the other army is declared the winner {@link #checkWinnerArmy()}.
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

        return checkWinnerArmy();
    }

    /**
     * This method retrieves the terrain that the battle is currently set to occur in.
     * @return The terrain of the battle, represented as a TerrainType enum.
     */
    public TerrainType getBattleTerrain() {
        return battleTerrain;
    }

    /**
     * This method retrieves the Army object armyOne.
     * @return The Army object named armyOne
     */
    public Army getArmyOne() {
        return armyOne;
    }

    /**
     * This method retrieves the Army object armyOne.
     * @return The Army object named armyOne
     */
    public Army getArmyTwo() {
        return armyTwo;
    }

    /**
     * This method retrieves the winner army.
     * @return Winner army, given as an Army object.
     */
    public Army getWinnerArmy() {
        return winnerArmy;
    }

    //Make getter for winner

    /**
     * This method checks which army still has units after fighting. That army is declared the winner.
     * @return The winning army, represented using an Army object.
     */
    private Army checkWinnerArmy(){
        //Check if armyOne has units
        if (armyOne.hasUnits()) {
            this.winnerArmy = armyOne;
            return armyOne;
        }
        //If armyOne doesn't have any, check armyTwo
        else if (armyTwo.hasUnits()) {
            this.winnerArmy = armyTwo;
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
