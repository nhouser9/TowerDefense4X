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
package units.Towers;

import gui.GameBoardPanel;
import java.awt.Point;
import units.Unit;

/**
 * Abstract class which represents a Tower, which is a player-controlled unit
 * that either attacks enemies or assists other units.
 *
 * @author Nick Houser
 */
public abstract class Tower extends Unit {

    //the location of this unit on the board
    private final Point position;
    
    //the location of the unit on the grid imposed on the board
    private final Point gridPosition;

    /**
     * Constructor which sets the position of the Tower. Ensures the position is
     * aligned evenly to the grid defined by the game board.
     *
     * @param xPosition the x position where the user wants to create the Tower
     * @param yPosition the y position where the user wants to create the Tower
     */
    public Tower(int xPosition, int yPosition) {
        super();
        xPosition = xPosition - (xPosition % GameBoardPanel.SQUARE_SIZE);
        yPosition = yPosition - (yPosition % GameBoardPanel.SQUARE_SIZE);
        position = new Point(xPosition, yPosition);
        gridPosition = new Point(xPosition / GameBoardPanel.SQUARE_SIZE, yPosition / GameBoardPanel.SQUARE_SIZE);
    }

    /**
     * Gets the current position of the unit.
     *
     * @return the position of the unit as a Point object
     */
    @Override
    public Point getPosition() {
        return position;
    }

    /**
     * Returns the position on of the Tower on the imaginary grid to which
     * Towers snap. Does this by dividing the position by the size of a grid
     * square.
     *
     * @return the co-ordinates of this Tower on the grid imposed on the board
     */
    public Point getGridPosition() {
        return gridPosition;
    }

    /**
     * Returns the draw size of a Tower, which should be equal to the size of
     * one grid square on the board.
     *
     * @return the draw size of a Tower
     */
    @Override
    public int getSize() {
        return 1;
    }
}
