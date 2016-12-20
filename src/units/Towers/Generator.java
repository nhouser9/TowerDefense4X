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

/**
 * Class representing a Generator, a type of Tower that acts as a power source
 * for units which needs Power. A generator needs power itself, so it extends
 * class Powered
 *
 * @author Nick Houser
 */
public class Generator extends Powered {

    //the range of a Generator on the grid
    private static final int RANGE = 2;

    //list of units being powered by this generator
    private LinkedList<Powered> powering;

    /**
     * Constructor which calls the inherited constructor to initialize position.
     *
     * @param xPosition the x position where the user wants to create the
     * Generator
     * @param yPosition the y position where the user wants to create the
     * Generator
     */
    public Generator(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        powering = new LinkedList<>();
    }

    /**
     * Method which searches for nearby units of type Powered that need a power
     * source and powers them. Also unpowers itself if it is marked for death.
     * This is called repeatedly by the main game loop to simulate continuous
     * action.
     *
     * @param board the game board to search for nearby units
     */
    @Override
    protected void poweredTick(GameBoardPanel board) {
        providePower(board);
        removeDeadConsumers();
        deactivateIfDead();
    }

    /**
     * Method that is called as part of the poweredTick method. Provides power
     * to nearby power consumers.
     *
     * @param board the game board to search for nearby units
     */
    private void providePower(GameBoardPanel board) {
        for (int xSearch = -RANGE; xSearch <= RANGE; xSearch++) {
            for (int ySearch = -RANGE; ySearch <= RANGE; ySearch++) {
                Point gridSearchPoint = new Point(xSearch + getGridPosition().x, ySearch + getGridPosition().y);
                Tower towerAtPosition = board.towerAtGridPosition(gridSearchPoint);

                if (towerAtPosition == null) {
                    continue;
                }
                if (!Powered.class.isAssignableFrom(towerAtPosition.getClass())) {
                    continue;
                }
                if (towerAtPosition == this) {
                    continue;
                }

                Powered toPower = (Powered) towerAtPosition;
                if (toPower.power(this)) {
                    powering.add(toPower);
                }
            }
        }
    }

    /**
     * Method that is called as part of the poweredTick method. Removes a power
     * consumer from the list of consumers if it is dead.
     */
    private void removeDeadConsumers() {
        for (Powered consumer : (LinkedList<Powered>) powering.clone()) {
            if (consumer.isDead()) {
                powering.remove(consumer);
            }
        }
    }

    /**
     * Method that is called as part of the poweredTick method. Un-powers this
     * unit if it is dead.
     */
    private void deactivateIfDead() {
        if (isDead()) {
            unPower();
        }
    }

    /**
     * Override of unPower from the Powered class. Calls super.unPower() to set
     * this as unpowered, but also chains the loss of power forward to all units
     * which were being powered by this Generator.
     */
    @Override
    protected void unPower() {
        super.unPower();
        while (!powering.isEmpty()) {
            powering.removeFirst().unPower();
        }
    }

    /**
     * Method which defines the initial health of a Generator.
     *
     * @return the initial health of a Generator
     */
    @Override
    protected int initialHealth() {
        return 300;
    }

    /**
     * Method which draws a Generator on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
        g.setColor(Color.BLACK);
        for (Powered powered : powering) {
            g.drawLine(getCenterPosition().x, getCenterPosition().y, powered.getCenterPosition().x, powered.getCenterPosition().y);
        }
    }
}
