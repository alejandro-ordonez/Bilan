package org.bilan.co.application;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.enums.Phase;
import org.springframework.web.multipart.MultipartFile;

public interface IEvidenceService {

    ResponseDto<String> uploadEvidence(Phase phase, Long tribeId, MultipartFile file, AuthenticatedUserDto authenticatedUser);
}
