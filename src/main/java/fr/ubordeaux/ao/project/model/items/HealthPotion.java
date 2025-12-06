package fr.ubordeaux.ao.project.model.items;

import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Potion de vie
 * Code 400 = Torche (représente une potion rouge)
 * Restaure 30 points de vie au joueur
 */
public class HealthPotion extends Item {
    private static final int HEAL_AMOUNT = 30;
    private static final int POTION_CODE = 400; // Torche (Table 400)

    public HealthPotion(Position position) {
        super(position, POTION_CODE, "HEALTH_POTION");
    }

    public int use() {
        System.out.println("💖 Potion de vie utilisée ! +" + HEAL_AMOUNT + " PV");
        return HEAL_AMOUNT;
    }

    public boolean canCollect(Position playerPos) {
        return getPosition().distanceTo(playerPos) < 0.8f;
    }

    public int getHealAmount() {
        return HEAL_AMOUNT;
    }
}