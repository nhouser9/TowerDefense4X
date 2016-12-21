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

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class which represents a puchasable Tower that does nothing but get in the way of Enemies. It has high enough health to withstand some attacks and is intended to be used as a blockade to protect more functional Towers.
 * 
 * @author Nick Houser
 */
public class Blocker extends Inactive {

    /**
     * Constructor which calls the parent constructor to initialize position.
     *
     * @param xPosition the x position where the user wants to create the Terrain
     * @param yPosition the y position where the user wants to create the Terrain
     * @param squareSize the size of one grid square on the board
     */
    public Blocker(int xPosition, int yPosition, int squareSize) {
        super(xPosition, yPosition, squareSize);
    }

    /**
     * Method which will return the initial health of a Blocker.
     * 
     * @return the initial health of a Blocker
     */
    @Override
    protected int initialHealth() {
        return 1000;
    }

    /**
     * Method which draws a Blocker on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
    }
}
