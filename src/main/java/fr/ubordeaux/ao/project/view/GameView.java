package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.mazing.api.Spider;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.maze.Position;

public class GameView {
    private IWindowGame window;
    private Game game;
    private Crusader crusader;
    private Spider spiderView;

    public GameView(Game game) {
        this.window = new WindowGame();
        this.game = game;

        // Tuiles de taille raisonnable pour voir tout le labyrinthe
        window.setTileSize(60);

        // 🎯 CENTRER LE LABYRINTHE
        // Le labyrinthe fait ~32x32 tuiles
        // On veut le centrer dans la fenêtre
        int mazeWidth = game.getMaze().getTotalWidth();
        int mazeHeight = game.getMaze().getTotalHeight();

        // Calculer le décalage pour centrer (valeurs négatives = décaler vers le haut/gauche)
        // Formule : on veut que le centre du labyrinthe soit au centre de l'écran
        int scrollX = -6;
        int scrollY = -9;

        window.scroll(scrollX, scrollY);

        System.out.println("📐 Scroll appliqué : (" + scrollX + ", " + scrollY + ")");

        // Créer le personnage du joueur
        crusader = new Crusader();
        Position startPos = game.getPlayer().getPosition();
        crusader.setPosition(
                startPos.getX(),
                startPos.getY(),
                startPos.getZ()
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

    public void renderMaze() {
        game.getMaze().render(window);

        System.out.println("Labyrinthe affiché : " +
                game.getMaze().getTotalWidth() + "x" +
                game.getMaze().getTotalHeight());
    }

    /**
     * Met à jour la position du joueur à l'écran
     */
    public void updatePlayerView() {
        Position currentPos = game.getPlayer().getPosition();

        // Mettre à jour simplement la position du crusader
        crusader.setPosition(
                currentPos.getX(),
                currentPos.getY(),
                currentPos.getZ()
        );

        // Pas de scroll dynamique - tout est visible !
    }

    public void updateSpiderView() {
        spiderView.setPosition(
                game.getSpider().getPosition().getX(),
                game.getSpider().getPosition().getY(),
                game.getSpider().getPosition().getZ()
        );
    }
}