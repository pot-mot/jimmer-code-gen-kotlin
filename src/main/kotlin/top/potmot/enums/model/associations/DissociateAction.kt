package top.potmot.enums.model.associations


/**
 * 脱钩行为
 */
enum class DissociateAction {
    /**
     * NONE
     */
    NONE,

    /**
     * LAX
     */
    LAX,

    /**
     * CHECK
     */
    CHECK,

    /**
     * SET_NULL
     */
    SET_NULL,

    /**
     * DELETE
     */
    DELETE,
}
