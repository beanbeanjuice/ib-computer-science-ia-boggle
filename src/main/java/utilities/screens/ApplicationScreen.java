package utilities.screens;

import javafx.scene.Scene;
import utilities.gamecreation.Game;

public interface ApplicationScreen {

    Scene display();
    String getName();

    // Return null unless specified otherwise
    default Game getGame() {
        return null;
    }

}
