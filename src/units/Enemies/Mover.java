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
     */
    public Mover(int xPosition, int yPosition, Point target) {
        super(xPosition, yPosition);
        direction = new DirectionVector(getPosition(), target, getScaledSpeed());
    }

    /**
     * Method which tries to move in target direction and returns the Tower that
     * got in the way if one exists. The move will fail if the Enemy's hitbox
     * would intersect a Tower after the move. The move will also fail without
     * returning a Tower if the move would exit the game board.
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

        if (topLeft.x <= 0 || topLeft.y <= 0) {
            return null;
        }
        if (bottomRight.x >= GameBoardPanel.SIZE || bottomRight.y >= GameBoardPanel.SIZE) {
            return null;
        }

        if (board.towerAtPosition(topLeft) != null) {
            return board.towerAtPosition(topLeft);
        }
        if (board.towerAtPosition(topRight) != null) {
            return board.towerAtPosition(topRight);
        }
        if (board.towerAtPosition(bottomLeft) != null) {
            return board.towerAtPosition(bottomLeft);
        }
        if (board.towerAtPosition(bottomRight) != null) {
            return board.towerAtPosition(bottomRight);
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
        return GameBoardPanel.SQUARE_SIZE / getSpeed();
    }

    /**
     * Method which defines how fast the Mover will move.
     *
     * @return a double representing the number of units the Mover will travel
     * per tick
     */
    protected abstract double getSpeed();
}
