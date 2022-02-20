package edu.ntnu.trym.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RangedUnitTest {

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
        void reduces_an_Opponents_Health(){
            //Given/Arrange
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            Unit Opponent1 = new InfantryUnit("Pikeman", 100);
            Unit Opponent2 = new InfantryUnit("Pikeman", 100);
            int opponent1ExpectedHealth = 91; //FILL IN
            int opponent2ExpectedHealth = 93; //FILL IN

            //When/Act
            specialRangedUnit.attack(Opponent1);
            presetRangedUnit.attack(Opponent2);


            //Then/Assert
            Assertions.assertEquals(opponent1ExpectedHealth, Opponent1.getHealth());
            Assertions.assertEquals(opponent2ExpectedHealth, Opponent2.getHealth());
        }
        

        @Test
        void gets_2_attack_bonus(){
            //Given/Arrange
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromSpecialUnit = specialInfantryUnit.getResistBonus();
            int actualResistBonusFromPresetUnit = presetInfantryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromSpecialUnit);
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }

        @Test
        void gets_decreasing_resistance_bonus_based_on_number_of_attacks(){
            //Given/Arrange
            RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, 10);
            RangedUnit presetRangedUnit = new RangedUnit("Crossbowman", 10);
            
            int expectedResistanceBonusAfter1Defense = 6;
            int expectedResistanceBonusAfter2Defense = 4;
            int expectedResistanceBonusAfter3OrMoreDefense = 2;

            //When/Act
            //Since the resistance bonus decreases when called during attacks, this will be tested by calling the resist
            //bonus.
            int actualResistanceBonusAfter1DefenseSpecialUnit = specialRangedUnit.getResistBonus();
            int actualResistanceBonusAfter1DefensePresetUnit = presetRangedUnit.getResistBonus();

            int actualResistanceBonusAfter2DefenseSpecialUnit = specialRangedUnit.getResistBonus();
            int actualResistanceBonusAfter2DefensekPresetUnit = presetRangedUnit.getResistBonus();

            int actualResistanceBonusAfter3OrMoreDefenseSpecialUnit = specialRangedUnit.getResistBonus();
            int actualResistanceBonusAfter3OrMoreDefensePresetUnit = presetRangedUnit.getResistBonus();

            //Then/Assert
            //First attack
            Assertions.assertEquals(expectedResistanceBonusAfter1Defense, actualResistanceBonusAfter1DefenseSpecialUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter1Defense, actualResistanceBonusAfter1DefensePresetUnit);
            //Second attack
            Assertions.assertEquals(expectedResistanceBonusAfter2Defense, actualResistanceBonusAfter2DefenseSpecialUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter2Defense, actualResistanceBonusAfter2DefensekPresetUnit);
            //Third attack
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter3OrMoreDefenseSpecialUnit);
            Assertions.assertEquals(expectedResistanceBonusAfter3OrMoreDefense, actualResistanceBonusAfter3OrMoreDefensePresetUnit);
            
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
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_health_input_is_less_than_0(int healthValue){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit("Archer", healthValue, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_attack_input_is_less_than_0(int attackValue){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, attackValue, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_armor_input_is_less_than_0(int armorValue){
            assertThrows(IllegalArgumentException.class, ()->{
                RangedUnit specialRangedUnit = new RangedUnit("Archer", 10, 17, armorValue);
                //Act and assert
            });
        }
    }
}