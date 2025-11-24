package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private Game game;
    private GameView view;
    private Thread spiderThread;
    private volatile boolean running = true;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
    }

    public void start() {
        ((javax.swing.JFrame) view.getWindow()).addKeyListener(this);
        ((javax.swing.JFrame) view.getWindow()).setFocusable(true);

        System.out.println("Contrôleur démarré. Utilisez les flèches pour bouger.");

        // Thread pour déplacer l'araignée automatiquement
        spiderThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000); // Toutes les secondes
                    game.moveSpider();
                    view.updateSpiderView();
                    System.out.println("Araignée déplacée vers: " + game.getSpider().getPosition());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        spiderThread.start();
    }

    public void stop() {
        running = false;
        if (spiderThread != null) {
            spiderThread.interrupt();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean moved = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> moved = game.movePlayer(0, -1);
            case KeyEvent.VK_DOWN -> moved = game.movePlayer(0, 1);
            case KeyEvent.VK_LEFT -> moved = game.movePlayer(-1, 0);
            case KeyEvent.VK_RIGHT -> moved = game.movePlayer(1, 0);
        }
        if (moved) {
            view.updatePlayerView();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}