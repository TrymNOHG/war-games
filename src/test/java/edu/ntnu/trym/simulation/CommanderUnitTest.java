package edu.ntnu.trym.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class CommanderUnitTest {

    @Nested
    public class A_CommanderUnit_with_valid_input_values{

        @Test
        void instantiates_properly_with_preset_constructor() {
            //Given/Arrange
            String name = "King";
            int health = 10;
            int expectedAttack = 25;
            int expectedArmor = 15;

            //When/Act
            CommanderUnit presetCommanderUnit = null;
            try {
                presetCommanderUnit = new CommanderUnit(name, health);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, presetCommanderUnit.getName());
            Assertions.assertEquals(health, presetCommanderUnit.getHealth());
            Assertions.assertEquals(expectedAttack, presetCommanderUnit.getAttack());
            Assertions.assertEquals(expectedArmor, presetCommanderUnit.getArmor());
        }

        @Test
        void instantiates_properly_with_manually_set_constructor(){
            //Given/Arrange
            String name = "King";
            int health = 10;
            int attack = 20;
            int armor = 15;

            //When/Act
            CommanderUnit specialCommanderUnit = null;
            try {
                specialCommanderUnit = new CommanderUnit("King", health, attack, armor);
            } catch (Exception e) {
                fail("Preset constructor did not instantiate properly");
            }

            //Then/Assert
            Assertions.assertEquals(name, specialCommanderUnit.getName());
            Assertions.assertEquals(health, specialCommanderUnit.getHealth());
            Assertions.assertEquals(attack, specialCommanderUnit.getAttack());
            Assertions.assertEquals(armor, specialCommanderUnit.getArmor());
        }

        @Test
        void returns_correct_name(){
            //Given/Arrange
            CommanderUnit specialCommanderUnit = new CommanderUnit("King", 10, 17, 10);
            String expectedName = "King";

            //When/Act
            String actualNameReturned = specialCommanderUnit.getName();

            //Then/Assert
            Assertions.assertEquals(expectedName, actualNameReturned);
        }

        @Test
        void returns_health_value(){
            //Given/Arrange
            CommanderUnit specialCommanderUnit = new CommanderUnit("King", 10, 17, 10);
            int expectedHealth = 10;

            //When/Act
            int actualHealthReturned = specialCommanderUnit.getHealth();

            //Then/Assert
            Assertions.assertEquals(expectedHealth, actualHealthReturned);
        }

        @Test
        void properly_sets_health_value(){
            //Given/Arrange
            CommanderUnit specialCommanderUnit = new CommanderUnit("King", 10, 17, 10);
            int newHealth = 5;

            //When/Act
            specialCommanderUnit.setHealth(5);

            //Then/Assert
            Assertions.assertEquals(newHealth, specialCommanderUnit.getHealth());
        }

        @Test
        void returns_armor_value(){
            //Given/Arrange
            CommanderUnit specialCommanderUnit = new CommanderUnit("King", 10, 17, 10);
            int expectedArmor = 10;

            //When/Act
            int actualArmorReturned = specialCommanderUnit.getArmor();

            //Then/Assert
            Assertions.assertEquals(expectedArmor, actualArmorReturned);
        }

        @Test
        void reduces_an_Opponents_Health(){
            //Given/Arrange
            CommanderUnit presetCommanderUnit = new CommanderUnit("King", 10);
            Unit opponent = new InfantryUnit("Pikeman", 100);
            int opponentExpectedHealth = 80;

            //When/Act
            presetCommanderUnit.attack(opponent);


            //Then/Assert
            Assertions.assertEquals(opponentExpectedHealth, opponent.getHealth());
        }

        @Test
        void gets_6_attack_bonus_from_first_attack(){
            //Given/Arrange
            CommanderUnit presetCommanderUnit = new CommanderUnit("Crossbowman", 10);
            int expectedAttackBonusAfter1Attack = 6;

            //When/Act
            //Since the attack bonus decreases when called during attacks, this will be tested by calling the attack
            //bonus.
            int actualAttackBonusAfter1AttackPresetUnit = presetCommanderUnit.getAttackBonus();

            //Then/Assert
            //First attack
            Assertions.assertEquals(expectedAttackBonusAfter1Attack, actualAttackBonusAfter1AttackPresetUnit);

        }

        @Test
        void gets_2_attack_bonus_from_the_second_or_later_attack(){
            //Given/Arrange
            CommanderUnit presetCommanderUnit = new CommanderUnit("Crossbowman", 10);
            int expectedAttackBonusAfter2OrMoreAttacks = 2;

            //When/Act
            //Since the attack bonus decreases when called during attacks, this will be tested by calling the attack
            //bonus.
            presetCommanderUnit.getAttackBonus();
            int actualAttackBonusAfter2AttackPresetUnit = presetCommanderUnit.getAttackBonus();
            int actualAttackBonusAfter3AttackPresetUnit = presetCommanderUnit.getAttackBonus();

            //Then/Assert
            //Second attack
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter2AttackPresetUnit);
            //Third attack
            Assertions.assertEquals(expectedAttackBonusAfter2OrMoreAttacks, actualAttackBonusAfter3AttackPresetUnit);

        }

        @Test
        void gets_1_resistance_bonus(){
            //Given/Arrange
            CommanderUnit presetCommanderUnit = new CommanderUnit("King", 10);
            int expectedResistBonus = 1;

            //When/Act
            int actualResistBonusFromPresetUnit = presetCommanderUnit.getResistBonus();

            //Then/Assert
            Assertions.assertEquals(expectedResistBonus, actualResistBonusFromPresetUnit);
        }

    }

    @Nested
    public class A_CommanderUnit_is_not_initialized{
        //Exceptions
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "           "}) //Arrange
        void if_name_input_is_empty_or_blank(String name){
            assertThrows(IllegalArgumentException.class, ()->{
                CommanderUnit specialCommanderUnit = new CommanderUnit(name, 10, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999}) //Arrange
        void if_health_input_is_less_than_0(int healthValue){
            assertThrows(IllegalArgumentException.class, ()->{
                CommanderUnit specialCommanderUnit = new CommanderUnit("King", healthValue, 17, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999})//Arrange
        void if_attack_input_is_less_than_0(int attackValue){
            assertThrows(IllegalArgumentException.class, ()->{
                CommanderUnit specialCommanderUnit = new CommanderUnit("King", 10, attackValue, 10);
                //Act and assert
            });
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -99999})//Arrange
        void if_armor_input_is_less_than_0(int armorValue){
            assertThrows(IllegalArgumentException.class, ()->{
                CommanderUnit specialCommanderUnit = new CommanderUnit("King", 10, 17, armorValue);
                //Act and assert
            });
        }
    }
}
