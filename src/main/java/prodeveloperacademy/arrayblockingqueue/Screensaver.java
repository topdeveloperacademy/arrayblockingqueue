/*
 *
 * MIT License
 *
 * Copyright (c) 2021 Pro Developer Academy <https://www.prodeveloperacademy.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package prodeveloperacademy.arrayblockingqueue;

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
 * Pro Developer Academy - https://www.prodeveloperacademy.com
 * Authors: Michael P. and Hiram K.
 */
public class Screensaver extends Application {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;

    private static final Logger LOG = Logger.getLogger(Screensaver.class.getName());

    @Override
    public void start(Stage stage) {
        var root = new Group();
        var scene = new Scene(root, Color.WHITE);
        var visualBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());


        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.setMaxWidth(WIDTH);
        stage.setMaxHeight(HEIGHT);

        stage.setTitle("ArrayBlockingQueue Screensaver");
        stage.setScene(scene);
        stage.show();

        stage.setFullScreen(false);

        var capacity = 10;
        var params = getParameters();

        if (!params.getRaw().isEmpty()) {
            var param = params.getRaw().get(0);

            try {
                capacity = Integer.parseInt(param);
            } catch (NumberFormatException exception) {
                var msg = "'{0}' is not a valid number. Will default to: {1}";
                LOG.log(Level.WARNING, msg, new Object[]{param, capacity});
            }
        }

        var shapes = new ArrayBlockingQueue<Shape>(capacity);

        var producer = new Producer(WIDTH, HEIGHT, shapes);
        var consumer = new Consumer(root, shapes);

        Thread producingThread = new Thread(producer);
        Thread consumingThread = new Thread(consumer);

        producingThread.setDaemon(true);
        consumingThread.setDaemon(true);

        producingThread.start();
        consumingThread.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
