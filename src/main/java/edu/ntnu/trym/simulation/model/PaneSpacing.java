package edu.ntnu.trym.simulation.model;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * This class provides utility for spacing layouts using panes. This is done by placing panes between each node provided.
 */
public class PaneSpacing {

    /**
     * This method takes in different nodes and creates an HBox with equal spaces between each of the nodes. The
     * spaces are created using panes.
     * @param nodes Any type of node from JavaFX such as Text
     * @return      An HBox with spacing between the nodes
     */
    public static HBox createHBoxWithSpacing(Node...nodes){
        return (HBox) createPaneWithSpacing(new HBox(), nodes);
    }

    /**
     * This method takes in different nodes and creates a VBox with equal spaces between each of the nodes. The
     * spaces are created using panes.
     * @param nodes Any type of node from JavaFX such as Text
     * @return      A VBox with spacing between the nodes
     */
    public static VBox createVBoxWithSpacing(Node...nodes){
        return (VBox) createPaneWithSpacing(new VBox(), nodes);
    }

    /**
     * This method takes advantage of the fact that other layout nodes such as VBox and HBox inherit from the Pane class.
     * Therefore, it creates a general pane spacing method.
     * @param typeOfPane The type of pane, for example HBox, represented using a Pane object
     * @param nodes      Any type of node from JavaFX such as Text
     * @return           A pane that has spacing between the given nodes
     */
    private static Pane createPaneWithSpacing(Pane typeOfPane, Node...nodes){
        Pane initialPaneSpacing = new Pane();
        typeOfPane.getChildren().add(initialPaneSpacing);
        HBox.setHgrow(initialPaneSpacing, Priority.ALWAYS);

        Arrays.stream(nodes).forEach(node -> {
            Pane spacingPane = new Pane();

            if(typeOfPane instanceof HBox) HBox.setHgrow(spacingPane, Priority.ALWAYS);
            else if(typeOfPane instanceof VBox) VBox.setVgrow(spacingPane, Priority.ALWAYS);

            typeOfPane.getChildren().addAll(node, spacingPane);
        });
        return typeOfPane;
    }

}
