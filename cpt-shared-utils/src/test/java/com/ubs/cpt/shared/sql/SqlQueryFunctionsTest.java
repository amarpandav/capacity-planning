package com.ubs.cpt.shared.sql;

import org.junit.jupiter.api.Test;

import static com.ubs.cpt.shared.sql.NullsOrder.NULLS_FIRST;
import static com.ubs.cpt.shared.sql.SqlQueryFunctions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SqlQueryFunctionsTest {
    private static void assertTrimmedEquals(String expected, String actual) {
        System.out.println(actual);
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    public void select_empty_throws() {
        assertThrows(IllegalArgumentException.class, () -> fromTables());
    }

    @Test
    public void select_one_table_star() {
        var t1 = from("TABLE_1", "t1");

        String sql = fromTables(t1)
            .noConditions()
            .selectFields(t1.allFields())
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.*
                FROM TABLE_1 t1
                """,
            sql);
    }

    @Test
    public void select_one_table_alias() {
        var t1 = from("TABLE_1", "t1");
        var t1_uuid = t1.field("UUID");

        String sql = fromTables(t1)
            .noConditions()
            .selectFields(t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID
                FROM TABLE_1 t1
                """,
            sql);
    }

    @Test
    public void select_two_tables_alias() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");
        var t1_uuid = t1.field("UUID");
        var t2_uuid = t2.field("UUID");

        String sql = fromTables(t1, t2)
            .noConditions()
            .selectFields(t1_uuid, t2_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID
                FROM TABLE_1 t1, TABLE_2 t2
                """,
            sql);
    }

    @Test
    public void select_two_tables_one_join_alias() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");
        var t1_uuid = t1.field("UUID");
        var t2_uuid = t2.field("UUID");


        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));

        String sql = fromTables(t1, t2, j1)
            .noConditions()
            .selectFields(t1_uuid, t2_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                """,
            sql);
    }

    @Test
    public void select_two_tables_one_join_select_alias() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");
        var t1_uuid = t1.field("UUID");
        var t2_uuid = t2.field("UUID");


        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .noConditions()
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                """,
            sql);
    }

    @Test
    public void select_two_tables_one_join_select_alias_where_t1_name_like_param() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");

        var t1_uuid = t1.field("UUID");
        var t1_name = t1.field("NAME");
        var t2_uuid = t2.field("UUID");


        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .where(t1_name.like(param("nameLike")))
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                WHERE t1.NAME LIKE :nameLike
                """,
            sql);
    }

    @Test
    public void select_two_tables_one_join_select_alias_where_t1_name_like_param_and_t2_code_equals() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");

        var t1_uuid = t1.field("UUID");
        var t1_name = t1.field("NAME");
        var t2_uuid = t2.field("UUID");
        var t2_code = t2.field("CODE");

        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .where(
                t1_name.like(param("nameLike")),
                t2_code.eq(param("code"))
            )
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                WHERE ((t1.NAME LIKE :nameLike) AND (t2.CODE = :code))
                """,
            sql);
    }

    @Test
    public void order_by_one_alias() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");

        var t1_uuid = t1.field("UUID");
        var t1_name = t1.field("NAME");
        var t2_uuid = t2.field("UUID");
        var t2_code = t2.field("CODE");

        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .where(
                t1_name.like(param("nameLike")),
                t2_code.eq(param("code"))
            )
            .orderBy(t1_uuid)
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                WHERE ((t1.NAME LIKE :nameLike) AND (t2.CODE = :code))
                ORDER BY t1.UUID
                """,
            sql);
    }

    @Test
    public void order_by_one_alias_desc() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");

        var t1_uuid = t1.field("UUID");
        var t1_name = t1.field("NAME");
        var t2_uuid = t2.field("UUID");
        var t2_code = t2.field("CODE");

        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .where(
                t1_name.like(param("nameLike")),
                t2_code.eq(param("code"))
            )
            .orderBy(t1_uuid.desc(NULLS_FIRST))
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                WHERE ((t1.NAME LIKE :nameLike) AND (t2.CODE = :code))
                ORDER BY t1.UUID DESC NULLS FIRST
                """,
            sql);
    }

    @Test
    public void order_by_two_aliases() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");

        var t1_uuid = t1.field("UUID");
        var t1_name = t1.field("NAME");
        var t2_uuid = t2.field("UUID");
        var t2_code = t2.field("CODE");

        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .where(
                t1_name.like(param("nameLike")),
                t2_code.eq(param("code"))
            )
            .orderBy(t1_uuid, t2_code)
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                WHERE ((t1.NAME LIKE :nameLike) AND (t2.CODE = :code))
                ORDER BY t1.UUID, t2.CODE
                """,
            sql);
    }

    @Test
    public void order_by_two_aliases_asc_desc() {
        var t1 = from("TABLE_1", "t1");
        var t2 = from("TABLE_2", "t2");

        var t1_uuid = t1.field("UUID");
        var t1_name = t1.field("NAME");
        var t2_uuid = t2.field("UUID");
        var t2_code = t2.field("CODE");

        var j1 = innerJoin("JOIN_TABLE_1", "j1")
            .on(j1_ -> j1_.field("TABLE_1_UUID").eq(t1_uuid));
        var j1_t1_uuid = j1.field("TABLE_1_UUID");

        String sql = fromTables(t1, t2, j1)
            .where(
                t1_name.like(param("nameLike")),
                t2_code.eq(param("code"))
            )
            .orderBy(t1_uuid.asc(NULLS_FIRST), t2_code.desc(NULLS_FIRST))
            .selectFields(t1_uuid, t2_uuid, j1_t1_uuid)
            .sql(SqlDialect.GENERIC);

        assertTrimmedEquals(
            """
                SELECT t1.UUID, t2.UUID, j1.TABLE_1_UUID
                FROM TABLE_1 t1, TABLE_2 t2
                JOIN JOIN_TABLE_1 j1 ON (j1.TABLE_1_UUID = t1.UUID)
                WHERE ((t1.NAME LIKE :nameLike) AND (t2.CODE = :code))
                ORDER BY t1.UUID ASC NULLS FIRST, t2.CODE DESC NULLS FIRST
                """,
            sql);
    }
}