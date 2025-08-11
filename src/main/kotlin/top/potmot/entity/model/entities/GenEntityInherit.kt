package top.potmot.entity.model.entities

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * 实体继承
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_entity_inherit")
interface GenEntityInherit {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 父级
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "parent_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val parent: GenEntity

    /**
     * 父级 ID View
     */
    @IdView("parent")
    val parentId: Int

    /**
     * 子级
     */
    @Key
    @ManyToOne
    @JoinColumn(
        name = "child_id",
        referencedColumnName = "id"
    )
    @get:Valid
    val child: GenEntity

    /**
     * 子级 ID View
     */
    @IdView("child")
    val childId: Int
}
