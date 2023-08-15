import type { QueryType, SortDirection } from '../enums';

export interface GenEntityPropertiesView {
    
    add: boolean;
    
    author: string;
    
    classComment: string;
    
    className: string;
    
    createdTime: string;
    
    edit: boolean;
    
    functionName: string;
    
    genPath: string;
    
    id?: number;
    
    list: boolean;
    
    modifiedTime: string;
    
    moduleName: string;
    
    orderKey: number;
    
    packageName: string;
    
    properties: GenEntityPropertiesView_TargetOf_properties[];
    
    query: boolean;
    
    remark: string;
    
    tableId?: number;
}

export interface GenEntityPropertiesView_TargetOf_properties {
    
    add: boolean;
    
    addRequired: boolean;
    
    addSort: number;
    
    columnId?: number;
    
    createdTime: string;
    
    edit: boolean;
    
    editReadOnly: boolean;
    
    editRequired: boolean;
    
    editSort: number;
    
    entityId: number;
    
    enumId: number;
    
    id: boolean;
    
    idGenerationType: string;
    
    key: boolean;
    
    list: boolean;
    
    listSort: number;
    
    logicalDelete: boolean;
    
    modifiedTime: string;
    
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
