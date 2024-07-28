package com.ubs.tools.cpt.web.data.aura.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import static com.ubs.tools.cpt.web.data.aura.entity.InternalUser.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class InternalUser {
    public static final String TABLE_NAME = "INTERNAL_USER";

    public static final class Columns {
        public static final String UUID = "UUID";
        public static final String FULL_NAME = "FULL_NAME";
        public static final String EMAIL = "EMAIL";
    }

    @Id
    @Column(name = Columns.UUID)
    private String uuid;

    @Column(name = Columns.FULL_NAME, nullable = false)
    private String fullName;

    @Column(name = Columns.EMAIL, nullable = false)
    private String email;
}
