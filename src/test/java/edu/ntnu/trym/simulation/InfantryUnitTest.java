package edu.ntnu.trym.simulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class InfantryUnitTest {

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
        void reduces_an_Opponents_Health(){
            //Given/Arrange
            InfantryUnit specialInfantryUnit = new InfantryUnit("Pikeman", 10, 17, 10);
            InfantryUnit presetInfantryUnit = new InfantryUnit("Pikeman", 10);
            Unit Opponent1 = new InfantryUnit("Pikeman", 100);
            Unit Opponent2 = new InfantryUnit("Pikeman", 100);
            int opponent1ExpectedHealth = 92; //FILL IN
            int opponent2ExpectedHealth = 94; //FILL IN

            //When/Act
            specialInfantryUnit.attack(Opponent1);
            presetInfantryUnit.attack(Opponent2);


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
        void gets_1_resistance_bonus(){
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