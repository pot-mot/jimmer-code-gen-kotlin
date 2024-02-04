# Jimmer Code Gen 代码生成器

> ## TODO
> 
> - [x] 实现模型设计
>   - [x] 将 table 与 datasource 解除强关联，允许 table 单独存在
>   - [x] 补充 table、association 和 model 的关联
>   - [x] 补充 entity、enum 和 model 的关联，彻底将所有建模层面的实体迁移至 model 
> - [ ] 实现模版与继承
>   - [ ] 将 GenConfig 迁移到 Model 中
>   - [ ] 将 ColumnDefault 补充至 TableTemplate 和 ColumnTemplate
>   - [ ] Table 与 Entity 的继承
> - [ ] 补充生成
>  - [ ] DTO
>  - [ ] 业务类
>  - [ ] react 和 vue 的 UI 基础模版

项目基于 Kotlin + Gradle 编写。

## 文档

[github page](https://pot-mot.github.io/jimmer-code-gen-doc/)

## Git 仓库地址

### 前端
- [Github 前端](https://github.com/pot-mot/jimmer-code-gen-vue3)
- [Gitee 前端](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-vue3)

### 后端
- [Github 后端](https://github.com/pot-mot/jimmer-code-gen-kotlin)
- [Gitee 后端](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-kotlin)

## 启动项目

**！！ 目前经验证的数据库脚本仅有 H2、MySQL 5.7 或 8、PostgreSQL 16**

**！！本项目通过外部导入数据源进行生成，所以基础数据源类型无需和目标数据源一致**

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用**

### 直接使用 jar

考虑到部分开发环境需要补充安装 gradle，所以项目也直接提供的包含前端的 jar，且默认以 h2 启动。

前往 [发行版](https://github.com/pot-mot/jimmer-code-gen-kotlin/releases) 获取 jar 包，使用 `java -jar` 直接启动 jar 包并从浏览器访问 `localhost:8080` 即可。

如果计划二次开发，请继续往下。

### 运行 SQL 脚本

- [MySQL](src%2Fmain%2Fresources%2Fsql%2Fmysql%2Fjimmer_code_gen.sql)  
创建 schema **jimmer_code_gen** 之后运行脚本。

- [PostgreSQL](src%2Fmain%2Fresources%2Fsql%2Fpostgresql%2Fjimmer_code_gen.sql)  
在默认 database **postgres** 中创建 schema **jimmer_code_gen** 之后运行脚本。

- [H2](src%2Fmain%2Fresources%2Fsql%2Fh2%2Fjimmer_code_gen.sql)  
基于 [H2Initializer.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FH2Initializer.kt) 随项目启动直接于内存中创建。

### 修改项目配置

修改对应 [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) 中的 `spring.profiles.active` 一项为目标 profile，并在特定环境中配置对应的数据源连接
- [application-h2.yml](src%2Fmain%2Fresources%2Fapplication-h2.yml)
- [application-mysql.yml](src%2Fmain%2Fresources%2Fapplication-mysql.yml)
- [application-postgresql.yml](src%2Fmain%2Fresources%2Fapplication-postgresql.yml)

`jimmer-code-gen.common` 路径下为全局 GenConfig 配置，具体请参照 [GlobalConfig.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGlobalConfig.kt)

### 启动

[JimmerCodeGenApplication.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2FJimmerCodeGenApplication.kt)


## 核心代码说明

以下是本代码生成器的核心流程与入口代码介绍，建议在使用前阅读。

### 数据结构导入

首先需要完成的就是数据库基本信息的导入，目前项目有以下两种途径完成：

#### [DataSource load](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fload%2FDataSourceLoad.kt) 数据源导入

基于 [SchemaCrawler](https://github.com/schemacrawler/SchemaCrawler) 获取数据库元数据，导入 [schema](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenSchema.kt)、[table](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenTable.kt)、[column](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenColumn.kt) 三级数据，并根据外键生成关联。

#### [Model load](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fload%2FModelLoad.kt) 模型设计导入

基于 [AntV X6](https://x6.antv.antgroup.com/) 生产 JSON，具体参照前端项目。最终产物同样为 table 和 association。

### Table Entity Convert 转换

将 table 与 association 转换成业务实体 entity。

具体实现参考以下：

- [TableEntityConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fconvert%2FTableEntityConvert.kt) 表到实体转换
- [BasePropertyConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fconvert%2FBasePropertyConvert.kt) 基础属性转换
- [AssociationPropertyConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fconvert%2FAssociationPropertyConvert.kt) 关联属性转换

### Code Generate 代码生成

- [PreviewService.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fservice%2FPreviewService.kt) 预览生成代码

#### 生成工具类

- [TableDefineBuilder.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fgenerate%2Fbuilder%2FTableDefineBuilder.kt) 表定义构建器

- [CodeBuilder.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fgenerate%2Fbuilder%2FCodeBuilder.kt) 通用构建器器

- [EntityBuilder.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fgenerate%2Fbuilder%2FEntityBuilder.kt) 实体构建器
- [EnumBuilder.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fgenerate%2Fbuilder%2FEnumBuilder.kt) 枚举构建器

## 其他

### 添加数据源支持

补充 [DataSourceType.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fenumeration%2FDataSourceType.kt) 枚举值，并**重新生成前端项目的 api**。

#### 元数据获取

目前项目的元数据是基于 SchemaCrawler 实现的，所以只要是这个库可以支持的数据源就可以被本项目支持。

项目内已经实现元数据的转换导入 [DataSourceLoad.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fload%2FDataSourceLoad.kt)，
只需要在 [build.gradle.kts](build.gradle.kts) 补充对应的 us.fatehi:schemacrawler-[ ] 依赖即可。


#### 生成 TableDefine

针对目标数据源实现以下两个类，并补充对应入口文件：

- [ColumnTypeDefiner.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fgenerate%2FcolumnTypeDefiner%2FColumnTypeDefiner.kt)
  - [ColumnTypeDefine.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fgenerate%2FcolumnTypeDefiner%2FColumnTypeDefine.kt) 入口文件
- [TableDefineGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fgenerate%2FTableDefineGenerator.kt)
  - [TableDefineGenerate.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fgenerate%2FTableDefineGenerate.kt) 入口文件

#### [Liquibase](https://www.liquibase.org/)
关于 TableDefine 有另一种更完善的 diff 生成方式，[LiquibaseUtil.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Futils%2Fliquibase%2FLiquibaseUtil.kt)，
但关于在不同数据源种类间进行类型映射没有较好的解决方案，且**必须真正连接数据源才可进行生成**，考虑到目前项目无法对生成 sql 的效果做出保证，所以目前依然使用手动拼接方案。

如要尝试，请自行在 [PreviewService.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fservice%2FPreviewService.kt) 使用 LiquibaseUtil 中的 createSql 覆盖 previewModelSql。

### 关于超级聚合根类型与大量 output/view dto
> 此处的"超级聚合根类型"是指从聚合根出发，关联层级极深的类型  
> （table 和 entity 就是作为 数据库 和 业务代码两个领域的聚合根）

在生成器这种相对特殊的业务场景下，模型关联密集，后端对于类型操作较多。
为了确保类型安全，规避动态类型带来的属性 Unload 风险，
本项目使用 view dto 作为核心代码的基础。  
**在普通业务场景仅供前端使用的情况下建议使用 fetchBy 而不是 view dto。**

### 目前方向与拓展建议
个人目前无力支持完整的脚手架/低代码，本项目目前仅作为构建与设计模型的入口。  
若使用本项目实现业务场景，欢迎 fork 本项目去实现业务生成分支。

## LICENSE

本项目使用 [GPL 许可](LICENSE)
