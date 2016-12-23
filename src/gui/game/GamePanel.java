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
package gui.game;

import gui.MainFrame;
import gui.game.levels.BoardState;
import gui.game.levels.LevelCreator;
import gui.game.levels.LevelNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Panel which contains all of the necessary components for gameplay, that is:
 * the HUD panel, the Purchase panel, and the Game Board panel.
 *
 * @author Nick Houser
 */
public class GamePanel extends JPanel {

    //references to the game board and gui so they can be updated continuously
    private GameBoardPanel board;
    private GuiPanel gui;
    
    /**
     * Constructor which initializes subcomponents and adds them in the proper
     * layout.
     *
     * @param parent the parent frame
     * @param level the level to create
     * @throws LevelNotFoundException if passed an invalid level
     */
    public GamePanel(MainFrame parent, int level) throws LevelNotFoundException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        BoardState levelState = LevelCreator.createLevel(level);
        
        gui = new GuiPanel(parent, level, levelState.initialTime);
        board = new GameBoardPanel(levelState);
        PurchasePanel buy = new PurchasePanel();

        BoardMouseListener mouseListener = new BoardMouseListener(buy, board);
        board.addMouseListener(mouseListener);
        board.addMouseMotionListener(mouseListener);

        add(gui);
        add(board);
        add(buy);
    }
    
    /**
     * Method which updates the game board. Expects to be called repeatedly to simulate continuous action.
     */
    public void tick() {
        board.tick();
        gui.tick();
    }
}
