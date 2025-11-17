package fr.ubordeaux.ao.project.model.maze;

public class Maze {
    private int width;
    private int height;
    private static final int ROOM_SIZE = 5;
    private int[][] tiles;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        int totalWidth = width * ROOM_SIZE;
        int totalHeight = height * ROOM_SIZE;
        this.tiles = new int[totalWidth][totalHeight];

        // Remplir avec du sol par défaut
        for (int x = 0; x < totalWidth; x++) {
            for (int y = 0; y < totalHeight; y++) {
                tiles[x][y] = 0;
            }
        }

        generateRooms();

        System.out.println("Labyrinthe créé: " + width + "x" + height + " salles");
    }

    private void generateRooms() {
        int totalWidth = width * ROOM_SIZE;
        int totalHeight = height * ROOM_SIZE;

        // SOL
        for (int x = 1; x < totalWidth - 1; x++) {
            for (int y = 1; y < totalHeight - 1; y++) {
                tiles[x][y] = 72;
            }
        }

        // Murs extérieurs
        for (int x = 0; x < totalWidth; x++) {
            tiles[x][0] = 110;
            tiles[x][totalHeight - 1] = 109;
        }
        for (int y = 0; y < totalHeight; y++) {
            tiles[0][y] = 108;
            tiles[totalWidth - 1][y] = 111;
        }

        // Murs verticaux entre salles (avec portes)
        for (int roomX = 1; roomX < width; roomX++) {
            int wallX = roomX * ROOM_SIZE;
            for (int y = 1; y < totalHeight - 1; y++) {
                int roomY = y / ROOM_SIZE;
                int middleOfRoom = roomY * ROOM_SIZE + ROOM_SIZE / 2;

                if (y == middleOfRoom) {
                    tiles[wallX][y] = 112;
                } else {
                    tiles[wallX][y] = 108;
                }
            }
        }

        // Murs horizontaux entre salles (avec portes) - SANS toucher aux murs verticaux
        for (int roomY = 1; roomY < height; roomY++) {
            int wallY = roomY * ROOM_SIZE;
            for (int x = 1; x < totalWidth - 1; x++) {
                // Ne pas écraser les murs verticaux existants
                if (tiles[x][wallY] == 108 || tiles[x][wallY] == 112) {
                    continue; // Sauter cette case
                }

                int roomX = x / ROOM_SIZE;
                int middleOfRoom = roomX * ROOM_SIZE + ROOM_SIZE / 2;

                if (x == middleOfRoom) {
                    tiles[x][wallY] = 113;
                } else {
                    tiles[x][wallY] = 110;
                }
            }
        }

        // 4 COINS extérieurs
        tiles[0][0] = 138;
        tiles[totalWidth - 1][0] = 139;
        tiles[0][totalHeight - 1] = 136;
        tiles[totalWidth - 1][totalHeight - 1] = 137;
    }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getRoomSize() { return ROOM_SIZE; }
    public int getTotalWidth() { return width * ROOM_SIZE; }
    public int getTotalHeight() { return height * ROOM_SIZE; }

    public int getTileCode(int x, int y) {
        if (x < 0 || x >= getTotalWidth() || y < 0 || y >= getTotalHeight()) {
            return -1;
        }
        return tiles[x][y];
    }

    public boolean isInBounds(Position pos) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        return x >= 0 && x < getTotalWidth() && y >= 0 && y < getTotalHeight();
    }

    public boolean isWall(Position pos) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        return isWall(x, y);
    }

    public boolean isWall(int x, int y) {
        if (x < 0 || x >= getTotalWidth() || y < 0 || y >= getTotalHeight()) {
            return true;
        }
        int code = tiles[x][y];
        // Portes = passages autorisés
        if (code == 112 || code == 113) {
            return false;
        }
        return code >= 100;
    }
}