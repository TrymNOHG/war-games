package edu.ntnu.trym.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ArmyTest {

    static List<Unit> fillArmyList(){
        List<Unit> filledArmyList = new ArrayList<>();
        filledArmyList.add(new CommanderUnit("King", 10));
        filledArmyList.add(new CavalryUnit("Knight", 10));
        filledArmyList.add(new InfantryUnit("Pikeman", 10));
        filledArmyList.add(new RangedUnit("Crossbowman", 10));
        return filledArmyList;
    }

    @Nested
    public class An_Army_with_valid_input_values{

        @Test
        void instantiates_properly_with_preset_constructor() {
            //Given/Arrange
            String name = "Trym's Army";
            List<Unit> expectedArmyList = new ArrayList<>();

            //When/Act
            Army army = null;
            try {
                army = new Army(name);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, army.getName());
            Assertions.assertEquals(expectedArmyList, army.getAllUnits());
        }

        @Test
        void instantiates_properly_with_manually_set_constructor(){
            //Given/Arrange
            String name = "Trym's Army";
            List<Unit> inputArmyList = fillArmyList();

            //When/Act
            Army army = null;
            try {
                army = new Army(name, inputArmyList);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, army.getName());
            Assertions.assertEquals(inputArmyList, army.getAllUnits());
        }

        @Test
        void returns_correct_name(){
            //Given/Arrange
            String expectedName = "Trym's army";
            Army army = new Army(expectedName, fillArmyList());

            //When/Act
            String actualName = army.getName();

            //Then/Assert
            Assertions.assertEquals(expectedName, actualName);
        }

        @Test
        void adds_valid_new_unit_to_list(){
            //Given/Arrange
            Army army = new Army("Trym's army", null);
            List<Unit> expectedArmyList = new ArrayList<>();
            RangedUnit rangedUnit = new RangedUnit("Archer", 10);
            expectedArmyList.add(rangedUnit);

            //When/Act
            army.add(rangedUnit);

            //Then/Assert
            Assertions.assertEquals(expectedArmyList, army.getAllUnits());

        }

        @Test
        void does_not_add_unit_equal_null_to_Army_list(){
            //Given/Arrange
            Army army = new Army("Trym's army", null);
            List<Unit> expectedArmyList = new ArrayList<>();
            RangedUnit rangedUnit = null;
            expectedArmyList.add(rangedUnit);

            //When/Act
            army.add(rangedUnit);

            //Then/Assert
            Assertions.assertNotEquals(expectedArmyList, army.getAllUnits());
        }

        @Test
        void adds_all_units_from_valid_given_list_to_Army_list(){
            //Given/Arrange
            Army army = new Army("Trym's army", null);
            List<Unit> unitListToBeAdded = fillArmyList();

            //When/Act
            army.addAll(fillArmyList());

            //Then/Assert
            for(int i = 0; i < army.getAllUnits().size(); i++){
                Assertions.assertEquals(fillArmyList().get(i).getResistBonus(), army.getAllUnits().get(i).getResistBonus());
                //Is it smarter to create an equals method in the army class?
            }

        }

        @Test
        void does_not_add_invalid_units_from_invalid_list_to_Army_list(){
            //Given/Arrange
            Army army = new Army("Trym's army", null);
            List<Unit> unitListToBeAdded = fillArmyList();
            unitListToBeAdded.add(null);

            //When/Act
            army.addAll(unitListToBeAdded);

            //Then/Assert
            Assertions.assertNotEquals(unitListToBeAdded, army.getAllUnits());
        }

        @Test
        void does_not_add_null_unit_list_to_Army_list(){
            //Given/Arrange
            Army army = new Army("Trym's army", null);
            List<Unit> unitListToBeAdded = null;

            //When/Act
            army.addAll(unitListToBeAdded);

            //Then/Assert
            Assertions.assertNotEquals(unitListToBeAdded, army.getAllUnits());
        }

        @Test
        void removes_correct_Unit_from_Army_list(){
            //Given/Arrange
            List<Unit> armyList = new ArrayList<>();
            RangedUnit rangedUnit = new RangedUnit("Archer", 10);
            armyList.add(rangedUnit);
            Army army = new Army("Trym's army", armyList);

            //When/Act
            army.remove(rangedUnit);

            //Then/Assert
            //Since there was only one unit, army's units list should be an empty arrayList.
            //Assertions.assertNotEquals(armyList, army.getAllUnits());
            // Lists refer to same place in memory because of lack
            //deep copy
            Assertions.assertEquals(new ArrayList<>(), army.getAllUnits());

            //Maybe test trying to remove a null Unit
        }

        @Test
        void checks_if_the_Army_List_has_units(){
            //Given/Arrange
            Army army = new Army("Trym's army", fillArmyList());
            boolean expectedStatus = true;

            //When/Act
            boolean actualStatus = army.hasUnits();

            //Then/Assert
            Assertions.assertEquals(expectedStatus, actualStatus);

        }

        @Test
        void checks_if_the_Army_List_is_empty(){
            //Given/Arrange
            Army army = new Army("Trym's army", null);
            boolean expectedStatus = false;

            //When/Act
            boolean actualStatus = army.hasUnits();

            //Then/Assert
            Assertions.assertEquals(expectedStatus, actualStatus);
        }

        @Test
        void returns_Army_List_when_not_empty(){
            //Given/Arrange
            List<Unit> armyList = fillArmyList();
            Army army = new Army("Trym's Army", armyList);

            //When/Act
            List<Unit> actualList = army.getAllUnits();

            //Then/Assert
            Assertions.assertEquals(armyList, actualList);
        }

        @Test
        void returns_Army_List_when_empty(){
            //Given/Arrange
            List<Unit> armyList = new ArrayList<>();
            Army army = new Army("Trym's Army", armyList);

            //When/Act
            List<Unit> actualList = army.getAllUnits();

            //Then/Assert
            Assertions.assertEquals(armyList, actualList);
        }

    }

    @Nested
    public class An_Armys_random_function{
        @Test
        void returns_no_random_unit_when_list_is_empty(){
            //Given/Arrange
            List<Unit> armyList = new ArrayList<>();
            Army army = new Army("Trym's Army", armyList);
            Unit expectedUnit = null;


            //When/Act
            Unit actualUnit = army.getRandom();

            //Then/Assert
            Assertions.assertEquals(expectedUnit, actualUnit);
        }

        @Test
        void returns_a_random_unit_equal_to_the_only_unit_in_a_list(){
            //Given/Arrange
            List<Unit> armyList = new ArrayList<>();
            Unit expectedUnit = new RangedUnit("Archer", 10);
            armyList.add(expectedUnit);
            Army army = new Army("Trym's Army", armyList);

            //When/Act
            Unit actualUnit = army.getRandom();

            //Then/Assert
            Assertions.assertEquals(expectedUnit, actualUnit);
        }

        @Test
        void returns_a_unit_from_the_list(){
            //Given/Arrange
            List<Unit> armyList = fillArmyList();
            Army army = new Army("Trym's Army", armyList);

            //When/Act
            Unit actualUnit = army.getRandom();

            //Then/Assert
            Assertions.assertTrue(armyList.contains(actualUnit));
        }
    }

    @Nested
    public class An_Army_with_invalid_input_values{
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "           "}) //Arrange
        void throws_IllegalArgumentException_if_name_input_is_empty_or_blank(String name){
            assertThrows(IllegalArgumentException.class, ()->{
                Army army = new Army(name);
                //Act and assert
            });
        }
    }


}
