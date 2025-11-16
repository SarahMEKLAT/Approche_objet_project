package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.WindowGame;

public class GameView {
    private IWindowGame window;
    private Game game;

    public GameView() {
        this.window = new WindowGame();
    }

    public void initialize(Game game) {
        this.game = game;
        System.out.println("Vue initialisée");
    }

    public void renderMaze() {
        if (game.getMaze() == null) return;

        int width = game.getMaze().getTotalWidth();
        int height = game.getMaze().getTotalHeight();

        // Dessiner le sol ET les murs
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (game.getMaze().isWall(x, y)) {
                    // Mur : code 100 = mur simple
                    window.add(100, x, y, 0);
                } else {
                    // Sol : code 1 = dalle
                    window.add(1, x, y, 0);
                }
            }
        }
        System.out.println("Labyrinthe affiché: " + width + "x" + height + " tuiles");
    }

    public void renderPlayer() {
        if (game.getPlayer() != null) {
            window.add(game.getPlayer().getCrusader());
            System.out.println("Joueur ajouté à la scène");
        }
    }

    public void render() {
        window.clear();
        renderMaze();
        renderPlayer();

    }

    public void show() {
        window.setVisible(true);
    }

    public IWindowGame getWindow() {
        return window;
    }
}