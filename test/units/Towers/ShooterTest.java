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
package units.Towers;

import gui.Game.BoardSearch;
import gui.Game.GameBoardPanel;
import org.junit.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import units.Enemies.Burrower;
import units.Enemies.Enemy;
import units.Enemies.Hive;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the Shooter class.
 *
 * @author Nick Houser
 */
public class ShooterTest {

    /**
     * Test of poweredTick method, of class Shooter.
     */
    @Test
    public void PoweredTick_DamagesANearbyEnemy_WhenOneExists() {
        GameBoardPanel board = mock(GameBoardPanel.class);
        BoardSearch search = mock(BoardSearch.class);
        when(board.search()).thenReturn(search);
        Enemy target = mock(Burrower.class);
        when(search.firstEnemyInArea(any(), any())).thenReturn(target);

        Shooter instance = new Shooter(0, 0, 1);

        instance.poweredTick(board);
        verify(target).changeHealth(any(int.class));
    }

    /**
     * Test of poweredTick method, of class Shooter.
     */
    @Test
    public void PoweredTick_WaitsBeforeDamagaingAnEnemyAgain_AfterDamagingAnEnemy() {
        GameBoardPanel board = mock(GameBoardPanel.class);
        BoardSearch search = mock(BoardSearch.class);
        when(board.search()).thenReturn(search);
        Enemy target = mock(Burrower.class);
        when(search.firstEnemyInArea(any(), any())).thenReturn(target);

        Shooter instance = new Shooter(0, 0, 1);

        instance.poweredTick(board);
        instance.poweredTick(board);
        verify(target, times(1)).changeHealth(any(int.class));
    }
}
