import type { Executor } from '../';
import type { GenTableDto } from '../model/dto';

export class GenTableController {
    
    constructor(private executor: Executor) {}
    
    async get(options: GenTableControllerOptions['get']): Promise<
        GenTableDto['NodeSet/COMPLEX']
    > {
        let _uri = '/genTable/';
        _uri += encodeURIComponent(options.id);
        return (await this.executor({uri: _uri, method: 'GET'})) as GenTableDto['NodeSet/COMPLEX']
    }
    
    async getClass(options: GenTableControllerOptions['getClass']): Promise<
        GenTableDto['NodeSet/CLASS_COMPLEX']
    > {
        let _uri = '/genTable/class/';
        _uri += encodeURIComponent(options.id);
        return (await this.executor({uri: _uri, method: 'GET'})) as GenTableDto['NodeSet/CLASS_COMPLEX']
    }
    
    async getTable(options: GenTableControllerOptions['getTable']): Promise<
        GenTableDto['NodeSet/TABLE_COMPLEX']
    > {
        let _uri = '/genTable/table/';
        _uri += encodeURIComponent(options.id);
        return (await this.executor({uri: _uri, method: 'GET'})) as GenTableDto['NodeSet/TABLE_COMPLEX']
    }
    
    async list(): Promise<
        ReadonlyArray<GenTableDto['NodeSet/COMMON']>
    > {
        let _uri = '/genTable/list';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['NodeSet/COMMON']>
    }
    
    async listClasses(): Promise<
        ReadonlyArray<GenTableDto['NodeSet/CLASS']>
    > {
        let _uri = '/genTable/class/list';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['NodeSet/CLASS']>
    }
    
    async listTables(): Promise<
        ReadonlyArray<GenTableDto['NodeSet/TABLE']>
    > {
        let _uri = '/genTable/table/list';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['NodeSet/TABLE']>
    }
}

export type GenTableControllerOptions = {
    'get': {readonly id: number},
    'getClass': {readonly id: number},
    'getTable': {readonly id: number},
    'list': {},
    'listClasses': {},
    'listTables': {}
}