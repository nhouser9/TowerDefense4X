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

import java.awt.Point;
import java.util.LinkedList;
import units.Enemies.Enemy;
import units.Enemies.Hive;
import units.Towers.Tower;

/**
 * Class which exposes an API for searching the board for units.
 *
 * @author Nick Houser
 */
public class BoardSearch {

    //the BoardState to query for searches
    private BoardState boardState;

    /**
     * Constructor which saves a link to a BoardState for later queries.
     *
     * @param state
     */
    public BoardSearch(BoardState state) {
        boardState = state;
    }

    /**
     * Converts the passed co-ordinates to grid indices before delegating the
     * search to the towerAtGridPositionMethod to find the tower at the passed
     * position. This is used to ensure that Enemies cannot move freely into
     * squares where the player has placed Towers.
     *
     * @param position a Point object representing the position to check
     * @return the Tower at the passed position, or null if none exists
     * @throws ArrayIndexOutOfBoundsException if passed a position not on the
     * board
     */
    public Tower towerAtPosition(Point position) throws ArrayIndexOutOfBoundsException {
        int gridX = Math.floorDiv(position.x, GameBoardPanel.SQUARE_SIZE);
        int gridY = Math.floorDiv(position.y, GameBoardPanel.SQUARE_SIZE);
        return towerAtGridPosition(new Point(gridX, gridY));
    }

    /**
     * Checks whether a given position on the grid imposed on the board is
     * occupied by a Tower. This is used to ensure that Enemies cannot move
     * freely into squares where the player has placed Towers.
     *
     * @param position a Point object representing the grid position to check
     * @return the Tower at the passed position, or null if none exists
     * @throws ArrayIndexOutOfBoundsException if passed a position not on the
     * board
     */
    public Tower towerAtGridPosition(Point position) throws ArrayIndexOutOfBoundsException {
        return boardState.towers[position.x][position.y];
    }

    /**
     * Returns all Towers in the specified grid area.
     *
     * @param topLeft the top left corner of the grid rectangle to search
     * @param bottomRight the bottom left corner of the grid rectangle to search
     * @return a LinkedList containing all of the Towers in the specified area
     */
    public LinkedList<Tower> allTowersInArea(Point topLeft, Point bottomRight) {
        LinkedList<Tower> toReturn = new LinkedList<>();

        for (int xSearch = topLeft.x; xSearch <= bottomRight.x; xSearch++) {
            for (int ySearch = topLeft.y; ySearch <= bottomRight.y; ySearch++) {
                Tower towerAtPosition;

                try {
                    towerAtPosition = towerAtGridPosition(new Point(xSearch, ySearch));
                } catch (ArrayIndexOutOfBoundsException offScreen) {
                    continue;
                }

                if (towerAtPosition == null) {
                    continue;
                }

                toReturn.add(towerAtPosition);
            }
        }

        return toReturn;
    }

    /**
     * Method which returns a single Enemy from the specified area. Does not
     * find Hives, because they should not be target by Towers. Which enemy will
     * be returned if multiple are found is undefined. Returns null if no
     * enemies are found.
     *
     * @param topLeft the top left grid square of the search area
     * @param bottomRight the bottom right grid square of the search area
     * @return an Enemy within the specified area, or null if none are found
     */
    public Enemy firstEnemyInArea(Point topLeft, Point bottomRight) {
        for (Enemy enemy : boardState.enemies) {
            if (enemy instanceof Hive) {
                continue;
            }

            int gridX = enemy.getGridPosition().x;
            int gridY = enemy.getGridPosition().y;
            if (gridX >= topLeft.x && gridX <= bottomRight.x && gridY >= topLeft.y && gridY <= bottomRight.y) {
                return enemy;
            }
        }
        return null;
    }

    /**
     * Method which returns all Enemies.
     *
     * @return a LinkedList of all Enemies within the specified area
     */
    public LinkedList<Enemy> allEnemies() {
        return boardState.enemies;
    }

    /**
     * Method which returns all Enemies from the specified area. Does not find
     * Hives, because they should not be target by Towers.
     *
     * @param topLeft the top left grid square of the search area
     * @param bottomRight the bottom right grid square of the search area
     * @return a LinkedList of all Enemies within the specified area
     */
    public LinkedList<Enemy> allEnemiesInArea(Point topLeft, Point bottomRight) {
        LinkedList<Enemy> toReturn = new LinkedList<>();
        for (Enemy enemy : boardState.enemies) {
            if (enemy instanceof Hive) {
                continue;
            }

            int gridX = enemy.getGridPosition().x;
            int gridY = enemy.getGridPosition().y;
            if (gridX >= topLeft.x && gridX <= bottomRight.x && gridY >= topLeft.y && gridY <= bottomRight.y) {
                toReturn.add(enemy);
            }
        }
        return toReturn;
    }
}
