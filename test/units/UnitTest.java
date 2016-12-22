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

import units.enemies.Burrower;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import units.towers.Healer;

/**
 * Unit tests for the Unit class.
 *
 * @author Nick Houser
 */
public class UnitTest {

    /**
     * Test of isDead method, of class Unit.
     */
    @Test
    public void IsDead_ShouldReturnTrue_AfterSubtractingAUnitsInitialHealth() {
        Burrower testUnit = new Burrower(0, 0, new Point(1, 1), 4);
        testUnit.changeHealth(0 - testUnit.initialHealth());
        assertEquals(true, testUnit.isDead());
    }

    /**
     * Test of isDead method, of class Unit.
     */
    @Test
    public void IsDead_ShouldReturnTrue_AfterCallingDestroy() {
        Burrower testUnit = new Burrower(0, 0, new Point(1, 1), 1);
        testUnit.destroy();
        assertEquals(true, testUnit.isDead());
    }

    /**
     * Test of isDead method, of class Unit.
     */
    @Test
    public void IsDead_ShouldReturnFalse_Initially() {
        Burrower testUnit = new Burrower(0, 0, new Point(1, 1), 1);
        assertEquals(false, testUnit.isDead());
    }
    
    /**
     * Test of isDamaged method, of class Unit.
     */
    @Test
    public void IsDamaged_ShouldReturnFalse_Initially() {
        Healer testUnit = new Healer(0, 0, 1);
        assertEquals(false, testUnit.isDamaged());
    }
    
    /**
     * Test of isDamaged method, of class Unit.
     */
    @Test
    public void IsDamaged_ShouldReturnTrue_AfterTakingDamage() {
        Healer testUnit = new Healer(0, 0, 1);
        testUnit.changeHealth(-1);
        assertEquals(true, testUnit.isDamaged());
    }
    
    /**
     * Test of isDamaged method, of class Unit.
     */
    @Test
    public void IsDamaged_ShouldReturnFalse_AfterTakingDamageThenHealing() {
        Healer testUnit = new Healer(0, 0, 2);
        testUnit.changeHealth(-1);
        testUnit.changeHealth(1);
        assertEquals(false, testUnit.isDamaged());
    }
    
    /**
     * Test of changeHealth method, of class Unit.
     */
    @Test
    public void ChangeHealth_ShouldNotSetHealthAboveInitialHealth() {
        Unit testUnit = new Healer(0, 0, 3);
        testUnit.changeHealth(1);
        testUnit.changeHealth(-testUnit.initialHealth());
        assertEquals(true, testUnit.isDead());
    }
}
