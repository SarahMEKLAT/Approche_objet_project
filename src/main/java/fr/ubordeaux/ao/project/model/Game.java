package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.maze.Maze;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.project.model.entities.Player;
import fr.ubordeaux.ao.project.model.entities.Spider;

/**
 * Classe principale du jeu
 * Gère le labyrinthe, le joueur et les ennemis
 */
public class Game {
    private Maze maze;
    private Player player;
    private Spider spider;
    private int score;
    private boolean isGameOver;
    private boolean isVictory;

    public Game() {
        this.score = 0;
        this.isGameOver = false;
        this.isVictory = false;
        initialize();
    }

    /**
     * Initialise une nouvelle partie
     */
    public void initialize() {
        // Créer le labyrinthe
        maze = new Maze();


        // Créer le joueur au centre
        Position startPos = maze.getStartPosition();
        player = new Player(startPos);

        // Créer l'araignée dans un coin (pour le test)
        Position spiderPos = new Position(3, 3, 0);
        spider = new Spider(spiderPos.getX(), spiderPos.getY(), spiderPos.getZ());

        System.out.println("Jeu initialisé !");
        System.out.println("Joueur en : " + startPos);
        System.out.println("Araignée en : " + spiderPos);
    }

    /**
     * Déplace le joueur
     */
    public boolean movePlayer(int dx, int dy) {
        Position currentPos = player.getPosition();
        Position newPos = new Position(
                currentPos.getX() + dx,
                currentPos.getY() + dy,
                currentPos.getZ()
        );

        // Vérifier collision
        if (maze.isInBounds(newPos) && !maze.isWall(newPos)) {
            player.move(newPos);
            System.out.println("Joueur déplacé vers : " + newPos);
            return true;
        }

        System.out.println("Mouvement bloqué (mur)");
        return false;
    }

    /**
     * Déplace l'araignée aléatoirement
     */
    public void moveSpider() {
        Position newPos = spider.getRandomMove();

        // Vérifier collision
        if (maze.isInBounds(newPos) && !maze.isWall(newPos)) {
            spider.setPosition(newPos);
            System.out.println("Araignée déplacée vers : " + newPos);
        }
    }

    /**
     * Vérifie si le joueur a gagné
     */
    public void checkVictoryCondition() {
        // Pour le test : pas de condition de victoire
        // On pourra ajouter ça plus tard
    }

    /**
     * Vérifie si le joueur a perdu
     */
    public void checkGameOverCondition() {
        if (player.isDead()) {
            isGameOver = true;
            System.out.println("💀 GAME OVER !");
        }
    }

    /**
     * Mise à jour du jeu (appelé chaque frame)
     */
    public void update(float deltaTime) {
        if (isGameOver || isVictory) return;

        player.update(deltaTime);

        checkVictoryCondition();
        checkGameOverCondition();
    }

    /**
     * Ajoute des points au score
     */
    public void addScore(int points) {
        score += points;
        System.out.println("Score : " + score);
    }

    // Getters
    public Maze getMaze() { return maze; }
    public Player getPlayer() { return player; }
    public Spider getSpider() { return spider; }
    public int getScore() { return score; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isVictory() { return isVictory; }
}