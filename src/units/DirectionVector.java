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
 *
 * @author Nick Houser
 */
class DirectionVector {

    //variables for storing the x and y components of this vector
    public final double xDirection;
    public final double yDirection;

    /**
     * Constructor which computes the x and y components of this vector based on
     * a source and destination of travel.
     *
     * @param source the origin of travel
     * @param target the destination of travel
     * @param speed the length of this vector
     */
    public DirectionVector(Point source, Point target, double speed) {
        double xDir = target.x - source.x;
        double yDir = target.y - source.y;
        double length = Math.sqrt((xDir * xDir) + (yDir * yDir));
        xDirection = (xDir / length) * speed;
        yDirection = (yDir / length) * speed;
    }

    /**
     * Constructor which sets the x and y components of this vector directly.
     *
     * @param xDirection the x component of direction for this vector
     * @param yDirection the y component of direction for this vector
     */
    public DirectionVector(double xDirection, double yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }
}
