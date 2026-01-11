package models;

import enums.NodePermission;

public class FileNode extends Node {

    private StringBuilder content;

    public FileNode(String name, Directory parent) {
        super(name, parent);
        this.content = new StringBuilder();
    }

    public boolean isDirectory() {
        return false;
    }

    public String read() {
        return content.toString();
    }

    public void write(String text) {

        if (!hasPermission(NodePermission.WRITE)) {
            throw new RuntimeException("Write permission denied");
        }

        content.append(text);
        touch();
    }

}
