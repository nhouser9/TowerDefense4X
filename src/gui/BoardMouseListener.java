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
package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import units.Towers.Blocker;
import units.Towers.Generator;
import units.Towers.Healer;
import units.Towers.Shooter;
import units.Towers.TowerType;

/**
 * Class which handles mouse click events on the game board.
 *
 * @author Nick Houser
 */
public class BoardMouseListener implements MouseListener {

    //link to the purchase menu to determine which Tower type to place
    private PurchasePanel buyMenu;

    //link to the game board to place Towers on
    private GameBoardPanel gameBoard;

    /**
     * Constructor which stores a link to the buy menu for querying which Tower
     * type to place and the game board for placing the Tower.
     *
     * @param buyMenu the buy menu to ask which Tower type to buy
     * @param gameBoard the game board to add new Towers to
     */
    public BoardMouseListener(PurchasePanel buyMenu, GameBoardPanel gameBoard) {
        this.buyMenu = buyMenu;
        this.gameBoard = gameBoard;
    }

    /**
     * Mouse clicked event which handles creating a new Tower at the clicked
     * location.
     *
     * @param e the mouse event which triggered this action
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            TowerType selection = buyMenu.selection();
            switch(selection) {
                case BLOCKER:
                    gameBoard.addUnit(new Blocker(e.getX(), e.getY(), gameBoard.getSquareSize()));
                    break;
                case GENERATOR:
                    gameBoard.addUnit(new Generator(e.getX(), e.getY(), gameBoard.getSquareSize()));
                    break;
                case SHOOTER:
                    gameBoard.addUnit(new Shooter(e.getX(), e.getY(), gameBoard.getSquareSize()));
                    break;
                case HEALER:
                    gameBoard.addUnit(new Healer(e.getX(), e.getY(), gameBoard.getSquareSize()));
                    break;
            }
        }catch (Exception noSelection) {
            //simply don't place a Tower
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
