package it.polimi.ingsw.PSP25.Model;

/**
 * Class Board. Board is the playground and it's made of 25 spaces organized in a 5x5 matrix.
 */
public class Board {
    private Space[][] spaceMatrix = new Space[5][5];

    /**
     * Board Constructor.
     */
    public Board() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                spaceMatrix[i][j] = new Space(j, i);
            }
        }
    }

    /**
     * @param x Space column number
     * @param y Space row number
     * @return the Space in column x and row y
     */
    public Space getSpace(int x, int y) {
        return spaceMatrix[y][x];
    }

}