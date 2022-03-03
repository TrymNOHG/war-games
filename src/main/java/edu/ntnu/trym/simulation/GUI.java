package edu.ntnu.trym.simulation;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        Army humanArmy = constructArmy("Human");
        Army orcArmy = constructArmy("Orc");

        Battle battle = new Battle(humanArmy, orcArmy);

        System.out.println(battle.simulate().getName() + " army won the battle!");

    }

    private static Army constructArmy(String nameOfArmy){
        String infantryName = "";
        String cavalryName = "";
        String rangedName = "";
        String commanderName = "";
        
        if(nameOfArmy.equals("Human")){
            infantryName = "Footman";
            cavalryName = "Knight";
            rangedName = "Archer";
            commanderName = "Mountain King";
        }
        else if(nameOfArmy.equals("Orc")){
            infantryName = "Grunt";
            cavalryName = "Raider";
            rangedName = "Spearman";
            commanderName = "Gul'dan";
        }
        
        
        List<Unit> armyList = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            //Infantry units
            armyList.add(new InfantryUnit(infantryName, 100));
            armyList.add(new InfantryUnit(infantryName, 100));
            armyList.add(new InfantryUnit(infantryName, 100));
            armyList.add(new InfantryUnit(infantryName, 100));
            armyList.add(new InfantryUnit(infantryName, 100));

            //Cavalry units
            armyList.add(new CavalryUnit(cavalryName, 100));

            //Ranged units
            armyList.add(new RangedUnit(rangedName, 100));
            armyList.add(new RangedUnit(rangedName, 100));

        }
        armyList.add(new CommanderUnit(commanderName, 180));

        return new Army(nameOfArmy, armyList);
    }

}
