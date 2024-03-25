package top.potmot.model.superTable

const val javaResult = """
[(top/potmot/BaseEntity.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.MappedSuperclass;

/**
 * @author 
 */
@MappedSuperclass
public interface BaseEntity {
    @ManyToOne
    @JoinColumn(
            name = "CREATE_USER_ID",
            referencedColumnName = "ID"
    )
    User createUser();

    @IdView("createUser")
    long createUserId();

    @ManyToOne
    @JoinColumn(
            name = "MODIFY_USER_ID",
            referencedColumnName = "ID"
    )
    User modifyUser();

    @IdView("modifyUser")
    long modifyUserId();
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
    @ManyToOne
    @JoinColumn(
        name = "CREATE_USER_ID",
        referencedColumnName = "ID"
    )
    val createUser: User

    @IdView("createUser")
    val createUserId: Long

    @ManyToOne
    @JoinColumn(
        name = "MODIFY_USER_ID",
        referencedColumnName = "ID"
    )
    val modifyUser: User

    @IdView("modifyUser")
    val modifyUserId: Long
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

    @Column(name = "NAME")
    val name: String
}
)]
"""
