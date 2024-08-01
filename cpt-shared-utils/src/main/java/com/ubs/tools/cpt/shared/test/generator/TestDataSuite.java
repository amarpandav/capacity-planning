package com.ubs.tools.cpt.shared.test.generator;

import jakarta.persistence.EntityManager;

import java.util.Collections;

public interface TestDataSuite {
    /**
     * Returns a list of {@link TestDataSuite}s that are required to have been persisted before persisting this one.
     * <br>
     * <b>IMPORTANT</b>: Always use {@link TestDataCreator} to persist {@link TestDataSuite}s.
     * Never call {@link TestDataSuite#persistData(EntityManager)} directly.
     * <br>
     * Example:
     * <pre>{@code
     * @Override
     * Iterable<TestDataSuite> requiredDataSuites() {
     *   return List.of(new UserTestSuite(), new ParametrizedTestSuite("param1", 6));
     * }
     * }</pre>
     */
    default Iterable<TestDataSuite> requiredDataSuites() {
        return Collections.emptyList();
    }

    /**
     * This should be unique for each data suite.
     * If your {@link TestDataSuite} is parametrized, this function should be overridden.
     * <br>
     * Example:
     * <pre>{@code
     *  public record UserTestDataSuite(String userNameSeed) implements TestDataSuite {
     *    public final User USER_1 = createUser("1", "USER1" + userNameSeed);
     *    public final User USER_2 = createUser("2", "USER2" + userNameSeed);
     *
     *    @Override
     *    public String suiteId() {
     *      return super.suiteId() + "#" + userNameSeed();
     *    }
     *
     *    ...
     *  }
     * }</pre>
     */
    default String suiteId() {
        return getClass().getName();
    }

    /**
     * Do not use directly. Use via {@link TestDataCreator#persistSuite(TestDataSuite, EntityManager)}
     *
     * Example:
     * <pre>{@code
     * @Override
     * void persistData(EntityManager em) {
     *   em.persist(ENTITY_1);
     *   em.persist(ENTITY_2);
     * }
     * }</pre>
     */
    void persistData(EntityManager em);
}
