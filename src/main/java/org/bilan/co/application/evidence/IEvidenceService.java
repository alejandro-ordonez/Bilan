package org.bilan.co.application.evidence;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.teacher.EvaluationDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.enums.Phase;
import org.bilan.co.domain.projections.IEvidence;
import org.bilan.co.domain.utils.Tuple;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IEvidenceService {

    ResponseDto<String> upload(Phase phase, Long tribeId, MultipartFile file, AuthenticatedUserDto authenticatedUser);

    ResponseDto<String> evaluate(AuthenticatedUserDto authenticatedUser, EvaluationDto evaluationDto);

    ResponseDto<List<IEvidence>> filter(EvidenceService.FilterEvidence filter, AuthenticatedUserDto user);

    Optional<Tuple<byte[], String>> download(String fileNameEvidence);
}
