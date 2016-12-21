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
import gui.InitialState;
import org.junit.Test;
import static org.junit.Assert.*;

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
        int fakeSquareSize = 5;
        int xGridAlignment = fakeSquareSize * 2;
        int yGridAlignment = fakeSquareSize * 4;
        Hive test = new Hive(xGridAlignment + 2, yGridAlignment + fakeSquareSize - 2, fakeSquareSize);
        assertEquals(test.getPosition().x, xGridAlignment);
        assertEquals(test.getPosition().y, yGridAlignment);
    }

    /**
     * Test of changeHealth method, of class Hive.
     */
    @Test
    public void ChangeHealth_ShouldNeverMarkAHiveForDeath() {
        Hive testHive = new Hive(0, 0, 1);
        testHive.changeHealth(0 - Integer.MAX_VALUE);
        assertEquals(false, testHive.isDead());
    }

    /**
     * Test of changeHealth method, of class Hive.
     */
    @Test
    public void Destroy_ShouldNeverMarkAHiveForDeath() {
        Hive testHive = new Hive(0, 0, 1);
        testHive.destroy();
        assertEquals(false, testHive.isDead());
    }

    /**
     * Test of tick method, of class Hive.
     */
    @Test
    public void Tick_ShouldSpawnABurrowerAtTheProperTimes() {
        Hive testHive = new Hive(0, 0, 1);
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));

        for (int ticks = 0; ticks < Hive.CADENCE_BURROWER - 1; ticks++) {
            testHive.tick(board);
        }
        for (Enemy enemy : board.search().allEnemies()) {
            if (enemy instanceof Burrower) {
                fail("Tick spawned a Burrower too soon.");
            }
        }

        testHive.tick(board);
        int burrowerCount = 0;
        for (Enemy enemy : board.search().allEnemies()) {
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
        Hive testHive = new Hive(0, 0, 1);
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));

        for (int ticks = 0; ticks < Hive.CADENCE_QUEEN - 1; ticks++) {
            testHive.tick(board);
        }
        for (Enemy enemy : board.search().allEnemies()) {
            if (enemy instanceof Queen) {
                fail("Tick spawned a Queen too soon.");
            }
        }

        testHive.tick(board);
        int queenCount = 0;
        for (Enemy enemy : board.search().allEnemies()) {
            if (enemy instanceof Queen) {
                queenCount = queenCount + 1;
            }
        }
        if (queenCount != 1) {
            fail("Tick did not spawn a Queen soon enough.");
        }
    }
}
