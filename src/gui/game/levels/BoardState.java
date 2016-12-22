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
package gui.game.levels;

import gui.game.GameBoardPanel;
import java.util.LinkedList;
import towerdefense4x.SingletonRandom;
import units.enemies.Enemy;
import units.enemies.Hive;
import units.towers.Generator;
import units.towers.Terrain;
import units.towers.Tower;

/**
 * Class which tracks the current state of the board, consisting mainly of which
 * units are on the board.
 *
 * @author Nick Houser
 */
public class BoardState {

    //a list of the Enemies on the board
    public LinkedList<Enemy> enemies;

    //a double array of the Towers on the board
    public Tower[][] towers;

    //variables which track the size of the board
    public final int squareSize;
    public final int numSquares;
    public final int initialTime;

    /**
     * Constructor which takes an initial state and sets the board up according
     * to that state.
     * 
     * @param numSquares the number of squares to create for this board state
     * @param initialTime the amount of time available to solve the level
     */
    public BoardState(int numSquares, int initialTime) {
        this.numSquares = numSquares;
        this.initialTime = initialTime;
        squareSize = Math.floorDiv(GameBoardPanel.SIZE, numSquares);
        enemies = new LinkedList<>();
        towers = new Tower[numSquares][numSquares];
    }
}
