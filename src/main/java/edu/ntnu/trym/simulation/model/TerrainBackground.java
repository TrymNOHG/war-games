package edu.ntnu.trym.simulation.model;

import javafx.scene.layout.Pane;

/**
 * This class concerns itself with which background a scene should have based on the terrain.
 */
public class TerrainBackground {

    /**
     * This method attaches a background to a pane using a css file.
     * @param terrain       The desired terrain background, given as the enumeration TerrainType.
     * @param background    The pane which the picture will be placed, given as a Pane object
     */
    public static void setBackgroundByTerrain(TerrainType terrain, Pane background){
        if(terrain == TerrainType.DEFAULT) background.setId("default-background");
        else if(terrain == TerrainType.HILL) background.setId("hill-background");
        else if(terrain == TerrainType.PLAINS) background.setId("plains-background");
        else if(terrain == TerrainType.FOREST) background.setId("forest-background");


    }

}
