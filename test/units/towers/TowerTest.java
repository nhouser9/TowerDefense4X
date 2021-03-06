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
package units.towers;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Tower class.
 *
 * @author Nick Houser
 */
public class TowerTest {

    /**
     * Test of Constructor method, of class Tower.
     */
    @Test
    public void Constructor_ShouldRoundPosition_WhenArgsNotDivisibleBySquareSize() {
        int fakeSquareSize = 6;
        int xGridAlignment = fakeSquareSize * 2;
        int yGridAlignment = fakeSquareSize * 4;
        Terrain test = new Terrain(xGridAlignment + 2, yGridAlignment + fakeSquareSize - 2, fakeSquareSize);
        assertEquals(test.getPosition().x, xGridAlignment);
        assertEquals(test.getPosition().y, yGridAlignment);
    }
}
