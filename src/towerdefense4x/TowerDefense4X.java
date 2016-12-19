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
package towerdefense4x;

import gui.BoardState;
import gui.GameBoardPanel;
import gui.GuiPanel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * Main class and entry point for the program. Creates the frame which will
 * display the game before running the main game loop.
 *
 * @author Nick Houser
 */
public class TowerDefense4X {

    //constant indicating how many milliseconds should elapse between game ticks
    private static final int TICK_LENGTH = 20;

    /**
     * Main method and entry point for the program. Creates the main game window
     * and adds two panels to it: the game board panel and the HUD panel.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        JFrame gameWindow = new JFrame();
        gameWindow.setLayout(new BoxLayout(gameWindow.getContentPane(), BoxLayout.Y_AXIS));
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.add(new GuiPanel());
        GameBoardPanel board = new GameBoardPanel(new BoardState(BoardState.InitialState.INTEGRATIONTEST));
        gameWindow.add(board);
        gameWindow.pack();
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);

        try {
            while (true) {
                Thread.sleep(TICK_LENGTH);
                board.tick();
                gameWindow.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
