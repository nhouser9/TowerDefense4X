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

import gui.GameBoardPanel;
import java.awt.Point;

/**
 * Abstract class which represents an Enemy, which is an AI-controlled unit that
 * tries to defeat the player.
 *
 * @author Nick Houser
 */
public abstract class Enemy extends Unit {

    //the location of this unit on the board
    private DoublePoint position;

    /**
     * Constructor which sets the position of the Enemy equal to the passed
     * arguments.
     *
     * @param xPosition the x position where the Enemy should be created
     * @param yPosition the y position where the Enemy should be created
     */
    public Enemy(int xPosition, int yPosition) {
        super();
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

    /**
     * Method which tries to move in the passed direction and the Tower that got
     * in the way if not. The move will fail if the Enemy's hitbox would
     * intersect a Tower after the move. The move will also fail without
     * returning a Tower if the move would exit the game board.
     *
     * @param board the game board on which the move is being performed
     * @param direction a DirectionVector object representing the direction of
     * the move being attempted
     * @return the Tower that blocked this move, or null if the move succeeded
     */
    protected Tower move(GameBoardPanel board, DirectionVector direction) {
        DoublePoint target = position.offset(direction);

        Point topLeft = target.toPoint();
        Point topRight = new Point(topLeft.x + getSize(), topLeft.y);
        Point bottomLeft = new Point(topLeft.x, topLeft.y + getSize());
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
}
