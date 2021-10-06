package org.bilan.co.api;

import org.bilan.co.application.IEvidenceService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/evidences")
public class EvidencesController {

    private final IEvidenceService evidenceService;

    public EvidencesController(IEvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping
    private ResponseEntity<ResponseDto<String>> uploadEvidence(@RequestParam("evidence") MultipartFile file,
                                                               @RequestParam("activityId") String activityId,
                                                               @RequestHeader(Constants.AUTHORIZATION) String jwt){

        return ResponseEntity.ok(evidenceService.saveEvidence(file, activityId, jwt));
    }

}
