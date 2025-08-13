package top.potmot.entity.database

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length
import top.potmot.enums.database.DatabaseType

/**
 * 数据源
 * 
 * @author potmot
 */
@Entity
interface DbDataSource {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 数据库类型
     */
    val type: DatabaseType

    /**
     * 名称
     */
    @get:Length(max = 500)
    val name: String

    /**
     * 链接
     */
    @get:Length(max = 500)
    val url: String

    /**
     * 用户名
     */
    @get:Length(max = 500)
    val username: String

    /**
     * 密码
     */
    @get:Length(max = 500)
    val password: String

    /**
     * 备注
     */
    @get:Length(max = 500)
    val remark: String

    /**
     * 数据架构
     * 
     * @see top.potmot.entity.database.DbSchema.dataSource
     */
    @OneToMany(mappedBy = "dataSource", orderedProps = [OrderedProp("id")])
    @get:Valid
    val dbSchemas: List<DbSchema>

    /**
     * 数据架构 ID View
     */
    @IdView("dbSchemas")
    val dbSchemaIds: List<Int>
}
