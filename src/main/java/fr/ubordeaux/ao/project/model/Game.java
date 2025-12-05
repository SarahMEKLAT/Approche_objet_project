package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.maze.Maze;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.project.model.entities.Player;
import fr.ubordeaux.ao.project.model.entities.Spider;
import fr.ubordeaux.ao.project.model.states.*;

/**
 * Classe principale du jeu
 * Gère le labyrinthe, le joueur, les ennemis et les états
 * Pattern: State (pour gérer les différents états du jeu)
 */
public class Game {
    private Maze maze;
    private Player player;
    private Spider spider;
    private int score;

    // Pattern State
    private GameState currentState;
    private MenuState menuState;
    private PlayingState playingState;
    private VictoryState victoryState;
    private GameOverState gameOverState;

    public Game() {
        this.score = 0;

        // Initialiser les états
        this.menuState = new MenuState(this);
        this.playingState = new PlayingState(this);
        this.victoryState = new VictoryState(this);
        this.gameOverState = new GameOverState(this);

        // Commencer au menu
        changeState(menuState);

        // Initialiser directement le jeu (pour le test)
        initialize();
        changeState(playingState);
    }

    /**
     * Change l'état du jeu (Pattern State)
     */
    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        currentState.enter();
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
     * Redémarre le jeu
     */
    public void restart() {
        score = 0;
        initialize();
        changeState(playingState);
    }

    /**
     * Déplace le joueur
     */
    public boolean movePlayer(int dx, int dy) {
        // Ne pas bouger si pas en jeu
        if (!(currentState instanceof PlayingState)) {
            return false;
        }

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
     * Pour l'instant : arriver à la salle Sud (room 4)
     */
    public void checkVictoryCondition() {
        if (!(currentState instanceof PlayingState)) {
            return;
        }

        Position playerPos = player.getPosition();
        Position southRoom = maze.getPositionInRoom(Maze.SOUTH_ROOM);

        // Si le joueur est dans la salle Sud (approximation)
        if (Math.abs(playerPos.getX() - southRoom.getX()) < 3 &&
                Math.abs(playerPos.getY() - southRoom.getY()) < 3) {
            changeState(victoryState);
        }
    }

    /**
     * Vérifie si le joueur a perdu
     */
    public void checkGameOverCondition() {
        if (!(currentState instanceof PlayingState)) {
            return;
        }

        if (player.isDead()) {
            changeState(gameOverState);
        }
    }

    /**
     * Mise à jour du jeu (appelé chaque frame)
     */
    public void update(float deltaTime) {
        if (currentState != null) {
            currentState.update(deltaTime);
        }
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
    public GameState getCurrentState() { return currentState; }

    // Vérifications d'état
    public boolean isGameOver() { return currentState instanceof GameOverState; }
    public boolean isVictory() { return currentState instanceof VictoryState; }
    public boolean isPlaying() { return currentState instanceof PlayingState; }
}