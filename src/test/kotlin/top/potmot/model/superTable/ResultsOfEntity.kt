package top.potmot.model.superTable

const val javaResult = """
[(java/top/potmot/entity/BaseEntity.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.MappedSuperclass;

/**
 * 基础实体
 * 
 * @author 
 */
@MappedSuperclass
public interface BaseEntity {
    /**
     * 创建者
     */
    @ManyToOne
    @JoinColumn(
            name = "CREATED_BY",
            referencedColumnName = "ID"
    )
    SysUser createdBy();

    /**
     * 创建者 ID View
     */
    @IdView("createdBy")
    int createdById();

    /**
     * 修改者
     */
    @ManyToOne
    @JoinColumn(
            name = "MODIFIED_BY",
            referencedColumnName = "ID"
    )
    SysUser modifiedBy();

    /**
     * 修改者 ID View
     */
    @IdView("modifiedBy")
    int modifiedById();
}
), (java/top/potmot/entity/BizProject.java, package top.potmot.entity;

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
 * 项目
 * 
 * @author 
 */
@Entity
@Table(name = "BIZ_PROJECT")
public interface BizProject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id();

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(
            name = "USER",
            referencedColumnName = "ID"
    )
    SysUser user();

    /**
     * 用户 ID View
     */
    @IdView("user")
    int userId();

    /**
     * 名称
     */
    @Column(name = "NAME")
    String name();
}
), (java/top/potmot/entity/SysUser.java, package top.potmot.entity;

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
 * 用户
 * 
 * @author 
 */
@Entity
@Table(name = "SYS_USER")
public interface SysUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    int id();

    /**
     * 项目 (MappedBy BizProject.user)
     */
    @OneToMany(mappedBy = "user")
    List<BizProject> bizProjectsForUser();

    /**
     * 项目 ID View (MappedBy BizProject.user)
     */
    @IdView("bizProjectsForUser")
    List<Integer> bizProjectIdsForUser();

    /**
     * 项目 (MappedBy BizProject.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    List<BizProject> bizProjectsForCreatedBy();

    /**
     * 项目 ID View (MappedBy BizProject.createdBy)
     */
    @IdView("bizProjectsForCreatedBy")
    List<Integer> bizProjectIdsForCreatedBy();

    /**
     * 项目 (MappedBy BizProject.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    List<BizProject> bizProjectsForModifiedBy();

    /**
     * 项目 ID View (MappedBy BizProject.modifiedBy)
     */
    @IdView("bizProjectsForModifiedBy")
    List<Integer> bizProjectIdsForModifiedBy();

    /**
     * 用户 (MappedBy SysUser.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    List<SysUser> sysUsersForCreatedBy();

    /**
     * 用户 ID View (MappedBy SysUser.createdBy)
     */
    @IdView("sysUsersForCreatedBy")
    List<Integer> sysUserIdsForCreatedBy();

    /**
     * 用户 (MappedBy SysUser.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    List<SysUser> sysUsersForModifiedBy();

    /**
     * 用户 ID View (MappedBy SysUser.modifiedBy)
     */
    @IdView("sysUsersForModifiedBy")
    List<Integer> sysUserIdsForModifiedBy();

    /**
     * 名称
     */
    @Column(name = "NAME")
    String name();
}
)]
"""

const val kotlinResult = """
[(kotlin/top/potmot/entity/BaseEntity.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass

/**
 * 基础实体
 * 
 * @author 
 */
@MappedSuperclass
interface BaseEntity {
    /**
     * 创建者
     */
    @ManyToOne
    @JoinColumn(
        name = "CREATED_BY",
        referencedColumnName = "ID"
    )
    val createdBy: SysUser

    /**
     * 创建者 ID View
     */
    @IdView("createdBy")
    val createdById: Int

    /**
     * 修改者
     */
    @ManyToOne
    @JoinColumn(
        name = "MODIFIED_BY",
        referencedColumnName = "ID"
    )
    val modifiedBy: SysUser

    /**
     * 修改者 ID View
     */
    @IdView("modifiedBy")
    val modifiedById: Int
}
), (kotlin/top/potmot/entity/BizProject.kt, package top.potmot.entity

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
 * 项目
 * 
 * @author 
 */
@Entity
@Table(name = "BIZ_PROJECT")
interface BizProject : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Int

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(
        name = "USER",
        referencedColumnName = "ID"
    )
    val user: SysUser

    /**
     * 用户 ID View
     */
    @IdView("user")
    val userId: Int

    /**
     * 名称
     */
    @Column(name = "NAME")
    val name: String
}
), (kotlin/top/potmot/entity/SysUser.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Table

/**
 * 用户
 * 
 * @author 
 */
@Entity
@Table(name = "SYS_USER")
interface SysUser : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Int

    /**
     * 项目 (MappedBy BizProject.user)
     */
    @OneToMany(mappedBy = "user")
    val bizProjectsForUser: List<BizProject>

    /**
     * 项目 ID View (MappedBy BizProject.user)
     */
    @IdView("bizProjectsForUser")
    val bizProjectIdsForUser: List<Int>

    /**
     * 项目 (MappedBy BizProject.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    val bizProjectsForCreatedBy: List<BizProject>

    /**
     * 项目 ID View (MappedBy BizProject.createdBy)
     */
    @IdView("bizProjectsForCreatedBy")
    val bizProjectIdsForCreatedBy: List<Int>

    /**
     * 项目 (MappedBy BizProject.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    val bizProjectsForModifiedBy: List<BizProject>

    /**
     * 项目 ID View (MappedBy BizProject.modifiedBy)
     */
    @IdView("bizProjectsForModifiedBy")
    val bizProjectIdsForModifiedBy: List<Int>

    /**
     * 用户 (MappedBy SysUser.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    val sysUsersForCreatedBy: List<SysUser>

    /**
     * 用户 ID View (MappedBy SysUser.createdBy)
     */
    @IdView("sysUsersForCreatedBy")
    val sysUserIdsForCreatedBy: List<Int>

    /**
     * 用户 (MappedBy SysUser.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    val sysUsersForModifiedBy: List<SysUser>

    /**
     * 用户 ID View (MappedBy SysUser.modifiedBy)
     */
    @IdView("sysUsersForModifiedBy")
    val sysUserIdsForModifiedBy: List<Int>

    /**
     * 名称
     */
    @Column(name = "NAME")
    val name: String
}
)]
"""
