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
package gui.game;

import gui.game.levels.BoardState;
import java.awt.Point;
import java.util.LinkedList;
import units.enemies.Enemy;
import units.enemies.Hive;
import units.towers.Tower;

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
     * @throws OffscreenException if passed a position not on the board
     */
    public Tower towerAtPosition(Point position) throws OffscreenException {
        int gridX = Math.floorDiv(position.x, boardState.squareSize);
        int gridY = Math.floorDiv(position.y, boardState.squareSize);
        return towerAtGridPosition(new Point(gridX, gridY));
    }

    /**
     * Checks whether a given position on the grid imposed on the board is
     * occupied by a Tower. This is used to ensure that Enemies cannot move
     * freely into squares where the player has placed Towers.
     *
     * @param position a Point object representing the grid position to check
     * @return the Tower at the passed position, or null if none exists
     * @throws OffscreenException if passed a position not on the board
     */
    public Tower towerAtGridPosition(Point position) throws OffscreenException {
        try {
            return boardState.towers[position.x][position.y];
        } catch (ArrayIndexOutOfBoundsException exception) {
            boolean left = false;
            boolean right = false;
            boolean top = false;
            boolean bottom = false;

            if (position.x < 0) {
                left = true;
            } else if (position.x >= boardState.numSquares) {
                right = true;
            }

            if (position.y < 0) {
                top = true;
            } else if (position.y >= boardState.numSquares) {
                bottom = true;
            }

            throw new OffscreenException(left, right, top, bottom);
        }
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
                } catch (OffscreenException offScreen) {
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

            int gridX = Math.floorDiv(enemy.getPosition().x, boardState.squareSize);
            int gridY = Math.floorDiv(enemy.getPosition().y, boardState.squareSize);
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
     * Helper method which converts an absolute position to a grid position.
     *
     * @param absolute the absolute position
     * @return the equivalent grid position
     */
    public Point absoluteToGridPosition(Point absolute) {
        int gridX = Math.floorDiv(absolute.x, boardState.squareSize);
        int gridY = Math.floorDiv(absolute.y, boardState.squareSize);
        return new Point(gridX, gridY);
    }
}
