package fr.ubordeaux.ao.project.model.maze;

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

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }

    public float distanceTo(Position other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public Position copy() {
        return new Position(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("Position(%.1f, %.1f, %.1f)", x, y, z);
    }
}