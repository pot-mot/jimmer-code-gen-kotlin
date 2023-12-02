# Jimmer Code Gen 代码生成器 模型重构分支

> ## 重构内容
> 
> 核心 model 确定为 table
> 
> **将 table 与 datasource 解除强关联，允许 table 单独存在**
> 
> **补充 table 与 association 和 model 可空的关联，实现 table 与 model 的关联**
> 

项目基于 Kotlin + Gradle + [SchemaCrawler](https://github.com/schemacrawler/SchemaCrawler) 编写

目前支持根据数据库元数据生成 jimmer 实体类与简单关联属性

## 前端项目地址

[乱跑-了 / table-graph](https://gitee.com/run-around---whats-wrong/table-graph.git)

## 启动项目

**！！ 目前经验证的数据库脚本仅支持 MySQL 8 和 PostgreSQL 16**

**！！本项目通过外部导入数据源进行生成，所以无需和开发用数据源一致**

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用**

### 运行项目 sql 脚本

- [MySQL](sql%2Fmysql%2Fjimmer_code_gen.sql)  
创建 schema **jimmer_code_gen** 运行脚本创建数据库表

- [PostgreSQL](sql%2Fpostgresql%2Fjimmer_code_gen.sql)  
在默认 database **postgres** 中创建 schema **jimmer_code_gen** 运行脚本创建数据库表

修改对应 [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) 中的 `spring.profiles.active`

### 修改项目配置

- [GenConfig.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGenConfig.kt) (全局 object)
- [GenConfigProperties.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGenConfigProperties.kt) (data class 入参)
- [application.yml](src%2Fmain%2Fresources%2Fapplication.yml)
- [application-mysql.yml](src%2Fmain%2Fresources%2Fapplication-mysql.yml)
- [application-postgresql.yml](src%2Fmain%2Fresources%2Fapplication-postgresql.yml)

### IDEA 中启动

[JimmerCodeGenApplication.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2FJimmerCodeGenApplication.kt)

## 核心代码说明

以下即是核心功能，也是整个代码生成器的使用流程

### DataSource Load 数据源导入

这是可选的一步，也可以直接通过模型设计创建数据库结构

- [DataSourceLoad.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fload%2FDataSourceLoad.kt)  
数据源导入，可导入 [schema](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenSchema.kt)、[table](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenTable.kt)、[column](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenColumn.kt) 三级数据，并根据外键和索引生成基础的关联

### Model Edit 模型设计

此处的模型基于 antV X6 JSON 的 table 和 association 的 input 聚合，内部数据以 JSON 形式存储，并在保存时转换出内部 table 和 association。

#### 业务类

[ModelService.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fservice%2FModelService.kt)

#### 实体类

[GenModel.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenModel.kt)

[ModelExtension.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2Fextension%2FModelExtension.kt)

### Association Match 关联匹配

自动关联匹配，快速扫描列建立关联

[AssociationMatch.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fmatch%2FAssociationMatch.kt)

#### 拓展点

```
typealias AssociationMatch = (source: GenColumnMatchView, target: GenColumnMatchView) -> AssociationType?
```

可通过继续实现 top.potmot.core.AssociationMatch 进行更多种类列关联的半自动匹配

**（!!注意，需要补充 [AssociationMatchType.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fenumeration%2FAssociationMatchType.kt) 枚举才可在前端显示）**

### Table Entity Convert 转换

将数据库组成部分（table 与 association）转换成实体对象（entity）

>（基于超级聚合根类型 [GenTableAssociationsView](src%2Fmain%2Fdto%2Ftop%2Fpotmot%2Fmodel%2FGenTable.dto)）

#### 工具函数

- [NameConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FNameConvert.kt) 命名转换
- [TypeMapping.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FTypeMapping.kt) 类型映射
- [TableEntityConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FTableEntityConvert.kt) 表到实体转换
- [ColumnPropertyConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FColumnPropertyConvert.kt) 列到属性转换


### Template & Generate 模版与生成

将实体对象转换为对应语言实体类代码的模版，并最终转变为 Map 或直接生成文件

#### 业务类

[GenerateService.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fservice%2FGenerateService.kt)

#### 模版工具类

[TemplateBuilder.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2FTemplateBuilder.kt)

#### EntityCode 业务实体代码
>（基于超级聚合根类型 [GenEntityPropertiesView](src%2Fmain%2Fdto%2Ftop%2Fpotmot%2Fmodel%2FGenEntity.dto)）
> 可连带枚举进行生成

- [JavaEntityCodeGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Fentity%2FJavaEntityCodeGenerator.kt)
- [KotlinEntityCodeGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Fentity%2FKotlinEntityCodeGenerator.kt)

#### Table Define 表定义

- [MysqlTableDefineGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Ftable%2FMysqlTableDefineGenerator.kt)
- [PostgreTableDefineGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Ftable%2FPostgreTableDefineGenerator.kt)

## TODO 计划实现的功能（按时间）

- 包生成、包管理、枚举设计

- 业务类模版

- UI 基础模版

- DTO 设计与业务流程设计

## 其他

### 关于超级聚合根类型与大量 output/view dto
  > 此处的"超级聚合根类型"是指从聚合根出发，关联极深的类型（table 和 entity 就是作为 数据库 和 业务代码两个领域的聚合根）

  在生成器这种相对特殊的业务场景下，模型关联密集，后端对于类型操作较多。
  为了确保类型安全，规避动态类型带来的属性 Unload 风险，
  本项目广泛 view dto 作为核心代码的基础，并且“生成代码”功能相关的代码完全依赖于超级聚合根类型。  
  **在普通业务场景仅供前端使用的情况下建议使用 fetchBy 而不是 view dto。**

### [Liquibase](https://www.bing.com/search?q=liquibase&form=ANNTH1&refig=ef394be2e4284bdbbe5b442876a12d57&pc=NMTS)   
生成表定义 ddl 有另一种生成方式 [LiquibaseUtil.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fliquibase%2FLiquibaseUtil.kt)，但在类型映射方面没有较好的解决方案，所以目前依然使用手动拼接方案

### 目前方向与拓展建议
个人目前无力支持完整的脚手架/低代码，本项目目前仅作为构建与设计模型的入口。  
若使用本项目实现业务场景，欢迎 fork 本项目去实现业务生成分支。  
建议在 entity 表上补充字段以实现更复杂完善的 template，

## LICENSE

本项目使用 [GPL 许可](LICENSE)
