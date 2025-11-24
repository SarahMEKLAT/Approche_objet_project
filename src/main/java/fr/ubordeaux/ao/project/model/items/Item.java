package fr.ubordeaux.ao.project.model.items;

import fr.ubordeaux.ao.project.model.maze.Position;

public class Item {
    private Position position;
    private int code; // Code graphique de l'objet
    private String type; // "CHEST", "TABLE", "BARREL", etc.

    public Item(Position position, int code, String type) {
        this.position = position;
        this.code = code;
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}