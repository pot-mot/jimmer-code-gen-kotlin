import {GenTableGroupTreeView} from "../__generated/model/static";
import {GenTableGroupTreeView_TargetOf_tables} from "../__generated/model/static/GenTableGroupTreeView";

interface TableGroupTreeItem {
    type: "group" | "table",
    data: GenTableGroupTreeView | GenTableGroupTreeView_TargetOf_tables,
    children: TableGroupTreeItem[]
}