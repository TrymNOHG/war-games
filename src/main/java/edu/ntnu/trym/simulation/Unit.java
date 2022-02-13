package edu.ntnu.trym.simulation;

public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;

    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    public void attack(Unit opponent){
        int thisDamage = this.getAttack() + this.getAttackBonus();
        int opponentResistance = opponent.getArmor() + opponent.getResistBonus();
        opponent.setHealth(opponent.getHealth() - opponentResistance + opponentResistance);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("'s Stats:\nUnit Type: ").append(this.getClass())
                .append("\nHealth: ").append(this.health).append("\nAttack: ").append(this.attack)
                .append("\nAttack Bonus: ").append(this.getAttackBonus()).append("\nArmor: ").append(this.armor)
                .append("\nResistance Bonus: ").append(this.getResistBonus()).append(this.getClass());
        return sb.toString();
    }

    public abstract int getAttackBonus();

    public abstract int getResistBonus();

}
