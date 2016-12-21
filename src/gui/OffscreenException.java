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

/**
 * Extension of exception which captures a search that referenced an offscreen
 * area.
 *
 * @author Nick Houser
 */
public class OffscreenException extends Exception {

    //variables which track the direction from the board to the offscreen area that was referenced
    public final boolean offscreenLeft;
    public final boolean offscreenRight;
    public final boolean offscreenTop;
    public final boolean offscreenBottom;

    /**
     * Constructor which stores the direction from the board to the offscreen
     * area that caused this exception.
     *
     * @param left whether the area was left of the board
     * @param right whether the area was right of the board
     * @param top whether the area was above the board
     * @param bottom whether the area was below the board
     */
    public OffscreenException(boolean left, boolean right, boolean top, boolean bottom) {
        super("Tried to search for a grid position that was not on the board.");
        offscreenLeft = left;
        offscreenRight = right;
        offscreenTop = top;
        offscreenBottom = bottom;
    }
}
