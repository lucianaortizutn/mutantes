package com.example.mutantes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mutant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Audited
public class Mutant extends Base {
    @Builder.Default
    private List<String> dna = new ArrayList<>();
    private boolean mutant;
}
