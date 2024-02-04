package top.potmot.model.associations.oneToOne

const val javaRealFkResult = """
[(top/potmot/OOSource.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "O_O_SOURCE")
public interface OOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToOne
    @JoinColumn(
            name = "TARGET_ID",
            referencedColumnName = "ID"
    )
    OOTarget oOTarget();
    
    @IdView("oOTarget")
    long oOTargetId();
}
), (top/potmot/OOTarget.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

/**
 * @author 
 */
@Entity
@Table(name = "O_O_TARGET")
public interface OOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToOne(mappedBy = "oOTarget")
    @Nullable
    OOSource oOSource();
    
    @IdView("oOSource")
    @Nullable
    Long oOSourceId();
}
)]
"""

const val javaFakeFkResult = """
[(top/potmot/OOSource.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "O_O_SOURCE")
public interface OOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToOne
    @JoinColumn(
            name = "TARGET_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.REAL
    )
    OOTarget oOTarget();
    
    @IdView("oOTarget")
    long oOTargetId();
}
), (top/potmot/OOTarget.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

/**
 * @author 
 */
@Entity
@Table(name = "O_O_TARGET")
public interface OOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToOne(mappedBy = "oOTarget")
    @Nullable
    OOSource oOSource();
    
    @IdView("oOSource")
    @Nullable
    Long oOSourceId();
}
)]
"""

const val kotlinRealFkResult = """
[(top/potmot/OOSource.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_O_SOURCE")
interface OOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToOne
    @JoinColumn(
        name = "TARGET_ID",
        referencedColumnName = "ID"
    )
    val oOTarget: OOTarget
    
    @IdView("oOTarget")
    val oOTargetId: Long
}
), (top/potmot/OOTarget.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_O_TARGET")
interface OOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToOne(mappedBy = "oOTarget")
    val oOSource: OOSource?
    
    @IdView("oOSource")
    val oOSourceId: Long?
}
)]
"""

const val kotlinFakeFkResult = """
[(top/potmot/OOSource.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_O_SOURCE")
interface OOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToOne
    @JoinColumn(
        name = "TARGET_ID",
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.REAL
    )
    val oOTarget: OOTarget
    
    @IdView("oOTarget")
    val oOTargetId: Long
}
), (top/potmot/OOTarget.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToOne
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "O_O_TARGET")
interface OOTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToOne(mappedBy = "oOTarget")
    val oOSource: OOSource?
    
    @IdView("oOSource")
    val oOSourceId: Long?
}
)]
"""
