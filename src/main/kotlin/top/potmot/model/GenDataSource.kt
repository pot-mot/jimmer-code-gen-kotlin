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
 * @since 2023-08-14 23:07:56
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
     * 用户名
     */
    val username: String

    /**
     * 密码
     */
    val password: String

    /**
     * 自定排序
     */
    val orderKey: Long

    /**
     * 架构
     */
    @OneToMany(mappedBy = "dataSource")
    val schemas: List<GenSchema>
}

