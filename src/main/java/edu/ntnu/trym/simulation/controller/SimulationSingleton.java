package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.battle.Battle;
import edu.ntnu.trym.simulation.model.TerrainType;

/**
 * This enumeration is constructed using the singleton design pattern. The implementation of this design pattern
 * restricts the amount of instances of the enumeration to one. This is essential for the enumeration's purpose.
 * The enum contains all the data that needs to be transmitted between controllers.
 *
 * @author Trym Hamer Gudvangen
 */
public enum SimulationSingleton {
    INSTANCE;

    private Army army1;
    private Army army2;
    private int currentArmy;
    private Battle battle;
    private TerrainType currentTerrain = TerrainType.DEFAULT;

    /**
     * This method retrieves the current army, which is given by the currentArmy int. If the integer is equal to 1, then
     * the current army is army1, else army2.
     * @return  The current army, given as an Army object.
     */
    public Army getArmyOfCurrentArmy(){
        if(this.currentArmy == 1) return getArmy1();
        else return getArmy2();
    }

    /**
     * This method changes the current army, given by the currentArmy int, to the army provided as input.
     * @param army  The new army, represented using an Army object.
     */
    public void setArmyOfCurrentArmy(Army army){
        if(this.currentArmy == 1) setArmy1(army);
        else if (this.currentArmy == 2) setArmy2(army);
    }

    /**
     * This method retrieves the current army integer. The army integer of the army being worked on.
     * @return  The current army number, given as an integer.
     */
    public int getCurrentArmy() {
        return currentArmy;
    }

    /**
     * This method sets the current army integer to the provided input. The army integer of the army being worked on.
     * @param currentArmy   The new current army number, represented using an integer.
     */
    public void setCurrentArmy(int currentArmy) {
        this.currentArmy = currentArmy;
    }

    /**
     * This method retrieves army1.
     * @return The object named army1, which is an Army object.
     */
    public Army getArmy1() {
        return army1;
    }

    /**
     * This method allows for the changing of army1 to the given input.
     * @param army1     The new army1, given as an Army object.
     */
    public void setArmy1(Army army1) {
        this.army1 = army1;
    }

    /**
     * This method retrieves army2.
     * @return The object named army2, which is an Army object.
     */
    public Army getArmy2() {
        return army2;
    }

    /**
     * This method allows for the changing of army2 to the given input.
     * @param army2     The new army2, given as an Army object.
     */
    public void setArmy2(Army army2) {
        this.army2 = army2;
    }

    /**
     * This method retrieves the simulation's battle.
     * @return  The battle, represented through a Battle object.
     */
    public Battle getBattle() {
        return battle;
    }

    /**
     * This method allows the battle object to be changed to the input battle.
     * @param battle    The new battle, given as a Battle object.
     */
    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    /**
     * This method retrieves the current terrain of the simulation.
     * @return  The current terrain, given as a TerrainType enumeration.
     */
    public TerrainType getCurrentTerrain() {
        return currentTerrain;
    }

    /**
     * This method allows for the currentTerrain to be changed to a given input.
     * @param currentTerrain    The new currentTerrain, given as a TerrainType enumeration.
     */
    public void setCurrentTerrain(TerrainType currentTerrain) {
        this.currentTerrain = currentTerrain;
    }

}