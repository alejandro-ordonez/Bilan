package org.bilan.co.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.BasicInfo;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedUserDto extends BasicInfo {

    @NotNull
    private UserType userType;
    private Collection<? extends GrantedAuthority> GrantedAuthorities;

    public AuthenticatedUserDto(String document, UserType userType, DocumentType documentType) {
        this.document = document;
        this.userType = userType;
        this.documentType = documentType;
    }

    public AuthenticatedUserDto(String document, UserType userType, DocumentType documentType, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this(document, userType, documentType);
        this.GrantedAuthorities = grantedAuthorities;
    }
}
