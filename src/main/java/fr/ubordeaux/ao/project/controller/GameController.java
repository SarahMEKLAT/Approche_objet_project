package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Contrôleur principal du jeu
 * Gère les inputs clavier et la boucle de jeu
 */
public class GameController implements KeyListener {
    private Game game;
    private GameView view;
    private Thread spiderThread;
    private volatile boolean running = true;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
    }

    /**
     * Démarre le contrôleur
     */
    public void start() {
        // Attacher le listener clavier à la fenêtre
        ((javax.swing.JFrame) view.getWindow()).addKeyListener(this);
        ((javax.swing.JFrame) view.getWindow()).setFocusable(true);
        ((javax.swing.JFrame) view.getWindow()).requestFocus();

        System.out.println("Contrôleur démarré. Utilisez les flèches pour bouger.");

        // Thread pour déplacer l'araignée automatiquement
        spiderThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000); // Toutes les secondes
                    if (game.isPlaying()) {
                        game.moveSpider();
                        view.updateSpiderView();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        spiderThread.start();
    }

    /**
     * Arrête le contrôleur
     */
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
            // Déplacement
            case KeyEvent.VK_UP:
            case KeyEvent.VK_Z:
                moved = game.movePlayer(0, -1);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                moved = game.movePlayer(0, 1);
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
                moved = game.movePlayer(-1, 0);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                moved = game.movePlayer(1, 0);
                break;

            // Attaque
            case KeyEvent.VK_SPACE:
                if (game.isPlaying()) {
                    System.out.println("⚔️ Attaque !");
                    game.getPlayer().attack();
                }
                break;

            // Redémarrer (R)
            case KeyEvent.VK_R:
                if (game.isGameOver() || game.isVictory()) {
                    System.out.println("🔄 Redémarrage du jeu...");
                    game.restart();
                    view.renderMaze();
                    view.updatePlayerView();
                }
                break;

            // Quitter (ESC)
            case KeyEvent.VK_ESCAPE:
                System.out.println("Sortie du jeu...");
                stop();
                System.exit(0);
                break;
        }

        // Si le joueur a bougé, rafraîchir la vue (caméra suit)
        if (moved) {
            view.updatePlayerView();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Rien pour l'instant
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Rien pour l'instant
    }
}