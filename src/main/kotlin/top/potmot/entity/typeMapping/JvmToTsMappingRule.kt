package top.potmot.entity.typeMapping

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.ManyToOne
import top.potmot.entity.model.JvmSource

@Entity
interface JvmToTsMappingRule {
    @Id
    val id: String

    val jvmSource: JvmSource

    val matchRegExp: String

    @ManyToOne
    val result: TsType
}
