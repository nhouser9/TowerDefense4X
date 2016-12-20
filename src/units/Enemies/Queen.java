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

/**
 * Class representing a monster that wanders around until it has lived for long
 * enough to sacrifice itself and found a new Hive, which will spawn more
 * monsters.
 *
 * @author Nick Houser
 */
public class Queen extends Mover {

    //constant which defines how long a Queen must survive to found a new Hive
    protected static final int HIVE_DELAY = 500;
    
    //variable tracking whether a queen has lived long enough to found a Hive
    private int lifetime;
    
    /**
     * Constructor which calls the parent constructor to initialize position and
     * direction.
     *
     * @param xPosition the x position where the Burrower should be created
     * @param yPosition the y position where the Burrower should be created
     * @param target the point the Burrower should travel towards
     */
    public Queen(int xPosition, int yPosition, Point target) {
        super(xPosition, yPosition, target);
        lifetime = 0;
    }

    /**
     * Method which defines the speed of a Queen.
     *
     * @return the speed of a Queen as a double
     */
    @Override
    protected double getSpeed() {
        return 200;
    }

    /**
     * Method which will return the initial health of a Queen.
     *
     * @return the initial health of the unit
     */
    @Override
    public int initialHealth() {
        return 7;
    }

    /**
     * Method which returns the draw size of a Queen.
     *
     * @return the draw size of a Burrower
     */
    @Override
    public int getSize() {
        return 6;
    }

    /**
     * Method which allows the Queen to move and, if it has lived long enough,
     * sacrifice itself to found a new Hive.
     *
     * @param board the game board on which the unit is acting
     */
    @Override
    public void tick(GameBoardPanel board) {
        move(board);
        lifetime = lifetime + 1;
        if (lifetime >= HIVE_DELAY) {
            board.addUnit(new Hive(getPosition().x, getPosition().y));
            destroy();
        }
    }

    /**
     * Method which asks the Queen to draw itself on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
    }
}
