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
import units.Enemies.Enemy;
import units.Enemies.Hive;
import units.ILayeredGraphics;
import units.Towers.Tower;
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
    public static final int SQUARE_SIZE = 20;

    /*
     * Constant which defines how many squares are in the board.
     */
    public static final int NUM_SQUARES = 45;

    /*
     * Constant which defines how big the panel is as a whole.
     */
    public static final int SIZE = SQUARE_SIZE * NUM_SQUARES;

    //variable which tracks which units are currently on the board
    private BoardState boardState;

    //variable which exposes search APIs for units on the board
    private BoardSearch boardSearch;

    /**
     * Constructor which initializes the game board, including setting the size,
     * and filling the squares with their initial contents.
     *
     * @param intitialState the intial board state to use for setting up
     */
    public GameBoardPanel(BoardState intitialState) {
        setPreferredSize(new Dimension(SIZE, SIZE));
        boardState = intitialState;
        boardSearch = new BoardSearch(boardState);
    }

    /**
     * Adds a Unit to the board.
     *
     * @param toAdd the Unit to be added
     */
    public void addUnit(Unit toAdd) {
        if (toAdd instanceof Tower) {
            for (Enemy enemy : boardState.enemies) {
                if (enemy.getGridPosition().equals(toAdd.getGridPosition())) {
                    return;
                }
            }

            Tower addTower = (Tower) toAdd;
            boardState.towers[toAdd.getGridPosition().x][toAdd.getGridPosition().y] = addTower;
        } else if (toAdd instanceof Enemy) {
            boardState.enemies.add((Enemy) toAdd);
        } else {
            throw new Error("Tried to add a unit that was neither a Tower nor an Enemy.");
        }
    }

    /**
     * Method which returns an object that exposes various methods for searching
     * the units on the board.
     *
     * @return a BoardSearch object which exposes many methods for searching
     */
    public BoardSearch search() {
        return boardSearch;
    }

    /**
     * Update method. This is called continuously by the main game loop to
     * simulated continuous action. Calls all objects on the game board to
     * update themselves.
     */
    public void tick() {
        for (Enemy enemy : (LinkedList<Enemy>) boardState.enemies.clone()) {
            enemy.tick(this);
            if (enemy.isDead()) {
                boardState.enemies.remove(enemy);
            }
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
        paintWhite(g);
        drawEnemies(g);
        drawTowers(g);
        drawEnemyLayers(g);
        drawTowerLayers(g);
    }

    /**
     * Method which fills the entire board with white to prep for a new render.
     *
     * @param g the Graphics object to draw on
     */
    private void paintWhite(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, SIZE, SIZE);
    }

    /**
     * Method which asks each Enemy to draw itself.
     *
     * @param g the Graphics object to draw on
     */
    private void drawEnemies(Graphics g) {
        for (Unit unit : (LinkedList<Enemy>) boardState.enemies.clone()) {
            unit.drawSelf(g);
        }
    }

    /**
     * Method which asks each Tower to draw itself.
     *
     * @param g the Graphics object to draw on
     */
    private void drawTowers(Graphics g) {
        for (int x = 0; x < NUM_SQUARES; x++) {
            for (int y = 0; y < NUM_SQUARES; y++) {
                if (boardState.towers[x][y] != null) {
                    boardState.towers[x][y].drawSelf(g);
                }
            }
        }
    }

    /**
     * Method which asks each Tnemy that draws top layer graphics to do so.
     *
     * @param g the Graphics object to draw on
     */
    private void drawEnemyLayers(Graphics g) {
        for (Unit unit : (LinkedList<Enemy>) boardState.enemies.clone()) {
            if (unit instanceof ILayeredGraphics) {
                ((ILayeredGraphics) unit).drawLayer(g);
            }
        }
    }

    /**
     * Method which asks each Tower that draws top layer graphics to do so.
     *
     * @param g the Graphics object to draw on
     */
    private void drawTowerLayers(Graphics g) {
        for (int x = 0; x < NUM_SQUARES; x++) {
            for (int y = 0; y < NUM_SQUARES; y++) {
                Tower currentTower = boardState.towers[x][y];
                if (currentTower != null && currentTower instanceof ILayeredGraphics) {
                    ((ILayeredGraphics) currentTower).drawLayer(g);
                }
            }
        }
    }
}
