package edu.ntnu.trym.simulation.model;

import edu.ntnu.trym.simulation.model.units.Unit;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class concerns itself with the aspects intrinsic to a unit column. It, therefore, contains a method for
 * the creation of such a column.
 */
public class UnitColumn {
    private TableColumn<Unit, ?> column;

    /**
     * This constructor focuses on constructing the desired column from the information specified.
     * @param unitInfoHeader    The name of the column, represented using a String.
     * @param unitVariableName  The unit attribute the information will be extracted from, represented as a String.
     */
    public UnitColumn(String unitInfoHeader, String unitVariableName) {
        createUnitColumn(unitInfoHeader, unitVariableName);
    }

    /**
     * This method creates a table column with the specified information concerning a unit. It, thereafter, is added
     * as a column in the tableview provided.
     * @param unitInfoHeader    The header of the column being added, represented as a String.
     * @param unitVariableName  The name of the variable from the Unit, represented as a String.
     */
    private void createUnitColumn(String unitInfoHeader, String unitVariableName){
        this.column = new TableColumn<>(unitInfoHeader);
        column.setCellValueFactory(new PropertyValueFactory<>(unitVariableName));
    }

    /**
     * This method retrieves the unit column.
     * @return The unit column, represented using a TableColumn object.
     */
    public TableColumn<Unit, ?> getColumn() {
        return column;
    }
}
