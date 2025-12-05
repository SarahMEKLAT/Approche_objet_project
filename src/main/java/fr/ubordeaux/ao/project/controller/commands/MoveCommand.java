package fr.ubordeaux.ao.project.controller.commands;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Commande de déplacement du joueur
 * Pattern: Command
 */
public class MoveCommand extends Command {
    private int dx;
    private int dy;
    private Position previousPosition;
    private boolean wasExecuted;

    public MoveCommand(Game game, int dx, int dy) {
        super(game);
        this.dx = dx;
        this.dy = dy;
        this.wasExecuted = false;
    }

    @Override
    public boolean execute() {
        if (game.getPlayer() == null) {
            return false;
        }

        // Sauvegarder la position actuelle (pour undo)
        previousPosition = game.getPlayer().getPosition().copy();

        // Tenter le mouvement
        boolean success = game.movePlayer(dx, dy);

        if (success) {
            wasExecuted = true;
            System.out.println("[CMD] " + getDescription());
        }

        return success;
    }

    @Override
    public void undo() {
        if (wasExecuted && previousPosition != null) {
            game.getPlayer().setPosition(previousPosition);
            System.out.println("[UNDO] Retour à la position précédente : " + previousPosition);
        }
    }

    @Override
    public boolean isUndoable() {
        return wasExecuted;
    }

    @Override
    public String getDescription() {
        String direction = "";
        if (dx > 0) direction = "Droite";
        else if (dx < 0) direction = "Gauche";
        else if (dy > 0) direction = "Bas";
        else if (dy < 0) direction = "Haut";

        return "Déplacement vers " + direction + " (" + dx + ", " + dy + ")";
    }
}