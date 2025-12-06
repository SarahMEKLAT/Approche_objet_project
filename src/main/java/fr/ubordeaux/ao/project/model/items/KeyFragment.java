package fr.ubordeaux.ao.project.model.items;

import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Fragment de clé magique
 * Code 550 = Fragment de clé brillant
 * 3 fragments nécessaires pour ouvrir la porte finale
 */
public class KeyFragment extends Item {
    private static final int FRAGMENT_CODE = 550; // ✨ Code Mazing pour fragment
    private int fragmentId; // 1, 2 ou 3
    private boolean collected; // ✨ État de collection

    public KeyFragment(Position position, int fragmentId) {
        super(position, FRAGMENT_CODE, "KEY_FRAGMENT_" + fragmentId);
        this.fragmentId = fragmentId;
        this.collected = false;
    }

    public void collect() {
        if (!collected) {
            collected = true;
            System.out.println("🔑 Fragment de clé " + fragmentId + "/3 collecté !");
        }
    }

    public boolean canCollect(Position playerPos) {
        return !collected && getPosition().distanceTo(playerPos) < 0.5f;
    }

    public int getFragmentId() {
        return fragmentId;
    }

    public boolean isCollected() {
        return collected;
    }
}