package com.amarpandav.cpt.domain.entity.org;

import com.amarpandav.cpt.domain.BaseEntity;
import com.amarpandav.cpt.domain.base.FieldConstants;
import jakarta.persistence.*;

/**
 * Entity representing cpt_org_unit table. Org units form a hierarchy (division, department, team) via parent.
 * <p>
 * Codes are hierarchical, e.g. TEC, TEC-PLT, TEC-PLT-01, so everything below a unit can be found by code prefix.
 *
 * @author Amar Pandav
 */
@Entity
@Table(
        name = OrgUnit.TABLE_NAME
)
public class OrgUnit extends BaseEntity<OrgUnit> {
    public static final String TABLE_NAME = "cpt_org_unit";

    public static final class Columns {
        public static final String ORG_UNIT_CODE = "org_unit_code";
        public static final String ORG_UNIT_NAME = "org_unit_name";
        public static final String PARENT_UUID = "parent_uuid";
    }

    @Column(name = Columns.ORG_UNIT_CODE, length = FieldConstants.ORG_UNIT_CODE, nullable = false, unique = true)
    private String orgUnitCode;

    @Column(name = Columns.ORG_UNIT_NAME, length = FieldConstants.ORG_UNIT_NAME, nullable = false)
    private String orgUnitName;

    /**
     * Parent org unit, null for top level units (divisions).
     */
    @ManyToOne
    @JoinColumn(name = Columns.PARENT_UUID)
    private OrgUnit parent;

    protected OrgUnit() {// required by JPA
    }

    public OrgUnit(String orgUnitCode, String orgUnitName, OrgUnit parent) {
        this.orgUnitCode = orgUnitCode;
        this.orgUnitName = orgUnitName;
        this.parent = parent;
    }

    public String getOrgUnitCode() {
        return orgUnitCode;
    }

    public String getOrgUnitName() {
        return orgUnitName;
    }

    public OrgUnit getParent() {
        return parent;
    }
}
