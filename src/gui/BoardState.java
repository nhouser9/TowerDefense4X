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
import units.Burrower;
import units.Enemy;
import units.Terrain;
import units.Tower;

/**
 * Class which tracks the current state of the board, consisting mainly of which
 * units are on the board.
 *
 * @author Nick Houser
 */
public class BoardState {

    /**
     * Enum which defines a constant for each possible initial board state.
     */
    public static enum InitialState {
        GAMEPLAY,
        EMPTY
    }

    //a list of the Enemies on the board
    protected LinkedList<Enemy> enemies;

    //a double array of the Towers on the board
    protected Tower[][] towers;

    /**
     * Constructor which takes an initial state and sets the board up according
     * to that state.
     *
     * @param state constant indicating which initial state should be used
     */
    public BoardState(InitialState state) {
        enemies = new LinkedList<>();
        towers = new Tower[GameBoardPanel.NUM_SQUARES][GameBoardPanel.NUM_SQUARES];

        switch (state) {
            case GAMEPLAY:
                setupGameplayState();
                break;
            case EMPTY:
                setupEmptyState();
                break;
        }
    }

    /**
     * Sets up the normal gamestate, which consists of mostly Terrain with a few
     * initial units.
     */
    private void setupGameplayState() {
                for (int x = 0; x < GameBoardPanel.NUM_SQUARES; x++) {
                    for (int y = 0; y < GameBoardPanel.NUM_SQUARES; y++) {
                        towers[x][y] = new Terrain(GameBoardPanel.SQUARE_SIZE * x, GameBoardPanel.SQUARE_SIZE * y);
                    }
                }
                
                towers[4][1] = null;
                enemies.add(new Burrower(4 * GameBoardPanel.SQUARE_SIZE + 20, GameBoardPanel.SQUARE_SIZE + 20));
                enemies.add(new Burrower(4 * GameBoardPanel.SQUARE_SIZE + 40, GameBoardPanel.SQUARE_SIZE + 20));
                enemies.add(new Burrower(4 * GameBoardPanel.SQUARE_SIZE + 60, GameBoardPanel.SQUARE_SIZE + 20));
                enemies.add(new Burrower(4 * GameBoardPanel.SQUARE_SIZE + 80, GameBoardPanel.SQUARE_SIZE + 20));
    }

    /**
     * Sets up the empty gamestate, which consists of no Units at all.
     */
    private void setupEmptyState() {
        for (int x = 0; x < GameBoardPanel.NUM_SQUARES; x++) {
            for (int y = 0; y < GameBoardPanel.NUM_SQUARES; y++) {
                towers[x][y] = null;
            }
        }
    }
}
