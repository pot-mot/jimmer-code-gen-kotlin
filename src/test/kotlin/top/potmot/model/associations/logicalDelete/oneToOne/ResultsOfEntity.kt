package top.potmot.model.associations.logicalDelete.oneToOne

const val kotlinRealFkResult = """
[(main/kotlin/top/potmot/entity/User.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "USER")
interface User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToOne(mappedBy = "user")
    val userDetail: UserDetail?

    @IdView("userDetail")
    val userDetailId: Long?

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
), (main/kotlin/top/potmot/entity/UserDetail.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "USER_DETAIL")
interface UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToOne
    @JoinColumn(
        name = "USER_ID",
        referencedColumnName = "ID"
    )
    @get:Valid
    val user: User?

    @IdView("user")
    val userId: Long?

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
)]
"""

const val kotlinFakeFkResult = """
[(main/kotlin/top/potmot/entity/User.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "USER")
interface User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToOne(mappedBy = "user")
    val userDetail: UserDetail?

    @IdView("userDetail")
    val userDetailId: Long?

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
), (main/kotlin/top/potmot/entity/UserDetail.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "USER_DETAIL")
interface UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToOne
    @JoinColumn(
        name = "USER_ID",
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.REAL
    )
    val user: User?

    @IdView("user")
    val userId: Long?

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    val deleteFlag: Boolean
}
)]
"""

const val javaRealFkResult = """
[(java/top/potmot/entity/User.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "USER")
public interface User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToOne(mappedBy = "user")
    @Nullable
    UserDetail userDetail();

    @IdView("userDetail")
    @Nullable
    Long userDetailId();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
), (java/top/potmot/entity/UserDetail.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "USER_DETAIL")
public interface UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToOne
    @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "ID"
    )
    @Valid 
    @Nullable
    User user();

    @IdView("user")
    @Nullable
    Long userId();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
)]
"""

const val javaFakeFkResult = """
[(java/top/potmot/entity/User.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "USER")
public interface User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToOne(mappedBy = "user")
    @Nullable
    UserDetail userDetail();

    @IdView("userDetail")
    @Nullable
    Long userDetailId();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
), (java/top/potmot/entity/UserDetail.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ForeignKeyType;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.LogicalDeleted;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "USER_DETAIL")
public interface UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToOne
    @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.REAL
    )
    @Nullable
    User user();

    @IdView("user")
    @Nullable
    Long userId();

    @LogicalDeleted("true")
    @Column(name = "DELETE_FLAG")
    boolean deleteFlag();
}
)]
"""
