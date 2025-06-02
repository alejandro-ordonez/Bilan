package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.user.IUserImportService;
import org.bilan.co.application.user.IUserService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.ImportRequestDto;
import org.bilan.co.domain.dtos.user.ImportResultDto;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserImportService userImportService;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserInfoDto>> getUserInfo(@RequestHeader(Constants.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(userService.getUserInfo(jwt));
    }

    @PostMapping("/info")
    public ResponseEntity<ResponseDto<String>> updateUserInfo(@RequestBody UserInfoDto userInfoDto,
                                                              @RequestHeader(Constants.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(userService.updateUserInfo(userInfoDto, jwt));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'DIRECT_TEACHER')")
    @PutMapping("/enable")
    public ResponseEntity<ResponseDto<Boolean>> update(@RequestBody EnableUser user) {
        return ResponseEntity.ok(userService.enableUser(user));
    }

    @PreAuthorize("hasAnyAuthority('DIRECT_TEACHER', 'ADMIN')")
    @PostMapping(path = "/import",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<ImportResultDto>> uploadUsersFromFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("importType") ImportType importType,
            @RequestParam(value = "campusCodeDane", required = false) String campusCode,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        ResponseDto<ImportResultDto> result = userImportService
                .importUsers(file, importType, campusCode, jwt);

        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PreAuthorize("hasAnyAuthority('DIRECT_TEACHER', 'ADMIN')")
    @GetMapping(path = "/import")
    public ResponseEntity<PagedResponse<ImportRequestDto>> getUserImportRequest(
            @RequestHeader(Constants.AUTHORIZATION) String jwt,
            @RequestParam("page") Integer nPage
    ) {
        ResponseDto<PagedResponse<ImportRequestDto>> result = userImportService.getUserRequests(jwt, nPage);
        return ResponseEntity.status(result.getCode()).body(result.getResult());
    }

    @GetMapping(path = "/import/rejected")
    public ResponseEntity<byte[]> downloadRejectedUsers(@RequestHeader(Constants.AUTHORIZATION) String jwt,
                                                        @RequestParam("importId") String importId) {

        return this.userImportService.downloadRejectUsers(importId)
                .map(data -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, data.getValue2())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + importId + ".csv")
                        .body(data.getValue1()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TEACHER', 'DIRECT_TEACHER')")
    @GetMapping
    public ResponseEntity<ResponseDto<PagedResponse<UserInfoDto>>> getUsers(
            @RequestParam("page") Integer nPage,
            @RequestParam(value = "partialDocument", required = false) String partialDocument,
            @RequestHeader(Constants.AUTHORIZATION) String jwt) {

        ResponseDto<PagedResponse<UserInfoDto>> users = userService.getUsersAdmin(nPage, partialDocument, jwt);
        return ResponseEntity.status(users.getCode()).body(users);
    }
}
