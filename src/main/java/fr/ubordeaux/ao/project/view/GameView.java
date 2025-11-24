package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.items.Item;


public class GameView {
    private IWindowGame window;
    private Game game;
    private Crusader crusader;
    private fr.ubordeaux.ao.mazing.api.Spider spiderView;

    public GameView(Game game) {
        this.window = new WindowGame();
        this.game = game;

        // Créer le personnage du joueur
        crusader = new Crusader();
        crusader.setPosition(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY(),
                game.getPlayer().getPosition().getZ()
        );

        // Créer l'araignée visuelle
        spiderView = new fr.ubordeaux.ao.mazing.api.Spider();
        spiderView.setPosition(
                game.getSpider().getPosition().getX(),
                game.getSpider().getPosition().getY(),
                game.getSpider().getPosition().getZ()
        );

        window.add(crusader);
        window.add(spiderView);

        renderMaze();
        window.setVisible(true);
    }

    public IWindowGame getWindow() {
        return window;
    }

    public void renderMaze() {
        if (game.getMaze() == null) return;

        int width = game.getMaze().getTotalWidth();
        int height = game.getMaze().getTotalHeight();

        // Remplir TOUTE la zone visible avec du sol (même en dehors du maze)
        int extraSpace = 5; // Espace supplémentaire autour du maze
        for (int x = -extraSpace; x < width + extraSpace; x++) {
            for (int y = -extraSpace; y < height + extraSpace; y++) {
                window.add(72, x, y, 0); // Sol partout
            }
        }

        // Dessiner les murs par-dessus
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int code = game.getMaze().getTileCode(x, y);

                if (code >= 100) {
                    // Murs
                    window.add(code, x, y, 0);
                }
            }
        }

        // Dessiner les objets décoratifs
        for (Item item : game.getMaze().getItems()) {
            window.add(
                    item.getCode(),
                    (int) item.getPosition().getX(),
                    (int) item.getPosition().getY(),
                    (int) item.getPosition().getZ()
            );
        }

        System.out.println("Labyrinthe affiché: " + width + "x" + height + " tuiles");
    }

    public void updatePlayerView() {
        crusader.setPosition(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY(),
                game.getPlayer().getPosition().getZ()
        );
    }

    public void updateSpiderView() {
        spiderView.setPosition(
                game.getSpider().getPosition().getX(),
                game.getSpider().getPosition().getY(),
                game.getSpider().getPosition().getZ()
        );
    }
}