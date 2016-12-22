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

import gui.game.levels.LevelCreator;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class representing the levels menu for the game.
 *
 * @author Nick Houser
 */
public class LevelsPanel extends JPanel {

    /**
     * Constructor which initializes subcomponents and arranges them according
     * to the proper layout.
     *
     * @param window the game window to call to reset or return to the main menu
     */
    public LevelsPanel(MainFrame window) {
        for (int level = 1; level <= LevelCreator.MAX_LEVEL; level++) {
            final int finalLevel = level;
            JButton levelButton = new JButton("" + level);
            levelButton.addActionListener((ActionEvent e) -> {
                window.showGame(finalLevel);
            });
            add(levelButton);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            window.showMenu();
        });
        add(backButton);
    }
}
