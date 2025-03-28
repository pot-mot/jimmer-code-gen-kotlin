package top.potmot.model.associations.fake.treeNode

const val javaRealFkResult = """
[(java/top/potmot/entity/TreeNode.java, package top.potmot.entity;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ForeignKeyType;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "TREE_NODE")
public interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "parent")
    List<TreeNode> treeNodes();

    @IdView("treeNodes")
    List<Long> treeNodeIds();

    @ManyToOne
    @JoinColumn(
            name = "PARENT_ID",
            referencedColumnName = "ID",
            foreignKeyType = ForeignKeyType.FAKE
    )
    @Nullable
    TreeNode parent();

    @IdView("parent")
    @Nullable
    Long parentId();
}
)]
"""

const val javaFakeFkResult = """
[(java/top/potmot/entity/TreeNode.java, package top.potmot.entity;

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

@Entity
@Table(name = "TREE_NODE")
public interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @OneToMany(mappedBy = "parent")
    List<TreeNode> treeNodes();

    @IdView("treeNodes")
    List<Long> treeNodeIds();

    @ManyToOne
    @JoinColumn(
            name = "PARENT_ID",
            referencedColumnName = "ID"
    )
    @Valid 
    @Nullable
    TreeNode parent();

    @IdView("parent")
    @Nullable
    Long parentId();
}
)]
"""

const val kotlinRealFkResult = """
[(main/kotlin/top/potmot/entity/TreeNode.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "TREE_NODE")
interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "parent")
    val treeNodes: List<TreeNode>

    @IdView("treeNodes")
    val treeNodeIds: List<Long>

    @ManyToOne
    @JoinColumn(
        name = "PARENT_ID",
        referencedColumnName = "ID",
        foreignKeyType = ForeignKeyType.FAKE
    )
    val parent: TreeNode?

    @IdView("parent")
    val parentId: Long?
}
)]
"""

const val kotlinFakeFkResult = """
[(main/kotlin/top/potmot/entity/TreeNode.kt, package top.potmot.entity

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

@Entity
@Table(name = "TREE_NODE")
interface TreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @OneToMany(mappedBy = "parent")
    val treeNodes: List<TreeNode>

    @IdView("treeNodes")
    val treeNodeIds: List<Long>

    @ManyToOne
    @JoinColumn(
        name = "PARENT_ID",
        referencedColumnName = "ID"
    )
    @get:Valid
    val parent: TreeNode?

    @IdView("parent")
    val parentId: Long?
}
)]
"""
