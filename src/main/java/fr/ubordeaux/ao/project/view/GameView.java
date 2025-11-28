package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.mazing.api.Spider;
import fr.ubordeaux.ao.project.model.Game;

/**
 * Vue du jeu avec Mazing
 * Version simple et fonctionnelle
 */
public class GameView {
    private IWindowGame window;
    private Game game;
    private Crusader crusader;
    private Spider spiderView;

    public GameView(Game game) {
        this.window = new WindowGame();
        this.game = game;

        // Tuiles plus petites pour voir plus de labyrinthe
        window.setTileSize(70);

        // Créer le personnage du joueur
        crusader = new Crusader();
        crusader.setPosition(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY(),
                game.getPlayer().getPosition().getZ()
        );

        // Créer l'araignée visuelle
        spiderView = new Spider();
        spiderView.setPosition(
                game.getSpider().getPosition().getX(),
                game.getSpider().getPosition().getY(),
                game.getSpider().getPosition().getZ()
        );

        // Ajouter les personnages
        window.add(crusader);
        window.add(spiderView);

        // Afficher le labyrinthe
        renderMaze();

        // Afficher la fenêtre
        window.setVisible(true);
    }

    public IWindowGame getWindow() {
        return window;
    }

    /**
     * Affiche le labyrinthe complet
     */
    public void renderMaze() {
        // Utiliser la méthode render() du Maze (plus efficace)
        game.getMaze().render(window);

        System.out.println("Labyrinthe affiché : " +
                game.getMaze().getTotalWidth() + "x" +
                game.getMaze().getTotalHeight());
    }

    /**
     * Met à jour la position du joueur à l'écran
     */
    public void updatePlayerView() {
        crusader.setPosition(
                game.getPlayer().getPosition().getX(),
                game.getPlayer().getPosition().getY(),
                game.getPlayer().getPosition().getZ()
        );
    }

    /**
     * Met à jour la position de l'araignée à l'écran
     */
    public void updateSpiderView() {
        spiderView.setPosition(
                game.getSpider().getPosition().getX(),
                game.getSpider().getPosition().getY(),
                game.getSpider().getPosition().getZ()
        );
    }
}