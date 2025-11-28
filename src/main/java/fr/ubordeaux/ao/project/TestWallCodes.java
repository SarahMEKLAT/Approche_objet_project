package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.IWindowGame;

/**
 * Programme de TEST pour voir quels codes de murs s'affichent
 */
public class TestWallCodes {
    public static void main(String[] args) {
        System.out.println("=== TEST DES CODES DE MURS MAZING ===");
        System.out.println("On affiche tous les codes de 100 à 150");
        System.out.println("pour voir lesquels fonctionnent vraiment.\n");

        IWindowGame window = new WindowGame();

        // Sol de base (10x15 tuiles)
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 15; x++) {
                window.add(72, x, y, 0); // Sol partout
            }
        }

        // Tester tous les codes de 100 à 150
        int code = 100;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 10; x++) {
                if (code <= 150) {
                    // Afficher le code en Z=1 (au-dessus du sol)
                    window.add(code, x, y, 1);
                    System.out.println("Code " + code + " à position (" + x + ", " + y + ")");
                    code++;
                }
            }
        }

        System.out.println("\n✅ Tous les codes affichés !");
        System.out.println("Regardez la fenêtre pour voir lesquels apparaissent.");
        System.out.println("Les codes invisibles = non supportés par Mazing.\n");
        System.out.println("Note : Code 101 devrait être visible en (1, 0)");
        System.out.println("      Code 108 devrait être en (8, 0)");
        System.out.println("      Code 109 devrait être en (9, 0)");
        System.out.println("      Code 110 devrait être en (0, 1)");

        window.setVisible(true);
    }
}