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
package units.enemies;

import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Enemy class.
 *
 * @author Nick Houser
 */
public class EnemyTest {

    /**
     * Test of Constructor method, of class Enemy.
     */
    @Test
    public void Constructor_ShouldSetPositionEqualToPassedPosition() {
        int x = 3;
        int y = 200;
        Burrower testEnemy = new Burrower(x, y, new Point(1, 1), 1);
        assertEquals(x, testEnemy.getPosition().x);
        assertEquals(y, testEnemy.getPosition().y);
    }
}
