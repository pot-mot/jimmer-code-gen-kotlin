package top.potmot.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OneToMany
import top.potmot.constant.DataSourceType

/**
 * 生成数据源实体类
 *
 * @author potmot
 * @since 2023-08-14 15:28:59
 */
@Entity
interface GenDataSource {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    /**
     * 名称
     */
    @Key
    val name: String

    /**
     * URL
     */
    @Key
    val url: String

    /**
     * 用户名
     */
    val username: String

    /**
     * 密码
     */
    val password: String

    /**
     * 数据库类型
     */
    val type: DataSourceType

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 表
     */
    @OneToMany(mappedBy = "dataSource")
    val tables: List<GenTable>
}

