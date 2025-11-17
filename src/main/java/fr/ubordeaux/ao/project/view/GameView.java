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

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int code = game.getMaze().getTileCode(x, y);

                if (code >= 100) {
                    // C'est un mur - afficher le mur
                    window.add(code, x, y, 0);
                } else {
                    // C'est du sol - effet damier
                    int floorCode = ((x + y) % 2 == 0) ? 0 : 1;
                    window.add(floorCode, x, y, 0);
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