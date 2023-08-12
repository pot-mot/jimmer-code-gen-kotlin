package top.potmot.model.base

interface TreeNode<E : BaseEntity> {
    /**
     * 父 ID
     */
    val parentId: Long?

    /**
     * 父
     */
    val parent: E?

    /**
     * 子
     */
    val children: List<E>
}
