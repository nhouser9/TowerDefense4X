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
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mockito.Mockito;
import units.Burrower;
import units.Enemy;
import units.Terrain;
import units.Tower;
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
        Mockito.when(fakeUnit1.getPosition()).thenReturn(new Point(200, 200));
        Unit fakeUnit2 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit2.getPosition()).thenReturn(new Point(400, 400));

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
        Mockito.when(fakeUnit1.getPosition()).thenReturn(new Point(200, 200));
        Unit fakeUnit2 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit2.getPosition()).thenReturn(new Point(400, 400));
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
}
