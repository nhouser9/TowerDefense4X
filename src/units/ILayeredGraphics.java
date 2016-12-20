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
package units;

import java.awt.Graphics;

/**
 * Interface which supports drawing graphics over or under the normal graphics.
 *
 * @author Nick Houser
 */
public interface ILayeredGraphics {

    /**
     * Draws graphics. This expects to be called before or after the regular
     * graphics are drawn to simulate drawing below or on top of those graphics.
     *
     * @param g the Graphics object to draw on
     */
    void drawLayer(Graphics g);
}
