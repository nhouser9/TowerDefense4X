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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import units.DirectionVector;
import units.Towers.Terrain;
import units.Towers.Tower;

/**
 * Unit tests for the Burrower class.
 *
 * @author Nick Houser
 */
public class BurrowerTest {

    /**
     * Test of tick method, of class Burrower.
     */
    @Test
    public void Tick_ShouldCallMoveMethod() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        BoardSearch fakeSearch = mock(BoardSearch.class);
        when(fakeBoard.search()).thenReturn(fakeSearch);
        when(fakeSearch.towerAtPosition(any())).thenReturn(new Terrain(0, 0));

        Burrower testBurrower = spy(new Burrower(0, 0, new Point(1, 1)));

        testBurrower.tick(fakeBoard);

        Mockito.verify(testBurrower).move(any(GameBoardPanel.class));
    }

    /**
     * Test of tick method, of class Burrower.
     */
    @Test
    public void Tick_ShouldAttack_WhenMoveFails() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        BoardSearch fakeSearch = mock(BoardSearch.class);
        when(fakeBoard.search()).thenReturn(fakeSearch);
        Tower fakeBlocker = mock(Terrain.class);
        when(fakeSearch.towerAtPosition(any())).thenReturn(fakeBlocker);

        Burrower testBurrower = new Burrower(5, 5, new Point(1, 1));

        testBurrower.tick(fakeBoard);

        Mockito.verify(fakeBlocker).changeHealth(any(int.class));
    }

    /**
     * Test of tick method, of class Burrower.
     */
    @Test
    public void Tick_ShouldMoveTowardsThePassedPoint() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        BoardSearch fakeSearch = mock(BoardSearch.class);
        when(fakeBoard.search()).thenReturn(fakeSearch);
        when(fakeSearch.towerAtPosition(any())).thenReturn(null);

        int initialX = 1;
        int initialY = 1;
        int speed = 4;
        Point source = new Point(initialX, initialY);
        Point target = new Point(1000, 300);
        DirectionVector toTargetBeforeTick = new DirectionVector(source, target, speed);

        Burrower testBurrower = new Burrower(initialX, initialY, target);
        for (int ticks = 0; ticks < 100; ticks++) {
            testBurrower.tick(fakeBoard);
        }
        DirectionVector toTargetAfterTick = new DirectionVector(testBurrower.getPosition(), target, speed);

        assertEquals(toTargetAfterTick.xDirection, toTargetBeforeTick.xDirection, .1);
        assertEquals(toTargetAfterTick.yDirection, toTargetBeforeTick.yDirection, .1);
    }

    /**
     * Test of tick method, of class Burrower.
     */
    @Test
    public void Tick_ShouldMarkTheBurrowerForDeath_WhenItDestroysATower() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        BoardSearch fakeSearch = mock(BoardSearch.class);
        when(fakeBoard.search()).thenReturn(fakeSearch);
        Tower fakeBlocker = mock(Terrain.class);
        when(fakeBlocker.isDead()).thenReturn(true);
        when(fakeSearch.towerAtPosition(any())).thenReturn(fakeBlocker);

        Burrower testBurrower = new Burrower(5, 5, new Point(1, 1));

        testBurrower.tick(fakeBoard);

        if (!testBurrower.isDead()) {
            fail("The Burrower did not die after destroying a Tower.");
        }
    }
}
