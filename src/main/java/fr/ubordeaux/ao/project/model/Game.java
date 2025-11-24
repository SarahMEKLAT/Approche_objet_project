package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.maze.Maze;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.project.model.entities.Player;
import fr.ubordeaux.ao.project.model.entities.Spider;

public class Game {
    private Maze maze;
    private Player player;
    private Spider spider;

    public Game() {
        this.maze = new Maze(2, 2);  // 2x2 = 4 salles
        Position startPos = new Position(2, 2, 0);
        this.player = new Player(startPos);

        // Araignée dans une autre salle (coin opposé)
        this.spider = new Spider(7, 7, 0);

        System.out.println("Jeu initialisé");
    }

    public Maze getMaze() {
        return maze;
    }

    public Player getPlayer() {
        return player;
    }

    public Spider getSpider() {
        return spider;
    }

    public boolean movePlayer(int dx, int dy) {
        Position currentPos = player.getPosition();
        Position newPos = new Position(
                currentPos.getX() + dx,
                currentPos.getY() + dy,
                currentPos.getZ()
        );

        if (!maze.isWall(newPos) && maze.isInBounds(newPos)) {
            player.setPosition(newPos);
            System.out.println("Joueur déplacé vers: " + newPos);
            return true;
        }
        System.out.println("Mouvement impossible - Mur ou hors limites");
        return false;
    }

    public void moveSpider() {
        Position newPos = spider.getRandomMove();
        if (!maze.isWall(newPos) && maze.isInBounds(newPos)) {
            spider.setPosition(newPos);
        }
    }
}