package edu.ntnu.trym.simulation;

import edu.ntnu.trym.simulation.model.units.UnitFactory;
import edu.ntnu.trym.simulation.model.units.UnitType;
import edu.ntnu.trym.simulation.model.units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

class UnitFactoryTest{

    @Nested
    class Factory_for_default_units{

        @ParameterizedTest (name = "{index}. Type of unit tested : {0}")
        @EnumSource(UnitType.class)
        void initializes_a_unit_with_valid_information_correctly(UnitType unitType){
            //Given/Arrange
            Unit expectedUnit = null;
            String name = unitType + " unit";
            int health = 10;
            if(unitType == UnitType.RANGED){
                expectedUnit = new RangedUnit(name, health);
            }
            else if(unitType == UnitType.CAVALRY){
                expectedUnit = new CavalryUnit(name, health);
            }
            else if(unitType == UnitType.INFANTRY){
                expectedUnit = new InfantryUnit(name, health);
            }
            else if(unitType == UnitType.COMMANDER){
                expectedUnit = new CommanderUnit(name, health);
            }

            //When/Act
            Unit actualUnit = UnitFactory.getUnit(unitType, name, health);

            //Then/Assert
            Assertions.assertEquals(expectedUnit, actualUnit);
        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class, names = {"INFANTRY"})
        void does_not_initialize_a_unit_with_invalid_name(UnitType unitType){
            //Given/Arrange
            String name = "";
            int health = 10;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                Unit actualUnit = UnitFactory.getUnit(unitType, name, health);
            }); //Then/Assert

        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class, names = {"INFANTRY"})
        void does_not_initialize_a_unit_with_invalid_health(UnitType unitType){
            //Given/Arrange
            String name = "Pikeman";
            int health = -5;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                Unit actualUnit = UnitFactory.getUnit(unitType, name, health);
            }); //Then/Assert

        }

        @ParameterizedTest (name = "{index}. Invalid Unit Type : {0}")
        @ValueSource(strings = {"inFanTry", "General", "123", " "})
        void does_not_initialize_a_unit_with_invalid_unit_type(String unitType){
            //Given/Arrange
            String name = unitType + " unit";
            int health = 10;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                Unit unit = UnitFactory.getUnit(UnitType.valueOf(unitType), name, health);
            });//Then/Assert

        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class)
        void deep_copies_a_given_unit(UnitType unitType){
            //Given/Arrange
            String name = unitType + " unit";
            int health = 10;
            Unit unitToBeCopied = UnitFactory.getUnit(unitType, name, health);

            //When/Act
            Unit deepCopiedUnit = UnitFactory.getDeepCopiedDefaultUnit(unitToBeCopied);

            //Then/Assert
            Assertions.assertFalse(unitToBeCopied == deepCopiedUnit); //Testing reference in memory
            Assertions.assertEquals(unitToBeCopied, deepCopiedUnit); //Testing actual information in unit
        }

        @Test
        void throws_NullPointerException_when_null_unit_is_deep_copied(){
            //Given/Arrange
            Unit nullUnit = null;

            //When/Act
            Assertions.assertThrows(NullPointerException.class, () -> {
                Unit deepCopiedUnit = UnitFactory.getDeepCopiedDefaultUnit(null);
            }); //Then/Assert
        }

        @ParameterizedTest (name = "{index}. Sizes tested = {0}")
        @ValueSource (ints = {0, 1, 15})
        void fills_list_with_specified_number_of_units(int desiredAmount){
            //Given/Arrange
            int expectedSizeOfList = desiredAmount;

            //When/Act
            List<Unit> listToBeFilled = UnitFactory.getMultipleUnits(desiredAmount, UnitType.INFANTRY,
                    "Archer", 10);

            //Then/Assert
            Assertions.assertEquals(expectedSizeOfList, listToBeFilled.size());
        }

        @Test
        void fills_list_with_correct_unit(){
            //Given/Arrange
            int desiredAmount = 5;
            String name = "Archer";
            int health = 10;
            List<Unit> expectedUnitList = new ArrayList<>();
            for(int i = 0; i < desiredAmount; i++){
                expectedUnitList.add(new RangedUnit(name, health));
            }

            //When/Act
            List<Unit> actualUnitList = UnitFactory.getMultipleUnits(desiredAmount, UnitType.RANGED, name, health);

            //Then/Assert
            Assertions.assertEquals(expectedUnitList, actualUnitList);
        }

        @ParameterizedTest (name = "{index}. Invalid sizes tested = {0}")
        @ValueSource (ints = {-1, -5})
        void throws_IllegalArgumentException_when_desired_amount_is_invalid(int desiredSize){
            //Given/Arrange
            String name = "Archer";
            int health = 10;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                List<Unit> actualUnitList = UnitFactory.getMultipleUnits(desiredSize, UnitType.RANGED, name, health);
            }); //Then/Assert
        }

    }

    @Nested
    class Factory_for_special_units{
        @ParameterizedTest (name = "{index}. Type of unit tested : {0}")
        @EnumSource(UnitType.class)
        void initializes_a_unit_with_valid_information_correctly(UnitType unitType){
            //Given/Arrange
            Unit expectedUnit = null;
            String name = unitType + " unit";
            int health = 10;
            int attackValue = 15;
            int armorValue = 20;
            if(unitType == UnitType.RANGED){
                expectedUnit = new RangedUnit(name, health, attackValue, armorValue);
            }
            else if(unitType == UnitType.CAVALRY){
                expectedUnit = new CavalryUnit(name, health, attackValue, armorValue);
            }
            else if(unitType == UnitType.INFANTRY){
                expectedUnit = new InfantryUnit(name, health, attackValue, armorValue);
            }
            else if(unitType == UnitType.COMMANDER){
                expectedUnit = new CommanderUnit(name, health, attackValue, armorValue);
            }

            //When/Act
            Unit actualUnit = UnitFactory.getUnit(unitType, name, health, attackValue, armorValue);

            //Then/Assert
            Assertions.assertEquals(expectedUnit, actualUnit);
        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class, names = {"INFANTRY"})
        void does_not_initialize_a_unit_with_invalid_name(UnitType unitType){
            //Given/Arrange
            String name = "";
            int health = 10;
            int attackValue = 15;
            int armorValue = 20;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                Unit actualUnit = UnitFactory.getUnit(unitType, name, health, attackValue, armorValue);
            }); //Then/Assert

        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class, names = {"INFANTRY"})
        void does_not_initialize_a_unit_with_invalid_health(UnitType unitType){
            //Given/Arrange
            String name = "Pikeman";
            int health = -5;
            int attackValue = 15;
            int armorValue = 20;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                Unit actualUnit = UnitFactory.getUnit(unitType, name, health, attackValue, armorValue);
            }); //Then/Assert
        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class, names = {"INFANTRY"})
        void does_not_initialize_a_unit_with_invalid_attack(UnitType unitType){
            //Given/Arrange
            String name = "Pikeman";
            int health = 5;
            int attackValue = -15;
            int armorValue = 20;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                Unit actualUnit = UnitFactory.getUnit(unitType, name, health, attackValue, armorValue);
            }); //Then/Assert
        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class, names = {"INFANTRY"})
        void does_not_initialize_a_unit_with_invalid_armor(UnitType unitType){
            //Given/Arrange
            String name = "Pikeman";
            int health = 5;
            int attackValue = 15;
            int armorValue = -20;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                Unit actualUnit = UnitFactory.getUnit(unitType, name, health, attackValue, armorValue);
            }); //Then/Assert
        }

        @ParameterizedTest (name = "{index}. Invalid Unit Type : {0}")
        @ValueSource(strings = {"inFanTry", "General", "123", " "})
        void does_not_initialize_a_unit_with_invalid_unit_type(String unitType){
            //Given/Arrange
            String name = unitType + " unit";
            int health = 10;
            int attackValue = 15;
            int armorValue = 20;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                Unit unit = UnitFactory.getUnit(UnitType.valueOf(unitType), name, health, attackValue, armorValue);
            });//Then/Assert
        }

        @ParameterizedTest (name = "Unit tested : {0}")
        @EnumSource(value = UnitType.class)
        void deep_copies_a_given_unit(UnitType unitType){
            //Given/Arrange
            String name = unitType + " unit";
            int health = 10;
            int attack = 15;
            int armor = 20;
            Unit unitToBeCopied = UnitFactory.getUnit(unitType, name, health, attack, armor);

            //When/Act
            Unit deepCopiedUnit = UnitFactory.getDeepCopiedSpecialUnit(unitToBeCopied);

            //Then/Assert
            Assertions.assertFalse(unitToBeCopied == deepCopiedUnit); //Testing reference in memory
            Assertions.assertEquals(unitToBeCopied, deepCopiedUnit); //Testing actual information in unit
        }

        @Test
        void throws_NullPointerException_when_null_unit_is_deep_copied(){
            //Given/Arrange
            Unit nullUnit = null;

            //When/Act
            Assertions.assertThrows(NullPointerException.class, () -> {
                Unit deepCopiedUnit = UnitFactory.getDeepCopiedSpecialUnit(null);
            }); //Then/Assert
        }

        @ParameterizedTest (name = "{index}. Sizes tested = {0}")
        @ValueSource (ints = {0, 1, 15})
        void fills_list_with_specified_number_of_units(int desiredAmount){
            //Given/Arrange
            int expectedSizeOfList = desiredAmount;

            //When/Act
            List<Unit> listToBeFilled = UnitFactory.getMultipleUnits(desiredAmount, UnitType.INFANTRY,
                    "Archer", 10, 15, 20);

            //Then/Assert
            Assertions.assertEquals(expectedSizeOfList, listToBeFilled.size());
        }

        @Test
        void fills_list_with_correct_unit(){
            //Given/Arrange
            int desiredAmount = 5;
            String name = "Archer";
            int health = 10;
            int attackValue = 15;
            int armorValue = 20;
            List<Unit> expectedUnitList = new ArrayList<>();
            for(int i = 0; i < desiredAmount; i++){
                expectedUnitList.add(new RangedUnit(name, health, attackValue, armorValue));
            }

            //When/Act
            List<Unit> actualUnitList = UnitFactory.getMultipleUnits(desiredAmount, UnitType.RANGED, name, health,
                    attackValue, armorValue);

            //Then/Assert
            Assertions.assertEquals(expectedUnitList, actualUnitList);
        }

        @ParameterizedTest (name = "{index}. Invalid sizes tested = {0}")
        @ValueSource (ints = {-1, -5})
        void throws_IllegalArgumentException_when_desired_amount_is_invalid(int desiredSize){
            //Given/Arrange
            String name = "Archer";
            int health = 10;
            int attackValue = 15;
            int armorValue = 20;

            //When/Act
            Assertions.assertThrows(IllegalArgumentException.class, () ->{
                List<Unit> actualUnitList = UnitFactory.getMultipleUnits(desiredSize, UnitType.RANGED, name, health,
                        attackValue, armorValue);
            }); //Then/Assert
        }


        //TODO: Should I test when the input is invalid for multiple unit method or are the other tests enough?

    }

}