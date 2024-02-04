package top.potmot.model.associations.treeNode

const val javaRealFkResult = """
[(top/potmot/TreeNode.java, package top.potmot;

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
import org.jetbrains.annotations.Nullable;

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
    List<Long> treeNodeIds();
    
    @ManyToOne
    @JoinColumn(
            name = "PARENT_ID",
            referencedColumnName = "ID"
    )
    @Nullable
    TreeNode treeNode();
    
    @IdView("treeNode")
    @Nullable
    Long treeNodeId();
}
)]
"""

const val javaFakeFkResult = """
[(top/potmot/TreeNode.java, package top.potmot;

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
import org.jetbrains.annotations.Nullable;

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
    List<Long> treeNodeIds();
    
    @ManyToOne
    @JoinColumn(
            name = "PARENT_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.REAL
    )
    @Nullable
    TreeNode treeNode();
    
    @IdView("treeNode")
    @Nullable
    Long treeNodeId();
}
)]
"""

const val kotlinRealFkResult = """
[(top/potmot/TreeNode.kt, package top.potmot

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
    val treeNode: TreeNode?
    
    @IdView("treeNode")
    val treeNodeId: Long?
}
)]
"""

const val kotlinFakeFkResult = """
[(top/potmot/TreeNode.kt, package top.potmot

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
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.REAL
    )
    val treeNode: TreeNode?
    
    @IdView("treeNode")
    val treeNodeId: Long?
}
)]
"""
