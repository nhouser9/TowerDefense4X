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

import gui.Game.GameBoardPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * Class representing a Hive, which is an Enemy that cannot be killed and which
 * spawns other Enemies.
 *
 * @author Nick Houser
 */
public class Hive extends Enemy {

    //constant indicating how often Burrowers will be spawned
    protected static final int CADENCE_BURROWER = 100;

    //constant indicating how often Queens will be spawned
    protected static final int CADENCE_QUEEN = 1000;

    //timer variable which tracks ticks so the Hive knows when to spawn Enemies
    private long lifetime;

    //random number generator
    private Random rng;

    /**
     * Constructor which sets the position of the Hive equal to the passed
     * arguments.
     *
     * @param xPosition the x position where the Enemy should be created
     * @param yPosition the y position where the Enemy should be created
     * @param squareSize the size of one board square
     */
    public Hive(int xPosition, int yPosition, int squareSize, Random rng) {
        super(xPosition - (xPosition % squareSize), yPosition - (yPosition % squareSize), squareSize);
        lifetime = 0;
        this.rng = rng;
    }

    /**
     * Override of the isDead method of superclass Unit to ensure that a Hive
     * cannot be killed.
     */
    @Override
    public boolean isDead() {
        return false;
    }

    /**
     * Method which will return the initial health of a Hive.
     *
     * @return the initial health of a Hive
     */
    @Override
    protected int initialHealth() {
        return 1;
    }

    /**
     * Method which will return the draw size of a Hive.
     *
     * @return the draw size of a Hive
     */
    @Override
    public int getSize() {
        return 1;
    }

    /**
     * Method which allows the Hive to spawn other Enemies.
     *
     * @param board the game board on which the unit is acting
     */
    @Override
    public void tick(GameBoardPanel board) {
        lifetime = (lifetime + 1) % 100000;
        
        spawnBurrower(board);
        spawnQueen(board);
    }

    /**
     * Method which handles spawning a new Burrower at the proper time.
     *
     * @param board the game board to add the new unit to
     */
    private void spawnBurrower(GameBoardPanel board) {
        if (lifetime % CADENCE_BURROWER == 0) {
            int yTarget = rng.nextInt();
            int xTarget = rng.nextInt();
            Point target = new Point(xTarget, yTarget);
            board.addUnit(new Burrower(getCenterPosition().x, getCenterPosition().y, target, squareSize));
        }
    }

    /**
     * Method which handles spawning a new Queen at the proper time.
     *
     * @param board the game board to add the new unit to
     */
    private void spawnQueen(GameBoardPanel board) {
        if (lifetime % CADENCE_QUEEN == 0) {
            int yTarget = rng.nextInt();
            int xTarget = rng.nextInt();
            Point target = new Point(xTarget, yTarget);
            board.addUnit(new Queen(getCenterPosition().x, getCenterPosition().y, target, squareSize));
        }
    }

    /**
     * Method which asks the Hive to draw itself on the board.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(getPosition().x, getPosition().y, getScaledSize(), getScaledSize());
    }
}
