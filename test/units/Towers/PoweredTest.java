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
import org.junit.Test;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the Powered class.
 *
 * @author Nick Houser
 */
public class PoweredTest {

    /**
     * Test of tick method, of class Powered.
     */
    @Test
    public void Tick_ShouldNotCallPoweredTick_Initially() {
        BoardState boardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(boardState);

        Powered testConsumer = spy(new Shooter(0, 0));
        board.addUnit(testConsumer);

        testConsumer.tick(board);

        verify(testConsumer, never()).poweredTick(board);
    }

    /**
     * Test of tick method, of class Powered.
     */
    @Test
    public void Tick_ShouldNotCallPoweredTick_WhenPoweredThenUnPowered() {
        BoardState boardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(boardState);

        Powered testConsumer = spy(new Shooter(0, 0));
        board.addUnit(testConsumer);
        testConsumer.power(new Generator(0, 0));
        testConsumer.unPower();

        testConsumer.tick(board);

        verify(testConsumer, never()).poweredTick(board);
    }

    /**
     * Test of tick method, of class Powered.
     */
    @Test
    public void Tick_ShouldCallPoweredTick_WhenPowered() {
        BoardState boardState = new BoardState(BoardState.InitialState.EMPTY);
        GameBoardPanel board = new GameBoardPanel(boardState);

        Powered testConsumer = spy(new Shooter(0, 0));
        board.addUnit(testConsumer);
        testConsumer.power(new Generator(0, 0));

        testConsumer.tick(board);

        verify(testConsumer).poweredTick(board);
    }
}
