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

import gui.MainFrame;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class representing the main menu for the game.
 *
 * @author Nick Houser
 */
public class MenuPanel extends JPanel {

    /**
     * Constructor which initializes subcomponents and arranges them according
     * to the proper layout.
     *
     * @param window the game window to call to reset or return to the main menu
     */
    public MenuPanel(MainFrame window) {
        JButton playButton = new JButton("Play");
        playButton.addActionListener((ActionEvent e) -> {
            window.showLevels();
        });
        add(playButton);

        JButton highScoresButton = new JButton("High Scores");
        add(highScoresButton);
        
        JButton themesButton = new JButton("Themes");
        add(themesButton);
    }
}
