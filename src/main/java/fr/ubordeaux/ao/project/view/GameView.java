package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.mazing.api.Spider;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.maze.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameView {
    private IWindowGame window;
    private Game game;
    private Crusader crusader;

    // Map pour associer chaque araignée du modèle à sa vue
    private Map<UUID, Spider> spiderViews;

    public GameView(Game game) {
        this.window = new WindowGame();
        this.game = game;
        this.spiderViews = new HashMap<>();

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

        // Ajouter le crusader
        window.add(crusader);

        // Créer les araignées visuelles
        createSpiderViews();

        // Afficher le labyrinthe
        renderMaze();

        // Afficher la fenêtre
        window.setVisible(true);
    }

    /**
     * Crée les vues pour toutes les araignées
     */
    private void createSpiderViews() {
        for (fr.ubordeaux.ao.project.model.entities.Spider spiderModel : game.getSpiders()) {
            Spider spiderView = new Spider();
            Position pos = spiderModel.getPosition();
            spiderView.setPosition(pos.getX(), pos.getY(), pos.getZ());

            // Ajouter à la fenêtre et à la map
            window.add(spiderView);
            spiderViews.put(spiderModel.getId(), spiderView);
        }

        System.out.println("✅ " + spiderViews.size() + " araignées affichées");
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
     * ✨ NOUVEAU : Rafraîchit l'affichage du labyrinthe (pour masquer les fragments collectés)
     */
    public void refreshMaze() {
        // Nettoyer la fenêtre et redessiner le labyrinthe
        window.clear();
        renderMaze();

        // Ré-ajouter le crusader et les araignées
        window.add(crusader);
        for (Spider spiderView : spiderViews.values()) {
            window.add(spiderView);
        }
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

    /**
     * Met à jour toutes les araignées à l'écran
     */
    public void updateSpidersView() {
        for (fr.ubordeaux.ao.project.model.entities.Spider spiderModel : game.getSpiders()) {
            Spider spiderView = spiderViews.get(spiderModel.getId());

            if (spiderView != null) {
                Position pos = spiderModel.getPosition();

                // Si l'araignée est morte, la cacher en la mettant sous le sol
                if (spiderModel.isDead()) {
                    spiderView.setPosition(pos.getX(), pos.getY(), -10); // Sous le sol
                    spiderView.setMode(Spider.Mode.DEATH); // Animation de mort
                } else {
                    spiderView.setPosition(pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
    }
}