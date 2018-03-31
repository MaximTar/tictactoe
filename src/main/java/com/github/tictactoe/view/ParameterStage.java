package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by maxtar.
 */
public class ParameterStage extends Stage {

    public enum Tab {GAME, VIEW}

    public ParameterStage(Controller controller, Window owner, Tab selectedTab) {

        TabPane parameterPane = new TabPane();
        GameTab gameTab = new GameTab(controller);
        ViewTab viewTab = new ViewTab(controller);
        parameterPane.getTabs().addAll(gameTab, viewTab);
        this.setScene(new Scene(parameterPane, 300, 400));
        this.initOwner(owner);
        this.initModality(Modality.WINDOW_MODAL);
        switch (selectedTab) {
            case GAME: {
                parameterPane.getSelectionModel().select(gameTab);
                break;
            }
            case VIEW: {
                parameterPane.getSelectionModel().select(viewTab);
                break;
            }
        }
    }
}
