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

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Extension of JPanel which represents the GUI.
 *
 * @author Nick Houser
 */
public class GuiPanel extends JPanel {

    /**
     * Constructor which initializes the GUI, including adding dummy text and
     * color.
     */
    public GuiPanel() {
        add(new JLabel("Temporary Text for GUI"));
        setBackground(Color.red);
        setPreferredSize(new Dimension(100, 100));
    }
}
