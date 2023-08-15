import type { Executor } from './';

import { AssociationController, DataSourceController, EntityController, TableController, TableGroupController } from './services';

export class Api {
    
    readonly associationController: AssociationController;
    
    readonly dataSourceController: DataSourceController;
    
    readonly entityController: EntityController;
    
    readonly tableController: TableController;
    
    readonly tableGroupController: TableGroupController;
    
    constructor(executor: Executor) {
        this.associationController = new AssociationController(executor);
        this.dataSourceController = new DataSourceController(executor);
        this.entityController = new EntityController(executor);
        this.tableController = new TableController(executor);
        this.tableGroupController = new TableGroupController(executor);
    }
}