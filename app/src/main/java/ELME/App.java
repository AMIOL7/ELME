package ELME;

import ELME.View.*;
import de.gurkenlabs.litiengine.*;
import de.gurkenlabs.litiengine.graphics.*;
import de.gurkenlabs.litiengine.input.Input;

public class App {
 
    public static void main(String[] args) {
        
        Game.addGameListener(new GameListener() {
            @Override
            public void initialized(String... args) {
                System.out.println("Game initialized");                
            }

            @Override
            public void started() {
                System.out.println("Game started");
            }

            @Override
            public void terminated() {
                System.out.println("Game terminated");
            }
        });
        
        Game.init(args);
        Game.screens().add(new MainScreen());
        Camera cam  = new Camera();
        Game.world().setCamera(cam);
        
        Input.mouse().onDragged(new MouseDraggedListener());
        Input.mouse().onWheelMoved(new CustomWheelListener());
        Game.start();
    }
}

