package top.potmot.model.superTable

const val javaResult = """
[(top/potmot/BaseEntity.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.MappedSuperclass;
import org.jetbrains.annotations.Nullable;

/**
 * @author 
 */
@MappedSuperclass
public interface BaseEntity {
    @ManyToOne(inputNotNull = true)
    @JoinColumn(
            name = "CREATE_USER_ID",
            referencedColumnName = "ID"
    )
    @Nullable
    User createUser();

    @IdView("createUser")
    @Nullable
    Long createUserId();

    @ManyToOne(inputNotNull = true)
    @JoinColumn(
            name = "MODIFY_USER_ID",
            referencedColumnName = "ID"
    )
    @Nullable
    User modifyUser();

    @IdView("modifyUser")
    @Nullable
    Long modifyUserId();
}
), (top/potmot/Resource.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "RESOURCE")
public interface Resource implements BaseEntity {
    @Id
    @Column(name = "ID")
    long id();

    @Column(name = "NAME")
    String name();

    @ManyToOne
    @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "ID"
    )
    User user();

    @IdView("user")
    long userId();
}
), (top/potmot/User.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "USER")
public interface User implements BaseEntity {
    @Id
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "modifyUser")
    List<BaseEntity> baseEntities();

    @IdView("baseEntities")
    List<Long> baseEntityIds();

    @OneToMany(mappedBy = "user")
    List<Resource> resources();

    @IdView("resources")
    List<Long> resourceIds();

    @Column(name = "NAME")
    String name();
}
)]
"""

const val kotlinResult = """
[(top/potmot/BaseEntity.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass

/**
 * @author 
 */
@MappedSuperclass
interface BaseEntity {
    @ManyToOne(inputNotNull = true)
    @JoinColumn(
        name = "CREATE_USER_ID",
        referencedColumnName = "ID"
    )
    val createUser: User?

    @IdView("createUser")
    val createUserId: Long?

    @ManyToOne(inputNotNull = true)
    @JoinColumn(
        name = "MODIFY_USER_ID",
        referencedColumnName = "ID"
    )
    val modifyUser: User?

    @IdView("modifyUser")
    val modifyUserId: Long?
}
), (top/potmot/Resource.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "RESOURCE")
interface Resource : BaseEntity {
    @Id
    @Column(name = "ID")
    val id: Long

    @Column(name = "NAME")
    val name: String

    @ManyToOne
    @JoinColumn(
        name = "USER_ID",
        referencedColumnName = "ID"
    )
    val user: User

    @IdView("user")
    val userId: Long
}
), (top/potmot/User.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "USER")
interface User : BaseEntity {
    @Id
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "modifyUser")
    val baseEntities: List<BaseEntity>

    @IdView("baseEntities")
    val baseEntityIds: List<Long>

    @OneToMany(mappedBy = "user")
    val resources: List<Resource>

    @IdView("resources")
    val resourceIds: List<Long>

    @Column(name = "NAME")
    val name: String
}
)]
"""
