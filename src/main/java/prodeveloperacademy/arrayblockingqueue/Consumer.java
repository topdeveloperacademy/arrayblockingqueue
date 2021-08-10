/*
 * Copyright (c) 2021 Pro Developer Academy <https://www.prodeveloperacademy.com>
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
package prodeveloperacademy.arrayblockingqueue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

import java.util.Queue;

/**
 * Pro Developer Academy - https://www.prodeveloperacademy.com
 * Authors: Michael P. and Hiram K.
 */
public class Consumer extends Task<Void> {
    private final Group root;
    private final Queue<Shape> shapesQueue;

    public Consumer(Group root, Queue<Shape> shapeQueue) {
        this.root = root;
        this.shapesQueue = shapeQueue;
    }

    @Override
    protected Void call() throws Exception {
        while (true) {
            // Blocks if the queue is empty
            var shape = shapesQueue.poll();

            // Send to the UI thread
            Platform.runLater(() -> {
                if (shape != null) {
                    root.getChildren().add(shape);
                }
            });

            // Simulates a slow consumer
            Thread.sleep(500);
        }
    }
}
