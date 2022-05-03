package edu.ntnu.trym.simulation.units;

import edu.ntnu.trym.simulation.units.CavalryUnit;
import edu.ntnu.trym.simulation.units.InfantryUnit;
import edu.ntnu.trym.simulation.units.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

//TODO: Write tests for the different terrain!

class CavalryUnitTest {

    @Nested
    public class A_CavalryUnit_with_valid_input_values{

        @Test
        void instantiates_properly_with_preset_constructor() {
            //Given/Arrange
            String name = "Knight";
            int health = 10;
            int expectedAttack = 20;
            int expectedArmor = 12;

            //When/Act
            CavalryUnit presetCavalryUnit  = null;
            try {
                presetCavalryUnit = new CavalryUnit(name, health);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, presetCavalryUnit.getName());
            Assertions.assertEquals(health, presetCavalryUnit.getHealth());
            Assertions.assertEquals(expectedAttack, presetCavalryUnit.getAttack());
            Assertions.assertEquals(expectedArmor, presetCavalryUnit.getArmor());
        }

        @Test
        void instantiates_properly_with_manually_set_constructor(){
            //Given/Arrange
            String name = "Knight";
            int health = 10;
            int attack = 17;
            int armor = 13;

            //When/Act
            CavalryUnit specialCavalryUnit = null;
            try {
                specialCavalryUnit = new CavalryUnit(name, health, attack, armor);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, specialCavalryUnit.getName());
            Assertions.assertEquals(health, specialCavalryUnit.getHealth());
            Assertions.assertEquals(attack, specialCavalryUnit.getAttack());
            Assertions.assertEquals(armor, specialCavalryUnit.getArmor());
        }

        @Test
        void returns_correct_name(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, 10);
            String expectedName = "Knight";

            //When/Act
            String actualNameReturned = specialCavalryUnit.getName();

            //Then/Assert
            Assertions.assertEquals(expectedName, actualNameReturned);
        }

        @Test
        void returns_health_value(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, 10);
            int expectedHealth = 10;

            //When/Act
            int actualHealthReturned = specialCavalryUnit.getHealth();

            //Then/Assert
            Assertions.assertEquals(expectedHealth, actualHealthReturned);
        }

        @Test
        void properly_sets_health_value(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, 10);
            int newHealth = 5;

            //When/Act
            specialCavalryUnit.setHealth(5);

            //Then/Assert
            Assertions.assertEquals(newHealth, specialCavalryUnit.getHealth());
        }

        @Test
        void returns_armor_value(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, 10);
            int expectedArmor = 10;

            //When/Act
            int actualArmorReturned = specialCavalryUnit.getArmor();

            //Then/Assert
            Assertions.assertEquals(expectedArmor, actualArmorReturned);
        }

        @Test
        void reduces_an_Opponents_Health_above_0(){
            //Given/Arrange
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            Unit opponent = new InfantryUnit("Pikeman", 100);
            int opponentExpectedHealth = 85;

            //When/Act
            presetCavalryUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void reduces_an_Opponents_Health_below_0(){
            //Given/Arrange
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            Unit opponent = new InfantryUnit("Pikeman", 15);
            int opponentExpectedHealth = 0;

            //When/Act
            presetCavalryUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void reduces_an_Opponents_Health_equal_to_0(){
            //Given/Arrange
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            Unit opponent = new InfantryUnit("Pikeman", 15);
            int opponentExpectedHealth = 0;

            //When/Act
            presetCavalryUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }
        //Make a reduces_Opponents_Health for a null opponent. Maybe change attack method to not all opponent
        //to be null.

        @Test
        void gets_6_attack_bonus_during_first_attack(){
            //Given/Arrange
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            int expectedAttackBonusAfter1Attack = 6;

            //When/Act
            //Since the attack bonus decreases when called during attacks, this will be tested by calling the attack
            //bonus.
            int actualAttackBonusAfter1AttackPresetUnit = presetCavalryUnit.getAttackBonus();

            //Then/Assert
            //First attack
            Assertions.assertEquals(expectedAttackBonusAfter1Attack, actualAttackBonusAfter1AttackPresetUnit);
        }

        @Test
        void gets_2_attack_bonus_from_the_second_or_later_attack(){
            //Given/Arrange
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            int expectedAttackBonusAfter2OrMoreAttacks = 2;

            //When/Act
            //Since the attack bonus decreases when called during attacks, this will be tested by calling the attack
            //bonus.
            presetCavalryUnit.getAttackBonus();
            int actualAttackBonusAfter2AttackPresetUnit = presetCavalryUnit.getAttackBonus();
            int actualAttackBonusAfter3AttackPresetUnit = presetCavalryUnit.getAttackBonus();

            //Second attack
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter2AttackPresetUnit);
            //Third attack
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter3AttackPresetUnit);
        }

        @Test
        void gets_1_resistance_bonus(){
            //Given/Arrange
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromPresetUnit = presetCavalryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }

    }

    @Nested
    public class A_CavalryUnit_is_not_initialized{
        //Exceptions
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "           "}) //Arrange
        void if_name_input_is_empty_or_blank(String name){
            assertThrows(IllegalArgumentException.class, ()->{
                CavalryUnit specialCavalryUnit = new CavalryUnit(name, 10, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_health_input_is_less_than_0(int healthValue){
            assertThrows(IllegalArgumentException.class, ()->{
                CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", healthValue, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999})//Arrange
        void if_attack_input_is_less_than_0(int attackValue){
            assertThrows(IllegalArgumentException.class, ()->{
                CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, attackValue, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999})//Arrange
        void if_armor_input_is_less_than_0(int armorValue){
            assertThrows(IllegalArgumentException.class, ()->{
                CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, armorValue);
                //Act and assert
            });
        }
    }
}