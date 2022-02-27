package edu.ntnu.trym.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BattleTest {
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

        Battle battle = new Battle(expectedToWin, expectedToLose);

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

        Battle battle = new Battle(armyOne, armyTwo);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        if(actualVictor.getName().equals(armyOne.getName()) || actualVictor.getName().equals(armyTwo.getName())){
            Assertions.assertTrue(true);
        }
        else{
            Assertions.fail("Neither army won, error!");
        }
    }

    @Test
    void if_only_one_army_has_units_it_wins(){
        //Given/Arrange
        Army expectedToWin = fillArmy1();

        Army expectedToLose = new Army("Eirik's Army", null);

        Battle battle = new Battle(expectedToWin, expectedToLose);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        Assertions.assertEquals(expectedToWin.getName(), actualVictor.getName());
    }

    @Test
    void if_neither_army_has_units_then_no_battle(){
        //Given/Arrange
        Army armyOne = new Army("Trym's Army", null);
        Army armyTwo = new Army("Eirik's Army", null);
        Army expectedResult = null;

        Battle battle = new Battle(armyOne, armyTwo);

        //When/Act
        Army actualVictor = battle.simulate();

        //Then/Assert
        Assertions.assertEquals(expectedResult, actualVictor);
    }

}