public class GameMap {

    private char[][] currentMap;
    private int playerX;
    private int playerY;

    public GameMap(int level) {
        switch (level) {
            case 1:
                currentMap = new char[][]{
                        {'P', '-', '-', '-', '-', '-', 'X'}
                };
                playerX = 0;
                playerY = 0;
                break;
            case 2:
                currentMap = new char[][]{
                        {'P', '-', '-', '-', 'X'},
                        {'-', '-', '-', '-', '-'}
                };
                playerX = 0;
                playerY = 0;
                break;
            case 3:
                currentMap = new char[][]{
                        {'P', '-', '-', '-'},
                        {'-', '-', '-', '-'},
                        {'-', '-', 'X', '-'}
                };
                playerX = 0;
                playerY = 0;
                break;
        }
    }

    public void moveRight() {
        if (playerX < currentMap[playerY].length - 1) {
            currentMap[playerY][playerX] = '-';
            playerX++;
            currentMap[playerY][playerX] = 'P';
        }
    }

    public void drawMap(int mapLevel) {
        System.out.println("Level: " + mapLevel);
        for (char[] row : currentMap) {
            for (char tile : row) {
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }

}