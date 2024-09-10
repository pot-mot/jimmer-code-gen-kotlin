# Jimmer Code Gen 代码生成器

[项目文档](https://pot-mot.github.io/jimmer-code-gen-doc/)

一款实体模型设计与代码生成工具，旨在快捷创建 [Jimmer](https://github.com/babyfish-ct/jimmer) 下的实体与关联。

前往[发行版](https://github.com/pot-mot/jimmer-code-gen-kotlin/releases)获取 jar 包，使用 `java -jar` 直接启动 jar 包并从浏览器访问 `localhost:8080` 即可。  
目前内置支持 H2、MySQL8、PostgreSQL。  
默认采用 H2 内存模式，请注意及时保存模型。

## 仓库地址

| 后端 | 前端 |
| --- | --- |
| [Github](https://github.com/pot-mot/jimmer-code-gen-kotlin) | [Github](https://github.com/pot-mot/jimmer-code-gen-vue3) |
| [Gitee](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-kotlin) | [Gitee](https://gitee.com/run-around---whats-wrong/jimmer-code-gen-vue3) |

## 数据源种类

| sql 脚本                                                              | 配置文件 | 说明                                                                                            |
|---------------------------------------------------------------------|------|-----------------------------------------------------------------------------------------------|
| [MySQL](src%2Fmain%2Fresources%2Fsql%2Fmysql%2Fjimmer_code_gen.sql) | [application-mysql.yml](src%2Fmain%2Fresources%2Fapplication-mysql.yml) | 创建 schema`jimmer_code_gen`，<br/>然后执行脚本。                                                       |
| [PostgreSQL](src%2Fmain%2Fresources%2Fsql%2Fpostgresql%2Fjimmer_code_gen.sql) | [application-postgresql.yml](src%2Fmain%2Fresources%2Fapplication-postgresql.yml) | 使用默认 database`postgres`，<br/>创建 schema`jimmer_code_gen`，<br/>然后执行脚本。                          |
| [H2](src%2Fmain%2Fresources%2Fsql%2Fh2%2Fjimmer_code_gen.sql) | [application-h2.yml](src%2Fmain%2Fresources%2Fapplication-h2.yml) | 通过 [H2Initializer](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FH2Initializer.kt) 随项目启动直接执行。 |

修改对应 [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) 中的 `spring.profiles.active`，并配置对应数据源连接。

## 模型全局通用配置

`jimmer-code-gen` 路径下为全局 GenConfig 配置，默认值请参照 [GlobalGenConfig.kt](src%2Fmain%2Fkotlin%2Ftop%2Fpotmot%2Fconfig%2FGlobalGenConfig.kt)。

## 注意事项

**！！本项目通过外部导入数据源进行生成，所以基础数据源类型无需和目标数据源类型一致**

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用**

## LICENSE

本项目使用 [GPL 许可](LICENSE)
