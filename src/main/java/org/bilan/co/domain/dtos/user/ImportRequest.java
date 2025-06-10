package org.bilan.co.domain.dtos.user;

import lombok.Builder;
import lombok.Data;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.enums.BucketName;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

@Builder
@Data
public class ImportRequest<T> {
    private final String requestId;
    private final ImportType importType;
    private final int expectedColumns;
    private final Function<String[], T> converter;
    private final Function<T, List<String>> validation;
    private final BucketName bucket;
}
