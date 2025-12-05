package fr.ubordeaux.ao.project.model.states;

import fr.ubordeaux.ao.project.model.Game;

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