package be.valuya.accountingtroll.event;

import java.nio.file.Path;
import java.util.Optional;

public class ArchiveNotFoundIgnoredEvent {
    private Path path;
    private Optional<String> fileNameOptional = Optional.empty();
    private Optional<String> archiveYearOptional = Optional.empty();

    public ArchiveNotFoundIgnoredEvent(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Optional<String> getFileNameOptional() {
        return fileNameOptional;
    }

    public void setFileNameOptional(Optional<String> fileNameOptional) {
        this.fileNameOptional = fileNameOptional;
    }

    public Optional<String> getArchiveYearOptional() {
        return archiveYearOptional;
    }

    public void setArchiveYearOptional(Optional<String> archiveYearOptional) {
        this.archiveYearOptional = archiveYearOptional;
    }

    @Override
    public String toString() {
        return "ArchiveNotFoundIgnoredEvent{" +
                "path=" + path +
                ", fileNameOptional=" + fileNameOptional +
                ", archiveYearOptional=" + archiveYearOptional +
                '}';
    }
}
