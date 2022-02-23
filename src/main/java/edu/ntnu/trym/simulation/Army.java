package edu.ntnu.trym.simulation;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Army {
    private String name;
    private List<Unit> units;

    public Army(String name) {
        this.name = name;
    }

    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void add(Unit unit){
        this.units.add(unit);
    }

    public void addAll(List<Unit> units){
        this.units.addAll(units);
    }

    public void remove(Unit unit){
        this.units.removeIf(unitInQuestion -> unitInQuestion.equals(unit));
    }

    public boolean hasUnits(){
        return this.units.size() > 0;
    }

    public List<Unit> getAllUnits(){
       return this.units;
    }

    public Unit getRandom(){
        int randomIndex = new Random().nextInt(this.units.size());
        return this.units.get(randomIndex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("'s Army:\n");
        for (Unit unit:this.units) {
            sb.append(unit.toString()).append("\n");

        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Army)) return false;

        Army army = (Army) object;

        if (!Objects.equals(name, army.name)) return false;
        return Objects.equals(units, army.units);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }
}
