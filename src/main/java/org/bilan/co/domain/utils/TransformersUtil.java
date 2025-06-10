package org.bilan.co.domain.utils;

import org.bilan.co.domain.dtos.user.ImportRecordDto;
import org.bilan.co.domain.entities.ImportRequests;

import java.util.List;
import java.util.stream.Collectors;

public class TransformersUtil {

    public static List<ImportRecordDto> getRequestsDto(List<ImportRequests> requests){
        return requests
                .stream()
                .collect(Collectors.groupingBy(
                        ImportRequests::getType,
                        Collectors.mapping(
                                ImportRequests::getImportId,
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> new ImportRecordDto(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();
    }
}
