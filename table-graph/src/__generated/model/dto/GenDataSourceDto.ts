import type { DataSourceType } from '../enums';

export type GenDataSourceDto = {
    'DEFAULT': {
        id: number, 
        type: DataSourceType, 
        name: string, 
        host: string, 
        port: string, 
        username: string, 
        password: string, 
        orderKey: number
    }
}