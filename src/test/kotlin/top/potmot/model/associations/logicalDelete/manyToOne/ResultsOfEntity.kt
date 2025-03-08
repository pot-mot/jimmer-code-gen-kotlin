package top.potmot.model.associations.logicalDelete.manyToOne

const val kotlinRealFkResult = """
[(main/kotlin/top/potmot/entity/Order.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "ORDER")
interface Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "order")
    val orderDetails: List<OrderDetail>

    @IdView("orderDetails")
    val orderDetailIds: List<Long>

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
), (main/kotlin/top/potmot/entity/OrderDetail.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "ORDER_DETAIL")
interface OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToOne
    @JoinColumn(
        name = "ORDER_ID",
        referencedColumnName = "ID"
    )
    @get:Valid
    val order: Order?

    @IdView("order")
    val orderId: Long?

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
)]
"""

const val kotlinFakeFkResult = """
[(main/kotlin/top/potmot/entity/Order.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "ORDER")
interface Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "order")
    val orderDetails: List<OrderDetail>

    @IdView("orderDetails")
    val orderDetailIds: List<Long>

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
), (main/kotlin/top/potmot/entity/OrderDetail.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "ORDER_DETAIL")
interface OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToOne
    @JoinColumn(
        name = "ORDER_ID",
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.REAL
    )
    val order: Order?

    @IdView("order")
    val orderId: Long?

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
)]
"""

const val javaRealFkResult = """
[(java/top/potmot/entity/Order.java, package top.potmot.entity;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

@Entity
@Table(name = "ORDER")
public interface Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails();

    @IdView("orderDetails")
    List<Long> orderDetailIds();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
), (java/top/potmot/entity/OrderDetail.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "ORDER_DETAIL")
public interface OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToOne
    @JoinColumn(
            name = "ORDER_ID",
            referencedColumnName = "ID"
    )
    @Valid 
    @Nullable
    Order order();

    @IdView("order")
    @Nullable
    Long orderId();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
)]
"""

const val javaFakeFkResult = """
[(java/top/potmot/entity/Order.java, package top.potmot.entity;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

@Entity
@Table(name = "ORDER")
public interface Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails();

    @IdView("orderDetails")
    List<Long> orderDetailIds();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
), (java/top/potmot/entity/OrderDetail.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ForeignKeyType;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "ORDER_DETAIL")
public interface OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToOne
    @JoinColumn(
            name = "ORDER_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.REAL
    )
    @Nullable
    Order order();

    @IdView("order")
    @Nullable
    Long orderId();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
)]
"""
