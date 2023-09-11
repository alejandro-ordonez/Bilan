package org.bilan.co.domain.dtos.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UploadFromFileResultDto {
    List<UploadedUserResultDto> processed = new ArrayList<>();
    List<UploadedUserResultDto> skipped = new ArrayList<>();
    int totalReceived = 0;
}
