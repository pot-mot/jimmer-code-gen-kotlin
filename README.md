# Jimmer Code Gen 代码生成器 模型重构分支

> ## 重构内容
> 
> 在经过思考后，核心 model 确定为 table
> 则重构内容由 增加额外 model 实体并提供转换迁移至
> **将 table 与 datasource 解除强关联，允许 table 单独存在**
> 

项目基于 Kotlin + Gradle + [SchemaCrawler](https://github.com/schemacrawler/SchemaCrawler) + [Liquibase](https://www.bing.com/search?q=liquibase&form=ANNTH1&refig=ef394be2e4284bdbbe5b442876a12d57&pc=NMTS) 编写

目前支持根据数据库元数据生成 jimmer 实体类与简单关联属性

## 前端项目地址

[乱跑-了 / table-graph](https://gitee.com/run-around---whats-wrong/table-graph.git)

## 启动项目

**！！ 目前经验证的数据库脚本仅支持 MySQL 8 和 PostgreSQL 16**

**！！本项目通过外部导入数据源进行生成，所以无需和开发用数据源一致**

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用**

### 运行项目 sql 脚本

- MySQL: 
[jimmer_code_gen.sql](sql%2Fmysql%2Fjimmer_code_gen.sql)  
创建 schema **jimmer_code_gen** 运行脚本创建数据库表

- PostgreSQL
[jimmer_code_gen.sql](sql%2Fpostgresql%2Fjimmer_code_gen.sql)  
在默认 database **postgres** 中创建 schema **jimmer_code_gen** 运行脚本创建数据库表

修改对应 [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) 中的 `spring.profiles.active`

### 修改项目配置

- [GenConfig.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGenConfig.kt)
- [GenConfigProperties.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGenConfigProperties.kt)
- [application.yml](src%2Fmain%2Fresources%2Fapplication.yml)
- [application-mysql.yml](src%2Fmain%2Fresources%2Fapplication-mysql.yml)
- [application-postgresql.yml](src%2Fmain%2Fresources%2Fapplication-postgresql.yml)

### IDEA 中运行

[JimmerCodeGenApplication.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2FJimmerCodeGenApplication.kt)

## 核心代码说明

### convert 转换

- [NameConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FNameConvert.kt) 命名转换
- [TableEntityConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FTableEntityConvert.kt) 表到实体转换
- [TypeMapping.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fconvert%2FTypeMapping.kt) 类型映射

### load 导入

包含 [DataSourceLoad.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fload%2FDataSourceLoad.kt) 数据源导入，可导入 schema、table、column 三级数据，并根据外键和索引生成基础的 Association 关联

### match 匹配

包含 [AssociationMatch.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fmatch%2FAssociationMatch.kt) 关联匹配，可根据列间比较返回关联

#### 拓展点

```
typealias AssociationMatch = (source: GenColumnMatchView, target: GenColumnMatchView) -> AssociationType?
```

可通过继续实现 top.potmot.core.AssociationMatch 进行更多种类列关联的半自动匹配

(注意，需要补充 [AssociationMatchType.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fenumeration%2FAssociationMatchType.kt) 枚举 )

### template 模版

将实体对象转换为对应语言实体类代码的模版

#### 实体

调用处:
- [CodeGenerate.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fgenerate%2FCodeGenerate.kt)

具体模版:
- [JavaEntityStringify.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Fentity%2FJavaEntityStringify.kt)
- [KotlinEntityStringify.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Fentity%2FKotlinEntityStringify.kt)

#### 表定义
调用处:
- [DataBaseGenerate.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fgenerate%2FDataBaseGenerate.kt)

具体模版:
- [MysqlTableDefineStringify.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Ftable%2FMysqlTableDefineStringify.kt)
- [PostgreTableDefineStringify.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Ftemplate%2Ftable%2FPostgreTableDefineStringify.kt)

#### 拓展点

可继续通过扩展 [GenEntity.dto](src%2Fmain%2Fdto%2Ftop%2Fpotmot%2Fmodel%2FGenEntity.dto) 中 GenEntityPropertiesView 实体类生产更多的模版

## LICENSE

本项目使用 [GPL 许可](LICENSE)
