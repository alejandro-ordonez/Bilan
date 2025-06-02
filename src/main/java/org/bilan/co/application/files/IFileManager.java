package org.bilan.co.application.files;

import com.fasterxml.jackson.core.type.TypeReference;
import org.bilan.co.domain.dtos.user.ImportResultDto;
import org.bilan.co.domain.dtos.user.StagedImportRequestDto;
import org.bilan.co.domain.enums.BucketName;

import java.io.InputStream;
import java.nio.file.Path;

public interface IFileManager {
    String uploadFile(Path path, InputStream inputStream);

    String uploadEvidence(String fileName, InputStream inputStream);

    String uploadTeacherEnrollment(String fileName, InputStream inputStream);

    <T> String saveProcessedImport(StagedImportRequestDto<T> request);

    String saveRejectedImport(ImportResultDto request);

    byte[] downloadRejected(String importId, BucketName bucket);

    <T> T getFromJsonFile(String path, TypeReference<T> reference);
    byte[] downloadFile(String path, String fileName);
}
