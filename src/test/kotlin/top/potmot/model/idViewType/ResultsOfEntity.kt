package top.potmot.model.idViewType

const val javaResult = """
[(top/potmot/Source.java, package top.potmot;

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
@Table(name = "SOURCE")
public interface Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToOne
    @JoinColumn(
            name = "TARGET_ID",
            referencedColumnName = "ID"
    )
    Target target();

    @IdView("target")
    int targetId();
}
), (top/potmot/Target.java, package top.potmot;

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
@Table(name = "TARGET")
public interface Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id();

    @OneToOne(mappedBy = "target")
    @Nullable
    Source source();

    @IdView("source")
    @Nullable
    Long sourceId();
}
)]
"""

const val kotlinResult = """
[(top/potmot/Source.kt, package top.potmot

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
@Table(name = "SOURCE")
interface Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToOne
    @JoinColumn(
        name = "TARGET_ID",
        referencedColumnName = "ID"
    )
    val target: Target

    @IdView("target")
    val targetId: Int
}
), (top/potmot/Target.kt, package top.potmot

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
@Table(name = "TARGET")
interface Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Int

    @OneToOne(mappedBy = "target")
    val source: Source?

    @IdView("source")
    val sourceId: Long?
}
)]
"""
