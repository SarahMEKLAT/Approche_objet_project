package fr.ubordeaux.ao.project.model.entities;



import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Entité vivante avec points de vie
 */
public abstract class LivingEntity extends Entity {
    protected int health;
    protected int maxHealth;
    protected boolean isDead;

    public LivingEntity(Position position, int maxHealth) {
        super(position);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.isDead = false;
    }

    // Getters
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isDead() { return isDead; }

    /**
     * Inflige des dégâts à l'entité
     */
    public void takeDamage(int damage) {
        if (isDead) return;

        health -= damage;
        if (health <= 0) {
            health = 0;
            die();
        }
    }

    /**
     * Soigne l'entité
     */
    public void heal(int amount) {
        if (isDead) return;

        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Gère la mort de l'entité
     */
    protected void die() {
        isDead = true;
        isActive = false;
        onDeath();
    }

    /**
     * Hook pour actions spécifiques à la mort (Pattern: Template Method)
     */
    protected abstract void onDeath();

    /**
     * Vérifie si l'entité est vivante
     */
    public boolean isAlive() {
        return !isDead && health > 0;
    }
}