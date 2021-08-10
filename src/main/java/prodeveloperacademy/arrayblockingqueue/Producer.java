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

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

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
    protected Void call() {
        // Create rectangles endlessly
        while (true) {
            var shape = createNewShape();
            // Blocks if capacities reached
            shapesQueue.offer(shape);
        }
    }

    private Shape createNewShape() {
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
        return shape;
    }
}
