package org.bilan.co.domain.dtos.user;

import lombok.Builder;
import lombok.Data;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.enums.BucketName;

import java.io.File;
import java.util.List;
import java.util.function.Function;

@Builder
@Data
public class ImportRequest<T> {
    private final File file;
    private final String requestorId;
    private final int collegeId;
    private final int expectedColumns;
    private final ImportType type;
    private final Function<String[], T> converter;
    private final Function<T, List<String>> validation;
    private final BucketName bucket;
}
