package org.bilan.co.application.files;

import java.io.InputStream;
import java.util.Map;

public interface IFileManager {
    void uploadFile(String path, String fileName,
                    Map<String, String> metadata, InputStream inputStream);

    byte[] downloadFile(String path, String fileName);
}
