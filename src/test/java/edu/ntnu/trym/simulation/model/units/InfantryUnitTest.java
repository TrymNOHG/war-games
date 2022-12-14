package edu.ntnu.trym.simulation.model.units;

import edu.ntnu.trym.simulation.model.TerrainType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {

    @Nested
    public class A_InfantryUnit_object {
        @Test
        void instantiates_properly_with_preset_constructor() {
            //Given/Arrange
            String name = "Pikeman";
            int health = 10;
            int expectedAttack = 15;
            int expectedArmor = 10;

            //When/Act
            InfantryUnit presetInfantryUnit = null;
            try {
                presetInfantryUnit = new InfantryUnit(name, health);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, presetInfantryUnit.getName());
            Assertions.assertEquals(health, presetInfantryUnit.getHealth());
            Assertions.assertEquals(expectedAttack, presetInfantryUnit.getAttack());
            Assertions.assertEquals(expectedArmor, presetInfantryUnit.getArmor());
        }

        @Test
        void instantiates_properly_with_manually_set_constructor(){
            //Given/Arrange
            String name = "Pikeman";
            int health = 10;
            int attack = 17;
            int armor = 10;

            //When/Act
            InfantryUnit specialInfantryUnit = null;
            try {
                specialInfantryUnit = new InfantryUnit(name, health, attack, armor);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, specialInfantryUnit.getName());
            Assertions.assertEquals(health, specialInfantryUnit.getHealth());
            Assertions.assertEquals(attack, specialInfantryUnit.getAttack());
            Assertions.assertEquals(armor, specialInfantryUnit.getArmor());
        }

    }

    @Nested
    public class An_InfantryUnit_with_valid_input_values{

        @Test
        void returns_correct_name(){
            //Given/Arrange
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            String expectedName = "Pikeman";

            //When/Act
            String actualNameReturned = specialInfantryUnit.getName();

            //Then/Assert
            Assertions.assertEquals(expectedName, actualNameReturned);
        }

        @Test
        void has_UnitType_InfantryUnit_after_instantiation(){
            //Given/Arrange
            UnitType expectedUnitType = UnitType.INFANTRY;

            //When/Act
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            UnitType actualUnitType = specialInfantryUnit.getUnitType();

            //Then/Assert
            Assertions.assertEquals(expectedUnitType, actualUnitType);
        }

        @Test
        void returns_health_value(){
            //Given/Arrange
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            int expectedHealth = 10;

            //When/Act
            int actualHealthReturned = specialInfantryUnit.getHealth();

            //Then/Assert
            Assertions.assertEquals(expectedHealth, actualHealthReturned);
        }

        @Test
        void properly_sets_health_value(){
            //Given/Arrange
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            int newHealth = 5;

            //When/Act
            specialInfantryUnit.setHealth(5);

            //Then/Assert
            Assertions.assertEquals(newHealth, specialInfantryUnit.getHealth());
        }

        @Test
        void returns_armor_value(){
            //Given/Arrange
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            int expectedArmor = 10;

            //When/Act
            int actualArmorReturned = specialInfantryUnit.getArmor();

            //Then/Assert
            Assertions.assertEquals(expectedArmor, actualArmorReturned);
        }

        @Test
        void reduces_an_Opponents_Health_above_0(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            Unit opponent = new InfantryUnit("Pikeman", 100);
            int opponentExpectedHealth = 94;

            //When/Act
            presetInfantryUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void reduces_an_Opponents_Health_below_0(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            Unit opponent = new InfantryUnit("Pikeman", 6);
            int opponentExpectedHealth = 0;

            //When/Act
            presetInfantryUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void reduces_an_Opponents_Health_equal_to_0(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            Unit opponent = new InfantryUnit("Pikeman", 6);
            int opponentExpectedHealth = 0;

            //When/Act
            presetInfantryUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void gets_2_attack_bonus(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            int expectedAttackBonus = 2;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetInfantryUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void gets_1_resistance_bonus(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromPresetUnit = presetInfantryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }

    }

    @Nested
    public class An_InfantryUnit_in_the_PLAINS_terrain{
        @Test
        void gets_the_same_attack_bonus_as_default(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            TerrainType plainTerrain = TerrainType.PLAINS;
            presetInfantryUnit.setCurrentTerrain(plainTerrain);
            int expectedAttackBonus = 2;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetInfantryUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void gets_the_same_resistance_bonus_as_default(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            TerrainType plainTerrain = TerrainType.PLAINS;
            presetInfantryUnit.setCurrentTerrain(plainTerrain);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromPresetUnit = presetInfantryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }

    }

    @Nested
    public class An_InfantryUnit_in_the_FOREST_terrain{
        @Test
        void gets_12_attack_bonus(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            TerrainType forestTerrain = TerrainType.FOREST;
            presetInfantryUnit.setCurrentTerrain(forestTerrain);
            int expectedAttackBonus = 12;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetInfantryUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void gets_11_resistance_bonus(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            TerrainType forestTerrain = TerrainType.FOREST;
            presetInfantryUnit.setCurrentTerrain(forestTerrain);
            int expectedResistBonus = 11;

            //When/Act
            int actualResistBonusFromPresetUnit = presetInfantryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }
    }

    @Nested
    public class An_InfantryUnit_in_the_HILL_terrain{
        @Test
        void gets_the_same_attack_bonus_as_default(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            TerrainType hillTerrain = TerrainType.HILL;
            presetInfantryUnit.setCurrentTerrain(hillTerrain);
            int expectedAttackBonus = 2;

            //When/Act
            int actualAttackBonusFromPresetUnit = presetInfantryUnit.getAttackBonus();

            //Then/Assert
            Assertions.assertEquals(expectedAttackBonus, actualAttackBonusFromPresetUnit);
        }

        @Test
        void gets_the_same_resistance_bonus_as_default(){
            //Given/Arrange
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            TerrainType hillTerrain = TerrainType.HILL;
            presetInfantryUnit.setCurrentTerrain(hillTerrain);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromPresetUnit = presetInfantryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }
    }

    @Nested
    public class A_InfantryUnit_is_not_initialized{
        //Exceptions
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "           "}) //Arrange
        void if_name_input_is_empty_or_blank(String name){
            assertThrows(IllegalArgumentException.class, ()->{
                InfantryUnit specialInfantryUnit = new InfantryUnit(name, 10, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_health_input_is_less_than_0(int healthValue){
            assertThrows(IllegalArgumentException.class, ()->{
                InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", healthValue, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_attack_input_is_less_than_0(int attackValue){
            assertThrows(IllegalArgumentException.class, ()->{
                InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, attackValue, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_armor_input_is_less_than_0(int armorValue){
            assertThrows(IllegalArgumentException.class, ()->{
                InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, armorValue);
                //Act and assert
            });
        }
    }
}