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
import java.awt.Graphics;
import java.awt.Point;

/**
 * Abstract class representing an entity that exists on the board and takes
 * actions each tick. This could be a player-controlled unit (a tower) or an
 * AI-controller unit (an enemy).
 *
 * @author Nick Houser
 */
public abstract class Unit {

    //the health of the unit
    private int health;

    /**
     * Constructor which initializes health.
     */
    public Unit() {
        health = initialHealth();
    }

    /**
     * Method which exposes health for modification.
     *
     * @param delta the amount to increment the health
     */
    public void changeHealth(int delta) {
        health = health + delta;
    }

    /**
     * Method which allows a Unit to be instantly marked for deletion.
     */
    public void destroy() {
        health = -1000;
    }

    /**
     * Method which checks if this unit has been destroyed.
     *
     * @return true if the unit's health is 0 or less, false otherwise
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Method which returns the position of the center of the unit. This is used
     * during graphics rendering.
     *
     * @return the center Point of the unit
     */
    public Point getCenterPosition() {
        return new Point(getPosition().x + getSize() / 2, getPosition().y + getSize() / 2);
    }

    /**
     * Method which will return the initial health of the unit.
     *
     * @return the initial health of the unit
     */
    protected abstract int initialHealth();

    /**
     * Method which will return the draw size of the unit.
     *
     * @return the draw size of the unit
     */
    public abstract int getSize();

    /**
     * Gets the current position of the unit in terms of its co-ordinates on the
     * board.
     *
     * @return the position of the unit as a Point object
     */
    public abstract Point getPosition();

    /**
     * Gets the current position of the unit in terms of its location on the
     * grid imposed on the board.
     *
     * @return the position of the unit on the grid as a Point object
     */
    public abstract Point getGridPosition();

    /**
     * Method which defines what action the unit will take during gameplay. Does
     * nothing by default; implementations must override this if they wish to
     * act. This will be called repeatedly by the main game loop to simulate
     * continuous action.
     *
     * @param board the game board on which the unit is acting
     */
    public void tick(GameBoardPanel board) {
        //do nothing by default
    }

    /**
     * Method which asks the Unit to draw itself on the board.
     *
     * @param g the Graphics object to draw on
     */
    public abstract void drawSelf(Graphics g);
}
