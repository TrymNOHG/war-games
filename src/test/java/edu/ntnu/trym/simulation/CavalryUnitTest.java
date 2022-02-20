package edu.ntnu.trym.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CavalryUnitTest {

    @Nested
    public class An_CavalryUnit_with_valid_input_values{

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
        void reduces_an_Opponents_Health(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, 10);
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            Unit Opponent1 = new InfantryUnit("Pikeman", 100);
            Unit Opponent2 = new InfantryUnit("Pikeman", 100);
            int opponent1ExpectedHealth = 88; //FILL IN
            int opponent2ExpectedHealth = 85; //FILL IN

            //When/Act
            specialCavalryUnit.attack(Opponent1);
            presetCavalryUnit.attack(Opponent2);


            //Then/Assert
            Assertions.assertEquals(opponent1ExpectedHealth, Opponent1.getHealth());
            Assertions.assertEquals(opponent2ExpectedHealth, Opponent2.getHealth());
        }

        @Test
        void gets_decreasing_attack_bonus_based_on_number_of_attacks(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Archer", 10, 17, 10);
            CavalryUnit presetCavalryUnit = new CavalryUnit("Crossbowman", 10);
            int expectedAttackBonusAfter1Attack = 6;
            int expectedAttackBonusAfter2OrMoreAttacks = 2;

            //When/Act
            //Since the attack bonus decreases when called during attacks, this will be tested by calling the attack
            //bonus.
            int actualAttackBonusAfter1AttackSpecialUnit = specialCavalryUnit.getAttackBonus();
            int actualAttackBonusAfter1AttackPresetUnit = presetCavalryUnit.getAttackBonus();

            int actualAttackBonusAfter2AttackSpecialUnit = specialCavalryUnit.getAttackBonus();
            int actualAttackBonusAfter2AttackPresetUnit = presetCavalryUnit.getAttackBonus();

            int actualAttackBonusAfter3AttackSpecialUnit = specialCavalryUnit.getAttackBonus();
            int actualAttackBonusAfter3AttackPresetUnit = presetCavalryUnit.getAttackBonus();

            //Then/Assert
            //First attack
            Assertions.assertEquals(expectedAttackBonusAfter1Attack, actualAttackBonusAfter1AttackSpecialUnit);
            Assertions.assertEquals(expectedAttackBonusAfter1Attack, actualAttackBonusAfter1AttackPresetUnit);
            //Second attack
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter2AttackSpecialUnit);
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter2AttackPresetUnit);
            //Third attack
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter3AttackSpecialUnit);
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter3AttackPresetUnit);
        }

        @Test
        void gets_1_resistance_bonus(){
            //Given/Arrange
            CavalryUnit specialCavalryUnit = new CavalryUnit("Knight", 10, 17, 10);
            CavalryUnit presetCavalryUnit = new CavalryUnit("Knight", 10);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromSpecialUnit = specialCavalryUnit.getResistBonus();
            int actualResistBonusFromPresetUnit = presetCavalryUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromSpecialUnit);
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