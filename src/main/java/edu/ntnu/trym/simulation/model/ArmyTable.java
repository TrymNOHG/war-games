package edu.ntnu.trym.simulation.model;

import edu.ntnu.trym.simulation.model.units.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * This class concerns itself with building a table view filled with the desired unit information columns. It does so
 * by using an ArmyTableBuilder.
 */
public class ArmyTable extends TableView<Unit>{


    /**
     * This is a constructor which is used to construct an army table.
     * @param armyTableBuilder The builder used to construct the army table, represented using an ArmyTableBuilder object.
     */
    public ArmyTable(ArmyTableBuilder armyTableBuilder) {
        super();
        this.getColumns().addAll(armyTableBuilder.armyTableColumns);
    }

    /**
     * This class concerns itself with building a table view filled with the desired unit information columns. In order
     * to efficiently accomplish this, as well as remove the telescoping constructor anti-pattern, the builder pattern
     * was utilized.
     */
    public static class ArmyTableBuilder {
        private final ObservableList<TableColumn<Unit, ?>> armyTableColumns;

        public ArmyTableBuilder() {
            this.armyTableColumns = FXCollections.observableArrayList();
        }

        /**
         * This method attaches a desired column {@link UnitColumn#UnitColumn(String, String)} to the
         * tableview.
         * @param unitInfoHeader   The name of the column, represented using a String.
         * @param unitVariableName The unit attribute the information will be extracted from, represented as a String.
         * @return                 The builder itself is returned, represented as an ArmyTableBuilder object.
         */
        public ArmyTableBuilder addUnitColumn(String unitInfoHeader, String unitVariableName) {
            armyTableColumns.add(new UnitColumn(unitInfoHeader, unitVariableName).getColumn());
            return this;
        }
    }

}
