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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Pro Developer Academy - https://www.prodeveloperacademy.com
 * Authors: Michael P. and Hiram K.
 */
public class Producer extends Task<Void> {
    private static final int MAX_SHAPE_WIDTH = 20;
    private static final int MAX_SHAPE_HEIGHT = 50;
    private final int width;
    private final int height;
    private final Queue<Shape> shapesQueue;

    public Producer(int width, int height, Queue<Shape> shapesQueue) {
        this.width = width;
        this.height = height;
        this.shapesQueue = shapesQueue;
    }
    @Override
    protected Void call() throws Exception {
        //Create rectangles endlessly
        while (true) {
            var random = ThreadLocalRandom.current();

            var xCoordinate = random.nextInt(this.width);
            var yCoordinate = random.nextInt(this.height);
            var width = random.nextInt(MAX_SHAPE_WIDTH);
            var height = random.nextInt(MAX_SHAPE_HEIGHT);
            var shape = new Rectangle(xCoordinate, yCoordinate, width, height);

            shape.setArcWidth(20);
            shape.setArcHeight(random.nextInt(20));

            var hue = random.nextDouble() * 360.0;
            var saturation = random.nextDouble();
            var brightness = random.nextDouble();
            var opacity = random.nextDouble();
            shape.setFill(Color.hsb(hue, saturation, brightness, opacity));

            // Blocks if capacities reached
            shapesQueue.offer(shape);
        }
    }
}
