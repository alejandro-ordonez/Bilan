package org.bilan.co.api;

import org.bilan.co.application.IEvidenceService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.enums.Phase;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/evidences")
public class EvidencesController {

    private final IEvidenceService evidenceService;
    private final JwtTokenUtil jwtTokenUtil;

    public EvidencesController(IEvidenceService evidenceService, JwtTokenUtil jwtTokenUtil) {
        this.evidenceService = evidenceService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseDto<String>> uploadEvidence(@RequestParam("phase") Phase phase,
                                                               @RequestParam("tribeId") Long tribeId,
                                                               @RequestPart("file") MultipartFile file,
                                                               @RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto authenticatedUser = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(evidenceService.uploadEvidence(phase, tribeId, file, authenticatedUser));
    }
}
