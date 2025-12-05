package fr.ubordeaux.ao.project.model.states;

import fr.ubordeaux.ao.project.model.Game;
import javax.swing.JOptionPane;

/**
 * État Game Over - joueur mort
 * Pattern: State
 */
public class GameOverState implements GameState {
    private Game game;

    public GameOverState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("💀 >>> GAME OVER ! <<<");
        System.out.println("Score final : " + game.getScore());

        // 💀 AFFICHER UN MESSAGE GRAPHIQUE DE GAME OVER
        String message = "💀 GAME OVER 💀\n\n" +
                "Vous avez succombé aux dangers du donjon...\n\n" +
                "Score final : " + game.getScore() + " points\n\n" +
                "Appuyez sur R pour réessayer\n" +
                "ou ESC pour quitter";

        JOptionPane.showMessageDialog(
                null,
                message,
                "☠️ GAME OVER ☠️",
                JOptionPane.ERROR_MESSAGE
        );

        System.out.println("Appuyez sur R pour recommencer ou ESC pour quitter");
    }

    @Override
    public void update(float deltaTime) {
        // Rien à faire, on attend que le joueur redémarre ou quitte
    }

    @Override
    public void exit() {
        System.out.println("<<< Sortie de GAME OVER");
    }

    @Override
    public String getName() {
        return "GAME_OVER";
    }
}