import type { Dynamic, Executor } from '../';
import type { GenDataSourceDto, GenSchemaDto } from '../model/dto';
import type { GenDataSource, GenTableGroup } from '../model/entities';
import type { GenDataSourceInput, GenSchemaInsertInput } from '../model/static';

export class DataSourceController {
    
    constructor(private executor: Executor) {}
    
    async deleteDataSources(options: DataSourceControllerOptions['deleteDataSources']): Promise<number> {
        let _uri = '/dataSource/';
        return (await this.executor({uri: _uri, method: 'DELETE', body: options.body})) as number
    }
    
    async importTables(options: DataSourceControllerOptions['importTables']): Promise<
        ReadonlyArray<Dynamic<GenTableGroup>>
    > {
        let _uri = '/dataSource/import';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupId;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupId='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as ReadonlyArray<Dynamic<GenTableGroup>>
    }
    
    async list(): Promise<
        ReadonlyArray<GenDataSourceDto['DEFAULT']>
    > {
        let _uri = '/dataSource/';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenDataSourceDto['DEFAULT']>
    }
    
    async save(options: DataSourceControllerOptions['save']): Promise<
        Dynamic<GenDataSource>
    > {
        let _uri = '/dataSource/';
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as Dynamic<GenDataSource>
    }
    
    async viewSchemas(options: DataSourceControllerOptions['viewSchemas']): Promise<
        ReadonlyArray<GenSchemaDto['DEFAULT']>
    > {
        let _uri = '/dataSource/';
        _uri += encodeURIComponent(options.dataSourceId);
        _uri += '/schemas';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenSchemaDto['DEFAULT']>
    }
}

export type DataSourceControllerOptions = {
    'deleteDataSources': {readonly body?: ReadonlyArray<number>},
    'importTables': {readonly body: GenSchemaInsertInput, readonly groupId?: number},
    'list': {},
    'save': {readonly body: GenDataSourceInput},
    'viewSchemas': {readonly dataSourceId: number}
}