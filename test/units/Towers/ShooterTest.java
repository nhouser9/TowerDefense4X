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

import gui.BoardState;
import gui.GameBoardPanel;
import java.awt.Point;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import units.Enemies.Burrower;
import units.Enemies.Enemy;
import units.Enemies.Hive;

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
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));

        Shooter instance = new Shooter(0, 0);
        board.addUnit(instance);

        Enemy target = mock(Burrower.class);
        when(target.getGridPosition()).thenReturn(new Point(1, 1));
        board.addUnit(target);

        instance.poweredTick(board);
        verify(target).changeHealth(any(int.class));
    }

    /**
     * Test of poweredTick method, of class Shooter.
     */
    @Test
    public void PoweredTick_DoesNotDamageAnEnemy_WhenNoneAreInRange() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));

        Shooter instance = new Shooter(0, 0);
        board.addUnit(instance);

        Enemy target = mock(Burrower.class);
        when(target.getGridPosition()).thenReturn(new Point(GameBoardPanel.NUM_SQUARES - 1, 1));
        board.addUnit(target);

        instance.poweredTick(board);
        verify(target, times(0)).changeHealth(any(int.class));
    }

    /**
     * Test of poweredTick method, of class Shooter.
     */
    @Test
    public void PoweredTick_WaitsBeforeDamagaingAnEnemyAgain_AfterDamagingAnEnemy() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));

        Shooter instance = new Shooter(0, 0);
        board.addUnit(instance);

        Enemy target = mock(Burrower.class);
        when(target.getGridPosition()).thenReturn(new Point(1, 1));
        board.addUnit(target);

        instance.poweredTick(board);
        instance.poweredTick(board);
        verify(target, times(1)).changeHealth(any(int.class));
    }

    /**
     * Test of poweredTick method, of class Shooter.
     */
    @Test
    public void PoweredTick_WillNotFireOnHives() {
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.EMPTY));

        Shooter instance = new Shooter(0, 0);
        board.addUnit(instance);

        Enemy target = mock(Hive.class);
        when(target.getGridPosition()).thenReturn(new Point(2, 1));
        board.addUnit(target);

        instance.poweredTick(board);
        verify(target, times(0)).changeHealth(any(int.class));
    }
}
