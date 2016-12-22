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
package units.towers;

import gui.game.GameBoardPanel;

/**
 * Extension of Tower which disables ticking functionality. This is intended for
 * implementation by units which will take no action during gameplay.
 *
 * @author Nick Houser
 */
public abstract class Inactive extends Tower {
    
    /**
     * Constructor which calls the parent constructor to initialize position.
     *
     * @param xPosition the x position where the user wants to create the Terrain
     * @param yPosition the y position where the user wants to create the Terrain
     * @param squareSize the size of one grid square on the board
     */
    public Inactive(int xPosition, int yPosition, int squareSize) {
        super(xPosition, yPosition, squareSize);
    }
    
    /**
     * Method which does nothing because this unit should take no actions during
     * gameplay.
     *
     * @param board the game board on which the unit is acting
     */
    @Override
    public void tick(GameBoardPanel board) {
        //do nothing
    }
}
