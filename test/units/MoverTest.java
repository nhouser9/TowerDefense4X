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
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the Enemy class.
 * 
 * @author Nick Houser
 */
public class MoverTest {
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldSucceed_WhenTargetIsNotOccupied() {
        int initialX = 10;
        int initialY = 200;
        Point target = new Point(initialX + 1, initialY + 3);
        
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(null);
                
        Mover mover = new Burrower(initialX, initialY, target);
        DirectionVector moveDirection = new DirectionVector(mover.getPosition(), target, mover.getSpeed());
        Tower success = mover.move(fakeBoard);
        
        assertEquals(mover.position.x, initialX + moveDirection.xDirection, .01);
        assertEquals(mover.position.y, initialY + moveDirection.yDirection, .01);
        assertEquals(success, null);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldFail_WhenTargetIsOccupied() {
        int initialX = 10;
        int initialY = 200;
        int directionX = 1;
        int directionY = 2;
        
        Terrain blocker = new Terrain(0, 0);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(blocker);
                
        Mover mover = new Burrower(initialX, initialY, new Point(initialX + directionX, initialY + directionY));
        Tower success = mover.move(fakeBoard);
        
        assertEquals(mover.position.x, initialX, .01);
        assertEquals(mover.position.y, initialY, .01);
        assertEquals(success, blocker);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldFail_WhenHitboxTouchesOccupiedArea() {
        int initialX = 5;
        int initialY = 5;
        
        Mover mover = new Burrower(initialX, initialY, new Point(initialX, initialY + 1));
        
        Terrain blocker = new Terrain(0, 0);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(new Point(initialX, initialY + mover.getSpeed()))).thenReturn(null);
        when(fakeBoard.towerAtPosition(new Point(initialX, initialY + mover.getSpeed() + mover.getSize()))).thenReturn(blocker);
                
        Tower success = mover.move(fakeBoard);
        
        assertEquals(mover.position.x, initialX, .01);
        assertEquals(mover.position.y, initialY, .01);
        assertEquals(success, blocker);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldFail_WhenMovingOffTheBoard() {
        int initialX = 0;
        int initialY = 0;
        
        Mover mover = new Burrower(initialX, initialY, new Point(-1, -1));
        
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(null);
                
        Tower success = mover.move(fakeBoard);
        
        assertEquals(mover.position.x, initialX, .01);
        assertEquals(mover.position.y, initialY, .01);
        assertEquals(success, null);
    }
}
