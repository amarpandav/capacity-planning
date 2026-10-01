package com.ubs.cpt.service.repository;

import com.ubs.cpt.domain.entity.availability.AvailabilityType;
import com.ubs.cpt.domain.entity.pod.Pod;
import com.ubs.cpt.domain.entity.pod.PodAssignment;
import com.ubs.cpt.domain.entity.user.User;
import com.ubs.cpt.infra.test.base.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs the native unassign query against HSQLDB, so the SQL itself is tested (not a Mockito stub).
 */
@Transactional
public class PodAssignmentRepositoryTest extends TestBase {

    // Far-future weekdays (2099-03-02 is a Monday), clear of the generated test data.
    private static final LocalDate START = LocalDate.of(2099, 3, 2);
    private static final LocalDate END = LocalDate.of(2099, 3, 6);

    @Autowired
    PodAssignmentRepository podAssignmentRepository;

    @Autowired
    PodRepository podRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void getPodAssignmentForPodOnlyMatchesRequestedUsersAndDates() {
        List<User> users = userRepository.findAll();
        User user = users.get(0);
        User otherUser = users.get(1);
        Pod pod = podRepository.findAll().get(0);

        PodAssignment morningInRange = podAssignmentRepository.save(morning(START, user, pod));
        PodAssignment afternoonInRange = podAssignmentRepository.save(afternoon(END, user, pod));
        podAssignmentRepository.save(afternoon(START, otherUser, pod));          // user not requested
        podAssignmentRepository.save(afternoon(END.plusDays(3), user, pod));    // date after the range
        podAssignmentRepository.flush();

        List<PodAssignment> result = podAssignmentRepository.getPodAssignment(
                Set.of(user.getEntityId().getUuid()), pod.getEntityId().getUuid(), START, END);

        assertThat(result).containsExactly(morningInRange, afternoonInRange);
    }

    private static PodAssignment morning(LocalDate day, User user, Pod pod) {
        return new PodAssignment(day, user, AvailabilityType.POD_ASSIGNMENT, AvailabilityType.AVAILABLE, pod, null);
    }

    private static PodAssignment afternoon(LocalDate day, User user, Pod pod) {
        return new PodAssignment(day, user, AvailabilityType.AVAILABLE, AvailabilityType.POD_ASSIGNMENT, null, pod);
    }
}
