package fr.ubordeaux.ao.project.model.entities;

import fr.ubordeaux.ao.project.model.maze.Position;
import java.util.Random;
import java.util.UUID;

/**
 * Araignée ennemie avec IA basique
 * Pattern: Entity (hérite conceptuellement de LivingEntity)
 */
public class Spider {
    private UUID id;
    private Position position;
    private int health;
    private int maxHealth;
    private boolean isDead;
    private Random random;

    // Comportement IA
    private static final float MOVE_SPEED = 0.8f;
    private static final int ATTACK_DAMAGE = 15;
    private static final float ATTACK_RANGE = 1.0f;

    public Spider(float x, float y, float z) {
        this.id = UUID.randomUUID();
        this.position = new Position(x, y, z);
        this.maxHealth = 50;
        this.health = maxHealth;
        this.isDead = false;
        this.random = new Random();
    }

    // Getters
    public UUID getId() { return id; }
    public Position getPosition() { return position; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isDead() { return isDead; }

    // Setters
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Inflige des dégâts à l'araignée
     */
    public void takeDamage(int damage) {
        if (isDead) return;

        health -= damage;
        System.out.println("🕷️ Araignée [" + id.toString().substring(0, 8) +
                "] : -" + damage + " PV (" + health + "/" + maxHealth + ")");

        if (health <= 0) {
            health = 0;
            die();
        }
    }

    /**
     * Tue l'araignée
     */
    private void die() {
        isDead = true;
        System.out.println("💀 Araignée [" + id.toString().substring(0, 8) + "] est morte !");
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

    /**
     * IA : Se déplace vers une cible (joueur)
     * Retourne la nouvelle position souhaitée
     */
    public Position moveTowards(Position target) {
        if (isDead) return position.copy();

        float dx = target.getX() - position.getX();
        float dy = target.getY() - position.getY();

        // Normaliser et se déplacer dans la direction du joueur
        float moveX = 0;
        float moveY = 0;

        if (Math.abs(dx) > Math.abs(dy)) {
            // Se déplacer horizontalement
            moveX = dx > 0 ? 1 : -1;
        } else {
            // Se déplacer verticalement
            moveY = dy > 0 ? 1 : -1;
        }

        return new Position(
                position.getX() + moveX,
                position.getY() + moveY,
                position.getZ()
        );
    }

    /**
     * Vérifie si le joueur est à portée d'attaque
     */
    public boolean canAttack(Position targetPos) {
        return !isDead && position.distanceTo(targetPos) <= ATTACK_RANGE;
    }

    /**
     * Attaque le joueur
     */
    public int attack() {
        if (isDead) return 0;
        System.out.println("🕷️ Araignée attaque ! (-" + ATTACK_DAMAGE + " PV)");
        return ATTACK_DAMAGE;
    }

    /**
     * Mise à jour de l'araignée (appelée chaque frame)
     */
    public void update(float deltaTime) {
        // Pour l'instant rien, l'IA est gérée dans Game
    }

    @Override
    public String toString() {
        return "Spider[" + id.toString().substring(0, 8) + "] " +
                (isDead ? "💀" : "❤️ " + health + "/" + maxHealth);
    }
}