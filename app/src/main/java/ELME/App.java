/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ELME;

import View.*;
import de.gurkenlabs.litiengine.*;
import de.gurkenlabs.litiengine.graphics.*;
import de.gurkenlabs.litiengine.input.Input;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


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
//        cam.setClampToMap(true);
        Game.world().setCamera(cam);
        System.out.println("Camera printout: "+cam.getZoom());
        
        Input.mouse().onDragged(new MouseDraggedListener(cam));
        Input.mouse().onWheelMoved(new CustomWheelListener(cam));
        Game.start();
        
//        cam.setZoom((float) 1000,10);
//        cam.update();
        System.out.println("Camera printout: "+cam.getZoom());
    }
}

