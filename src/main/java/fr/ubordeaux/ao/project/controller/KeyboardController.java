package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.Direction;
import fr.ubordeaux.ao.project.controller.commands.*;
import fr.ubordeaux.ao.project.view.GameView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

/**
 * Contrôleur clavier avec Pattern Command
 */
public class KeyboardController implements KeyListener {
    private Game game;
    private GameView view;

    // Historique des commandes (pour undo)
    private Stack<Command> commandHistory;
    private static final int MAX_HISTORY = 10;

    public KeyboardController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        this.commandHistory = new Stack<>();

        // Essayer d'attacher le listener de plusieurs façons
        IWindowGame window = view.getWindow();

        if (window instanceof JFrame) {
            JFrame frame = (JFrame) window;
            frame.addKeyListener(this);
            frame.setFocusable(true);
            frame.requestFocusInWindow();

            // Aussi ajouter au content pane
            frame.getContentPane().addKeyListener(this);
            frame.getContentPane().setFocusable(true);
        } else if (window instanceof Component) {
            Component comp = (Component) window;
            comp.addKeyListener(this);
            comp.setFocusable(true);
            comp.requestFocus();
        }

        System.out.println("Contrôleur clavier attaché (avec Pattern Command)");
    }

    /**
     * Exécute une commande et l'ajoute à l'historique
     */
    private void executeCommand(Command command) {
        if (command.execute()) {
            // Mettre à jour la vue après chaque commande réussie
            view.updatePlayerView();

            // Ajouter à l'historique seulement si la commande a réussi et est undoable
            if (command.isUndoable()) {
                commandHistory.push(command);

                // Limiter la taille de l'historique
                if (commandHistory.size() > MAX_HISTORY) {
                    commandHistory.remove(0);
                }
            }
        }
    }

    /**
     * Annule la dernière commande
     */
    private void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();

            // Mettre à jour la vue après l'undo
            view.updatePlayerView();

            System.out.println("⏪ Commande annulée : " + lastCommand.getDescription());
        } else {
            System.out.println("⚠️ Aucune commande à annuler");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Touche pressée: " + e.getKeyCode());

        if (game.getPlayer() == null) {
            System.out.println("Player est null!");
            return;
        }

        Command command = null;

        switch (e.getKeyCode()) {
            // Déplacements avec Pattern Command
            case KeyEvent.VK_Z:
            case KeyEvent.VK_UP:
                command = new MoveCommand(game, 0, -1);
                updateDirection(Direction.NORTH);
                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                command = new MoveCommand(game, 0, 1);
                updateDirection(Direction.SOUTH);
                break;

            case KeyEvent.VK_Q:
            case KeyEvent.VK_LEFT:
                command = new MoveCommand(game, -1, 0);
                updateDirection(Direction.WEST);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                command = new MoveCommand(game, 1, 0);
                updateDirection(Direction.EAST);
                break;

            // Undo (touche U)
            case KeyEvent.VK_U:
                undoLastCommand();
                break;

            // Redémarrer (R)
            case KeyEvent.VK_R:
                if (game.isGameOver() || game.isVictory()) {
                    System.out.println("🔄 Redémarrage...");
                    commandHistory.clear(); // Vider l'historique
                    game.restart();
                    view.renderMaze();
                    view.updatePlayerView();
                }
                break;
        }

        // Exécuter la commande si elle existe
        if (command != null) {
            executeCommand(command);
        }
    }

    /**
     * Met à jour la direction du personnage graphique
     */
    private void updateDirection(Direction direction) {
        if (game.getPlayer() != null && game.getPlayer().getCrusader() != null) {
            game.getPlayer().getCrusader().setDirection(direction);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Affiche l'historique des commandes (pour debug)
     */
    public void printHistory() {
        System.out.println("📜 Historique des commandes :");
        for (int i = 0; i < commandHistory.size(); i++) {
            System.out.println("  " + (i + 1) + ". " +
                    commandHistory.get(i).getDescription());
        }
    }
}