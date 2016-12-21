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

import gui.BoardSearch;
import gui.GameBoardPanel;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import units.Towers.Terrain;

/**
 * Unit tests for the Queen class.
 *
 * @author Nick Houser
 */
public class QueenTest {

    /**
     * Test of tick method, of class Burrower.
     */
    @Test
    public void Tick_ShouldCallMoveMethod() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        BoardSearch fakeSearch = mock(BoardSearch.class);
        when(fakeBoard.search()).thenReturn(fakeSearch);
        when(fakeSearch.towerAtPosition(any())).thenReturn(new Terrain(0, 0));

        Queen testQueen = spy(new Queen(0, 0, new Point(1, 1)));

        testQueen.tick(fakeBoard);

        Mockito.verify(testQueen).move(any(GameBoardPanel.class));
    }

    /**
     * Test of tick method, of class Queen.
     */
    @Test
    public void Tick_ShouldSpawnAHiveAndDie_AfterTheCooldownHasElapsed() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        BoardSearch fakeSearch = mock(BoardSearch.class);
        when(fakeBoard.search()).thenReturn(fakeSearch);
        when(fakeSearch.towerAtPosition(any())).thenReturn(new Terrain(0, 0));

        Queen testQueen = spy(new Queen(0, 0, new Point(1, 1)));

        for (int tick = 0; tick < Queen.HIVE_DELAY - 1; tick++) {
            testQueen.tick(fakeBoard);
        }

        Mockito.verify(fakeBoard, never()).addUnit(any(Hive.class));

        testQueen.tick(fakeBoard);
        Mockito.verify(fakeBoard).addUnit(any(Hive.class));
        assertEquals(true, testQueen.isDead());
    }
}
