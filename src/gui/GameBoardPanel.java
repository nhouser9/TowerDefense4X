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
     * Constant representing the size of the board.
     */
    public static final int SIZE = 900;

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
        boardState = intitialState;
        boardSearch = new BoardSearch(boardState);
        setPreferredSize(new Dimension(SIZE, SIZE));
    }
    
    /**
     * Method which exposes the square size of the current board state.
     *
     * @return the draw size of one grid square
     */
    public int getSquareSize() {
        return boardState.getSquareSize();
    }

    /**
     * Adds a Unit to the board.
     *
     * @param toAdd the Unit to be added
     */
    public void addUnit(Unit toAdd) {
        if (toAdd instanceof Tower) {
            Tower addTower = (Tower) toAdd;
            
            for (Enemy enemy : boardState.enemies) {
                Point enemyGridPosition = boardSearch.absoluteToGridPosition(enemy.getPosition());
                if (enemyGridPosition.equals(addTower.getGridPosition())) {
                    return;
                }
            }

            boardState.towers[addTower.getGridPosition().x][addTower.getGridPosition().y] = addTower;
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

        for (int x = 0; x < boardState.getNumSquares(); x++) {
            for (int y = 0; y < boardState.getNumSquares(); y++) {
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
        for (int x = 0; x < boardState.getNumSquares(); x++) {
            for (int y = 0; y < boardState.getNumSquares(); y++) {
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
        for (int x = 0; x < boardState.getNumSquares(); x++) {
            for (int y = 0; y < boardState.getNumSquares(); y++) {
                Tower currentTower = boardState.towers[x][y];
                if (currentTower != null && currentTower instanceof ILayeredGraphics) {
                    ((ILayeredGraphics) currentTower).drawLayer(g);
                }
            }
        }
    }
}
