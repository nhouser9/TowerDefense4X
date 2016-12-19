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

import java.awt.Graphics;
import java.awt.Point;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mockito.Mockito;
import units.Enemies.Burrower;
import units.Enemies.Enemy;
import units.Towers.Blocker;
import units.Towers.Terrain;
import units.Towers.Tower;
import units.Unit;

/**
 * Unit tests for the GameBoardPanel class.
 *
 * @author Nick Houser
 */
public class GameBoardPanelTest {

    /**
     * Test of tick method, of class GameBoardPanel.
     */
    @Test
    public void Tick_ShouldTickAllTowers_AfterTowersPassedToAddUnit() {
        Unit fakeUnit1 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit1.getGridPosition()).thenReturn(new Point(1, 1));
        Unit fakeUnit2 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit2.getGridPosition()).thenReturn(new Point(2, 2));

        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(fakeUnit1);
        board.addUnit(fakeUnit2);

        board.tick();
        Mockito.verify(fakeUnit1).tick(board);
        Mockito.verify(fakeUnit2).tick(board);
    }

    /**
     * Test of paintComponent method, of class GameBoardPanel.
     */
    @Test
    public void PaintComponent_ShouldDrawAllTowers_AfterTowersPassedToAddUnit() {
        Unit fakeUnit1 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit1.getGridPosition()).thenReturn(new Point(3, 2));
        Unit fakeUnit2 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit2.getGridPosition()).thenReturn(new Point(5, 4));
        Graphics fakeGraphics = Mockito.mock(Graphics.class);

        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(fakeUnit1);
        board.addUnit(fakeUnit2);

        board.paintComponent(fakeGraphics);
        Mockito.verify(fakeUnit1).drawSelf(fakeGraphics);
        Mockito.verify(fakeUnit2).drawSelf(fakeGraphics);
    }

    /**
     * Test of tick method, of class GameBoardPanel.
     */
    @Test
    public void Tick_ShouldTickAllEnemies_AfterEnemiesPassedToAddUnit() {
        Unit fakeUnit1 = Mockito.mock(Enemy.class);
        Unit fakeUnit2 = Mockito.mock(Enemy.class);

        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(fakeUnit1);
        board.addUnit(fakeUnit2);

        board.tick();
        Mockito.verify(fakeUnit1).tick(board);
        Mockito.verify(fakeUnit2).tick(board);
    }

    /**
     * Test of paintComponent method, of class GameBoardPanel.
     */
    @Test
    public void PaintComponent_ShouldDrawAllEnemies_AfterEnemiesPassedToAddUnit() {
        Unit fakeUnit1 = Mockito.mock(Enemy.class);
        Unit fakeUnit2 = Mockito.mock(Enemy.class);
        Graphics fakeGraphics = Mockito.mock(Graphics.class);

        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(fakeUnit1);
        board.addUnit(fakeUnit2);

        board.paintComponent(fakeGraphics);
        Mockito.verify(fakeUnit1).drawSelf(fakeGraphics);
        Mockito.verify(fakeUnit2).drawSelf(fakeGraphics);
    }

    /**
     * Test of towerAtPosition method, of class GameBoardPanel.
     */
    @Test
    public void PositionOccupied_ShouldReturnATower_WhenATowerAddedAtThatPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(new Terrain(100, 100));
        if (board.towerAtPosition(new Point(100, 100)) == null) {
            fail("Expected positionOccupied to return true after adding a Tower.");
        }
    }

    /**
     * Test of towerAtPosition method, of class GameBoardPanel.
     */
    @Test
    public void PositionOccupied_ShouldReturnATower_WhenATowerAddedAtAPositionWithinTheSameGridArea() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(new Terrain(100, 100));
        if (board.towerAtPosition(new Point(105, 109)) == null) {
            fail("Expected positionOccupied to return true after adding a Tower.");
        }
    }

    /**
     * Test of towerAtPosition method, of class GameBoardPanel.
     */
    @Test
    public void PositionOccupied_ShouldReturnNull_WhenNoTowerAddedAtThatPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        if (board.towerAtPosition(new Point(100, 100)) != null) {
            fail("Expected positionOccupied to return false after not adding a Tower.");
        }
    }

    /**
     * Test of towerAtPosition method, of class GameBoardPanel.
     */
    @Test
    public void PositionOccupied_ShouldReturnNull_WhenAUnitAddedAtThatPosition() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));
        board.addUnit(new Burrower(100, 100, new Point(1, 1)));
        if (board.towerAtPosition(new Point(100, 100)) != null) {
            fail("Expected positionOccupied to return false after adding an Enemy.");
        }
    }

    /**
     * Test of tick method, of class GameBoardPanel.
     */
    @Test
    public void Tick_ShouldRemoveDeadEnemies() {
        BoardState testBoardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Enemy toRemove = new Burrower(0, 0, new Point(1, 1));
        board.addUnit(toRemove);
        toRemove.destroy();
        board.tick();
        if (testBoardState.enemies.contains(toRemove)) {
            fail("The unit was not removed after taking more than its max health in damage.");
        }
    }

    /**
     * Test of tick method, of class GameBoardPanel.
     */
    @Test
    public void Tick_ShouldRemoveDeadTowers() {
        BoardState testBoardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Tower toRemove = new Terrain(0, 0);
        board.addUnit(toRemove);
        toRemove.destroy();
        board.tick();
        for (int x = 0; x < GameBoardPanel.NUM_SQUARES; x++) {
            for (int y = 0; y < GameBoardPanel.NUM_SQUARES; y++) {
                if (testBoardState.towers[x][y] != null) {
                    fail("The unit was not removed after taking more than its max health in damage.");
                }
            }
        }
    }

    /**
     * Test of towerAtGridPosition method, of class GameBoardPanel.
     */
    @Test
    public void TowerAtGridPosition_ShouldNotThrow_WhenPassedInvalidIndeces() {
        BoardState testBoardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        try {
            board.towerAtGridPosition(new Point(-1, -1));
        } catch (ArrayIndexOutOfBoundsException exception) {
            fail("Exception was expected to be handled but was thrown from method towerAtGridPosition().");
        }
    }

    /**
     * Test of addUnit method, of class GameBoardPanel.
     */
    @Test
    public void AddUnit_ShouldNotAddATower_IfAnEnemyExistsAtThatPosition() {
        BoardState testBoardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Enemy testEnemy = new Burrower(0, 0, new Point(0, 0));
        Tower testTower = new Blocker(0, 0);
        
        board.addUnit(testEnemy);
        board.addUnit(testTower);
        
        Assert.assertEquals(board.towerAtGridPosition(new Point(0,0)), null);
    }
}
