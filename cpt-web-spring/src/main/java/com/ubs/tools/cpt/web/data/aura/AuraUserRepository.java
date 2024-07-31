package com.ubs.tools.cpt.web.data.aura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

import static com.ubs.tools.cpt.shared.jpa.JpaQueryBuilder.jpaQuery;
import static com.ubs.tools.cpt.shared.jpa.JpaQueryBuilder.optionalParam;
import static com.ubs.tools.cpt.shared.sql.NativeSqlQueryFunctions.nativeSqlQuery;
import static com.ubs.tools.cpt.shared.sql.SqlQueryFunctions.include;
import static com.ubs.tools.cpt.web.data.AuraDataSourceConfiguration.AURA_PERSISTENCE_UNIT;

@Repository
public class AuraUserRepository {
    @PersistenceContext(unitName = AURA_PERSISTENCE_UNIT)
    private EntityManager em;

    @AuraTransactionalRO
    public Stream<AuraUserVO> findUsers(String uuid, String fullName, String email) {
        return jpaQuery(
            nativeSqlQuery(
                """
                    SELECT iu.UUID      as uuid,    \s
                           iu.FULL_NAME as fullName,\s
                           iu.EMAIL     as email    \s
                    FROM INTERNAL_USER iu
                    """
            ).where(
                include("iu.uuid = :uuid").whenNotNull(uuid),
                include("upper(iu.FULL_NAME) LIKE :fullNameLike").whenNotNull(fullName),
                include("upper(iu.EMAIL) LIKE :emailLike").whenNotNull(email)
            ).build().sql(em)
        ).jpaParams(
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
