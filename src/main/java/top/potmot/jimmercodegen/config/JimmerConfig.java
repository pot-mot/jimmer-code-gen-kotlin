package top.potmot.jimmercodegen.config;

import org.babyfish.jimmer.sql.runtime.DefaultExecutor;
import org.babyfish.jimmer.sql.runtime.ExecutionPurpose;
import org.babyfish.jimmer.sql.runtime.Executor;
import org.babyfish.jimmer.sql.runtime.ExecutorContext;
import org.babyfish.jimmer.sql.runtime.SqlFunction;
import org.babyfish.jimmer.sql.runtime.StatementFactory;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.potmot.jimmercodegen.utils.LogOutputUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Configuration
public class JimmerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimmerConfig.class);

    @Value("${gen.show-sql}")
    public Boolean showSql;

    @Bean
    public Executor executor() {
        return new Executor() {
            @Override
            public <R> R execute(
                    Connection con, String sql, List<Object> variables, ExecutionPurpose purpose, @Nullable ExecutorContext ctx, StatementFactory statementFactory, SqlFunction<PreparedStatement, R> block
            ) {
                long start = System.currentTimeMillis();
                R result = DefaultExecutor
                        .INSTANCE
                        .execute(
                                con,
                                sql,
                                variables,
                                purpose,
                                ctx,
                                statementFactory,
                                block
                        );
                if (showSql) {
                    long cost = System.currentTimeMillis() - start;
                    LogOutputUtil.infoSqlLog(sql, variables, cost, result, LOGGER);
                }
                return result;
            }
        };
    }
}
