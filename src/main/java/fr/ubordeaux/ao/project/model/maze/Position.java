package fr.ubordeaux.ao.project.model.maze;

/**
 * Représente une position dans le labyrinthe (coordonnées 3D pour Mazing)
 */
public class Position {
    private float x;
    private float y;
    private float z;

    public Position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(float x, float y) {
        this(x, y, 0);
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    // Setters
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }

    /**
     * Calcule la distance avec une autre position
     */
    public float distanceTo(Position other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Crée une copie de cette position
     */
    public Position copy() {
        return new Position(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("Position(%.1f, %.1f, %.1f)", x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return Float.compare(x, other.x) == 0
                && Float.compare(y, other.y) == 0
                && Float.compare(z, other.z) == 0;
    }
}