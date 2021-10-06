package org.bilan.co.application;

import org.bilan.co.domain.dtos.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface IEvidenceService {

    ResponseDto<String> saveEvidence(MultipartFile file, String activityId, String jwt);

}
