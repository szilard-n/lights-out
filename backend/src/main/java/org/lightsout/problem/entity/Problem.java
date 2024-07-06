package org.lightsout.problem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lightsout.problem.entity.converter.ListTextConverter;
import org.lightsout.solution.entity.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "problem")
public class Problem {

    @Id
    private UUID id;

    @Convert(converter = ListTextConverter.class)
    @Column(name = "grid", columnDefinition = "TEXT", nullable = false)
    @Builder.Default
    private List<List<Byte>> grid = new ArrayList<>();

    @Column(name = "size", nullable = false)
    private int size;

    @OneToOne(
            mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Solution solution;

}
