/*
 * Copyright (C) 2016 Nick Houser
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
package units.enemies;

import java.awt.Point;
import units.DoublePoint;
import units.Unit;

/**
 * Abstract class which represents an Enemy, which is an AI-controlled unit that
 * tries to defeat the player.
 *
 * @author Nick Houser
 */
public abstract class Enemy extends Unit {

    //the location of this unit on the board
    protected DoublePoint position;

    /**
     * Constructor which sets the position of the Enemy equal to the passed
     * arguments.
     *
     * @param xPosition the x position where the Enemy should be created
     * @param yPosition the y position where the Enemy should be created
     * @param squareSize the size of one board square
     */
    public Enemy(int xPosition, int yPosition, int squareSize) {
        super(squareSize);
        position = new DoublePoint(xPosition, yPosition);
    }

    /**
     * Gets the current position of the unit.
     *
     * @return the position of the unit as a Point object
     */
    @Override
    public Point getPosition() {
        return position.toPoint();
    }
}
