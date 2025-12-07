package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.project.model.Game;
import javax.swing.*;
import java.awt.*;

/**
 * Panel HUD pour afficher les informations du joueur
 */
public class HUDPanel extends JPanel {
    private Game game;
    private Font hudFont;
    private Font titleFont;

    public HUDPanel(Game game) {
        this.game = game;
        this.hudFont = new Font("Arial", Font.BOLD, 20);
        this.titleFont = new Font("Arial", Font.BOLD, 16);

        setOpaque(false); // Transparent pour voir le jeu en dessous
        setLayout(null); // Positionnement manuel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Antialiasing pour un rendu plus lisse
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner les 3 éléments du HUD
        drawHealthBar(g2d, 20, 20);
        drawKeyFragments(g2d, 20, 80);
        drawScore(g2d, 20, 140);
    }

    /**
     * Dessine la barre de vie
     */
    private void drawHealthBar(Graphics2D g, int x, int y) {
        int health = game.getPlayer().getHealth();
        int maxHealth = game.getPlayer().getMaxHealth();

        // Fond noir semi-transparent
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(x - 5, y - 5, 210, 50, 10, 10);

        // Titre "VIE"
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString("VIE", x + 5, y + 15);

        // Barre de fond (gris)
        g.setColor(new Color(80, 80, 80));
        g.fillRect(x, y + 20, 200, 25);

        // Barre de vie colorée selon le pourcentage
        int barWidth = (int) (200 * ((float) health / maxHealth));

        if (health > maxHealth * 0.5) {
            g.setColor(new Color(34, 139, 34)); // Vert
        } else if (health > maxHealth * 0.25) {
            g.setColor(new Color(255, 165, 0)); // Orange
        } else {
            g.setColor(new Color(220, 20, 60)); // Rouge
        }
        g.fillRect(x, y + 20, barWidth, 25);

        // Bordure blanche
        g.setColor(Color.WHITE);
        g.drawRect(x, y + 20, 200, 25);

        // Texte centré
        g.setFont(hudFont);
        String healthText = health + " / " + maxHealth;
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(healthText);
        g.drawString(healthText, x + (200 - textWidth) / 2, y + 40);
    }

    /**
     * Dessine les fragments de clés (3 emplacements)
     */
    private void drawKeyFragments(Graphics2D g, int x, int y) {
        int fragments = game.getPlayer().getKeyFragments();

        // Fond noir semi-transparent
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(x - 5, y - 5, 210, 45, 10, 10);

        // Titre
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString("FRAGMENTS DE CLE", x + 5, y + 15);

        // Dessiner 3 cercles pour les fragments
        for (int i = 0; i < 3; i++) {
            int keyX = x + 20 + (i * 60);
            int keyY = y + 20;

            if (i < fragments) {
                // Fragment collecté (doré)
                g.setColor(new Color(255, 215, 0));
                g.fillOval(keyX, keyY, 40, 40);
                g.setColor(new Color(184, 134, 11));
                g.drawOval(keyX, keyY, 40, 40);

                // Lettre K pour "Key"
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(Color.BLACK);
                g.drawString("K", keyX + 12, keyY + 27);
            } else {
                // Emplacement vide (gris)
                g.setColor(new Color(100, 100, 100));
                g.fillOval(keyX, keyY, 40, 40);
                g.setColor(new Color(60, 60, 60));
                g.drawOval(keyX, keyY, 40, 40);

                // Point d'interrogation
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.setColor(new Color(150, 150, 150));
                g.drawString("?", keyX + 14, keyY + 30);
            }
        }
    }

    /**
     * Dessine le score
     */
    private void drawScore(Graphics2D g, int x, int y) {
        int score = game.getScore();

        // Fond noir semi-transparent
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(x - 5, y - 5, 210, 45, 10, 10);

        // Titre
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString("SCORE", x + 5, y + 15);

        // Score en doré
        g.setFont(hudFont);
        g.setColor(new Color(255, 215, 0));
        g.drawString(String.valueOf(score), x + 5, y + 35);
    }

    /**
     * Force le rafraîchissement du HUD
     */
    public void refresh() {
        repaint();
    }
}