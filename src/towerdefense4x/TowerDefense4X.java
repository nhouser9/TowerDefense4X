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

import gui.MainFrame;

/**
 * Main class and entry point for the program. Creates the frame which will
 * display the game before running the main game loop.
 *
 * @author Nick Houser
 */
public class TowerDefense4X {

    /**
     * Constant indicating how many milliseconds should elapse between game ticks.
     */
    public static final int TICK_LENGTH = 20;

    /**
     * Main method and entry point for the program. Creates the main game window
     * and adds two panels to it: the game board panel and the HUD panel.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        MainFrame frame = new MainFrame();

        try {
            long nextTick = System.currentTimeMillis();
            long delta;
            while (true) {
                nextTick = nextTick + TICK_LENGTH;

                frame.tick();

                delta = nextTick - System.currentTimeMillis();
                if (delta > 0) {
                    Thread.sleep(delta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
