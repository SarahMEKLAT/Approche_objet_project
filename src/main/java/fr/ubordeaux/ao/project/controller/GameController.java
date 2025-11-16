package fr.ubordeaux.ao.project.controller;



import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameView;

import javax.swing.Timer;

/**
 * Contrôleur principal du jeu (Pattern: MVC)
 */
public class GameController {
    private Game game;
    private GameView view;
    private KeyboardController keyboardController;

    private Timer gameLoop;
    private static final int FPS = 50;
    private long lastUpdateTime;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * Démarre le jeu
     */
    public void start() {
        System.out.println("=== Démarrage du contrôleur ===");

        // Initialiser la vue
        view.initialize(game);

        // Initialiser le contrôleur clavier
        keyboardController = new KeyboardController(game, view.getWindow());

        // Démarrer une nouvelle partie
        game.startNewGame();

        // Afficher la scène
        view.render();
        view.show();

        // Démarrer la boucle de jeu
        startGameLoop();
    }

    /**
     * Démarre la boucle de mise à jour
     */
    private void startGameLoop() {
        gameLoop = new Timer(1000 / FPS, e -> {
            long currentTime = System.currentTimeMillis();
            float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
            lastUpdateTime = currentTime;

            // Mettre à jour le modèle
            game.update(deltaTime);

            // Pas besoin de re-render à chaque frame pour l'instant
        });

        gameLoop.start();
        System.out.println("Boucle de jeu démarrée (" + FPS + " FPS)");
    }

    /**
     * Arrête le jeu
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        System.out.println("Jeu arrêté");
    }
}
