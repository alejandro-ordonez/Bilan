package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.bilan.co.domain.enums.GameCycleStatus;

import java.util.Date;

@Entity
@Data
public class GameCycles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String gameId;
    Date startDate;
    Date endDate;

    @Enumerated(EnumType.STRING)
    GameCycleStatus gameStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closing_requested_by", referencedColumnName = "document")
    @JsonIgnore
    private UserInfo closingRequestedBy;
}
