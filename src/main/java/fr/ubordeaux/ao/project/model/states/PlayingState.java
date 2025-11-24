package fr.ubordeaux.ao.project.model.states;



import fr.ubordeaux.ao.project.model.Game;

/**
 * État quand le jeu est en cours

public class PlayingState implements GameState {
    private Game game;

    public PlayingState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println(">>> État: EN JEU");
    }

    @Override
    public void update(float deltaTime) {
        // Mise à jour du joueur
        if (game.getPlayer() != null) {
            game.getPlayer().update(deltaTime);
        }

        // TODO: Mise à jour des araignées

        // Vérifier les conditions de fin
        game.checkVictoryCondition();
        game.checkGameOverCondition();
    }

    @Override
    public void exit() {
        System.out.println("<<< Sortie de l'état EN JEU");
    }

    @Override
    public String getName() {
        return "PLAYING";
    }
}
 */