package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.project.controller.GameController;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;

/**
 * Point d'entrée de l'application
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dungeon Escape ===");

        // Créer le modèle (Game)
        Game game = new Game();

        // Créer la vue (GameView)
        GameView view = new GameView(game);

        // Créer le contrôleur (GameController)
        GameController controller = new GameController(game, view);

        // Démarrer le jeu
        controller.start();
    }
}