package utilities.screens;

import javafx.scene.Scene;
import utilities.gamecreation.Game;

public interface ApplicationScreen {

    Scene display();
    String getName();

    default Game getGame() {
        return null;
    }

}
