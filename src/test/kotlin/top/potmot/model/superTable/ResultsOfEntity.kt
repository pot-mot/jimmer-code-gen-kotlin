package top.potmot.model.superTable

const val javaResult = """
[(top/potmot/BaseEntity1.java, package top.potmot;

import java.time.LocalDateTime;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.MappedSuperclass;

/**
 * @author 
 */
@MappedSuperclass
public interface BaseEntity1 {
    @Column(name = "CREATE_TIME")
    LocalDateTime createTime();
}
), (top/potmot/BaseEntity2.java, package top.potmot;

import java.time.LocalDateTime;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.MappedSuperclass;

/**
 * @author 
 */
@MappedSuperclass
public interface BaseEntity2 {
    @Column(name = "UPDATE_TIME")
    LocalDateTime updateTime();
}
), (top/potmot/MOSource.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_O_SOURCE")
public interface MOSource implements BaseEntity1, BaseEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToOne
    @JoinColumn(
            name = "SOURCE_ID",
            referencedColumnName = "ID"
    )
    MOTarget mOTarget();

    @IdView("mOTarget")
    long mOTargetId();
}
), (top/potmot/MOTarget.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_O_TARGET")
public interface MOTarget implements BaseEntity1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "mOTarget")
    List<MOSource> mOSources();

    @IdView("mOSources")
    List<Long> mOSourceIds();
}
)]
"""

const val kotlinResult = """
[(top/potmot/BaseEntity1.kt, package top.potmot

import java.time.LocalDateTime
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.MappedSuperclass

/**
 * @author 
 */
@MappedSuperclass
interface BaseEntity1 {
    @Column(name = "CREATE_TIME")
    val createTime: LocalDateTime
}
), (top/potmot/BaseEntity2.kt, package top.potmot

import java.time.LocalDateTime
import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.MappedSuperclass

/**
 * @author 
 */
@MappedSuperclass
interface BaseEntity2 {
    @Column(name = "UPDATE_TIME")
    val updateTime: LocalDateTime
}
), (top/potmot/MOSource.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_O_SOURCE")
interface MOSource : BaseEntity1, BaseEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToOne
    @JoinColumn(
        name = "SOURCE_ID",
        referencedColumnName = "ID"
    )
    val mOTarget: MOTarget

    @IdView("mOTarget")
    val mOTargetId: Long
}
), (top/potmot/MOTarget.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_O_TARGET")
interface MOTarget : BaseEntity1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "mOTarget")
    val mOSources: List<MOSource>

    @IdView("mOSources")
    val mOSourceIds: List<Long>
}
)]
"""
