# Jimmer Code Gen 代码生成器

> ## TODO
> 
> - [x] 实现从 0 开始设计实体模型进行保存
>   - [x] 将 table 与 datasource 解除强关联，允许 table 单独存在
>   - [x] 补充 table 与 association 和 model 可空的关联，实现 table 与 model 的关联
>   - [x] model 到 table 和 association 的生成
> - [ ] 定义元数据
>   - [ ] TableDefine 的元数据
>   - [ ] Entity 的元数据
> - [ ] 补充模版
>   - [ ] 业务类模版
>   - [ ] react 和 vue 的 UI 基础模版
>   - [ ] DTO 设计与业务流程设计

项目基于 Kotlin + Gradle 编写。

## Git 仓库地址

### 前端
- [Github 前端](https://github.com/pot-mot/jimmer-code-gen-vue3)
- [Gitee 前端](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-vue3)

### 后端
- [Github 后端](https://github.com/pot-mot/jimmer-code-gen-kotlin)
- [Gitee 后端](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-kotlin)

## 启动项目

// 目前 PostgreSQL 库表还没有调整，如需使用请务必对着 MySQL 进行修改

**！！ 目前经验证的数据库脚本仅支持 MySQL 8 和 PostgreSQL 16**

**！！本项目通过外部导入数据源进行生成，所以基础数据源类型无需和目标数据源一致**

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用**

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

`.gen` 路径下为全局配置，具体请参照 [GenConfig.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGenConfig.kt) 

### 启动

[JimmerCodeGenApplication.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2FJimmerCodeGenApplication.kt)


## 核心代码说明

以下是本代码生成器的核心流程与入口代码介绍，建议在使用前阅读。

### 数据结构导入

首先需要完成的就是数据库基本信息的导入，目前项目有以下两种途径完成：

#### [DataSource load](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fload%2FDataSourceLoad.kt) 数据源导入

基于 [SchemaCrawler](https://github.com/schemacrawler/SchemaCrawler) 获取数据库元数据，导入 [schema](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenSchema.kt)、[table](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenTable.kt)、[column](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fmodel%2FGenColumn.kt) 三级数据，并根据外键生成关联。

#### [Model load](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fload%2FModelLoad.kt) 模型设计后导入

基于 [AntV X6](https://x6.antv.antgroup.com/) 生产 JSON，具体参照前端项目。最终产物同样为 table 和 association。

### Table Entity Convert 转换

将 table 与 association 转换成业务实体 entity。

具体实现参考以下：

- [TableEntityConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fconvert%2FTableEntityConvert.kt) 表到实体转换
- [ColumnPropertyConvert.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fconvert%2FColumnPropertyConvert.kt) 列到属性转换

>（基于超级聚合根类型 [GenTableAssociationsView](src%2Fmain%2Fdto%2Ftop%2Fpotmot%2Fmodel%2FGenTable.dto)）

### Code Generate 代码生成

[GenerateService.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fservice%2FGenerateService.kt)

- convert（table 转换 entity）
- preview（预览生成代码）
- generate（生成代码 zip）

#### 模版工具类

[TemplateBuilder.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Futils%2Ftemplate%2FTemplateBuilder.kt)

简化字符串的行级拼接

#### Table Define 表定义

[TableDefineGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fdatabase%2Fgenerate%2FTableDefineGenerator.kt)

#### EntityCode 业务实体代码

[EntityCodeGenerator.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fcore%2Fentity%2Fgenerate%2FEntityCodeGenerator.kt)

## 其他

### 关于超级聚合根类型与大量 output/view dto
  > 此处的"超级聚合根类型"是指从聚合根出发，关联层级极深的类型  
  > （table 和 entity 就是作为 数据库 和 业务代码两个领域的聚合根）

  在生成器这种相对特殊的业务场景下，模型关联密集，后端对于类型操作较多。
  为了确保类型安全，规避动态类型带来的属性 Unload 风险，
  本项目使用 view dto 作为核心代码的基础。  
  **在普通业务场景仅供前端使用的情况下建议使用 fetchBy 而不是 view dto。**

### [Liquibase](https://www.liquibase.org/)
关于 TableDefine 有另一种更完善的 diff 生成方式，[LiquibaseUtil.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Futils%2Fliquibase%2FLiquibaseUtil.kt)，
但关于在不同数据源种类间进行类型映射没有较好的解决方案，且必须真正连接数据源才可进行生成，所以目前依然使用手动拼接方案。  
未来计划通过 jdbc type -> liquibase type 的映射来实现通用类型映射。

### 目前方向与拓展建议
个人目前无力支持完整的脚手架/低代码，本项目目前仅作为构建与设计模型的入口。  
若使用本项目实现业务场景，欢迎 fork 本项目去实现业务生成分支。

## LICENSE

本项目使用 [GPL 许可](LICENSE)
