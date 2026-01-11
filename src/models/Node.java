package models;

public abstract class Node {
    protected  String name;
    protected  Directory parent;

    protected Node(String name, Directory dic){
        this.name = name;
        this.parent = dic;
    }

    public String getName(){
        return name;
    }

    public Directory getParent(){
        return this.parent;
    }
    
    public String getPath(){
        if(parent == null) return "/";
        if(parent.getParent() == null) return "/" + name;

        return parent.getParent() + "/" + name;

    }

    public abstract  boolean isDirectory();

}
