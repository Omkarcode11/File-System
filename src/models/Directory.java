package models;

import java.util.*;

public class Directory extends Node {

    public Map<String, Node> children;

    public Directory(String name, Directory parent) {
        super(name, parent);
        this.children = new HashMap<>();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public void addNode(Node node) {
        if (children.containsKey(node.getName())) {
            throw new RuntimeException("Node already exists: " + node.getName());
        }

        children.put(node.getName(), node);
    }

    public Node getNode(String name) {
        return children.get(name);
    }

    public void removeNode(String name) {
        if (!children.containsKey(name)) {
            throw new RuntimeException("Directory is not present " + name);
        }
        children.remove(name);
    }

    public List<String> list() {
        return new ArrayList<>(children.keySet());
    }

}
