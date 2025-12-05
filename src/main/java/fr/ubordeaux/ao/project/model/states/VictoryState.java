package fr.ubordeaux.ao.project.model.states;

import fr.ubordeaux.ao.project.model.Game;
import javax.swing.JOptionPane;

/**
 * État Victoire - joueur a gagné
 * Pattern: State
 */
public class VictoryState implements GameState {
    private Game game;

    public VictoryState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("🎉 >>> VICTOIRE ! <<<");
        game.addScore(5000); // Bonus victoire
        System.out.println("Score final : " + game.getScore());

        // 🎉 AFFICHER UN MESSAGE GRAPHIQUE DE VICTOIRE
        String message = "🎉 FÉLICITATIONS ! 🎉\n\n" +
                "Vous avez réussi à vous échapper du donjon !\n\n" +
                "Score final : " + game.getScore() + " points\n\n" +
                "Appuyez sur R pour rejouer\n" +
                "ou ESC pour quitter";

        JOptionPane.showMessageDialog(
                null,
                message,
                "🏆 VICTOIRE ! 🏆",
                JOptionPane.INFORMATION_MESSAGE
        );

        System.out.println("Appuyez sur R pour recommencer ou ESC pour quitter");
    }

    @Override
    public void update(float deltaTime) {
        // Rien à faire, on attend que le joueur redémarre ou quitte
    }

    @Override
    public void exit() {
        System.out.println("<<< Sortie de VICTOIRE");
    }

    @Override
    public String getName() {
        return "VICTORY";
    }
}