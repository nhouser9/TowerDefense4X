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
import java.awt.Color;
import java.awt.Graphics;

/**
 * Class representing a monster that burrows its way through walls and towers;
 * it is essentially a melee attacker.
 *
 * @author Nick Houser
 */
public class Burrower extends Enemy {
    
    /**
     * Constructor which calls the parent constructor to initialize position.
     *
     * @param xPosition the x position where the Burrower should be created
     * @param yPosition the y position where the Burrower should be created
     */
    public Burrower(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    /**
     * Method which will return the initial health of the unit.
     * 
     * @return the initial health of the unit
     */
    @Override
    public int initialHealth() {
        return 10;
    }

    /**
     * Method which returns the draw size of a Burrower.
     * 
     * @return the draw size of a Burrower
     */
    @Override
    public int getSize() {
        return 10;
    }
    
    /**
     * Method which allows the Burrower to move.
     * 
     * @param board the game board on which the unit is acting
     */
    @Override
    public void tick(GameBoardPanel board) {
        Tower blocker = move(board, new DirectionVector(0, .5));
        if (blocker != null) {
            blocker.changeHealth(-1);
        }
    }

    /**
     * Method which asks the Burrower to draw itself on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(getPosition().x, getPosition().y, 10, 10);
    }
}