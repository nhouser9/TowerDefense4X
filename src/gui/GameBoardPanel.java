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
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import javax.swing.JPanel;
import units.Enemy;
import units.Tower;
import units.Unit;

/**
 * Extension of JPanel which represents the game board.
 *
 * @author Nick Houser
 */
public class GameBoardPanel extends JPanel {

    /**
     * Constant which defines how big each square on the board will be.
     */
    public static final int SQUARE_SIZE = 100;

    /*
     * Constant which defines how many squares are in the board.
     */
    public static final int NUM_SQUARES = 9;

    /*
     * Constant which defines how big the panel is as a whole.
     */
    public static final int SIZE = SQUARE_SIZE * NUM_SQUARES;

    //variable which tracks which units are currently on the board
    private BoardState boardState;

    /**
     * Constructor which initializes the game board, including setting the size,
     * and filling the squares with their initial contents.
     *
     * @param intitialState the intial board state to use for setting up
     */
    public GameBoardPanel(BoardState intitialState) {
        setPreferredSize(new Dimension(SIZE, SIZE));
        boardState = intitialState;
    }

    /**
     * Adds a Unit to the board.
     *
     * @param toAdd the Unit to be added
     */
    public void addUnit(Unit toAdd) {
        if (toAdd instanceof Tower) {
            Tower addTower = (Tower) toAdd;
            int gridX = toAdd.getPosition().x / SQUARE_SIZE;
            int gridY = toAdd.getPosition().y / SQUARE_SIZE;
            boardState.towers[gridX][gridY] = addTower;
        } else if (toAdd instanceof Enemy) {
            boardState.enemies.add((Enemy) toAdd);
        } else {
            throw new Error("Tried to add a unit that was neither a Tower nor an Enemy.");
        }
    }

    /**
     * Checks whether a given position is occupied by a Tower. This is used to
     * ensure that Enemies cannot move freely into squares where the player has
     * placed Towers.
     *
     * @param position a Point ojbect representing the position to check
     * @return the Tower at the passed position, or null if none exists
     */
    public Tower towerAtPosition(Point position) {
        int gridX = Math.floorDiv(position.x, SQUARE_SIZE);
        int gridY = Math.floorDiv(position.y, SQUARE_SIZE);
        return boardState.towers[gridX][gridY];
    }

    /**
     * Update method. This is called continuously by the main game loop to
     * simulated continuous action. Calls all objects on the game board to
     * update themselves.
     */
    public void tick() {
        LinkedList<Enemy> deadEnemies = new LinkedList<>();
        for (Enemy enemy : boardState.enemies) {
            enemy.tick(this);
            if (enemy.isDead()) {
                deadEnemies.add(enemy);
            }
        }
        for (Enemy deadEnemy : deadEnemies) {
            boardState.enemies.remove(deadEnemy);
        }

        for (int x = 0; x < NUM_SQUARES; x++) {
            for (int y = 0; y < NUM_SQUARES; y++) {
                Tower current = boardState.towers[x][y];
                if (current != null) {
                    current.tick(this);
                    if (current.isDead()) {
                        boardState.towers[x][y] = null;
                    }
                }
            }
        }
    }

    /**
     * Override of JPanel's paintComponent which is called every time this
     * component is rendered. Calls all objects on the game board to draw
     * themselves.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, SIZE, SIZE);

        for (Unit unit : boardState.enemies) {
            unit.drawSelf(g);
        }

        for (int x = 0; x < NUM_SQUARES; x++) {
            for (int y = 0; y < NUM_SQUARES; y++) {
                if (boardState.towers[x][y] != null) {
                    boardState.towers[x][y].drawSelf(g);
                }
            }
        }
    }
}
