package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.maze.Maze;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.project.model.entities.Player;
import fr.ubordeaux.ao.project.model.entities.Spider;
import fr.ubordeaux.ao.project.model.items.KeyFragment;
import fr.ubordeaux.ao.project.model.states.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe principale du jeu
 * Pattern: State
 */
public class Game {
    private Maze maze;
    private Player player;
    private List<Spider> spiders;
    private int score;

    // Pattern State
    private GameState currentState;
    private MenuState menuState;
    private PlayingState playingState;
    private VictoryState victoryState;
    private GameOverState gameOverState;

    public Game() {
        this.score = 0;
        this.spiders = new ArrayList<>();

        this.menuState = new MenuState(this);
        this.playingState = new PlayingState(this);
        this.victoryState = new VictoryState(this);
        this.gameOverState = new GameOverState(this);

        changeState(menuState);
        initialize();
        changeState(playingState);
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        currentState.enter();
    }

    public void initialize() {
        maze = new Maze();
        Position startPos = maze.getStartPosition();
        player = new Player(startPos);
        spiders.clear();
        spawnSpiders();

        System.out.println("Jeu initialisé !");
        System.out.println("Joueur en : " + startPos);
        System.out.println("Nombre d'araignées : " + spiders.size());
    }

    private void spawnSpiders() {
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.NORTH_ROOM).getX() + 2,
                maze.getPositionInRoom(Maze.NORTH_ROOM).getY() + 2, 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.NORTH_ROOM).getX() - 2,
                maze.getPositionInRoom(Maze.NORTH_ROOM).getY() - 2, 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.EAST_ROOM).getX() + 3,
                maze.getPositionInRoom(Maze.EAST_ROOM).getY(), 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.EAST_ROOM).getX() - 3,
                maze.getPositionInRoom(Maze.EAST_ROOM).getY(), 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.WEST_ROOM).getX(),
                maze.getPositionInRoom(Maze.WEST_ROOM).getY() + 3, 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.SOUTH_ROOM).getX(),
                maze.getPositionInRoom(Maze.SOUTH_ROOM).getY() + 2, 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.SOUTH_ROOM).getX() + 2,
                maze.getPositionInRoom(Maze.SOUTH_ROOM).getY() - 2, 0));
        spiders.add(new Spider(
                maze.getPositionInRoom(Maze.SOUTH_ROOM).getX() - 2,
                maze.getPositionInRoom(Maze.SOUTH_ROOM).getY() - 2, 0));

        System.out.println("🕷️ " + spiders.size() + " araignées spawned");
    }

    public void restart() {
        score = 0;
        initialize();
        changeState(playingState);
    }

    public boolean movePlayer(int dx, int dy) {
        if (!(currentState instanceof PlayingState)) {
            return false;
        }

        Position currentPos = player.getPosition();
        Position newPos = new Position(
                currentPos.getX() + dx,
                currentPos.getY() + dy,
                currentPos.getZ()
        );

        if (maze.isInBounds(newPos) && !maze.isWall(newPos)) {
            player.move(newPos, dx, dy);

            //  Vérifier la collecte automatique des KeyFragments après chaque mouvement
            checkItemCollection();

            return true;
        }
        return false;
    }

    /**
     * ✨ NOUVEAU : Vérifie et collecte automatiquement les KeyFragments proches
     */
    private void checkItemCollection() {
        Position playerPos = player.getPosition();

        // Parcourir tous les KeyFragments
        for (KeyFragment fragment : maze.getKeyFragments()) {
            // Si pas encore collecté ET proche du joueur (distance < 1.0 tuile)
            if (fragment.canCollect(playerPos)) {
                fragment.collect();              // Marquer comme collecté
                player.collectKeyFragment();     // Ajouter au compteur du joueur
                addScore(100);                   // Bonus de 100 points
            }
        }
    }

    public void updateSpiders(float deltaTime) {
        if (!(currentState instanceof PlayingState)) {
            return;
        }

        Position playerPos = player.getPosition();
        Iterator<Spider> iterator = spiders.iterator();

        while (iterator.hasNext()) {
            Spider spider = iterator.next();
            if (spider.isDead()) continue;

            if (spider.canAttack(playerPos)) {
                int damage = spider.attack();
                player.takeDamage(damage);
            }

            float distance = spider.getPosition().distanceTo(playerPos);
            Position newPos;

            if (distance < 7.0f) {
                newPos = spider.moveTowards(playerPos);
            } else {
                newPos = spider.getRandomMove();
            }

            if (maze.isInBounds(newPos) && !maze.isWall(newPos)) {
                spider.setPosition(newPos);
            }
        }
    }

    /**
     * ✨ Victoire AUTOMATIQUE : toucher la porte (si 3 fragments collectés)
     */
    public void checkVictoryCondition() {
        if (!(currentState instanceof PlayingState)) {
            return;
        }

        // Vérifier si le joueur est proche de la porte
        if (maze.isAtExitDoor(player.getPosition())) {
            // Essayer d'ouvrir la porte (vérifie automatiquement le nombre de fragments)
            if (maze.getExitDoor().tryOpen(player.getKeyFragments())) {
                System.out.println("🚪 Vous avez atteint la porte de sortie !");
                changeState(victoryState);
            }
        }
    }

    public void checkGameOverCondition() {
        if (!(currentState instanceof PlayingState)) {
            return;
        }

        if (player.isDead()) {
            changeState(gameOverState);
        }
    }

    public void update(float deltaTime) {
        if (currentState != null) {
            currentState.update(deltaTime);
        }
    }

    public void addScore(int points) {
        score += points;
    }

    public Spider getClosestSpider(Position pos, float maxDistance) {
        Spider closest = null;
        float minDistance = maxDistance;

        for (Spider spider : spiders) {
            if (spider.isDead()) continue;

            float distance = spider.getPosition().distanceTo(pos);
            if (distance < minDistance) {
                minDistance = distance;
                closest = spider;
            }
        }

        return closest;
    }

    public Maze getMaze() { return maze; }
    public Player getPlayer() { return player; }
    public List<Spider> getSpiders() { return spiders; }
    public int getScore() { return score; }
    public GameState getCurrentState() { return currentState; }

    public boolean isGameOver() { return currentState instanceof GameOverState; }
    public boolean isVictory() { return currentState instanceof VictoryState; }
    public boolean isPlaying() { return currentState instanceof PlayingState; }
}