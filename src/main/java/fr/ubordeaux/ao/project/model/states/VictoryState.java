package fr.ubordeaux.ao.project.model.states;

import fr.ubordeaux.ao.project.model.Game;

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