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
package gui.Game;

import java.awt.Point;
import java.util.Random;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import units.Enemies.Burrower;
import units.Enemies.Enemy;
import units.Enemies.Hive;
import units.Towers.Blocker;
import units.Towers.Terrain;
import units.Towers.Tower;

/**
 * Unit tests for the BoardSearch class.
 *
 * @author Nick Houser
 */
public class BoardSearchTest {

    /**
     * Test of towerAtPosition method, of class BoardSearch.
     */
    @Test
    public void TowerAtPosition_ShouldReturnATower_WhenATowerAddedAtThatPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));
        board.addUnit(new Terrain(100, 100, board.getSquareSize()));
        try {
            if (board.search().towerAtPosition(new Point(100, 100)) == null) {
                fail("Expected positionOccupied to return true after adding a Tower.");
            }
        } catch (OffscreenException offscreen) {
            fail("Did not expect offscreen exception.");
        }
    }

    /**
     * Test of towerAtPosition method, of class BoardSearch.
     */
    @Test
    public void TowerAtPosition_ShouldReturnATower_WhenATowerAddedAtAPositionWithinTheSameGridArea() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));
        board.addUnit(new Terrain(100, 100, board.getSquareSize()));
        try {
            if (board.search().towerAtPosition(new Point(105, 109)) == null) {
                fail("Expected positionOccupied to return true after adding a Tower.");
            }
        } catch (OffscreenException offscreen) {
            fail("Did not expect offscreen exception.");
        }
    }

    /**
     * Test of towerAtPosition method, of class BoardSearch.
     */
    @Test
    public void TowerAtPosition_ShouldReturnNull_WhenNoTowerAddedAtThatPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));
        try {
            if (board.search().towerAtPosition(new Point(100, 100)) != null) {
                fail("Expected positionOccupied to return false after not adding a Tower.");
            }
        } catch (OffscreenException offscreen) {
            fail("Did not expect offscreen exception.");
        }
    }

    /**
     * Test of towerAtPosition method, of class BoardSearch.
     */
    @Test
    public void TowerAtPosition_ShouldReturnNull_WhenAUnitAddedAtThatPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));
        board.addUnit(new Burrower(100, 100, new Point(1, 1), board.getSquareSize()));
        try {
            if (board.search().towerAtPosition(new Point(100, 100)) != null) {
                fail("Expected positionOccupied to return false after adding an Enemy.");
            }
        } catch (OffscreenException offscreen) {
            fail("Did not expect offscreen exception.");
        }
    }

    /**
     * Test of towerAtPosition method, of class BoardSearch.
     */
    @Test
    public void TowerAtPosition_ShouldThrow_WhenPassedOutOfBoundsPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));
        try {
            board.search().towerAtPosition(new Point(-100, -100));
            fail("Expected towerAtPosition to throw when passed an out of bounds position.");
        } catch (OffscreenException expected) {

        }
    }

    /**
     * Test of towerAtGridPosition method, of class BoardSearch.
     */
    @Test
    public void TowerAtGridPosition_ShouldThrow_WhenPassedInvalidIndeces() {
        BoardState testBoardState = new BoardState(InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        try {
            board.search().towerAtGridPosition(new Point(-1, -1));
            fail("Exception was expected to be handled but was thrown from method towerAtGridPosition().");
        } catch (OffscreenException exception) {
        }
    }

    /**
     * Test of addUnit method, of class BoardSearch.
     */
    @Test
    public void AddUnit_ShouldNotAddATower_IfAnEnemyExistsAtThatPosition() {
        BoardState testBoardState = new BoardState(InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Enemy testEnemy = new Burrower(0, 0, new Point(0, 0), board.getSquareSize());
        Tower testTower = new Blocker(0, 0, board.getSquareSize());

        board.addUnit(testEnemy);
        board.addUnit(testTower);

        try {
            Assert.assertEquals(board.search().towerAtGridPosition(new Point(0, 0)), null);
        } catch (OffscreenException offscreen) {
            fail("Did not expect offscreen exception.");
        }
    }

    /**
     * Test of firstEnemyInArea method, of class BoardSearch.
     */
    @Test
    public void FirstEnemyInArea_ShouldNotFindHives() {
        BoardState testBoardState = new BoardState(InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Enemy testEnemy = new Hive(0, 0, board.getSquareSize(), new Random());

        board.addUnit(testEnemy);

        Enemy result = board.search().firstEnemyInArea(new Point(0, 0), new Point(5, 5));
        Assert.assertEquals(result, null);
    }
}
