#!/bin/bash

/opt/mssql/bin/sqlservr &
MSSQL_PID=$!  # 获取 SQL Server 进程 PID

# 等待 SQL Server 启动完成
until /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "Test1234!" -Q "SELECT 1" -C
do
    echo "Waiting for SQL Server to start..."
    sleep 5
done

echo "SQL Server started, running initialization scripts..."

# 执行初始化脚本
/opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "Test1234!" -C -i /docker-entrypoint-initdb.d/init.sql

echo "Initialization completed."

wait $MSSQL_PID
