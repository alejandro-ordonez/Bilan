package org.bilan.co.application.user;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.user.ImportRequestDto;
import org.bilan.co.domain.dtos.user.ImportResultDto;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.utils.Tuple;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IUserImportService {
    ResponseDto<ImportResultDto> importUsers(MultipartFile file, ImportType importType, String campusCodeDane, String token);

    ResponseDto<PagedResponse<ImportRequestDto>> getUserRequests(String token, int pageNumber);

    Optional<Tuple<byte[], String>> downloadRejectUsers(String importId);
}
