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
package units.Enemies;

import gui.BoardState;
import gui.GameBoardPanel;
import java.awt.Point;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import units.Unit;

/**
 * Unit tests for the Hive class.
 *
 * @author Nick Houser
 */
public class HiveTest {

    /**
     * Test of Constructor method, of class Hive.
     */
    @Test
    public void Constructor_ShouldRoundPosition_WhenArgsNotDivisibleBySquareSize() {
        int xGridAlignment = GameBoardPanel.SQUARE_SIZE * 2;
        int yGridAlignment = GameBoardPanel.SQUARE_SIZE * 4;
        Hive test = new Hive(xGridAlignment + 2, yGridAlignment + GameBoardPanel.SQUARE_SIZE - 2);
        assertEquals(test.getPosition().x, xGridAlignment);
        assertEquals(test.getPosition().y, yGridAlignment);
    }

    /**
     * Test of changeHealth method, of class Hive.
     */
    @Test
    public void ChangeHealth_ShouldNeverMarkAHiveForDeath() {
        Hive testHive = new Hive(0, 0);
        testHive.changeHealth(0 - Integer.MAX_VALUE);
        assertEquals(false, testHive.isDead());
    }

    /**
     * Test of changeHealth method, of class Hive.
     */
    @Test
    public void Destroy_ShouldNeverMarkAHiveForDeath() {
        Hive testHive = new Hive(0, 0);
        testHive.destroy();
        assertEquals(false, testHive.isDead());
    }

    /**
     * Test of tick method, of class Hive.
     */
    @Test
    public void Tick_ShouldSpawnABurrowerAtTheProperTimes() {
        Hive testHive = new Hive(0, 0);
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));

        for (int ticks = 0; ticks < Hive.CADENCE_BURROWER - 1; ticks++) {
            testHive.tick(board);
        }
        for (Enemy enemy : board.allEnemies()) {
            if (enemy instanceof Burrower) {
                fail("Tick spawned a Burrower too soon.");
            }
        }

        testHive.tick(board);
        int burrowerCount = 0;
        for (Enemy enemy : board.allEnemies()) {
            if (enemy instanceof Burrower) {
                burrowerCount = burrowerCount + 1;
            }
        }
        if (burrowerCount != 1) {
            fail("Tick did not spawn a Burrower soon enough.");
        }
    }

    /**
     * Test of tick method, of class Hive.
     */
    @Test
    public void Tick_ShouldSpawnAQueenAtTheProperTimes() {
        Hive testHive = new Hive(0, 0);
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));

        for (int ticks = 0; ticks < Hive.CADENCE_QUEEN - 1; ticks++) {
            testHive.tick(board);
        }
        for (Enemy enemy : board.allEnemies()) {
            if (enemy instanceof Queen) {
                fail("Tick spawned a Queen too soon.");
            }
        }

        testHive.tick(board);
        int queenCount = 0;
        for (Enemy enemy : board.allEnemies()) {
            if (enemy instanceof Queen) {
                queenCount = queenCount + 1;
            }
        }
        if (queenCount != 1) {
            fail("Tick did not spawn a Queen soon enough.");
        }
    }
}
