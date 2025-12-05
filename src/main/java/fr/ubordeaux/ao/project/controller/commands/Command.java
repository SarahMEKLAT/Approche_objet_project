package fr.ubordeaux.ao.project.controller.commands;

import fr.ubordeaux.ao.project.model.Game;

/**
 * Pattern Command
 * Classe abstraite pour encapsuler les actions du joueur
 */
public abstract class Command {
    protected Game game;

    public Command(Game game) {
        this.game = game;
    }

    /**
     * Exécute la commande
     * @return true si la commande a réussi, false sinon
     */
    public abstract boolean execute();

    /**
     * Annule la commande (pour l'historique)
     * Par défaut, ne fait rien
     */
    public void undo() {
        // Implémentation optionnelle dans les sous-classes
    }

    /**
     * Indique si la commande peut être annulée
     */
    public boolean isUndoable() {
        return false;
    }

    /**
     * Description de la commande (pour debug/log)
     */
    public abstract String getDescription();
}