import type { QueryType, SortDirection } from '../enums';
import type { GenColumn, GenEntity } from './';

export interface GenProperty {
    
    createdTime: string;
    
    modifiedTime: string;
    
    remark: string;
    
    id: boolean;
    
    columnId?: number;
    
    column?: GenColumn;
    
    entityId: number;
    
    entity: GenEntity;
    
    propertyName: string;
    
    isId: boolean;
    
    idGenerationType: string;
    
    isKey: boolean;
    
    isLogicalDelete: boolean;
    
    propertyAssociationType: string;
    
    propertyType: string;
    
    propertyAnnotationExpression: string;
    
    propertyComment: string;
    
    isList: boolean;
    
    listSort: number;
    
    isAdd: boolean;
    
    addSort: number;
    
    isAddRequired: boolean;
    
    isEdit: boolean;
    
    editSort: number;
    
    isEditRequired: boolean;
    
    isEditReadOnly: boolean;
    
    isQuery: boolean;
    
    querySort: number;
    
    queryType: QueryType;
    
    enumId: number;
    
    isSort: boolean;
    
    sortDirection: SortDirection;
}
