package fr.ubordeaux.ao.project.model;



import fr.ubordeaux.ao.project.model.entities.Player;
import fr.ubordeaux.ao.project.model.entities.Spider;
import fr.ubordeaux.ao.project.model.maze.Maze;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.project.model.states.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Classe principale du modèle - Gère l'état global du jeu
 * Pattern: Singleton + State
 */
public class Game extends Observable {
    private static Game instance;

    private Maze maze;
    private Player player;
    private List<Spider> spiders;

    private GameState currentState;
    private MenuState menuState;
    private PlayingState playingState;
    private VictoryState victoryState;
    private GameOverState gameOverState;

    private int score;
    private float elapsedTime;

    private Game() {
        this.spiders = new ArrayList<>();
        this.score = 0;
        this.elapsedTime = 0;

        // Initialiser les états
        this.menuState = new MenuState(this);
        this.playingState = new PlayingState(this);
        this.victoryState = new VictoryState(this);
        this.gameOverState = new GameOverState(this);

        this.currentState = menuState;
    }

    /**
     * Pattern Singleton
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Initialise une nouvelle partie
     */
    public void startNewGame() {
        System.out.println("=== Nouvelle partie ===");

        // Créer le labyrinthe
        this.maze = new Maze(2, 2);  // Très petit : 2x2 salles = 10x10 tuiles
        Position startPos = new Position(2, 2, 0);  // Centre
        this.player = new Player(startPos);

        // TODO: Créer les araignées
        // TODO: Placer les objets

        // Changer d'état
        changeState(playingState);

        setChanged();
        notifyObservers("GAME_STARTED");
    }

    /**
     * Pattern State - Change l'état du jeu
     */
    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        currentState.enter();

        setChanged();
        notifyObservers("STATE_CHANGED");
    }

    /**
     * Met à jour le jeu (appelé chaque frame)
     */
    public void update(float deltaTime) {
        if (currentState != null) {
            currentState.update(deltaTime);
        }

        elapsedTime += deltaTime;
    }

    /**
     * Vérifie les conditions de victoire
     */
    public void checkVictoryCondition() {
        if (player.getKeyFragments() >= 3) {
            // TODO: Vérifier si à la sortie
            changeState(victoryState);
        }
    }

    /**
     * Vérifie les conditions de défaite
     */
    public void checkGameOverCondition() {
        if (player.isDead()) {
            changeState(gameOverState);
        }
    }

    // Getters
    public Maze getMaze() { return maze; }
    public Player getPlayer() { return player; }
    public List<Spider> getSpiders() { return spiders; }
    public GameState getCurrentState() { return currentState; }
    public int getScore() { return score; }
    public float getElapsedTime() { return elapsedTime; }

    public void addScore(int points) {
        this.score += points;
        setChanged();
        notifyObservers("SCORE_CHANGED");
    }
}