package edu.ntnu.trym.simulation.model;

import edu.ntnu.trym.simulation.model.Army;
import edu.ntnu.trym.simulation.model.TerrainType;
import edu.ntnu.trym.simulation.model.units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    static List<Unit> fillArmyList(){
        List<Unit> filledArmyList = new ArrayList<>();
        filledArmyList.add(new CommanderUnit("King", 10));
        filledArmyList.add(new CavalryUnit("Knight", 10));
        filledArmyList.add(new InfantryUnit("Pikeman", 10));
        filledArmyList.add(new RangedUnit("Crossbowman", 10));
        filledArmyList.add(new RangedUnit("Crossbowman", 10, 11, 12));
        return filledArmyList;
    }

    @Nested
    public class An_Army_object{

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
        void instantiates_properly_with_deep_copy_constructor(){
            //Given/Arrange
            String name = "Trym's Army";
            List<Unit> inputArmyList = fillArmyList();
            Army inputArmy = new Army(name, inputArmyList);

            //When/Act
            Army deepCopyArmy = null;
            try {
                deepCopyArmy = new Army(inputArmy);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, deepCopyArmy.getName());
            Assertions.assertEquals(inputArmyList, deepCopyArmy.getAllUnits());
        }

    }

    @Nested
    public class An_Army_with_valid_input_values{

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
        void sets_correct_name(){
            //Given/Arrange
            String expectedName = "Sarah's Army";
            Army army = new Army("Trym's army", fillArmyList());

            //When/Act
            army.setName(expectedName);
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

        @ParameterizedTest (name = "Terrain tested : {0}")
        @EnumSource(value = TerrainType.class)
        void sets_all_units_terrain_to_given_terrain(TerrainType terrain){
            //Given/Arrange
            TerrainType expectedTerrain = terrain;
            Army army = new Army("Trym's Army", fillArmyList());

            //When/Act
            army.setAllUnitsTerrain(expectedTerrain);

            //Then/Assert
            Assertions.assertTrue(army.getAllUnits().stream().allMatch(unit ->
                    unit.getCurrentTerrain() == expectedTerrain));
        }

        @ParameterizedTest (name = "Terrain tested : {0}")
        @EnumSource(value = TerrainType.class)
        void doesnt_throw_exception_when_setting_terrain_for_empty_units_list(TerrainType terrain){
            //Given/Arrange
            TerrainType expectedTerrain = terrain;
            Army army = new Army("Trym's Army", new ArrayList<>());

            //When/Act
            try {
                army.setAllUnitsTerrain(expectedTerrain);
            }
            catch (Exception e){
                //Then/Assert
                fail("An exception occurred when it wasn't supposed to");
            }

        }

        @Test
        void removes_correct_Unit_from_Army_list(){
            //Given/Arrange
            List<Unit> expectedEmptyArmyList = new ArrayList<>();

            List<Unit> armyList = new ArrayList<>();
            RangedUnit rangedUnit = new RangedUnit("Archer", 10);
            armyList.add(rangedUnit);
            Army army = new Army("Trym's army", armyList);

            //When/Act
            army.remove(rangedUnit);

            //Then/Assert
            Assertions.assertEquals(expectedEmptyArmyList, army.getAllUnits());

        }

        @Test
        void returns_a_deep_copied_filled_army_list(){
            //Given/Arrange
            List<Unit> armyListToBeCopied = fillArmyList();
            Army armyWithList = new Army("Army", armyListToBeCopied);

            //When/Act
            List<Unit> actualCopiedList = armyWithList.deepCopyArmyUnits();

            //Then/Assert
            Assertions.assertFalse(armyListToBeCopied == actualCopiedList); //This checks that the reference in memory is different
            Assertions.assertEquals(armyListToBeCopied, actualCopiedList); //This checks if the elements are the same
        }

        @Test
        void returns_empty_list_when_an_empty_army_list_is_deep_copied(){
            //Given/Arrange
            List<Unit> armyListToBeCopied = new ArrayList<>();
            Army armyWithList = new Army("Army", armyListToBeCopied);

            //When/Act
            List<Unit> actualCopiedList = armyWithList.deepCopyArmyUnits();

            //Then/Assert
            Assertions.assertFalse(armyListToBeCopied == actualCopiedList); //This checks that the reference in memory is different
            Assertions.assertEquals(armyListToBeCopied, actualCopiedList); //This checks if the elements are the same
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
    public class An_Army_filters_correctly_if_it{

        @Test
        void returns_units_list_by_Infantry_unit_when_filled(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new CavalryUnit("Knight", 10));
            filledArmyList.add(new RangedUnit("Crossbowman", 10));

            //Creates an army without any infantry units.
            Army army = new Army("Trym's Army", filledArmyList);

            List<Unit> infantryList = new ArrayList<>();
            infantryList.add(new InfantryUnit("Pikeman", 10));
            infantryList.add(new InfantryUnit("Pikeman", 20));
            infantryList.add(new InfantryUnit("Pikeman", 30));

            //infantryList contains all the Infantry Units that are in the army
            army.addAll(infantryList);

            //When/Act
            List<Unit> actualReturnedInfantryList = army.getInfantryUnits();

            //Then/Assert
            actualReturnedInfantryList.forEach(infantryUnit -> {
                Assertions.assertTrue(infantryList.contains(infantryUnit));
            });
        }

        @Test
        void returns_empty_list_when_filtering_units_list_without_Infantry_units(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new CavalryUnit("Knight", 10));
            filledArmyList.add(new RangedUnit("Crossbowman", 10));

            //Creates an army without any infantry units.
            Army army = new Army("Trym's Army", filledArmyList);

            //When/Act
            List<Unit> actualReturnedInfantryList = army.getInfantryUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedInfantryList.isEmpty());
        }

        @Test
        void returns_empty_list_when_filtering_empty_units_list_by_Infantry(){
            //Given/Arrange

            //Creates an army without units.
            Army army = new Army("Trym's Army", new ArrayList<>());

            //When/Act
            List<Unit> actualReturnedInfantryList = army.getInfantryUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedInfantryList.isEmpty());
        }

        @Test
        void returns_units_list_by_Cavalry_unit_when_filled(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new RangedUnit("Crossbowman", 10));

            //Creates an army without any cavalry units.
            Army army = new Army("Trym's Army", filledArmyList);

            List<Unit> cavalryList = new ArrayList<>();
            cavalryList.add(new CavalryUnit("Knight", 10));
            cavalryList.add(new CavalryUnit("Knight", 20));
            cavalryList.add(new CavalryUnit("Knight", 30));

            //cavalryList contains all the Cavalry Units that are in the army
            army.addAll(cavalryList);

            //When/Act
            List<Unit> actualReturnedCavalryList = army.getCavalryUnits();

            //Then/Assert
            actualReturnedCavalryList.forEach(cavalryUnit ->{
                Assertions.assertTrue(cavalryList.contains(cavalryUnit));
            });
        }

        @Test
        void doesnt_add_CommanderUnit_when_filtering_for_Cavalry_unit(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new RangedUnit("Crossbowman", 10));

            //Creates an army without any cavalry units.
            Army army = new Army("Trym's Army", filledArmyList);

            List<Unit> cavalryList = new ArrayList<>();
            cavalryList.add(new CavalryUnit("Knight", 10));
            cavalryList.add(new CavalryUnit("Knight", 20));
            cavalryList.add(new CavalryUnit("Knight", 30));

            //cavalryList contains all the Cavalry Units that are in the army
            army.addAll(cavalryList);

            //When/Act
            List<Unit> actualReturnedCavalryList = army.getCavalryUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedCavalryList.stream().noneMatch(unit -> unit instanceof CommanderUnit));
            Assertions.assertTrue(actualReturnedCavalryList.stream().allMatch(unit -> unit instanceof CavalryUnit));
        }

        @Test
        void returns_empty_list_when_filtering_units_list_without_Cavalry_units(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new RangedUnit("Crossbowman", 10));

            //Creates an army without any cavalry units.
            Army army = new Army("Trym's Army", filledArmyList);

            //When/Act
            List<Unit> actualReturnedCavalryList = army.getCavalryUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedCavalryList.isEmpty());
        }

        @Test
        void returns_empty_list_when_filtering_empty_units_list_by_Cavalry(){
            //Given/Arrange

            //Creates an army without any units.
            Army army = new Army("Trym's Army", new ArrayList<>());

            //When/Act
            List<Unit> actualReturnedCavalryList = army.getCavalryUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedCavalryList.isEmpty());
        }

        @Test
        void returns_units_list_by_Ranged_unit_when_filled(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new CavalryUnit("Knight", 10));

            //Creates an army without any ranged units.
            Army army = new Army("Trym's Army", filledArmyList);

            List<Unit> rangedList = new ArrayList<>();
            rangedList.add(new RangedUnit("Crossbowman", 10));
            rangedList.add(new RangedUnit("Crossbowman", 20));
            rangedList.add(new RangedUnit("Crossbowman", 30));

            //rangedList contains all the Ranged Units that are in the army
            army.addAll(rangedList);

            //When/Act
            List<Unit> actualReturnedRangedList = army.getRangedUnits();

            //Then/Assert
            actualReturnedRangedList.forEach(rangedUnit ->{
                Assertions.assertTrue(rangedList.contains(rangedUnit));
            });
        }

        @Test
        void returns_empty_list_when_filtering_units_list_without_Ranged_units(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new CavalryUnit("Knight", 10));

            //Creates an army without any ranged units.
            Army army = new Army("Trym's Army", filledArmyList);

            //When/Act
            List<Unit> actualReturnedRangedList = army.getRangedUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedRangedList.isEmpty());
        }

        @Test
        void returns_empty_list_when_filtering_empty_units_list_by_Ranged(){
            //Given/Arrange

            //Creates an army without units.
            Army army = new Army("Trym's Army", new ArrayList<>());

            //When/Act
            List<Unit> actualReturnedRangedList = army.getRangedUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedRangedList.isEmpty());
        }

        @Test
        void returns_units_list_by_Commander_unit_when_filled(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new RangedUnit("Crossbowman", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new CavalryUnit("Knight", 10));

            //Creates an army without any commander units.
            Army army = new Army("Trym's Army", filledArmyList);

            List<Unit> commanderList = new ArrayList<>();
            commanderList.add(new CommanderUnit("King", 10));
            commanderList.add(new CommanderUnit("Commander Trym", 20));
            commanderList.add(new CommanderUnit("General", 30));

            //commanderList contains all the Commander Units that are in the army
            army.addAll(commanderList);

            //When/Act
            List<Unit> actualReturnedCommanderList = army.getCommanderUnits();

            //Then/Assert
            actualReturnedCommanderList.forEach(commanderUnit ->{
                Assertions.assertTrue(commanderList.contains(commanderUnit));
            });
        }

        @Test
        void returns_empty_list_when_filtering_units_list_without_Commander_units(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new RangedUnit("Crossbowman", 10));
            filledArmyList.add(new InfantryUnit("Pikeman", 10));
            filledArmyList.add(new CavalryUnit("Knight", 10));

            //Creates an army without any commander units.
            Army army = new Army("Trym's Army", filledArmyList);

            //When/Act
            List<Unit> actualReturnedCommanderList = army.getCommanderUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedCommanderList.isEmpty());
        }

        @Test
        void returns_empty_list_when_filtering_empty_units_list_by_Commander(){
            //Given/Arrange

            //Creates an army without any units.
            Army army = new Army("Trym's Army", new ArrayList<>());

            //When/Act
            List<Unit> actualReturnedCommanderList = army.getCommanderUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedCommanderList.isEmpty());
        }

        @Test
        void doesnt_return_Commander_Units_when_filtering_for_Cavalry(){
            //Given/Arrange
            List<Unit> filledArmyList = new ArrayList<>();
            filledArmyList.add(new CommanderUnit("King", 10));
            filledArmyList.add(new CommanderUnit("Queen", 10));
            filledArmyList.add(new CommanderUnit("General", 10));

            //Creates an army without any cavalry units.
            Army army = new Army("Trym's Army", filledArmyList);

            List<Unit> cavalryList = new ArrayList<>();
            cavalryList.add(new CavalryUnit("Knight", 10));
            cavalryList.add(new CavalryUnit("Knight", 20));
            cavalryList.add(new CavalryUnit("Knight", 30));

            //cavalryList contains all the Cavalry Units that are in the army
            army.addAll(cavalryList);

            //When/Act
            List<Unit> actualReturnedCavalryList = army.getCavalryUnits();

            //Then/Assert
            Assertions.assertTrue(actualReturnedCavalryList.stream().noneMatch(cavalryUnit ->
                    cavalryUnit.getClass().getSimpleName().equals("CommanderUnit")));
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
        @ValueSource(strings = {"", " ", "           "}) //Given/Arrange
        void throws_IllegalArgumentException_if_name_input_is_empty_or_blank_in_constructor(String name){
            assertThrows(IllegalArgumentException.class, ()->{
                Army army = new Army(name); //When/Act
            }); //Then/Assert
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "           "}) //Given/Arrange
        void throws_IllegalArgumentException_if_name_input_is_empty_or_blank_when_setting(String name){
            Army army = new Army("Valid name");

            assertThrows(IllegalArgumentException.class, ()->{
                army.setName(name); //When/Act
            });//Then/Assert
        }

        @Test
        void throws_NullPointerException_if_army_input_in_deep_copy_constructor_is_null(){
            //Given/Arrange
            Army inputArmy = null;

            Assertions.assertThrows(NullPointerException.class, () ->{
                Army newArmy = new Army(inputArmy); //When/Act
            }); //Then/Assert
        }
    }

}
