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
import gui.BoardState.InitialState;
import gui.GameBoardPanel;
import org.junit.Test;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the Generator class.
 *
 * @author Nick Houser
 */
public class GeneratorTest {

    /**
     * Test of poweredTick method, of class Generator.
     */
    @Test
    public void PoweredTick_PowersNearbyUnpoweredUnits() {
        Generator testGenerator = new Generator(0, 0);
        Powered testPowered = spy(new Shooter(GameBoardPanel.SQUARE_SIZE, 0));
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));

        testGenerator.power(new Generator(0, 0));
        board.addUnit(testGenerator);
        board.addUnit(testPowered);

        testGenerator.poweredTick(board);

        testPowered.tick(board);
        verify(testPowered).poweredTick(board);
    }

    /**
     * Test of poweredTick method, of class Generator.
     */
    @Test
    public void PoweredTick_ShouldNotPowerSelf() {
        Generator testGenerator = spy(new Generator(0, 0));
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));

        testGenerator.power(new Generator(0, 0));
        board.addUnit(testGenerator);

        testGenerator.poweredTick(board);

        verify(testGenerator, never()).power(testGenerator);
    }

    /**
     * Test of poweredTick method, of class Generator.
     */
    @Test
    public void PoweredTick_ShouldCallUnpower_WhenGeneratorIsDead() {
        Generator testGenerator = spy(new Generator(0, 0));
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));

        testGenerator.power(new Generator(0, 0));
        board.addUnit(testGenerator);

        testGenerator.destroy();
        testGenerator.poweredTick(board);

        verify(testGenerator).unPower();
    }

    /**
     * Test of unPower method, of class Generator.
     */
    @Test
    public void UnPower_ShouldAlsoUnpowerUnitsPoweredByThisUnit() {
        Generator testGenerator = new Generator(0, 0);
        Powered testPowered = spy(new Shooter(GameBoardPanel.SQUARE_SIZE, 0));
        GameBoardPanel board = new GameBoardPanel(new BoardState(InitialState.EMPTY));

        testGenerator.power(new Generator(0, 0));
        board.addUnit(testGenerator);
        board.addUnit(testPowered);

        testGenerator.poweredTick(board);
        testGenerator.unPower();

        testPowered.tick(board);
        verify(testPowered, never()).poweredTick(board);
    }
}
