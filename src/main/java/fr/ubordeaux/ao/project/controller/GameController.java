package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;

/**
 * Contrôleur principal du jeu
 * Gère la boucle de jeu et délègue les inputs au KeyboardController
 */
public class GameController {
    private Game game;
    private GameView view;
    private KeyboardController keyboardController;
    private Thread spiderThread;
    private Thread gameLoopThread;
    private volatile boolean running = true;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;

        // Créer le contrôleur clavier avec Pattern Command
        // IMPORTANT: Passer la view pour qu'elle puisse être mise à jour
        this.keyboardController = new KeyboardController(game, view);
    }

    /**
     * Démarre le contrôleur
     */
    public void start() {
        System.out.println("Contrôleur démarré (avec Pattern Command).");
        System.out.println("Nouvelles touches :");
        System.out.println("  U : Annuler le dernier mouvement");
        System.out.println("  R : Redémarrer après victoire/défaite");

        // Thread pour la boucle de jeu principale (60 FPS)
        gameLoopThread = new Thread(() -> {
            long lastTime = System.nanoTime();
            double nsPerTick = 1000000000.0 / 60.0; // 60 FPS
            double delta = 0;

            while (running) {
                long now = System.nanoTime();
                delta += (now - lastTime) / nsPerTick;
                lastTime = now;

                if (delta >= 1) {
                    float deltaTime = (float) delta / 60.0f;
                    game.update(deltaTime);
                    delta = 0;
                }

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        gameLoopThread.start();

        // Thread pour déplacer TOUTES les araignées automatiquement
        spiderThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(500); // Toutes les 0.5 secondes
                    if (game.isPlaying()) {
                        // Mettre à jour l'IA de toutes les araignées
                        game.updateSpiders(0.5f);

                        // Mettre à jour la vue
                        view.updateSpidersView();
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
        if (gameLoopThread != null) {
            gameLoopThread.interrupt();
        }
    }

    /**
     * Donne accès au KeyboardController pour des fonctionnalités avancées
     */
    public KeyboardController getKeyboardController() {
        return keyboardController;
    }
}