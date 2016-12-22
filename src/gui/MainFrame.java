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

import gui.game.GamePanel;
import gui.game.levels.LevelNotFoundException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class representing the main game window.
 *
 * @author Nick Houser
 */
public class MainFrame extends JFrame {

    //reference to the UI component currently displayed
    private JPanel visiblePanel;

    /**
     * Constructor which sets up the initial view.
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visiblePanel = null;

        showMenu();

        setResizable(false);
        setVisible(true);
    }

    /**
     * Helper method which sets up the Frame to show the game.
     *
     * @param level the level to show
     */
    public void showGame(int level) {
        GamePanel tryAdd;
        try {
            tryAdd = new GamePanel(this, level);
        } catch (LevelNotFoundException badLevel) {
            return;
        }

        if (visiblePanel != null) {
            remove(visiblePanel);
        }

        visiblePanel = tryAdd;
        add(visiblePanel);

        pack();
    }

    public void showMenu() {
        if (visiblePanel != null) {
            remove(visiblePanel);
        }

        visiblePanel = new MenuPanel(this);
        add(visiblePanel);

        pack();
    }

    public void showLevels() {
        if (visiblePanel != null) {
            remove(visiblePanel);
        }

        visiblePanel = new LevelsPanel(this);
        add(visiblePanel);

        pack();
    }

    /**
     * Method which updates the game board if gameplay is underway. Expects to
     * be called repeatedly to simulate continuous action.
     */
    public void tick() {
        if (visiblePanel != null && visiblePanel instanceof GamePanel) {
            ((GamePanel) visiblePanel).tick();
            repaint();
        }
    }
}
