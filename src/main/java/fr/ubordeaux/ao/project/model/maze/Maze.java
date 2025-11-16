package fr.ubordeaux.ao.project.model.maze;

public class Maze {
    private int width;
    private int height;
    private static final int ROOM_SIZE = 5;
    private boolean[][] walls; // Grille des murs

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        int totalWidth = width * ROOM_SIZE;
        int totalHeight = height * ROOM_SIZE;
        this.walls = new boolean[totalWidth][totalHeight];

        // Générer les murs
        generateWalls();

        System.out.println("Labyrinthe créé: " + width + "x" + height + " salles");
    }

    private void generateWalls() {
        int totalWidth = width * ROOM_SIZE;
        int totalHeight = height * ROOM_SIZE;

        // Murs extérieurs (bordure du labyrinthe)
        for (int x = 0; x < totalWidth; x++) {
            walls[x][0] = true;                    // Haut
            walls[x][totalHeight - 1] = true;     // Bas
        }
        for (int y = 0; y < totalHeight; y++) {
            walls[0][y] = true;                    // Gauche
            walls[totalWidth - 1][y] = true;      // Droite
        }

        // Murs entre les salles (grille)
        for (int roomX = 1; roomX < width; roomX++) {
            int wallX = roomX * ROOM_SIZE;
            for (int y = 0; y < totalHeight; y++) {
                // Laisser une ouverture au milieu (porte)
                int doorY = (y / ROOM_SIZE) * ROOM_SIZE + ROOM_SIZE / 2;
                if (y != doorY) {
                    walls[wallX][y] = true;
                }
            }
        }

        for (int roomY = 1; roomY < height; roomY++) {
            int wallY = roomY * ROOM_SIZE;
            for (int x = 0; x < totalWidth; x++) {
                // Laisser une ouverture au milieu (porte)
                int doorX = (x / ROOM_SIZE) * ROOM_SIZE + ROOM_SIZE / 2;
                if (x != doorX) {
                    walls[x][wallY] = true;
                }
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getRoomSize() { return ROOM_SIZE; }
    public int getTotalWidth() { return width * ROOM_SIZE; }
    public int getTotalHeight() { return height * ROOM_SIZE; }

    public boolean isInBounds(Position pos) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        return x >= 0 && x < getTotalWidth() && y >= 0 && y < getTotalHeight();
    }

    public boolean isWall(Position pos) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();

        if (!isInBounds(pos)) return true;

        return walls[x][y];
    }

    public boolean isWall(int x, int y) {
        if (x < 0 || x >= getTotalWidth() || y < 0 || y >= getTotalHeight()) {
            return true;
        }
        return walls[x][y];
    }
}