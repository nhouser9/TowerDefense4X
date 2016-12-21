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
package units.Enemies;

import units.Towers.Tower;
import gui.GameBoardPanel;
import java.awt.Point;
import units.DirectionVector;
import units.DoublePoint;

/**
 * Extension of Enemy which represents an enemy that moves around the board.
 *
 * @author Nick Houser
 */
public abstract class Mover extends Enemy {

    //variable tracking the direction of travel
    private DirectionVector direction;

    /**
     * Constructor which calls the inherited constructor to initialize position
     * before initializing the direction vector.
     *
     * @param xPosition the x position where the Mover should be created
     * @param yPosition the y position where the Mover should be created
     * @param target the point the Mover should travel towards
     * @param squareSize the size of one grid square on the board
     */
    public Mover(int xPosition, int yPosition, Point target, int squareSize) {
        super(xPosition, yPosition, squareSize);
        direction = new DirectionVector(getPosition(), target, getScaledSpeed());
    }

    /**
     * Method which tries to move in target direction and returns the Tower that
     * got in the way if one exists. The move will fail if the Enemy's hitbox
     * would intersect a Tower after the move. The move will delete this Mover
     * if it would try to move off the board.
     *
     * @param board the game board on which the move is being performed
     * @return the Tower that blocked this move, or null if the move succeeded
     */
    protected Tower move(GameBoardPanel board) {
        DoublePoint target = position.offset(direction);

        Point topLeft = target.toPoint();
        Point topRight = new Point(topLeft.x + getScaledSize(), topLeft.y);
        Point bottomLeft = new Point(topLeft.x, topLeft.y + getScaledSize());
        Point bottomRight = new Point(topRight.x, bottomLeft.y);

        try {
            if (board.search().towerAtPosition(topLeft) != null) {
                return board.search().towerAtPosition(topLeft);
            }
            if (board.search().towerAtPosition(topRight) != null) {
                return board.search().towerAtPosition(topRight);
            }
            if (board.search().towerAtPosition(bottomLeft) != null) {
                return board.search().towerAtPosition(bottomLeft);
            }
            if (board.search().towerAtPosition(bottomRight) != null) {
                return board.search().towerAtPosition(bottomRight);
            }
        } catch (ArrayIndexOutOfBoundsException movedOffBoard) {
            destroy();
        }

        position = target;
        return null;
    }

    /**
     * Method which translates a Mover's speed into a scaled speed, which is
     * used to make the Mover appear to move at the same rate no matter how the
     * board is resized.
     *
     * @return the Mover's speed scaled in relation to the board size
     */
    protected double getScaledSpeed() {
        return squareSize / getSpeed();
    }

    /**
     * Method which defines how fast the Mover will move. Because the board size
     * is divided by this to achieve size-independent scaling, higher numbers
     * are smaller.
     *
     * @return a double representing the number of units the Mover will travel
     * per tick
     */
    protected abstract double getSpeed();
}
