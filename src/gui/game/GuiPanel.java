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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import towerdefense4x.TowerDefense4X;

/**
 * Extension of JPanel which represents the GUI.
 *
 * @author Nick Houser
 */
public class GuiPanel extends JPanel {

    //constant which stores the amount of ticks in a second
    private static final int TICKS_PER_SECOND = 1000 / TowerDefense4X.TICK_LENGTH;

    //the number of seconds per minute
    private static final int SECONDS_PER_MINUTE = 60;

    //variables indicating how much time remains in the round
    private int remainingTicks;
    private int remainingSeconds;
    private int remainingMinutes;

    //reference to the main frame for triggering the end of a game
    private MainFrame window;

    //UI element to update with the remaining time
    private JLabel timeLabel;

    /**
     * Constructor which initializes the GUI, including adding main menu and
     * reset buttons, as well as time labels.
     *
     * @param window the game window to call to reset or return to the main menu
     * @param level the initial level used for the game, used for resetting
     * @param initialTicks the number of ticks the player must survive to win
     */
    public GuiPanel(MainFrame window, int level, int initialTicks) {
        this.window = window;

        setLayout(new GridLayout(0, 1));

        add(new JLabel("Time Left:"));

        remainingTicks = initialTicks % TICKS_PER_SECOND;
        remainingSeconds = Math.floorDiv(initialTicks, TICKS_PER_SECOND);
        remainingMinutes = Math.floorDiv(remainingSeconds, SECONDS_PER_MINUTE);
        remainingSeconds = remainingSeconds % SECONDS_PER_MINUTE;
        timeLabel = new JLabel();
        add(timeLabel);

        JButton retry = new JButton("Retry");
        retry.addActionListener((ActionEvent e) -> {
            window.showGame(level);
        });
        add(retry);

        JButton mainMenu = new JButton("Menu");
        mainMenu.addActionListener((ActionEvent e) -> {
            window.showMenu();
        });
        add(mainMenu);
    }

    /**
     * Method which updates the time remaining in the round.
     */
    public void tick() {
        if (remainingTicks == 0) {
            if (remainingSeconds == 0) {
                if (remainingMinutes == 0) {
                    //win the game
                } else {
                    remainingMinutes = remainingMinutes - 1;
                    remainingSeconds = 60;
                    remainingTicks = TICKS_PER_SECOND;
                }
            } else {
                remainingSeconds = remainingSeconds - 1;
                remainingTicks = TICKS_PER_SECOND;
            }
        }
        remainingTicks = remainingTicks - 1;
        timeLabel.setText(remainingMinutes + ":" + remainingSeconds + ":" + remainingTicks);
    }
}
