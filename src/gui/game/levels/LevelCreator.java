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

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import towerdefense4x.SingletonRandom;
import units.enemies.Hive;
import units.towers.Generator;
import units.towers.Terrain;

/**
 * Class which handles creating the board state for any requested level.
 *
 * @author Nick Houser
 */
public class LevelCreator {

    /**
     * Constant indicating the highest available level.
     */
    public static final int MAX_LEVEL = 10;

    //constants storing the names of valid commands contained by .level files
    private static final String COMMAND_SQUARES = "squares";
    private static final String COMMAND_TIME = "time";
    private static final String COMMAND_HIVE = "hive";
    private static final String COMMAND_GENERATOR = "generator";

    //constant indicating the command delimter for .level files
    private static final String DELIMITER_COMMAND = "=";

    //constant indicating the argument delimiter for .level files
    private static final String DELIMITER_ARGUMENT = ",";

    /**
     * Sets up and returns the board state for the passed level.
     *
     * @param level the level to create
     * @return an initial board state corresponding to the passed level
     * @throws LevelNotFoundException if an error occurs during deserialization
     */
    public static BoardState createLevel(int level) throws LevelNotFoundException {
        URL levelFilePath = LevelCreator.class.getResource(level + ".level");
        try {
            List<String> lines = Files.readAllLines(Paths.get(levelFilePath.toURI()));
            return deserializeLines(lines);
        } catch (Exception e) {
            throw new LevelNotFoundException(e, level);
        }
    }

    /**
     * Method which deserializes a list of file lines into a BoardState.
     *
     * @param lines the lines to deserialize
     * @return the deserialized BoardState
     */
    private static BoardState deserializeLines(List<String> lines) {
        int squares = -1;
        BoardState toReturn = null;

        for (String line : lines) {
            String[] lineSplit = line.split(DELIMITER_COMMAND);
            String command = lineSplit[0];
            String args = lineSplit[1];

            switch (command) {
                case COMMAND_SQUARES:
                    squares = Integer.parseInt(args);
                    break;
                case COMMAND_TIME:
                    toReturn = new BoardState(squares, Integer.parseInt(args));
                    fillTerrain(toReturn);
                    break;
                case COMMAND_HIVE:
                    addHive(toReturn, args);
                    break;
                case COMMAND_GENERATOR:
                    addGenerator(toReturn, args);
                    break;
                default:
                    break;
            }
        }

        return toReturn;
    }

    /**
     * Helper method which fills the board with terrain.
     *
     * @param addTo the BoardState to add to
     */
    private static void fillTerrain(BoardState addTo) {
        for (int gridX = 0; gridX < addTo.numSquares; gridX++) {
            for (int gridY = 0; gridY < addTo.numSquares; gridY++) {
                addTo.towers[gridX][gridY] = new Terrain(gridX * addTo.squareSize, gridY * addTo.squareSize, addTo.squareSize);
            }
        }
    }

    /**
     * Helper method which adds a Hive to the BoardState.
     *
     * @param addTo the BoardState to add to
     * @param args the position arguments
     */
    private static void addHive(BoardState addTo, String args) {
        String[] argsSplit = args.split(DELIMITER_ARGUMENT);
        int gridX = Integer.parseInt(argsSplit[0]);
        int gridY = Integer.parseInt(argsSplit[1]);
        addTo.towers[gridX][gridY] = null;
        addTo.enemies.add(new Hive(gridX * addTo.squareSize, gridY * addTo.squareSize, addTo.squareSize, SingletonRandom.getRandom()));
    }

    /**
     * Helper method which adds a powered Generator to the BoardState.
     *
     * @param addTo the BoardState to add to
     * @param args the position arguments
     */
    private static void addGenerator(BoardState addTo, String args) {
        String[] argsSplit = args.split(DELIMITER_ARGUMENT);
        int gridX = Integer.parseInt(argsSplit[0]);
        int gridY = Integer.parseInt(argsSplit[1]);
        addTo.towers[gridX][gridY] = null;
        Generator toAdd = new Generator(gridX * addTo.squareSize, gridY * addTo.squareSize, addTo.squareSize);
        toAdd.power(new Generator(0, 0, 1));
        addTo.towers[gridX][gridY] = toAdd;
    }
}
