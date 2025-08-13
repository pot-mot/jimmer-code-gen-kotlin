package top.potmot.entity.model.entities

import jakarta.validation.Valid
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
import org.hibernate.validator.constraints.Length

/**
 * 实体业务键组
 * 
 * @author potmot
 */
@Entity
interface GenEntityKeyGroup {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @get:Length(max = 255)
    val name: String
}
