package fr.ubordeaux.ao.project.model.entities;


import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.mazing.api.Direction;
import java.util.UUID;

/**
 * Classe abstraite représentant toute entité du jeu
 * (Pattern: Template Method)
 */
public abstract class Entity {
    protected UUID id;
    protected Position position;
    protected Direction direction;
    protected boolean isActive;

    public Entity(Position position) {
        this.id = UUID.randomUUID();
        this.position = position.copy();
        this.direction = Direction.SOUTH;
        this.isActive = true;
    }

    // Getters/Setters
    public UUID getId() { return id; }
    public Position getPosition() { return position; }
    public Direction getDirection() { return direction; }
    public boolean isActive() { return isActive; }

    public void setPosition(Position position) {
        this.position = position.copy();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    /**
     * Méthode abstraite pour la mise à jour (à implémenter par les sous-classes)
     */
    public abstract void update(float deltaTime);

    /**
     * Vérifie la collision avec une autre entité
     */
    public boolean collidesWith(Entity other) {
        return this.position.distanceTo(other.position) < 1.0f;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + id.toString().substring(0, 8) + "]";
    }
}