import type { GenAssociation, GenProperty, GenTable } from './';

export interface GenColumn {
    
    createdTime: string;
    
    modifiedTime: string;
    
    remark: string;
    
    id: number;
    
    propertyIds: number[];
    
    properties: GenProperty[];
    
    tableId?: number;
    
    table?: GenTable;
    
    columnSort: number;
    
    columnName: string;
    
    columnTypeCode: number;
    
    columnType: string;
    
    columnDisplaySize: number;
    
    columnPrecision: number;
    
    columnDefault?: string;
    
    columnComment: string;
    
    isPk: boolean;
    
    isAutoIncrement: boolean;
    
    isFk: boolean;
    
    isUnique: boolean;
    
    isNotNull: boolean;
    
    inAssociations: GenAssociation[];
    
    outAssociations: GenAssociation[];
}
