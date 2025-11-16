package fr.ubordeaux.ao.project.model.states;



import fr.ubordeaux.ao.project.model.Game;

public class MenuState implements GameState {
    private Game game;

    public MenuState(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {
        System.out.println(">>> État: MENU");
    }

    @Override
    public void update(float deltaTime) {
        // Rien pour l'instant
    }

    @Override
    public void exit() {
        System.out.println("<<< Sortie du MENU");
    }

    @Override
    public String getName() {
        return "MENU";
    }
}