package org.bilan.co.domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadedUserResultDto {
    String document;
    UploadResult uploadResult;
    String message;
}
