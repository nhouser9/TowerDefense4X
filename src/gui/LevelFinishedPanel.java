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

import gui.Game.InitialState;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class representing the pane shown when a level ends.
 *
 * @author Nick Houser
 */
public class LevelFinishedPanel extends JPanel {

    /**
     * Constructor which initializes subcomponents and arranges them according
     * to the proper layout.
     *
     * @param window the game window to call to reset or return to the main menu
     * @param won whether the user won or lost the leve
     */
    public LevelFinishedPanel(MainFrame window, boolean won) {
        

        JButton menuButton = new JButton("Main Menu");
        menuButton.addActionListener((ActionEvent e) -> {
            window.showMenu();
        });
        add(menuButton);
    }
}
