import type { QueryType, SortDirection } from '../enums';

export interface GenEntityConfigInput {
    
    add: boolean;
    
    author: string;
    
    classComment: string;
    
    className: string;
    
    edit: boolean;
    
    functionName: string;
    
    genPath: string;
    
    id?: number;
    
    list: boolean;
    
    moduleName: string;
    
    orderKey: number;
    
    packageName: string;
    
    properties: GenEntityConfigInput_TargetOf_properties[];
    
    query: boolean;
    
    remark: string;
}

export interface GenEntityConfigInput_TargetOf_properties {
    
    add: boolean;
    
    addRequired: boolean;
    
    addSort: number;
    
    edit: boolean;
    
    editReadOnly: boolean;
    
    editRequired: boolean;
    
    editSort: number;
    
    enumId: number;
    
    id: boolean;
    
    idGenerationType: string;
    
    key: boolean;
    
    list: boolean;
    
    listSort: number;
    
    logicalDelete: boolean;
    
    propertyAnnotationExpression: string;
    
    propertyAssociationType: string;
    
    propertyComment: string;
    
    propertyName: string;
    
    propertyType: string;
    
    query: boolean;
    
    querySort: number;
    
    queryType: QueryType;
    
    remark: string;
    
    sort: boolean;
    
    sortDirection: SortDirection;
}
