package org.zegai.miniopoc.storage;

public enum FileStorageFolder {

    TOOLS("tools", true),
    CONFIG("config", true);

    private final String name;
    private final boolean isPublic;

    FileStorageFolder(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    public String getPath() {
        String parentFolder = isPublic ? "public/" : "";
        return parentFolder + name;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
