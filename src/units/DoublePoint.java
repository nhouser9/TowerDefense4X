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
package units;

import java.awt.Point;

/**
 * Class representing a point in the x-y co-ordinate plane that can be
 * in-between integers. This is basically a rewrite of the java.awt.Point class
 * that allows for the co-ordinates to be stored as doubles instead of ints.
 *
 * @author Nick Houser
 */
class DoublePoint {

    //variables representing x and y co-ordinates
    public final double x;
    public final double y;

    /**
     * Constructor which sets the values of x and y directly.
     *
     * @param x the x value to set
     * @param y the y value to set
     */
    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method which converts this to a traditional Point.
     *
     * @return a Point object representing the closest integer co-ordinates for
     * this DoublePoint
     */
    public Point toPoint() {
        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    /**
     * Method which gets the DoublePoint offset from this DoublePoint by a
     * specified vector.
     *
     * @param direction the vector to offset the DoublePoint by
     * @return the offset DoublePoint
     */
    public DoublePoint offset(DirectionVector direction) {
        return new DoublePoint(x + direction.xDirection, y + direction.yDirection);
    }
}
