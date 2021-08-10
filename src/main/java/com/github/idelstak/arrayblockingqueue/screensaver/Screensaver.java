/*
 * Copyright (C) 2021 Hiram K. <https://github.com/IdelsTak>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.idelstak.arrayblockingqueue.screensaver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class Screensaver extends Application {

    private static final Logger LOG = Logger.getLogger(Screensaver.class.getName());

    @Override
    public void start(Stage primaryStage) {
        var root = new Group();
        var scene = new Scene(root, Color.WHITE);
        var bounds = Screen.getPrimary().getVisualBounds();

        scene.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            if (newWidth.doubleValue() == bounds.getWidth()) {
                var capacity = 200;
                var params = getParameters();

                if (!params.getRaw().isEmpty()) {
                    var param = params.getRaw().get(0);

                    try {
                        capacity = Integer.parseInt(param);
                    } catch (NumberFormatException exception) {
                        var msg = "\"{0}\" is not a valid number. Will default to: {1}";
                        LOG.log(Level.WARNING, msg, new Object[]{param, capacity});
                    }
                }

                var shapes = new ArrayBlockingQueue<Shape>(capacity);

                var producer = new Producer(scene, shapes, capacity);
                var consumer = new Consumer(root, shapes);

                var producingThread = new Thread(producer);
                var consumingThread = new Thread(consumer);

                producingThread.setDaemon(true);
                consumingThread.setDaemon(true);

                producingThread.start();
                consumingThread.start();
            }
        });

        primaryStage.setTitle("ArrayBlockingQueue Screensaver");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreen(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
