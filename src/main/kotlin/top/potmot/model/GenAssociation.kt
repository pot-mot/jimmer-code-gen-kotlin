package top.potmot.model

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.constant.AssociationType

/**
 * 生成关联实体类
 *
 * @author potmot
 * @since 2023-08-06 17:19:36
 */
@Entity
interface GenAssociation {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 关联描述
     */
    val associationComment: String

    /**
     * 主实体 ID
     */
    @IdView
    val sourceEntityId: Long

    /**
     * 主实体
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val sourceEntity: GenEntity

    /**
     * 主属性 ID
     */
    @IdView
    val sourcePropertyId: Long

    /**
     * 主属性
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val sourceProperty: GenProperty

    /**
     * 从实体 ID
     */
    @IdView
    val targetEntityId: Long

    /**
     * 从实体
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val targetEntity: GenEntity

    /**
     * 从属性 ID
     */
    @IdView
    val targetPropertyId: Long

    /**
     * 从属性
     */
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val targetProperty: GenProperty

    /**
     * 关联类型（OneToOne, ManyToOne, OneToMany, ManyToMany）
     */
    val associationType: AssociationType

    /**
     * 关联表达式
     */
    val associationExpress: String

    /**
     * 自定排序
     */
    val orderKey: Long

}

