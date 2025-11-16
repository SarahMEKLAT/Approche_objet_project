package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.maze.Position;
import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.Direction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements KeyListener {
    private Game game;
    private IWindowGame window;

    public KeyboardController(Game game, IWindowGame window) {
        this.game = game;
        this.window = window;

        // Essayer d'attacher le listener de plusieurs façons
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

        System.out.println("Contrôleur clavier attaché");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Touche pressée: " + e.getKeyCode());  // DEBUG

        if (game.getPlayer() == null) {
            System.out.println("Player est null!");
            return;
        }

        Position currentPos = game.getPlayer().getPosition();
        Position newPos = currentPos.copy();
        Direction newDirection = null;
        boolean moved = false;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:
            case KeyEvent.VK_UP:
                newPos.setY(currentPos.getY() - 1);
                newDirection = Direction.NORTH;
                moved = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                newPos.setY(currentPos.getY() + 1);
                newDirection = Direction.SOUTH;
                moved = true;
                break;
            case KeyEvent.VK_Q:
            case KeyEvent.VK_LEFT:
                newPos.setX(currentPos.getX() - 1);
                newDirection = Direction.WEST;
                moved = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                newPos.setX(currentPos.getX() + 1);
                newDirection = Direction.EAST;
                moved = true;
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Attaque!");
                game.getPlayer().attack();
                break;
        }

        if (moved) {
            System.out.println("Position actuelle: " + currentPos);
            System.out.println("Tentative de mouvement vers: " + newPos);
            System.out.println("isInBounds: " + game.getMaze().isInBounds(newPos));
            System.out.println("isWall: " + game.getMaze().isWall(newPos));
            if (game.getMaze().isInBounds(newPos) && !game.getMaze().isWall(newPos)) {
                game.getPlayer().move(newPos);
                if (newDirection != null) {
                    game.getPlayer().getCrusader().setDirection(newDirection);
                }
                System.out.println("Joueur: " + newPos);
            } else {
                System.out.println("Mouvement bloqué (mur ou hors limites)");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}