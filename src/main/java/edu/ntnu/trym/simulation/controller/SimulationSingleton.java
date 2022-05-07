package edu.ntnu.trym.simulation.controller;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.TerrainType;

/**
 * This enumeration is constructed using the singleton design pattern. The implementation of this design pattern
 * restricts the amount of instances of the enumeration to one. This is essential for the enumeration's purpose.
 * The enum contains all the data that needs to be transmitted between controllers.
 */
public enum SimulationSingleton {
    INSTANCE;

    private Army army1;
    private Army army2;
    private TerrainType currentTerrain = TerrainType.DEFAULT;

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

    public TerrainType getCurrentTerrain() {
        return currentTerrain;
    }

    public void setCurrentTerrain(TerrainType currentTerrain) {
        this.currentTerrain = currentTerrain;
    }
}

//This singleton could also contain some logical methods such as determining whether the army variables have armies
// when loading the battle preparation scene.
