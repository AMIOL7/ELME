package ELME;

import ELME.Controller.ImageLoader;
import ELME.View.*;
import de.gurkenlabs.litiengine.*;
import de.gurkenlabs.litiengine.graphics.*;
import de.gurkenlabs.litiengine.input.Input;

import java.io.IOException;

public class App {
 
    public static void main(String[] args) {
        
        Game.addGameListener(new GameListener() {
            @Override
            public void initialized(String... args) {
                System.out.println("Game initialized");
                try { ImageLoader.loadResources(); }
                catch (IOException e) { throw new RuntimeException(e); }
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

