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

import java.util.Random;

/**
 * Class which generates global random numbers; this prevents each unit that
 * needs a random number from maintining its own Random(), which can lead to a
 * loss of true randomness.
 *
 * @author Nick Houser
 */
public class SingletonRandom {
    //field which contains a singleton Random

    private static Random rng = null;

    /**
     * Method which gets the singleton Random. Initializes it if it is null.
     *
     * @return a random integer
     */
    public static Random getRandom() {
        if (rng == null) {
            rng = new Random(System.currentTimeMillis());
        }
        return rng;
    }
}
