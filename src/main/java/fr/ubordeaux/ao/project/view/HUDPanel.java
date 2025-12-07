package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.project.model.entities.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * HUD du joueur : gère l'affichage des vies
 */
public class HUDPanel implements Observer {

    private int lives = 3;
    private int lastHealth;
    private Player player;

    public HUDPanel(Player player) {
        this.player = player;
        this.lastHealth = player.getHealth();

        // Le HUD observe le Player
        player.addObserver(this);

        System.out.println("🎮 HUD initialisé - Vies : " + lives);
    }

    @Override
    public void update(Observable o, Object arg) {
        if ("HEALTH_CHANGED".equals(arg)) {
            int currentHealth = player.getHealth();

            // Si le joueur vient de tomber à 0 PV
            if (currentHealth <= 0 && lastHealth > 0) {
                lives--;
                System.out.println("❤️ Vie perdue ! Vies restantes : " + lives);

                if (lives <= 0) {
                    System.out.println("💀 GAME OVER");
                }
            }

            lastHealth = currentHealth;
        }
    }

    public String getLivesText() {
        return "Vies : " + lives;
    }

    public int getLives() {
        return lives;
    }
}
