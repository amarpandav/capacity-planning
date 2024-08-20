package com.ubs.cpt.domain.base;

/**
 * @author Amar Pandav
 */
public final class FieldConstants {


    private FieldConstants() {
    }

    //Application Specific
    public static final int NAME = 50;
    public static final int POD_NAME = 100;
    public static final int GENERAL_50 = 50;
    public static final int ENUM_30 = 30;
    public static final int DESCRIPTION_SHORT = 500;
    public static final int DESCRIPTION = 4000;

    // Framework specific General Suffixes

    public static final int UUID_LEN = 32;
    public static final int USER_NAME_LEN = 35;
    public static final int STACK_TRACE_LEN = 5000;

    /// primary key constraints
    public static final String _PK = "_PK";
    // foreign key/single column indexes
    public static final String _IX = "_IX";
    // foreign key constraints
    public static final String _FK = "_FK";
    // not null constraints
    public static final String _NN = "_NN";
    // unique constraint (single column)
    public static final String _UK = "_UK";
    // enum check constraints
    public static final String _CK = "_CK";

    public static final String _SEQ = "_SEQ";
    public static final String _HST = "_HST";
    public static final String _I18N = "_I18N";

    // suffix for audit trigger on each ordinary table
    public static final String _ATR = "_ATR";
    // suffix for triggers to enforce conditional not null constraint
    public static final String _NNTR = "_NNTR";

    // suffix for language detail view
    public static final String _ALL_LANG_V = "_ALL_LANG_V";

    // General Lengths
    public static final int ENUM_DEFAULT_LEN = 30;
}
