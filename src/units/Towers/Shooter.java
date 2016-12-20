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
import units.Enemies.Enemy;

/**
 * Class representing a Shooter, a type of Tower which looks for nearby Enemies
 * and deals damage to them one at a time with a ranged attack.
 *
 * @author Nick Houser
 */
public class Shooter extends Powered {

    //the range of a shooter on the grid
    private static final int RANGE = 3;
    
    //the amount of damage this unit deals to enemies
    private static final int DAMAGE = 3;
    
    //the amount of time that must elapse before this unit can fire again
    private static final int FIRING_DELAY = 50;
    
    //the amount of ticks that have elapsed since this unit fired
    private int firingCooldown;
    
    //the last enemy fired upon, used for drawing graphics
    private Enemy lastTarget;
    
    /**
     * Constructor which calls the inherited constructor to initialize position.
     *
     * @param xPosition the x position where the user wants to create the
     * Shooter
     * @param yPosition the y position where the user wants to create the
     * Shooter
     */
    public Shooter(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        firingCooldown = FIRING_DELAY;
        lastTarget = null;
    }

    /**
     * Method which allows this Shooter to search for nearby units and fire at
     * one if enough time has passed since it last fired.
     *
     * @param board the board to search for nearby units
     */
    @Override
    protected void poweredTick(GameBoardPanel board) {
        lastTarget = null;
        firingCooldown = firingCooldown + 1;
        if (firingCooldown < FIRING_DELAY) {
            return;
        }
        
        Point topLeftOfRangeArea = new Point(getGridPosition().x - RANGE, getGridPosition().y - RANGE);
        Point bottomRightOfRangeArea = new Point(getGridPosition().x + RANGE, getGridPosition().y + RANGE);
        Enemy currentTarget = board.firstEnemyInArea(topLeftOfRangeArea, bottomRightOfRangeArea);
        if (currentTarget != null) {
            currentTarget.changeHealth(-DAMAGE);
            firingCooldown = 0;
            lastTarget = currentTarget;
        }
    }

    /**
     * Method which defines the initial health of a Shooter.
     *
     * @return the initial health of a Shooter
     */
    @Override
    protected int initialHealth() {
        return 400;
    }

    /**
     * Method which draws a Shooter on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
        if (lastTarget != null) {
            g.setColor(Color.BLACK);
            g.drawLine(getCenterPosition().x, getCenterPosition().y, lastTarget.getCenterPosition().x, lastTarget.getCenterPosition().y);
        }
    }
}
