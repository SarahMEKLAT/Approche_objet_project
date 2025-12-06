package fr.ubordeaux.ao.project.model.maze;

/**
 * Représente un obstacle physique dans le labyrinthe
 * (pilier, caisse, tonneau, etc.)
 */
public class Obstacle {
    private Position position;
    private int code;           // Code Mazing de l'objet
    private String type;        // Type d'obstacle (pour debug)
    private boolean blocking;   // Est-ce que ça bloque le passage ?

    public Obstacle(Position position, int code, String type, boolean blocking) {
        this.position = position;
        this.code = code;
        this.type = type;
        this.blocking = blocking;
    }

    public Position getPosition() {
        return position;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public boolean isBlocking() {
        return blocking;
    }

    /**
     * Vérifie si une position correspond à cet obstacle
     */
    public boolean isAt(int x, int y) {
        return (int)position.getX() == x && (int)position.getY() == y;
    }
}