package be.valuya.accountingtroll.event;

import java.nio.file.Path;

public class ArchiveFileNotFoundIgnoredEvent {
    private Path path;
    private String fileName;

    public ArchiveFileNotFoundIgnoredEvent(Path path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ArchiveFileNotFoundIgnoredEvent{" +
                "path=" + path +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
