package service;

import models.*;
import models.Node;

public class FileSystem {

    private Directory root;

    public FileSystem() {
        this.root = new Directory("/", null);
    }

    private String[] tokenize(String path) {
        if (path.equals("/"))
            return new String[0];
        return path.substring(1).split("/");
    }

    private Directory traverseToParent(String path) {
        if (path.equals("/") || !path.startsWith("/")) {
            return root;
        }

        String[] parts = tokenize(path);
        Directory curr = this.root;

        for (int i = 0; i < parts.length - 1; i++) {
            Node node = curr.getNode(parts[i]);
            if (node == null || !node.isDirectory()) {
                throw new RuntimeException("Path is invalid: not able to find " + parts[i]);
            }
            curr = (Directory) node;
        }

        return curr;
    }

    public void mkdir(String path) {
        Directory parent = traverseToParent(path);
        String name = path.substring(path.lastIndexOf("/") + 1);
        parent.addNode(new Directory(name, parent));
    }

    public void touch(String path) {
        Directory parent = traverseToParent(path);
        String name = path.substring(path.lastIndexOf("/") + 1);
        parent.addNode(new FileNode(name, parent));
    }

    public void write(String path, String content) {
        Directory parent = traverseToParent(path);
        String name = path.substring(path.lastIndexOf("/") + 1);

        Node file = parent.getNode(name);

        if (file == null || file.isDirectory()) {
            throw new RuntimeException("File not found: " + name);
        }

        ((FileNode) file).write(content);
    }

    public String read(String path) {
        Directory parent = traverseToParent(path);
        String name = path.substring(path.lastIndexOf("/") + 1);

        Node file = parent.getNode(name);
        if (file == null || file.isDirectory()) {
            throw new RuntimeException("File not found: " + name);
        }

        return ((FileNode) file).read();
    }

    public void ls(String path) {
        Directory curr = root;

        if (!path.equals("/")) {
            String[] parts = tokenize(path);
            for (int i = 0; i < parts.length; i++) {
                Node node = curr.getNode(parts[i]);
                if (node == null || !node.isDirectory()) {
                    throw new RuntimeException("Invalid Path: " + path);
                }
                curr = (Directory) node;
            }
        }
        System.out.println("Contents of " + path + ": " + curr.list());
    }

    public void rm(String path) {
        Directory parent = traverseToParent(path);
        String name = path.substring(path.lastIndexOf("/") + 1);

        parent.removeNode(name);
    }

}
