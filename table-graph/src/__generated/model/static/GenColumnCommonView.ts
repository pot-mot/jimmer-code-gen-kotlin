export interface GenColumnCommonView {
    
    autoIncrement: boolean;
    
    columnComment: string;
    
    columnDefault?: string;
    
    columnDisplaySize: number;
    
    columnName: string;
    
    columnPrecision: number;
    
    columnSort: number;
    
    columnType: string;
    
    columnTypeCode: number;
    
    createdTime: string;
    
    fk: boolean;
    
    id?: number;
    
    modifiedTime: string;
    
    notNull: boolean;
    
    pk: boolean;
    
    remark: string;
    
    unique: boolean;
}
