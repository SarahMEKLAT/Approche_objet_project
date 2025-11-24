package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private Game game;
    private GameView view;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
    }

    public void start() {
        // Ajouter le KeyListener à la fenêtre
        ((javax.swing.JFrame) view.getWindow()).addKeyListener(this);
        ((javax.swing.JFrame) view.getWindow()).setFocusable(true);

        System.out.println("Contrôleur démarré. Utilisez les flèches pour bouger.");

        // Boucle de déplacement de l'araignée
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Déplacer l'araignée toutes les secondes
                    game.moveSpider();
                    view.updateSpiderView();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean moved = false;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                moved = game.movePlayer(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                moved = game.movePlayer(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                moved = game.movePlayer(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                moved = game.movePlayer(1, 0);
                break;
        }

        if (moved) {
            view.updatePlayerView();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Non utilisé
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé
    }
}