import type { DataSourceType } from '../enums';

export interface GenDataSourceInput {
    
    host: string;
    
    name: string;
    
    orderKey: number;
    
    password: string;
    
    port: string;
    
    type: DataSourceType;
    
    username: string;
}
