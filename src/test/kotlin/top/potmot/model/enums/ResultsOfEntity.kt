package top.potmot.model.enums

const val javaResult = """
[(java/top/potmot/enum/EnumCommon.java, package top.potmot.enum;


public enum EnumCommon {
    item1,

    item2,
}
), (java/top/potmot/enum/EnumName.java, package top.potmot.enum;

import org.babyfish.jimmer.sql.EnumType;
import org.babyfish.jimmer.sql.EnumItem;

@EnumType(EnumType.Strategy.NAME)
public enum EnumName {
    @EnumItem(name = "item1")
    item1,

    @EnumItem(name = "item2")
    item2,
}
), (java/top/potmot/enum/EnumOrdinal.java, package top.potmot.enum;

import org.babyfish.jimmer.sql.EnumType;
import org.babyfish.jimmer.sql.EnumItem;

@EnumType(EnumType.Strategy.ORDINAL)
public enum EnumOrdinal {
    @EnumItem(ordinal = 0)
    item1,

    @EnumItem(ordinal = 1)
    item2,
}
), (java/top/potmot/entity/EnumTable.java, package top.potmot.entity;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Table;
import org.jetbrains.annotations.Nullable;
import top.potmot.enum.EnumCommon;
import top.potmot.enum.EnumName;
import top.potmot.enum.EnumOrdinal;

/**
 * @author 
 */
@Entity
@Table(name = "ENUM_TABLE")
public interface EnumTable {
    @Column(name = "ENUM_COMMON")
    @Nullable
    EnumCommon enumCommon();

    @Column(name = "ENUM_ORDINAL")
    EnumOrdinal enumOrdinal();

    @Column(name = "ENUM_NAME")
    EnumName enumName();
}
)]
"""

const val kotlinResult = """
[(kotlin/top/potmot/enum/EnumCommon.kt, package top.potmot.enum


enum class EnumCommon {
    item1,

    item2,
}
), (kotlin/top/potmot/enum/EnumName.kt, package top.potmot.enum

import org.babyfish.jimmer.sql.EnumType
import org.babyfish.jimmer.sql.EnumItem

@EnumType(EnumType.Strategy.NAME)
enum class EnumName {
    @EnumItem(name = "item1")
    item1,

    @EnumItem(name = "item2")
    item2,
}
), (kotlin/top/potmot/enum/EnumOrdinal.kt, package top.potmot.enum

import org.babyfish.jimmer.sql.EnumType
import org.babyfish.jimmer.sql.EnumItem

@EnumType(EnumType.Strategy.ORDINAL)
enum class EnumOrdinal {
    @EnumItem(ordinal = 0)
    item1,

    @EnumItem(ordinal = 1)
    item2,
}
), (kotlin/top/potmot/entity/EnumTable.kt, package top.potmot.entity

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table
import top.potmot.enum.EnumCommon
import top.potmot.enum.EnumName
import top.potmot.enum.EnumOrdinal

/**
 * @author 
 */
@Entity
@Table(name = "ENUM_TABLE")
interface EnumTable {
    @Column(name = "ENUM_COMMON")
    val enumCommon: EnumCommon?

    @Column(name = "ENUM_ORDINAL")
    val enumOrdinal: EnumOrdinal

    @Column(name = "ENUM_NAME")
    val enumName: EnumName
}
)]
"""
