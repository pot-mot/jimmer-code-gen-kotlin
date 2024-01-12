package top.potmot.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.enumeration.GenLanguage
import top.potmot.service.ModelService
import top.potmot.service.PreviewService
import top.potmot.util.replaceSinceTimeComment

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToEntity (
    @Autowired val modelService: ModelService,
    @Autowired val previewService: PreviewService
) {
    @Test
    @Order(1)
    fun testCreateJavaEntity() {
        val model = getBaseModel()

        model.language = GenLanguage.JAVA

        val id = modelService.save(model)

        val entityCodes = previewService.previewModelEntity(id, true)

        assertEquals(
            javaResult.trim(),
            entityCodes.toString().trim().replaceSinceTimeComment()
        )
    }

    @Test
    @Order(2)
    fun testCreateKotlinEntity() {
        val model = getBaseModel()

        model.language = GenLanguage.KOTLIN

        val id = modelService.save(model)

        val entityCodes = previewService.previewModelEntity(id, true)

        assertEquals(
            kotlinResult.trim(),
            entityCodes.toString().trim().replaceSinceTimeComment()
        )
    }

    private val javaResult = """
[(top/potmot/CommentTable.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Table;

/**
 * 注释测试
 * 
 * @author 
 */
@Entity
@Table(name = "COMMENT_TABLE")
public interface CommentTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    /**
     * 名称
     */
    @Column(name = "NAME")
    String name();
}
), (top/potmot/EnumCommon.java, package top.potmot;


public enum EnumCommon {
    item1,
    
    item2,
}
), (top/potmot/EnumName.java, package top.potmot;

import org.babyfish.jimmer.sql.EnumType;
import org.babyfish.jimmer.sql.EnumItem;

@EnumType(EnumType.Strategy.NAME)
public enum EnumName {
    @EnumItem(name = "item1")
    item1,
    
    @EnumItem(name = "item2")
    item2,
}
), (top/potmot/EnumOrdinal.java, package top.potmot;

import org.babyfish.jimmer.sql.EnumType;
import org.babyfish.jimmer.sql.EnumItem;

@EnumType(EnumType.Strategy.ORDINAL)
public enum EnumOrdinal {
    @EnumItem(ordinal = 0)
    item1,
    
    @EnumItem(ordinal = 1)
    item2,
}
), (top/potmot/EnumTable.java, package top.potmot;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "ENUM_TABLE")
public interface EnumTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @Column(name = "COMMON_ENUM")
    EnumCommon commonEnum();
    
    @Column(name = "ORDINAL_ENUM")
    EnumOrdinal ordinalEnum();
    
    @Column(name = "NAME_ENUM")
    EnumName nameEnum();
}
), (top/potmot/MNSource.java, package top.potmot;

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
        name = "M_N_SOURCE_M_N_TARGET_MAPPING",
        joinColumnName = "M_N_SOURCE_ID",
        inverseJoinColumnName = "M_N_TARGET_ID"
    )
    List<MNTarget> mNTargets();
    
    @IdView("mNTargets")
    List<long> mNTargetIds();
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
    List<long> mNSourceIds();
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
    List<long> mOSourceIds();
}
), (top/potmot/OMSource.java, package top.potmot;

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
    
    @OneToMany(mappedBy = "oMSource")
    List<OMTarget> oMTargets();
    
    @IdView("oMTargets")
    List<long> oMTargetIds();
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
        name = "ID",
        referencedColumnName = "SOURCE_ID"
    )
    OMSource oMSource();
    
    @IdView("oMSource")
    long oMSourceId();
}
), (top/potmot/OOSource.java, package top.potmot;

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
), (top/potmot/TreeNode.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "TREE_NODE")
public interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();
    
    @OneToMany(mappedBy = "treeNode")
    List<TreeNode> treeNodes();
    
    @IdView("treeNodes")
    List<long> treeNodeIds();
    
    @ManyToOne
    @JoinColumn(
        name = "PARENT_ID",
        referencedColumnName = "ID"
    )
    TreeNode treeNode();
    
    @IdView("treeNode")
    long treeNodeId();
}
)]
    """

    private val kotlinResult = """
[(top/potmot/CommentTable.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Table

/**
 * 注释测试
 * 
 * @author 
 */
@Entity
@Table(name = "COMMENT_TABLE")
interface CommentTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    /**
     * 名称
     */
    @Column(name = "NAME")
    val name: String
}
), (top/potmot/EnumCommon.kt, package top.potmot


enum class EnumCommon {
    item1,
    
    item2,
}
), (top/potmot/EnumName.kt, package top.potmot

import org.babyfish.jimmer.sql.EnumType
import org.babyfish.jimmer.sql.EnumItem

@EnumType(EnumType.Strategy.NAME)
enum class EnumName {
    @EnumItem(name = "item1")
    item1,
    
    @EnumItem(name = "item2")
    item2,
}
), (top/potmot/EnumOrdinal.kt, package top.potmot

import org.babyfish.jimmer.sql.EnumType
import org.babyfish.jimmer.sql.EnumItem

@EnumType(EnumType.Strategy.ORDINAL)
enum class EnumOrdinal {
    @EnumItem(ordinal = 0)
    item1,
    
    @EnumItem(ordinal = 1)
    item2,
}
), (top/potmot/EnumTable.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "ENUM_TABLE")
interface EnumTable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @Column(name = "COMMON_ENUM")
    val commonEnum: EnumCommon
    
    @Column(name = "ORDINAL_ENUM")
    val ordinalEnum: EnumOrdinal
    
    @Column(name = "NAME_ENUM")
    val nameEnum: EnumName
}
), (top/potmot/MNSource.kt, package top.potmot

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
        name = "M_N_SOURCE_M_N_TARGET_MAPPING",
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
), (top/potmot/OMSource.kt, package top.potmot

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
    
    @OneToMany(mappedBy = "oMSource")
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
        name = "ID",
        referencedColumnName = "SOURCE_ID"
    )
    val oMSource: OMSource
    
    @IdView("oMSource")
    val oMSourceId: Long
}
), (top/potmot/OOSource.kt, package top.potmot

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
), (top/potmot/TreeNode.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "TREE_NODE")
interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long
    
    @OneToMany(mappedBy = "treeNode")
    val treeNodes: List<TreeNode>
    
    @IdView("treeNodes")
    val treeNodeIds: List<Long>
    
    @ManyToOne
    @JoinColumn(
        name = "PARENT_ID",
        referencedColumnName = "ID"
    )
    val treeNode: TreeNode
    
    @IdView("treeNode")
    val treeNodeId: Long
}
)]
    """
}
