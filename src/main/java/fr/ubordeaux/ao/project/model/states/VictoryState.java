package fr.ubordeaux.ao.project.model.states;



import fr.ubordeaux.ao.project.model.Game;

public class VictoryState implements GameState {
    private Game game;

    public VictoryState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println("🎉 >>> VICTOIRE ! <<<");
        game.addScore(5000); // Bonus victoire
    }

    @Override
    public void update(float deltaTime) {
        // Rien
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