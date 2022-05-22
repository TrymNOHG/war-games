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

    public Army getArmyOfCurrentArmy(){
        if(this.currentArmy == 1) return getArmy1();
        else return getArmy2();
    }
    public void setArmyOfCurrentArmy(Army army){
        if(this.currentArmy == 1) setArmy1(army);
        else if (this.currentArmy == 2) setArmy2(army);
    }

    public int getCurrentArmy() {
        return currentArmy;
    }

    public void setCurrentArmy(int currentArmy) {
        this.currentArmy = currentArmy;
    }

    public Army getArmy1() {
        return army1;
    }

    public void setArmy1(Army army1) {
        this.army1 = army1;
    }

    public Army getArmy2() {
        return army2;
    }

    public void setArmy2(Army army2) {
        this.army2 = army2;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public TerrainType getCurrentTerrain() {
        return currentTerrain;
    }

    public void setCurrentTerrain(TerrainType currentTerrain) {
        this.currentTerrain = currentTerrain;
    }
}

//This singleton could also contain some logical methods such as determining whether the army variables have armies
// when loading the battle preparation scene.

//Do I want a currentArmy variable? Maybe a number representing which one is being edited

//TODO: should I have one armyFileHandler in this class and then just use it everywhere I need it?

//TODO: javadoc