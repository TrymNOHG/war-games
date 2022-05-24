package edu.ntnu.trym.simulation.model;

import edu.ntnu.trym.simulation.model.battle.Battle;
import edu.ntnu.trym.simulation.model.units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;


class BattleTest {
    Army fillArmy1(){
        List<Unit> filledArmyList = new ArrayList<>();
        filledArmyList.add(new CommanderUnit("King", 10));
        filledArmyList.add(new CavalryUnit("Knight", 10));
        filledArmyList.add(new InfantryUnit("Pikeman", 10));
        filledArmyList.add(new RangedUnit("Crossbowman", 10));
        Army army = new Army("Trym's Army", filledArmyList);
        return army;
    }

    Army fillArmy2(){
        List<Unit> filledArmyList = new ArrayList<>();
        filledArmyList.add(new CommanderUnit("King", 10));
        filledArmyList.add(new CavalryUnit("Knight", 10));
        filledArmyList.add(new InfantryUnit("Pikeman", 10));
        filledArmyList.add(new RangedUnit("Crossbowman", 10));
        Army army = new Army("Eirik's Army", filledArmyList);
        return army;
    }

    @Nested
    public class An_instantiated_battle_object{

        @Test
        void can_be_constructed(){
            //Given/Arrange
            Army army1 = fillArmy1();
            Army army2 = fillArmy2();
            TerrainType terrainType = TerrainType.DEFAULT;

            //When/Act
            Battle battle = null;
            try {
                battle = new Battle(army1, army2, terrainType);
            }
            catch (Exception e){
                fail("The constructor was not instantiated properly");
            }

            //Then/Assert
            //Since no errors were thrown during the instantiation of the constructor,
            //it is assumed that it is functioning properly.
            Assertions.assertTrue(true);

        }

        @ParameterizedTest(name = "Terrain tested : {0}")
        @EnumSource(value = TerrainType.class)
        void sets_all_army_units_to_have_battleTerrain_as_currentTerrain(TerrainType terrain){
            //Given/Arrange
            Army armyOne = fillArmy1();
            Army armyTwo = fillArmy2();
            TerrainType expectedTerrain = terrain;

            //When/Act
            Battle battle = new Battle(armyOne, armyTwo, expectedTerrain);

            //Then/Assert
            Assertions.assertTrue(armyOne.getAllUnits().stream().allMatch(unit ->
                    unit.getCurrentTerrain() == expectedTerrain));
            Assertions.assertTrue(armyTwo.getAllUnits().stream().allMatch(unit ->
                    unit.getCurrentTerrain() == expectedTerrain));
        }
    }


    //When a unit has max int health and is attacked by a unit who has less damage than their resistance,
    //there is a rollover problem and the unit's health drops from max to 0.
    //TODO: Fix rollover problem
    @Test
    void a_unit_without_attack_damage_loses_against_a_high_health_unit_in_battle(){
        //Given/Arrange
        List<Unit> army1List = new ArrayList<>();
        army1List.add(new CommanderUnit("King", 999999));
        Army expectedToWin = new Army("Trym's Army", army1List);

        List<Unit> army2List = new ArrayList<>();
        army2List.add(new CommanderUnit("King", 1, 0, 0));
        Army expectedToLose = new Army("Eirik's Army", army2List);

        TerrainType terrainType = TerrainType.DEFAULT;

        Battle battle = new Battle(expectedToWin, expectedToLose, terrainType);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        Assertions.assertEquals(expectedToWin.getName(), actualVictor.getName());
    }

    @Test
    void one_of_the_two_armies_is_returned_after_a_battle(){
        //Given/Arrange
        Army armyOne = fillArmy1();
        Army armyTwo = fillArmy2();
        TerrainType terrainType = TerrainType.DEFAULT;

        Battle battle = new Battle(armyOne, armyTwo, terrainType);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        if(actualVictor.getName().equals(armyOne.getName()) || actualVictor.getName().equals(armyTwo.getName())){
            Assertions.assertTrue(true);
        }
        else{
            fail("Neither army won, error!");
        }
    }

    @Test
    void if_only_one_army_has_units_it_wins(){
        //Given/Arrange
        TerrainType terrainType = TerrainType.DEFAULT;
        Army expectedToWin = fillArmy1();

        Army expectedToLose = new Army("Eirik's Army", null);

        Battle battle = new Battle(expectedToWin, expectedToLose, terrainType);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        Assertions.assertEquals(expectedToWin.getName(), actualVictor.getName());
    }

    @Test
    void if_neither_army_has_units_then_no_battle(){
        //Given/Arrange
        TerrainType terrainType = TerrainType.DEFAULT;
        Army armyOne = new Army("Trym's Army", null);
        Army armyTwo = new Army("Eirik's Army", null);
        Army expectedResult = null;

        Battle battle = new Battle(armyOne, armyTwo, terrainType);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        Assertions.assertEquals(expectedResult, actualVictor);
    }

    @Nested
    public class A_Battle_object_can_simulate{
        @Test
        void a_fight_in_plains_terrain(){
            //Given/Arrange
            Army armyOne = fillArmy1();
            Army armyTwo = fillArmy2();
            TerrainType terrainType = TerrainType.PLAINS;

            Battle battle = new Battle(armyOne, armyTwo, terrainType);

            //When/Act
            Army actualVictor = battle.simulate();

            //Then/Assert
            if(actualVictor.getName().equals(armyOne.getName()) || actualVictor.getName().equals(armyTwo.getName())){
                Assertions.assertTrue(true);
            }
            else{
                fail("Neither army won, error!");
            }
        }

        @Test
        void a_fight_in_hill_terrain(){
            //Given/Arrange
            Army armyOne = fillArmy1();
            Army armyTwo = fillArmy2();
            TerrainType terrainType = TerrainType.HILL;

            Battle battle = new Battle(armyOne, armyTwo, terrainType);

            //When/Act
            Army actualVictor = battle.simulate();

            //Then/Assert
            if(actualVictor.getName().equals(armyOne.getName()) || actualVictor.getName().equals(armyTwo.getName())){
                Assertions.assertTrue(true);
            }
            else{
                fail("Neither army won, error!");
            }
        }

        @Test
        void a_fight_in_forest_terrain(){
            //Given/Arrange
            Army armyOne = fillArmy1();
            Army armyTwo = fillArmy2();
            TerrainType terrainType = TerrainType.FOREST;

            Battle battle = new Battle(armyOne, armyTwo, terrainType);

            //When/Act
            Army actualVictor = battle.simulate();

            //Then/Assert
            if(actualVictor.getName().equals(armyOne.getName()) || actualVictor.getName().equals(armyTwo.getName())){
                Assertions.assertTrue(true);
            }
            else{
                fail("Neither army won, error!");
            }
        }
    }


    @Nested
    public class A_Battle_object_is_not_initialized_if{
        @Test
        void an_army_input_is_null(){
            //Given/Arrange
            Army army1 = fillArmy1();
            Army nullArmy = null;
            TerrainType terrainType = TerrainType.DEFAULT;

            //When/Act
            Assertions.assertThrows(NullPointerException.class, () ->{
                Battle battle = new Battle(army1, nullArmy, terrainType);
            }); //Then/Assert
        }
    }

}
