export const tables = [
    {
        "id": 1140,
        "tableName": "cal_holiday",
        "tableComment": "节假日设置",
        "columns": [
            {
                "id": 20143,
                "columnName": "holiday_id",
                "columnComment": "流水号"
            },
            {
                "id": 20144,
                "columnName": "the_day",
                "columnComment": "日期"
            },
            {
                "id": 20145,
                "columnName": "holiday_type",
                "columnComment": "日期类型"
            },
            {
                "id": 20146,
                "columnName": "start_time",
                "columnComment": "开始时间"
            },
            {
                "id": 20147,
                "columnName": "end_time",
                "columnComment": "结束时间"
            }
        ]
    },
    {
        "id": 1141,
        "tableName": "cal_plan",
        "tableComment": "排班计划表",
        "columns": [
            {
                "id": 20157,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20159,
                "columnName": "plan_name",
                "columnComment": "计划名称"
            },
            {
                "id": 20160,
                "columnName": "calendar_type",
                "columnComment": "班组类型"
            },
            {
                "id": 20161,
                "columnName": "start_date",
                "columnComment": "开始日期"
            },
            {
                "id": 20162,
                "columnName": "end_date",
                "columnComment": "结束日期"
            },
            {
                "id": 20163,
                "columnName": "shift_type",
                "columnComment": "轮班方式"
            },
            {
                "id": 20164,
                "columnName": "shift_method",
                "columnComment": "倒班方式"
            },
            {
                "id": 20165,
                "columnName": "shift_count",
                "columnComment": "数"
            }
        ]
    },
    {
        "id": 1142,
        "tableName": "cal_plan_team",
        "tableComment": "计划班组表",
        "columns": [
            {
                "id": 20176,
                "columnName": "record_id",
                "columnComment": "流水号"
            },
            {
                "id": 20177,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20178,
                "columnName": "team_id",
                "columnComment": "班组ID"
            },
            {
                "id": 20180,
                "columnName": "team_name",
                "columnComment": "班组名称"
            }
        ]
    },
    {
        "id": 1143,
        "tableName": "cal_shift",
        "tableComment": "计划班次表",
        "columns": [
            {
                "id": 20190,
                "columnName": "shift_id",
                "columnComment": "班次ID"
            },
            {
                "id": 20191,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20193,
                "columnName": "shift_name",
                "columnComment": "班次名称"
            },
            {
                "id": 20194,
                "columnName": "start_time",
                "columnComment": "开始时间"
            },
            {
                "id": 20195,
                "columnName": "end_time",
                "columnComment": "结束时间"
            }
        ]
    },
    {
        "id": 1144,
        "tableName": "cal_team",
        "tableComment": "班组表",
        "columns": [
            {
                "id": 20205,
                "columnName": "team_id",
                "columnComment": "班组ID"
            },
            {
                "id": 20207,
                "columnName": "team_name",
                "columnComment": "班组名称"
            },
            {
                "id": 20208,
                "columnName": "calendar_type",
                "columnComment": "班组类型"
            }
        ]
    },
    {
        "id": 1145,
        "tableName": "cal_team_member",
        "tableComment": "班组成员表",
        "columns": [
            {
                "id": 20218,
                "columnName": "member_id",
                "columnComment": "班组成员ID"
            },
            {
                "id": 20219,
                "columnName": "team_id",
                "columnComment": "班组ID"
            },
            {
                "id": 20220,
                "columnName": "user_id",
                "columnComment": "用户ID"
            },
            {
                "id": 20221,
                "columnName": "user_name",
                "columnComment": "用户名"
            },
            {
                "id": 20222,
                "columnName": "nick_name",
                "columnComment": "用户昵称"
            },
            {
                "id": 20223,
                "columnName": "tel",
                "columnComment": "电话"
            }
        ]
    },
    {
        "id": 1146,
        "tableName": "cal_teamshift",
        "tableComment": "班组排班表",
        "columns": [
            {
                "id": 20233,
                "columnName": "record_id",
                "columnComment": "流水号"
            },
            {
                "id": 20234,
                "columnName": "the_day",
                "columnComment": "日期"
            },
            {
                "id": 20235,
                "columnName": "team_id",
                "columnComment": "班组ID"
            },
            {
                "id": 20236,
                "columnName": "team_name",
                "columnComment": "班组名称"
            },
            {
                "id": 20237,
                "columnName": "shift_id",
                "columnComment": "班次ID"
            },
            {
                "id": 20238,
                "columnName": "shift_name",
                "columnComment": "班次名称"
            },
            {
                "id": 20240,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20241,
                "columnName": "calendar_type",
                "columnComment": "班组类型"
            },
            {
                "id": 20242,
                "columnName": "shift_type",
                "columnComment": "轮班方式"
            }
        ]
    },
    {
        "id": 1197,
        "tableName": "qrtz_calendars",
        "tableComment": "日历信息表",
        "columns": [
            {
                "id": 21334,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21335,
                "columnName": "calendar_name",
                "columnComment": "日历名称"
            },
            {
                "id": 21336,
                "columnName": "calendar",
                "columnComment": "存放持久化calendar对象"
            }
        ]
    }
]

export const associations = [
    {
        "remark": "计划班组表-计划ID -> 排班计划表-计划ID",
        "id": 27340,
        "tableAssociationName": "cal_plan_team.plan_id to cal_plan.plan_id",
        "sourceColumn": {
            "id": 20177
        },
        "targetColumn": {
            "id": 20157
        }
    },
    {
        "remark": "计划班次表-计划ID -> 排班计划表-计划ID",
        "id": 27341,
        "tableAssociationName": "cal_shift.plan_id to cal_plan.plan_id",
        "sourceColumn": {
            "id": 20191
        },
        "targetColumn": {
            "id": 20157
        }
    },
    {
        "remark": "班组排班表-计划ID -> 排班计划表-计划ID",
        "id": 27342,
        "tableAssociationName": "cal_teamshift.plan_id to cal_plan.plan_id",
        "sourceColumn": {
            "id": 20240
        },
        "targetColumn": {
            "id": 20157
        }
    },
    {
        "remark": "点检设备表-计划ID -> 排班计划表-计划ID",
        "id": 27343,
        "tableAssociationName": "dv_check_machinery.plan_id to cal_plan.plan_id",
        "sourceColumn": {
            "id": 20253
        },
        "targetColumn": {
            "id": 20157
        }
    },
    {
        "remark": "设备点检保养计划头表-计划ID -> 排班计划表-计划ID",
        "id": 27344,
        "tableAssociationName": "dv_check_plan.plan_id to cal_plan.plan_id",
        "sourceColumn": {
            "id": 20268
        },
        "targetColumn": {
            "id": 20157
        }
    },
    {
        "remark": "点检项目表-计划ID -> 排班计划表-计划ID",
        "id": 27345,
        "tableAssociationName": "dv_check_subject.plan_id to cal_plan.plan_id",
        "sourceColumn": {
            "id": 20287
        },
        "targetColumn": {
            "id": 20157
        }
    },
    {
        "remark": "班组排班表-流水号 -> 计划班组表-流水号",
        "id": 27346,
        "tableAssociationName": "cal_teamshift.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "点检设备表-流水号 -> 计划班组表-流水号",
        "id": 27347,
        "tableAssociationName": "dv_check_machinery.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20252
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "点检项目表-流水号 -> 计划班组表-流水号",
        "id": 27348,
        "tableAssociationName": "dv_check_subject.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20286
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "设备资源表-记录ID -> 计划班组表-流水号",
        "id": 27349,
        "tableAssociationName": "md_workstation_machine.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20637
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 计划班组表-流水号",
        "id": 27350,
        "tableAssociationName": "md_workstation_tool.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20652
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "人力资源表-记录ID -> 计划班组表-流水号",
        "id": 27351,
        "tableAssociationName": "md_workstation_worker.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20667
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 计划班组表-流水号",
        "id": 27352,
        "tableAssociationName": "pro_feedback.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20682
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 计划班组表-流水号",
        "id": 27353,
        "tableAssociationName": "pro_route_process.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20764
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品制程-记录ID -> 计划班组表-流水号",
        "id": 27354,
        "tableAssociationName": "pro_route_product.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20787
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 计划班组表-流水号",
        "id": 27355,
        "tableAssociationName": "pro_route_product_bom.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20806
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 计划班组表-流水号",
        "id": 27356,
        "tableAssociationName": "pro_task_issue.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20866
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 计划班组表-流水号",
        "id": 27357,
        "tableAssociationName": "pro_trans_consume.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20891
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 计划班组表-流水号",
        "id": 27358,
        "tableAssociationName": "pro_user_workstation.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 20951
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 计划班组表-流水号",
        "id": 27359,
        "tableAssociationName": "pro_workrecord.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 21017
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 计划班组表-流水号",
        "id": 27360,
        "tableAssociationName": "qc_defect_record.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 21049
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 计划班组表-流水号",
        "id": 27361,
        "tableAssociationName": "qc_template_index.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 21287
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 计划班组表-流水号",
        "id": 27362,
        "tableAssociationName": "qc_template_product.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 21309
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 计划班组表-流水号",
        "id": 27363,
        "tableAssociationName": "wm_item_consume.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 21823
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 计划班组表-流水号",
        "id": 27364,
        "tableAssociationName": "wm_item_consume_line.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 21848
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 计划班组表-流水号",
        "id": 27365,
        "tableAssociationName": "wm_product_produce.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 22032
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 计划班组表-流水号",
        "id": 27366,
        "tableAssociationName": "wm_product_produce_line.record_id to cal_plan_team.record_id",
        "sourceColumn": {
            "id": 22057
        },
        "targetColumn": {
            "id": 20176
        }
    },
    {
        "remark": "班组排班表-班次ID -> 计划班次表-班次ID",
        "id": 27367,
        "tableAssociationName": "cal_teamshift.shift_id to cal_shift.shift_id",
        "sourceColumn": {
            "id": 20237
        },
        "targetColumn": {
            "id": 20190
        }
    },
    {
        "remark": "计划班组表-班组ID -> 班组表-班组ID",
        "id": 27368,
        "tableAssociationName": "cal_plan_team.team_id to cal_team.team_id",
        "sourceColumn": {
            "id": 20178
        },
        "targetColumn": {
            "id": 20205
        }
    },
    {
        "remark": "班组成员表-班组ID -> 班组表-班组ID",
        "id": 27369,
        "tableAssociationName": "cal_team_member.team_id to cal_team.team_id",
        "sourceColumn": {
            "id": 20219
        },
        "targetColumn": {
            "id": 20205
        }
    },
    {
        "remark": "班组排班表-班组ID -> 班组表-班组ID",
        "id": 27370,
        "tableAssociationName": "cal_teamshift.team_id to cal_team.team_id",
        "sourceColumn": {
            "id": 20235
        },
        "targetColumn": {
            "id": 20205
        }
    },
    {
        "remark": "计划班组表-流水号 -> 班组排班表-流水号",
        "id": 27371,
        "tableAssociationName": "cal_plan_team.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "点检设备表-流水号 -> 班组排班表-流水号",
        "id": 27372,
        "tableAssociationName": "dv_check_machinery.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20252
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "点检项目表-流水号 -> 班组排班表-流水号",
        "id": 27373,
        "tableAssociationName": "dv_check_subject.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20286
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "设备资源表-记录ID -> 班组排班表-流水号",
        "id": 27374,
        "tableAssociationName": "md_workstation_machine.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20637
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 班组排班表-流水号",
        "id": 27375,
        "tableAssociationName": "md_workstation_tool.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20652
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "人力资源表-记录ID -> 班组排班表-流水号",
        "id": 27376,
        "tableAssociationName": "md_workstation_worker.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20667
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 班组排班表-流水号",
        "id": 27377,
        "tableAssociationName": "pro_feedback.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20682
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 班组排班表-流水号",
        "id": 27378,
        "tableAssociationName": "pro_route_process.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20764
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品制程-记录ID -> 班组排班表-流水号",
        "id": 27379,
        "tableAssociationName": "pro_route_product.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20787
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 班组排班表-流水号",
        "id": 27380,
        "tableAssociationName": "pro_route_product_bom.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20806
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 班组排班表-流水号",
        "id": 27381,
        "tableAssociationName": "pro_task_issue.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20866
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 班组排班表-流水号",
        "id": 27382,
        "tableAssociationName": "pro_trans_consume.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20891
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 班组排班表-流水号",
        "id": 27383,
        "tableAssociationName": "pro_user_workstation.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 20951
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 班组排班表-流水号",
        "id": 27384,
        "tableAssociationName": "pro_workrecord.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 21017
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 班组排班表-流水号",
        "id": 27385,
        "tableAssociationName": "qc_defect_record.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 21049
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 班组排班表-流水号",
        "id": 27386,
        "tableAssociationName": "qc_template_index.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 21287
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 班组排班表-流水号",
        "id": 27387,
        "tableAssociationName": "qc_template_product.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 21309
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 班组排班表-流水号",
        "id": 27388,
        "tableAssociationName": "wm_item_consume.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 21823
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 班组排班表-流水号",
        "id": 27389,
        "tableAssociationName": "wm_item_consume_line.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 21848
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 班组排班表-流水号",
        "id": 27390,
        "tableAssociationName": "wm_product_produce.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 22032
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 班组排班表-流水号",
        "id": 27391,
        "tableAssociationName": "wm_product_produce_line.record_id to cal_teamshift.record_id",
        "sourceColumn": {
            "id": 22057
        },
        "targetColumn": {
            "id": 20233
        }
    },
    {
        "remark": "计划班组表-流水号 -> 点检设备表-流水号",
        "id": 27392,
        "tableAssociationName": "cal_plan_team.record_id to dv_check_machinery.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20252
        }
    },
    {
        "remark": "班组排班表-流水号 -> 点检设备表-流水号",
        "id": 27393,
        "tableAssociationName": "cal_teamshift.record_id to dv_check_machinery.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20252
        }
    },
    {
        "remark": "排班计划表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27413,
        "tableAssociationName": "cal_plan.plan_id to dv_check_plan.plan_id",
        "sourceColumn": {
            "id": 20157
        },
        "targetColumn": {
            "id": 20268
        }
    },
    {
        "remark": "计划班组表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27414,
        "tableAssociationName": "cal_plan_team.plan_id to dv_check_plan.plan_id",
        "sourceColumn": {
            "id": 20177
        },
        "targetColumn": {
            "id": 20268
        }
    },
    {
        "remark": "计划班次表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27415,
        "tableAssociationName": "cal_shift.plan_id to dv_check_plan.plan_id",
        "sourceColumn": {
            "id": 20191
        },
        "targetColumn": {
            "id": 20268
        }
    },
    {
        "remark": "班组排班表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27416,
        "tableAssociationName": "cal_teamshift.plan_id to dv_check_plan.plan_id",
        "sourceColumn": {
            "id": 20240
        },
        "targetColumn": {
            "id": 20268
        }
    },
    {
        "remark": "计划班组表-流水号 -> 点检项目表-流水号",
        "id": 27419,
        "tableAssociationName": "cal_plan_team.record_id to dv_check_subject.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20286
        }
    },
    {
        "remark": "班组排班表-流水号 -> 点检项目表-流水号",
        "id": 27420,
        "tableAssociationName": "cal_teamshift.record_id to dv_check_subject.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20286
        }
    },
    {
        "remark": "计划班组表-流水号 -> 设备资源表-记录ID",
        "id": 27546,
        "tableAssociationName": "cal_plan_team.record_id to md_workstation_machine.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20637
        }
    },
    {
        "remark": "班组排班表-流水号 -> 设备资源表-记录ID",
        "id": 27547,
        "tableAssociationName": "cal_teamshift.record_id to md_workstation_machine.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20637
        }
    },
    {
        "remark": "计划班组表-流水号 -> 工装夹具资源表-记录ID",
        "id": 27567,
        "tableAssociationName": "cal_plan_team.record_id to md_workstation_tool.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20652
        }
    },
    {
        "remark": "班组排班表-流水号 -> 工装夹具资源表-记录ID",
        "id": 27568,
        "tableAssociationName": "cal_teamshift.record_id to md_workstation_tool.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20652
        }
    },
    {
        "remark": "计划班组表-流水号 -> 人力资源表-记录ID",
        "id": 27588,
        "tableAssociationName": "cal_plan_team.record_id to md_workstation_worker.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20667
        }
    },
    {
        "remark": "班组排班表-流水号 -> 人力资源表-记录ID",
        "id": 27589,
        "tableAssociationName": "cal_teamshift.record_id to md_workstation_worker.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20667
        }
    },
    {
        "remark": "计划班组表-流水号 -> 生产报工记录表-记录ID",
        "id": 27609,
        "tableAssociationName": "cal_plan_team.record_id to pro_feedback.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20682
        }
    },
    {
        "remark": "班组排班表-流水号 -> 生产报工记录表-记录ID",
        "id": 27610,
        "tableAssociationName": "cal_teamshift.record_id to pro_feedback.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20682
        }
    },
    {
        "remark": "计划班组表-流水号 -> 工艺组成表-记录ID",
        "id": 27646,
        "tableAssociationName": "cal_plan_team.record_id to pro_route_process.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20764
        }
    },
    {
        "remark": "班组排班表-流水号 -> 工艺组成表-记录ID",
        "id": 27647,
        "tableAssociationName": "cal_teamshift.record_id to pro_route_process.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20764
        }
    },
    {
        "remark": "计划班组表-流水号 -> 产品制程-记录ID",
        "id": 27667,
        "tableAssociationName": "cal_plan_team.record_id to pro_route_product.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20787
        }
    },
    {
        "remark": "班组排班表-流水号 -> 产品制程-记录ID",
        "id": 27668,
        "tableAssociationName": "cal_teamshift.record_id to pro_route_product.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20787
        }
    },
    {
        "remark": "计划班组表-流水号 -> 产品制程物料BOM表-记录ID",
        "id": 27688,
        "tableAssociationName": "cal_plan_team.record_id to pro_route_product_bom.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20806
        }
    },
    {
        "remark": "班组排班表-流水号 -> 产品制程物料BOM表-记录ID",
        "id": 27689,
        "tableAssociationName": "cal_teamshift.record_id to pro_route_product_bom.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20806
        }
    },
    {
        "remark": "计划班组表-流水号 -> 生产任务投料表-记录ID",
        "id": 27717,
        "tableAssociationName": "cal_plan_team.record_id to pro_task_issue.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20866
        }
    },
    {
        "remark": "班组排班表-流水号 -> 生产任务投料表-记录ID",
        "id": 27718,
        "tableAssociationName": "cal_teamshift.record_id to pro_task_issue.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20866
        }
    },
    {
        "remark": "计划班组表-流水号 -> 物料消耗记录表-记录ID",
        "id": 27738,
        "tableAssociationName": "cal_plan_team.record_id to pro_trans_consume.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20891
        }
    },
    {
        "remark": "班组排班表-流水号 -> 物料消耗记录表-记录ID",
        "id": 27739,
        "tableAssociationName": "cal_teamshift.record_id to pro_trans_consume.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20891
        }
    },
    {
        "remark": "计划班组表-流水号 -> 用户工作站绑定关系-记录ID",
        "id": 27760,
        "tableAssociationName": "cal_plan_team.record_id to pro_user_workstation.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 20951
        }
    },
    {
        "remark": "班组排班表-流水号 -> 用户工作站绑定关系-记录ID",
        "id": 27761,
        "tableAssociationName": "cal_teamshift.record_id to pro_user_workstation.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 20951
        }
    },
    {
        "remark": "计划班组表-流水号 -> 上下工记录表-记录ID",
        "id": 27817,
        "tableAssociationName": "cal_plan_team.record_id to pro_workrecord.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 21017
        }
    },
    {
        "remark": "班组排班表-流水号 -> 上下工记录表-记录ID",
        "id": 27818,
        "tableAssociationName": "cal_teamshift.record_id to pro_workrecord.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 21017
        }
    },
    {
        "remark": "计划班组表-流水号 -> 检验单缺陷记录表-缺陷ID",
        "id": 27838,
        "tableAssociationName": "cal_plan_team.record_id to qc_defect_record.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 21049
        }
    },
    {
        "remark": "班组排班表-流水号 -> 检验单缺陷记录表-缺陷ID",
        "id": 27839,
        "tableAssociationName": "cal_teamshift.record_id to qc_defect_record.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 21049
        }
    },
    {
        "remark": "计划班组表-流水号 -> 检测模板-检测项表-记录ID",
        "id": 27930,
        "tableAssociationName": "cal_plan_team.record_id to qc_template_index.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 21287
        }
    },
    {
        "remark": "班组排班表-流水号 -> 检测模板-检测项表-记录ID",
        "id": 27931,
        "tableAssociationName": "cal_teamshift.record_id to qc_template_index.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 21287
        }
    },
    {
        "remark": "计划班组表-流水号 -> 检测模板-产品表-记录ID",
        "id": 27951,
        "tableAssociationName": "cal_plan_team.record_id to qc_template_product.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 21309
        }
    },
    {
        "remark": "班组排班表-流水号 -> 检测模板-产品表-记录ID",
        "id": 27952,
        "tableAssociationName": "cal_teamshift.record_id to qc_template_product.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 21309
        }
    },
    {
        "remark": "日历信息表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27972,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_blob_triggers.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21330
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27993,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21330
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27994,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21337
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27995,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21342
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 日历信息表-调度名称",
        "id": 27996,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21355
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 日历信息表-调度名称",
        "id": 27997,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21365
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27998,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21367
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 日历信息表-调度名称",
        "id": 27999,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21369
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 日历信息表-调度名称",
        "id": 28000,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21373
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 日历信息表-调度名称",
        "id": 28001,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21379
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 日历信息表-调度名称",
        "id": 28002,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_calendars.sched_name",
        "sourceColumn": {
            "id": 21393
        },
        "targetColumn": {
            "id": 21334
        }
    },
    {
        "remark": "触发器详细信息表-日程表名称 -> 日历信息表-日历名称",
        "id": 28003,
        "tableAssociationName": "qrtz_triggers.calendar_name to qrtz_calendars.calendar_name",
        "sourceColumn": {
            "id": 21406
        },
        "targetColumn": {
            "id": 21335
        }
    },
    {
        "remark": "日历信息表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28005,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_cron_triggers.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21337
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28026,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_fired_triggers.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21342
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28036,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_job_details.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21355
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28054,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_locks.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21365
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28064,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_paused_trigger_grps.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21367
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 调度器状态表-调度名称",
        "id": 28080,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_scheduler_state.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21369
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28091,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_simple_triggers.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21373
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28112,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_simprop_triggers.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21379
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28133,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_triggers.sched_name",
        "sourceColumn": {
            "id": 21334
        },
        "targetColumn": {
            "id": 21393
        }
    },
    {
        "remark": "班组成员表-用户ID -> 用户信息表-用户ID",
        "id": 28181,
        "tableAssociationName": "cal_team_member.user_id to sys_user.user_id",
        "sourceColumn": {
            "id": 20220
        },
        "targetColumn": {
            "id": 21659
        }
    },
    {
        "remark": "班组成员表-用户ID -> 用户与岗位关联表-用户ID",
        "id": 28186,
        "tableAssociationName": "cal_team_member.user_id to sys_user_post.user_id",
        "sourceColumn": {
            "id": 20220
        },
        "targetColumn": {
            "id": 21678
        }
    },
    {
        "remark": "班组成员表-用户ID -> 用户和角色关联表-用户ID",
        "id": 28193,
        "tableAssociationName": "cal_team_member.user_id to sys_user_role.user_id",
        "sourceColumn": {
            "id": 20220
        },
        "targetColumn": {
            "id": 21680
        }
    },
    {
        "remark": "计划班组表-流水号 -> 物料消耗记录表-记录ID",
        "id": 28237,
        "tableAssociationName": "cal_plan_team.record_id to wm_item_consume.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 21823
        }
    },
    {
        "remark": "班组排班表-流水号 -> 物料消耗记录表-记录ID",
        "id": 28238,
        "tableAssociationName": "cal_teamshift.record_id to wm_item_consume.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 21823
        }
    },
    {
        "remark": "计划班组表-流水号 -> 产品产出记录表-入库单ID",
        "id": 28328,
        "tableAssociationName": "cal_plan_team.record_id to wm_product_produce.record_id",
        "sourceColumn": {
            "id": 20176
        },
        "targetColumn": {
            "id": 22032
        }
    },
    {
        "remark": "班组排班表-流水号 -> 产品产出记录表-入库单ID",
        "id": 28329,
        "tableAssociationName": "cal_teamshift.record_id to wm_product_produce.record_id",
        "sourceColumn": {
            "id": 20233
        },
        "targetColumn": {
            "id": 22032
        }
    }
]