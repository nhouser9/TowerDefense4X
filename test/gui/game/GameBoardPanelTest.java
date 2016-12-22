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
import java.awt.Graphics;
import java.awt.Point;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import units.enemies.Burrower;
import units.enemies.Enemy;
import units.towers.Blocker;
import units.towers.Generator;
import units.towers.Terrain;
import units.towers.Tower;
import units.Unit;
import static org.mockito.Mockito.mock;

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
        Tower fakeUnit1 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit1.getGridPosition()).thenReturn(new Point(1, 1));
        Tower fakeUnit2 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit2.getGridPosition()).thenReturn(new Point(2, 2));

        GameBoardPanel board = new GameBoardPanel(new BoardState(10, 10));
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
        Tower fakeUnit1 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit1.getGridPosition()).thenReturn(new Point(3, 2));
        Tower fakeUnit2 = Mockito.mock(Tower.class);
        Mockito.when(fakeUnit2.getGridPosition()).thenReturn(new Point(5, 4));
        Graphics fakeGraphics = Mockito.mock(Graphics.class);

        GameBoardPanel board = new GameBoardPanel(new BoardState(10, 10));
        board.addUnit(fakeUnit1);
        board.addUnit(fakeUnit2);

        board.paintComponent(fakeGraphics);
        Mockito.verify(fakeUnit1).drawSelf(fakeGraphics);
        Mockito.verify(fakeUnit2).drawSelf(fakeGraphics);
    }

    /**
     * Test of paintComponent method, of class GameBoardPanel.
     */
    @Test
    public void PaintComponent_ShouldDrawLayeredGraphics_AfterLayeredGraphicsUnitAdded() {
        Generator layeredGraphicsTower = mock(Generator.class);
        when(layeredGraphicsTower.getGridPosition()).thenReturn(new Point(0, 0));
        GameBoardPanel board = new GameBoardPanel(new BoardState(10, 10));
        board.addUnit(layeredGraphicsTower);

        Graphics fakeGraphics = Mockito.mock(Graphics.class);
        board.paintComponent(fakeGraphics);
        Mockito.verify(layeredGraphicsTower).drawLayer(fakeGraphics);
    }

    /**
     * Test of tick method, of class GameBoardPanel.
     */
    @Test
    public void Tick_ShouldTickAllEnemies_AfterEnemiesPassedToAddUnit() {
        Unit fakeUnit1 = Mockito.mock(Enemy.class);
        Unit fakeUnit2 = Mockito.mock(Enemy.class);

        GameBoardPanel board = new GameBoardPanel(new BoardState(10, 10));
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

        GameBoardPanel board = new GameBoardPanel(new BoardState(10, 10));
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
    public void Tick_ShouldRemoveDeadEnemies() {
        BoardState testBoardState = new BoardState(10, 10);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Enemy toRemove = new Burrower(0, 0, new Point(1, 1), board.getSquareSize());
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
        BoardState testBoardState = new BoardState(10, 10);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Tower toRemove = new Terrain(0, 0, board.getSquareSize());
        board.addUnit(toRemove);
        toRemove.destroy();
        board.tick();
        for (int x = 0; x < testBoardState.numSquares; x++) {
            for (int y = 0; y < testBoardState.numSquares; y++) {
                if (testBoardState.towers[x][y] != null) {
                    fail("The unit was not removed after taking more than its max health in damage.");
                }
            }
        }
    }

    /**
     * Test of addUnit method, of class GameBoardPanel.
     */
    @Test
    public void AddUnit_ShouldNotAddATower_IfAnEnemyExistsAtThatPosition() {
        BoardState testBoardState = new BoardState(10, 10);
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
     * Test of addUnit method, of class GameBoardPanel.
     */
    @Test
    public void AddUnit_ShouldNotAddATower_IfATowerExistsAtThatPosition() {
        BoardState testBoardState = new BoardState(10, 10);
        GameBoardPanel board = new GameBoardPanel(testBoardState);
        Tower testTower = new Blocker(0, 0, board.getSquareSize());
        Tower testTower2 = new Blocker(0, 0, board.getSquareSize());

        board.addUnit(testTower);
        board.addUnit(testTower2);

        try {
            Assert.assertEquals(board.search().towerAtGridPosition(new Point(0, 0)), testTower);
        } catch (OffscreenException offscreen) {
            fail("Did not expect offscreen exception.");
        }
    }
}
