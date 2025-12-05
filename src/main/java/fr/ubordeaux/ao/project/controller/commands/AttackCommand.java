package fr.ubordeaux.ao.project.controller.commands;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.entities.Player;
import fr.ubordeaux.ao.project.model.entities.Spider;
import fr.ubordeaux.ao.project.model.maze.Position;

/**
 * Commande d'attaque du joueur
 * Pattern: Command
 */
public class AttackCommand extends Command {
    private static final float ATTACK_RANGE = 1.5f;
    private static final int ATTACK_DAMAGE = 25;

    public AttackCommand(Game game) {
        super(game);
    }

    @Override
    public boolean execute() {
        Player player = game.getPlayer();

        if (player == null || player.isDead()) {
            return false;
        }

        // Vérifier si le joueur a assez d'endurance
        if (player.getStamina() < 20) {
            System.out.println("⚠️ Pas assez d'endurance pour attaquer !");
            return false;
        }

        // Exécuter l'attaque
        player.attack();

        // Trouver l'araignée la plus proche à portée
        Spider target = game.getClosestSpider(player.getPosition(), ATTACK_RANGE);

        if (target != null) {
            target.takeDamage(ATTACK_DAMAGE);
            System.out.println("🎯 Araignée touchée !");
            game.addScore(50); // Points pour toucher

            if (target.isDead()) {
                game.addScore(100); // Bonus pour tuer
                System.out.println("💀 Araignée éliminée ! +100 points");
            }
        } else {
            System.out.println("❌ Attaque manquée (aucune cible à portée)");
        }

        System.out.println("[CMD] " + getDescription());
        return true;
    }

    @Override
    public void undo() {
        // On ne peut pas vraiment "annuler" une attaque
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Attaque (coût: 20 endurance, dégâts: " + ATTACK_DAMAGE + ")";
    }
}