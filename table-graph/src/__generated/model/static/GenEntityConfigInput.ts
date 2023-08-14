import type { QueryType, SortDirection } from '../enums';

export interface GenEntityConfigInput {
    
    readonly add: boolean;
    
    readonly author: string;
    
    readonly classComment: string;
    
    readonly className: string;
    
    readonly edit: boolean;
    
    readonly functionName: string;
    
    readonly genPath: string;
    
    readonly id?: number;
    
    readonly list: boolean;
    
    readonly moduleName: string;
    
    readonly orderKey: number;
    
    readonly packageName: string;
    
    readonly properties: ReadonlyArray<GenEntityConfigInput_TargetOf_properties>;
    
    readonly query: boolean;
    
    readonly remark: string;
}

export interface GenEntityConfigInput_TargetOf_properties {
    
    readonly add: boolean;
    
    readonly addRequired: boolean;
    
    readonly addSort: number;
    
    readonly edit: boolean;
    
    readonly editReadOnly: boolean;
    
    readonly editRequired: boolean;
    
    readonly editSort: number;
    
    readonly enumId: number;
    
    readonly id: boolean;
    
    readonly idGenerationType: string;
    
    readonly key: boolean;
    
    readonly list: boolean;
    
    readonly listSort: number;
    
    readonly logicalDelete: boolean;
    
    readonly propertyAnnotationExpression: string;
    
    readonly propertyAssociationType: string;
    
    readonly propertyComment: string;
    
    readonly propertyName: string;
    
    readonly propertyType: string;
    
    readonly query: boolean;
    
    readonly querySort: number;
    
    readonly queryType: QueryType;
    
    readonly remark: string;
    
    readonly sort: boolean;
    
    readonly sortDirection: SortDirection;
}
