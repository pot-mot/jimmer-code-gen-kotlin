package top.potmot.model;

import org.babyfish.jimmer.sql.*
import top.potmot.model.common.BaseEntity

/**
 * 字典类型表实体类
 *
 * @author potmot
 * @since 2023-05-06 19:10:24
 */
@Entity
interface SysDictType : BaseEntity {
    /**
     * 字典类型编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val sysDictTypeId: Long

    /**
     * 字典名称
     */
    val dictName: String

    /**
     * 字典类型
     */
    val dictType: String


    @OneToMany(mappedBy = "dictType")
    val dictData: List<SysDictData>


}
