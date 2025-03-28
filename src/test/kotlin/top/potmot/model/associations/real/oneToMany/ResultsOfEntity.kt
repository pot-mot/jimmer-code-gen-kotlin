package top.potmot.model.associations.real.oneToMany

const val kotlinRealFkResult = """
[(main/kotlin/top/potmot/entity/Order.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
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
}
), (main/kotlin/top/potmot/entity/OrderDetail.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
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
    val order: Order

    @IdView("order")
    val orderId: Long
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
    val order: Order

    @IdView("order")
    val orderId: Long
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
}
), (java/top/potmot/entity/OrderDetail.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

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
    Order order();

    @IdView("order")
    long orderId();
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
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

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
    Order order();

    @IdView("order")
    long orderId();
}
)]
"""
