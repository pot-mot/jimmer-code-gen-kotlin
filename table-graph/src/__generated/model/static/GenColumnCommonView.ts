export interface GenColumnCommonView {
    
    readonly autoIncrement: boolean;
    
    readonly columnComment: string;
    
    readonly columnDefault?: string;
    
    readonly columnDisplaySize: number;
    
    readonly columnName: string;
    
    readonly columnPrecision: number;
    
    readonly columnSort: number;
    
    readonly columnType: string;
    
    readonly columnTypeCode: number;
    
    readonly createdTime: string;
    
    readonly fk: boolean;
    
    readonly id?: number;
    
    readonly modifiedTime: string;
    
    readonly notNull: boolean;
    
    readonly pk: boolean;
    
    readonly remark: string;
    
    readonly unique: boolean;
    
    readonly virtualColumn: boolean;
}
