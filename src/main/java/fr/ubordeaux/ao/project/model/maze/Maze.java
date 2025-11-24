package fr.ubordeaux.ao.project.model.maze;

import fr.ubordeaux.ao.project.model.items.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    private int width;
    private int height;
    private static final int ROOM_SIZE = 5;
    private int[][] tiles;
    private List<Item> items;
    private Random random;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.items = new ArrayList<>();

        int totalWidth = width * ROOM_SIZE;
        int totalHeight = height * ROOM_SIZE;
        this.tiles = new int[totalWidth][totalHeight];

        for (int x = 0; x < totalWidth; x++) {
            for (int y = 0; y < totalHeight; y++) {
                tiles[x][y] = 0;
            }
        }

        generateRooms();
        placeItems(); // Nouvelle méthode

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

        // Murs verticaux entre salles
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

        // Murs horizontaux entre salles
        for (int roomY = 1; roomY < height; roomY++) {
            int wallY = roomY * ROOM_SIZE;
            for (int x = 1; x < totalWidth - 1; x++) {
                if (tiles[x][wallY] == 108 || tiles[x][wallY] == 112) {
                    continue;
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

        // 4 COINS
        tiles[0][0] = 138;
        tiles[totalWidth - 1][0] = 139;
        tiles[0][totalHeight - 1] = 136;
        tiles[totalWidth - 1][totalHeight - 1] = 137;
    }

    /**
     * Place des objets décoratifs dans chaque salle
     */
    private void placeItems() {
        // Objets disponibles (Table 500 du PDF)
        int[] objectCodes = {
                520,  // Coffre
                544,  // Table
                527,  // Banc
                528   // Chaise
        };

        // Garder trace des positions occupées
        List<String> occupiedPositions = new ArrayList<>();

        // Placer 1-2 objets par salle seulement
        for (int roomX = 0; roomX < width; roomX++) {
            for (int roomY = 0; roomY < height; roomY++) {
                int numObjects = 1 + random.nextInt(2); // 1 ou 2 objets

                for (int i = 0; i < numObjects; i++) {
                    // Position aléatoire dans la salle (éviter les bords)
                    int x = roomX * ROOM_SIZE + 2 + random.nextInt(2);
                    int y = roomY * ROOM_SIZE + 2 + random.nextInt(2);

                    String posKey = x + "," + y;

                    // Éviter de placer 2 objets au même endroit
                    if (occupiedPositions.contains(posKey)) {
                        continue;
                    }

                    occupiedPositions.add(posKey);

                    // Choisir un objet aléatoire
                    int code = objectCodes[random.nextInt(objectCodes.length)];

                    Position pos = new Position(x, y, 0);
                    items.add(new Item(pos, code, getItemType(code)));
                }
            }
        }

        System.out.println("Objets placés: " + items.size());
    }

    private String getItemType(int code) {
        return switch (code) {
            case 520 -> "CHEST";
            case 544 -> "TABLE";
            case 527, 528 -> "FURNITURE";
            default -> "DECORATION";
        };
    }

    public List<Item> getItems() {
        return items;
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
        if (code == 112 || code == 113) {
            return false;
        }
        return code >= 100;
    }
}