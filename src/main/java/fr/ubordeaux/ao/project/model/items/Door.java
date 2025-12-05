package fr.ubordeaux.ao.project.model.items;

import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Porte de sortie du donjon
 * Le joueur doit interagir avec (touche E) pour gagner
 */
public class Door extends Item {
    private boolean isLocked;
    private int requiredFragments;

    public Door(Position position) {
        super(position, 165, "EXIT_DOOR");
        this.isLocked = true;
        this.requiredFragments = 0; // Pour l'instant, pas besoin de fragments
    }

    /**
     * Tente d'ouvrir la porte
     * @param playerFragments nombre de fragments que possède le joueur
     * @return true si la porte s'ouvre, false sinon
     */
    public boolean tryOpen(int playerFragments) {
        if (playerFragments >= requiredFragments) {
            isLocked = false;
            System.out.println("🔓 La porte s'ouvre ! Vous avez gagné !");
            return true;
        } else {
            System.out.println("🔒 La porte est verrouillée. Il vous faut " +
                    requiredFragments + " fragments de clé.");
            System.out.println("   Vous avez : " + playerFragments + "/" + requiredFragments);
            return false;
        }
    }

    /**
     * Vérifie si le joueur est proche de la porte
     */
    public boolean isPlayerNearby(Position playerPos) {
        return getPosition().distanceTo(playerPos) <= 1.5f;
    }

    // Getters
    public boolean isLocked() { return isLocked; }
    public int getRequiredFragments() { return requiredFragments; }

    public void setRequiredFragments(int fragments) {
        this.requiredFragments = fragments;
    }
}