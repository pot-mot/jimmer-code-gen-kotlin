package top.potmot.model

import org.babyfish.jimmer.sql.*
import top.potmot.model.base.BaseEntity

/**
 * 生成实体实体类
 *
 * @author potmot
 * @since 2023-08-12 10:48:54
 */
@Entity
@Table(name = "jimmer-code-gen.gen_dto")
interface GenDto : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long

    /**
     * 对应实体 ID
     */
    @IdView
    val entityId: Long

    /**
     * 对应实体
     */
    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val entity: GenEntity

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * 属性 ID
     */
    @IdView("properties")
    val propertyIds: List<Long>

    /**
     * 属性
     */
    @OneToMany(mappedBy = "dto")
    val properties: List<GenDtoProperty>
}

