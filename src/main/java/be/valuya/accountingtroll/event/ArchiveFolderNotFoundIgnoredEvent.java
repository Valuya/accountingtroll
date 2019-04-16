package be.valuya.accountingtroll.event;

import java.nio.file.Path;

public class ArchiveFolderNotFoundIgnoredEvent {

    private Path basePath;
    private Path archivePath;

    public ArchiveFolderNotFoundIgnoredEvent(Path basePath, Path archivePath) {
        this.basePath = basePath;
        this.archivePath = archivePath;
    }

    public Path getBasePath() {
        return basePath;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public Path getArchivePath() {
        return archivePath;
    }

    public void setArchivePath(Path archivePath) {
        this.archivePath = archivePath;
    }

    @Override
    public String toString() {
        return "ArchiveFolderNotFoundIgnoredEvent{" +
                "basePath=" + basePath +
                ", archivePath=" + archivePath +
                '}';
    }
}
