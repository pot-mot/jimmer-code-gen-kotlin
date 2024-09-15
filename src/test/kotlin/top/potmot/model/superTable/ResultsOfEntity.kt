package top.potmot.model.superTable

const val javaResult = """
[(top/potmot/BaseEntity.java, package top.potmot;

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
     * 用户
     */
    @ManyToOne
    @JoinColumn(
            name = "CREATED_BY",
            referencedColumnName = "ID"
    )
    SysUser createdBy();

    /**
     * 用户 ID 视图
     */
    @IdView("createdBy")
    int createdById();

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(
            name = "MODIFIED_BY",
            referencedColumnName = "ID"
    )
    SysUser modifiedBy();

    /**
     * 用户 ID 视图
     */
    @IdView("modifiedBy")
    int modifiedById();
}
), (top/potmot/BizProject.java, package top.potmot;

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
     * 用户 ID 视图
     */
    @IdView("user")
    int userId();

    /**
     * 名称
     */
    @Column(name = "NAME")
    String name();
}
), (top/potmot/SysUser.java, package top.potmot;

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
     * 项目 (映射自 BizProject.user)
     */
    @OneToMany(mappedBy = "user")
    List<BizProject> bizProjectsForUser();

    /**
     * 项目 ID 视图 (映射自 BizProject.user)
     */
    @IdView("bizProjectsForUser")
    List<Integer> bizProjectIdsForUser();

    /**
     * 项目 (映射自 BizProject.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    List<BizProject> bizProjectsForCreatedBy();

    /**
     * 项目 ID 视图 (映射自 BizProject.createdBy)
     */
    @IdView("bizProjectsForCreatedBy")
    List<Integer> bizProjectIdsForCreatedBy();

    /**
     * 项目 (映射自 BizProject.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    List<BizProject> bizProjectsForModifiedBy();

    /**
     * 项目 ID 视图 (映射自 BizProject.modifiedBy)
     */
    @IdView("bizProjectsForModifiedBy")
    List<Integer> bizProjectIdsForModifiedBy();

    /**
     * 用户 (映射自 SysUser.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    List<SysUser> sysUsersForCreatedBy();

    /**
     * 用户 ID 视图 (映射自 SysUser.createdBy)
     */
    @IdView("sysUsersForCreatedBy")
    List<Integer> sysUserIdsForCreatedBy();

    /**
     * 用户 (映射自 SysUser.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    List<SysUser> sysUsersForModifiedBy();

    /**
     * 用户 ID 视图 (映射自 SysUser.modifiedBy)
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
[(top/potmot/BaseEntity.kt, package top.potmot

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
     * 用户
     */
    @ManyToOne
    @JoinColumn(
        name = "CREATED_BY",
        referencedColumnName = "ID"
    )
    val createdBy: SysUser

    /**
     * 用户 ID 视图
     */
    @IdView("createdBy")
    val createdById: Int

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(
        name = "MODIFIED_BY",
        referencedColumnName = "ID"
    )
    val modifiedBy: SysUser

    /**
     * 用户 ID 视图
     */
    @IdView("modifiedBy")
    val modifiedById: Int
}
), (top/potmot/BizProject.kt, package top.potmot

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
     * 用户 ID 视图
     */
    @IdView("user")
    val userId: Int

    /**
     * 名称
     */
    @Column(name = "NAME")
    val name: String
}
), (top/potmot/SysUser.kt, package top.potmot

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
     * 项目 (映射自 BizProject.user)
     */
    @OneToMany(mappedBy = "user")
    val bizProjectsForUser: List<BizProject>

    /**
     * 项目 ID 视图 (映射自 BizProject.user)
     */
    @IdView("bizProjectsForUser")
    val bizProjectIdsForUser: List<Int>

    /**
     * 项目 (映射自 BizProject.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    val bizProjectsForCreatedBy: List<BizProject>

    /**
     * 项目 ID 视图 (映射自 BizProject.createdBy)
     */
    @IdView("bizProjectsForCreatedBy")
    val bizProjectIdsForCreatedBy: List<Int>

    /**
     * 项目 (映射自 BizProject.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    val bizProjectsForModifiedBy: List<BizProject>

    /**
     * 项目 ID 视图 (映射自 BizProject.modifiedBy)
     */
    @IdView("bizProjectsForModifiedBy")
    val bizProjectIdsForModifiedBy: List<Int>

    /**
     * 用户 (映射自 SysUser.createdBy)
     */
    @OneToMany(mappedBy = "createdBy")
    val sysUsersForCreatedBy: List<SysUser>

    /**
     * 用户 ID 视图 (映射自 SysUser.createdBy)
     */
    @IdView("sysUsersForCreatedBy")
    val sysUserIdsForCreatedBy: List<Int>

    /**
     * 用户 (映射自 SysUser.modifiedBy)
     */
    @OneToMany(mappedBy = "modifiedBy")
    val sysUsersForModifiedBy: List<SysUser>

    /**
     * 用户 ID 视图 (映射自 SysUser.modifiedBy)
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
