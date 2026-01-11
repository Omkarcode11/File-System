package service;

import models.Directory;
import models.FileNode;
import org.w3c.dom.Node;

public class FileSystem {

    private Directory root;

    public FileSystem(Directory dict) {
        this.root = dict;
    }

    private String[] tokenize(String path) {
        return path.split("/");
    }

    private Directory traverseToParent(String path) {
        String[] parts = tokenize(path);
        Directory curr = this.root;

        for (int i = 1; i < parts.length - 1; i++) {
            Node node = (Node) curr.getNode(parts[i]);
            if (node == null || !((Directory) node).isDirectory()) {
                throw new RuntimeException("Path is invalid  not able to find " + parts[i]);
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

        Node file = (Node) parent.getNode(name);

        if (file == null || ((FileNode) file).isDirectory()) {
            throw new RuntimeException("File not found " + name);
        }

        ((FileNode) file).write(content);
    }

    public void ls(String path) {
        Directory curr = root;

        if (!path.equals("/")) {
            String[] parts = tokenize(path);
            for (int i = 1; i < parts.length; i++) {
                Node node = (Node) curr.getNode(parts[i]);
                if (node == null || !((Directory) node).isDirectory()) {
                    throw new RuntimeException("Invalid Path");
                }

                curr = (Directory) node;
            }
        }
        System.err.println(curr.list());
    }

    public void rm(String path) {
        Directory node = traverseToParent(path);
        String name = path.substring(path.lastIndexOf("/") + 1);

        if (node == null || !node.isDirectory()) {
            throw new RuntimeException("Invalid Path");
        }

        node.removeNode(name);
    }

}
