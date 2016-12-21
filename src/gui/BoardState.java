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

import java.util.LinkedList;
import units.Enemies.Enemy;
import units.Enemies.Hive;
import units.Towers.Generator;
import units.Towers.Terrain;
import units.Towers.Tower;

/**
 * Class which tracks the current state of the board, consisting mainly of which
 * units are on the board.
 *
 * @author Nick Houser
 */
public class BoardState {

    //a list of the Enemies on the board
    protected LinkedList<Enemy> enemies;

    //a double array of the Towers on the board
    protected Tower[][] towers;

    //variables which track the size of the board
    private int squareSize;
    private int numSquares;

    /**
     * Constructor which takes an initial state and sets the board up according
     * to that state.
     *
     * @param state constant indicating which initial state should be used
     */
    public BoardState(InitialState state) {
        enemies = new LinkedList<>();
        
        switch (state) {
            case EMPTY:
                setupEmptyState();
                break;
            case INTEGRATIONTEST:
                setupIntegrationTestState();
                break;
            case LEVEL_1:
                setupLevelOne();
                break;
            default:
                throw new Error("Invalid level selection");
        }
    }

    /**
     * Accessor for squareSize field, which represents the size of one grid
     * square.
     *
     * @return the draw size for one grid square
     */
    public int getSquareSize() {
        return squareSize;
    }

    /**
     * Accessor for numSquares field, which represents the number of grid
     * squares on the board.
     *
     * @return the number of grid squares on the board
     */
    public int getNumSquares() {
        return numSquares;
    }

    /**
     * Sets up the empty gamestate, which consists of no Units at all.
     */
    private void setupEmptyState() {
        setNumSquares(10);

        for (int x = 0; x < numSquares; x++) {
            for (int y = 0; y < numSquares; y++) {
                towers[x][y] = null;
            }
        }
    }

    /**
     * Sets up the integration test gamestate, which varies with testing needs.
     */
    private void setupIntegrationTestState() {
        setNumSquares(45);

        fillTerrain();

        addHive(4, 2);
        addHive(40, 40);
        addHive(41, 41);

        addBaseGenerator(4, 6);
    }

    /**
     * Sets up the level one gamestate.
     */
    private void setupLevelOne() {
        setNumSquares(5);

        fillTerrain();

        addHive(2, 2);

        addBaseGenerator(0, 0);
        addBaseGenerator(4, 4);
    }

    /**
     * Method which sets the number of squares on the board. Also initializes
     * the Towers array.
     *
     * @param num the number of squares to set
     */
    private void setNumSquares(int num) {
        numSquares = num;
        squareSize = Math.floorDiv(GameBoardPanel.SIZE, numSquares);
        towers = new Tower[numSquares][numSquares];
    }

    /**
     * Method which fills the board with Terrain.
     */
    private void fillTerrain() {
        for (int x = 0; x < numSquares; x++) {
            for (int y = 0; y < numSquares; y++) {
                towers[x][y] = new Terrain(squareSize * x, squareSize * y, squareSize);
            }
        }
    }

    /**
     * Method which adds a Hive to the grid at the specified position.
     *
     * @param gridX the x position at which to add the hive
     * @param gridY the y position at which to add the hive
     */
    private void addHive(int gridX, int gridY) {
        towers[gridX][gridY] = null;
        enemies.add(new Hive(gridX * squareSize, gridY * squareSize, squareSize));
    }

    /**
     * Method which adds an always-powered Generator to the grid at the
     * specified location.
     *
     * @param gridX the x position at which to add the hive
     * @param gridY the y position at which to add the hive
     */
    private void addBaseGenerator(int gridX, int gridY) {
        Generator baseGenerator = new Generator(gridX * squareSize, gridY * squareSize, squareSize);
        baseGenerator.power(new Generator(0, 0, squareSize));
        towers[gridX][gridY] = baseGenerator;
    }
}
