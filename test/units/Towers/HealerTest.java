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

import gui.BoardSearch;
import gui.GameBoardPanel;
import java.util.LinkedList;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the Healer class.
 * 
 * @author Nick Houser
 */
public class HealerTest {

    /**
     * Test of poweredTick method, of class Healer.
     */
    @Test
    public void PoweredTick_ShouldHealANearbyUnit_IfItIsDamaged() {
        BoardSearch fakeSearch = mock(BoardSearch.class);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        Tower fakeDamaged = mock(Tower.class);
        
        when(fakeBoard.search()).thenReturn(fakeSearch);
        LinkedList<Tower> searchResults = new LinkedList<>();
        searchResults.add(fakeDamaged);
        when(fakeSearch.allTowersInArea(any(), any())).thenReturn(searchResults);
        when(fakeDamaged.isDamaged()).thenReturn(true);
        
        Healer testHealer = new Healer(0, 0, 1);
        testHealer.poweredTick(fakeBoard);
        
        verify(fakeDamaged).changeHealth(1);
    }    
    
    /**
     * Test of poweredTick method, of class Healer.
     */
    @Test
    public void PoweredTick_ShouldNotHealAUnit_IfItIsNotDamaged() {
        BoardSearch fakeSearch = mock(BoardSearch.class);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        Tower fakeDamaged = mock(Tower.class);
        
        when(fakeBoard.search()).thenReturn(fakeSearch);
        LinkedList<Tower> searchResults = new LinkedList<>();
        searchResults.add(fakeDamaged);
        when(fakeSearch.allTowersInArea(any(), any())).thenReturn(searchResults);
        when(fakeDamaged.isDamaged()).thenReturn(false);
        
        Healer testHealer = new Healer(0, 0, 1);
        testHealer.poweredTick(fakeBoard);
        
        verify(fakeDamaged, never()).changeHealth(1);
    } 

    /**
     * Test of poweredTick method, of class Healer.
     */
    @Test
    public void PoweredTick_ShouldNotHealMultipleUnits() {
        BoardSearch fakeSearch = mock(BoardSearch.class);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        Tower fakeDamaged = mock(Tower.class);
        Tower fakeDamaged2 = mock(Tower.class);
        
        when(fakeBoard.search()).thenReturn(fakeSearch);
        LinkedList<Tower> searchResults = new LinkedList<>();
        searchResults.add(fakeDamaged);
        searchResults.add(fakeDamaged2);
        when(fakeSearch.allTowersInArea(any(), any())).thenReturn(searchResults);
        when(fakeDamaged.isDamaged()).thenReturn(true);
        when(fakeDamaged2.isDamaged()).thenReturn(true);
        
        Healer testHealer = new Healer(0, 0, 1);
        testHealer.poweredTick(fakeBoard);
        
        verify(fakeDamaged).changeHealth(1);
        verify(fakeDamaged2, never()).changeHealth(1);
    } 

    /**
     * Test of poweredTick method, of class Healer.
     */
    @Test
    public void PoweredTick_ShouldNotHealSelf() {
        BoardSearch fakeSearch = mock(BoardSearch.class);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        Healer testHealer = spy(new Healer(0, 0, 1));
        
        when(fakeBoard.search()).thenReturn(fakeSearch);
        LinkedList<Tower> searchResults = new LinkedList<>();
        searchResults.add(testHealer);
        when(fakeSearch.allTowersInArea(any(), any())).thenReturn(searchResults);
        
        testHealer.poweredTick(fakeBoard);
        
        verify(testHealer, never()).changeHealth(1);
    } 

    /**
     * Test of poweredTick method, of class Healer.
     */
    @Test
    public void PoweredTick_ShouldNotHealTerrain() {
        BoardSearch fakeSearch = mock(BoardSearch.class);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        Tower fakeDamaged = mock(Terrain.class);
        
        when(fakeBoard.search()).thenReturn(fakeSearch);
        LinkedList<Tower> searchResults = new LinkedList<>();
        searchResults.add(fakeDamaged);
        when(fakeSearch.allTowersInArea(any(), any())).thenReturn(searchResults);
        when(fakeDamaged.isDamaged()).thenReturn(true);
        
        Healer testHealer = new Healer(0, 0, 1);
        testHealer.poweredTick(fakeBoard);
        
        verify(fakeDamaged, never()).changeHealth(1);
    }    
}
