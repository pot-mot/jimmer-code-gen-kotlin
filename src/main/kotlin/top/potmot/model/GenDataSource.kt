package top.potmot.model

import org.babyfish.jimmer.Formula
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table
import top.potmot.enumeration.DataSourceType
import top.potmot.model.base.BaseEntity

/**
 * 生成数据源实体类
 *
 * @author potmot
 * @since 2023-08-14 23:07:56
 */
@Entity
@Table(name = "jimmer_code_gen.gen_data_source")
interface GenDataSource : BaseEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 数据库类型
     */
    @Key
    val type: DataSourceType

    /**
     * 名称
     */
    val name: String

    /**
     * 主机
     */
    @Key
    val host: String

    /**
     * 端口
     */
    @Key
    val port: String

    /**
     * 链接后缀
     */
    val urlSuffix: String

    @Formula(dependencies = ["host", "port", "urlSuffix", "type"])
    val url: String
        get() = "jdbc:${type.name.lowercase()}://${host}:${port}${urlSuffix}"

    /**
     * 用户名
     */
    @Key
    val username: String

    /**
     * 密码
     */
    val password: String

    /**
     * 备注
     */
    val remark: String

    /**
     * 架构
     */
    @OneToMany(mappedBy = "dataSource")
    val schemas: List<GenSchema>
}

