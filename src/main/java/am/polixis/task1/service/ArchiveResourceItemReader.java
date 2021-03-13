package am.polixis.task1.service;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.zip.ZipFile;

@Service
public class ArchiveResourceItemReader<T> extends MultiResourceItemReader<T> {

    private ZipFile zipFile;
    private Resource resource;

    @Override
    public void close() throws ItemStreamException {
        try {
            if (zipFile != null)
                zipFile.close();
        } catch (IOException ex) {
            throw new ItemStreamException(ex);
        }
    }

    @Override
    public void open(@NonNull ExecutionContext context) {
        try {
            zipFile = new ZipFile(resource.getFile());
            Resource[] resources = extractFiles(zipFile);
            this.setResources(resources);
        } catch (IOException ex) {
            System.err.println(String.format("Error reading archive file %s: Error message: %s",
                    resource.getFilename(), ex.getMessage()));
            ex.printStackTrace();
        }
        super.open(context);
    }

    private Resource[] extractFiles(final ZipFile zipFile) {
        return zipFile.stream().filter(zipEntry -> !zipEntry.isDirectory())
                .map(zipEntry -> {
                    try {
                        return new InputStreamResource(
                                zipFile.getInputStream(zipEntry),
                                zipEntry.getName());
                    } catch (IOException e) {
                        System.err.println(String.format("Error reading archive file %s: Error message: %s",
                                resource.getFilename(), e.getMessage()));
                        e.printStackTrace();
                    }
                    return null;
                }).toArray(Resource[]::new);
    }

    @Autowired
    public void setResource(@Value("${zip-file-path}") Resource resource) {
        this.resource = resource;
    }
}
