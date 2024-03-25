package top.potmot.model.associations.fake.oneToMany

const val javaRealFkResult = """
[(top/potmot/OMSource.java, package top.potmot;

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
@Table(name = "O_M_SOURCE")
public interface OMSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "source")
    List<OMTarget> oMTargets();

    @IdView("oMTargets")
    List<Long> oMTargetIds();
}
), (top/potmot/OMTarget.java, package top.potmot;

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
import org.jetbrains.annotations.Nullable;

/**
 * @author 
 */
@Entity
@Table(name = "O_M_TARGET")
public interface OMTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToOne
    @JoinColumn(
            name = "SOURCE_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.FAKE
    )
    @Nullable
    OMSource source();

    @IdView("source")
    @Nullable
    Long sourceId();
}
)]
"""

const val javaFakeFkResult = """
[(top/potmot/OMSource.java, package top.potmot;

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
@Table(name = "O_M_SOURCE")
public interface OMSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "source")
    List<OMTarget> oMTargets();

    @IdView("oMTargets")
    List<Long> oMTargetIds();
}
), (top/potmot/OMTarget.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

/**
 * @author 
 */
@Entity
@Table(name = "O_M_TARGET")
public interface OMTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToOne
    @JoinColumn(
            name = "SOURCE_ID",
            referencedColumnName = "ID"
    )
    @Nullable
    OMSource source();

    @IdView("source")
    @Nullable
    Long sourceId();
}
)]
"""

const val kotlinRealFkResult = """
[(top/potmot/OMSource.kt, package top.potmot

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
@Table(name = "O_M_SOURCE")
interface OMSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "source")
    val oMTargets: List<OMTarget>

    @IdView("oMTargets")
    val oMTargetIds: List<Long>
}
), (top/potmot/OMTarget.kt, package top.potmot

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

/**
 * @author 
 */
@Entity
@Table(name = "O_M_TARGET")
interface OMTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToOne
    @JoinColumn(
        name = "SOURCE_ID",
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.FAKE
    )
    val source: OMSource?

    @IdView("source")
    val sourceId: Long?
}
)]
"""

const val kotlinFakeFkResult = """
[(top/potmot/OMSource.kt, package top.potmot

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
@Table(name = "O_M_SOURCE")
interface OMSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "source")
    val oMTargets: List<OMTarget>

    @IdView("oMTargets")
    val oMTargetIds: List<Long>
}
), (top/potmot/OMTarget.kt, package top.potmot

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
@Table(name = "O_M_TARGET")
interface OMTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToOne
    @JoinColumn(
        name = "SOURCE_ID",
        referencedColumnName = "ID"
    )
    val source: OMSource?

    @IdView("source")
    val sourceId: Long?
}
)]
"""
