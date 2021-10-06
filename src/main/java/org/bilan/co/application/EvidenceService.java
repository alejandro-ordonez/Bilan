package org.bilan.co.application;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class EvidenceService implements IEvidenceService{


    @Override
    public ResponseDto<String> saveEvidence(MultipartFile file, String activityId, String jwt) {


        return null;
    }
}
