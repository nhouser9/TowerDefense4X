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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import units.ILayeredGraphics;

/**
 * Class representing a type of Tower which heals other nearby Towers.
 *
 * @author Nick Houser
 */
public class Healer extends Powered implements ILayeredGraphics {

    //the range of a Healer on the grid
    private static final int RANGE = 2;

    //the last Tower healed, used for drawing graphics
    private Tower lastTarget;

    /**
     * Constructor which calls the inherited constructor to initialize position.
     *
     * @param xPosition the x position where the user wants to create the Healer
     * @param yPosition the y position where the user wants to create the Healer
     * @param squareSize the size of one grid square on the board
     */
    public Healer(int xPosition, int yPosition, int squareSize) {
        super(xPosition, yPosition, squareSize);
        lastTarget = null;
    }

    /**
     * Method which searches for nearby Towers are less than full health and
     * heals them. This is called repeatedly by the main game loop to simulate
     * continuous action.
     *
     * @param board the game board to search for nearby units
     */
    @Override
    protected void poweredTick(GameBoardPanel board) {
        lastTarget = null;
        
        Point searchTopLeft = new Point(getGridPosition().x - RANGE, getGridPosition().y - RANGE);
        Point searchBottomRight = new Point(getGridPosition().x + RANGE, getGridPosition().y + RANGE);
        LinkedList<Tower> towersInRange = board.search().allTowersInArea(searchTopLeft, searchBottomRight);

        for (Tower inRange : towersInRange) {
            if (inRange != this && inRange.isDamaged() && !(inRange instanceof Terrain)) {
                inRange.changeHealth(1);
                lastTarget = inRange;
                return;
            }
        }
    }

    /**
     * Method which defines the initial health of a Healer.
     *
     * @return the initial health of a Generator
     */
    @Override
    protected int initialHealth() {
        return 350;
    }

    /**
     * Method which draws a Healer on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
    }

    /**
     * Method which supports drawing lines indicating which Towers this Healer
     * is currently healing. These use the drawLayer() method of
     * ILayeredGraphics to support drawing them on top of everything else.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawLayer(Graphics g) {
        super.drawLayer(g);
        g.setColor(Color.BLACK);
        if (lastTarget != null) {
            g.setColor(Color.BLACK);
            g.drawLine(getCenterPosition().x, getCenterPosition().y, lastTarget.getCenterPosition().x, lastTarget.getCenterPosition().y);
        }
    }
}
