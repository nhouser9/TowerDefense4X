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
import units.ILayeredGraphics;

/**
 * Extension of Tower that represents a type of tower which consumes power; that
 * is, it needs to be close enough to an active power source to function. It is
 * the responsibility of the power source to call this class's API to power an
 * instance of Powered.
 *
 * @author Nick Houser
 */
public abstract class Powered extends Tower implements ILayeredGraphics {

    //reference to the unit powering this unit
    private Powered poweredBy;

    /**
     * Constructor which calls the inherited constructor to set the position of
     * the PowerConsumer before setting the power state.
     *
     * @param xPosition the x position where the user wants to create the
     * PowerConsumer
     * @param yPosition the y position where the user wants to create the
     * PowerConsumer
     * @param squareSize the size of one grid square on the board
     */
    public Powered(int xPosition, int yPosition, int squareSize) {
        super(xPosition, yPosition, squareSize);
        poweredBy = null;
    }

    /**
     * Checks whether this Unit is receiving power.
     *
     * @return true if the Unit is powered, false othewise
     */
    public boolean isPowered() {
        return poweredBy != null;
    }

    /**
     * Sets a passed generator as the power provider for this unit, which also
     * marks this unit as powered.
     *
     * @param provider the Generator providing power to this unit
     * @return true if the power operation was successful, false otherwise
     */
    public boolean power(Generator provider) {
        if (!isPowered()) {
            poweredBy = provider;
            return true;
        }
        return false;
    }

    /**
     * Marks this unit as unpowered and removes the reference to any unit that
     * was powering it.
     */
    protected void unPower() {
        poweredBy = null;
    }

    /**
     * Method which defines what action the unit will take during gameplay. This
     * will be called repeatedly by the main game loop to simulate continuous
     * action.
     *
     * @param board the game board on which the unit is acting
     */
    @Override
    public void tick(GameBoardPanel board) {
        if (isPowered()) {
            poweredTick(board);
        }
    }

    /**
     * Method which supports drawing strikethrough lines through Towers that are
     * unpowered. These use the drawLayer() method of ILayeredGraphics to
     * support drawing them on top of everything else.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawLayer(Graphics g) {
        if (!isPowered()) {
            g.setColor(Color.BLACK);
            g.drawLine(getPosition().x, getPosition().y, getPosition().x + getScaledSize(), getPosition().y + getScaledSize());
        }
    }

    /**
     * Method which defines what action the unit will take while it is powered.
     * This implementation allows a Powered to distinguish between its powered
     * and unpowered states and take different actions based on that state. This
     * will be called repeatedly by the main game loop to simulate continuous
     * action.
     *
     * @param board the game board on which the unit is acting
     */
    protected abstract void poweredTick(GameBoardPanel board);
}
