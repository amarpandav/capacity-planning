package com.ubs.cpt.web.data.aura;

import com.ubs.cpt.core.user.FindUsersQuery;
import com.ubs.cpt.core.user.SystemUser;
import com.ubs.cpt.core.user.SystemUserSource;
import com.ubs.cpt.core.user.UserSystem;
import com.ubs.cpt.core.user.aura.AuraSpecificAttributes;
import com.ubs.cpt.core.user.aura.AuraUser;
import com.ubs.cpt.core.user.aura.AuraUserId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class AuraSystemUserSource implements SystemUserSource<AuraUserId> {
    private final AuraUserRepository auraUserRepository;

    public AuraSystemUserSource(AuraUserRepository auraUserRepository) {
        this.auraUserRepository = auraUserRepository;
    }

    @Override
    public UserSystem userSystem() {
        return UserSystem.AURA;
    }

    @Override
    public Stream<SystemUser> findUsers(FindUsersQuery query) {
        return auraUserRepository.findUsers(
            null,
            query.getFullNameLike().orElse(null),
            query.getEmailLike().orElse(null)
        ).map(AuraSystemUserSource::convertVo);
    }

    private static SystemUser convertVo(AuraUserVO vo) {
        return new AuraUser(
            new AuraUserId(vo.uuid()),
            vo.fullName(),
            vo.email(),
            new AuraSpecificAttributes(vo.pid())
        );
    }

    @Override
    public Optional<SystemUser> findUser(AuraUserId id) {
        return auraUserRepository.findUsers(id.uuid(), null, null)
            .findFirst()
            .map(AuraSystemUserSource::convertVo);
    }
}
