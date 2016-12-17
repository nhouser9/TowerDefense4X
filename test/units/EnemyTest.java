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
public class EnemyTest {    

    /**
     * Test of Constructor method, of class Enemy.
     */
    @Test
    public void Constructor_ShouldSetPositionEqualToPassedPosition() {
        int x = 3;
        int y = 200;
        Burrower testEnemy = new Burrower(x, y);
        assertEquals(x, testEnemy.getPosition().x);
        assertEquals(y, testEnemy.getPosition().y);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldSucceed_WhenTargetIsNotOccupied() {
        int initialX = 0;
        int initialY = 200;
        int directionX = 1;
        int directionY = 3;
        
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(null);
                
        Enemy mover = new Burrower(initialX, initialY);
        DirectionVector direction = new DirectionVector(directionX, directionY);
        Tower success = mover.move(fakeBoard, direction);
        
        assertEquals(mover.getPosition().x, initialX + directionX);
        assertEquals(mover.getPosition().y, initialY + directionY);
        assertEquals(success, null);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldMoveTheCorrectAmount_WhenMovingFractionsOfAPixel() {
        int initialX = 0;
        int initialY = 200;
        double directionX = 1.5;
        double directionY = 2.5;
        
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(null);
                
        Enemy mover = new Burrower(initialX, initialY);
        DirectionVector direction = new DirectionVector(directionX, directionY);
        mover.move(fakeBoard, direction);
        mover.move(fakeBoard, direction);
        
        assertEquals(mover.getPosition().x, initialX + 2 * directionX, .001);
        assertEquals(mover.getPosition().y, initialY + 2 * directionY, .001);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldFail_WhenTargetIsOccupied() {
        int initialX = 0;
        int initialY = 200;
        double directionX = 0.5;
        double directionY = 2.5;
        
        Terrain blocker = new Terrain(0, 0);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(blocker);
                
        Enemy mover = new Burrower(initialX, initialY);
        DirectionVector direction = new DirectionVector(directionX, directionY);
        Tower success = mover.move(fakeBoard, direction);
        
        assertEquals(mover.getPosition().x, initialX);
        assertEquals(mover.getPosition().y, initialY);
        assertEquals(success, blocker);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldFail_WhenHitboxTouchesOccupiedArea() {
        int initialX = 0;
        int initialY = 0;
        int directionY = 1;
        
        Enemy mover = new Burrower(initialX, initialY);
        
        Terrain blocker = new Terrain(0, 0);
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(new Point(initialX, initialY + directionY))).thenReturn(null);
        when(fakeBoard.towerAtPosition(new Point(initialX, initialY + directionY + mover.getSize()))).thenReturn(blocker);
                
        DirectionVector direction = new DirectionVector(0, directionY);
        Tower success = mover.move(fakeBoard, direction);
        
        assertEquals(mover.getPosition().x, initialX);
        assertEquals(mover.getPosition().y, initialY);
        assertEquals(success, blocker);
    }
    
    /**
     * Test of move method, of class Enemy.
     */
    @Test
    public void Move_ShouldFail_WhenMovingOffTheBoard() {
        int initialX = 0;
        int initialY = 0;
        int directionY = -1;
        
        Enemy mover = new Burrower(initialX, initialY);
        
        GameBoardPanel fakeBoard = mock(GameBoardPanel.class);
        when(fakeBoard.towerAtPosition(any(Point.class))).thenReturn(null);
                
        DirectionVector direction = new DirectionVector(0, directionY);
        Tower success = mover.move(fakeBoard, direction);
        
        assertEquals(mover.getPosition().x, initialX);
        assertEquals(mover.getPosition().y, initialY);
        assertEquals(success, null);
    }
}
