package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;
import fr.ubordeaux.ao.project.controller.GameController;

/**
 * Point d'entrée du programme
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   THE ARACHNID CURSE - Demo     ║");
        System.out.println("║   Labyrinthe en croix (5 salles) ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();

        // Créer le jeu
        Game game = new Game();

        // Créer la vue
        GameView view = new GameView(game);

        // Créer le contrôleur
        GameController controller = new GameController(game, view);
        controller.start();

        System.out.println("\n✅ Jeu lancé !");
        System.out.println("\n📋 Contrôles :");
        System.out.println("  ↑ ↓ ← → : Déplacement");
        System.out.println("  Espace  : Attaque");
        System.out.println();
        System.out.println("🎯 Objectif : Atteindre la salle Sud [4] pour gagner !");
    }
}