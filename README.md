# Jimmer Code Gen 代码生成器

[项目文档](https://pot-mot.github.io/jimmer-code-gen-doc/)

一款实体模型设计与代码生成工具，旨在快捷创建 [Jimmer](https://github.com/babyfish-ct/jimmer) 下的实体与关联。

前往[发行版](https://github.com/pot-mot/jimmer-code-gen-kotlin/releases)获取 jar 包，使用 `java -jar` 直接启动 jar 包并从浏览器访问 `localhost:39000` 即可。  
目前内置支持 H2、MySQL、PostgreSQL，其余关系型数据库（Oracle、SQL Server、SQLite）支持测试中。  
默认采用 H2 文件模式，将在jar包同级目录下生成对应的 `jimmer_code_gen.mv.db` 数据库文件。

## 仓库地址

| 后端 | 前端 |
| --- | --- |
| [Github](https://github.com/pot-mot/jimmer-code-gen-kotlin) | [Github](https://github.com/pot-mot/jimmer-code-gen-vue3) |

## 注意事项

**！！生成数据源涉及的认证信息均无加密，建议仅在内网环境使用或配置合理的安全措施**

## LICENSE

本项目使用 [GPL 许可](LICENSE)
