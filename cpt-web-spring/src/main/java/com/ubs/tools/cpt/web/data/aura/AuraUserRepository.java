package com.ubs.tools.cpt.web.data.aura;

import com.ubs.tools.cpt.web.data.aura.entity.AuraInternalUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

import static com.ubs.tools.cpt.shared.jpa.JpaQueryBuilder.jpaQuery;
import static com.ubs.tools.cpt.shared.jpa.JpaQueryBuilder.optionalParam;
import static com.ubs.tools.cpt.shared.sql.NativeSqlQueryFunctions.nativeSqlQuery;
import static com.ubs.tools.cpt.shared.sql.SqlQueryFunctions.include;
import static com.ubs.tools.cpt.shared.util.SqlUtil.likeUppercaseArgument;
import static com.ubs.tools.cpt.web.data.aura.AuraDataSourceConfiguration.AURA_PERSISTENCE_UNIT;

@Repository
public class AuraUserRepository {
    @PersistenceContext(unitName = AURA_PERSISTENCE_UNIT)
    private EntityManager em;

    @AuraTransactionalRO
    public Stream<AuraUserVO> findUsers(String uuid, String fullName, String email) {
        return jpaQuery(
            nativeSqlQuery("from InternalUser iu")
                .where(
                    include("iu.uuid = :uuid").whenNotNull(uuid),
                    include("upper(iu.fullName) LIKE :fullNameLike").whenNotNull(fullName),
                    include("upper(iu.email) LIKE :emailLike").whenNotNull(email)
                )
                .build()
        ).jpaParams(
             optionalParam("uuid", uuid),
             optionalParam("fullNameLike", likeUppercaseArgument(fullName)),
             optionalParam("emailLike", likeUppercaseArgument(email))
         )
         .buildJpaQuery(em, AuraInternalUser.class)
         .getResultStream()
         .map(iu -> new AuraUserVO(
             iu.getUuid(),
             iu.getPid(),
             iu.getFullName(),
             iu.getEmail()
         ));
    }

}
