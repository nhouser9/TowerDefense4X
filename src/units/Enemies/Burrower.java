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

import gui.GameBoardPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import units.Towers.Tower;

/**
 * Class representing a monster that burrows its way through walls and towers;
 * it is essentially a melee attacker.
 *
 * @author Nick Houser
 */
public class Burrower extends Mover {
    
    /**
     * Constructor which calls the parent constructor to initialize position.
     *
     * @param xPosition the x position where the Burrower should be created
     * @param yPosition the y position where the Burrower should be created
     * @param target the point the Burrower should travel towards
     */
    public Burrower(int xPosition, int yPosition, Point target) {
        super(xPosition, yPosition, target);
    }

    /**
     * Method which defines the speed of a Burrower.
     * 
     * @return the speed of a Burrower as a double
     */
    @Override
    protected double getSpeed() {
        return 100;
    }
    
    /**
     * Method which will return the initial health of a Burrower.
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
     * Method which allows the Burrower to move and attack nearby Towers.
     * 
     * @param board the game board on which the unit is acting
     */
    @Override
    public void tick(GameBoardPanel board) {
        Tower blocker = move(board);
        if (blocker != null) {
            blocker.changeHealth(-1);
            if (blocker.isDead()) {
                destroy();
            }
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
        g.fillRect(getPosition().x, getPosition().y, getSize(), getSize());
    }
}