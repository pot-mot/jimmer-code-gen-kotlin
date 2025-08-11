package top.potmot.entity.model.entities

import jakarta.validation.Valid
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OrderedProp
import org.babyfish.jimmer.sql.Table

/**
 * 实体图数据
 * 
 * @author potmot
 */
@Entity
@Table(name = "gen_entity_graph_data")
interface GenEntityGraphData {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int

    /**
     * X坐标
     */
    @Column(name = "x")
    val x: Double

    /**
     * Y坐标
     */
    @Column(name = "y")
    val y: Double

    /**
     * 实体
     * 
     * @see top.potmot.entity.model.entities.GenEntity.graphData
     */
    @OneToMany(mappedBy = "graphData", orderedProps = [OrderedProp("id")])
    @get:Valid
    val entities: List<GenEntity>

    /**
     * 实体 ID View
     */
    @IdView("entities")
    val entityIds: List<Int>
}
