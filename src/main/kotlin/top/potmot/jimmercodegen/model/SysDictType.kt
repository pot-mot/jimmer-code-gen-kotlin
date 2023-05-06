package top.potmot.jimmercodegen.model;
import java.time.LocalDateTime;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.OneToMany

/**
 * 字典类型表实体类
 *
 * @author potmot
 * @since 2023-05-06 19:10:24
 */
@Entity
interface SysDictType {
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
