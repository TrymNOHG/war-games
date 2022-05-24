package edu.ntnu.trym.simulation.model.units;

import edu.ntnu.trym.simulation.model.TerrainType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RangedUnitTest {

    @Nested
    public class A_InfantryUnit_object {

        @Test
        void instantiates_properly_with_preset_constructor() {
            //Given/Arrange
            String name = "Archer";
            int health = 10;
            int expectedAttack = 15;
            int expectedArmor = 8;

            //When/Act
            RangedUnit presetRangedUnit  = null;
            try {
                presetRangedUnit = new RangedUnit(name, health);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, presetRangedUnit.getName());
            Assertions.assertEquals(health, presetRangedUnit.getHealth());
            Assertions.assertEquals(expectedAttack, presetRangedUnit.getAttack());
            Assertions.assertEquals(expectedArmor, presetRangedUnit.getArmor());
        }

        //Maybe test attack and armor equal to 0, to show that this is a possibility
        @Test
        void instantiates_properly_with_manually_set_constructor(){
            //Given/Arrange
            String name = "Archer";
            int health = 10;
            int attack = 17;
            int armor = 10;

            //When/Act
            RangedUnit specialRangedUnit = null;
            try {
                specialRangedUnit = new RangedUnit(name, health, attack, armor);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, specialRangedUnit.getName());
            Assertions.assertEquals(health, specialRangedUnit.getHealth());
            Assertions.assertEquals(attack, specialRangedUnit.getAttack());
            Assertions.assertEquals(armor, specialRangedUnit.getArmor());
        }

    }

    @Nested
    public class A_RangedUnit_with_valid_input_values{

        @Test
        void returns_correct_name(){
            //Given/Arrange
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            String expectedName = "Archer";

            //When/Act
            String actualNameReturned = specialRangedUnit.getName();

            //Then/Assert
            Assertions.assertEquals(expectedName, actualNameReturned);
        }

        @Test
        void has_UnitType_RangedUnit_after_instantiation(){
            //Given/Arrange
            UnitType expectedUnitType = UnitType.RANGED;

            //When/Act
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            UnitType actualUnitType = specialRangedUnit.getUnitType();

            //Then/Assert
            Assertions.assertEquals(expectedUnitType, actualUnitType);
        }

        @Test
        void returns_health_value(){
            //Given/Arrange
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            int expectedHealth = 10;

            //When/Act
            int actualHealthReturned = specialRangedUnit.getHealth();

            //Then/Assert
            Assertions.assertEquals(expectedHealth, actualHealthReturned);
        }

        @Test
        void properly_sets_health_value(){
            //Given/Arrange
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            int newHealth = 5;

            //When/Act
            specialRangedUnit.setHealth(5);

            //Then/Assert
            Assertions.assertEquals(newHealth, specialRangedUnit.getHealth());
        }

        @Test
        void returns_armor_value(){
            //Given/Arrange
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            int expectedArmor = 10;

            //When/Act
            int actualArmorReturned = specialRangedUnit.getArmor();

            //Then/Assert
            Assertions.assertEquals(expectedArmor, actualArmorReturned);
        }

        @Test
        void reduces_an_Opponents_Health_above_0(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            Unit opponent = new InfantryUnit("Pikeman", 100);
            int opponentExpectedHealth = 93; //FILL IN

            //When/Act
            presetRangedUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void reduces_an_Opponents_Health_below_0(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            Unit opponent = new InfantryUnit("Pikeman", 7);
            int opponentExpectedHealth = 0; //FILL IN

            //When/Act
            presetRangedUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void reduces_an_Opponents_Health_equal_to_0(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            Unit opponent = new InfantryUnit("Pikeman", 7);
            int opponentExpectedHealth = 0; //FILL IN

            //When/Act
            presetRangedUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }
        //Make a reduces_Opponents_Health for a null opponent. Maybe change attack method to not all opponent
        //to be null.
        

        @Test
        void gets_3_attack_bonus(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Archer", 10);
            int expectedAttackBonus = 3;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetRangedUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void gets_6_resistance_bonus_based_on_first_attack(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            int expectedResistanceBonusAfter1Defense = 6;


            //When/Act
            //Since the resistance bonus decreases when called during attacks, this will be tested by calling the resist
            //bonus.
            int actualResistanceBonusAfter1DefensePresetUnit = presetRangedUnit.getResistBonus();//First defense

            //Then/Assert
            Assertions.assertEquals(expectedResistanceBonusAfter1Defense, actualResistanceBonusAfter1DefensePresetUnit);
        }

        @Test
        void gets_4_resistance_bonus_during_second_time_getting_attacked(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            int expectedResistanceBonusAfter2Defense = 4;

            //When/Act
            //Since the resistance bonus decreases when called during attacks, this will be tested by calling the resist
            //bonus.
            presetRangedUnit.getResistBonus(); //First defense
            int actualResistanceBonusAfter2DefensePresetUnit = presetRangedUnit.getResistBonus();//Second defense

            //Then/Assert
            Assertions.assertEquals(expectedResistanceBonusAfter2Defense, actualResistanceBonusAfter2DefensePresetUnit);
        }

        @Test
        void gets_2_resistance_bonus_during_third_time_or_later_of_getting_attacked(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            int expectedResistanceBonusAfter3OrMoreDefense = 2;

            //When/Act
            //Since the resistance bonus decreases when called during attacks, this will be tested by calling the resist
            //bonus.
            presetRangedUnit.getResistBonus(); //First defense
            presetRangedUnit.getResistBonus();//Second defense
            int actualResistanceBonusAfter3DefensePresetUnit = presetRangedUnit.getResistBonus();//Third defense
            int actualResistanceBonusAfter4DefensePresetUnit = presetRangedUnit.getResistBonus();//Fourth defense


            //Then/Assert
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter3DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter4DefensePresetUnit);
        }
        
    }

    @Nested
    public class A_RangedUnit_in_the_PLAINS_terrain{
        @Test
        void has_the_same_attack_bonus_as_default(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Archer", 10);
            TerrainType plainsTerrain = TerrainType.PLAINS;
            presetRangedUnit.setCurrentTerrain(plainsTerrain);
            int expectedAttackBonus = 3;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetRangedUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void has_the_same_resistance_bonus_as_default(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            TerrainType plainsTerrain = TerrainType.PLAINS;
            presetRangedUnit.setCurrentTerrain(plainsTerrain);
            int expectedResistanceBonusAfter1Defense = 6;
            int expectedResistanceBonusAfter2Defense = 4;
            int expectedResistanceBonusAfter3OrMoreDefense = 2;


            //When/Act
            int actualResistanceBonusAfter1DefensePresetUnit = presetRangedUnit.getResistBonus();//First defense
            int actualResistanceBonusAfter2DefensePresetUnit = presetRangedUnit.getResistBonus();//Second defense
            int actualResistanceBonusAfter3DefensePresetUnit = presetRangedUnit.getResistBonus();//Third defense
            int actualResistanceBonusAfter4DefensePresetUnit = presetRangedUnit.getResistBonus();//Fourth defense


            //Then/Assert
            Assertions.assertEquals(expectedResistanceBonusAfter1Defense, actualResistanceBonusAfter1DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter2Defense, actualResistanceBonusAfter2DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter3DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter4DefensePresetUnit);

        }
    }

    @Nested
    public class A_RangedUnit_in_the_FOREST_terrain{

        @Test
        void gets_1_attack_bonus(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Archer", 10);
            TerrainType forestTerrain = TerrainType.FOREST;
            presetRangedUnit.setCurrentTerrain(forestTerrain);
            int expectedAttackBonus = 1;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetRangedUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void has_the_same_resistance_bonus_as_default(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            TerrainType forestTerrain = TerrainType.FOREST;
            presetRangedUnit.setCurrentTerrain(forestTerrain);
            int expectedResistanceBonusAfter1Defense = 6;
            int expectedResistanceBonusAfter2Defense = 4;
            int expectedResistanceBonusAfter3OrMoreDefense = 2;


            //When/Act
            int actualResistanceBonusAfter1DefensePresetUnit = presetRangedUnit.getResistBonus();//First defense
            int actualResistanceBonusAfter2DefensePresetUnit = presetRangedUnit.getResistBonus();//Second defense
            int actualResistanceBonusAfter3DefensePresetUnit = presetRangedUnit.getResistBonus();//Third defense
            int actualResistanceBonusAfter4DefensePresetUnit = presetRangedUnit.getResistBonus();//Fourth defense


            //Then/Assert
            Assertions.assertEquals(expectedResistanceBonusAfter1Defense, actualResistanceBonusAfter1DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter2Defense, actualResistanceBonusAfter2DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter3DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter4DefensePresetUnit);

        }
    }

    @Nested
    public class A_RangedUnit_in_the_HILL_terrain{

        @Test
        void gets_13_attack_bonus(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Archer", 10);
            TerrainType hillTerrain = TerrainType.HILL;
            presetRangedUnit.setCurrentTerrain(hillTerrain);
            int expectedAttackBonus = 13;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetRangedUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void has_the_same_resistance_bonus_as_default(){
            //Given/Arrange
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            TerrainType hillTerrain = TerrainType.HILL;
            presetRangedUnit.setCurrentTerrain(hillTerrain);
            int expectedResistanceBonusAfter1Defense = 6;
            int expectedResistanceBonusAfter2Defense = 4;
            int expectedResistanceBonusAfter3OrMoreDefense = 2;


            //When/Act
            int actualResistanceBonusAfter1DefensePresetUnit = presetRangedUnit.getResistBonus();//First defense
            int actualResistanceBonusAfter2DefensePresetUnit = presetRangedUnit.getResistBonus();//Second defense
            int actualResistanceBonusAfter3DefensePresetUnit = presetRangedUnit.getResistBonus();//Third defense
            int actualResistanceBonusAfter4DefensePresetUnit = presetRangedUnit.getResistBonus();//Fourth defense


            //Then/Assert
            Assertions.assertEquals(expectedResistanceBonusAfter1Defense, actualResistanceBonusAfter1DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter2Defense, actualResistanceBonusAfter2DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter3DefensePresetUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter4DefensePresetUnit);

        }
    }

    @Nested
    public class A_RangedUnit_is_not_initialized{
        //Exceptions
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "           "}) //Arrange
        void if_name_input_is_empty_or_blank(String name){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit(name, 10, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, Integer.MIN_VALUE}) //Arrange
        void if_health_input_is_less_than_or_equal_to_0(int healthValue){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit("Archer", healthValue, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, Integer.MIN_VALUE}) //Arrange
        void if_attack_input_is_less_than_0(int attackValue){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, attackValue, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, Integer.MIN_VALUE}) //Arrange
        void if_armor_input_is_less_than_0(int armorValue){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, armorValue);
                //Act and assert
            });
        }
    }
}