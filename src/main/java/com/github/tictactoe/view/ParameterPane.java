package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import javafx.scene.control.TabPane;

/**
 * Created by maxtar.
 */
public class ParameterPane extends TabPane {

    public ParameterPane(Controller controller) {
        getTabs().addAll(new GameTab(controller));
    }
}
