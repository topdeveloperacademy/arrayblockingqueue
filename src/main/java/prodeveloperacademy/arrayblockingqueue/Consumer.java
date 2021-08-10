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

import java.util.Queue;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

/**
 * Pro Developer Academy - https://www.prodeveloperacademy.com
 * Authors: Michael P. and Hiram K.
 */
public class Consumer extends Task<Void> {

    private final Group root;
    private final Queue<Shape> shapes;

    public Consumer(Group root, Queue<Shape> shapes) {
        this.root = root;
        this.shapes = shapes;
    }

    @Override
    protected Void call() throws Exception {
        while (true) {
            // Blocks if the queue is empty
            var shape = shapes.poll();

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
