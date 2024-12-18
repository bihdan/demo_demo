package org.example.demo;

public class Desk {
    private int id;
    private String name;

    public Desk(int id, String name) {
        this.id = id;
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва колоди не повинна бути пустою.");
        }
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
