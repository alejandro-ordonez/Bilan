package org.bilan.co.domain.entities.builders;

import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.entities.Evidences;
import org.bilan.co.domain.entities.ResolvedAnswerBy;
import org.bilan.co.domain.entities.StudentStats;
import org.bilan.co.domain.entities.Students;

import java.util.List;

public class StudentsBuilder {
    private String document;
    private DocumentType documentType;
    private String name;
    private String email;
    private String lastName;
    private String password;
    private List<ResolvedAnswerBy> resolvedAnswerByList;
    private StudentStats studentStats;
    private List<Evidences> evidencesList;


    public StudentsBuilder setDocument(String document) {
        this.document = document;
        return this;
    }

    public StudentsBuilder setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public StudentsBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentsBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public StudentsBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public StudentsBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public StudentsBuilder setResolvedAnswerByList(List<ResolvedAnswerBy> resolvedAnswerByList) {
        this.resolvedAnswerByList = resolvedAnswerByList;
        return this;
    }

    public StudentsBuilder setStudentStats(StudentStats studentStats) {
        this.studentStats = studentStats;
        return this;
    }

    public StudentsBuilder setEvidencesList(List<Evidences> evidencesList) {
        this.evidencesList = evidencesList;
        return this;
    }

    public Students createStudents() {
        return new Students(name, lastName, document, documentType, email, password, resolvedAnswerByList,
                studentStats, evidencesList);
    }
}