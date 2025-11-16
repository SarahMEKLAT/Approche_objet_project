package fr.ubordeaux.ao.project.model.states;



import fr.ubordeaux.ao.project.model.Game;

public class GameOverState implements GameState {
    private Game game;

    public GameOverState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("💀 >>> GAME OVER <<<");
    }

    @Override
    public void update(float deltaTime) {
        // Rien
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