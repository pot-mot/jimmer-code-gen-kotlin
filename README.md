# Jimmer Code Gen 代码生成器

[项目文档](https://pot-mot.github.io/jimmer-code-gen-doc/)

## 仓库地址

| 后端 | 前端 |
| --- | --- |
| [Github](https://github.com/pot-mot/jimmer-code-gen-kotlin) | [Github](https://github.com/pot-mot/jimmer-code-gen-vue3) |
| [Gitee](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-kotlin) | [Gitee](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-vue3) |

## 启动项目

**！！ 目前经验证的数据库脚本仅有 H2、MySQL 5.7 或 8、PostgreSQL 16**

**！！本项目通过外部导入数据源进行生成，所以基础数据源类型无需和目标数据源一致**

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用**

### 直接使用 jar

前往 [发行版](https://github.com/pot-mot/jimmer-code-gen-kotlin/releases) 获取 jar 包，使用 `java -jar` 直接启动 jar 包并从浏览器访问 `localhost:8080` 即可。

默认采用 H2 内存模式，请注意及时保存模型。

### 不同数据源 sql 脚本

- [MySQL](src%2Fmain%2Fresources%2Fsql%2Fmysql%2Fjimmer_code_gen.sql)  
创建 schema **jimmer_code_gen** 之后运行脚本。

- [PostgreSQL](src%2Fmain%2Fresources%2Fsql%2Fpostgresql%2Fjimmer_code_gen.sql)  
在默认 database **postgres** 中创建 schema **jimmer_code_gen** 之后运行脚本。

- [H2](src%2Fmain%2Fresources%2Fsql%2Fh2%2Fjimmer_code_gen.sql)  
基于 [H2Initializer.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FH2Initializer.kt) 随项目启动直接执行。

### 全局配置

`jimmer-code-gen` 路径下为全局 GenConfig 配置，具体条件请参照 [GlobalGenConfig.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGlobalGenConfig.kt)。

### 基于源码启动

- [application-h2.yml](src%2Fmain%2Fresources%2Fapplication-h2.yml)
- [application-mysql.yml](src%2Fmain%2Fresources%2Fapplication-mysql.yml)
- [application-postgresql.yml](src%2Fmain%2Fresources%2Fapplication-postgresql.yml)

修改对应 [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) 中的 `spring.profiles.active`，并配置对应数据源连接。

随后启动应用 [JimmerCodeGenApplication.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2FJimmerCodeGenApplication.kt)。

## LICENSE

本项目使用 [GPL 许可](LICENSE)
