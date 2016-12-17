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
package units;

import gui.GameBoardPanel;
import java.awt.Point;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(new Terrain(0, 0));

        Burrower testBurrower = spy(new Burrower(0, 0));

        testBurrower.tick(fakeBoard);

        Mockito.verify(testBurrower).move(any(GameBoardPanel.class), any(DirectionVector.class));
    }
    
    /**
     * Test of tick method, of class Burrower.
     */
    @Test
    public void Tick_ShouldAttack_WhenMoveFails() {
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        Tower fakeBlocker = mock(Terrain.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(fakeBlocker);

        Burrower testBurrower = new Burrower(0, 0);

        testBurrower.tick(fakeBoard);

        Mockito.verify(fakeBlocker).changeHealth(any(int.class));
    }
}
