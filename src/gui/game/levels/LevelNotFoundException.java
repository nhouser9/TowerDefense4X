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
package gui.game.levels;

/**
 * Exception which will be thrown when an invalid level id is passed to the LevelCreator.
 * 
 * @author Nick Houser
 */
public class LevelNotFoundException extends Exception {
    
    //the bad level that caused the exception
    public final int level;
    
    /**
     * Constructor which saves the level number that caused the exception.
     * 
     * @param cause the inner exception
     * @param level the level number that caused the exception
     */
    public LevelNotFoundException(Exception cause, int level) {
        super(cause);
        this.level = level;
    }
}
