package top.potmot.model.associations.real.manyToMany

const val javaRealFkResult = """
[(top/potmot/MNSource.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinTable;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_N_SOURCE")
public interface MNSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany
    @JoinTable(
            name = "MANY_TO_MANY_MAPPING",
            joinColumnName = "M_N_SOURCE_ID",
            inverseJoinColumnName = "M_N_TARGET_ID"
    )
    List<MNTarget> mNTargets();

    @IdView("mNTargets")
    List<Long> mNTargetIds();
}
), (top/potmot/MNTarget.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_N_TARGET")
public interface MNTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany(mappedBy = "mNTargets")
    List<MNSource> mNSources();

    @IdView("mNSources")
    List<Long> mNSourceIds();
}
)]
"""

const val javaFakeFkResult = """
[(top/potmot/MNSource.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ForeignKeyType;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.JoinTable;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_N_SOURCE")
public interface MNSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany
    @JoinTable(
            name = "MANY_TO_MANY_MAPPING",
            joinColumns = {
                    @JoinColumn(name = "M_N_SOURCE_ID", foreignKeyType = ForeignKeyType.REAL),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "M_N_TARGET_ID", foreignKeyType = ForeignKeyType.REAL),
            }
    )
    List<MNTarget> mNTargets();

    @IdView("mNTargets")
    List<Long> mNTargetIds();
}
), (top/potmot/MNTarget.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "M_N_TARGET")
public interface MNTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany(mappedBy = "mNTargets")
    List<MNSource> mNSources();

    @IdView("mNSources")
    List<Long> mNSourceIds();
}
)]
"""

const val kotlinRealFkResult = """
[(top/potmot/MNSource.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_N_SOURCE")
interface MNSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany
    @JoinTable(
        name = "MANY_TO_MANY_MAPPING",
        joinColumnName = "M_N_SOURCE_ID",
        inverseJoinColumnName = "M_N_TARGET_ID"
    )
    val mNTargets: List<MNTarget>

    @IdView("mNTargets")
    val mNTargetIds: List<Long>
}
), (top/potmot/MNTarget.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_N_TARGET")
interface MNTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany(mappedBy = "mNTargets")
    val mNSources: List<MNSource>

    @IdView("mNSources")
    val mNSourceIds: List<Long>
}
)]
"""

const val kotlinFakeFkResult = """
[(top/potmot/MNSource.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_N_SOURCE")
interface MNSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany
    @JoinTable(
        name = "MANY_TO_MANY_MAPPING",
        joinColumns = [
            JoinColumn(name = "M_N_SOURCE_ID", foreignKeyType = ForeignKeyType.REAL),
        ],
        inverseJoinColumns = [
            JoinColumn(name = "M_N_TARGET_ID", foreignKeyType = ForeignKeyType.REAL),
        ]
    )
    val mNTargets: List<MNTarget>

    @IdView("mNTargets")
    val mNTargetIds: List<Long>
}
), (top/potmot/MNTarget.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "M_N_TARGET")
interface MNTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany(mappedBy = "mNTargets")
    val mNSources: List<MNSource>

    @IdView("mNSources")
    val mNSourceIds: List<Long>
}
)]
"""
