package fr.ubordeaux.ao.project.model.states;



/**
 * Interface pour les états du jeu (Pattern: State)
 */
public interface GameState {
    void enter();
    void update(float deltaTime);
    void exit();
    String getName();
}
