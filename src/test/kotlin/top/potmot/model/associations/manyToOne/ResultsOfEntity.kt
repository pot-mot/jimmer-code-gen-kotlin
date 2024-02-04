package top.potmot.model.associations.manyToOne

const val javaRealFkResult = """
[(top/potmot/MOSource.java, package top.potmot;

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
public interface MOSource {
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
public interface MOTarget {
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

const val javaFakeFkResult = """
[(top/potmot/MOSource.java, package top.potmot;

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
public interface MOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @ManyToOne
    @JoinColumn(
            name = "SOURCE_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.REAL
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
public interface MOTarget {
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

const val kotlinRealFkResult = """
[(top/potmot/MOSource.kt, package top.potmot

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
interface MOSource {
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
interface MOTarget {
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

const val kotlinFakeFkResult = """
[(top/potmot/MOSource.kt, package top.potmot

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
interface MOSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @ManyToOne
    @JoinColumn(
        name = "SOURCE_ID",
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.REAL
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
interface MOTarget {
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
