package top.potmot.entity.model.entities

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table
import org.hibernate.validator.constraints.Length

/**
 * 实体业务键组
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_entity_key_group")
interface GenEntityKeyGroup {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * 对应索引
     */
    @Key
    @OneToOne
    @JoinColumn(
        name = "index_id",
        referencedColumnName = "id"
    )
    @OnDissociate(DissociateAction.DELETE)
    @get:Valid
    val index: GenEntityIndex

    /**
     * 对应索引 ID View
     */
    @IdView("index")
    val indexId: Int

    /**
     * 名称
     */
    @Column(name = "name")
    @get:Length(max = 255)
    val name: String
}
