package edu.ntnu.trym.simulation.model;

import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class TerrainBackgroundTest {

    @ParameterizedTest(name = "Terrain tested : {0}")
    @EnumSource(value = TerrainType.class)
    void The_Id_of_the_provided_pane_is_properly_set(TerrainType terrainType){
        //Given/Arrange
        TerrainType terrainType1 = terrainType;
        Pane background = new Pane();
        String expectedId = terrainType1.toString().toLowerCase() + "-background";

        //When/Act
        TerrainBackground.setBackgroundByTerrain(terrainType1, background);
        String actualId = background.getId();

        //Then/Assert
        Assertions.assertEquals(expectedId, actualId);
    }

}
