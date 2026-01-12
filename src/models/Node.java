package models;

import enums.NodePermission;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;

public abstract class Node {

    protected String name;
    protected Directory parent;

    protected Set<NodePermission> permissions;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    protected Node(String name, Directory dic) {
        this.name = name;
        this.parent = dic;

        this.permissions = EnumSet.of(NodePermission.READ, NodePermission.WRITE);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return this.parent;
    }

    public String getPath() {
        if (parent == null) {
            return "/";
        }
        String parentPath = parent.getPath();
        return (parentPath.equals("/") ? "" : parentPath) + "/" + name;
    }

    public abstract boolean isDirectory();

    public boolean hasPermission(NodePermission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(NodePermission permission) {
        permissions.add(permission);
    }

    public void removePermission(NodePermission permission) {
        permissions.remove(permission);
    }

    protected void touch() {
        this.updatedAt = LocalDateTime.now();
    }

}
