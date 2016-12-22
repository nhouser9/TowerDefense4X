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
package gui.Game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import units.Towers.TowerType;

/**
 * Extension of JPanel which represents the menu used for selecting Towers to
 * buy.
 *
 * @author Nick Houser
 */
public class PurchasePanel extends JPanel {
    
    //label which tracks the current selection
    private JLabel selection;
    
    /*
     * Constructor which initializes the buy menu, including adding buttons for 
     * each tower type and labels for the current selection.
     */
    public PurchasePanel() {
        setLayout(new GridLayout(0, 1));

        JLabel selectionLabel = new JLabel("Selection:", SwingConstants.CENTER);
        add(selectionLabel);

        selection = new JLabel("NONE", SwingConstants.CENTER);
        add(selection);

        for (TowerType type : TowerType.values()) {
            JButton towerTypeButton = new JButton(type.toString());
            towerTypeButton.addActionListener((ActionEvent e) -> {
                selection.setText(type.toString());
            });
            add(towerTypeButton);
        }

        JButton noSelectionButton = new JButton("NONE");
        noSelectionButton.addActionListener((ActionEvent e) -> {
            selection.setText("NONE");
        });
        add(noSelectionButton);
    }
    
    /**
     * Method which gets the current selection.
     * 
     * @return the TowerType currently selected
     * @throws IllegalArgumentException if no TowerType is selected
     */
    public TowerType selection() throws IllegalArgumentException {
        return TowerType.valueOf(selection.getText());
    }
}
