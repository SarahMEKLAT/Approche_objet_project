package fr.ubordeaux.ao.project.model.items;

import fr.ubordeaux.ao.project.model.maze.Position;

public class Item {
    private Position position;
    private int code;
    private String type;

    public Item(Position position, int code, String type) {
        this.position = position;
        this.code = code;
        this.type = type;
    }

    public Position getPosition() { return position; }
    public int getCode() { return code; }
    public String getType() { return type; }
}