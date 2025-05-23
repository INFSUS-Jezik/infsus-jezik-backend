package infsus.jezik.backend.model.db;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "professors")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Professor extends User {
    @Column(columnDefinition = "TEXT")
    private String bio;
}