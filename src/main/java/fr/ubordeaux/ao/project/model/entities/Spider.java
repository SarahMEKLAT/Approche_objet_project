package fr.ubordeaux.ao.project.model.entities;



import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Représente une araignée ennemie
 */
public class Spider extends LivingEntity {
    private static final int MAX_HEALTH = 50;

    public Spider(Position position) {
        super(position, MAX_HEALTH);
    }

    @Override
    protected void onDeath() {
        System.out.println("Une araignée est morte!");
    }

    @Override
    public void update(float deltaTime) {
        // TODO: IA
    }
}
