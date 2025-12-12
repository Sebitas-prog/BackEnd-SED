package com.sed.backend.infrastructure.adapters.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorageAdapter {

    private final Path rootLocation;

    public FileStorageAdapter(@Value("${storage.path:uploads}") String storagePath) {
        this.rootLocation = Paths.get(storagePath);
    }

    public String guardar(MultipartFile archivo) {
        try {
            Files.createDirectories(rootLocation);
            String filename = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path destination = rootLocation.resolve(filename);
            archivo.transferTo(destination);
            return destination.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo almacenar el archivo", ex);
        }
    }

    public byte[] leer(String ruta) {
        try {
            return Files.readAllBytes(Paths.get(ruta));
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo leer el archivo", ex);
        }
    }

    public void eliminarTodo() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}