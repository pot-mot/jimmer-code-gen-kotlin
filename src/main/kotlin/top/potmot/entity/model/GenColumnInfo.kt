package top.potmot.entity.model

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.hibernate.validator.constraints.Length
import top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty
import top.potmot.entity.model.entities.properties.GenProperty

/**
 * 列信息
 * 
 * @author potmot
 */
@Entity
interface GenColumnInfo {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int

    /**
     * 列名
     */
    @get:Length(max = 255)
    val columnName: String

    /**
     * JdbcType 码值
     */
    val jdbcTypeCode: Int

    /**
     * 字面类型
     */
    @get:Length(max = 500)
    val rawType: String

    /**
     * 类型是否非空
     */
    val typeIsNotNull: Boolean

    /**
     * 长度
     */
    val dataSize: Int?

    /**
     * 精度
     */
    val numericPrecision: Int?

    /**
     * 列默认表达式
     */
    @get:Length(max = 500)
    val columnDefaultExp: String?

    /**
     * 复合类型属性
     * 
     * @see top.potmot.entity.model.embeddables.GenEmbeddableTypeProperty.columnInfo
     */
    @OneToMany(mappedBy = "columnInfo", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genEmbeddableTypeProperties: List<GenEmbeddableTypeProperty>

    /**
     * 复合类型属性 ID View
     */
    @IdView("genEmbeddableTypeProperties")
    val genEmbeddableTypePropertyIds: List<Int>

    /**
     * 属性
     * 
     * @see top.potmot.entity.model.entities.properties.GenProperty.columnInfo
     */
    @OneToMany(mappedBy = "columnInfo", orderedProps = [OrderedProp("id")])
    @get:Valid
    val genProperties: List<GenProperty>

    /**
     * 属性 ID View
     */
    @IdView("genProperties")
    val genPropertyIds: List<Int>
}
