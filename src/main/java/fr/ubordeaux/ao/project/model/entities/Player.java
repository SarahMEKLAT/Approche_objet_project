package fr.ubordeaux.ao.project.model.entities;

import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.mazing.api.Crusader;
import java.util.Observable;

/**
 * Représente le joueur (Crusader)
 * Pattern: Observer (extends Observable pour notifier la vue)
 */
public class Player extends Observable {
    private LivingEntity entity;
    private Crusader crusader; // Vue Mazing

    private int stamina;
    private int maxStamina;
    private int keyFragments;

    private boolean isRunning;
    private boolean isAttacking;

    private static final int MAX_HEALTH = 100;
    private static final int MAX_STAMINA = 100;
    private static final float MOVE_SPEED = 1.0f;
    private static final float RUN_SPEED = 2.0f;
    private static final int ATTACK_DAMAGE = 25;

    public Player(Position startPosition) {
        this.entity = new PlayerEntity(startPosition, MAX_HEALTH);
        this.crusader = new Crusader();

        this.stamina = MAX_STAMINA;
        this.maxStamina = MAX_STAMINA;
        this.keyFragments = 0;

        this.isRunning = false;
        this.isAttacking = false;

        // Initialiser la position du crusader graphique
        crusader.setPosition(startPosition.getX(), startPosition.getY(), startPosition.getZ());
        crusader.setMode(Crusader.Mode.IDLE);
    }

    // Getters
    public Position getPosition() { return entity.getPosition(); }
    public int getHealth() { return entity.getHealth(); }
    public int getMaxHealth() { return MAX_HEALTH; }
    public int getStamina() { return stamina; }
    public int getMaxStamina() { return maxStamina; }
    public int getKeyFragments() { return keyFragments; }
    public boolean isDead() { return entity.isDead(); }
    public Crusader getCrusader() { return crusader; }

    /**
     * Définit la position du joueur (NOUVELLE MÉTHODE)
     */
    public void setPosition(Position newPosition) {
        entity.setPosition(newPosition);
        crusader.setPosition(newPosition.getX(), newPosition.getY(), newPosition.getZ());
        setChanged();
        notifyObservers();
    }

    /**
     * Déplace le joueur
     */
    public void move(Position newPosition) {
        entity.setPosition(newPosition);
        crusader.setPosition(newPosition.getX(), newPosition.getY(), newPosition.getZ());

        // Animation
        if (isRunning) {
            crusader.setMode(Crusader.Mode.RUN);
        } else {
            crusader.setMode(Crusader.Mode.WALK);
        }

        notifyObservers();
    }

    /**
     * Active/désactive la course
     */
    public void setRunning(boolean running) {
        if (running && stamina > 0) {
            isRunning = true;
        } else {
            isRunning = false;
        }
    }

    /**
     * Attaque
     */
    public void attack() {
        if (stamina >= 20) {
            isAttacking = true;
            stamina -= 20;
            crusader.setMode(Crusader.Mode.ATTACK);
            // TODO: Détection collision avec ennemis
        }
    }

    /**
     * Ramasse un fragment de clé
     */
    public void collectKeyFragment() {
        keyFragments++;
        setChanged();
        notifyObservers("KEY_COLLECTED");
    }

    /**
     * Utilise une potion
     */
    public void useHealthPotion(int healAmount) {
        entity.heal(healAmount);
        setChanged();
        notifyObservers("HEALTH_CHANGED");
    }

    /**
     * Mise à jour du joueur (chaque frame)
     */
    public void update(float deltaTime) {
        // Régénération de l'endurance
        if (!isRunning && stamina < maxStamina) {
            stamina += (int)(5 * deltaTime); // +5 par seconde
            if (stamina > maxStamina) stamina = maxStamina;
        }

        // Consommation d'endurance en courant
        if (isRunning) {
            stamina -= (int)(10 * deltaTime); // -10 par seconde
            if (stamina <= 0) {
                stamina = 0;
                isRunning = false;
            }
        }

        // Reset attaque
        if (isAttacking) {
            isAttacking = false;
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Classe interne pour l'entité joueur
     */
    private class PlayerEntity extends LivingEntity {
        public PlayerEntity(Position position, int maxHealth) {
            super(position, maxHealth);
        }

        @Override
        protected void onDeath() {
            crusader.setMode(Crusader.Mode.DEATH);
            System.out.println("Player died!");
        }

        @Override
        public void update(float deltaTime) {
            // Géré par Player
        }
    }
}