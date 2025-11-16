package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.project.controller.GameController;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;

/**
 * Point d'entree de l'application
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   L'EVASION DU DONJON - v0.1      ");
        System.out.println("   Projet Approche Objet           ");
        System.out.println("=====================================");
        System.out.println();

        // Creer le modele (Singleton)
        Game game = Game.getInstance();

        // Creer la vue
        GameView view = new GameView();

        // Creer le controleur
        GameController controller = new GameController(game, view);

        // Demarrer le jeu
        controller.start();

        System.out.println();
        System.out.println("Controles:");
        System.out.println("   ZQSD ou Fleches : Deplacement");
        System.out.println("   SHIFT : Course");
        System.out.println("   ESPACE : Attaque");
    }
}