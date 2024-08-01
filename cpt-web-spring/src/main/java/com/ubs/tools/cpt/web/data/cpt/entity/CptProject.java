package com.ubs.tools.cpt.web.data.cpt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static com.ubs.tools.cpt.web.data.cpt.entity.CptProject.TABLE_NAME;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = TABLE_NAME)
@SequenceGenerator(name = CptProject.Generators.ID_GENERATOR)
public class CptProject {
    public static final String TABLE_NAME = "projects";

    public static class Generators {
        public static final String ID_GENERATOR =  "seq_projects_id";
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String CODE = "code";
        public static final String COLOR = "color";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
    }

    @Id
    @GeneratedValue(generator = Generators.ID_GENERATOR)
    @Column(name = Columns.ID)
    private Long id;

    @Column(name = Columns.NAME, unique = true, nullable = false)
    private String name;

    @Column(name = Columns.DESCRIPTION, nullable = false, length = 10240)
    private String description;

    @Column(name = Columns.CODE, nullable = false, unique = true, length = 16)
    private String code;

    @Column(name = Columns.COLOR, nullable = false, length = 6)
    private String color;

    @Column(name = Columns.START_DATE, nullable = true)
    private LocalDate startDate;

    @Column(name = Columns.END_DATE, nullable = true)
    private LocalDate endDate;
}
