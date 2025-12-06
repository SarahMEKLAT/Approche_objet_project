package fr.ubordeaux.ao.project.model.maze;

import fr.ubordeaux.ao.project.model.items.Item;
import fr.ubordeaux.ao.project.model.items.Door;
import fr.ubordeaux.ao.project.model.items.KeyFragment;
import fr.ubordeaux.ao.mazing.api.IWindowGame;
import java.util.ArrayList;
import java.util.List;

/**
 * Labyrinthe en forme de croix avec 5 salles :
 *       [1] Nord
 *         |
 *   [3]-[0]-[2]
 *   Ouest Centre Est
 *         |
 *       [4] Sud
 */
public class Maze {
    private int totalWidth;
    private int totalHeight;
    private List<Item> items;

    // ✨ Porte de sortie (objet Door)
    private Door exitDoor;

    // ✨ Liste des KeyFragments (code 550)
    private List<KeyFragment> keyFragments;

    // ✨ Obstacles bloquants
    private List<Obstacle> obstacles;

    // Taille d'une salle (10x10)
    private static final int ROOM_SIZE = 10;

    // IDs des salles
    public static final int CENTER_ROOM = 0;
    public static final int NORTH_ROOM = 1;
    public static final int EAST_ROOM = 2;
    public static final int WEST_ROOM = 3;
    public static final int SOUTH_ROOM = 4;

    // Codes Mazing
    private static final int FLOOR = 72;        // Sol bois
    private static final int WALL_N = 110;      // Mur Nord
    private static final int WALL_S = 109;      // Mur Sud
    private static final int WALL_W = 108;      // Mur Ouest
    private static final int WALL_E = 111;      // Mur Est

    private static final int DOOR_S = 113;      // Porte verticale
    private static final int DOOR_N = 114;      // Porte verticale
    private static final int DOOR_W = 112;
    private static final int DOOR_E = 115;      // Porte verticale

    // ✨ Codes Table 500 - TOUS BLOQUANTS
    private static final int OBJ_500 = 500;
    private static final int OBJ_504 = 504;
    private static final int OBJ_505 = 505;
    private static final int OBJ_506 = 506;
    private static final int OBJ_527 = 527;
    private static final int OBJ_532 = 532;
    private static final int OBJ_556 = 556;
    private static final int OBJ_557 = 557;
    private static final int OBJ_558 = 558;

    // ✨ Codes pour entourer les KeyFragments (décoration)
    private static final int DECO_412 = 412;
    private static final int DECO_413 = 413;
    private static final int DECO_414 = 414;
    private static final int DECO_415 = 415;

    // Positions des salles
    private int centerX, centerY;
    private int northX, northY;
    private int southX, southY;
    private int westX, westY;
    private int eastX, eastY;

    public Maze() {
        // Calcul des positions (avec espacement de 1)
        centerX = ROOM_SIZE + 1;
        centerY = ROOM_SIZE + 1;

        northX = ROOM_SIZE + 1;
        northY = 0;

        southX = ROOM_SIZE + 1;
        southY = (ROOM_SIZE + 1) * 2;

        westX = 0;
        westY = ROOM_SIZE + 1;

        eastX = (ROOM_SIZE + 1) * 2;
        eastY = ROOM_SIZE + 1;

        // Dimensions totales
        this.totalWidth = ROOM_SIZE * 3 + 2;  // 3 salles + 2 espaces
        this.totalHeight = ROOM_SIZE * 3 + 2;
        this.items = new ArrayList<>();
        this.keyFragments = new ArrayList<>();
        this.obstacles = new ArrayList<>();

        // ✨ Créer la porte de sortie au fond de la salle Sud
        Position exitPos = new Position(
                southX + ROOM_SIZE / 2,      // Centre horizontal de la salle Sud
                southY + ROOM_SIZE - 1,      // Mur du fond de la salle Sud
                0
        );
        exitDoor = new Door(exitPos);
        exitDoor.setRequiredFragments(3); // ✨ Il faut 3 fragments maintenant !

        // ✨ Placer les 3 KeyFragments (Nord, Est, Ouest)
        spawnKeyFragments();

        // ✨ Placer les obstacles BLOQUANTS de la table 500
        spawnObstacles();

        System.out.println("Labyrinthe créé : " + totalWidth + "x" + totalHeight + " tuiles");
        System.out.println("🚪 Porte de sortie placée en : " + exitPos);
        System.out.println("🗝️ " + keyFragments.size() + " fragments de clé placés (code 550)");
        System.out.println("🪨 " + obstacles.size() + " obstacles BLOQUANTS placés (table 500)");
    }

    /**
     * ✨ Place les 3 KeyFragments (code 550) dans les salles Nord, Est, Ouest
     * ENTOURÉS de décorations 412, 413, 414, 415 pour les mettre en valeur
     */
    private void spawnKeyFragments() {
        // Fragment 1 : Salle Nord (centre)
        int northFragX = northX + ROOM_SIZE / 2;
        int northFragY = northY + ROOM_SIZE / 2;
        Position northFragPos = new Position(northFragX, northFragY, 0);
        KeyFragment northFrag = new KeyFragment(northFragPos, 1);
        keyFragments.add(northFrag);
        items.add(northFrag);

        // Fragment 2 : Salle Est (centre)
        int eastFragX = eastX + ROOM_SIZE / 2;
        int eastFragY = eastY + ROOM_SIZE / 2;
        Position eastFragPos = new Position(eastFragX, eastFragY, 0);
        KeyFragment eastFrag = new KeyFragment(eastFragPos, 2);
        keyFragments.add(eastFrag);
        items.add(eastFrag);

        // Fragment 3 : Salle Ouest (centre)
        int westFragX = westX + ROOM_SIZE / 2;
        int westFragY = westY + ROOM_SIZE / 2;
        Position westFragPos = new Position(westFragX, westFragY, 0);
        KeyFragment westFrag = new KeyFragment(westFragPos, 3);
        keyFragments.add(westFrag);
        items.add(westFrag);

        System.out.println("🗝️ Fragment 1 (Nord) placé en : " + northFragPos);
        System.out.println("🗝️ Fragment 2 (Est) placé en : " + eastFragPos);
        System.out.println("🗝️ Fragment 3 (Ouest) placé en : " + westFragPos);
    }

    /**
     * ✨ Place des obstacles BLOQUANTS de la table 500 dans les salles
     * TOUS sont bloquants (blocking = true)
     */
    private void spawnObstacles() {
        // SALLE CENTRE : Mix varié - TOUS BLOQUANTS
        addObstacle(centerX + 2, centerY + 2, OBJ_500);
        addObstacle(centerX + 7, centerY + 3, OBJ_504);
        addObstacle(centerX + 3, centerY + 7, OBJ_556);
        addObstacle(centerX + 8, centerY + 8, OBJ_557);

        // SALLE NORD : Obstacles symétriques - TOUS BLOQUANTS
        addObstacle(northX + 2, northY + 2, OBJ_505);
        addObstacle(northX + 7, northY + 2, OBJ_505);
        addObstacle(northX + 2, northY + 7, OBJ_506);
        addObstacle(northX + 7, northY + 7, OBJ_506);
        // Pas d'obstacle au centre (réservé pour le fragment + décoration)

        // SALLE EST : Plus d'obstacles - TOUS BLOQUANTS
        addObstacle(eastX + 2, eastY + 3, OBJ_527);
        addObstacle(eastX + 2, eastY + 6, OBJ_527);
        addObstacle(eastX + 7, eastY + 4, OBJ_532);
        addObstacle(eastX + 8, eastY + 7, OBJ_500);
        // Pas d'obstacle au centre (réservé pour le fragment + décoration)

        // SALLE OUEST : Mix équilibré - TOUS BLOQUANTS
        addObstacle(westX + 3, westY + 3, OBJ_504);
        addObstacle(westX + 2, westY + 7, OBJ_557);
        // Pas d'obstacle au centre (réservé pour le fragment + décoration)

        // SALLE SUD : Zone de boss - Beaucoup d'obstacles - TOUS BLOQUANTS
        addObstacle(southX + 2, southY + 2, OBJ_532);
        addObstacle(southX + 7, southY + 2, OBJ_532);
        addObstacle(southX + 2, southY + 7, OBJ_527);
        addObstacle(southX + 7, southY + 7, OBJ_527);
        addObstacle(southX + 4, southY + 4, OBJ_500);
        addObstacle(southX + 3, southY + 6, OBJ_556);
        addObstacle(southX + 6, southY + 6, OBJ_557);
        addObstacle(southX + 5, southY + 3, OBJ_558);
    }

    /**
     * ✨ Ajoute un obstacle BLOQUANT à la liste
     * Par défaut, tous les obstacles bloquent maintenant
     */
    private void addObstacle(int x, int y, int code) {
        obstacles.add(new Obstacle(new Position(x, y, 0), code, "Obj" + code, true));
    }

    /**
     * Affiche le labyrinthe dans la fenêtre Mazing
     * Méthode appelée par GameView
     */
    public void render(IWindowGame window) {
        // 1. DESSINER LES SOLS de toutes les salles (fillArea = plus rapide)
        window.fillArea(FLOOR, centerX, centerY, 0, ROOM_SIZE, ROOM_SIZE);
        window.fillArea(FLOOR, northX, northY, 0, ROOM_SIZE, ROOM_SIZE);
        window.fillArea(FLOOR, southX, southY, 0, ROOM_SIZE, ROOM_SIZE);
        window.fillArea(FLOOR, westX, westY, 0, ROOM_SIZE, ROOM_SIZE);
        window.fillArea(FLOOR, eastX, eastY, 0, ROOM_SIZE, ROOM_SIZE);

        // 2. DESSINER LES COULOIRS (sol entre les salles) - 1 tuile de large
        // Nord <-> Centre
        window.fillArea(FLOOR, centerX + ROOM_SIZE/2, northY + ROOM_SIZE, 0, 1, 1);

        // Sud <-> Centre
        window.fillArea(FLOOR, centerX + ROOM_SIZE/2, centerY + ROOM_SIZE, 0, 1, 1);

        // Ouest <-> Centre
        window.fillArea(FLOOR, westX + ROOM_SIZE, westY + ROOM_SIZE/2, 0, 1, 1);

        // Est <-> Centre
        window.fillArea(FLOOR, centerX + ROOM_SIZE, eastY + ROOM_SIZE/2, 0, 1, 1);

        // 3. DESSINER LES MURS avec matrices (méthode du prof)
        createRoomWalls(window, centerX, centerY, true, true, true, true);   // Centre : 4 portes
        createRoomWalls(window, northX, northY, false, false, false, true);  // Nord : porte Sud
        createRoomWalls(window, southX, southY, true, false, false, false);  // Sud : porte Nord
        createRoomWalls(window, westX, westY, false, true, false, false);    // Ouest : porte Est
        createRoomWalls(window, eastX, eastY, false, false, true, false);    // Est : porte Ouest

        // 4. AJOUTER LES MURS DES COULOIRS
        createCorridorWalls(window);

        // ✨ 5. PLACER LES OBSTACLES BLOQUANTS (table 500)
        for (Obstacle obstacle : obstacles) {
            Position pos = obstacle.getPosition();
            window.add(obstacle.getCode(),
                    (int)pos.getX(),
                    (int)pos.getY(),
                    (int)pos.getZ());
        }

        // ✨ 6. PLACER LA PORTE DE SORTIE
        Position doorPos = exitDoor.getPosition();
        window.add(exitDoor.getCode(),
                (int)doorPos.getX(),
                (int)doorPos.getY(),
                (int)doorPos.getZ());

        // ✨ 7. PLACER LES KEY FRAGMENTS (code 550) AVEC DÉCORATIONS AUTOUR
        for (KeyFragment fragment : keyFragments) {
            if (!fragment.isCollected()) {
                Position fragPos = fragment.getPosition();
                int fragX = (int)fragPos.getX();
                int fragY = (int)fragPos.getY();

                // Placer les décorations autour (412, 413, 414, 415)
                // Pattern en croix autour du fragment:
                //     414
                // 412 [550] 415
                //     413
                // Placer le fragment au centre (par-dessus les décorations)
                window.add(fragment.getCode(), fragX, fragY, 0);
                window.add(DECO_414, fragX, fragY , 0);  // Haut
                window.add(DECO_412, fragX, fragY, 0);  // Gauche

                window.add(DECO_413, fragX, fragY , 0);  // Bas
                window.add(DECO_415, fragX , fragY, 0);  // Droite


            }
        }
    }

    /**
     * Crée les murs d'une salle avec la méthode matricielle
     */
    private void createRoomWalls(IWindowGame window, int startX, int startY,
                                 boolean doorN, boolean doorE, boolean doorW, boolean doorS) {

        // Créer la matrice des murs (10x10, une seule couche z=0)
        int[][] walls = new int[ROOM_SIZE][ROOM_SIZE];

        // Initialiser tout à -1 (rien)
        for (int y = 0; y < ROOM_SIZE; y++) {
            for (int x = 0; x < ROOM_SIZE; x++) {
                walls[y][x] = -1;
            }
        }

        // Les 4 COINS d'abord (prioritaires)
        walls[0][0] = 138;                          // Coin haut-gauche
        walls[0][ROOM_SIZE-1] = 139;                // Coin haut-droite
        walls[ROOM_SIZE-1][0] = 136;                // Coin bas-gauche
        walls[ROOM_SIZE-1][ROOM_SIZE-1] = 137;      // Coin bas-droite

        // Mur Nord (ligne 0) - SANS les coins
        for (int x = 1; x < ROOM_SIZE-1; x++) {
            walls[0][x] = WALL_N;
        }

        // Mur Sud (ligne ROOM_SIZE-1) - SANS les coins
        for (int x = 1; x < ROOM_SIZE-1; x++) {
            walls[ROOM_SIZE-1][x] = WALL_S;
        }

        // Mur Ouest (colonne 0) - SANS les coins
        for (int y = 1; y < ROOM_SIZE-1; y++) {
            walls[y][0] = WALL_W;
        }

        // Mur Est (colonne ROOM_SIZE-1) - SANS les coins
        for (int y = 1; y < ROOM_SIZE-1; y++) {
            walls[y][ROOM_SIZE-1] = WALL_E;
        }

        // Créer les portes (1 tuile de large au milieu seulement)
        int middle = ROOM_SIZE / 2;

        if (doorN) {
            walls[0][middle] = DOOR_N;
        }

        if (doorS) {
            walls[ROOM_SIZE-1][middle] = DOOR_S;
        }

        if (doorW) {
            walls[middle][0] = DOOR_W;
        }

        if (doorE) {
            walls[middle][ROOM_SIZE-1] = DOOR_E;
        }

        // Ajouter à la fenêtre tuile par tuile
        for (int y = 0; y < ROOM_SIZE; y++) {
            for (int x = 0; x < ROOM_SIZE; x++) {
                if (walls[y][x] != -1) {
                    window.add(walls[y][x], startX + x, startY + y, 0);
                }
            }
        }
    }

    /**
     * Crée les murs des couloirs entre les salles
     */
    private void createCorridorWalls(IWindowGame window) {
        int middle = ROOM_SIZE / 2;

        // Couloir Nord-Centre (vertical)
        int corridorX = centerX + middle;
        for (int y = northY + ROOM_SIZE; y < centerY; y++) {
            window.add(WALL_W, corridorX, y, 0);  // Mur gauche
            window.add(WALL_E, corridorX , y, 0);  // Mur droite
        }

        // Couloir Centre-Sud (vertical)
        for (int y = centerY + ROOM_SIZE; y < southY; y++) {
            window.add(WALL_W, corridorX , y, 0);  // Mur gauche
            window.add(WALL_E, corridorX , y, 0);  // Mur droite
        }

        // Couloir Ouest-Centre (horizontal)
        int corridorY = westY + middle;
        for (int x = westX + ROOM_SIZE; x < centerX; x++) {
            window.add(WALL_N, x, corridorY , 0);  // Mur haut
            window.add(WALL_S, x, corridorY , 0);  // Mur bas
        }

        // Couloir Centre-Est (horizontal)
        for (int x = centerX + ROOM_SIZE; x < eastX; x++) {
            window.add(WALL_N, x, corridorY , 0);  // Mur haut
            window.add(WALL_S, x, corridorY , 0);  // Mur bas
        }
    }

    /**
     * ✨ Vérifie si une position est un mur OU un obstacle bloquant
     */
    public boolean isWall(int x, int y) {
        // Vérifier d'abord les obstacles bloquants
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isBlocking() && obstacle.isAt(x, y)) {
                return true; // C'est un obstacle physique
            }
        }

        // Vérifier si c'est dans une salle
        if (isInRoom(x, y, centerX, centerY) ||
                isInRoom(x, y, northX, northY) ||
                isInRoom(x, y, southX, southY) ||
                isInRoom(x, y, westX, westY) ||
                isInRoom(x, y, eastX, eastY)) {

            return isBorder(x, y);
        }

        // Vérifier si c'est dans un couloir
        if (isInCorridor(x, y)) {
            return false; // Les couloirs sont walkable
        }

        // Sinon c'est du vide = mur
        return true;
    }

    private boolean isInRoom(int x, int y, int roomX, int roomY) {
        return x >= roomX && x < roomX + ROOM_SIZE &&
                y >= roomY && y < roomY + ROOM_SIZE;
    }

    private boolean isBorder(int x, int y) {
        return false;
    }

    private boolean isInCorridor(int x, int y) {
        int middle = ROOM_SIZE / 2;

        // Couloir Nord-Centre (1 tuile de large)
        if (x == centerX + middle &&
                y >= northY + ROOM_SIZE && y < centerY) return true;

        // Couloir Centre-Sud (1 tuile de large)
        if (x == centerX + middle &&
                y >= centerY + ROOM_SIZE && y < southY) return true;

        // Couloir Ouest-Centre (1 tuile de large)
        if (y == westY + middle &&
                x >= westX + ROOM_SIZE && x < centerX) return true;

        // Couloir Centre-Est (1 tuile de large)
        if (y == eastY + middle &&
                x >= centerX + ROOM_SIZE && x < eastX) return true;

        return false;
    }

    public boolean isWall(Position pos) {
        return isWall((int)pos.getX(), (int)pos.getY());
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < totalWidth && y >= 0 && y < totalHeight;
    }

    public boolean isInBounds(Position pos) {
        return isInBounds((int)pos.getX(), (int)pos.getY());
    }

    // ✨ Vérifie si le joueur est à la porte de sortie
    public boolean isAtExitDoor(Position playerPos) {
        return exitDoor.getPosition().distanceTo(playerPos) < 0.5f;
    }

    /**
     * Position de départ (centre de la salle centrale)
     */
    public Position getStartPosition() {
        return new Position(centerX + ROOM_SIZE/2, centerY + ROOM_SIZE/2, 0);
    }

    /**
     * Position dans une salle spécifique
     */
    public Position getPositionInRoom(int roomId) {
        switch (roomId) {
            case NORTH_ROOM: return new Position(northX + ROOM_SIZE/2, northY + ROOM_SIZE/2, 0);
            case SOUTH_ROOM: return new Position(southX + ROOM_SIZE/2, southY + ROOM_SIZE/2, 0);
            case WEST_ROOM: return new Position(westX + ROOM_SIZE/2, westY + ROOM_SIZE/2, 0);
            case EAST_ROOM: return new Position(eastX + ROOM_SIZE/2, eastY + ROOM_SIZE/2, 0);
            default: return getStartPosition();
        }
    }

    // Getters
    public Door getExitDoor() { return exitDoor; }
    public Position getExitDoorPosition() { return exitDoor.getPosition(); }
    public List<KeyFragment> getKeyFragments() { return keyFragments; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public int getTotalWidth() { return totalWidth; }
    public int getTotalHeight() { return totalHeight; }
    public List<Item> getItems() { return items; }
}