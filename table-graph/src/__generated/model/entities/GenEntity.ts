import type { GenProperty, GenTable } from './';

export interface GenEntity {
    
    createdTime: string;
    
    modifiedTime: string;
    
    remark: string;
    
    id: number;
    
    tableId?: number;
    
    table?: GenTable;
    
    className: string;
    
    classComment: string;
    
    packageName: string;
    
    moduleName: string;
    
    functionName: string;
    
    author: string;
    
    genPath: string;
    
    isAdd: boolean;
    
    isEdit: boolean;
    
    isList: boolean;
    
    isQuery: boolean;
    
    orderKey: number;
    
    propertyIds: number[];
    
    properties: GenProperty[];
}
