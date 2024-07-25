package com.ubs.tools.cpt.web.data.aura;

import com.ubs.tools.cpt.shared.sql.FieldSelector;
import com.ubs.tools.cpt.shared.sql.FromClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

import static com.ubs.tools.cpt.shared.jpa.JpaQueryBuilder.jpaQuery;
import static com.ubs.tools.cpt.shared.jpa.JpaQueryBuilder.optionalParam;
import static com.ubs.tools.cpt.shared.sql.OracleFunctions.toUpper;
import static com.ubs.tools.cpt.shared.sql.SqlQueryFunctions.*;
import static com.ubs.tools.cpt.web.data.AuraDataSourceConfiguration.AURA_PERSISTENCE_UNIT;

@Repository
public class AuraUserRepository {
    @PersistenceContext(unitName = AURA_PERSISTENCE_UNIT)
    private EntityManager em;

    @AuraTransactionalRO
    public Stream<AuraUserVO> findUsers(String uuid, String fullName, String email) {
        var iuTable = InternalUserTable.withAlias("iu");

        FromClause iu = iuTable.fromClause();
        FieldSelector iu_uuid = iuTable.uuid();
        FieldSelector iu_fullName = iuTable.fullName();
        FieldSelector iu_email = iuTable.email();

        return jpaQuery(fromTables(iu)
            .where(
                include(
                    iu_uuid.eq(param("uuid"))
                ).whenNotNull(uuid),
                include(
                    toUpper(iu_fullName).like(param("fullNameLike"))
                ).whenNotNull(fullName),
                include(
                    toUpper(iu_email).like(param("emailLike"))
                ).whenNotNull(email)
            )
            .selectFields(
                iu_uuid.as("uuid"),
                iu_fullName.as("fullName"),
                iu_email.as("email")
            ))
            .jpaParams(
                optionalParam("uuid", uuid),
                optionalParam("fullNameLike", likeParam(fullName)),
                optionalParam("emailLike", likeParam(email))
            )
            .getResultStream(em)
            .map(tuple -> new AuraUserVO(
                tuple.get("uuid", String.class),
                tuple.get("fullName", String.class),
                tuple.get("email", String.class)
            ));
    }

    private static String likeParam(String value) {
        if (value == null) {
            return null;
        }

        return "%" + value.trim().toUpperCase() + "%";
    }
}
