package org.bilan.co.domain.dtos.user.enums;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RejectedRow {
    String identifier;
    int lineInFile;
    String line;

    List<String> errors = new ArrayList<>();

    public RejectedRow(String identifier, int lineInFile, String line) {
        this.identifier = identifier;
        this.lineInFile = lineInFile;
        this.line = line;
    }

    public void addError(String title, String message) {
        this.errors.add("[%s: %s]".formatted(title, message));
    }

    public String getLineWithErrors(){
        return line + ", " + String.join(",", errors);
    }
}
