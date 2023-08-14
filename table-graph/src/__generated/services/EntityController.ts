import type { Executor } from '../';
import type { EntityQuery, GenEntityConfigInput, GenEntityPropertiesInput, GenEntityPropertiesView, GenTableColumnsInput, Optional } from '../model/static';

export class EntityController {
    
    constructor(private executor: Executor) {}
    
    async config(options: EntityControllerOptions['config']): Promise<
        Optional<GenEntityPropertiesView>
    > {
        let _uri = '/entity/config';
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as Optional<GenEntityPropertiesView>
    }
    
    async delete(options: EntityControllerOptions['delete']): Promise<number> {
        let _uri = '/entity/';
        return (await this.executor({uri: _uri, method: 'DELETE', body: options.body})) as number
    }
    
    async map(options: EntityControllerOptions['map']): Promise<
        GenEntityPropertiesView
    > {
        let _uri = '/entity/map';
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as GenEntityPropertiesView
    }
    
    async query(options: EntityControllerOptions['query']): Promise<
        ReadonlyArray<GenEntityPropertiesView>
    > {
        let _uri = '/entity/query';
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as ReadonlyArray<GenEntityPropertiesView>
    }
    
    async save(options: EntityControllerOptions['save']): Promise<
        ReadonlyArray<Optional<GenEntityPropertiesView>>
    > {
        let _uri = '/entity/save';
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as ReadonlyArray<Optional<GenEntityPropertiesView>>
    }
    
    async sync(options: EntityControllerOptions['sync']): Promise<
        ReadonlyArray<GenEntityPropertiesView>
    > {
        let _uri = '/entity/sync/';
        _uri += encodeURIComponent(options.tableId);
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenEntityPropertiesView>
    }
}

export type EntityControllerOptions = {
    'config': {readonly body: GenEntityConfigInput},
    'delete': {readonly body: ReadonlyArray<number>},
    'map': {readonly body: GenTableColumnsInput},
    'query': {readonly body: EntityQuery},
    'save': {readonly body: ReadonlyArray<GenEntityPropertiesInput>},
    'sync': {readonly tableId: number}
}