package org.bilan.co.api;

import org.bilan.co.application.evidence.EvidenceService;
import org.bilan.co.application.evidence.IEvidenceService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.teacher.EvaluationDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.enums.Phase;
import org.bilan.co.domain.projections.IEvidence;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/evidences")
public class EvidencesController {

    private final IEvidenceService evidenceService;
    private final JwtTokenUtil jwtTokenUtil;

    public EvidencesController(IEvidenceService evidenceService, JwtTokenUtil jwtTokenUtil) {
        this.evidenceService = evidenceService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<String>> upload(@RequestParam("phase") Phase phase,
                                                      @RequestParam("tribeId") Long tribeId,
                                                      @RequestPart("file") MultipartFile file,
                                                      @RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(evidenceService.upload(phase, tribeId, file, user));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/evaluate")
    public ResponseEntity<ResponseDto<String>> evaluate(@RequestBody EvaluationDto evaluationDto,
                                                        @RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        return ResponseEntity.ok(this.evidenceService.evaluate(user, evaluationDto));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<IEvidence>>> filter(
            @RequestParam("grade") String grade,
            @RequestParam("tribeId") Integer tribeId,
            @RequestParam("courseId") Integer courseId,
            @RequestParam("phase") Phase phase,
            @RequestHeader(Constants.AUTHORIZATION) String token) {
        AuthenticatedUserDto user = jwtTokenUtil.getInfoFromToken(token);
        EvidenceService.FilterEvidence filter = new EvidenceService.FilterEvidence(grade, tribeId, courseId, phase);
        return ResponseEntity.ok(this.evidenceService.filter(filter, user));
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/download/{id}")
    public byte[] download(@PathVariable("id") Long id,
                           @RequestHeader(Constants.AUTHORIZATION) String token) {
        return this.evidenceService.download(id);
    }
}
