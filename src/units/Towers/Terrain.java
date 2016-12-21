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
 * Class representing a unit of terrain. Every square is filled with this by
 * default. It does nothing and serves only as a removable impediment for
 * enemies.
 *
 * @author Nick Houser
 */
public class Terrain extends Inactive {

    /**
     * Constructor which calls the parent constructor to initialize position.
     *
     * @param xPosition the x position where the user wants to create the Terrain
     * @param yPosition the y position where the user wants to create the Terrain
     * @param squareSize the size of one grid square on the board
     */
    public Terrain(int xPosition, int yPosition, int squareSize) {
        super(xPosition, yPosition, squareSize);
    }

    /**
     * Method which will return the initial health of a Terrain.
     * 
     * @return the initial health of a Terrain
     */
    @Override
    public int initialHealth() {
        return 200;
    }

    /**
     * Method which draws a Terrain on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(new Color(200, 200, 100));
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
    }
}
