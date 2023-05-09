import type { Executor } from '../';
import type { GenTableAssociationDto, GenTableDto } from '../model/dto';

export class ReturnService {
    
    constructor(private executor: Executor) {}
    
    async getAllAssociation(options: ReturnServiceOptions['getAllAssociation']): Promise<
        ReadonlyArray<GenTableAssociationDto['DEFAULT']>
    > {
        let _uri = '/associations/';
        _uri += encodeURIComponent(options.keyword);
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableAssociationDto['DEFAULT']>
    }
    
    async getAllTable(options: ReturnServiceOptions['getAllTable']): Promise<
        ReadonlyArray<GenTableDto['DEFAULT']>
    > {
        let _uri = '/tables/';
        _uri += encodeURIComponent(options.keyword);
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['DEFAULT']>
    }
}

export type ReturnServiceOptions = {
    'getAllAssociation': {readonly keyword: string},
    'getAllTable': {readonly keyword: string}
}