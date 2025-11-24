package fr.ubordeaux.ao.project.model.entities;

import fr.ubordeaux.ao.project.model.maze.Position;
import java.util.Random;

public class Spider {
    private Position position;
    private Random random;

    public Spider(float x, float y, float z) {
        this.position = new Position(x, y, z);
        this.random = new Random();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Retourne une position aléatoire autour de l'araignée
     */
    public Position getRandomMove() {
        int direction = random.nextInt(4);
        float newX = position.getX();
        float newY = position.getY();

        switch (direction) {
            case 0 -> newX++; // Droite
            case 1 -> newX--; // Gauche
            case 2 -> newY++; // Bas
            case 3 -> newY--; // Haut
        }

        return new Position(newX, newY, position.getZ());
    }
}