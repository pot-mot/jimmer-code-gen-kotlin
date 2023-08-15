import type { DataSourceType } from '../enums';
import type { GenSchema } from './';

export interface GenDataSource {
    
    id: number;
    
    type: DataSourceType;
    
    name: string;
    
    host: string;
    
    port: string;
    
    username: string;
    
    password: string;
    
    orderKey: number;
    
    schemas: GenSchema[];
}
