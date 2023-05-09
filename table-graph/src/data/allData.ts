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
        "id": 1147,
        "tableName": "dv_check_machinery",
        "tableComment": "点检设备表",
        "columns": [
            {
                "id": 20252,
                "columnName": "record_id",
                "columnComment": "流水号"
            },
            {
                "id": 20253,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20254,
                "columnName": "machinery_id",
                "columnComment": "设备ID"
            },
            {
                "id": 20255,
                "columnName": "machinery_code",
                "columnComment": "设备编码"
            },
            {
                "id": 20256,
                "columnName": "machinery_name",
                "columnComment": "设备名称"
            },
            {
                "id": 20257,
                "columnName": "machinery_brand",
                "columnComment": "品牌"
            },
            {
                "id": 20258,
                "columnName": "machinery_spec",
                "columnComment": "规格型号"
            }
        ]
    },
    {
        "id": 1148,
        "tableName": "dv_check_plan",
        "tableComment": "设备点检保养计划头表",
        "columns": [
            {
                "id": 20268,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20269,
                "columnName": "plan_code",
                "columnComment": "计划编码"
            },
            {
                "id": 20270,
                "columnName": "plan_name",
                "columnComment": "计划名称"
            },
            {
                "id": 20271,
                "columnName": "plan_type",
                "columnComment": "计划类型"
            },
            {
                "id": 20272,
                "columnName": "start_date",
                "columnComment": "开始日期"
            },
            {
                "id": 20273,
                "columnName": "end_date",
                "columnComment": "结束日期"
            },
            {
                "id": 20274,
                "columnName": "cycle_type",
                "columnComment": "频率"
            },
            {
                "id": 20275,
                "columnName": "cycle_count",
                "columnComment": "次数"
            }
        ]
    },
    {
        "id": 1149,
        "tableName": "dv_check_subject",
        "tableComment": "点检项目表",
        "columns": [
            {
                "id": 20286,
                "columnName": "record_id",
                "columnComment": "流水号"
            },
            {
                "id": 20287,
                "columnName": "plan_id",
                "columnComment": "计划ID"
            },
            {
                "id": 20288,
                "columnName": "subject_id",
                "columnComment": "项目ID"
            },
            {
                "id": 20289,
                "columnName": "subject_code",
                "columnComment": "项目编码"
            },
            {
                "id": 20290,
                "columnName": "subject_name",
                "columnComment": "项目名称"
            },
            {
                "id": 20291,
                "columnName": "subject_type",
                "columnComment": "项目类型"
            },
            {
                "id": 20292,
                "columnName": "subject_content",
                "columnComment": "项目内容"
            },
            {
                "id": 20293,
                "columnName": "subject_standard",
                "columnComment": "标准"
            }
        ]
    },
    {
        "id": 1150,
        "tableName": "dv_machinery",
        "tableComment": "设备表",
        "columns": [
            {
                "id": 20303,
                "columnName": "machinery_id",
                "columnComment": "设备类型ID"
            },
            {
                "id": 20304,
                "columnName": "machinery_code",
                "columnComment": "设备类型编码"
            },
            {
                "id": 20305,
                "columnName": "machinery_name",
                "columnComment": "设备类型名称"
            },
            {
                "id": 20306,
                "columnName": "machinery_brand",
                "columnComment": "品牌"
            },
            {
                "id": 20307,
                "columnName": "machinery_spec",
                "columnComment": "规格型号"
            },
            {
                "id": 20308,
                "columnName": "machinery_type_id",
                "columnComment": "设备类型ID"
            },
            {
                "id": 20309,
                "columnName": "machinery_type_code",
                "columnComment": "设备类型编码"
            },
            {
                "id": 20310,
                "columnName": "machinery_type_name",
                "columnComment": "设备类型名称"
            },
            {
                "id": 20311,
                "columnName": "workshop_id",
                "columnComment": "所属车间ID"
            },
            {
                "id": 20312,
                "columnName": "workshop_code",
                "columnComment": "所属车间编码"
            },
            {
                "id": 20313,
                "columnName": "workshop_name",
                "columnComment": "所属车间名称"
            }
        ]
    },
    {
        "id": 1151,
        "tableName": "dv_machinery_type",
        "tableComment": "设备类型表",
        "columns": [
            {
                "id": 20324,
                "columnName": "machinery_type_id",
                "columnComment": "设备类型ID"
            },
            {
                "id": 20325,
                "columnName": "machinery_type_code",
                "columnComment": "设备类型编码"
            },
            {
                "id": 20326,
                "columnName": "machinery_type_name",
                "columnComment": "设备类型名称"
            },
            {
                "id": 20327,
                "columnName": "parent_type_id",
                "columnComment": "父类型ID"
            },
            {
                "id": 20328,
                "columnName": "ancestors",
                "columnComment": "所有父节点ID"
            },
            {
                "id": 20329,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1152,
        "tableName": "dv_repair",
        "tableComment": "设备维修单",
        "columns": [
            {
                "id": 20339,
                "columnName": "repair_id",
                "columnComment": "维修单ID"
            },
            {
                "id": 20341,
                "columnName": "repair_name",
                "columnComment": "维修单名称"
            },
            {
                "id": 20342,
                "columnName": "machinery_id",
                "columnComment": "设备ID"
            },
            {
                "id": 20343,
                "columnName": "machinery_code",
                "columnComment": "设备编码"
            },
            {
                "id": 20344,
                "columnName": "machinery_name",
                "columnComment": "设备名称"
            },
            {
                "id": 20345,
                "columnName": "machinery_brand",
                "columnComment": "品牌"
            },
            {
                "id": 20346,
                "columnName": "machinery_spec",
                "columnComment": "规格型号"
            },
            {
                "id": 20347,
                "columnName": "machinery_type_id",
                "columnComment": "设备类型ID"
            },
            {
                "id": 20348,
                "columnName": "require_date",
                "columnComment": "报修日期"
            },
            {
                "id": 20349,
                "columnName": "finish_date",
                "columnComment": "维修完成日期"
            },
            {
                "id": 20350,
                "columnName": "confirm_date",
                "columnComment": "验收日期"
            },
            {
                "id": 20351,
                "columnName": "repair_result",
                "columnComment": "维修结果"
            },
            {
                "id": 20352,
                "columnName": "accepted_by",
                "columnComment": "维修人员"
            },
            {
                "id": 20353,
                "columnName": "confirm_by",
                "columnComment": "验收人员"
            }
        ]
    },
    {
        "id": 1153,
        "tableName": "dv_repair_line",
        "tableComment": "设备维修单行",
        "columns": [
            {
                "id": 20364,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 20365,
                "columnName": "repair_id",
                "columnComment": "维修单ID"
            },
            {
                "id": 20366,
                "columnName": "subject_id",
                "columnComment": "项目ID"
            },
            {
                "id": 20367,
                "columnName": "subject_code",
                "columnComment": "项目编码"
            },
            {
                "id": 20368,
                "columnName": "subject_name",
                "columnComment": "项目名称"
            },
            {
                "id": 20369,
                "columnName": "subject_type",
                "columnComment": "项目类型"
            },
            {
                "id": 20370,
                "columnName": "subject_content",
                "columnComment": "项目内容"
            },
            {
                "id": 20371,
                "columnName": "subject_standard",
                "columnComment": "标准"
            },
            {
                "id": 20372,
                "columnName": "malfunction",
                "columnComment": "故障描述"
            },
            {
                "id": 20373,
                "columnName": "malfunction_url",
                "columnComment": "故障描述资源"
            },
            {
                "id": 20374,
                "columnName": "repair_des",
                "columnComment": "维修情况"
            }
        ]
    },
    {
        "id": 1154,
        "tableName": "dv_subject",
        "tableComment": "设备点检保养项目表",
        "columns": [
            {
                "id": 20384,
                "columnName": "subject_id",
                "columnComment": "项目ID"
            },
            {
                "id": 20385,
                "columnName": "subject_code",
                "columnComment": "项目编码"
            },
            {
                "id": 20386,
                "columnName": "subject_name",
                "columnComment": "项目名称"
            },
            {
                "id": 20387,
                "columnName": "subject_type",
                "columnComment": "项目类型"
            },
            {
                "id": 20388,
                "columnName": "subject_content",
                "columnComment": "项目内容"
            },
            {
                "id": 20389,
                "columnName": "subject_standard",
                "columnComment": "标准"
            },
            {
                "id": 20390,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1155,
        "tableName": "gen_table",
        "tableComment": "代码生成业务表",
        "columns": [
            {
                "id": 20401,
                "columnName": "table_name",
                "columnComment": "表名称"
            },
            {
                "id": 20402,
                "columnName": "table_comment",
                "columnComment": "表描述"
            },
            {
                "id": 20403,
                "columnName": "sub_table_name",
                "columnComment": "关联子表的表名"
            },
            {
                "id": 20404,
                "columnName": "sub_table_fk_name",
                "columnComment": "子表关联的外键名"
            },
            {
                "id": 20405,
                "columnName": "class_name",
                "columnComment": "实体类名称"
            },
            {
                "id": 20406,
                "columnName": "tpl_category",
                "columnComment": "使用的模板（crud单表操作 tree树表操作）"
            },
            {
                "id": 20407,
                "columnName": "package_name",
                "columnComment": "生成包路径"
            },
            {
                "id": 20408,
                "columnName": "module_name",
                "columnComment": "生成模块名"
            },
            {
                "id": 20409,
                "columnName": "business_name",
                "columnComment": "生成业务名"
            },
            {
                "id": 20410,
                "columnName": "function_name",
                "columnComment": "生成功能名"
            },
            {
                "id": 20411,
                "columnName": "function_author",
                "columnComment": "生成功能作者"
            },
            {
                "id": 20412,
                "columnName": "gen_type",
                "columnComment": "生成代码方式（0zip压缩包 1自定义路径）"
            },
            {
                "id": 20413,
                "columnName": "gen_path",
                "columnComment": "生成路径（不填默认项目路径）"
            },
            {
                "id": 20414,
                "columnName": "options",
                "columnComment": "其它生成选项"
            }
        ]
    },
    {
        "id": 1156,
        "tableName": "gen_table_column",
        "tableComment": "代码生成业务表字段",
        "columns": [
            {
                "id": 20422,
                "columnName": "column_name",
                "columnComment": "列名称"
            },
            {
                "id": 20423,
                "columnName": "column_comment",
                "columnComment": "列描述"
            },
            {
                "id": 20424,
                "columnName": "column_type",
                "columnComment": "列类型"
            },
            {
                "id": 20425,
                "columnName": "java_type",
                "columnComment": "JAVA类型"
            },
            {
                "id": 20426,
                "columnName": "java_field",
                "columnComment": "JAVA字段名"
            },
            {
                "id": 20427,
                "columnName": "is_pk",
                "columnComment": "是否主键（1是）"
            },
            {
                "id": 20428,
                "columnName": "is_increment",
                "columnComment": "是否自增（1是）"
            },
            {
                "id": 20429,
                "columnName": "is_required",
                "columnComment": "是否必填（1是）"
            },
            {
                "id": 20430,
                "columnName": "is_insert",
                "columnComment": "是否为插入字段（1是）"
            },
            {
                "id": 20431,
                "columnName": "is_edit",
                "columnComment": "是否编辑字段（1是）"
            },
            {
                "id": 20432,
                "columnName": "is_list",
                "columnComment": "是否列表字段（1是）"
            },
            {
                "id": 20433,
                "columnName": "is_query",
                "columnComment": "是否查询字段（1是）"
            },
            {
                "id": 20434,
                "columnName": "query_type",
                "columnComment": "查询方式（等于、不等于、大于、小于、范围）"
            },
            {
                "id": 20435,
                "columnName": "html_type",
                "columnComment": "显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）"
            },
            {
                "id": 20436,
                "columnName": "dict_type",
                "columnComment": "字典类型"
            },
            {
                "id": 20437,
                "columnName": "sort",
                "columnComment": "排序"
            }
        ]
    },
    {
        "id": 1157,
        "tableName": "md_client",
        "tableComment": "客户表",
        "columns": [
            {
                "id": 20442,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 20443,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 20444,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 20445,
                "columnName": "client_nick",
                "columnComment": "客户简称"
            },
            {
                "id": 20446,
                "columnName": "client_en",
                "columnComment": "客户英文名称"
            },
            {
                "id": 20447,
                "columnName": "client_des",
                "columnComment": "客户简介"
            },
            {
                "id": 20448,
                "columnName": "client_logo",
                "columnComment": "客户LOGO地址"
            },
            {
                "id": 20449,
                "columnName": "client_type",
                "columnComment": "客户类型"
            },
            {
                "id": 20450,
                "columnName": "address",
                "columnComment": "客户地址"
            },
            {
                "id": 20451,
                "columnName": "website",
                "columnComment": "客户官网地址"
            },
            {
                "id": 20452,
                "columnName": "email",
                "columnComment": "客户邮箱地址"
            },
            {
                "id": 20453,
                "columnName": "tel",
                "columnComment": "客户电话"
            },
            {
                "id": 20454,
                "columnName": "contact1",
                "columnComment": "联系人1"
            },
            {
                "id": 20455,
                "columnName": "contact1_tel",
                "columnComment": "联系人1-电话"
            },
            {
                "id": 20456,
                "columnName": "contact1_email",
                "columnComment": "联系人1-邮箱"
            },
            {
                "id": 20457,
                "columnName": "contact2",
                "columnComment": "联系人2"
            },
            {
                "id": 20458,
                "columnName": "contact2_tel",
                "columnComment": "联系人2-电话"
            },
            {
                "id": 20459,
                "columnName": "contact2_email",
                "columnComment": "联系人2-邮箱"
            },
            {
                "id": 20460,
                "columnName": "credit_code",
                "columnComment": "统一社会信用代码"
            },
            {
                "id": 20461,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1158,
        "tableName": "md_item",
        "tableComment": "物料产品表",
        "columns": [
            {
                "id": 20471,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20472,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20473,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20474,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20475,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20476,
                "columnName": "item_or_product",
                "columnComment": "产品物料标识"
            },
            {
                "id": 20477,
                "columnName": "item_type_id",
                "columnComment": "物料类型ID"
            },
            {
                "id": 20478,
                "columnName": "item_type_code",
                "columnComment": "物料类型编码"
            },
            {
                "id": 20479,
                "columnName": "item_type_name",
                "columnComment": "物料类型名称"
            },
            {
                "id": 20480,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            },
            {
                "id": 20481,
                "columnName": "safe_stock_flag",
                "columnComment": "是否设置安全库存"
            },
            {
                "id": 20482,
                "columnName": "min_stock",
                "columnComment": "最低库存量"
            },
            {
                "id": 20483,
                "columnName": "max_stock",
                "columnComment": "最大库存量"
            }
        ]
    },
    {
        "id": 1159,
        "tableName": "md_item_type",
        "tableComment": "物料产品分类表",
        "columns": [
            {
                "id": 20493,
                "columnName": "item_type_id",
                "columnComment": "产品物料类型ID"
            },
            {
                "id": 20494,
                "columnName": "item_type_code",
                "columnComment": "产品物料类型编码"
            },
            {
                "id": 20495,
                "columnName": "item_type_name",
                "columnComment": "产品物料类型名称"
            },
            {
                "id": 20496,
                "columnName": "parent_type_id",
                "columnComment": "父类型ID"
            },
            {
                "id": 20497,
                "columnName": "ancestors",
                "columnComment": "所有层级父节点"
            },
            {
                "id": 20498,
                "columnName": "item_or_product",
                "columnComment": "产品物料标识"
            },
            {
                "id": 20499,
                "columnName": "order_num",
                "columnComment": "排列顺序"
            },
            {
                "id": 20500,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1160,
        "tableName": "md_product_bom",
        "tableComment": "产品BOM关系表",
        "columns": [
            {
                "id": 20510,
                "columnName": "bom_id",
                "columnComment": "流水号"
            },
            {
                "id": 20511,
                "columnName": "item_id",
                "columnComment": "物料产品ID"
            },
            {
                "id": 20512,
                "columnName": "bom_item_id",
                "columnComment": "BOM物料ID"
            },
            {
                "id": 20513,
                "columnName": "bom_item_code",
                "columnComment": "BOM物料编码"
            },
            {
                "id": 20514,
                "columnName": "bom_item_name",
                "columnComment": "BOM物料名称"
            },
            {
                "id": 20515,
                "columnName": "bom_item_spec",
                "columnComment": "BOM物料规格"
            },
            {
                "id": 20516,
                "columnName": "unit_of_measure",
                "columnComment": "BOM物料单位"
            },
            {
                "id": 20517,
                "columnName": "item_or_product",
                "columnComment": "产品物料标识"
            },
            {
                "id": 20518,
                "columnName": "quantity",
                "columnComment": "物料使用比例"
            },
            {
                "id": 20519,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1161,
        "tableName": "md_product_sop",
        "tableComment": "产品SOP表",
        "columns": [
            {
                "id": 20529,
                "columnName": "sop_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20530,
                "columnName": "item_id",
                "columnComment": "物料产品ID"
            },
            {
                "id": 20531,
                "columnName": "order_num",
                "columnComment": "排列顺序"
            },
            {
                "id": 20532,
                "columnName": "process_id",
                "columnComment": "对应的工序"
            },
            {
                "id": 20534,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20535,
                "columnName": "sop_title",
                "columnComment": "标题"
            },
            {
                "id": 20536,
                "columnName": "sop_description",
                "columnComment": "详细描述"
            },
            {
                "id": 20537,
                "columnName": "sop_url",
                "columnComment": "图片地址"
            }
        ]
    },
    {
        "id": 1162,
        "tableName": "md_unit_measure",
        "tableComment": "单位表",
        "columns": [
            {
                "id": 20547,
                "columnName": "measure_id",
                "columnComment": "单位ID"
            },
            {
                "id": 20548,
                "columnName": "measure_code",
                "columnComment": "单位编码"
            },
            {
                "id": 20549,
                "columnName": "measure_name",
                "columnComment": "单位名称"
            },
            {
                "id": 20550,
                "columnName": "primary_flag",
                "columnComment": "是否是主单位"
            },
            {
                "id": 20551,
                "columnName": "primary_id",
                "columnComment": "主单位ID"
            },
            {
                "id": 20552,
                "columnName": "change_rate",
                "columnComment": "与主单位换算比例"
            },
            {
                "id": 20553,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1163,
        "tableName": "md_vendor",
        "tableComment": "供应商表",
        "columns": [
            {
                "id": 20563,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 20564,
                "columnName": "vendor_code",
                "columnComment": "供应商编码"
            },
            {
                "id": 20565,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 20566,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            },
            {
                "id": 20567,
                "columnName": "vendor_en",
                "columnComment": "供应商英文名称"
            },
            {
                "id": 20568,
                "columnName": "vendor_des",
                "columnComment": "供应商简介"
            },
            {
                "id": 20569,
                "columnName": "vendor_logo",
                "columnComment": "供应商LOGO地址"
            },
            {
                "id": 20570,
                "columnName": "vendor_level",
                "columnComment": "供应商等级"
            },
            {
                "id": 20571,
                "columnName": "vendor_score",
                "columnComment": "供应商评分"
            },
            {
                "id": 20572,
                "columnName": "address",
                "columnComment": "供应商地址"
            },
            {
                "id": 20573,
                "columnName": "website",
                "columnComment": "供应商官网地址"
            },
            {
                "id": 20574,
                "columnName": "email",
                "columnComment": "供应商邮箱地址"
            },
            {
                "id": 20575,
                "columnName": "tel",
                "columnComment": "供应商电话"
            },
            {
                "id": 20576,
                "columnName": "contact1",
                "columnComment": "联系人1"
            },
            {
                "id": 20577,
                "columnName": "contact1_tel",
                "columnComment": "联系人1-电话"
            },
            {
                "id": 20578,
                "columnName": "contact1_email",
                "columnComment": "联系人1-邮箱"
            },
            {
                "id": 20579,
                "columnName": "contact2",
                "columnComment": "联系人2"
            },
            {
                "id": 20580,
                "columnName": "contact2_tel",
                "columnComment": "联系人2-电话"
            },
            {
                "id": 20581,
                "columnName": "contact2_email",
                "columnComment": "联系人2-邮箱"
            },
            {
                "id": 20582,
                "columnName": "credit_code",
                "columnComment": "统一社会信用代码"
            },
            {
                "id": 20583,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1164,
        "tableName": "md_workshop",
        "tableComment": "车间表",
        "columns": [
            {
                "id": 20593,
                "columnName": "workshop_id",
                "columnComment": "车间ID"
            },
            {
                "id": 20594,
                "columnName": "workshop_code",
                "columnComment": "车间编码"
            },
            {
                "id": 20595,
                "columnName": "workshop_name",
                "columnComment": "车间名称"
            },
            {
                "id": 20596,
                "columnName": "area",
                "columnComment": "面积"
            },
            {
                "id": 20597,
                "columnName": "charge",
                "columnComment": "负责人"
            },
            {
                "id": 20598,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1165,
        "tableName": "md_workstation",
        "tableComment": "工作站表",
        "columns": [
            {
                "id": 20608,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20609,
                "columnName": "workstation_code",
                "columnComment": "工作站编码"
            },
            {
                "id": 20610,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 20611,
                "columnName": "workstation_address",
                "columnComment": "工作站地点"
            },
            {
                "id": 20612,
                "columnName": "workshop_id",
                "columnComment": "所在车间ID"
            },
            {
                "id": 20613,
                "columnName": "workshop_code",
                "columnComment": "所在车间编码"
            },
            {
                "id": 20614,
                "columnName": "workshop_name",
                "columnComment": "所在车间名称"
            },
            {
                "id": 20615,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20616,
                "columnName": "process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 20617,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20618,
                "columnName": "warehouse_id",
                "columnComment": "线边库ID"
            },
            {
                "id": 20619,
                "columnName": "warehouse_code",
                "columnComment": "线边库编码"
            },
            {
                "id": 20620,
                "columnName": "warehouse_name",
                "columnComment": "线边库名称"
            },
            {
                "id": 20621,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 20622,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 20623,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 20624,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 20625,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 20626,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 20627,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1166,
        "tableName": "md_workstation_machine",
        "tableComment": "设备资源表",
        "columns": [
            {
                "id": 20637,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20638,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20639,
                "columnName": "machinery_id",
                "columnComment": "设备ID"
            },
            {
                "id": 20640,
                "columnName": "machinery_code",
                "columnComment": "设备编码"
            },
            {
                "id": 20641,
                "columnName": "machinery_name",
                "columnComment": "设备名称"
            },
            {
                "id": 20642,
                "columnName": "quantity",
                "columnComment": "数量"
            }
        ]
    },
    {
        "id": 1167,
        "tableName": "md_workstation_tool",
        "tableComment": "工装夹具资源表",
        "columns": [
            {
                "id": 20652,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20653,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20654,
                "columnName": "tool_type_id",
                "columnComment": "工装夹具类型ID"
            },
            {
                "id": 20655,
                "columnName": "tool_type_code",
                "columnComment": "类型编码"
            },
            {
                "id": 20656,
                "columnName": "tool_type_name",
                "columnComment": "类型名称"
            },
            {
                "id": 20657,
                "columnName": "quantity",
                "columnComment": "数量"
            }
        ]
    },
    {
        "id": 1168,
        "tableName": "md_workstation_worker",
        "tableComment": "人力资源表",
        "columns": [
            {
                "id": 20667,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20668,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20669,
                "columnName": "post_id",
                "columnComment": "岗位ID"
            },
            {
                "id": 20670,
                "columnName": "post_code",
                "columnComment": "岗位编码"
            },
            {
                "id": 20671,
                "columnName": "post_name",
                "columnComment": "岗位名称"
            },
            {
                "id": 20672,
                "columnName": "quantity",
                "columnComment": "数量"
            }
        ]
    },
    {
        "id": 1169,
        "tableName": "pro_feedback",
        "tableComment": "生产报工记录表",
        "columns": [
            {
                "id": 20682,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20683,
                "columnName": "feedback_type",
                "columnComment": "报工类型"
            },
            {
                "id": 20684,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20686,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 20687,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 20689,
                "columnName": "workorder_name",
                "columnComment": "生产工单名称"
            },
            {
                "id": 20690,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20691,
                "columnName": "process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 20692,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20693,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 20695,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20696,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20697,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20698,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20699,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20700,
                "columnName": "quantity",
                "columnComment": "排产数量"
            },
            {
                "id": 20701,
                "columnName": "quantity_feedback",
                "columnComment": "本次报工数量"
            },
            {
                "id": 20702,
                "columnName": "quantity_qualified",
                "columnComment": "合格品数量"
            },
            {
                "id": 20703,
                "columnName": "quantity_unquanlified",
                "columnComment": "不良品数量"
            },
            {
                "id": 20704,
                "columnName": "user_name",
                "columnComment": "报工用户名"
            },
            {
                "id": 20705,
                "columnName": "nick_name",
                "columnComment": "昵称"
            },
            {
                "id": 20706,
                "columnName": "feedback_channel",
                "columnComment": "报工途径"
            },
            {
                "id": 20707,
                "columnName": "feedback_time",
                "columnComment": "报工时间"
            },
            {
                "id": 20708,
                "columnName": "record_user",
                "columnComment": "记录人"
            },
            {
                "id": 20709,
                "columnName": "record_nick",
                "columnComment": "记录人名称"
            }
        ]
    },
    {
        "id": 1170,
        "tableName": "pro_process",
        "tableComment": "生产工序表",
        "columns": [
            {
                "id": 20720,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20721,
                "columnName": "process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 20722,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20723,
                "columnName": "attention",
                "columnComment": "工艺要求"
            },
            {
                "id": 20724,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1171,
        "tableName": "pro_process_content",
        "tableComment": "生产工序内容表",
        "columns": [
            {
                "id": 20734,
                "columnName": "content_id",
                "columnComment": "内容ID"
            },
            {
                "id": 20735,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20737,
                "columnName": "content_text",
                "columnComment": "内容说明"
            },
            {
                "id": 20738,
                "columnName": "device",
                "columnComment": "辅助设备"
            },
            {
                "id": 20739,
                "columnName": "material",
                "columnComment": "辅助材料"
            },
            {
                "id": 20740,
                "columnName": "doc_url",
                "columnComment": "材料URL"
            }
        ]
    },
    {
        "id": 1172,
        "tableName": "pro_route",
        "tableComment": "工艺路线表",
        "columns": [
            {
                "id": 20750,
                "columnName": "route_id",
                "columnComment": "工艺路线ID"
            },
            {
                "id": 20752,
                "columnName": "route_name",
                "columnComment": "工艺路线名称"
            },
            {
                "id": 20753,
                "columnName": "route_desc",
                "columnComment": "工艺路线说明"
            },
            {
                "id": 20754,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1173,
        "tableName": "pro_route_process",
        "tableComment": "工艺组成表",
        "columns": [
            {
                "id": 20764,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20765,
                "columnName": "route_id",
                "columnComment": "工艺路线ID"
            },
            {
                "id": 20766,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20767,
                "columnName": "process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 20768,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20770,
                "columnName": "next_process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20771,
                "columnName": "next_process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 20772,
                "columnName": "next_process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20773,
                "columnName": "link_type",
                "columnComment": "与下一道工序关系"
            },
            {
                "id": 20774,
                "columnName": "default_pre_time",
                "columnComment": "准备时间"
            },
            {
                "id": 20775,
                "columnName": "default_suf_time",
                "columnComment": "等待时间"
            },
            {
                "id": 20776,
                "columnName": "color_code",
                "columnComment": "甘特图显示颜色"
            },
            {
                "id": 20777,
                "columnName": "key_flag",
                "columnComment": "关键工序"
            }
        ]
    },
    {
        "id": 1174,
        "tableName": "pro_route_product",
        "tableComment": "产品制程",
        "columns": [
            {
                "id": 20787,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20788,
                "columnName": "route_id",
                "columnComment": "工艺路线ID"
            },
            {
                "id": 20789,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20790,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20791,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20792,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20793,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20794,
                "columnName": "quantity",
                "columnComment": "生产数量"
            },
            {
                "id": 20795,
                "columnName": "production_time",
                "columnComment": "生产用时"
            },
            {
                "id": 20796,
                "columnName": "time_unit_type",
                "columnComment": "时间单位"
            }
        ]
    },
    {
        "id": 1175,
        "tableName": "pro_route_product_bom",
        "tableComment": "产品制程物料BOM表",
        "columns": [
            {
                "id": 20806,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20807,
                "columnName": "route_id",
                "columnComment": "工艺路线ID"
            },
            {
                "id": 20808,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20809,
                "columnName": "product_id",
                "columnComment": "产品BOM中的唯一ID"
            },
            {
                "id": 20810,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20811,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20812,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20813,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20814,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20815,
                "columnName": "quantity",
                "columnComment": "用料比例"
            }
        ]
    },
    {
        "id": 1176,
        "tableName": "pro_task",
        "tableComment": "生产任务表",
        "columns": [
            {
                "id": 20825,
                "columnName": "task_id",
                "columnComment": "任务ID"
            },
            {
                "id": 20827,
                "columnName": "task_name",
                "columnComment": "任务名称"
            },
            {
                "id": 20828,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 20830,
                "columnName": "workorder_name",
                "columnComment": "工单名称"
            },
            {
                "id": 20831,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20833,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 20834,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20835,
                "columnName": "process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 20836,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20837,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20838,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20839,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20840,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20841,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20842,
                "columnName": "quantity",
                "columnComment": "排产数量"
            },
            {
                "id": 20843,
                "columnName": "quantity_produced",
                "columnComment": "已生产数量"
            },
            {
                "id": 20844,
                "columnName": "quantity_quanlify",
                "columnComment": "合格品数量"
            },
            {
                "id": 20845,
                "columnName": "quantity_unquanlify",
                "columnComment": "不良品数量"
            },
            {
                "id": 20846,
                "columnName": "quantity_changed",
                "columnComment": "调整数量"
            },
            {
                "id": 20847,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 20848,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 20849,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 20850,
                "columnName": "client_nick",
                "columnComment": "客户简称"
            },
            {
                "id": 20851,
                "columnName": "start_time",
                "columnComment": "开始生产时间"
            },
            {
                "id": 20852,
                "columnName": "duration",
                "columnComment": "生产时长"
            },
            {
                "id": 20853,
                "columnName": "end_time",
                "columnComment": "完成生产时间"
            },
            {
                "id": 20854,
                "columnName": "color_code",
                "columnComment": "甘特图显示颜色"
            },
            {
                "id": 20855,
                "columnName": "request_date",
                "columnComment": "需求日期"
            }
        ]
    },
    {
        "id": 1177,
        "tableName": "pro_task_issue",
        "tableComment": "生产任务投料表",
        "columns": [
            {
                "id": 20866,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20867,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 20868,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 20869,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20870,
                "columnName": "source_doc_id",
                "columnComment": "单据ID"
            },
            {
                "id": 20872,
                "columnName": "batch_code",
                "columnComment": "投料批次"
            },
            {
                "id": 20873,
                "columnName": "source_line_id",
                "columnComment": "行ID"
            },
            {
                "id": 20874,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20875,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20876,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20877,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20878,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20879,
                "columnName": "quantity_issued",
                "columnComment": "总的投料数量"
            },
            {
                "id": 20880,
                "columnName": "quantity_available",
                "columnComment": "当前可用数量"
            },
            {
                "id": 20881,
                "columnName": "quantity_used",
                "columnComment": "当前使用数量"
            }
        ]
    },
    {
        "id": 1178,
        "tableName": "pro_trans_consume",
        "tableComment": "物料消耗记录表",
        "columns": [
            {
                "id": 20891,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20892,
                "columnName": "trans_order_id",
                "columnComment": "流转单ID"
            },
            {
                "id": 20894,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 20895,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20896,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20897,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 20898,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 20899,
                "columnName": "source_doc_id",
                "columnComment": "被消耗单据ID"
            },
            {
                "id": 20901,
                "columnName": "source_doc_type",
                "columnComment": "被消耗单据类型"
            },
            {
                "id": 20902,
                "columnName": "source_line_id",
                "columnComment": "被消耗单据行ID"
            },
            {
                "id": 20903,
                "columnName": "source_batch_code",
                "columnComment": "被消耗物料批次号"
            },
            {
                "id": 20904,
                "columnName": "item_id",
                "columnComment": "被消耗产品物料ID"
            },
            {
                "id": 20905,
                "columnName": "item_code",
                "columnComment": "被消耗产品物料编码"
            },
            {
                "id": 20906,
                "columnName": "item_name",
                "columnComment": "被消耗产品物料名称"
            },
            {
                "id": 20907,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20908,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20909,
                "columnName": "quantity_consumed",
                "columnComment": "消耗数量"
            },
            {
                "id": 20910,
                "columnName": "consume_date",
                "columnComment": "消耗时间"
            }
        ]
    },
    {
        "id": 1179,
        "tableName": "pro_trans_order",
        "tableComment": "流转单表",
        "columns": [
            {
                "id": 20920,
                "columnName": "trans_order_id",
                "columnComment": "流转单ID"
            },
            {
                "id": 20922,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 20924,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20926,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 20927,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 20929,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 20930,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 20932,
                "columnName": "workorder_name",
                "columnComment": "生产工单名称"
            },
            {
                "id": 20933,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 20934,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 20935,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 20936,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 20937,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 20938,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20939,
                "columnName": "barcode_url",
                "columnComment": "赋码地址"
            },
            {
                "id": 20940,
                "columnName": "quantity_transfered",
                "columnComment": "流转数量"
            },
            {
                "id": 20941,
                "columnName": "produce_date",
                "columnComment": "生产日期"
            }
        ]
    },
    {
        "id": 1180,
        "tableName": "pro_user_workstation",
        "tableComment": "用户工作站绑定关系",
        "columns": [
            {
                "id": 20951,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 20952,
                "columnName": "user_id",
                "columnComment": "用户ID"
            },
            {
                "id": 20953,
                "columnName": "user_name",
                "columnComment": "用户名"
            },
            {
                "id": 20954,
                "columnName": "nick_name",
                "columnComment": "名称"
            },
            {
                "id": 20955,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 20957,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 20958,
                "columnName": "operation_time",
                "columnComment": "操作时间"
            }
        ]
    },
    {
        "id": 1181,
        "tableName": "pro_workorder",
        "tableComment": "生产工单表",
        "columns": [
            {
                "id": 20968,
                "columnName": "workorder_id",
                "columnComment": "工单ID"
            },
            {
                "id": 20969,
                "columnName": "workorder_code",
                "columnComment": "工单编码"
            },
            {
                "id": 20970,
                "columnName": "workorder_name",
                "columnComment": "工单名称"
            },
            {
                "id": 20971,
                "columnName": "order_source",
                "columnComment": "来源类型"
            },
            {
                "id": 20972,
                "columnName": "source_code",
                "columnComment": "来源单据"
            },
            {
                "id": 20973,
                "columnName": "product_id",
                "columnComment": "产品ID"
            },
            {
                "id": 20975,
                "columnName": "product_name",
                "columnComment": "产品名称"
            },
            {
                "id": 20976,
                "columnName": "product_spc",
                "columnComment": "规格型号"
            },
            {
                "id": 20977,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 20978,
                "columnName": "quantity",
                "columnComment": "生产数量"
            },
            {
                "id": 20979,
                "columnName": "quantity_produced",
                "columnComment": "已生产数量"
            },
            {
                "id": 20980,
                "columnName": "quantity_changed",
                "columnComment": "调整数量"
            },
            {
                "id": 20981,
                "columnName": "quantity_scheduled",
                "columnComment": "已排产数量"
            },
            {
                "id": 20982,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 20983,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 20984,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 20985,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 20986,
                "columnName": "request_date",
                "columnComment": "需求日期"
            },
            {
                "id": 20987,
                "columnName": "parent_id",
                "columnComment": "父工单"
            },
            {
                "id": 20988,
                "columnName": "ancestors",
                "columnComment": "所有父节点ID"
            }
        ]
    },
    {
        "id": 1182,
        "tableName": "pro_workorder_bom",
        "tableComment": "生产工单BOM组成表",
        "columns": [
            {
                "id": 20999,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 21000,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 21001,
                "columnName": "item_id",
                "columnComment": "BOM物料ID"
            },
            {
                "id": 21003,
                "columnName": "item_name",
                "columnComment": "BOM物料名称"
            },
            {
                "id": 21004,
                "columnName": "item_spc",
                "columnComment": "规格型号"
            },
            {
                "id": 21005,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21006,
                "columnName": "item_or_product",
                "columnComment": "物料产品标识"
            },
            {
                "id": 21007,
                "columnName": "quantity",
                "columnComment": "预计使用量"
            }
        ]
    },
    {
        "id": 1183,
        "tableName": "pro_workrecord",
        "tableComment": "上下工记录表",
        "columns": [
            {
                "id": 21017,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21018,
                "columnName": "user_id",
                "columnComment": "用户ID"
            },
            {
                "id": 21019,
                "columnName": "user_name",
                "columnComment": "用户名"
            },
            {
                "id": 21020,
                "columnName": "nick_name",
                "columnComment": "名称"
            },
            {
                "id": 21021,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 21023,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 21024,
                "columnName": "operation_flag",
                "columnComment": "操作类型"
            },
            {
                "id": 21025,
                "columnName": "operation_time",
                "columnComment": "操作时间"
            }
        ]
    },
    {
        "id": 1184,
        "tableName": "qc_defect",
        "tableComment": "常见缺陷表",
        "columns": [
            {
                "id": 21035,
                "columnName": "defect_id",
                "columnComment": "缺陷ID"
            },
            {
                "id": 21036,
                "columnName": "defect_code",
                "columnComment": "缺陷编码"
            },
            {
                "id": 21037,
                "columnName": "defect_name",
                "columnComment": "缺陷描述"
            },
            {
                "id": 21038,
                "columnName": "index_type",
                "columnComment": "检测项类型"
            },
            {
                "id": 21039,
                "columnName": "defect_level",
                "columnComment": "缺陷等级"
            }
        ]
    },
    {
        "id": 1185,
        "tableName": "qc_defect_record",
        "tableComment": "检验单缺陷记录表",
        "columns": [
            {
                "id": 21049,
                "columnName": "record_id",
                "columnComment": "缺陷ID"
            },
            {
                "id": 21050,
                "columnName": "qc_type",
                "columnComment": "检验单类型"
            },
            {
                "id": 21051,
                "columnName": "qc_id",
                "columnComment": "检验单ID"
            },
            {
                "id": 21052,
                "columnName": "line_id",
                "columnComment": "检验单行ID"
            },
            {
                "id": 21053,
                "columnName": "defect_name",
                "columnComment": "缺陷描述"
            },
            {
                "id": 21054,
                "columnName": "defect_level",
                "columnComment": "缺陷等级"
            },
            {
                "id": 21055,
                "columnName": "defect_quantity",
                "columnComment": "缺陷数量"
            }
        ]
    },
    {
        "id": 1186,
        "tableName": "qc_index",
        "tableComment": "检测项表",
        "columns": [
            {
                "id": 21065,
                "columnName": "index_id",
                "columnComment": "检测项ID"
            },
            {
                "id": 21066,
                "columnName": "index_code",
                "columnComment": "检测项编码"
            },
            {
                "id": 21067,
                "columnName": "index_name",
                "columnComment": "检测项名称"
            },
            {
                "id": 21068,
                "columnName": "index_type",
                "columnComment": "检测项类型"
            },
            {
                "id": 21069,
                "columnName": "qc_tool",
                "columnComment": "检测工具"
            }
        ]
    },
    {
        "id": 1187,
        "tableName": "qc_ipqc",
        "tableComment": "过程检验单表",
        "columns": [
            {
                "id": 21079,
                "columnName": "ipqc_id",
                "columnComment": "检验单ID"
            },
            {
                "id": 21081,
                "columnName": "ipqc_name",
                "columnComment": "检验单名称"
            },
            {
                "id": 21082,
                "columnName": "ipqc_type",
                "columnComment": "检验类型"
            },
            {
                "id": 21083,
                "columnName": "template_id",
                "columnComment": "检验模板ID"
            },
            {
                "id": 21084,
                "columnName": "workorder_id",
                "columnComment": "工单ID"
            },
            {
                "id": 21085,
                "columnName": "workorder_code",
                "columnComment": "工单编码"
            },
            {
                "id": 21086,
                "columnName": "workorder_name",
                "columnComment": "工单名称"
            },
            {
                "id": 21087,
                "columnName": "task_id",
                "columnComment": "任务ID"
            },
            {
                "id": 21089,
                "columnName": "task_name",
                "columnComment": "任务名称"
            },
            {
                "id": 21090,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 21092,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 21093,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 21094,
                "columnName": "process_code",
                "columnComment": "工序编码"
            },
            {
                "id": 21095,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 21096,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21097,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21098,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21099,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21100,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21101,
                "columnName": "quantity_check",
                "columnComment": "检测数量"
            },
            {
                "id": 21102,
                "columnName": "quantity_unqualified",
                "columnComment": "不合格数"
            },
            {
                "id": 21103,
                "columnName": "quantity_qualified",
                "columnComment": "合格品数量"
            },
            {
                "id": 21104,
                "columnName": "cr_rate",
                "columnComment": "致命缺陷率"
            },
            {
                "id": 21105,
                "columnName": "maj_rate",
                "columnComment": "严重缺陷率"
            },
            {
                "id": 21106,
                "columnName": "min_rate",
                "columnComment": "轻微缺陷率"
            },
            {
                "id": 21107,
                "columnName": "cr_quantity",
                "columnComment": "致命缺陷数量"
            },
            {
                "id": 21108,
                "columnName": "maj_quantity",
                "columnComment": "严重缺陷数量"
            },
            {
                "id": 21109,
                "columnName": "min_quantity",
                "columnComment": "轻微缺陷数量"
            },
            {
                "id": 21110,
                "columnName": "check_result",
                "columnComment": "检测结果"
            },
            {
                "id": 21111,
                "columnName": "inspect_date",
                "columnComment": "检测日期"
            },
            {
                "id": 21112,
                "columnName": "inspector",
                "columnComment": "检测人员"
            }
        ]
    },
    {
        "id": 1188,
        "tableName": "qc_ipqc_line",
        "tableComment": "过程检验单行表",
        "columns": [
            {
                "id": 21123,
                "columnName": "line_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21124,
                "columnName": "ipqc_id",
                "columnComment": "检验单ID"
            },
            {
                "id": 21125,
                "columnName": "index_id",
                "columnComment": "检测项ID"
            },
            {
                "id": 21126,
                "columnName": "index_code",
                "columnComment": "检测项编码"
            },
            {
                "id": 21127,
                "columnName": "index_name",
                "columnComment": "检测项名称"
            },
            {
                "id": 21128,
                "columnName": "index_type",
                "columnComment": "检测项类型"
            },
            {
                "id": 21129,
                "columnName": "qc_tool",
                "columnComment": "检测工具"
            },
            {
                "id": 21130,
                "columnName": "check_method",
                "columnComment": "检测要求"
            },
            {
                "id": 21131,
                "columnName": "stander_val",
                "columnComment": "标准值"
            },
            {
                "id": 21132,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21133,
                "columnName": "threshold_max",
                "columnComment": "误差上限"
            },
            {
                "id": 21134,
                "columnName": "threshold_min",
                "columnComment": "误差下限"
            },
            {
                "id": 21135,
                "columnName": "cr_quantity",
                "columnComment": "致命缺陷数量"
            },
            {
                "id": 21136,
                "columnName": "maj_quantity",
                "columnComment": "严重缺陷数量"
            },
            {
                "id": 21137,
                "columnName": "min_quantity",
                "columnComment": "轻微缺陷数量"
            }
        ]
    },
    {
        "id": 1189,
        "tableName": "qc_iqc",
        "tableComment": "来料检验单表",
        "columns": [
            {
                "id": 21147,
                "columnName": "iqc_id",
                "columnComment": "来料检验单ID"
            },
            {
                "id": 21149,
                "columnName": "iqc_name",
                "columnComment": "来料检验单名称"
            },
            {
                "id": 21150,
                "columnName": "template_id",
                "columnComment": "检验模板ID"
            },
            {
                "id": 21151,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 21152,
                "columnName": "vendor_code",
                "columnComment": "供应商编码"
            },
            {
                "id": 21153,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 21154,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            },
            {
                "id": 21155,
                "columnName": "vendor_batch",
                "columnComment": "供应商批次号"
            },
            {
                "id": 21156,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21157,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21158,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21159,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21160,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21161,
                "columnName": "quantity_min_check",
                "columnComment": "最低检测数"
            },
            {
                "id": 21162,
                "columnName": "quantity_max_unqualified",
                "columnComment": "最大不合格数"
            },
            {
                "id": 21163,
                "columnName": "quantity_recived",
                "columnComment": "本次接收数量"
            },
            {
                "id": 21164,
                "columnName": "quantity_check",
                "columnComment": "本次检测数量"
            },
            {
                "id": 21165,
                "columnName": "quantity_unqualified",
                "columnComment": "不合格数"
            },
            {
                "id": 21166,
                "columnName": "cr_rate",
                "columnComment": "致命缺陷率"
            },
            {
                "id": 21167,
                "columnName": "maj_rate",
                "columnComment": "严重缺陷率"
            },
            {
                "id": 21168,
                "columnName": "min_rate",
                "columnComment": "轻微缺陷率"
            },
            {
                "id": 21169,
                "columnName": "cr_quantity",
                "columnComment": "致命缺陷数量"
            },
            {
                "id": 21170,
                "columnName": "maj_quantity",
                "columnComment": "严重缺陷数量"
            },
            {
                "id": 21171,
                "columnName": "min_quantity",
                "columnComment": "轻微缺陷数量"
            },
            {
                "id": 21172,
                "columnName": "check_result",
                "columnComment": "检测结果"
            },
            {
                "id": 21173,
                "columnName": "recive_date",
                "columnComment": "来料日期"
            },
            {
                "id": 21174,
                "columnName": "inspect_date",
                "columnComment": "检测日期"
            },
            {
                "id": 21175,
                "columnName": "inspector",
                "columnComment": "检测人员"
            }
        ]
    },
    {
        "id": 1190,
        "tableName": "qc_iqc_line",
        "tableComment": "来料检验单行表",
        "columns": [
            {
                "id": 21186,
                "columnName": "line_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21187,
                "columnName": "iqc_id",
                "columnComment": "检验单ID"
            },
            {
                "id": 21188,
                "columnName": "index_id",
                "columnComment": "检测项ID"
            },
            {
                "id": 21189,
                "columnName": "index_code",
                "columnComment": "检测项编码"
            },
            {
                "id": 21190,
                "columnName": "index_name",
                "columnComment": "检测项名称"
            },
            {
                "id": 21191,
                "columnName": "index_type",
                "columnComment": "检测项类型"
            },
            {
                "id": 21192,
                "columnName": "qc_tool",
                "columnComment": "检测工具"
            },
            {
                "id": 21193,
                "columnName": "check_method",
                "columnComment": "检测要求"
            },
            {
                "id": 21194,
                "columnName": "stander_val",
                "columnComment": "标准值"
            },
            {
                "id": 21195,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21196,
                "columnName": "threshold_max",
                "columnComment": "误差上限"
            },
            {
                "id": 21197,
                "columnName": "threshold_min",
                "columnComment": "误差下限"
            },
            {
                "id": 21198,
                "columnName": "cr_quantity",
                "columnComment": "致命缺陷数量"
            },
            {
                "id": 21199,
                "columnName": "maj_quantity",
                "columnComment": "严重缺陷数量"
            },
            {
                "id": 21200,
                "columnName": "min_quantity",
                "columnComment": "轻微缺陷数量"
            }
        ]
    },
    {
        "id": 1191,
        "tableName": "qc_oqc",
        "tableComment": "出货检验单表",
        "columns": [
            {
                "id": 21210,
                "columnName": "oqc_id",
                "columnComment": "出货检验单ID"
            },
            {
                "id": 21212,
                "columnName": "oqc_name",
                "columnComment": "出货检验单名称"
            },
            {
                "id": 21213,
                "columnName": "template_id",
                "columnComment": "检验模板ID"
            },
            {
                "id": 21214,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 21215,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 21216,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 21217,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 21218,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21219,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21220,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21221,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21222,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21223,
                "columnName": "quantity_min_check",
                "columnComment": "最低检测数"
            },
            {
                "id": 21224,
                "columnName": "quantity_max_unqualified",
                "columnComment": "最大不合格数"
            },
            {
                "id": 21225,
                "columnName": "quantity_out",
                "columnComment": "发货数量"
            },
            {
                "id": 21226,
                "columnName": "quantity_check",
                "columnComment": "本次检测数量"
            },
            {
                "id": 21227,
                "columnName": "quantity_unqualified",
                "columnComment": "不合格数"
            },
            {
                "id": 21228,
                "columnName": "quantity_quanlified",
                "columnComment": "合格数量"
            },
            {
                "id": 21229,
                "columnName": "cr_rate",
                "columnComment": "致命缺陷率"
            },
            {
                "id": 21230,
                "columnName": "maj_rate",
                "columnComment": "严重缺陷率"
            },
            {
                "id": 21231,
                "columnName": "min_rate",
                "columnComment": "轻微缺陷率"
            },
            {
                "id": 21232,
                "columnName": "cr_quantity",
                "columnComment": "致命缺陷数量"
            },
            {
                "id": 21233,
                "columnName": "maj_quantity",
                "columnComment": "严重缺陷数量"
            },
            {
                "id": 21234,
                "columnName": "min_quantity",
                "columnComment": "轻微缺陷数量"
            },
            {
                "id": 21235,
                "columnName": "check_result",
                "columnComment": "检测结果"
            },
            {
                "id": 21236,
                "columnName": "out_date",
                "columnComment": "出货日期"
            },
            {
                "id": 21237,
                "columnName": "inspect_date",
                "columnComment": "检测日期"
            },
            {
                "id": 21238,
                "columnName": "inspector",
                "columnComment": "检测人员"
            }
        ]
    },
    {
        "id": 1192,
        "tableName": "qc_oqc_line",
        "tableComment": "出货检验单行表",
        "columns": [
            {
                "id": 21249,
                "columnName": "line_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21250,
                "columnName": "oqc_id",
                "columnComment": "检验单ID"
            },
            {
                "id": 21251,
                "columnName": "index_id",
                "columnComment": "检测项ID"
            },
            {
                "id": 21252,
                "columnName": "index_code",
                "columnComment": "检测项编码"
            },
            {
                "id": 21253,
                "columnName": "index_name",
                "columnComment": "检测项名称"
            },
            {
                "id": 21254,
                "columnName": "index_type",
                "columnComment": "检测项类型"
            },
            {
                "id": 21255,
                "columnName": "qc_tool",
                "columnComment": "检测工具"
            },
            {
                "id": 21256,
                "columnName": "check_method",
                "columnComment": "检测要求"
            },
            {
                "id": 21257,
                "columnName": "stander_val",
                "columnComment": "标准值"
            },
            {
                "id": 21258,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21259,
                "columnName": "threshold_max",
                "columnComment": "误差上限"
            },
            {
                "id": 21260,
                "columnName": "threshold_min",
                "columnComment": "误差下限"
            },
            {
                "id": 21261,
                "columnName": "cr_quantity",
                "columnComment": "致命缺陷数量"
            },
            {
                "id": 21262,
                "columnName": "maj_quantity",
                "columnComment": "严重缺陷数量"
            },
            {
                "id": 21263,
                "columnName": "min_quantity",
                "columnComment": "轻微缺陷数量"
            }
        ]
    },
    {
        "id": 1193,
        "tableName": "qc_template",
        "tableComment": "检测模板表",
        "columns": [
            {
                "id": 21273,
                "columnName": "template_id",
                "columnComment": "检测模板ID"
            },
            {
                "id": 21275,
                "columnName": "template_name",
                "columnComment": "检测模板名称"
            },
            {
                "id": 21276,
                "columnName": "qc_types",
                "columnComment": "检测种类"
            },
            {
                "id": 21277,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1194,
        "tableName": "qc_template_index",
        "tableComment": "检测模板-检测项表",
        "columns": [
            {
                "id": 21287,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21288,
                "columnName": "template_id",
                "columnComment": "检测模板ID"
            },
            {
                "id": 21289,
                "columnName": "index_id",
                "columnComment": "检测项ID"
            },
            {
                "id": 21290,
                "columnName": "index_code",
                "columnComment": "检测项编码"
            },
            {
                "id": 21291,
                "columnName": "index_name",
                "columnComment": "检测项名称"
            },
            {
                "id": 21292,
                "columnName": "index_type",
                "columnComment": "检测项类型"
            },
            {
                "id": 21293,
                "columnName": "qc_tool",
                "columnComment": "检测工具"
            },
            {
                "id": 21294,
                "columnName": "check_method",
                "columnComment": "检测要求"
            },
            {
                "id": 21295,
                "columnName": "stander_val",
                "columnComment": "标准值"
            },
            {
                "id": 21296,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21297,
                "columnName": "threshold_max",
                "columnComment": "误差上限"
            },
            {
                "id": 21298,
                "columnName": "threshold_min",
                "columnComment": "误差下限"
            },
            {
                "id": 21299,
                "columnName": "doc_url",
                "columnComment": "说明图"
            }
        ]
    },
    {
        "id": 1195,
        "tableName": "qc_template_product",
        "tableComment": "检测模板-产品表",
        "columns": [
            {
                "id": 21309,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21310,
                "columnName": "template_id",
                "columnComment": "检测模板ID"
            },
            {
                "id": 21311,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21312,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21313,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21314,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21315,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21316,
                "columnName": "quantity_check",
                "columnComment": "最低检测数"
            },
            {
                "id": 21317,
                "columnName": "quantity_unqualified",
                "columnComment": "最大不合格数"
            },
            {
                "id": 21318,
                "columnName": "cr_rate",
                "columnComment": "最大致命缺陷率"
            },
            {
                "id": 21319,
                "columnName": "maj_rate",
                "columnComment": "最大严重缺陷率"
            },
            {
                "id": 21320,
                "columnName": "min_rate",
                "columnComment": "最大轻微缺陷率"
            }
        ]
    },
    {
        "id": 1196,
        "tableName": "qrtz_blob_triggers",
        "tableComment": "Blob类型的触发器表",
        "columns": [
            {
                "id": 21330,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21331,
                "columnName": "trigger_name",
                "columnComment": "qrtz_triggers表trigger_name的外键"
            },
            {
                "id": 21332,
                "columnName": "trigger_group",
                "columnComment": "qrtz_triggers表trigger_group的外键"
            },
            {
                "id": 21333,
                "columnName": "blob_data",
                "columnComment": "存放持久化Trigger对象"
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
    },
    {
        "id": 1198,
        "tableName": "qrtz_cron_triggers",
        "tableComment": "Cron类型的触发器表",
        "columns": [
            {
                "id": 21337,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21338,
                "columnName": "trigger_name",
                "columnComment": "qrtz_triggers表trigger_name的外键"
            },
            {
                "id": 21339,
                "columnName": "trigger_group",
                "columnComment": "qrtz_triggers表trigger_group的外键"
            },
            {
                "id": 21340,
                "columnName": "cron_expression",
                "columnComment": "cron表达式"
            },
            {
                "id": 21341,
                "columnName": "time_zone_id",
                "columnComment": "时区"
            }
        ]
    },
    {
        "id": 1199,
        "tableName": "qrtz_fired_triggers",
        "tableComment": "已触发的触发器表",
        "columns": [
            {
                "id": 21342,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21343,
                "columnName": "entry_id",
                "columnComment": "调度器实例id"
            },
            {
                "id": 21344,
                "columnName": "trigger_name",
                "columnComment": "qrtz_triggers表trigger_name的外键"
            },
            {
                "id": 21345,
                "columnName": "trigger_group",
                "columnComment": "qrtz_triggers表trigger_group的外键"
            },
            {
                "id": 21346,
                "columnName": "instance_name",
                "columnComment": "调度器实例名"
            },
            {
                "id": 21347,
                "columnName": "fired_time",
                "columnComment": "触发的时间"
            },
            {
                "id": 21348,
                "columnName": "sched_time",
                "columnComment": "定时器制定的时间"
            },
            {
                "id": 21349,
                "columnName": "priority",
                "columnComment": "优先级"
            },
            {
                "id": 21350,
                "columnName": "state",
                "columnComment": "状态"
            },
            {
                "id": 21351,
                "columnName": "job_name",
                "columnComment": "任务名称"
            },
            {
                "id": 21352,
                "columnName": "job_group",
                "columnComment": "任务组名"
            },
            {
                "id": 21353,
                "columnName": "is_nonconcurrent",
                "columnComment": "是否并发"
            },
            {
                "id": 21354,
                "columnName": "requests_recovery",
                "columnComment": "是否接受恢复执行"
            }
        ]
    },
    {
        "id": 1200,
        "tableName": "qrtz_job_details",
        "tableComment": "任务详细信息表",
        "columns": [
            {
                "id": 21355,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21356,
                "columnName": "job_name",
                "columnComment": "任务名称"
            },
            {
                "id": 21357,
                "columnName": "job_group",
                "columnComment": "任务组名"
            },
            {
                "id": 21358,
                "columnName": "description",
                "columnComment": "相关介绍"
            },
            {
                "id": 21359,
                "columnName": "job_class_name",
                "columnComment": "执行任务类名称"
            },
            {
                "id": 21360,
                "columnName": "is_durable",
                "columnComment": "是否持久化"
            },
            {
                "id": 21361,
                "columnName": "is_nonconcurrent",
                "columnComment": "是否并发"
            },
            {
                "id": 21362,
                "columnName": "is_update_data",
                "columnComment": "是否更新数据"
            },
            {
                "id": 21363,
                "columnName": "requests_recovery",
                "columnComment": "是否接受恢复执行"
            },
            {
                "id": 21364,
                "columnName": "job_data",
                "columnComment": "存放持久化job对象"
            }
        ]
    },
    {
        "id": 1201,
        "tableName": "qrtz_locks",
        "tableComment": "存储的悲观锁信息表",
        "columns": [
            {
                "id": 21365,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21366,
                "columnName": "lock_name",
                "columnComment": "悲观锁名称"
            }
        ]
    },
    {
        "id": 1202,
        "tableName": "qrtz_paused_trigger_grps",
        "tableComment": "暂停的触发器表",
        "columns": [
            {
                "id": 21367,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21368,
                "columnName": "trigger_group",
                "columnComment": "qrtz_triggers表trigger_group的外键"
            }
        ]
    },
    {
        "id": 1203,
        "tableName": "qrtz_scheduler_state",
        "tableComment": "调度器状态表",
        "columns": [
            {
                "id": 21369,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21370,
                "columnName": "instance_name",
                "columnComment": "实例名称"
            },
            {
                "id": 21371,
                "columnName": "last_checkin_time",
                "columnComment": "上次检查时间"
            },
            {
                "id": 21372,
                "columnName": "checkin_interval",
                "columnComment": "检查间隔时间"
            }
        ]
    },
    {
        "id": 1204,
        "tableName": "qrtz_simple_triggers",
        "tableComment": "简单触发器的信息表",
        "columns": [
            {
                "id": 21373,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21374,
                "columnName": "trigger_name",
                "columnComment": "qrtz_triggers表trigger_name的外键"
            },
            {
                "id": 21375,
                "columnName": "trigger_group",
                "columnComment": "qrtz_triggers表trigger_group的外键"
            },
            {
                "id": 21376,
                "columnName": "repeat_count",
                "columnComment": "重复的次数统计"
            },
            {
                "id": 21377,
                "columnName": "repeat_interval",
                "columnComment": "重复的间隔时间"
            },
            {
                "id": 21378,
                "columnName": "times_triggered",
                "columnComment": "已经触发的次数"
            }
        ]
    },
    {
        "id": 1205,
        "tableName": "qrtz_simprop_triggers",
        "tableComment": "同步机制的行锁表",
        "columns": [
            {
                "id": 21379,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21380,
                "columnName": "trigger_name",
                "columnComment": "qrtz_triggers表trigger_name的外键"
            },
            {
                "id": 21381,
                "columnName": "trigger_group",
                "columnComment": "qrtz_triggers表trigger_group的外键"
            },
            {
                "id": 21382,
                "columnName": "str_prop_1",
                "columnComment": "String类型的trigger的第一个参数"
            },
            {
                "id": 21383,
                "columnName": "str_prop_2",
                "columnComment": "String类型的trigger的第二个参数"
            },
            {
                "id": 21384,
                "columnName": "str_prop_3",
                "columnComment": "String类型的trigger的第三个参数"
            },
            {
                "id": 21385,
                "columnName": "int_prop_1",
                "columnComment": "int类型的trigger的第一个参数"
            },
            {
                "id": 21386,
                "columnName": "int_prop_2",
                "columnComment": "int类型的trigger的第二个参数"
            },
            {
                "id": 21387,
                "columnName": "long_prop_1",
                "columnComment": "long类型的trigger的第一个参数"
            },
            {
                "id": 21388,
                "columnName": "long_prop_2",
                "columnComment": "long类型的trigger的第二个参数"
            },
            {
                "id": 21389,
                "columnName": "dec_prop_1",
                "columnComment": "decimal类型的trigger的第一个参数"
            },
            {
                "id": 21390,
                "columnName": "dec_prop_2",
                "columnComment": "decimal类型的trigger的第二个参数"
            },
            {
                "id": 21391,
                "columnName": "bool_prop_1",
                "columnComment": "Boolean类型的trigger的第一个参数"
            },
            {
                "id": 21392,
                "columnName": "bool_prop_2",
                "columnComment": "Boolean类型的trigger的第二个参数"
            }
        ]
    },
    {
        "id": 1206,
        "tableName": "qrtz_triggers",
        "tableComment": "触发器详细信息表",
        "columns": [
            {
                "id": 21393,
                "columnName": "sched_name",
                "columnComment": "调度名称"
            },
            {
                "id": 21394,
                "columnName": "trigger_name",
                "columnComment": "触发器的名字"
            },
            {
                "id": 21395,
                "columnName": "trigger_group",
                "columnComment": "触发器所属组的名字"
            },
            {
                "id": 21396,
                "columnName": "job_name",
                "columnComment": "qrtz_job_details表job_name的外键"
            },
            {
                "id": 21397,
                "columnName": "job_group",
                "columnComment": "qrtz_job_details表job_group的外键"
            },
            {
                "id": 21398,
                "columnName": "description",
                "columnComment": "相关介绍"
            },
            {
                "id": 21399,
                "columnName": "next_fire_time",
                "columnComment": "上一次触发时间（毫秒）"
            },
            {
                "id": 21400,
                "columnName": "prev_fire_time",
                "columnComment": "下一次触发时间（默认为-1表示不触发）"
            },
            {
                "id": 21401,
                "columnName": "priority",
                "columnComment": "优先级"
            },
            {
                "id": 21402,
                "columnName": "trigger_state",
                "columnComment": "触发器状态"
            },
            {
                "id": 21403,
                "columnName": "trigger_type",
                "columnComment": "触发器的类型"
            },
            {
                "id": 21404,
                "columnName": "start_time",
                "columnComment": "开始时间"
            },
            {
                "id": 21405,
                "columnName": "end_time",
                "columnComment": "结束时间"
            },
            {
                "id": 21406,
                "columnName": "calendar_name",
                "columnComment": "日程表名称"
            },
            {
                "id": 21407,
                "columnName": "misfire_instr",
                "columnComment": "补偿执行的策略"
            },
            {
                "id": 21408,
                "columnName": "job_data",
                "columnComment": "存放持久化job对象"
            }
        ]
    },
    {
        "id": 1207,
        "tableName": "sys_attachment",
        "tableComment": "附件表",
        "columns": [
            {
                "id": 21409,
                "columnName": "attachment_id",
                "columnComment": "附件ID"
            },
            {
                "id": 21410,
                "columnName": "source_doc_id",
                "columnComment": "关联的业务单据ID"
            },
            {
                "id": 21411,
                "columnName": "source_doc_type",
                "columnComment": "业务单据类型"
            },
            {
                "id": 21412,
                "columnName": "file_url",
                "columnComment": "访问URL"
            },
            {
                "id": 21413,
                "columnName": "base_path",
                "columnComment": "域名"
            },
            {
                "id": 21414,
                "columnName": "file_name",
                "columnComment": "文件名"
            },
            {
                "id": 21415,
                "columnName": "orignal_name",
                "columnComment": "原来的文件名"
            },
            {
                "id": 21416,
                "columnName": "file_type",
                "columnComment": "文件类型"
            },
            {
                "id": 21417,
                "columnName": "file_size",
                "columnComment": "文件大小"
            }
        ]
    },
    {
        "id": 1208,
        "tableName": "sys_auto_code_part",
        "tableComment": "编码生成规则组成表",
        "columns": [
            {
                "id": 21427,
                "columnName": "part_id",
                "columnComment": "分段ID"
            },
            {
                "id": 21428,
                "columnName": "rule_id",
                "columnComment": "规则ID"
            },
            {
                "id": 21430,
                "columnName": "part_type",
                "columnComment": "分段类型，INPUTCHAR：输入字符，NOWDATE：当前日期时间，FIXCHAR：固定字符，SERIALNO：流水号"
            },
            {
                "id": 21432,
                "columnName": "part_name",
                "columnComment": "分段名称"
            },
            {
                "id": 21433,
                "columnName": "part_length",
                "columnComment": "分段长度"
            },
            {
                "id": 21434,
                "columnName": "date_format",
                "columnComment": ""
            },
            {
                "id": 21435,
                "columnName": "input_character",
                "columnComment": "输入字符"
            },
            {
                "id": 21436,
                "columnName": "fix_character",
                "columnComment": "固定字符"
            },
            {
                "id": 21437,
                "columnName": "seria_start_no",
                "columnComment": "流水号起始值"
            },
            {
                "id": 21438,
                "columnName": "seria_step",
                "columnComment": "流水号步长"
            },
            {
                "id": 21439,
                "columnName": "seria_now_no",
                "columnComment": "流水号当前值"
            },
            {
                "id": 21440,
                "columnName": "cycle_flag",
                "columnComment": "流水号是否循环"
            },
            {
                "id": 21441,
                "columnName": "cycle_method",
                "columnComment": "循环方式，YEAR：按年，MONTH：按月，DAY：按天，HOUR：按小时，MINITE：按分钟，OTHER：按传入字符变"
            }
        ]
    },
    {
        "id": 1209,
        "tableName": "sys_auto_code_result",
        "tableComment": "编码生成记录表",
        "columns": [
            {
                "id": 21451,
                "columnName": "code_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21452,
                "columnName": "rule_id",
                "columnComment": "规则ID"
            },
            {
                "id": 21453,
                "columnName": "gen_date",
                "columnComment": "生成日期时间"
            },
            {
                "id": 21455,
                "columnName": "last_result",
                "columnComment": "最后产生的值"
            },
            {
                "id": 21456,
                "columnName": "last_serial_no",
                "columnComment": "最后产生的流水号"
            },
            {
                "id": 21457,
                "columnName": "last_input_char",
                "columnComment": "最后传入的参数"
            }
        ]
    },
    {
        "id": 1210,
        "tableName": "sys_auto_code_rule",
        "tableComment": "编码生成规则表",
        "columns": [
            {
                "id": 21467,
                "columnName": "rule_id",
                "columnComment": "规则ID"
            },
            {
                "id": 21468,
                "columnName": "rule_code",
                "columnComment": "规则编码"
            },
            {
                "id": 21469,
                "columnName": "rule_name",
                "columnComment": "规则名称"
            },
            {
                "id": 21470,
                "columnName": "rule_desc",
                "columnComment": "描述"
            },
            {
                "id": 21471,
                "columnName": "max_length",
                "columnComment": "最大长度"
            },
            {
                "id": 21472,
                "columnName": "is_padded",
                "columnComment": "是否补齐"
            },
            {
                "id": 21473,
                "columnName": "padded_char",
                "columnComment": "补齐字符"
            },
            {
                "id": 21474,
                "columnName": "padded_method",
                "columnComment": "补齐方式"
            },
            {
                "id": 21475,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1211,
        "tableName": "sys_config",
        "tableComment": "参数配置表",
        "columns": [
            {
                "id": 21485,
                "columnName": "config_id",
                "columnComment": "参数主键"
            },
            {
                "id": 21486,
                "columnName": "config_name",
                "columnComment": "参数名称"
            },
            {
                "id": 21487,
                "columnName": "config_key",
                "columnComment": "参数键名"
            },
            {
                "id": 21488,
                "columnName": "config_value",
                "columnComment": "参数键值"
            },
            {
                "id": 21489,
                "columnName": "config_type",
                "columnComment": "系统内置（Y是 N否）"
            }
        ]
    },
    {
        "id": 1212,
        "tableName": "sys_dept",
        "tableComment": "部门表",
        "columns": [
            {
                "id": 21495,
                "columnName": "dept_id",
                "columnComment": "部门id"
            },
            {
                "id": 21496,
                "columnName": "parent_id",
                "columnComment": "父部门id"
            },
            {
                "id": 21497,
                "columnName": "ancestors",
                "columnComment": "祖级列表"
            },
            {
                "id": 21498,
                "columnName": "dept_name",
                "columnComment": "部门名称"
            },
            {
                "id": 21499,
                "columnName": "order_num",
                "columnComment": "显示顺序"
            },
            {
                "id": 21500,
                "columnName": "leader",
                "columnComment": "负责人"
            },
            {
                "id": 21501,
                "columnName": "phone",
                "columnComment": "联系电话"
            },
            {
                "id": 21502,
                "columnName": "email",
                "columnComment": "邮箱"
            },
            {
                "id": 21504,
                "columnName": "del_flag",
                "columnComment": "删除标志（0代表存在 2代表删除）"
            }
        ]
    },
    {
        "id": 1213,
        "tableName": "sys_dict_data",
        "tableComment": "字典数据表",
        "columns": [
            {
                "id": 21509,
                "columnName": "dict_code",
                "columnComment": "字典编码"
            },
            {
                "id": 21510,
                "columnName": "dict_sort",
                "columnComment": "字典排序"
            },
            {
                "id": 21511,
                "columnName": "dict_label",
                "columnComment": "字典标签"
            },
            {
                "id": 21512,
                "columnName": "dict_value",
                "columnComment": "字典键值"
            },
            {
                "id": 21513,
                "columnName": "dict_type",
                "columnComment": "字典类型"
            },
            {
                "id": 21514,
                "columnName": "css_class",
                "columnComment": "样式属性（其他样式扩展）"
            },
            {
                "id": 21515,
                "columnName": "list_class",
                "columnComment": "表格回显样式"
            },
            {
                "id": 21516,
                "columnName": "is_default",
                "columnComment": "是否默认（Y是 N否）"
            }
        ]
    },
    {
        "id": 1214,
        "tableName": "sys_dict_type",
        "tableComment": "字典类型表",
        "columns": [
            {
                "id": 21523,
                "columnName": "dict_id",
                "columnComment": "字典主键"
            },
            {
                "id": 21524,
                "columnName": "dict_name",
                "columnComment": "字典名称"
            },
            {
                "id": 21525,
                "columnName": "dict_type",
                "columnComment": "字典类型"
            }
        ]
    },
    {
        "id": 1215,
        "tableName": "sys_job",
        "tableComment": "定时任务调度表",
        "columns": [
            {
                "id": 21532,
                "columnName": "job_id",
                "columnComment": "任务ID"
            },
            {
                "id": 21533,
                "columnName": "job_name",
                "columnComment": "任务名称"
            },
            {
                "id": 21534,
                "columnName": "job_group",
                "columnComment": "任务组名"
            },
            {
                "id": 21535,
                "columnName": "invoke_target",
                "columnComment": "调用目标字符串"
            },
            {
                "id": 21536,
                "columnName": "cron_expression",
                "columnComment": "cron执行表达式"
            },
            {
                "id": 21537,
                "columnName": "misfire_policy",
                "columnComment": "计划执行错误策略（1立即执行 2执行一次 3放弃执行）"
            },
            {
                "id": 21538,
                "columnName": "concurrent",
                "columnComment": "是否并发执行（0允许 1禁止）"
            }
        ]
    },
    {
        "id": 1216,
        "tableName": "sys_job_log",
        "tableComment": "定时任务调度日志表",
        "columns": [
            {
                "id": 21545,
                "columnName": "job_log_id",
                "columnComment": "任务日志ID"
            },
            {
                "id": 21546,
                "columnName": "job_name",
                "columnComment": "任务名称"
            },
            {
                "id": 21547,
                "columnName": "job_group",
                "columnComment": "任务组名"
            },
            {
                "id": 21548,
                "columnName": "invoke_target",
                "columnComment": "调用目标字符串"
            },
            {
                "id": 21549,
                "columnName": "job_message",
                "columnComment": "日志信息"
            },
            {
                "id": 21551,
                "columnName": "exception_info",
                "columnComment": "异常信息"
            }
        ]
    },
    {
        "id": 1217,
        "tableName": "sys_logininfor",
        "tableComment": "系统访问记录",
        "columns": [
            {
                "id": 21553,
                "columnName": "info_id",
                "columnComment": "访问ID"
            },
            {
                "id": 21554,
                "columnName": "user_name",
                "columnComment": "用户账号"
            },
            {
                "id": 21555,
                "columnName": "ipaddr",
                "columnComment": "登录IP地址"
            },
            {
                "id": 21556,
                "columnName": "login_location",
                "columnComment": "登录地点"
            },
            {
                "id": 21557,
                "columnName": "browser",
                "columnComment": "浏览器类型"
            },
            {
                "id": 21558,
                "columnName": "os",
                "columnComment": "操作系统"
            },
            {
                "id": 21560,
                "columnName": "msg",
                "columnComment": "提示消息"
            },
            {
                "id": 21561,
                "columnName": "login_time",
                "columnComment": "访问时间"
            }
        ]
    },
    {
        "id": 1218,
        "tableName": "sys_menu",
        "tableComment": "菜单权限表",
        "columns": [
            {
                "id": 21562,
                "columnName": "menu_id",
                "columnComment": "菜单ID"
            },
            {
                "id": 21563,
                "columnName": "menu_name",
                "columnComment": "菜单名称"
            },
            {
                "id": 21564,
                "columnName": "parent_id",
                "columnComment": "父菜单ID"
            },
            {
                "id": 21565,
                "columnName": "order_num",
                "columnComment": "显示顺序"
            },
            {
                "id": 21566,
                "columnName": "path",
                "columnComment": "路由地址"
            },
            {
                "id": 21567,
                "columnName": "component",
                "columnComment": "组件路径"
            },
            {
                "id": 21568,
                "columnName": "query",
                "columnComment": "路由参数"
            },
            {
                "id": 21569,
                "columnName": "is_frame",
                "columnComment": "是否为外链（0是 1否）"
            },
            {
                "id": 21570,
                "columnName": "is_cache",
                "columnComment": "是否缓存（0缓存 1不缓存）"
            },
            {
                "id": 21571,
                "columnName": "menu_type",
                "columnComment": "菜单类型（M目录 C菜单 F按钮）"
            },
            {
                "id": 21572,
                "columnName": "visible",
                "columnComment": "菜单状态（0显示 1隐藏）"
            },
            {
                "id": 21574,
                "columnName": "perms",
                "columnComment": "权限标识"
            },
            {
                "id": 21575,
                "columnName": "icon",
                "columnComment": "菜单图标"
            }
        ]
    },
    {
        "id": 1219,
        "tableName": "sys_message",
        "tableComment": "消息表",
        "columns": [
            {
                "id": 21581,
                "columnName": "message_id",
                "columnComment": "附件ID"
            },
            {
                "id": 21582,
                "columnName": "message_type",
                "columnComment": "消息类型"
            },
            {
                "id": 21583,
                "columnName": "message_level",
                "columnComment": "消息级别"
            },
            {
                "id": 21584,
                "columnName": "message_title",
                "columnComment": "标题"
            },
            {
                "id": 21585,
                "columnName": "message_content",
                "columnComment": "内容"
            },
            {
                "id": 21586,
                "columnName": "sender_id",
                "columnComment": "发送人ID"
            },
            {
                "id": 21587,
                "columnName": "sender_name",
                "columnComment": "发送人名称"
            },
            {
                "id": 21588,
                "columnName": "sender_nick",
                "columnComment": "发送人昵称"
            },
            {
                "id": 21589,
                "columnName": "recipient_id",
                "columnComment": "接收人ID"
            },
            {
                "id": 21590,
                "columnName": "recipient_name",
                "columnComment": "接收人名称"
            },
            {
                "id": 21591,
                "columnName": "recipient_nick",
                "columnComment": "接收人昵称"
            },
            {
                "id": 21592,
                "columnName": "process_time",
                "columnComment": "处理时间"
            },
            {
                "id": 21593,
                "columnName": "call_back",
                "columnComment": "回调地址"
            },
            {
                "id": 21595,
                "columnName": "deleted_flag",
                "columnComment": "是否删除"
            }
        ]
    },
    {
        "id": 1220,
        "tableName": "sys_notice",
        "tableComment": "通知公告表",
        "columns": [
            {
                "id": 21605,
                "columnName": "notice_id",
                "columnComment": "公告ID"
            },
            {
                "id": 21606,
                "columnName": "notice_title",
                "columnComment": "公告标题"
            },
            {
                "id": 21607,
                "columnName": "notice_type",
                "columnComment": "公告类型（1通知 2公告）"
            },
            {
                "id": 21608,
                "columnName": "notice_content",
                "columnComment": "公告内容"
            }
        ]
    },
    {
        "id": 1221,
        "tableName": "sys_oper_log",
        "tableComment": "操作日志记录",
        "columns": [
            {
                "id": 21615,
                "columnName": "oper_id",
                "columnComment": "日志主键"
            },
            {
                "id": 21616,
                "columnName": "title",
                "columnComment": "模块标题"
            },
            {
                "id": 21617,
                "columnName": "business_type",
                "columnComment": "业务类型（0其它 1新增 2修改 3删除）"
            },
            {
                "id": 21618,
                "columnName": "method",
                "columnComment": "方法名称"
            },
            {
                "id": 21619,
                "columnName": "request_method",
                "columnComment": "请求方式"
            },
            {
                "id": 21620,
                "columnName": "operator_type",
                "columnComment": "操作类别（0其它 1后台用户 2手机端用户）"
            },
            {
                "id": 21621,
                "columnName": "oper_name",
                "columnComment": "操作人员"
            },
            {
                "id": 21622,
                "columnName": "dept_name",
                "columnComment": "部门名称"
            },
            {
                "id": 21623,
                "columnName": "oper_url",
                "columnComment": "请求URL"
            },
            {
                "id": 21624,
                "columnName": "oper_ip",
                "columnComment": "主机地址"
            },
            {
                "id": 21625,
                "columnName": "oper_location",
                "columnComment": "操作地点"
            },
            {
                "id": 21626,
                "columnName": "oper_param",
                "columnComment": "请求参数"
            },
            {
                "id": 21627,
                "columnName": "json_result",
                "columnComment": "返回参数"
            },
            {
                "id": 21629,
                "columnName": "error_msg",
                "columnComment": "错误消息"
            },
            {
                "id": 21630,
                "columnName": "oper_time",
                "columnComment": "操作时间"
            }
        ]
    },
    {
        "id": 1222,
        "tableName": "sys_post",
        "tableComment": "岗位信息表",
        "columns": [
            {
                "id": 21631,
                "columnName": "post_id",
                "columnComment": "岗位ID"
            },
            {
                "id": 21632,
                "columnName": "post_code",
                "columnComment": "岗位编码"
            },
            {
                "id": 21633,
                "columnName": "post_name",
                "columnComment": "岗位名称"
            },
            {
                "id": 21634,
                "columnName": "post_sort",
                "columnComment": "显示顺序"
            }
        ]
    },
    {
        "id": 1223,
        "tableName": "sys_role",
        "tableComment": "角色信息表",
        "columns": [
            {
                "id": 21641,
                "columnName": "role_id",
                "columnComment": "角色ID"
            },
            {
                "id": 21642,
                "columnName": "role_name",
                "columnComment": "角色名称"
            },
            {
                "id": 21643,
                "columnName": "role_key",
                "columnComment": "角色权限字符串"
            },
            {
                "id": 21644,
                "columnName": "role_sort",
                "columnComment": "显示顺序"
            },
            {
                "id": 21645,
                "columnName": "data_scope",
                "columnComment": "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）"
            },
            {
                "id": 21646,
                "columnName": "menu_check_strictly",
                "columnComment": "菜单树选择项是否关联显示"
            },
            {
                "id": 21647,
                "columnName": "dept_check_strictly",
                "columnComment": "部门树选择项是否关联显示"
            },
            {
                "id": 21649,
                "columnName": "del_flag",
                "columnComment": "删除标志（0代表存在 2代表删除）"
            }
        ]
    },
    {
        "id": 1224,
        "tableName": "sys_role_dept",
        "tableComment": "角色和部门关联表",
        "columns": [
            {
                "id": 21655,
                "columnName": "role_id",
                "columnComment": "角色ID"
            },
            {
                "id": 21656,
                "columnName": "dept_id",
                "columnComment": "部门ID"
            }
        ]
    },
    {
        "id": 1225,
        "tableName": "sys_role_menu",
        "tableComment": "角色和菜单关联表",
        "columns": [
            {
                "id": 21657,
                "columnName": "role_id",
                "columnComment": "角色ID"
            },
            {
                "id": 21658,
                "columnName": "menu_id",
                "columnComment": "菜单ID"
            }
        ]
    },
    {
        "id": 1226,
        "tableName": "sys_user",
        "tableComment": "用户信息表",
        "columns": [
            {
                "id": 21659,
                "columnName": "user_id",
                "columnComment": "用户ID"
            },
            {
                "id": 21660,
                "columnName": "dept_id",
                "columnComment": "部门ID"
            },
            {
                "id": 21661,
                "columnName": "user_name",
                "columnComment": "用户账号"
            },
            {
                "id": 21662,
                "columnName": "nick_name",
                "columnComment": "用户昵称"
            },
            {
                "id": 21663,
                "columnName": "user_type",
                "columnComment": "用户类型（00系统用户）"
            },
            {
                "id": 21664,
                "columnName": "email",
                "columnComment": "用户邮箱"
            },
            {
                "id": 21665,
                "columnName": "phonenumber",
                "columnComment": "手机号码"
            },
            {
                "id": 21666,
                "columnName": "sex",
                "columnComment": "用户性别（0男 1女 2未知）"
            },
            {
                "id": 21667,
                "columnName": "avatar",
                "columnComment": "头像地址"
            },
            {
                "id": 21668,
                "columnName": "password",
                "columnComment": "密码"
            },
            {
                "id": 21670,
                "columnName": "del_flag",
                "columnComment": "删除标志（0代表存在 2代表删除）"
            },
            {
                "id": 21671,
                "columnName": "login_ip",
                "columnComment": "最后登录IP"
            },
            {
                "id": 21672,
                "columnName": "login_date",
                "columnComment": "最后登录时间"
            }
        ]
    },
    {
        "id": 1227,
        "tableName": "sys_user_post",
        "tableComment": "用户与岗位关联表",
        "columns": [
            {
                "id": 21678,
                "columnName": "user_id",
                "columnComment": "用户ID"
            },
            {
                "id": 21679,
                "columnName": "post_id",
                "columnComment": "岗位ID"
            }
        ]
    },
    {
        "id": 1228,
        "tableName": "sys_user_role",
        "tableComment": "用户和角色关联表",
        "columns": [
            {
                "id": 21680,
                "columnName": "user_id",
                "columnComment": "用户ID"
            },
            {
                "id": 21681,
                "columnName": "role_id",
                "columnComment": "角色ID"
            }
        ]
    },
    {
        "id": 1229,
        "tableName": "tm_tool",
        "tableComment": "工装夹具清单表",
        "columns": [
            {
                "id": 21682,
                "columnName": "tool_id",
                "columnComment": "工装夹具ID"
            },
            {
                "id": 21683,
                "columnName": "tool_code",
                "columnComment": "工装夹具编码"
            },
            {
                "id": 21684,
                "columnName": "tool_name",
                "columnComment": "工装夹具名称"
            },
            {
                "id": 21685,
                "columnName": "brand",
                "columnComment": "品牌"
            },
            {
                "id": 21686,
                "columnName": "spec",
                "columnComment": "型号"
            },
            {
                "id": 21687,
                "columnName": "tool_type_id",
                "columnComment": "工装夹具类型ID"
            },
            {
                "id": 21688,
                "columnName": "tool_type_code",
                "columnComment": "工装夹具类型编码"
            },
            {
                "id": 21689,
                "columnName": "tool_type_name",
                "columnComment": "工装夹具类型名称"
            },
            {
                "id": 21690,
                "columnName": "code_flag",
                "columnComment": "是否单独编码管理"
            },
            {
                "id": 21691,
                "columnName": "quantity",
                "columnComment": "数量"
            },
            {
                "id": 21692,
                "columnName": "quantity_avail",
                "columnComment": "可用数量"
            },
            {
                "id": 21693,
                "columnName": "mainten_type",
                "columnComment": "保养维护类型"
            },
            {
                "id": 21694,
                "columnName": "next_mainten_period",
                "columnComment": "下一次保养周期"
            },
            {
                "id": 21695,
                "columnName": "next_mainten_date",
                "columnComment": "下一次保养日期"
            }
        ]
    },
    {
        "id": 1230,
        "tableName": "tm_tool_type",
        "tableComment": "工装夹具类型表",
        "columns": [
            {
                "id": 21706,
                "columnName": "tool_type_id",
                "columnComment": "工装夹具类型ID"
            },
            {
                "id": 21707,
                "columnName": "tool_type_code",
                "columnComment": "类型编码"
            },
            {
                "id": 21708,
                "columnName": "tool_type_name",
                "columnComment": "类型名称"
            },
            {
                "id": 21709,
                "columnName": "code_flag",
                "columnComment": "是否编码管理"
            },
            {
                "id": 21710,
                "columnName": "mainten_type",
                "columnComment": "保养维护类型"
            },
            {
                "id": 21711,
                "columnName": "mainten_period",
                "columnComment": "保养周期"
            }
        ]
    },
    {
        "id": 1231,
        "tableName": "ureport_file_tbl",
        "tableComment": "",
        "columns": [
            {
                "id": 21721,
                "columnName": "id_",
                "columnComment": ""
            },
            {
                "id": 21722,
                "columnName": "name_",
                "columnComment": ""
            },
            {
                "id": 21723,
                "columnName": "content_",
                "columnComment": ""
            },
            {
                "id": 21724,
                "columnName": "create_time_",
                "columnComment": ""
            },
            {
                "id": 21725,
                "columnName": "update_time_",
                "columnComment": ""
            }
        ]
    },
    {
        "id": 1232,
        "tableName": "wm_barcode",
        "tableComment": "条码清单表",
        "columns": [
            {
                "id": 21726,
                "columnName": "barcode_id",
                "columnComment": "条码ID"
            },
            {
                "id": 21727,
                "columnName": "barcode_formart",
                "columnComment": "条码格式"
            },
            {
                "id": 21728,
                "columnName": "barcode_type",
                "columnComment": "条码类型"
            },
            {
                "id": 21729,
                "columnName": "barcode_content",
                "columnComment": "条码内容"
            },
            {
                "id": 21730,
                "columnName": "bussiness_id",
                "columnComment": "业务ID"
            },
            {
                "id": 21731,
                "columnName": "bussiness_code",
                "columnComment": "业务编码"
            },
            {
                "id": 21732,
                "columnName": "bussiness_name",
                "columnComment": "业务名称"
            },
            {
                "id": 21733,
                "columnName": "barcode_url",
                "columnComment": "条码地址"
            },
            {
                "id": 21734,
                "columnName": "enable_flag",
                "columnComment": "是否生效"
            }
        ]
    },
    {
        "id": 1233,
        "tableName": "wm_barcode_config",
        "tableComment": "条码配置",
        "columns": [
            {
                "id": 21744,
                "columnName": "config_id",
                "columnComment": "配置ID"
            },
            {
                "id": 21745,
                "columnName": "barcode_formart",
                "columnComment": "条码格式"
            },
            {
                "id": 21746,
                "columnName": "barcode_type",
                "columnComment": "条码类型"
            },
            {
                "id": 21747,
                "columnName": "content_formart",
                "columnComment": "内容格式"
            },
            {
                "id": 21748,
                "columnName": "content_example",
                "columnComment": "内容样例"
            },
            {
                "id": 21749,
                "columnName": "auto_gen_flag",
                "columnComment": "是否自动生成"
            },
            {
                "id": 21750,
                "columnName": "default_template",
                "columnComment": "默认的打印模板"
            },
            {
                "id": 21751,
                "columnName": "enable_flag",
                "columnComment": "是否生效"
            }
        ]
    },
    {
        "id": 1234,
        "tableName": "wm_issue_header",
        "tableComment": "生产领料单头表",
        "columns": [
            {
                "id": 21761,
                "columnName": "issue_id",
                "columnComment": "领料单ID"
            },
            {
                "id": 21763,
                "columnName": "issue_name",
                "columnComment": "领料单名称"
            },
            {
                "id": 21764,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 21766,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 21767,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 21768,
                "columnName": "workorder_code",
                "columnComment": "生产工单编码"
            },
            {
                "id": 21769,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 21770,
                "columnName": "task_code",
                "columnComment": "生产任务编码"
            },
            {
                "id": 21771,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 21772,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 21773,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 21774,
                "columnName": "client_nick",
                "columnComment": "客户简称"
            },
            {
                "id": 21775,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 21776,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 21777,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 21778,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 21779,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 21780,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 21781,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 21782,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 21783,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 21784,
                "columnName": "issue_date",
                "columnComment": "领料日期"
            }
        ]
    },
    {
        "id": 1235,
        "tableName": "wm_issue_line",
        "tableComment": "生产领料单行表",
        "columns": [
            {
                "id": 21795,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 21796,
                "columnName": "issue_id",
                "columnComment": "领料单ID"
            },
            {
                "id": 21797,
                "columnName": "material_stock_id",
                "columnComment": "库存ID"
            },
            {
                "id": 21798,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21799,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21800,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21801,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21802,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21803,
                "columnName": "quantity_issued",
                "columnComment": "领料数量"
            },
            {
                "id": 21804,
                "columnName": "batch_code",
                "columnComment": "领料批次号"
            },
            {
                "id": 21805,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 21806,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 21807,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 21808,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 21809,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 21810,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 21811,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 21812,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 21813,
                "columnName": "area_name",
                "columnComment": "库位名称"
            }
        ]
    },
    {
        "id": 1236,
        "tableName": "wm_item_consume",
        "tableComment": "物料消耗记录表",
        "columns": [
            {
                "id": 21823,
                "columnName": "record_id",
                "columnComment": "记录ID"
            },
            {
                "id": 21824,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 21825,
                "columnName": "workorder_code",
                "columnComment": "生产工单编码"
            },
            {
                "id": 21826,
                "columnName": "workorder_name",
                "columnComment": "生产工单名称"
            },
            {
                "id": 21827,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 21829,
                "columnName": "task_name",
                "columnComment": "生产任务名称"
            },
            {
                "id": 21830,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 21832,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 21833,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 21835,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 21836,
                "columnName": "consume_date",
                "columnComment": "消耗日期"
            }
        ]
    },
    {
        "id": 1237,
        "tableName": "wm_item_consume_line",
        "tableComment": "物料消耗记录行表",
        "columns": [
            {
                "id": 21847,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 21848,
                "columnName": "record_id",
                "columnComment": "消耗记录ID"
            },
            {
                "id": 21849,
                "columnName": "material_stock_id",
                "columnComment": "库存ID"
            },
            {
                "id": 21850,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21851,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21852,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21853,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21854,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21855,
                "columnName": "quantity_consume",
                "columnComment": "消耗数量"
            },
            {
                "id": 21856,
                "columnName": "batch_code",
                "columnComment": "领料批次号"
            },
            {
                "id": 21857,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 21858,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 21859,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 21860,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 21861,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 21862,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 21863,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 21864,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 21865,
                "columnName": "area_name",
                "columnComment": "库位名称"
            }
        ]
    },
    {
        "id": 1238,
        "tableName": "wm_item_recpt",
        "tableComment": "物料入库单表",
        "columns": [
            {
                "id": 21875,
                "columnName": "recpt_id",
                "columnComment": "入库单ID"
            },
            {
                "id": 21877,
                "columnName": "recpt_name",
                "columnComment": "入库单名称"
            },
            {
                "id": 21878,
                "columnName": "iqc_id",
                "columnComment": "来料检验单ID"
            },
            {
                "id": 21881,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 21882,
                "columnName": "vendor_code",
                "columnComment": "供应商编码"
            },
            {
                "id": 21883,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 21884,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            },
            {
                "id": 21885,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 21886,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 21887,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 21888,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 21889,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 21890,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 21891,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 21892,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 21893,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 21894,
                "columnName": "recpt_date",
                "columnComment": "入库日期"
            }
        ]
    },
    {
        "id": 1239,
        "tableName": "wm_item_recpt_line",
        "tableComment": "物料入库单行表",
        "columns": [
            {
                "id": 21905,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 21906,
                "columnName": "recpt_id",
                "columnComment": "入库单ID"
            },
            {
                "id": 21907,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21908,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21909,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21910,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21911,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21912,
                "columnName": "quantity_recived",
                "columnComment": "入库数量"
            },
            {
                "id": 21913,
                "columnName": "batch_code",
                "columnComment": "入库批次号"
            },
            {
                "id": 21914,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 21915,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 21916,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 21917,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 21918,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 21919,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 21920,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 21921,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 21922,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 21923,
                "columnName": "expire_date",
                "columnComment": "有效期"
            }
        ]
    },
    {
        "id": 1240,
        "tableName": "wm_material_stock",
        "tableComment": "库存记录表",
        "columns": [
            {
                "id": 21933,
                "columnName": "material_stock_id",
                "columnComment": "事务ID"
            },
            {
                "id": 21934,
                "columnName": "item_type_id",
                "columnComment": "物料类型ID"
            },
            {
                "id": 21935,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 21936,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 21937,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 21938,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 21939,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 21940,
                "columnName": "batch_code",
                "columnComment": "入库批次号"
            },
            {
                "id": 21941,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 21942,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 21943,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 21944,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 21945,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 21946,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 21947,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 21948,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 21949,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 21950,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 21952,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 21953,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            },
            {
                "id": 21954,
                "columnName": "quantity_onhand",
                "columnComment": "在库数量"
            },
            {
                "id": 21955,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 21957,
                "columnName": "recpt_date",
                "columnComment": "入库时间"
            },
            {
                "id": 21958,
                "columnName": "expire_date",
                "columnComment": "库存有效期"
            }
        ]
    },
    {
        "id": 1241,
        "tableName": "wm_package",
        "tableComment": "装箱单表",
        "columns": [
            {
                "id": 21967,
                "columnName": "package_id",
                "columnComment": "装箱单ID"
            },
            {
                "id": 21968,
                "columnName": "parent_id",
                "columnComment": "父箱ID"
            },
            {
                "id": 21969,
                "columnName": "ancestors",
                "columnComment": "所有父节点ID"
            },
            {
                "id": 21971,
                "columnName": "barcode_id",
                "columnComment": "条码ID"
            },
            {
                "id": 21972,
                "columnName": "barcode_content",
                "columnComment": "条码内容"
            },
            {
                "id": 21973,
                "columnName": "barcode_url",
                "columnComment": "条码地址"
            },
            {
                "id": 21974,
                "columnName": "package_date",
                "columnComment": "装箱日期"
            },
            {
                "id": 21977,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 21978,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 21979,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 21980,
                "columnName": "client_nick",
                "columnComment": "客户简称"
            },
            {
                "id": 21981,
                "columnName": "package_length",
                "columnComment": "箱长度"
            },
            {
                "id": 21982,
                "columnName": "package_width",
                "columnComment": "箱宽度"
            },
            {
                "id": 21983,
                "columnName": "package_height",
                "columnComment": "箱高度"
            },
            {
                "id": 21984,
                "columnName": "size_unit",
                "columnComment": "尺寸单位"
            },
            {
                "id": 21985,
                "columnName": "net_weight",
                "columnComment": "净重"
            },
            {
                "id": 21986,
                "columnName": "cross_weight",
                "columnComment": "毛重"
            },
            {
                "id": 21987,
                "columnName": "weight_unit",
                "columnComment": "重量单位"
            },
            {
                "id": 21988,
                "columnName": "inspector",
                "columnComment": "检查员用户名"
            },
            {
                "id": 21989,
                "columnName": "inspector_name",
                "columnComment": "检查员名称"
            },
            {
                "id": 21991,
                "columnName": "enable_flag",
                "columnComment": "是否生效"
            }
        ]
    },
    {
        "id": 1242,
        "tableName": "wm_package_line",
        "tableComment": "装箱明细表",
        "columns": [
            {
                "id": 22001,
                "columnName": "line_id",
                "columnComment": "明细行ID"
            },
            {
                "id": 22002,
                "columnName": "package_id",
                "columnComment": "装箱单ID"
            },
            {
                "id": 22003,
                "columnName": "material_stock_id",
                "columnComment": "库存记录ID"
            },
            {
                "id": 22004,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22005,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22006,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22007,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22008,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22009,
                "columnName": "quantity_package",
                "columnComment": "装箱数量"
            },
            {
                "id": 22010,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22012,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22013,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22014,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22015,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22016,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22017,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22018,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22019,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22020,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22021,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22022,
                "columnName": "expire_date",
                "columnComment": "有效期"
            }
        ]
    },
    {
        "id": 1243,
        "tableName": "wm_product_produce",
        "tableComment": "产品产出记录表",
        "columns": [
            {
                "id": 22032,
                "columnName": "record_id",
                "columnComment": "入库单ID"
            },
            {
                "id": 22033,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22034,
                "columnName": "workorder_code",
                "columnComment": "生产工单编码"
            },
            {
                "id": 22035,
                "columnName": "workorder_name",
                "columnComment": "生产工单名称"
            },
            {
                "id": 22036,
                "columnName": "task_id",
                "columnComment": "生产任务ID"
            },
            {
                "id": 22038,
                "columnName": "task_name",
                "columnComment": "生产任务名称"
            },
            {
                "id": 22039,
                "columnName": "workstation_id",
                "columnComment": "工作站ID"
            },
            {
                "id": 22041,
                "columnName": "workstation_name",
                "columnComment": "工作站名称"
            },
            {
                "id": 22042,
                "columnName": "process_id",
                "columnComment": "工序ID"
            },
            {
                "id": 22044,
                "columnName": "process_name",
                "columnComment": "工序名称"
            },
            {
                "id": 22045,
                "columnName": "produce_date",
                "columnComment": "生产日期"
            }
        ]
    },
    {
        "id": 1244,
        "tableName": "wm_product_produce_line",
        "tableComment": "产品产出记录表行表",
        "columns": [
            {
                "id": 22056,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 22057,
                "columnName": "record_id",
                "columnComment": "产出记录ID"
            },
            {
                "id": 22058,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22059,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22060,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22061,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22062,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22063,
                "columnName": "quantity_produce",
                "columnComment": "产出数量"
            },
            {
                "id": 22064,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22065,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22066,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22067,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22068,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22069,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22070,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22071,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22072,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22073,
                "columnName": "area_name",
                "columnComment": "库位名称"
            }
        ]
    },
    {
        "id": 1245,
        "tableName": "wm_product_recpt",
        "tableComment": "产品入库录表",
        "columns": [
            {
                "id": 22083,
                "columnName": "recpt_id",
                "columnComment": "入库单ID"
            },
            {
                "id": 22085,
                "columnName": "recpt_name",
                "columnComment": "入库单名称"
            },
            {
                "id": 22086,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22087,
                "columnName": "workorder_code",
                "columnComment": "生产工单编码"
            },
            {
                "id": 22088,
                "columnName": "workorder_name",
                "columnComment": "生产工单名称"
            },
            {
                "id": 22089,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22090,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22091,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22092,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22093,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22094,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22095,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22096,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22097,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22098,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22099,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22100,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22101,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22102,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22103,
                "columnName": "recpt_date",
                "columnComment": "入库日期"
            }
        ]
    },
    {
        "id": 1246,
        "tableName": "wm_product_recpt_line",
        "tableComment": "产品入库记录表行表",
        "columns": [
            {
                "id": 22114,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 22115,
                "columnName": "recpt_id",
                "columnComment": "入库记录ID"
            },
            {
                "id": 22116,
                "columnName": "material_stock_id",
                "columnComment": "库存记录ID"
            },
            {
                "id": 22117,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22118,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22119,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22120,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22121,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22122,
                "columnName": "quantity_recived",
                "columnComment": "入库数量"
            },
            {
                "id": 22123,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22124,
                "columnName": "workorder_code",
                "columnComment": "生产工单编码"
            },
            {
                "id": 22125,
                "columnName": "workorder_name",
                "columnComment": "生产工单名称"
            },
            {
                "id": 22126,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22127,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22128,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22129,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22130,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22131,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22132,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22133,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22134,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22135,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22136,
                "columnName": "expire_date",
                "columnComment": "有效期"
            }
        ]
    },
    {
        "id": 1247,
        "tableName": "wm_product_salse",
        "tableComment": "销售出库单表",
        "columns": [
            {
                "id": 22146,
                "columnName": "salse_id",
                "columnComment": "出库单ID"
            },
            {
                "id": 22148,
                "columnName": "salse_name",
                "columnComment": "出库单名称"
            },
            {
                "id": 22149,
                "columnName": "oqc_id",
                "columnComment": "出货检验单ID"
            },
            {
                "id": 22152,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 22153,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 22154,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 22155,
                "columnName": "client_nick",
                "columnComment": "客户简称"
            },
            {
                "id": 22156,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22157,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22158,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22159,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22160,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22161,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22162,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22163,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22164,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22165,
                "columnName": "salse_date",
                "columnComment": "出库日期"
            }
        ]
    },
    {
        "id": 1248,
        "tableName": "wm_product_salse_line",
        "tableComment": "产品销售出库行表",
        "columns": [
            {
                "id": 22176,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 22177,
                "columnName": "salse_id",
                "columnComment": "出库记录ID"
            },
            {
                "id": 22178,
                "columnName": "material_stock_id",
                "columnComment": "库存记录ID"
            },
            {
                "id": 22179,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22180,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22181,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22182,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22183,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22184,
                "columnName": "quantity_salse",
                "columnComment": "出库数量"
            },
            {
                "id": 22185,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22186,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22187,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22188,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22189,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22190,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22191,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22192,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22193,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22194,
                "columnName": "area_name",
                "columnComment": "库位名称"
            }
        ]
    },
    {
        "id": 1249,
        "tableName": "wm_rt_issue",
        "tableComment": "生产退料单头表",
        "columns": [
            {
                "id": 22204,
                "columnName": "rt_id",
                "columnComment": "退料单ID"
            },
            {
                "id": 22206,
                "columnName": "rt_name",
                "columnComment": "退料单名称"
            },
            {
                "id": 22207,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22208,
                "columnName": "workorder_code",
                "columnComment": "生产工单编码"
            },
            {
                "id": 22209,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22210,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22211,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22212,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22213,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22214,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22215,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22216,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22217,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22218,
                "columnName": "rt_date",
                "columnComment": "退料日期"
            }
        ]
    },
    {
        "id": 1250,
        "tableName": "wm_rt_issue_line",
        "tableComment": "生产退料单行表",
        "columns": [
            {
                "id": 22229,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 22230,
                "columnName": "rt_id",
                "columnComment": "退料单ID"
            },
            {
                "id": 22231,
                "columnName": "material_stock_id",
                "columnComment": "库存ID"
            },
            {
                "id": 22232,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22233,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22234,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22235,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22236,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22237,
                "columnName": "quantity_rt",
                "columnComment": "退料数量"
            },
            {
                "id": 22238,
                "columnName": "batch_code",
                "columnComment": "领料批次号"
            },
            {
                "id": 22239,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22240,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22241,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22242,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22243,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22244,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22245,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22246,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22247,
                "columnName": "area_name",
                "columnComment": "库位名称"
            }
        ]
    },
    {
        "id": 1251,
        "tableName": "wm_rt_salse",
        "tableComment": "产品销售退货单表",
        "columns": [
            {
                "id": 22257,
                "columnName": "rt_id",
                "columnComment": "退货单ID"
            },
            {
                "id": 22259,
                "columnName": "rt_name",
                "columnComment": "退货单名称"
            },
            {
                "id": 22261,
                "columnName": "client_id",
                "columnComment": "客户ID"
            },
            {
                "id": 22262,
                "columnName": "client_code",
                "columnComment": "客户编码"
            },
            {
                "id": 22263,
                "columnName": "client_name",
                "columnComment": "客户名称"
            },
            {
                "id": 22264,
                "columnName": "client_nick",
                "columnComment": "客户简称"
            },
            {
                "id": 22265,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22266,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22267,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22268,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22269,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22270,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22271,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22272,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22273,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22274,
                "columnName": "rt_date",
                "columnComment": "退货日期"
            },
            {
                "id": 22275,
                "columnName": "rt_reason",
                "columnComment": "退货原因"
            }
        ]
    },
    {
        "id": 1252,
        "tableName": "wm_rt_salse_line",
        "tableComment": "产品销售退货行表",
        "columns": [
            {
                "id": 22286,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 22287,
                "columnName": "rt_id",
                "columnComment": "退货单ID"
            },
            {
                "id": 22288,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22289,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22290,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22291,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22292,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22293,
                "columnName": "quantity_rted",
                "columnComment": "退货数量"
            },
            {
                "id": 22294,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22295,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22296,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22297,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22298,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22299,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22300,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22301,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22302,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22303,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22304,
                "columnName": "expire_date",
                "columnComment": "有效期"
            }
        ]
    },
    {
        "id": 1253,
        "tableName": "wm_rt_vendor",
        "tableComment": "供应商退货表",
        "columns": [
            {
                "id": 22314,
                "columnName": "rt_id",
                "columnComment": "退货单ID"
            },
            {
                "id": 22316,
                "columnName": "rt_name",
                "columnComment": "退货单名称"
            },
            {
                "id": 22318,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 22319,
                "columnName": "vendor_code",
                "columnComment": "供应商编码"
            },
            {
                "id": 22320,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 22321,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            },
            {
                "id": 22322,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22323,
                "columnName": "rt_date",
                "columnComment": "退货日期"
            }
        ]
    },
    {
        "id": 1254,
        "tableName": "wm_rt_vendor_line",
        "tableComment": "供应商退货行表",
        "columns": [
            {
                "id": 22334,
                "columnName": "line_id",
                "columnComment": "行ID"
            },
            {
                "id": 22335,
                "columnName": "rt_id",
                "columnComment": "退货单ID"
            },
            {
                "id": 22336,
                "columnName": "material_stock_id",
                "columnComment": "库存记录ID"
            },
            {
                "id": 22337,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22338,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22339,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22340,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22341,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22342,
                "columnName": "quantity_rted",
                "columnComment": "退货数量"
            },
            {
                "id": 22343,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22344,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22345,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22346,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22347,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22348,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22349,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22350,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22351,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22352,
                "columnName": "area_name",
                "columnComment": "库位名称"
            }
        ]
    },
    {
        "id": 1255,
        "tableName": "wm_sn",
        "tableComment": "SN码表",
        "columns": [
            {
                "id": 22362,
                "columnName": "sn_id",
                "columnComment": "SN码ID"
            },
            {
                "id": 22363,
                "columnName": "sn_code",
                "columnComment": "SN码"
            },
            {
                "id": 22364,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22365,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22366,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22367,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22368,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22369,
                "columnName": "batch_code",
                "columnComment": "批次号"
            }
        ]
    },
    {
        "id": 1256,
        "tableName": "wm_storage_area",
        "tableComment": "库位表",
        "columns": [
            {
                "id": 22379,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22380,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22381,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22382,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22383,
                "columnName": "area",
                "columnComment": "面积"
            },
            {
                "id": 22384,
                "columnName": "max_loa",
                "columnComment": "最大载重量"
            },
            {
                "id": 22385,
                "columnName": "position_x",
                "columnComment": "库位位置X"
            },
            {
                "id": 22386,
                "columnName": "position_y",
                "columnComment": "库位位置y"
            },
            {
                "id": 22387,
                "columnName": "position_z",
                "columnComment": "库位位置z"
            },
            {
                "id": 22388,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    },
    {
        "id": 1257,
        "tableName": "wm_storage_location",
        "tableComment": "库区表",
        "columns": [
            {
                "id": 22398,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22399,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22400,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22401,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22402,
                "columnName": "area",
                "columnComment": "面积"
            },
            {
                "id": 22403,
                "columnName": "area_flag",
                "columnComment": "是否开启库位管理"
            }
        ]
    },
    {
        "id": 1258,
        "tableName": "wm_transaction",
        "tableComment": "库存事务表",
        "columns": [
            {
                "id": 22413,
                "columnName": "transaction_id",
                "columnComment": "事务ID"
            },
            {
                "id": 22414,
                "columnName": "transaction_type",
                "columnComment": "事务类型"
            },
            {
                "id": 22415,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22416,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22417,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22418,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22419,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22420,
                "columnName": "batch_code",
                "columnComment": "入库批次号"
            },
            {
                "id": 22421,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22422,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22423,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22424,
                "columnName": "location_id",
                "columnComment": "库区ID"
            },
            {
                "id": 22425,
                "columnName": "location_code",
                "columnComment": "库区编码"
            },
            {
                "id": 22426,
                "columnName": "location_name",
                "columnComment": "库区名称"
            },
            {
                "id": 22427,
                "columnName": "area_id",
                "columnComment": "库位ID"
            },
            {
                "id": 22428,
                "columnName": "area_code",
                "columnComment": "库位编码"
            },
            {
                "id": 22429,
                "columnName": "area_name",
                "columnComment": "库位名称"
            },
            {
                "id": 22430,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 22432,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 22433,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            },
            {
                "id": 22434,
                "columnName": "source_doc_type",
                "columnComment": "单据类型"
            },
            {
                "id": 22435,
                "columnName": "source_doc_id",
                "columnComment": "单据ID"
            },
            {
                "id": 22437,
                "columnName": "source_doc_line_id",
                "columnComment": "单据行ID"
            },
            {
                "id": 22438,
                "columnName": "material_stock_id",
                "columnComment": "库存记录ID"
            },
            {
                "id": 22439,
                "columnName": "transaction_flag",
                "columnComment": "库存方向"
            },
            {
                "id": 22440,
                "columnName": "transaction_quantity",
                "columnComment": "事务数量"
            },
            {
                "id": 22441,
                "columnName": "transaction_date",
                "columnComment": "事务日期"
            },
            {
                "id": 22442,
                "columnName": "related_transaction_id",
                "columnComment": "关联的事务ID"
            },
            {
                "id": 22443,
                "columnName": "erp_date",
                "columnComment": "ERP账期"
            },
            {
                "id": 22444,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22446,
                "columnName": "recpt_date",
                "columnComment": "接收日期"
            },
            {
                "id": 22447,
                "columnName": "expire_date",
                "columnComment": "库存有效期"
            }
        ]
    },
    {
        "id": 1259,
        "tableName": "wm_transfer",
        "tableComment": "转移单表",
        "columns": [
            {
                "id": 22456,
                "columnName": "transfer_id",
                "columnComment": "转移单ID"
            },
            {
                "id": 22458,
                "columnName": "transfer_name",
                "columnComment": "转移单名称"
            },
            {
                "id": 22459,
                "columnName": "transfer_type",
                "columnComment": "转移单类型"
            },
            {
                "id": 22460,
                "columnName": "destination",
                "columnComment": "目的地"
            },
            {
                "id": 22461,
                "columnName": "carrier",
                "columnComment": "承运商"
            },
            {
                "id": 22462,
                "columnName": "booking_note",
                "columnComment": "托运单号"
            },
            {
                "id": 22463,
                "columnName": "receiver",
                "columnComment": "收货人"
            },
            {
                "id": 22464,
                "columnName": "receiver_nick",
                "columnComment": "收货人名称"
            },
            {
                "id": 22465,
                "columnName": "from_warehouse_id",
                "columnComment": "移出仓库ID"
            },
            {
                "id": 22466,
                "columnName": "from_warehouse_code",
                "columnComment": "移出仓库编码"
            },
            {
                "id": 22467,
                "columnName": "from_warehouse_name",
                "columnComment": "移出仓库名称"
            },
            {
                "id": 22468,
                "columnName": "to_warehouse_id",
                "columnComment": "移入仓库ID"
            },
            {
                "id": 22469,
                "columnName": "to_warehouse_code",
                "columnComment": "移入仓库编码"
            },
            {
                "id": 22470,
                "columnName": "to_warehouse_name",
                "columnComment": "移入仓库名称"
            },
            {
                "id": 22471,
                "columnName": "transfer_date",
                "columnComment": "转移日期"
            }
        ]
    },
    {
        "id": 1260,
        "tableName": "wm_transfer_line",
        "tableComment": "转移单行表",
        "columns": [
            {
                "id": 22482,
                "columnName": "line_id",
                "columnComment": "明细行ID"
            },
            {
                "id": 22483,
                "columnName": "transfer_id",
                "columnComment": "装箱单ID"
            },
            {
                "id": 22484,
                "columnName": "material_stock_id",
                "columnComment": "库存记录ID"
            },
            {
                "id": 22485,
                "columnName": "item_id",
                "columnComment": "产品物料ID"
            },
            {
                "id": 22486,
                "columnName": "item_code",
                "columnComment": "产品物料编码"
            },
            {
                "id": 22487,
                "columnName": "item_name",
                "columnComment": "产品物料名称"
            },
            {
                "id": 22488,
                "columnName": "specification",
                "columnComment": "规格型号"
            },
            {
                "id": 22489,
                "columnName": "unit_of_measure",
                "columnComment": "单位"
            },
            {
                "id": 22490,
                "columnName": "quantity_transfer",
                "columnComment": "装箱数量"
            },
            {
                "id": 22491,
                "columnName": "workorder_id",
                "columnComment": "生产工单ID"
            },
            {
                "id": 22493,
                "columnName": "batch_code",
                "columnComment": "批次号"
            },
            {
                "id": 22494,
                "columnName": "from_warehouse_id",
                "columnComment": "移出仓库ID"
            },
            {
                "id": 22495,
                "columnName": "from_warehouse_code",
                "columnComment": "移出仓库编码"
            },
            {
                "id": 22496,
                "columnName": "from_warehouse_name",
                "columnComment": "移出仓库名称"
            },
            {
                "id": 22497,
                "columnName": "from_location_id",
                "columnComment": "移出库区ID"
            },
            {
                "id": 22498,
                "columnName": "from_location_code",
                "columnComment": "移出库区编码"
            },
            {
                "id": 22499,
                "columnName": "from_location_name",
                "columnComment": "移出库区名称"
            },
            {
                "id": 22500,
                "columnName": "from_area_id",
                "columnComment": "移出库位ID"
            },
            {
                "id": 22501,
                "columnName": "from_area_code",
                "columnComment": "移出库位编码"
            },
            {
                "id": 22502,
                "columnName": "from_area_name",
                "columnComment": "移出库位名称"
            },
            {
                "id": 22503,
                "columnName": "to_warehouse_id",
                "columnComment": "移入仓库ID"
            },
            {
                "id": 22504,
                "columnName": "to_warehouse_code",
                "columnComment": "移入仓库编码"
            },
            {
                "id": 22505,
                "columnName": "to_warehouse_name",
                "columnComment": "移入仓库名称"
            },
            {
                "id": 22506,
                "columnName": "to_location_id",
                "columnComment": "移入库区ID"
            },
            {
                "id": 22507,
                "columnName": "to_location_code",
                "columnComment": "移入库区编码"
            },
            {
                "id": 22508,
                "columnName": "to_location_name",
                "columnComment": "移入库区名称"
            },
            {
                "id": 22509,
                "columnName": "to_area_id",
                "columnComment": "移入库位ID"
            },
            {
                "id": 22510,
                "columnName": "to_area_code",
                "columnComment": "移入库位编码"
            },
            {
                "id": 22511,
                "columnName": "to_area_name",
                "columnComment": "移入库位名称"
            },
            {
                "id": 22512,
                "columnName": "expire_date",
                "columnComment": "有效期"
            },
            {
                "id": 22513,
                "columnName": "vendor_id",
                "columnComment": "供应商ID"
            },
            {
                "id": 22514,
                "columnName": "vendor_code",
                "columnComment": "供应商编码"
            },
            {
                "id": 22515,
                "columnName": "vendor_name",
                "columnComment": "供应商名称"
            },
            {
                "id": 22516,
                "columnName": "vendor_nick",
                "columnComment": "供应商简称"
            }
        ]
    },
    {
        "id": 1261,
        "tableName": "wm_warehouse",
        "tableComment": "仓库表",
        "columns": [
            {
                "id": 22526,
                "columnName": "warehouse_id",
                "columnComment": "仓库ID"
            },
            {
                "id": 22527,
                "columnName": "warehouse_code",
                "columnComment": "仓库编码"
            },
            {
                "id": 22528,
                "columnName": "warehouse_name",
                "columnComment": "仓库名称"
            },
            {
                "id": 22529,
                "columnName": "location",
                "columnComment": "位置"
            },
            {
                "id": 22530,
                "columnName": "area",
                "columnComment": "面积"
            },
            {
                "id": 22531,
                "columnName": "charge",
                "columnComment": "负责人"
            },
            {
                "id": 22532,
                "columnName": "enable_flag",
                "columnComment": "是否启用"
            }
        ]
    }
]

export const associations = [
    {
        "remark": "计划班组表-计划ID -> 排班计划表-计划ID",
        "id": 27340,
        "tableAssociationName": "cal_plan_team.plan_id to cal_plan.plan_id",
        "masterColumn": {
            "id": 20177
        },
        "slaveColumn": {
            "id": 20157
        }
    },
    {
        "remark": "计划班次表-计划ID -> 排班计划表-计划ID",
        "id": 27341,
        "tableAssociationName": "cal_shift.plan_id to cal_plan.plan_id",
        "masterColumn": {
            "id": 20191
        },
        "slaveColumn": {
            "id": 20157
        }
    },
    {
        "remark": "班组排班表-计划ID -> 排班计划表-计划ID",
        "id": 27342,
        "tableAssociationName": "cal_teamshift.plan_id to cal_plan.plan_id",
        "masterColumn": {
            "id": 20240
        },
        "slaveColumn": {
            "id": 20157
        }
    },
    {
        "remark": "点检设备表-计划ID -> 排班计划表-计划ID",
        "id": 27343,
        "tableAssociationName": "dv_check_machinery.plan_id to cal_plan.plan_id",
        "masterColumn": {
            "id": 20253
        },
        "slaveColumn": {
            "id": 20157
        }
    },
    {
        "remark": "设备点检保养计划头表-计划ID -> 排班计划表-计划ID",
        "id": 27344,
        "tableAssociationName": "dv_check_plan.plan_id to cal_plan.plan_id",
        "masterColumn": {
            "id": 20268
        },
        "slaveColumn": {
            "id": 20157
        }
    },
    {
        "remark": "点检项目表-计划ID -> 排班计划表-计划ID",
        "id": 27345,
        "tableAssociationName": "dv_check_subject.plan_id to cal_plan.plan_id",
        "masterColumn": {
            "id": 20287
        },
        "slaveColumn": {
            "id": 20157
        }
    },
    {
        "remark": "班组排班表-流水号 -> 计划班组表-流水号",
        "id": 27346,
        "tableAssociationName": "cal_teamshift.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "点检设备表-流水号 -> 计划班组表-流水号",
        "id": 27347,
        "tableAssociationName": "dv_check_machinery.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "点检项目表-流水号 -> 计划班组表-流水号",
        "id": 27348,
        "tableAssociationName": "dv_check_subject.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "设备资源表-记录ID -> 计划班组表-流水号",
        "id": 27349,
        "tableAssociationName": "md_workstation_machine.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 计划班组表-流水号",
        "id": 27350,
        "tableAssociationName": "md_workstation_tool.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "人力资源表-记录ID -> 计划班组表-流水号",
        "id": 27351,
        "tableAssociationName": "md_workstation_worker.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 计划班组表-流水号",
        "id": 27352,
        "tableAssociationName": "pro_feedback.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 计划班组表-流水号",
        "id": 27353,
        "tableAssociationName": "pro_route_process.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品制程-记录ID -> 计划班组表-流水号",
        "id": 27354,
        "tableAssociationName": "pro_route_product.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 计划班组表-流水号",
        "id": 27355,
        "tableAssociationName": "pro_route_product_bom.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 计划班组表-流水号",
        "id": 27356,
        "tableAssociationName": "pro_task_issue.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 计划班组表-流水号",
        "id": 27357,
        "tableAssociationName": "pro_trans_consume.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 计划班组表-流水号",
        "id": 27358,
        "tableAssociationName": "pro_user_workstation.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 计划班组表-流水号",
        "id": 27359,
        "tableAssociationName": "pro_workrecord.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 计划班组表-流水号",
        "id": 27360,
        "tableAssociationName": "qc_defect_record.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 计划班组表-流水号",
        "id": 27361,
        "tableAssociationName": "qc_template_index.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 计划班组表-流水号",
        "id": 27362,
        "tableAssociationName": "qc_template_product.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 计划班组表-流水号",
        "id": 27363,
        "tableAssociationName": "wm_item_consume.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 计划班组表-流水号",
        "id": 27364,
        "tableAssociationName": "wm_item_consume_line.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 计划班组表-流水号",
        "id": 27365,
        "tableAssociationName": "wm_product_produce.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 计划班组表-流水号",
        "id": 27366,
        "tableAssociationName": "wm_product_produce_line.record_id to cal_plan_team.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20176
        }
    },
    {
        "remark": "班组排班表-班次ID -> 计划班次表-班次ID",
        "id": 27367,
        "tableAssociationName": "cal_teamshift.shift_id to cal_shift.shift_id",
        "masterColumn": {
            "id": 20237
        },
        "slaveColumn": {
            "id": 20190
        }
    },
    {
        "remark": "计划班组表-班组ID -> 班组表-班组ID",
        "id": 27368,
        "tableAssociationName": "cal_plan_team.team_id to cal_team.team_id",
        "masterColumn": {
            "id": 20178
        },
        "slaveColumn": {
            "id": 20205
        }
    },
    {
        "remark": "班组成员表-班组ID -> 班组表-班组ID",
        "id": 27369,
        "tableAssociationName": "cal_team_member.team_id to cal_team.team_id",
        "masterColumn": {
            "id": 20219
        },
        "slaveColumn": {
            "id": 20205
        }
    },
    {
        "remark": "班组排班表-班组ID -> 班组表-班组ID",
        "id": 27370,
        "tableAssociationName": "cal_teamshift.team_id to cal_team.team_id",
        "masterColumn": {
            "id": 20235
        },
        "slaveColumn": {
            "id": 20205
        }
    },
    {
        "remark": "计划班组表-流水号 -> 班组排班表-流水号",
        "id": 27371,
        "tableAssociationName": "cal_plan_team.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "点检设备表-流水号 -> 班组排班表-流水号",
        "id": 27372,
        "tableAssociationName": "dv_check_machinery.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "点检项目表-流水号 -> 班组排班表-流水号",
        "id": 27373,
        "tableAssociationName": "dv_check_subject.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "设备资源表-记录ID -> 班组排班表-流水号",
        "id": 27374,
        "tableAssociationName": "md_workstation_machine.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 班组排班表-流水号",
        "id": 27375,
        "tableAssociationName": "md_workstation_tool.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "人力资源表-记录ID -> 班组排班表-流水号",
        "id": 27376,
        "tableAssociationName": "md_workstation_worker.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 班组排班表-流水号",
        "id": 27377,
        "tableAssociationName": "pro_feedback.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 班组排班表-流水号",
        "id": 27378,
        "tableAssociationName": "pro_route_process.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品制程-记录ID -> 班组排班表-流水号",
        "id": 27379,
        "tableAssociationName": "pro_route_product.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 班组排班表-流水号",
        "id": 27380,
        "tableAssociationName": "pro_route_product_bom.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 班组排班表-流水号",
        "id": 27381,
        "tableAssociationName": "pro_task_issue.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 班组排班表-流水号",
        "id": 27382,
        "tableAssociationName": "pro_trans_consume.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 班组排班表-流水号",
        "id": 27383,
        "tableAssociationName": "pro_user_workstation.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 班组排班表-流水号",
        "id": 27384,
        "tableAssociationName": "pro_workrecord.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 班组排班表-流水号",
        "id": 27385,
        "tableAssociationName": "qc_defect_record.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 班组排班表-流水号",
        "id": 27386,
        "tableAssociationName": "qc_template_index.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 班组排班表-流水号",
        "id": 27387,
        "tableAssociationName": "qc_template_product.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 班组排班表-流水号",
        "id": 27388,
        "tableAssociationName": "wm_item_consume.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 班组排班表-流水号",
        "id": 27389,
        "tableAssociationName": "wm_item_consume_line.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 班组排班表-流水号",
        "id": 27390,
        "tableAssociationName": "wm_product_produce.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 班组排班表-流水号",
        "id": 27391,
        "tableAssociationName": "wm_product_produce_line.record_id to cal_teamshift.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20233
        }
    },
    {
        "remark": "计划班组表-流水号 -> 点检设备表-流水号",
        "id": 27392,
        "tableAssociationName": "cal_plan_team.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "班组排班表-流水号 -> 点检设备表-流水号",
        "id": 27393,
        "tableAssociationName": "cal_teamshift.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "点检项目表-流水号 -> 点检设备表-流水号",
        "id": 27394,
        "tableAssociationName": "dv_check_subject.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "设备资源表-记录ID -> 点检设备表-流水号",
        "id": 27395,
        "tableAssociationName": "md_workstation_machine.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 点检设备表-流水号",
        "id": 27396,
        "tableAssociationName": "md_workstation_tool.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "人力资源表-记录ID -> 点检设备表-流水号",
        "id": 27397,
        "tableAssociationName": "md_workstation_worker.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 点检设备表-流水号",
        "id": 27398,
        "tableAssociationName": "pro_feedback.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 点检设备表-流水号",
        "id": 27399,
        "tableAssociationName": "pro_route_process.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "产品制程-记录ID -> 点检设备表-流水号",
        "id": 27400,
        "tableAssociationName": "pro_route_product.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 点检设备表-流水号",
        "id": 27401,
        "tableAssociationName": "pro_route_product_bom.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 点检设备表-流水号",
        "id": 27402,
        "tableAssociationName": "pro_task_issue.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 点检设备表-流水号",
        "id": 27403,
        "tableAssociationName": "pro_trans_consume.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 点检设备表-流水号",
        "id": 27404,
        "tableAssociationName": "pro_user_workstation.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 点检设备表-流水号",
        "id": 27405,
        "tableAssociationName": "pro_workrecord.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 点检设备表-流水号",
        "id": 27406,
        "tableAssociationName": "qc_defect_record.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 点检设备表-流水号",
        "id": 27407,
        "tableAssociationName": "qc_template_index.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 点检设备表-流水号",
        "id": 27408,
        "tableAssociationName": "qc_template_product.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 点检设备表-流水号",
        "id": 27409,
        "tableAssociationName": "wm_item_consume.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 点检设备表-流水号",
        "id": 27410,
        "tableAssociationName": "wm_item_consume_line.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 点检设备表-流水号",
        "id": 27411,
        "tableAssociationName": "wm_product_produce.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 点检设备表-流水号",
        "id": 27412,
        "tableAssociationName": "wm_product_produce_line.record_id to dv_check_machinery.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20252
        }
    },
    {
        "remark": "排班计划表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27413,
        "tableAssociationName": "cal_plan.plan_id to dv_check_plan.plan_id",
        "masterColumn": {
            "id": 20157
        },
        "slaveColumn": {
            "id": 20268
        }
    },
    {
        "remark": "计划班组表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27414,
        "tableAssociationName": "cal_plan_team.plan_id to dv_check_plan.plan_id",
        "masterColumn": {
            "id": 20177
        },
        "slaveColumn": {
            "id": 20268
        }
    },
    {
        "remark": "计划班次表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27415,
        "tableAssociationName": "cal_shift.plan_id to dv_check_plan.plan_id",
        "masterColumn": {
            "id": 20191
        },
        "slaveColumn": {
            "id": 20268
        }
    },
    {
        "remark": "班组排班表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27416,
        "tableAssociationName": "cal_teamshift.plan_id to dv_check_plan.plan_id",
        "masterColumn": {
            "id": 20240
        },
        "slaveColumn": {
            "id": 20268
        }
    },
    {
        "remark": "点检设备表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27417,
        "tableAssociationName": "dv_check_machinery.plan_id to dv_check_plan.plan_id",
        "masterColumn": {
            "id": 20253
        },
        "slaveColumn": {
            "id": 20268
        }
    },
    {
        "remark": "点检项目表-计划ID -> 设备点检保养计划头表-计划ID",
        "id": 27418,
        "tableAssociationName": "dv_check_subject.plan_id to dv_check_plan.plan_id",
        "masterColumn": {
            "id": 20287
        },
        "slaveColumn": {
            "id": 20268
        }
    },
    {
        "remark": "计划班组表-流水号 -> 点检项目表-流水号",
        "id": 27419,
        "tableAssociationName": "cal_plan_team.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "班组排班表-流水号 -> 点检项目表-流水号",
        "id": 27420,
        "tableAssociationName": "cal_teamshift.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "点检设备表-流水号 -> 点检项目表-流水号",
        "id": 27421,
        "tableAssociationName": "dv_check_machinery.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "设备资源表-记录ID -> 点检项目表-流水号",
        "id": 27422,
        "tableAssociationName": "md_workstation_machine.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 点检项目表-流水号",
        "id": 27423,
        "tableAssociationName": "md_workstation_tool.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "人力资源表-记录ID -> 点检项目表-流水号",
        "id": 27424,
        "tableAssociationName": "md_workstation_worker.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 点检项目表-流水号",
        "id": 27425,
        "tableAssociationName": "pro_feedback.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 点检项目表-流水号",
        "id": 27426,
        "tableAssociationName": "pro_route_process.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "产品制程-记录ID -> 点检项目表-流水号",
        "id": 27427,
        "tableAssociationName": "pro_route_product.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 点检项目表-流水号",
        "id": 27428,
        "tableAssociationName": "pro_route_product_bom.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 点检项目表-流水号",
        "id": 27429,
        "tableAssociationName": "pro_task_issue.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 点检项目表-流水号",
        "id": 27430,
        "tableAssociationName": "pro_trans_consume.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 点检项目表-流水号",
        "id": 27431,
        "tableAssociationName": "pro_user_workstation.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 点检项目表-流水号",
        "id": 27432,
        "tableAssociationName": "pro_workrecord.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 点检项目表-流水号",
        "id": 27433,
        "tableAssociationName": "qc_defect_record.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 点检项目表-流水号",
        "id": 27434,
        "tableAssociationName": "qc_template_index.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 点检项目表-流水号",
        "id": 27435,
        "tableAssociationName": "qc_template_product.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 点检项目表-流水号",
        "id": 27436,
        "tableAssociationName": "wm_item_consume.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 点检项目表-流水号",
        "id": 27437,
        "tableAssociationName": "wm_item_consume_line.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 点检项目表-流水号",
        "id": 27438,
        "tableAssociationName": "wm_product_produce.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 点检项目表-流水号",
        "id": 27439,
        "tableAssociationName": "wm_product_produce_line.record_id to dv_check_subject.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20286
        }
    },
    {
        "remark": "点检设备表-设备ID -> 设备表-设备类型ID",
        "id": 27440,
        "tableAssociationName": "dv_check_machinery.machinery_id to dv_machinery.machinery_id",
        "masterColumn": {
            "id": 20254
        },
        "slaveColumn": {
            "id": 20303
        }
    },
    {
        "remark": "设备维修单-设备ID -> 设备表-设备类型ID",
        "id": 27441,
        "tableAssociationName": "dv_repair.machinery_id to dv_machinery.machinery_id",
        "masterColumn": {
            "id": 20342
        },
        "slaveColumn": {
            "id": 20303
        }
    },
    {
        "remark": "设备资源表-设备ID -> 设备表-设备类型ID",
        "id": 27442,
        "tableAssociationName": "md_workstation_machine.machinery_id to dv_machinery.machinery_id",
        "masterColumn": {
            "id": 20639
        },
        "slaveColumn": {
            "id": 20303
        }
    },
    {
        "remark": "设备表-设备类型ID -> 设备类型表-设备类型ID",
        "id": 27443,
        "tableAssociationName": "dv_machinery.machinery_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20308
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "设备表-设备类型编码 -> 设备类型表-设备类型ID",
        "id": 27444,
        "tableAssociationName": "dv_machinery.machinery_type_code to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20309
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "设备表-设备类型名称 -> 设备类型表-设备类型ID",
        "id": 27445,
        "tableAssociationName": "dv_machinery.machinery_type_name to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20310
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "设备维修单-设备类型ID -> 设备类型表-设备类型ID",
        "id": 27446,
        "tableAssociationName": "dv_repair.machinery_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20347
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "物料产品表-物料类型ID -> 设备类型表-设备类型ID",
        "id": 27447,
        "tableAssociationName": "md_item.item_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20477
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "物料产品分类表-产品物料类型ID -> 设备类型表-设备类型ID",
        "id": 27448,
        "tableAssociationName": "md_item_type.item_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20493
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "物料产品分类表-父类型ID -> 设备类型表-设备类型ID",
        "id": 27449,
        "tableAssociationName": "md_item_type.parent_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20496
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "工装夹具资源表-工装夹具类型ID -> 设备类型表-设备类型ID",
        "id": 27450,
        "tableAssociationName": "md_workstation_tool.tool_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 20654
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "工装夹具清单表-工装夹具类型ID -> 设备类型表-设备类型ID",
        "id": 27451,
        "tableAssociationName": "tm_tool.tool_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 21687
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "工装夹具类型表-工装夹具类型ID -> 设备类型表-设备类型ID",
        "id": 27452,
        "tableAssociationName": "tm_tool_type.tool_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 21706
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "库存记录表-物料类型ID -> 设备类型表-设备类型ID",
        "id": 27453,
        "tableAssociationName": "wm_material_stock.item_type_id to dv_machinery_type.machinery_type_id",
        "masterColumn": {
            "id": 21934
        },
        "slaveColumn": {
            "id": 20324
        }
    },
    {
        "remark": "设备维修单行-维修单ID -> 设备维修单-维修单ID",
        "id": 27454,
        "tableAssociationName": "dv_repair_line.repair_id to dv_repair.repair_id",
        "masterColumn": {
            "id": 20365
        },
        "slaveColumn": {
            "id": 20339
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 设备维修单行-行ID",
        "id": 27455,
        "tableAssociationName": "pro_task_issue.source_line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 设备维修单行-行ID",
        "id": 27456,
        "tableAssociationName": "pro_trans_consume.source_line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 设备维修单行-行ID",
        "id": 27457,
        "tableAssociationName": "pro_workorder_bom.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 设备维修单行-行ID",
        "id": 27458,
        "tableAssociationName": "qc_defect_record.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 设备维修单行-行ID",
        "id": 27459,
        "tableAssociationName": "qc_ipqc_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 设备维修单行-行ID",
        "id": 27460,
        "tableAssociationName": "qc_iqc_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 设备维修单行-行ID",
        "id": 27461,
        "tableAssociationName": "qc_oqc_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 设备维修单行-行ID",
        "id": 27462,
        "tableAssociationName": "wm_issue_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 设备维修单行-行ID",
        "id": 27463,
        "tableAssociationName": "wm_item_consume_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 设备维修单行-行ID",
        "id": 27464,
        "tableAssociationName": "wm_item_recpt_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 设备维修单行-行ID",
        "id": 27465,
        "tableAssociationName": "wm_package_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 设备维修单行-行ID",
        "id": 27466,
        "tableAssociationName": "wm_product_produce_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 设备维修单行-行ID",
        "id": 27467,
        "tableAssociationName": "wm_product_recpt_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 设备维修单行-行ID",
        "id": 27468,
        "tableAssociationName": "wm_product_salse_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 设备维修单行-行ID",
        "id": 27469,
        "tableAssociationName": "wm_rt_issue_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 设备维修单行-行ID",
        "id": 27470,
        "tableAssociationName": "wm_rt_salse_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 设备维修单行-行ID",
        "id": 27471,
        "tableAssociationName": "wm_rt_vendor_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 设备维修单行-行ID",
        "id": 27472,
        "tableAssociationName": "wm_transaction.source_doc_line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 设备维修单行-行ID",
        "id": 27473,
        "tableAssociationName": "wm_transfer_line.line_id to dv_repair_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 20364
        }
    },
    {
        "remark": "点检项目表-项目ID -> 设备点检保养项目表-项目ID",
        "id": 27474,
        "tableAssociationName": "dv_check_subject.subject_id to dv_subject.subject_id",
        "masterColumn": {
            "id": 20288
        },
        "slaveColumn": {
            "id": 20384
        }
    },
    {
        "remark": "设备维修单行-项目ID -> 设备点检保养项目表-项目ID",
        "id": 27475,
        "tableAssociationName": "dv_repair_line.subject_id to dv_subject.subject_id",
        "masterColumn": {
            "id": 20366
        },
        "slaveColumn": {
            "id": 20384
        }
    },
    {
        "remark": "生产任务表-客户ID -> 客户表-客户ID",
        "id": 27476,
        "tableAssociationName": "pro_task.client_id to md_client.client_id",
        "masterColumn": {
            "id": 20847
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "生产工单表-客户ID -> 客户表-客户ID",
        "id": 27477,
        "tableAssociationName": "pro_workorder.client_id to md_client.client_id",
        "masterColumn": {
            "id": 20982
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "出货检验单表-客户ID -> 客户表-客户ID",
        "id": 27478,
        "tableAssociationName": "qc_oqc.client_id to md_client.client_id",
        "masterColumn": {
            "id": 21214
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "生产领料单头表-客户ID -> 客户表-客户ID",
        "id": 27479,
        "tableAssociationName": "wm_issue_header.client_id to md_client.client_id",
        "masterColumn": {
            "id": 21771
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "装箱单表-客户ID -> 客户表-客户ID",
        "id": 27480,
        "tableAssociationName": "wm_package.client_id to md_client.client_id",
        "masterColumn": {
            "id": 21977
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "销售出库单表-客户ID -> 客户表-客户ID",
        "id": 27481,
        "tableAssociationName": "wm_product_salse.client_id to md_client.client_id",
        "masterColumn": {
            "id": 22152
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "产品销售退货单表-客户ID -> 客户表-客户ID",
        "id": 27482,
        "tableAssociationName": "wm_rt_salse.client_id to md_client.client_id",
        "masterColumn": {
            "id": 22261
        },
        "slaveColumn": {
            "id": 20442
        }
    },
    {
        "remark": "产品BOM关系表-物料产品ID -> 物料产品表-产品物料ID",
        "id": 27483,
        "tableAssociationName": "md_product_bom.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20511
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品BOM关系表-BOM物料ID -> 物料产品表-产品物料ID",
        "id": 27484,
        "tableAssociationName": "md_product_bom.bom_item_id to md_item.item_id",
        "masterColumn": {
            "id": 20512
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品SOP表-物料产品ID -> 物料产品表-产品物料ID",
        "id": 27485,
        "tableAssociationName": "md_product_sop.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20530
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "生产报工记录表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27486,
        "tableAssociationName": "pro_feedback.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20695
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品制程-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27487,
        "tableAssociationName": "pro_route_product.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20789
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品制程物料BOM表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27488,
        "tableAssociationName": "pro_route_product_bom.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20810
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "生产任务表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27489,
        "tableAssociationName": "pro_task.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20837
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "生产任务投料表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27490,
        "tableAssociationName": "pro_task_issue.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20874
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "物料消耗记录表-被消耗产品物料ID -> 物料产品表-产品物料ID",
        "id": 27491,
        "tableAssociationName": "pro_trans_consume.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20904
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "流转单表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27492,
        "tableAssociationName": "pro_trans_order.item_id to md_item.item_id",
        "masterColumn": {
            "id": 20934
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "生产工单BOM组成表-BOM物料ID -> 物料产品表-产品物料ID",
        "id": 27493,
        "tableAssociationName": "pro_workorder_bom.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21001
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "过程检验单表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27494,
        "tableAssociationName": "qc_ipqc.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21096
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "来料检验单表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27495,
        "tableAssociationName": "qc_iqc.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21156
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "出货检验单表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27496,
        "tableAssociationName": "qc_oqc.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21218
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "检测模板-产品表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27497,
        "tableAssociationName": "qc_template_product.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21311
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "生产领料单行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27498,
        "tableAssociationName": "wm_issue_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21798
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "物料消耗记录行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27499,
        "tableAssociationName": "wm_item_consume_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21850
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "物料入库单行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27500,
        "tableAssociationName": "wm_item_recpt_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21907
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "库存记录表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27501,
        "tableAssociationName": "wm_material_stock.item_id to md_item.item_id",
        "masterColumn": {
            "id": 21935
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "装箱明细表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27502,
        "tableAssociationName": "wm_package_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22004
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品产出记录表行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27503,
        "tableAssociationName": "wm_product_produce_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22058
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品入库录表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27504,
        "tableAssociationName": "wm_product_recpt.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22089
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品入库记录表行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27505,
        "tableAssociationName": "wm_product_recpt_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22117
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品销售出库行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27506,
        "tableAssociationName": "wm_product_salse_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22179
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "生产退料单行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27507,
        "tableAssociationName": "wm_rt_issue_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22232
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "产品销售退货行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27508,
        "tableAssociationName": "wm_rt_salse_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22288
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "供应商退货行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27509,
        "tableAssociationName": "wm_rt_vendor_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22337
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "SN码表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27510,
        "tableAssociationName": "wm_sn.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22364
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "库存事务表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27511,
        "tableAssociationName": "wm_transaction.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22415
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "转移单行表-产品物料ID -> 物料产品表-产品物料ID",
        "id": 27512,
        "tableAssociationName": "wm_transfer_line.item_id to md_item.item_id",
        "masterColumn": {
            "id": 22485
        },
        "slaveColumn": {
            "id": 20471
        }
    },
    {
        "remark": "设备表-设备类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27513,
        "tableAssociationName": "dv_machinery.machinery_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20308
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "设备类型表-设备类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27514,
        "tableAssociationName": "dv_machinery_type.machinery_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20324
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "设备类型表-父类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27515,
        "tableAssociationName": "dv_machinery_type.parent_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20327
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "设备维修单-设备类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27516,
        "tableAssociationName": "dv_repair.machinery_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20347
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "物料产品表-物料类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27517,
        "tableAssociationName": "md_item.item_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20477
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "物料产品表-物料类型编码 -> 物料产品分类表-产品物料类型ID",
        "id": 27518,
        "tableAssociationName": "md_item.item_type_code to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20478
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "物料产品表-物料类型名称 -> 物料产品分类表-产品物料类型ID",
        "id": 27519,
        "tableAssociationName": "md_item.item_type_name to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20479
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "工装夹具资源表-工装夹具类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27520,
        "tableAssociationName": "md_workstation_tool.tool_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 20654
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "工装夹具清单表-工装夹具类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27521,
        "tableAssociationName": "tm_tool.tool_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 21687
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "工装夹具类型表-工装夹具类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27522,
        "tableAssociationName": "tm_tool_type.tool_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 21706
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "库存记录表-物料类型ID -> 物料产品分类表-产品物料类型ID",
        "id": 27523,
        "tableAssociationName": "wm_material_stock.item_type_id to md_item_type.item_type_id",
        "masterColumn": {
            "id": 21934
        },
        "slaveColumn": {
            "id": 20493
        }
    },
    {
        "remark": "来料检验单表-供应商ID -> 供应商表-供应商ID",
        "id": 27524,
        "tableAssociationName": "qc_iqc.vendor_id to md_vendor.vendor_id",
        "masterColumn": {
            "id": 21151
        },
        "slaveColumn": {
            "id": 20563
        }
    },
    {
        "remark": "物料入库单表-供应商ID -> 供应商表-供应商ID",
        "id": 27525,
        "tableAssociationName": "wm_item_recpt.vendor_id to md_vendor.vendor_id",
        "masterColumn": {
            "id": 21881
        },
        "slaveColumn": {
            "id": 20563
        }
    },
    {
        "remark": "库存记录表-供应商ID -> 供应商表-供应商ID",
        "id": 27526,
        "tableAssociationName": "wm_material_stock.vendor_id to md_vendor.vendor_id",
        "masterColumn": {
            "id": 21950
        },
        "slaveColumn": {
            "id": 20563
        }
    },
    {
        "remark": "供应商退货表-供应商ID -> 供应商表-供应商ID",
        "id": 27527,
        "tableAssociationName": "wm_rt_vendor.vendor_id to md_vendor.vendor_id",
        "masterColumn": {
            "id": 22318
        },
        "slaveColumn": {
            "id": 20563
        }
    },
    {
        "remark": "库存事务表-供应商ID -> 供应商表-供应商ID",
        "id": 27528,
        "tableAssociationName": "wm_transaction.vendor_id to md_vendor.vendor_id",
        "masterColumn": {
            "id": 22430
        },
        "slaveColumn": {
            "id": 20563
        }
    },
    {
        "remark": "转移单行表-供应商ID -> 供应商表-供应商ID",
        "id": 27529,
        "tableAssociationName": "wm_transfer_line.vendor_id to md_vendor.vendor_id",
        "masterColumn": {
            "id": 22513
        },
        "slaveColumn": {
            "id": 20563
        }
    },
    {
        "remark": "设备表-所属车间ID -> 车间表-车间ID",
        "id": 27530,
        "tableAssociationName": "dv_machinery.workshop_id to md_workshop.workshop_id",
        "masterColumn": {
            "id": 20311
        },
        "slaveColumn": {
            "id": 20593
        }
    },
    {
        "remark": "工作站表-所在车间ID -> 车间表-车间ID",
        "id": 27531,
        "tableAssociationName": "md_workstation.workshop_id to md_workshop.workshop_id",
        "masterColumn": {
            "id": 20612
        },
        "slaveColumn": {
            "id": 20593
        }
    },
    {
        "remark": "设备资源表-工作站ID -> 工作站表-工作站ID",
        "id": 27532,
        "tableAssociationName": "md_workstation_machine.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20638
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "工装夹具资源表-工作站ID -> 工作站表-工作站ID",
        "id": 27533,
        "tableAssociationName": "md_workstation_tool.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20653
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "人力资源表-工作站ID -> 工作站表-工作站ID",
        "id": 27534,
        "tableAssociationName": "md_workstation_worker.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20668
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "生产报工记录表-工作站ID -> 工作站表-工作站ID",
        "id": 27535,
        "tableAssociationName": "pro_feedback.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20684
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "生产任务表-工作站ID -> 工作站表-工作站ID",
        "id": 27536,
        "tableAssociationName": "pro_task.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20831
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "生产任务投料表-工作站ID -> 工作站表-工作站ID",
        "id": 27537,
        "tableAssociationName": "pro_task_issue.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20869
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "物料消耗记录表-工作站ID -> 工作站表-工作站ID",
        "id": 27538,
        "tableAssociationName": "pro_trans_consume.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20895
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "流转单表-工作站ID -> 工作站表-工作站ID",
        "id": 27539,
        "tableAssociationName": "pro_trans_order.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20924
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "用户工作站绑定关系-工作站ID -> 工作站表-工作站ID",
        "id": 27540,
        "tableAssociationName": "pro_user_workstation.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 20955
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "上下工记录表-工作站ID -> 工作站表-工作站ID",
        "id": 27541,
        "tableAssociationName": "pro_workrecord.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 21021
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "过程检验单表-工作站ID -> 工作站表-工作站ID",
        "id": 27542,
        "tableAssociationName": "qc_ipqc.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 21090
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "生产领料单头表-工作站ID -> 工作站表-工作站ID",
        "id": 27543,
        "tableAssociationName": "wm_issue_header.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 21764
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "物料消耗记录表-工作站ID -> 工作站表-工作站ID",
        "id": 27544,
        "tableAssociationName": "wm_item_consume.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 21830
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "产品产出记录表-工作站ID -> 工作站表-工作站ID",
        "id": 27545,
        "tableAssociationName": "wm_product_produce.workstation_id to md_workstation.workstation_id",
        "masterColumn": {
            "id": 22039
        },
        "slaveColumn": {
            "id": 20608
        }
    },
    {
        "remark": "计划班组表-流水号 -> 设备资源表-记录ID",
        "id": 27546,
        "tableAssociationName": "cal_plan_team.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "班组排班表-流水号 -> 设备资源表-记录ID",
        "id": 27547,
        "tableAssociationName": "cal_teamshift.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "点检设备表-流水号 -> 设备资源表-记录ID",
        "id": 27548,
        "tableAssociationName": "dv_check_machinery.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "点检项目表-流水号 -> 设备资源表-记录ID",
        "id": 27549,
        "tableAssociationName": "dv_check_subject.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 设备资源表-记录ID",
        "id": 27550,
        "tableAssociationName": "md_workstation_tool.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "人力资源表-记录ID -> 设备资源表-记录ID",
        "id": 27551,
        "tableAssociationName": "md_workstation_worker.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 设备资源表-记录ID",
        "id": 27552,
        "tableAssociationName": "pro_feedback.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 设备资源表-记录ID",
        "id": 27553,
        "tableAssociationName": "pro_route_process.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "产品制程-记录ID -> 设备资源表-记录ID",
        "id": 27554,
        "tableAssociationName": "pro_route_product.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 设备资源表-记录ID",
        "id": 27555,
        "tableAssociationName": "pro_route_product_bom.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 设备资源表-记录ID",
        "id": 27556,
        "tableAssociationName": "pro_task_issue.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 设备资源表-记录ID",
        "id": 27557,
        "tableAssociationName": "pro_trans_consume.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 设备资源表-记录ID",
        "id": 27558,
        "tableAssociationName": "pro_user_workstation.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 设备资源表-记录ID",
        "id": 27559,
        "tableAssociationName": "pro_workrecord.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 设备资源表-记录ID",
        "id": 27560,
        "tableAssociationName": "qc_defect_record.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 设备资源表-记录ID",
        "id": 27561,
        "tableAssociationName": "qc_template_index.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 设备资源表-记录ID",
        "id": 27562,
        "tableAssociationName": "qc_template_product.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 设备资源表-记录ID",
        "id": 27563,
        "tableAssociationName": "wm_item_consume.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 设备资源表-记录ID",
        "id": 27564,
        "tableAssociationName": "wm_item_consume_line.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 设备资源表-记录ID",
        "id": 27565,
        "tableAssociationName": "wm_product_produce.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 设备资源表-记录ID",
        "id": 27566,
        "tableAssociationName": "wm_product_produce_line.record_id to md_workstation_machine.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20637
        }
    },
    {
        "remark": "计划班组表-流水号 -> 工装夹具资源表-记录ID",
        "id": 27567,
        "tableAssociationName": "cal_plan_team.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "班组排班表-流水号 -> 工装夹具资源表-记录ID",
        "id": 27568,
        "tableAssociationName": "cal_teamshift.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "点检设备表-流水号 -> 工装夹具资源表-记录ID",
        "id": 27569,
        "tableAssociationName": "dv_check_machinery.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "点检项目表-流水号 -> 工装夹具资源表-记录ID",
        "id": 27570,
        "tableAssociationName": "dv_check_subject.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "设备资源表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27571,
        "tableAssociationName": "md_workstation_machine.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "人力资源表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27572,
        "tableAssociationName": "md_workstation_worker.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27573,
        "tableAssociationName": "pro_feedback.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27574,
        "tableAssociationName": "pro_route_process.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "产品制程-记录ID -> 工装夹具资源表-记录ID",
        "id": 27575,
        "tableAssociationName": "pro_route_product.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27576,
        "tableAssociationName": "pro_route_product_bom.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27577,
        "tableAssociationName": "pro_task_issue.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27578,
        "tableAssociationName": "pro_trans_consume.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 工装夹具资源表-记录ID",
        "id": 27579,
        "tableAssociationName": "pro_user_workstation.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27580,
        "tableAssociationName": "pro_workrecord.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 工装夹具资源表-记录ID",
        "id": 27581,
        "tableAssociationName": "qc_defect_record.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27582,
        "tableAssociationName": "qc_template_index.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27583,
        "tableAssociationName": "qc_template_product.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 工装夹具资源表-记录ID",
        "id": 27584,
        "tableAssociationName": "wm_item_consume.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 工装夹具资源表-记录ID",
        "id": 27585,
        "tableAssociationName": "wm_item_consume_line.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 工装夹具资源表-记录ID",
        "id": 27586,
        "tableAssociationName": "wm_product_produce.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 工装夹具资源表-记录ID",
        "id": 27587,
        "tableAssociationName": "wm_product_produce_line.record_id to md_workstation_tool.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20652
        }
    },
    {
        "remark": "计划班组表-流水号 -> 人力资源表-记录ID",
        "id": 27588,
        "tableAssociationName": "cal_plan_team.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "班组排班表-流水号 -> 人力资源表-记录ID",
        "id": 27589,
        "tableAssociationName": "cal_teamshift.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "点检设备表-流水号 -> 人力资源表-记录ID",
        "id": 27590,
        "tableAssociationName": "dv_check_machinery.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "点检项目表-流水号 -> 人力资源表-记录ID",
        "id": 27591,
        "tableAssociationName": "dv_check_subject.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "设备资源表-记录ID -> 人力资源表-记录ID",
        "id": 27592,
        "tableAssociationName": "md_workstation_machine.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 人力资源表-记录ID",
        "id": 27593,
        "tableAssociationName": "md_workstation_tool.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 人力资源表-记录ID",
        "id": 27594,
        "tableAssociationName": "pro_feedback.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 人力资源表-记录ID",
        "id": 27595,
        "tableAssociationName": "pro_route_process.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "产品制程-记录ID -> 人力资源表-记录ID",
        "id": 27596,
        "tableAssociationName": "pro_route_product.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 人力资源表-记录ID",
        "id": 27597,
        "tableAssociationName": "pro_route_product_bom.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 人力资源表-记录ID",
        "id": 27598,
        "tableAssociationName": "pro_task_issue.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 人力资源表-记录ID",
        "id": 27599,
        "tableAssociationName": "pro_trans_consume.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 人力资源表-记录ID",
        "id": 27600,
        "tableAssociationName": "pro_user_workstation.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 人力资源表-记录ID",
        "id": 27601,
        "tableAssociationName": "pro_workrecord.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 人力资源表-记录ID",
        "id": 27602,
        "tableAssociationName": "qc_defect_record.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 人力资源表-记录ID",
        "id": 27603,
        "tableAssociationName": "qc_template_index.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 人力资源表-记录ID",
        "id": 27604,
        "tableAssociationName": "qc_template_product.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 人力资源表-记录ID",
        "id": 27605,
        "tableAssociationName": "wm_item_consume.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 人力资源表-记录ID",
        "id": 27606,
        "tableAssociationName": "wm_item_consume_line.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 人力资源表-记录ID",
        "id": 27607,
        "tableAssociationName": "wm_product_produce.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 人力资源表-记录ID",
        "id": 27608,
        "tableAssociationName": "wm_product_produce_line.record_id to md_workstation_worker.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20667
        }
    },
    {
        "remark": "计划班组表-流水号 -> 生产报工记录表-记录ID",
        "id": 27609,
        "tableAssociationName": "cal_plan_team.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "班组排班表-流水号 -> 生产报工记录表-记录ID",
        "id": 27610,
        "tableAssociationName": "cal_teamshift.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "点检设备表-流水号 -> 生产报工记录表-记录ID",
        "id": 27611,
        "tableAssociationName": "dv_check_machinery.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "点检项目表-流水号 -> 生产报工记录表-记录ID",
        "id": 27612,
        "tableAssociationName": "dv_check_subject.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "设备资源表-记录ID -> 生产报工记录表-记录ID",
        "id": 27613,
        "tableAssociationName": "md_workstation_machine.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 生产报工记录表-记录ID",
        "id": 27614,
        "tableAssociationName": "md_workstation_tool.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "人力资源表-记录ID -> 生产报工记录表-记录ID",
        "id": 27615,
        "tableAssociationName": "md_workstation_worker.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 生产报工记录表-记录ID",
        "id": 27616,
        "tableAssociationName": "pro_route_process.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "产品制程-记录ID -> 生产报工记录表-记录ID",
        "id": 27617,
        "tableAssociationName": "pro_route_product.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 生产报工记录表-记录ID",
        "id": 27618,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 生产报工记录表-记录ID",
        "id": 27619,
        "tableAssociationName": "pro_task_issue.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 生产报工记录表-记录ID",
        "id": 27620,
        "tableAssociationName": "pro_trans_consume.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 生产报工记录表-记录ID",
        "id": 27621,
        "tableAssociationName": "pro_user_workstation.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 生产报工记录表-记录ID",
        "id": 27622,
        "tableAssociationName": "pro_workrecord.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 生产报工记录表-记录ID",
        "id": 27623,
        "tableAssociationName": "qc_defect_record.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 生产报工记录表-记录ID",
        "id": 27624,
        "tableAssociationName": "qc_template_index.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 生产报工记录表-记录ID",
        "id": 27625,
        "tableAssociationName": "qc_template_product.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 生产报工记录表-记录ID",
        "id": 27626,
        "tableAssociationName": "wm_item_consume.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 生产报工记录表-记录ID",
        "id": 27627,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 生产报工记录表-记录ID",
        "id": 27628,
        "tableAssociationName": "wm_product_produce.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 生产报工记录表-记录ID",
        "id": 27629,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_feedback.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20682
        }
    },
    {
        "remark": "产品SOP表-对应的工序 -> 生产工序表-工序ID",
        "id": 27630,
        "tableAssociationName": "md_product_sop.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20532
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "工作站表-工序ID -> 生产工序表-工序ID",
        "id": 27631,
        "tableAssociationName": "md_workstation.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20615
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "生产报工记录表-工序ID -> 生产工序表-工序ID",
        "id": 27632,
        "tableAssociationName": "pro_feedback.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20690
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "生产工序内容表-工序ID -> 生产工序表-工序ID",
        "id": 27633,
        "tableAssociationName": "pro_process_content.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20735
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "工艺组成表-工序ID -> 生产工序表-工序ID",
        "id": 27634,
        "tableAssociationName": "pro_route_process.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20766
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "工艺组成表-工序ID -> 生产工序表-工序ID",
        "id": 27635,
        "tableAssociationName": "pro_route_process.next_process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20770
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "产品制程物料BOM表-工序ID -> 生产工序表-工序ID",
        "id": 27636,
        "tableAssociationName": "pro_route_product_bom.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20808
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "生产任务表-工序ID -> 生产工序表-工序ID",
        "id": 27637,
        "tableAssociationName": "pro_task.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20834
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "物料消耗记录表-工序ID -> 生产工序表-工序ID",
        "id": 27638,
        "tableAssociationName": "pro_trans_consume.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20896
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "流转单表-工序ID -> 生产工序表-工序ID",
        "id": 27639,
        "tableAssociationName": "pro_trans_order.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 20927
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "过程检验单表-工序ID -> 生产工序表-工序ID",
        "id": 27640,
        "tableAssociationName": "qc_ipqc.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 21093
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "物料消耗记录表-工序ID -> 生产工序表-工序ID",
        "id": 27641,
        "tableAssociationName": "wm_item_consume.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 21833
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "产品产出记录表-工序ID -> 生产工序表-工序ID",
        "id": 27642,
        "tableAssociationName": "wm_product_produce.process_id to pro_process.process_id",
        "masterColumn": {
            "id": 22042
        },
        "slaveColumn": {
            "id": 20720
        }
    },
    {
        "remark": "工艺组成表-工艺路线ID -> 工艺路线表-工艺路线ID",
        "id": 27643,
        "tableAssociationName": "pro_route_process.route_id to pro_route.route_id",
        "masterColumn": {
            "id": 20765
        },
        "slaveColumn": {
            "id": 20750
        }
    },
    {
        "remark": "产品制程-工艺路线ID -> 工艺路线表-工艺路线ID",
        "id": 27644,
        "tableAssociationName": "pro_route_product.route_id to pro_route.route_id",
        "masterColumn": {
            "id": 20788
        },
        "slaveColumn": {
            "id": 20750
        }
    },
    {
        "remark": "产品制程物料BOM表-工艺路线ID -> 工艺路线表-工艺路线ID",
        "id": 27645,
        "tableAssociationName": "pro_route_product_bom.route_id to pro_route.route_id",
        "masterColumn": {
            "id": 20807
        },
        "slaveColumn": {
            "id": 20750
        }
    },
    {
        "remark": "计划班组表-流水号 -> 工艺组成表-记录ID",
        "id": 27646,
        "tableAssociationName": "cal_plan_team.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "班组排班表-流水号 -> 工艺组成表-记录ID",
        "id": 27647,
        "tableAssociationName": "cal_teamshift.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "点检设备表-流水号 -> 工艺组成表-记录ID",
        "id": 27648,
        "tableAssociationName": "dv_check_machinery.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "点检项目表-流水号 -> 工艺组成表-记录ID",
        "id": 27649,
        "tableAssociationName": "dv_check_subject.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "设备资源表-记录ID -> 工艺组成表-记录ID",
        "id": 27650,
        "tableAssociationName": "md_workstation_machine.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 工艺组成表-记录ID",
        "id": 27651,
        "tableAssociationName": "md_workstation_tool.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "人力资源表-记录ID -> 工艺组成表-记录ID",
        "id": 27652,
        "tableAssociationName": "md_workstation_worker.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 工艺组成表-记录ID",
        "id": 27653,
        "tableAssociationName": "pro_feedback.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "产品制程-记录ID -> 工艺组成表-记录ID",
        "id": 27654,
        "tableAssociationName": "pro_route_product.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 工艺组成表-记录ID",
        "id": 27655,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 工艺组成表-记录ID",
        "id": 27656,
        "tableAssociationName": "pro_task_issue.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 工艺组成表-记录ID",
        "id": 27657,
        "tableAssociationName": "pro_trans_consume.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 工艺组成表-记录ID",
        "id": 27658,
        "tableAssociationName": "pro_user_workstation.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 工艺组成表-记录ID",
        "id": 27659,
        "tableAssociationName": "pro_workrecord.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 工艺组成表-记录ID",
        "id": 27660,
        "tableAssociationName": "qc_defect_record.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 工艺组成表-记录ID",
        "id": 27661,
        "tableAssociationName": "qc_template_index.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 工艺组成表-记录ID",
        "id": 27662,
        "tableAssociationName": "qc_template_product.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 工艺组成表-记录ID",
        "id": 27663,
        "tableAssociationName": "wm_item_consume.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 工艺组成表-记录ID",
        "id": 27664,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 工艺组成表-记录ID",
        "id": 27665,
        "tableAssociationName": "wm_product_produce.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 工艺组成表-记录ID",
        "id": 27666,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_route_process.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20764
        }
    },
    {
        "remark": "计划班组表-流水号 -> 产品制程-记录ID",
        "id": 27667,
        "tableAssociationName": "cal_plan_team.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "班组排班表-流水号 -> 产品制程-记录ID",
        "id": 27668,
        "tableAssociationName": "cal_teamshift.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "点检设备表-流水号 -> 产品制程-记录ID",
        "id": 27669,
        "tableAssociationName": "dv_check_machinery.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "点检项目表-流水号 -> 产品制程-记录ID",
        "id": 27670,
        "tableAssociationName": "dv_check_subject.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "设备资源表-记录ID -> 产品制程-记录ID",
        "id": 27671,
        "tableAssociationName": "md_workstation_machine.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 产品制程-记录ID",
        "id": 27672,
        "tableAssociationName": "md_workstation_tool.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "人力资源表-记录ID -> 产品制程-记录ID",
        "id": 27673,
        "tableAssociationName": "md_workstation_worker.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 产品制程-记录ID",
        "id": 27674,
        "tableAssociationName": "pro_feedback.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 产品制程-记录ID",
        "id": 27675,
        "tableAssociationName": "pro_route_process.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 产品制程-记录ID",
        "id": 27676,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 产品制程-记录ID",
        "id": 27677,
        "tableAssociationName": "pro_task_issue.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 产品制程-记录ID",
        "id": 27678,
        "tableAssociationName": "pro_trans_consume.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 产品制程-记录ID",
        "id": 27679,
        "tableAssociationName": "pro_user_workstation.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 产品制程-记录ID",
        "id": 27680,
        "tableAssociationName": "pro_workrecord.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 产品制程-记录ID",
        "id": 27681,
        "tableAssociationName": "qc_defect_record.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 产品制程-记录ID",
        "id": 27682,
        "tableAssociationName": "qc_template_index.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 产品制程-记录ID",
        "id": 27683,
        "tableAssociationName": "qc_template_product.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 产品制程-记录ID",
        "id": 27684,
        "tableAssociationName": "wm_item_consume.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 产品制程-记录ID",
        "id": 27685,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 产品制程-记录ID",
        "id": 27686,
        "tableAssociationName": "wm_product_produce.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 产品制程-记录ID",
        "id": 27687,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_route_product.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20787
        }
    },
    {
        "remark": "计划班组表-流水号 -> 产品制程物料BOM表-记录ID",
        "id": 27688,
        "tableAssociationName": "cal_plan_team.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "班组排班表-流水号 -> 产品制程物料BOM表-记录ID",
        "id": 27689,
        "tableAssociationName": "cal_teamshift.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "点检设备表-流水号 -> 产品制程物料BOM表-记录ID",
        "id": 27690,
        "tableAssociationName": "dv_check_machinery.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "点检项目表-流水号 -> 产品制程物料BOM表-记录ID",
        "id": 27691,
        "tableAssociationName": "dv_check_subject.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "设备资源表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27692,
        "tableAssociationName": "md_workstation_machine.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27693,
        "tableAssociationName": "md_workstation_tool.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "人力资源表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27694,
        "tableAssociationName": "md_workstation_worker.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27695,
        "tableAssociationName": "pro_feedback.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27696,
        "tableAssociationName": "pro_route_process.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "产品制程-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27697,
        "tableAssociationName": "pro_route_product.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27698,
        "tableAssociationName": "pro_task_issue.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27699,
        "tableAssociationName": "pro_trans_consume.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27700,
        "tableAssociationName": "pro_user_workstation.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27701,
        "tableAssociationName": "pro_workrecord.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 产品制程物料BOM表-记录ID",
        "id": 27702,
        "tableAssociationName": "qc_defect_record.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27703,
        "tableAssociationName": "qc_template_index.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27704,
        "tableAssociationName": "qc_template_product.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27705,
        "tableAssociationName": "wm_item_consume.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27706,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 产品制程物料BOM表-记录ID",
        "id": 27707,
        "tableAssociationName": "wm_product_produce.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 产品制程物料BOM表-记录ID",
        "id": 27708,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_route_product_bom.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20806
        }
    },
    {
        "remark": "生产报工记录表-生产任务ID -> 生产任务表-任务ID",
        "id": 27709,
        "tableAssociationName": "pro_feedback.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 20693
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "生产任务投料表-生产任务ID -> 生产任务表-任务ID",
        "id": 27710,
        "tableAssociationName": "pro_task_issue.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 20867
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "物料消耗记录表-生产任务ID -> 生产任务表-任务ID",
        "id": 27711,
        "tableAssociationName": "pro_trans_consume.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 20894
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "流转单表-生产任务ID -> 生产任务表-任务ID",
        "id": 27712,
        "tableAssociationName": "pro_trans_order.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 20922
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "过程检验单表-任务ID -> 生产任务表-任务ID",
        "id": 27713,
        "tableAssociationName": "qc_ipqc.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 21087
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "生产领料单头表-生产任务ID -> 生产任务表-任务ID",
        "id": 27714,
        "tableAssociationName": "wm_issue_header.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 21769
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "物料消耗记录表-生产任务ID -> 生产任务表-任务ID",
        "id": 27715,
        "tableAssociationName": "wm_item_consume.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 21827
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "产品产出记录表-生产任务ID -> 生产任务表-任务ID",
        "id": 27716,
        "tableAssociationName": "wm_product_produce.task_id to pro_task.task_id",
        "masterColumn": {
            "id": 22036
        },
        "slaveColumn": {
            "id": 20825
        }
    },
    {
        "remark": "计划班组表-流水号 -> 生产任务投料表-记录ID",
        "id": 27717,
        "tableAssociationName": "cal_plan_team.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "班组排班表-流水号 -> 生产任务投料表-记录ID",
        "id": 27718,
        "tableAssociationName": "cal_teamshift.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "点检设备表-流水号 -> 生产任务投料表-记录ID",
        "id": 27719,
        "tableAssociationName": "dv_check_machinery.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "点检项目表-流水号 -> 生产任务投料表-记录ID",
        "id": 27720,
        "tableAssociationName": "dv_check_subject.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "设备资源表-记录ID -> 生产任务投料表-记录ID",
        "id": 27721,
        "tableAssociationName": "md_workstation_machine.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 生产任务投料表-记录ID",
        "id": 27722,
        "tableAssociationName": "md_workstation_tool.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "人力资源表-记录ID -> 生产任务投料表-记录ID",
        "id": 27723,
        "tableAssociationName": "md_workstation_worker.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 生产任务投料表-记录ID",
        "id": 27724,
        "tableAssociationName": "pro_feedback.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 生产任务投料表-记录ID",
        "id": 27725,
        "tableAssociationName": "pro_route_process.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "产品制程-记录ID -> 生产任务投料表-记录ID",
        "id": 27726,
        "tableAssociationName": "pro_route_product.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 生产任务投料表-记录ID",
        "id": 27727,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 生产任务投料表-记录ID",
        "id": 27728,
        "tableAssociationName": "pro_trans_consume.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 生产任务投料表-记录ID",
        "id": 27729,
        "tableAssociationName": "pro_user_workstation.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 生产任务投料表-记录ID",
        "id": 27730,
        "tableAssociationName": "pro_workrecord.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 生产任务投料表-记录ID",
        "id": 27731,
        "tableAssociationName": "qc_defect_record.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 生产任务投料表-记录ID",
        "id": 27732,
        "tableAssociationName": "qc_template_index.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 生产任务投料表-记录ID",
        "id": 27733,
        "tableAssociationName": "qc_template_product.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 生产任务投料表-记录ID",
        "id": 27734,
        "tableAssociationName": "wm_item_consume.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 生产任务投料表-记录ID",
        "id": 27735,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 生产任务投料表-记录ID",
        "id": 27736,
        "tableAssociationName": "wm_product_produce.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 生产任务投料表-记录ID",
        "id": 27737,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_task_issue.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20866
        }
    },
    {
        "remark": "计划班组表-流水号 -> 物料消耗记录表-记录ID",
        "id": 27738,
        "tableAssociationName": "cal_plan_team.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "班组排班表-流水号 -> 物料消耗记录表-记录ID",
        "id": 27739,
        "tableAssociationName": "cal_teamshift.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "点检设备表-流水号 -> 物料消耗记录表-记录ID",
        "id": 27740,
        "tableAssociationName": "dv_check_machinery.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "点检项目表-流水号 -> 物料消耗记录表-记录ID",
        "id": 27741,
        "tableAssociationName": "dv_check_subject.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "设备资源表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27742,
        "tableAssociationName": "md_workstation_machine.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27743,
        "tableAssociationName": "md_workstation_tool.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "人力资源表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27744,
        "tableAssociationName": "md_workstation_worker.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27745,
        "tableAssociationName": "pro_feedback.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27746,
        "tableAssociationName": "pro_route_process.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "产品制程-记录ID -> 物料消耗记录表-记录ID",
        "id": 27747,
        "tableAssociationName": "pro_route_product.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27748,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27749,
        "tableAssociationName": "pro_task_issue.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 物料消耗记录表-记录ID",
        "id": 27750,
        "tableAssociationName": "pro_user_workstation.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27751,
        "tableAssociationName": "pro_workrecord.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 物料消耗记录表-记录ID",
        "id": 27752,
        "tableAssociationName": "qc_defect_record.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27753,
        "tableAssociationName": "qc_template_index.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27754,
        "tableAssociationName": "qc_template_product.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 物料消耗记录表-记录ID",
        "id": 27755,
        "tableAssociationName": "wm_item_consume.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 物料消耗记录表-记录ID",
        "id": 27756,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 物料消耗记录表-记录ID",
        "id": 27757,
        "tableAssociationName": "wm_product_produce.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 物料消耗记录表-记录ID",
        "id": 27758,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_trans_consume.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20891
        }
    },
    {
        "remark": "物料消耗记录表-流转单ID -> 流转单表-流转单ID",
        "id": 27759,
        "tableAssociationName": "pro_trans_consume.trans_order_id to pro_trans_order.trans_order_id",
        "masterColumn": {
            "id": 20892
        },
        "slaveColumn": {
            "id": 20920
        }
    },
    {
        "remark": "计划班组表-流水号 -> 用户工作站绑定关系-记录ID",
        "id": 27760,
        "tableAssociationName": "cal_plan_team.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "班组排班表-流水号 -> 用户工作站绑定关系-记录ID",
        "id": 27761,
        "tableAssociationName": "cal_teamshift.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "点检设备表-流水号 -> 用户工作站绑定关系-记录ID",
        "id": 27762,
        "tableAssociationName": "dv_check_machinery.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "点检项目表-流水号 -> 用户工作站绑定关系-记录ID",
        "id": 27763,
        "tableAssociationName": "dv_check_subject.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "设备资源表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27764,
        "tableAssociationName": "md_workstation_machine.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27765,
        "tableAssociationName": "md_workstation_tool.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "人力资源表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27766,
        "tableAssociationName": "md_workstation_worker.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27767,
        "tableAssociationName": "pro_feedback.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27768,
        "tableAssociationName": "pro_route_process.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "产品制程-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27769,
        "tableAssociationName": "pro_route_product.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27770,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27771,
        "tableAssociationName": "pro_task_issue.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27772,
        "tableAssociationName": "pro_trans_consume.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27773,
        "tableAssociationName": "pro_workrecord.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 用户工作站绑定关系-记录ID",
        "id": 27774,
        "tableAssociationName": "qc_defect_record.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27775,
        "tableAssociationName": "qc_template_index.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27776,
        "tableAssociationName": "qc_template_product.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27777,
        "tableAssociationName": "wm_item_consume.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27778,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 用户工作站绑定关系-记录ID",
        "id": 27779,
        "tableAssociationName": "wm_product_produce.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 用户工作站绑定关系-记录ID",
        "id": 27780,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_user_workstation.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 20951
        }
    },
    {
        "remark": "生产报工记录表-生产工单ID -> 生产工单表-工单ID",
        "id": 27781,
        "tableAssociationName": "pro_feedback.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 20687
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "生产任务表-生产工单ID -> 生产工单表-工单ID",
        "id": 27782,
        "tableAssociationName": "pro_task.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 20828
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "生产任务投料表-生产工单ID -> 生产工单表-工单ID",
        "id": 27783,
        "tableAssociationName": "pro_task_issue.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 20868
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "物料消耗记录表-生产工单ID -> 生产工单表-工单ID",
        "id": 27784,
        "tableAssociationName": "pro_trans_consume.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 20897
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "流转单表-生产工单ID -> 生产工单表-工单ID",
        "id": 27785,
        "tableAssociationName": "pro_trans_order.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 20930
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "生产工单BOM组成表-生产工单ID -> 生产工单表-工单ID",
        "id": 27786,
        "tableAssociationName": "pro_workorder_bom.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 21000
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "过程检验单表-工单ID -> 生产工单表-工单ID",
        "id": 27787,
        "tableAssociationName": "qc_ipqc.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 21084
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "生产领料单头表-生产工单ID -> 生产工单表-工单ID",
        "id": 27788,
        "tableAssociationName": "wm_issue_header.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 21767
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "物料消耗记录表-生产工单ID -> 生产工单表-工单ID",
        "id": 27789,
        "tableAssociationName": "wm_item_consume.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 21824
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "库存记录表-生产工单ID -> 生产工单表-工单ID",
        "id": 27790,
        "tableAssociationName": "wm_material_stock.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 21955
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "装箱明细表-生产工单ID -> 生产工单表-工单ID",
        "id": 27791,
        "tableAssociationName": "wm_package_line.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22010
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "产品产出记录表-生产工单ID -> 生产工单表-工单ID",
        "id": 27792,
        "tableAssociationName": "wm_product_produce.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22033
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "产品入库录表-生产工单ID -> 生产工单表-工单ID",
        "id": 27793,
        "tableAssociationName": "wm_product_recpt.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22086
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "产品入库记录表行表-生产工单ID -> 生产工单表-工单ID",
        "id": 27794,
        "tableAssociationName": "wm_product_recpt_line.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22123
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "生产退料单头表-生产工单ID -> 生产工单表-工单ID",
        "id": 27795,
        "tableAssociationName": "wm_rt_issue.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22207
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "库存事务表-生产工单ID -> 生产工单表-工单ID",
        "id": 27796,
        "tableAssociationName": "wm_transaction.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22444
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "转移单行表-生产工单ID -> 生产工单表-工单ID",
        "id": 27797,
        "tableAssociationName": "wm_transfer_line.workorder_id to pro_workorder.workorder_id",
        "masterColumn": {
            "id": 22491
        },
        "slaveColumn": {
            "id": 20968
        }
    },
    {
        "remark": "设备维修单行-行ID -> 生产工单BOM组成表-行ID",
        "id": 27798,
        "tableAssociationName": "dv_repair_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27799,
        "tableAssociationName": "pro_task_issue.source_line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 生产工单BOM组成表-行ID",
        "id": 27800,
        "tableAssociationName": "pro_trans_consume.source_line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 生产工单BOM组成表-行ID",
        "id": 27801,
        "tableAssociationName": "qc_defect_record.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 生产工单BOM组成表-行ID",
        "id": 27802,
        "tableAssociationName": "qc_ipqc_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 生产工单BOM组成表-行ID",
        "id": 27803,
        "tableAssociationName": "qc_iqc_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 生产工单BOM组成表-行ID",
        "id": 27804,
        "tableAssociationName": "qc_oqc_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27805,
        "tableAssociationName": "wm_issue_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27806,
        "tableAssociationName": "wm_item_consume_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27807,
        "tableAssociationName": "wm_item_recpt_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 生产工单BOM组成表-行ID",
        "id": 27808,
        "tableAssociationName": "wm_package_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27809,
        "tableAssociationName": "wm_product_produce_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27810,
        "tableAssociationName": "wm_product_recpt_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27811,
        "tableAssociationName": "wm_product_salse_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27812,
        "tableAssociationName": "wm_rt_issue_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27813,
        "tableAssociationName": "wm_rt_salse_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 生产工单BOM组成表-行ID",
        "id": 27814,
        "tableAssociationName": "wm_rt_vendor_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 生产工单BOM组成表-行ID",
        "id": 27815,
        "tableAssociationName": "wm_transaction.source_doc_line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 生产工单BOM组成表-行ID",
        "id": 27816,
        "tableAssociationName": "wm_transfer_line.line_id to pro_workorder_bom.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 20999
        }
    },
    {
        "remark": "计划班组表-流水号 -> 上下工记录表-记录ID",
        "id": 27817,
        "tableAssociationName": "cal_plan_team.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "班组排班表-流水号 -> 上下工记录表-记录ID",
        "id": 27818,
        "tableAssociationName": "cal_teamshift.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "点检设备表-流水号 -> 上下工记录表-记录ID",
        "id": 27819,
        "tableAssociationName": "dv_check_machinery.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "点检项目表-流水号 -> 上下工记录表-记录ID",
        "id": 27820,
        "tableAssociationName": "dv_check_subject.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "设备资源表-记录ID -> 上下工记录表-记录ID",
        "id": 27821,
        "tableAssociationName": "md_workstation_machine.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 上下工记录表-记录ID",
        "id": 27822,
        "tableAssociationName": "md_workstation_tool.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "人力资源表-记录ID -> 上下工记录表-记录ID",
        "id": 27823,
        "tableAssociationName": "md_workstation_worker.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 上下工记录表-记录ID",
        "id": 27824,
        "tableAssociationName": "pro_feedback.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 上下工记录表-记录ID",
        "id": 27825,
        "tableAssociationName": "pro_route_process.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "产品制程-记录ID -> 上下工记录表-记录ID",
        "id": 27826,
        "tableAssociationName": "pro_route_product.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 上下工记录表-记录ID",
        "id": 27827,
        "tableAssociationName": "pro_route_product_bom.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 上下工记录表-记录ID",
        "id": 27828,
        "tableAssociationName": "pro_task_issue.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 上下工记录表-记录ID",
        "id": 27829,
        "tableAssociationName": "pro_trans_consume.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 上下工记录表-记录ID",
        "id": 27830,
        "tableAssociationName": "pro_user_workstation.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 上下工记录表-记录ID",
        "id": 27831,
        "tableAssociationName": "qc_defect_record.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 上下工记录表-记录ID",
        "id": 27832,
        "tableAssociationName": "qc_template_index.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 上下工记录表-记录ID",
        "id": 27833,
        "tableAssociationName": "qc_template_product.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 上下工记录表-记录ID",
        "id": 27834,
        "tableAssociationName": "wm_item_consume.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 上下工记录表-记录ID",
        "id": 27835,
        "tableAssociationName": "wm_item_consume_line.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 上下工记录表-记录ID",
        "id": 27836,
        "tableAssociationName": "wm_product_produce.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 上下工记录表-记录ID",
        "id": 27837,
        "tableAssociationName": "wm_product_produce_line.record_id to pro_workrecord.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 21017
        }
    },
    {
        "remark": "计划班组表-流水号 -> 检验单缺陷记录表-缺陷ID",
        "id": 27838,
        "tableAssociationName": "cal_plan_team.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "班组排班表-流水号 -> 检验单缺陷记录表-缺陷ID",
        "id": 27839,
        "tableAssociationName": "cal_teamshift.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "点检设备表-流水号 -> 检验单缺陷记录表-缺陷ID",
        "id": 27840,
        "tableAssociationName": "dv_check_machinery.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "点检项目表-流水号 -> 检验单缺陷记录表-缺陷ID",
        "id": 27841,
        "tableAssociationName": "dv_check_subject.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "设备资源表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27842,
        "tableAssociationName": "md_workstation_machine.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27843,
        "tableAssociationName": "md_workstation_tool.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "人力资源表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27844,
        "tableAssociationName": "md_workstation_worker.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27845,
        "tableAssociationName": "pro_feedback.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27846,
        "tableAssociationName": "pro_route_process.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "产品制程-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27847,
        "tableAssociationName": "pro_route_product.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27848,
        "tableAssociationName": "pro_route_product_bom.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27849,
        "tableAssociationName": "pro_task_issue.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27850,
        "tableAssociationName": "pro_trans_consume.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27851,
        "tableAssociationName": "pro_user_workstation.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27852,
        "tableAssociationName": "pro_workrecord.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27853,
        "tableAssociationName": "qc_template_index.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27854,
        "tableAssociationName": "qc_template_product.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27855,
        "tableAssociationName": "wm_item_consume.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27856,
        "tableAssociationName": "wm_item_consume_line.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27857,
        "tableAssociationName": "wm_product_produce.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 检验单缺陷记录表-缺陷ID",
        "id": 27858,
        "tableAssociationName": "wm_product_produce_line.record_id to qc_defect_record.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 21049
        }
    },
    {
        "remark": "过程检验单行表-检测项ID -> 检测项表-检测项ID",
        "id": 27859,
        "tableAssociationName": "qc_ipqc_line.index_id to qc_index.index_id",
        "masterColumn": {
            "id": 21125
        },
        "slaveColumn": {
            "id": 21065
        }
    },
    {
        "remark": "来料检验单行表-检测项ID -> 检测项表-检测项ID",
        "id": 27860,
        "tableAssociationName": "qc_iqc_line.index_id to qc_index.index_id",
        "masterColumn": {
            "id": 21188
        },
        "slaveColumn": {
            "id": 21065
        }
    },
    {
        "remark": "出货检验单行表-检测项ID -> 检测项表-检测项ID",
        "id": 27861,
        "tableAssociationName": "qc_oqc_line.index_id to qc_index.index_id",
        "masterColumn": {
            "id": 21251
        },
        "slaveColumn": {
            "id": 21065
        }
    },
    {
        "remark": "检测模板-检测项表-检测项ID -> 检测项表-检测项ID",
        "id": 27862,
        "tableAssociationName": "qc_template_index.index_id to qc_index.index_id",
        "masterColumn": {
            "id": 21289
        },
        "slaveColumn": {
            "id": 21065
        }
    },
    {
        "remark": "过程检验单行表-检验单ID -> 过程检验单表-检验单ID",
        "id": 27863,
        "tableAssociationName": "qc_ipqc_line.ipqc_id to qc_ipqc.ipqc_id",
        "masterColumn": {
            "id": 21124
        },
        "slaveColumn": {
            "id": 21079
        }
    },
    {
        "remark": "设备维修单行-行ID -> 过程检验单行表-记录ID",
        "id": 27864,
        "tableAssociationName": "dv_repair_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 过程检验单行表-记录ID",
        "id": 27865,
        "tableAssociationName": "pro_task_issue.source_line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 过程检验单行表-记录ID",
        "id": 27866,
        "tableAssociationName": "pro_trans_consume.source_line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 过程检验单行表-记录ID",
        "id": 27867,
        "tableAssociationName": "pro_workorder_bom.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 过程检验单行表-记录ID",
        "id": 27868,
        "tableAssociationName": "qc_defect_record.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 过程检验单行表-记录ID",
        "id": 27869,
        "tableAssociationName": "qc_iqc_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 过程检验单行表-记录ID",
        "id": 27870,
        "tableAssociationName": "qc_oqc_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 过程检验单行表-记录ID",
        "id": 27871,
        "tableAssociationName": "wm_issue_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 过程检验单行表-记录ID",
        "id": 27872,
        "tableAssociationName": "wm_item_consume_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 过程检验单行表-记录ID",
        "id": 27873,
        "tableAssociationName": "wm_item_recpt_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 过程检验单行表-记录ID",
        "id": 27874,
        "tableAssociationName": "wm_package_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 过程检验单行表-记录ID",
        "id": 27875,
        "tableAssociationName": "wm_product_produce_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 过程检验单行表-记录ID",
        "id": 27876,
        "tableAssociationName": "wm_product_recpt_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 过程检验单行表-记录ID",
        "id": 27877,
        "tableAssociationName": "wm_product_salse_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 过程检验单行表-记录ID",
        "id": 27878,
        "tableAssociationName": "wm_rt_issue_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 过程检验单行表-记录ID",
        "id": 27879,
        "tableAssociationName": "wm_rt_salse_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 过程检验单行表-记录ID",
        "id": 27880,
        "tableAssociationName": "wm_rt_vendor_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 过程检验单行表-记录ID",
        "id": 27881,
        "tableAssociationName": "wm_transaction.source_doc_line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 过程检验单行表-记录ID",
        "id": 27882,
        "tableAssociationName": "wm_transfer_line.line_id to qc_ipqc_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 21123
        }
    },
    {
        "remark": "来料检验单行表-检验单ID -> 来料检验单表-来料检验单ID",
        "id": 27883,
        "tableAssociationName": "qc_iqc_line.iqc_id to qc_iqc.iqc_id",
        "masterColumn": {
            "id": 21187
        },
        "slaveColumn": {
            "id": 21147
        }
    },
    {
        "remark": "物料入库单表-来料检验单ID -> 来料检验单表-来料检验单ID",
        "id": 27884,
        "tableAssociationName": "wm_item_recpt.iqc_id to qc_iqc.iqc_id",
        "masterColumn": {
            "id": 21878
        },
        "slaveColumn": {
            "id": 21147
        }
    },
    {
        "remark": "设备维修单行-行ID -> 来料检验单行表-记录ID",
        "id": 27885,
        "tableAssociationName": "dv_repair_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 来料检验单行表-记录ID",
        "id": 27886,
        "tableAssociationName": "pro_task_issue.source_line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 来料检验单行表-记录ID",
        "id": 27887,
        "tableAssociationName": "pro_trans_consume.source_line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 来料检验单行表-记录ID",
        "id": 27888,
        "tableAssociationName": "pro_workorder_bom.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 来料检验单行表-记录ID",
        "id": 27889,
        "tableAssociationName": "qc_defect_record.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 来料检验单行表-记录ID",
        "id": 27890,
        "tableAssociationName": "qc_ipqc_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 来料检验单行表-记录ID",
        "id": 27891,
        "tableAssociationName": "qc_oqc_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 来料检验单行表-记录ID",
        "id": 27892,
        "tableAssociationName": "wm_issue_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 来料检验单行表-记录ID",
        "id": 27893,
        "tableAssociationName": "wm_item_consume_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 来料检验单行表-记录ID",
        "id": 27894,
        "tableAssociationName": "wm_item_recpt_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 来料检验单行表-记录ID",
        "id": 27895,
        "tableAssociationName": "wm_package_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 来料检验单行表-记录ID",
        "id": 27896,
        "tableAssociationName": "wm_product_produce_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 来料检验单行表-记录ID",
        "id": 27897,
        "tableAssociationName": "wm_product_recpt_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 来料检验单行表-记录ID",
        "id": 27898,
        "tableAssociationName": "wm_product_salse_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 来料检验单行表-记录ID",
        "id": 27899,
        "tableAssociationName": "wm_rt_issue_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 来料检验单行表-记录ID",
        "id": 27900,
        "tableAssociationName": "wm_rt_salse_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 来料检验单行表-记录ID",
        "id": 27901,
        "tableAssociationName": "wm_rt_vendor_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 来料检验单行表-记录ID",
        "id": 27902,
        "tableAssociationName": "wm_transaction.source_doc_line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 来料检验单行表-记录ID",
        "id": 27903,
        "tableAssociationName": "wm_transfer_line.line_id to qc_iqc_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 21186
        }
    },
    {
        "remark": "出货检验单行表-检验单ID -> 出货检验单表-出货检验单ID",
        "id": 27904,
        "tableAssociationName": "qc_oqc_line.oqc_id to qc_oqc.oqc_id",
        "masterColumn": {
            "id": 21250
        },
        "slaveColumn": {
            "id": 21210
        }
    },
    {
        "remark": "销售出库单表-出货检验单ID -> 出货检验单表-出货检验单ID",
        "id": 27905,
        "tableAssociationName": "wm_product_salse.oqc_id to qc_oqc.oqc_id",
        "masterColumn": {
            "id": 22149
        },
        "slaveColumn": {
            "id": 21210
        }
    },
    {
        "remark": "设备维修单行-行ID -> 出货检验单行表-记录ID",
        "id": 27906,
        "tableAssociationName": "dv_repair_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 出货检验单行表-记录ID",
        "id": 27907,
        "tableAssociationName": "pro_task_issue.source_line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 出货检验单行表-记录ID",
        "id": 27908,
        "tableAssociationName": "pro_trans_consume.source_line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 出货检验单行表-记录ID",
        "id": 27909,
        "tableAssociationName": "pro_workorder_bom.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 出货检验单行表-记录ID",
        "id": 27910,
        "tableAssociationName": "qc_defect_record.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 出货检验单行表-记录ID",
        "id": 27911,
        "tableAssociationName": "qc_ipqc_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 出货检验单行表-记录ID",
        "id": 27912,
        "tableAssociationName": "qc_iqc_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 出货检验单行表-记录ID",
        "id": 27913,
        "tableAssociationName": "wm_issue_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 出货检验单行表-记录ID",
        "id": 27914,
        "tableAssociationName": "wm_item_consume_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 出货检验单行表-记录ID",
        "id": 27915,
        "tableAssociationName": "wm_item_recpt_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 出货检验单行表-记录ID",
        "id": 27916,
        "tableAssociationName": "wm_package_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 出货检验单行表-记录ID",
        "id": 27917,
        "tableAssociationName": "wm_product_produce_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 出货检验单行表-记录ID",
        "id": 27918,
        "tableAssociationName": "wm_product_recpt_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 出货检验单行表-记录ID",
        "id": 27919,
        "tableAssociationName": "wm_product_salse_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 出货检验单行表-记录ID",
        "id": 27920,
        "tableAssociationName": "wm_rt_issue_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 出货检验单行表-记录ID",
        "id": 27921,
        "tableAssociationName": "wm_rt_salse_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 出货检验单行表-记录ID",
        "id": 27922,
        "tableAssociationName": "wm_rt_vendor_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 出货检验单行表-记录ID",
        "id": 27923,
        "tableAssociationName": "wm_transaction.source_doc_line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 出货检验单行表-记录ID",
        "id": 27924,
        "tableAssociationName": "wm_transfer_line.line_id to qc_oqc_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 21249
        }
    },
    {
        "remark": "过程检验单表-检验模板ID -> 检测模板表-检测模板ID",
        "id": 27925,
        "tableAssociationName": "qc_ipqc.template_id to qc_template.template_id",
        "masterColumn": {
            "id": 21083
        },
        "slaveColumn": {
            "id": 21273
        }
    },
    {
        "remark": "来料检验单表-检验模板ID -> 检测模板表-检测模板ID",
        "id": 27926,
        "tableAssociationName": "qc_iqc.template_id to qc_template.template_id",
        "masterColumn": {
            "id": 21150
        },
        "slaveColumn": {
            "id": 21273
        }
    },
    {
        "remark": "出货检验单表-检验模板ID -> 检测模板表-检测模板ID",
        "id": 27927,
        "tableAssociationName": "qc_oqc.template_id to qc_template.template_id",
        "masterColumn": {
            "id": 21213
        },
        "slaveColumn": {
            "id": 21273
        }
    },
    {
        "remark": "检测模板-检测项表-检测模板ID -> 检测模板表-检测模板ID",
        "id": 27928,
        "tableAssociationName": "qc_template_index.template_id to qc_template.template_id",
        "masterColumn": {
            "id": 21288
        },
        "slaveColumn": {
            "id": 21273
        }
    },
    {
        "remark": "检测模板-产品表-检测模板ID -> 检测模板表-检测模板ID",
        "id": 27929,
        "tableAssociationName": "qc_template_product.template_id to qc_template.template_id",
        "masterColumn": {
            "id": 21310
        },
        "slaveColumn": {
            "id": 21273
        }
    },
    {
        "remark": "计划班组表-流水号 -> 检测模板-检测项表-记录ID",
        "id": 27930,
        "tableAssociationName": "cal_plan_team.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "班组排班表-流水号 -> 检测模板-检测项表-记录ID",
        "id": 27931,
        "tableAssociationName": "cal_teamshift.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "点检设备表-流水号 -> 检测模板-检测项表-记录ID",
        "id": 27932,
        "tableAssociationName": "dv_check_machinery.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "点检项目表-流水号 -> 检测模板-检测项表-记录ID",
        "id": 27933,
        "tableAssociationName": "dv_check_subject.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "设备资源表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27934,
        "tableAssociationName": "md_workstation_machine.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27935,
        "tableAssociationName": "md_workstation_tool.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "人力资源表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27936,
        "tableAssociationName": "md_workstation_worker.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27937,
        "tableAssociationName": "pro_feedback.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27938,
        "tableAssociationName": "pro_route_process.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "产品制程-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27939,
        "tableAssociationName": "pro_route_product.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27940,
        "tableAssociationName": "pro_route_product_bom.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27941,
        "tableAssociationName": "pro_task_issue.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27942,
        "tableAssociationName": "pro_trans_consume.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27943,
        "tableAssociationName": "pro_user_workstation.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27944,
        "tableAssociationName": "pro_workrecord.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 检测模板-检测项表-记录ID",
        "id": 27945,
        "tableAssociationName": "qc_defect_record.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27946,
        "tableAssociationName": "qc_template_product.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 检测模板-检测项表-记录ID",
        "id": 27947,
        "tableAssociationName": "wm_item_consume.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 检测模板-检测项表-记录ID",
        "id": 27948,
        "tableAssociationName": "wm_item_consume_line.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 检测模板-检测项表-记录ID",
        "id": 27949,
        "tableAssociationName": "wm_product_produce.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 检测模板-检测项表-记录ID",
        "id": 27950,
        "tableAssociationName": "wm_product_produce_line.record_id to qc_template_index.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 21287
        }
    },
    {
        "remark": "计划班组表-流水号 -> 检测模板-产品表-记录ID",
        "id": 27951,
        "tableAssociationName": "cal_plan_team.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "班组排班表-流水号 -> 检测模板-产品表-记录ID",
        "id": 27952,
        "tableAssociationName": "cal_teamshift.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "点检设备表-流水号 -> 检测模板-产品表-记录ID",
        "id": 27953,
        "tableAssociationName": "dv_check_machinery.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "点检项目表-流水号 -> 检测模板-产品表-记录ID",
        "id": 27954,
        "tableAssociationName": "dv_check_subject.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "设备资源表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27955,
        "tableAssociationName": "md_workstation_machine.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27956,
        "tableAssociationName": "md_workstation_tool.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "人力资源表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27957,
        "tableAssociationName": "md_workstation_worker.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27958,
        "tableAssociationName": "pro_feedback.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27959,
        "tableAssociationName": "pro_route_process.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "产品制程-记录ID -> 检测模板-产品表-记录ID",
        "id": 27960,
        "tableAssociationName": "pro_route_product.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27961,
        "tableAssociationName": "pro_route_product_bom.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27962,
        "tableAssociationName": "pro_task_issue.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27963,
        "tableAssociationName": "pro_trans_consume.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 检测模板-产品表-记录ID",
        "id": 27964,
        "tableAssociationName": "pro_user_workstation.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27965,
        "tableAssociationName": "pro_workrecord.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 检测模板-产品表-记录ID",
        "id": 27966,
        "tableAssociationName": "qc_defect_record.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27967,
        "tableAssociationName": "qc_template_index.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 检测模板-产品表-记录ID",
        "id": 27968,
        "tableAssociationName": "wm_item_consume.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 检测模板-产品表-记录ID",
        "id": 27969,
        "tableAssociationName": "wm_item_consume_line.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 检测模板-产品表-记录ID",
        "id": 27970,
        "tableAssociationName": "wm_product_produce.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 检测模板-产品表-记录ID",
        "id": 27971,
        "tableAssociationName": "wm_product_produce_line.record_id to qc_template_product.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 21309
        }
    },
    {
        "remark": "日历信息表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27972,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27973,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27974,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27975,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27976,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27977,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27978,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27979,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27980,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> Blob类型的触发器表-调度名称",
        "id": 27981,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_blob_triggers.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21330
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_name的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 27982,
        "tableAssociationName": "qrtz_cron_triggers.trigger_name to qrtz_blob_triggers.trigger_name",
        "masterColumn": {
            "id": 21338
        },
        "slaveColumn": {
            "id": 21331
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_name的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 27983,
        "tableAssociationName": "qrtz_fired_triggers.trigger_name to qrtz_blob_triggers.trigger_name",
        "masterColumn": {
            "id": 21344
        },
        "slaveColumn": {
            "id": 21331
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_name的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 27984,
        "tableAssociationName": "qrtz_simple_triggers.trigger_name to qrtz_blob_triggers.trigger_name",
        "masterColumn": {
            "id": 21374
        },
        "slaveColumn": {
            "id": 21331
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_name的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 27985,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_name to qrtz_blob_triggers.trigger_name",
        "masterColumn": {
            "id": 21380
        },
        "slaveColumn": {
            "id": 21331
        }
    },
    {
        "remark": "触发器详细信息表-触发器的名字 -> Blob类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 27986,
        "tableAssociationName": "qrtz_triggers.trigger_name to qrtz_blob_triggers.trigger_name",
        "masterColumn": {
            "id": 21394
        },
        "slaveColumn": {
            "id": 21331
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_group的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 27987,
        "tableAssociationName": "qrtz_cron_triggers.trigger_group to qrtz_blob_triggers.trigger_group",
        "masterColumn": {
            "id": 21339
        },
        "slaveColumn": {
            "id": 21332
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_group的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 27988,
        "tableAssociationName": "qrtz_fired_triggers.trigger_group to qrtz_blob_triggers.trigger_group",
        "masterColumn": {
            "id": 21345
        },
        "slaveColumn": {
            "id": 21332
        }
    },
    {
        "remark": "暂停的触发器表-qrtz_triggers表trigger_group的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 27989,
        "tableAssociationName": "qrtz_paused_trigger_grps.trigger_group to qrtz_blob_triggers.trigger_group",
        "masterColumn": {
            "id": 21368
        },
        "slaveColumn": {
            "id": 21332
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_group的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 27990,
        "tableAssociationName": "qrtz_simple_triggers.trigger_group to qrtz_blob_triggers.trigger_group",
        "masterColumn": {
            "id": 21375
        },
        "slaveColumn": {
            "id": 21332
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_group的外键 -> Blob类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 27991,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_group to qrtz_blob_triggers.trigger_group",
        "masterColumn": {
            "id": 21381
        },
        "slaveColumn": {
            "id": 21332
        }
    },
    {
        "remark": "触发器详细信息表-触发器所属组的名字 -> Blob类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 27992,
        "tableAssociationName": "qrtz_triggers.trigger_group to qrtz_blob_triggers.trigger_group",
        "masterColumn": {
            "id": 21395
        },
        "slaveColumn": {
            "id": 21332
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27993,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27994,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27995,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 日历信息表-调度名称",
        "id": 27996,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 日历信息表-调度名称",
        "id": 27997,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 日历信息表-调度名称",
        "id": 27998,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 日历信息表-调度名称",
        "id": 27999,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 日历信息表-调度名称",
        "id": 28000,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 日历信息表-调度名称",
        "id": 28001,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 日历信息表-调度名称",
        "id": 28002,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_calendars.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21334
        }
    },
    {
        "remark": "触发器详细信息表-日程表名称 -> 日历信息表-日历名称",
        "id": 28003,
        "tableAssociationName": "qrtz_triggers.calendar_name to qrtz_calendars.calendar_name",
        "masterColumn": {
            "id": 21406
        },
        "slaveColumn": {
            "id": 21335
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28004,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "日历信息表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28005,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28006,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28007,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28008,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28009,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28010,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28011,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28012,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> Cron类型的触发器表-调度名称",
        "id": 28013,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_cron_triggers.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21337
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_name的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 28014,
        "tableAssociationName": "qrtz_blob_triggers.trigger_name to qrtz_cron_triggers.trigger_name",
        "masterColumn": {
            "id": 21331
        },
        "slaveColumn": {
            "id": 21338
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_name的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 28015,
        "tableAssociationName": "qrtz_fired_triggers.trigger_name to qrtz_cron_triggers.trigger_name",
        "masterColumn": {
            "id": 21344
        },
        "slaveColumn": {
            "id": 21338
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_name的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 28016,
        "tableAssociationName": "qrtz_simple_triggers.trigger_name to qrtz_cron_triggers.trigger_name",
        "masterColumn": {
            "id": 21374
        },
        "slaveColumn": {
            "id": 21338
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_name的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 28017,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_name to qrtz_cron_triggers.trigger_name",
        "masterColumn": {
            "id": 21380
        },
        "slaveColumn": {
            "id": 21338
        }
    },
    {
        "remark": "触发器详细信息表-触发器的名字 -> Cron类型的触发器表-qrtz_triggers表trigger_name的外键",
        "id": 28018,
        "tableAssociationName": "qrtz_triggers.trigger_name to qrtz_cron_triggers.trigger_name",
        "masterColumn": {
            "id": 21394
        },
        "slaveColumn": {
            "id": 21338
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_group的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28019,
        "tableAssociationName": "qrtz_blob_triggers.trigger_group to qrtz_cron_triggers.trigger_group",
        "masterColumn": {
            "id": 21332
        },
        "slaveColumn": {
            "id": 21339
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_group的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28020,
        "tableAssociationName": "qrtz_fired_triggers.trigger_group to qrtz_cron_triggers.trigger_group",
        "masterColumn": {
            "id": 21345
        },
        "slaveColumn": {
            "id": 21339
        }
    },
    {
        "remark": "暂停的触发器表-qrtz_triggers表trigger_group的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28021,
        "tableAssociationName": "qrtz_paused_trigger_grps.trigger_group to qrtz_cron_triggers.trigger_group",
        "masterColumn": {
            "id": 21368
        },
        "slaveColumn": {
            "id": 21339
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_group的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28022,
        "tableAssociationName": "qrtz_simple_triggers.trigger_group to qrtz_cron_triggers.trigger_group",
        "masterColumn": {
            "id": 21375
        },
        "slaveColumn": {
            "id": 21339
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_group的外键 -> Cron类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28023,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_group to qrtz_cron_triggers.trigger_group",
        "masterColumn": {
            "id": 21381
        },
        "slaveColumn": {
            "id": 21339
        }
    },
    {
        "remark": "触发器详细信息表-触发器所属组的名字 -> Cron类型的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28024,
        "tableAssociationName": "qrtz_triggers.trigger_group to qrtz_cron_triggers.trigger_group",
        "masterColumn": {
            "id": 21395
        },
        "slaveColumn": {
            "id": 21339
        }
    },
    {
        "remark": "Blob类型��触发器表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28025,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28026,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28027,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28028,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28029,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28030,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28031,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28032,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28033,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 已触发的触发器表-调度名称",
        "id": 28034,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_fired_triggers.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21342
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28035,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28036,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28037,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28038,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28039,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28040,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28041,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28042,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28043,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 任务详细信息表-调度名称",
        "id": 28044,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_job_details.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21355
        }
    },
    {
        "remark": "已触发的触发器表-任务名称 -> 任务详细信息表-任务名称",
        "id": 28045,
        "tableAssociationName": "qrtz_fired_triggers.job_name to qrtz_job_details.job_name",
        "masterColumn": {
            "id": 21351
        },
        "slaveColumn": {
            "id": 21356
        }
    },
    {
        "remark": "触发器详细信息表-qrtz_job_details表job_name的外键 -> 任务详细信息表-任务名称",
        "id": 28046,
        "tableAssociationName": "qrtz_triggers.job_name to qrtz_job_details.job_name",
        "masterColumn": {
            "id": 21396
        },
        "slaveColumn": {
            "id": 21356
        }
    },
    {
        "remark": "定时任务调度表-任务名称 -> 任务详细信息表-任务名称",
        "id": 28047,
        "tableAssociationName": "sys_job.job_name to qrtz_job_details.job_name",
        "masterColumn": {
            "id": 21533
        },
        "slaveColumn": {
            "id": 21356
        }
    },
    {
        "remark": "定时任务调度日志表-任务名称 -> 任务详细信息表-任务名称",
        "id": 28048,
        "tableAssociationName": "sys_job_log.job_name to qrtz_job_details.job_name",
        "masterColumn": {
            "id": 21546
        },
        "slaveColumn": {
            "id": 21356
        }
    },
    {
        "remark": "已触发的触发器表-任务组名 -> 任务详细信息表-任务组名",
        "id": 28049,
        "tableAssociationName": "qrtz_fired_triggers.job_group to qrtz_job_details.job_group",
        "masterColumn": {
            "id": 21352
        },
        "slaveColumn": {
            "id": 21357
        }
    },
    {
        "remark": "触发器详细信息表-qrtz_job_details表job_group的外键 -> 任务详细信息表-任务组名",
        "id": 28050,
        "tableAssociationName": "qrtz_triggers.job_group to qrtz_job_details.job_group",
        "masterColumn": {
            "id": 21397
        },
        "slaveColumn": {
            "id": 21357
        }
    },
    {
        "remark": "定时任务调度表-任务组名 -> 任务详细信息表-任务组名",
        "id": 28051,
        "tableAssociationName": "sys_job.job_group to qrtz_job_details.job_group",
        "masterColumn": {
            "id": 21534
        },
        "slaveColumn": {
            "id": 21357
        }
    },
    {
        "remark": "定时任务调度日志表-任务组名 -> 任务详细信息表-任务组名",
        "id": 28052,
        "tableAssociationName": "sys_job_log.job_group to qrtz_job_details.job_group",
        "masterColumn": {
            "id": 21547
        },
        "slaveColumn": {
            "id": 21357
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28053,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28054,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28055,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28056,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28057,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28058,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "调��器状态表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28059,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28060,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28061,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 存储的悲观锁信息表-调度名称",
        "id": 28062,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_locks.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21365
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28063,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28064,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28065,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28066,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28067,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28068,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28069,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28070,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28071,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 暂停的触发器表-调度名称",
        "id": 28072,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_paused_trigger_grps.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21367
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_group的外键 -> 暂停的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28073,
        "tableAssociationName": "qrtz_blob_triggers.trigger_group to qrtz_paused_trigger_grps.trigger_group",
        "masterColumn": {
            "id": 21332
        },
        "slaveColumn": {
            "id": 21368
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_group的外键 -> 暂停的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28074,
        "tableAssociationName": "qrtz_cron_triggers.trigger_group to qrtz_paused_trigger_grps.trigger_group",
        "masterColumn": {
            "id": 21339
        },
        "slaveColumn": {
            "id": 21368
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_group的外键 -> 暂停的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28075,
        "tableAssociationName": "qrtz_fired_triggers.trigger_group to qrtz_paused_trigger_grps.trigger_group",
        "masterColumn": {
            "id": 21345
        },
        "slaveColumn": {
            "id": 21368
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_group的外键 -> 暂停的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28076,
        "tableAssociationName": "qrtz_simple_triggers.trigger_group to qrtz_paused_trigger_grps.trigger_group",
        "masterColumn": {
            "id": 21375
        },
        "slaveColumn": {
            "id": 21368
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_group的外键 -> 暂停的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28077,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_group to qrtz_paused_trigger_grps.trigger_group",
        "masterColumn": {
            "id": 21381
        },
        "slaveColumn": {
            "id": 21368
        }
    },
    {
        "remark": "触发器详细信息表-触发器所属组的名字 -> 暂停的触发器表-qrtz_triggers表trigger_group的外键",
        "id": 28078,
        "tableAssociationName": "qrtz_triggers.trigger_group to qrtz_paused_trigger_grps.trigger_group",
        "masterColumn": {
            "id": 21395
        },
        "slaveColumn": {
            "id": 21368
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 调度器状态表-调度名称",
        "id": 28079,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 调度器状态表-调度名称",
        "id": 28080,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 调度器状态表-调度名称",
        "id": 28081,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 调度器状态表-调度名称",
        "id": 28082,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 调度器状态表-调度名称",
        "id": 28083,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 调度器状态表-调度名称",
        "id": 28084,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 调度器状态表-调度名称",
        "id": 28085,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 调度器状态表-调度名称",
        "id": 28086,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 调度器状态表-调度名称",
        "id": 28087,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 调度器状态表-调度名称",
        "id": 28088,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_scheduler_state.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21369
        }
    },
    {
        "remark": "已触发的触发器表-调度器实例名 -> 调度器状态表-实例名称",
        "id": 28089,
        "tableAssociationName": "qrtz_fired_triggers.instance_name to qrtz_scheduler_state.instance_name",
        "masterColumn": {
            "id": 21346
        },
        "slaveColumn": {
            "id": 21370
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28090,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28091,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28092,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28093,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28094,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28095,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28096,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28097,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28098,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 简单触发器的信息表-调度名称",
        "id": 28099,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_simple_triggers.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21373
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_name的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_name的外键",
        "id": 28100,
        "tableAssociationName": "qrtz_blob_triggers.trigger_name to qrtz_simple_triggers.trigger_name",
        "masterColumn": {
            "id": 21331
        },
        "slaveColumn": {
            "id": 21374
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_name的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_name的外键",
        "id": 28101,
        "tableAssociationName": "qrtz_cron_triggers.trigger_name to qrtz_simple_triggers.trigger_name",
        "masterColumn": {
            "id": 21338
        },
        "slaveColumn": {
            "id": 21374
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_name的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_name的外键",
        "id": 28102,
        "tableAssociationName": "qrtz_fired_triggers.trigger_name to qrtz_simple_triggers.trigger_name",
        "masterColumn": {
            "id": 21344
        },
        "slaveColumn": {
            "id": 21374
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_name的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_name的外键",
        "id": 28103,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_name to qrtz_simple_triggers.trigger_name",
        "masterColumn": {
            "id": 21380
        },
        "slaveColumn": {
            "id": 21374
        }
    },
    {
        "remark": "触发器详细信息表-触发器的名字 -> 简单触发器的信息表-qrtz_triggers表trigger_name的外键",
        "id": 28104,
        "tableAssociationName": "qrtz_triggers.trigger_name to qrtz_simple_triggers.trigger_name",
        "masterColumn": {
            "id": 21394
        },
        "slaveColumn": {
            "id": 21374
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_group的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_group的外键",
        "id": 28105,
        "tableAssociationName": "qrtz_blob_triggers.trigger_group to qrtz_simple_triggers.trigger_group",
        "masterColumn": {
            "id": 21332
        },
        "slaveColumn": {
            "id": 21375
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_group的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_group的外键",
        "id": 28106,
        "tableAssociationName": "qrtz_cron_triggers.trigger_group to qrtz_simple_triggers.trigger_group",
        "masterColumn": {
            "id": 21339
        },
        "slaveColumn": {
            "id": 21375
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_group的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_group的外键",
        "id": 28107,
        "tableAssociationName": "qrtz_fired_triggers.trigger_group to qrtz_simple_triggers.trigger_group",
        "masterColumn": {
            "id": 21345
        },
        "slaveColumn": {
            "id": 21375
        }
    },
    {
        "remark": "暂停的触发器表-qrtz_triggers表trigger_group的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_group的外键",
        "id": 28108,
        "tableAssociationName": "qrtz_paused_trigger_grps.trigger_group to qrtz_simple_triggers.trigger_group",
        "masterColumn": {
            "id": 21368
        },
        "slaveColumn": {
            "id": 21375
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_group的外键 -> 简单触发器的信息表-qrtz_triggers表trigger_group的外键",
        "id": 28109,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_group to qrtz_simple_triggers.trigger_group",
        "masterColumn": {
            "id": 21381
        },
        "slaveColumn": {
            "id": 21375
        }
    },
    {
        "remark": "触发器详细信息表-触发器所属组的名字 -> 简单触发器的信息表-qrtz_triggers表trigger_group的外键",
        "id": 28110,
        "tableAssociationName": "qrtz_triggers.trigger_group to qrtz_simple_triggers.trigger_group",
        "masterColumn": {
            "id": 21395
        },
        "slaveColumn": {
            "id": 21375
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28111,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28112,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28113,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28114,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28115,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28116,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28117,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28118,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28119,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "触发器详细信息表-调度名称 -> 同步机制的行锁表-调度名称",
        "id": 28120,
        "tableAssociationName": "qrtz_triggers.sched_name to qrtz_simprop_triggers.sched_name",
        "masterColumn": {
            "id": 21393
        },
        "slaveColumn": {
            "id": 21379
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_name的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_name的外键",
        "id": 28121,
        "tableAssociationName": "qrtz_blob_triggers.trigger_name to qrtz_simprop_triggers.trigger_name",
        "masterColumn": {
            "id": 21331
        },
        "slaveColumn": {
            "id": 21380
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_name的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_name的外键",
        "id": 28122,
        "tableAssociationName": "qrtz_cron_triggers.trigger_name to qrtz_simprop_triggers.trigger_name",
        "masterColumn": {
            "id": 21338
        },
        "slaveColumn": {
            "id": 21380
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_name的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_name的外键",
        "id": 28123,
        "tableAssociationName": "qrtz_fired_triggers.trigger_name to qrtz_simprop_triggers.trigger_name",
        "masterColumn": {
            "id": 21344
        },
        "slaveColumn": {
            "id": 21380
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_name的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_name的外键",
        "id": 28124,
        "tableAssociationName": "qrtz_simple_triggers.trigger_name to qrtz_simprop_triggers.trigger_name",
        "masterColumn": {
            "id": 21374
        },
        "slaveColumn": {
            "id": 21380
        }
    },
    {
        "remark": "触发器详细信息表-触发器的名字 -> 同步机制的行锁表-qrtz_triggers表trigger_name的外键",
        "id": 28125,
        "tableAssociationName": "qrtz_triggers.trigger_name to qrtz_simprop_triggers.trigger_name",
        "masterColumn": {
            "id": 21394
        },
        "slaveColumn": {
            "id": 21380
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_group的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_group的外键",
        "id": 28126,
        "tableAssociationName": "qrtz_blob_triggers.trigger_group to qrtz_simprop_triggers.trigger_group",
        "masterColumn": {
            "id": 21332
        },
        "slaveColumn": {
            "id": 21381
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_group的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_group的外键",
        "id": 28127,
        "tableAssociationName": "qrtz_cron_triggers.trigger_group to qrtz_simprop_triggers.trigger_group",
        "masterColumn": {
            "id": 21339
        },
        "slaveColumn": {
            "id": 21381
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_group的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_group的外键",
        "id": 28128,
        "tableAssociationName": "qrtz_fired_triggers.trigger_group to qrtz_simprop_triggers.trigger_group",
        "masterColumn": {
            "id": 21345
        },
        "slaveColumn": {
            "id": 21381
        }
    },
    {
        "remark": "暂停的触发器表-qrtz_triggers表trigger_group的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_group的外键",
        "id": 28129,
        "tableAssociationName": "qrtz_paused_trigger_grps.trigger_group to qrtz_simprop_triggers.trigger_group",
        "masterColumn": {
            "id": 21368
        },
        "slaveColumn": {
            "id": 21381
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_group的外键 -> 同步机制的行锁表-qrtz_triggers表trigger_group的外键",
        "id": 28130,
        "tableAssociationName": "qrtz_simple_triggers.trigger_group to qrtz_simprop_triggers.trigger_group",
        "masterColumn": {
            "id": 21375
        },
        "slaveColumn": {
            "id": 21381
        }
    },
    {
        "remark": "触发器详细信息表-触发器所属组的名字 -> 同步机制的行锁表-qrtz_triggers表trigger_group的外键",
        "id": 28131,
        "tableAssociationName": "qrtz_triggers.trigger_group to qrtz_simprop_triggers.trigger_group",
        "masterColumn": {
            "id": 21395
        },
        "slaveColumn": {
            "id": 21381
        }
    },
    {
        "remark": "Blob类型的触发器表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28132,
        "tableAssociationName": "qrtz_blob_triggers.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21330
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "日历信息表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28133,
        "tableAssociationName": "qrtz_calendars.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21334
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "Cron类型的触发器表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28134,
        "tableAssociationName": "qrtz_cron_triggers.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21337
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "已触发的触发器表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28135,
        "tableAssociationName": "qrtz_fired_triggers.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21342
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "任务详细信息表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28136,
        "tableAssociationName": "qrtz_job_details.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21355
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "存储的悲观锁信息表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28137,
        "tableAssociationName": "qrtz_locks.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21365
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "暂停的触发器表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28138,
        "tableAssociationName": "qrtz_paused_trigger_grps.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21367
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "调度器状态表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28139,
        "tableAssociationName": "qrtz_scheduler_state.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21369
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "简单触发器的信息表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28140,
        "tableAssociationName": "qrtz_simple_triggers.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21373
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "同步机制的行锁表-调度名称 -> 触发器详细信息表-调度名称",
        "id": 28141,
        "tableAssociationName": "qrtz_simprop_triggers.sched_name to qrtz_triggers.sched_name",
        "masterColumn": {
            "id": 21379
        },
        "slaveColumn": {
            "id": 21393
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_name的外键 -> 触发器详细信息表-触发器的名字",
        "id": 28142,
        "tableAssociationName": "qrtz_blob_triggers.trigger_name to qrtz_triggers.trigger_name",
        "masterColumn": {
            "id": 21331
        },
        "slaveColumn": {
            "id": 21394
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_name的外键 -> 触发器详细信息表-触发器的名字",
        "id": 28143,
        "tableAssociationName": "qrtz_cron_triggers.trigger_name to qrtz_triggers.trigger_name",
        "masterColumn": {
            "id": 21338
        },
        "slaveColumn": {
            "id": 21394
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_name的外键 -> 触发器详细信息表-触发器的名字",
        "id": 28144,
        "tableAssociationName": "qrtz_fired_triggers.trigger_name to qrtz_triggers.trigger_name",
        "masterColumn": {
            "id": 21344
        },
        "slaveColumn": {
            "id": 21394
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_name的外键 -> 触发器详细信息表-触发器的名字",
        "id": 28145,
        "tableAssociationName": "qrtz_simple_triggers.trigger_name to qrtz_triggers.trigger_name",
        "masterColumn": {
            "id": 21374
        },
        "slaveColumn": {
            "id": 21394
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_name的外键 -> 触发器详细信息表-触发器的名字",
        "id": 28146,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_name to qrtz_triggers.trigger_name",
        "masterColumn": {
            "id": 21380
        },
        "slaveColumn": {
            "id": 21394
        }
    },
    {
        "remark": "Blob类型的触发器表-qrtz_triggers表trigger_group的外键 -> 触发器详细信息表-触发器所属组的名字",
        "id": 28147,
        "tableAssociationName": "qrtz_blob_triggers.trigger_group to qrtz_triggers.trigger_group",
        "masterColumn": {
            "id": 21332
        },
        "slaveColumn": {
            "id": 21395
        }
    },
    {
        "remark": "Cron类型的触发器表-qrtz_triggers表trigger_group的外键 -> 触发器详细信息表-触发器所属组的名字",
        "id": 28148,
        "tableAssociationName": "qrtz_cron_triggers.trigger_group to qrtz_triggers.trigger_group",
        "masterColumn": {
            "id": 21339
        },
        "slaveColumn": {
            "id": 21395
        }
    },
    {
        "remark": "已触发的触发器表-qrtz_triggers表trigger_group的外键 -> 触发器详细信息表-触发器所属组的名字",
        "id": 28149,
        "tableAssociationName": "qrtz_fired_triggers.trigger_group to qrtz_triggers.trigger_group",
        "masterColumn": {
            "id": 21345
        },
        "slaveColumn": {
            "id": 21395
        }
    },
    {
        "remark": "暂停的触发器表-qrtz_triggers表trigger_group的外键 -> 触发器详细信息表-触发器所属组的名字",
        "id": 28150,
        "tableAssociationName": "qrtz_paused_trigger_grps.trigger_group to qrtz_triggers.trigger_group",
        "masterColumn": {
            "id": 21368
        },
        "slaveColumn": {
            "id": 21395
        }
    },
    {
        "remark": "简单触发器的信息表-qrtz_triggers表trigger_group的外键 -> 触发器详细信息表-触发器所属组的名字",
        "id": 28151,
        "tableAssociationName": "qrtz_simple_triggers.trigger_group to qrtz_triggers.trigger_group",
        "masterColumn": {
            "id": 21375
        },
        "slaveColumn": {
            "id": 21395
        }
    },
    {
        "remark": "同步机制的行锁表-qrtz_triggers表trigger_group的外键 -> 触发器详细信息表-触发器所属组的名字",
        "id": 28152,
        "tableAssociationName": "qrtz_simprop_triggers.trigger_group to qrtz_triggers.trigger_group",
        "masterColumn": {
            "id": 21381
        },
        "slaveColumn": {
            "id": 21395
        }
    },
    {
        "remark": "编码生成规则组成表-规则ID -> 编码生成规则表-规则ID",
        "id": 28153,
        "tableAssociationName": "sys_auto_code_part.rule_id to sys_auto_code_rule.rule_id",
        "masterColumn": {
            "id": 21428
        },
        "slaveColumn": {
            "id": 21467
        }
    },
    {
        "remark": "编码生成记录表-规则ID -> 编码生成规则表-规则ID",
        "id": 28154,
        "tableAssociationName": "sys_auto_code_result.rule_id to sys_auto_code_rule.rule_id",
        "masterColumn": {
            "id": 21452
        },
        "slaveColumn": {
            "id": 21467
        }
    },
    {
        "remark": "条码配置-配置ID -> 参数配置表-参数主键",
        "id": 28155,
        "tableAssociationName": "wm_barcode_config.config_id to sys_config.config_id",
        "masterColumn": {
            "id": 21744
        },
        "slaveColumn": {
            "id": 21485
        }
    },
    {
        "remark": "角色和部门关联表-部门ID -> 部门表-部门id",
        "id": 28156,
        "tableAssociationName": "sys_role_dept.dept_id to sys_dept.dept_id",
        "masterColumn": {
            "id": 21656
        },
        "slaveColumn": {
            "id": 21495
        }
    },
    {
        "remark": "用户信息表-部门ID -> 部门表-部门id",
        "id": 28157,
        "tableAssociationName": "sys_user.dept_id to sys_dept.dept_id",
        "masterColumn": {
            "id": 21660
        },
        "slaveColumn": {
            "id": 21495
        }
    },
    {
        "remark": "已触发的触发器表-任务名称 -> 定时任务调度表-任务名称",
        "id": 28158,
        "tableAssociationName": "qrtz_fired_triggers.job_name to sys_job.job_name",
        "masterColumn": {
            "id": 21351
        },
        "slaveColumn": {
            "id": 21533
        }
    },
    {
        "remark": "任务详细信息表-任务名称 -> 定时任务调度表-任务名称",
        "id": 28159,
        "tableAssociationName": "qrtz_job_details.job_name to sys_job.job_name",
        "masterColumn": {
            "id": 21356
        },
        "slaveColumn": {
            "id": 21533
        }
    },
    {
        "remark": "触发器详细信息表-qrtz_job_details表job_name的外键 -> 定时任务调度表-任务名称",
        "id": 28160,
        "tableAssociationName": "qrtz_triggers.job_name to sys_job.job_name",
        "masterColumn": {
            "id": 21396
        },
        "slaveColumn": {
            "id": 21533
        }
    },
    {
        "remark": "定时任务调度日志表-任务名称 -> 定时任务调度表-任务名称",
        "id": 28161,
        "tableAssociationName": "sys_job_log.job_name to sys_job.job_name",
        "masterColumn": {
            "id": 21546
        },
        "slaveColumn": {
            "id": 21533
        }
    },
    {
        "remark": "已触发的触发器表-任务组名 -> 定时任务调度表-任务组名",
        "id": 28162,
        "tableAssociationName": "qrtz_fired_triggers.job_group to sys_job.job_group",
        "masterColumn": {
            "id": 21352
        },
        "slaveColumn": {
            "id": 21534
        }
    },
    {
        "remark": "任务详细信息表-任务组名 -> 定时任务调度表-任务组名",
        "id": 28163,
        "tableAssociationName": "qrtz_job_details.job_group to sys_job.job_group",
        "masterColumn": {
            "id": 21357
        },
        "slaveColumn": {
            "id": 21534
        }
    },
    {
        "remark": "触发器详细信息表-qrtz_job_details表job_group的外键 -> 定时任务调度表-任务组名",
        "id": 28164,
        "tableAssociationName": "qrtz_triggers.job_group to sys_job.job_group",
        "masterColumn": {
            "id": 21397
        },
        "slaveColumn": {
            "id": 21534
        }
    },
    {
        "remark": "定时任务调度日志表-任务组名 -> 定时任务调度表-任务组名",
        "id": 28165,
        "tableAssociationName": "sys_job_log.job_group to sys_job.job_group",
        "masterColumn": {
            "id": 21547
        },
        "slaveColumn": {
            "id": 21534
        }
    },
    {
        "remark": "角色和菜单关联表-菜单ID -> 菜单权限表-菜单ID",
        "id": 28166,
        "tableAssociationName": "sys_role_menu.menu_id to sys_menu.menu_id",
        "masterColumn": {
            "id": 21658
        },
        "slaveColumn": {
            "id": 21562
        }
    },
    {
        "remark": "人力资源表-岗位ID -> 岗位信息表-岗位ID",
        "id": 28167,
        "tableAssociationName": "md_workstation_worker.post_id to sys_post.post_id",
        "masterColumn": {
            "id": 20669
        },
        "slaveColumn": {
            "id": 21631
        }
    },
    {
        "remark": "用户与岗位关联表-岗位ID -> 岗位信息表-岗位ID",
        "id": 28168,
        "tableAssociationName": "sys_user_post.post_id to sys_post.post_id",
        "masterColumn": {
            "id": 21679
        },
        "slaveColumn": {
            "id": 21631
        }
    },
    {
        "remark": "角色和部门关联表-角色ID -> 角色信息表-角色ID",
        "id": 28169,
        "tableAssociationName": "sys_role_dept.role_id to sys_role.role_id",
        "masterColumn": {
            "id": 21655
        },
        "slaveColumn": {
            "id": 21641
        }
    },
    {
        "remark": "角色和菜单关联表-角色ID -> 角色信息表-角色ID",
        "id": 28170,
        "tableAssociationName": "sys_role_menu.role_id to sys_role.role_id",
        "masterColumn": {
            "id": 21657
        },
        "slaveColumn": {
            "id": 21641
        }
    },
    {
        "remark": "用户和角色关联表-角色ID -> 角色信息表-角色ID",
        "id": 28171,
        "tableAssociationName": "sys_user_role.role_id to sys_role.role_id",
        "masterColumn": {
            "id": 21681
        },
        "slaveColumn": {
            "id": 21641
        }
    },
    {
        "remark": "角色信息表-角色ID -> 角色和部门关联表-角色ID",
        "id": 28172,
        "tableAssociationName": "sys_role.role_id to sys_role_dept.role_id",
        "masterColumn": {
            "id": 21641
        },
        "slaveColumn": {
            "id": 21655
        }
    },
    {
        "remark": "角色和菜单关联表-角色ID -> 角色和部门关联表-角色ID",
        "id": 28173,
        "tableAssociationName": "sys_role_menu.role_id to sys_role_dept.role_id",
        "masterColumn": {
            "id": 21657
        },
        "slaveColumn": {
            "id": 21655
        }
    },
    {
        "remark": "用户和角色关联表-角色ID -> 角色和部门关联表-角色ID",
        "id": 28174,
        "tableAssociationName": "sys_user_role.role_id to sys_role_dept.role_id",
        "masterColumn": {
            "id": 21681
        },
        "slaveColumn": {
            "id": 21655
        }
    },
    {
        "remark": "部门表-部门id -> 角色和部门关联表-部门ID",
        "id": 28175,
        "tableAssociationName": "sys_dept.dept_id to sys_role_dept.dept_id",
        "masterColumn": {
            "id": 21495
        },
        "slaveColumn": {
            "id": 21656
        }
    },
    {
        "remark": "用户信息表-部门ID -> 角色和部门关联表-部门ID",
        "id": 28176,
        "tableAssociationName": "sys_user.dept_id to sys_role_dept.dept_id",
        "masterColumn": {
            "id": 21660
        },
        "slaveColumn": {
            "id": 21656
        }
    },
    {
        "remark": "角色信息表-角色ID -> 角色和菜单关联表-角色ID",
        "id": 28177,
        "tableAssociationName": "sys_role.role_id to sys_role_menu.role_id",
        "masterColumn": {
            "id": 21641
        },
        "slaveColumn": {
            "id": 21657
        }
    },
    {
        "remark": "角色和部门关联表-角色ID -> 角色和菜单关联表-角色ID",
        "id": 28178,
        "tableAssociationName": "sys_role_dept.role_id to sys_role_menu.role_id",
        "masterColumn": {
            "id": 21655
        },
        "slaveColumn": {
            "id": 21657
        }
    },
    {
        "remark": "用户和角色关联表-角色ID -> 角色和菜单关联表-角色ID",
        "id": 28179,
        "tableAssociationName": "sys_user_role.role_id to sys_role_menu.role_id",
        "masterColumn": {
            "id": 21681
        },
        "slaveColumn": {
            "id": 21657
        }
    },
    {
        "remark": "菜单权限表-菜单ID -> 角色和菜单关联表-菜单ID",
        "id": 28180,
        "tableAssociationName": "sys_menu.menu_id to sys_role_menu.menu_id",
        "masterColumn": {
            "id": 21562
        },
        "slaveColumn": {
            "id": 21658
        }
    },
    {
        "remark": "班组成员表-用户ID -> 用户信息表-用户ID",
        "id": 28181,
        "tableAssociationName": "cal_team_member.user_id to sys_user.user_id",
        "masterColumn": {
            "id": 20220
        },
        "slaveColumn": {
            "id": 21659
        }
    },
    {
        "remark": "用户工作站绑定关系-用户ID -> 用户信息表-用户ID",
        "id": 28182,
        "tableAssociationName": "pro_user_workstation.user_id to sys_user.user_id",
        "masterColumn": {
            "id": 20952
        },
        "slaveColumn": {
            "id": 21659
        }
    },
    {
        "remark": "上下工记录表-用户ID -> 用户信息表-用户ID",
        "id": 28183,
        "tableAssociationName": "pro_workrecord.user_id to sys_user.user_id",
        "masterColumn": {
            "id": 21018
        },
        "slaveColumn": {
            "id": 21659
        }
    },
    {
        "remark": "用户与岗位关联表-用户ID -> 用户信息表-用户ID",
        "id": 28184,
        "tableAssociationName": "sys_user_post.user_id to sys_user.user_id",
        "masterColumn": {
            "id": 21678
        },
        "slaveColumn": {
            "id": 21659
        }
    },
    {
        "remark": "用户和角色关联表-用户ID -> 用户信息表-用户ID",
        "id": 28185,
        "tableAssociationName": "sys_user_role.user_id to sys_user.user_id",
        "masterColumn": {
            "id": 21680
        },
        "slaveColumn": {
            "id": 21659
        }
    },
    {
        "remark": "班组成员表-用户ID -> 用户与岗位关联表-用户ID",
        "id": 28186,
        "tableAssociationName": "cal_team_member.user_id to sys_user_post.user_id",
        "masterColumn": {
            "id": 20220
        },
        "slaveColumn": {
            "id": 21678
        }
    },
    {
        "remark": "用户工作站绑定关系-用户ID -> 用户与岗位关联表-用户ID",
        "id": 28187,
        "tableAssociationName": "pro_user_workstation.user_id to sys_user_post.user_id",
        "masterColumn": {
            "id": 20952
        },
        "slaveColumn": {
            "id": 21678
        }
    },
    {
        "remark": "上下工记录表-用户ID -> 用户与岗位关联表-用户ID",
        "id": 28188,
        "tableAssociationName": "pro_workrecord.user_id to sys_user_post.user_id",
        "masterColumn": {
            "id": 21018
        },
        "slaveColumn": {
            "id": 21678
        }
    },
    {
        "remark": "用户信息表-用户ID -> 用户与岗位关联表-用户ID",
        "id": 28189,
        "tableAssociationName": "sys_user.user_id to sys_user_post.user_id",
        "masterColumn": {
            "id": 21659
        },
        "slaveColumn": {
            "id": 21678
        }
    },
    {
        "remark": "用户和角色关联表-用户ID -> 用户与岗位关联表-用户ID",
        "id": 28190,
        "tableAssociationName": "sys_user_role.user_id to sys_user_post.user_id",
        "masterColumn": {
            "id": 21680
        },
        "slaveColumn": {
            "id": 21678
        }
    },
    {
        "remark": "人力资源表-岗位ID -> 用户与岗位关联表-岗位ID",
        "id": 28191,
        "tableAssociationName": "md_workstation_worker.post_id to sys_user_post.post_id",
        "masterColumn": {
            "id": 20669
        },
        "slaveColumn": {
            "id": 21679
        }
    },
    {
        "remark": "岗位信息表-岗位ID -> 用户与岗位关联表-岗位ID",
        "id": 28192,
        "tableAssociationName": "sys_post.post_id to sys_user_post.post_id",
        "masterColumn": {
            "id": 21631
        },
        "slaveColumn": {
            "id": 21679
        }
    },
    {
        "remark": "班组成员表-用户ID -> 用户和角色关联表-用户ID",
        "id": 28193,
        "tableAssociationName": "cal_team_member.user_id to sys_user_role.user_id",
        "masterColumn": {
            "id": 20220
        },
        "slaveColumn": {
            "id": 21680
        }
    },
    {
        "remark": "用户工作站绑定关系-用户ID -> 用户和角色关联表-用户ID",
        "id": 28194,
        "tableAssociationName": "pro_user_workstation.user_id to sys_user_role.user_id",
        "masterColumn": {
            "id": 20952
        },
        "slaveColumn": {
            "id": 21680
        }
    },
    {
        "remark": "上下工记录表-用户ID -> 用户和角色关联表-用户ID",
        "id": 28195,
        "tableAssociationName": "pro_workrecord.user_id to sys_user_role.user_id",
        "masterColumn": {
            "id": 21018
        },
        "slaveColumn": {
            "id": 21680
        }
    },
    {
        "remark": "用户信息表-用户ID -> 用户和角色关联表-用户ID",
        "id": 28196,
        "tableAssociationName": "sys_user.user_id to sys_user_role.user_id",
        "masterColumn": {
            "id": 21659
        },
        "slaveColumn": {
            "id": 21680
        }
    },
    {
        "remark": "用户与岗位关联表-用户ID -> 用户和角色关联表-用户ID",
        "id": 28197,
        "tableAssociationName": "sys_user_post.user_id to sys_user_role.user_id",
        "masterColumn": {
            "id": 21678
        },
        "slaveColumn": {
            "id": 21680
        }
    },
    {
        "remark": "角色信息表-角色ID -> 用户和角色关联表-角色ID",
        "id": 28198,
        "tableAssociationName": "sys_role.role_id to sys_user_role.role_id",
        "masterColumn": {
            "id": 21641
        },
        "slaveColumn": {
            "id": 21681
        }
    },
    {
        "remark": "角色和部门关联表-角色ID -> 用户和角色关联表-角色ID",
        "id": 28199,
        "tableAssociationName": "sys_role_dept.role_id to sys_user_role.role_id",
        "masterColumn": {
            "id": 21655
        },
        "slaveColumn": {
            "id": 21681
        }
    },
    {
        "remark": "角色和菜单关联表-角色ID -> 用户和角色关联表-角色ID",
        "id": 28200,
        "tableAssociationName": "sys_role_menu.role_id to sys_user_role.role_id",
        "masterColumn": {
            "id": 21657
        },
        "slaveColumn": {
            "id": 21681
        }
    },
    {
        "remark": "设备表-设备类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28201,
        "tableAssociationName": "dv_machinery.machinery_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20308
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "设备类型表-设备类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28202,
        "tableAssociationName": "dv_machinery_type.machinery_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20324
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "设备类型表-父类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28203,
        "tableAssociationName": "dv_machinery_type.parent_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20327
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "设备维修单-设备类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28204,
        "tableAssociationName": "dv_repair.machinery_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20347
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "物料产品表-物料类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28205,
        "tableAssociationName": "md_item.item_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20477
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "物料产品分类表-产品物料类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28206,
        "tableAssociationName": "md_item_type.item_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20493
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "物料产品分类表-父类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28207,
        "tableAssociationName": "md_item_type.parent_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20496
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "工装夹具资源表-工装夹具类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28208,
        "tableAssociationName": "md_workstation_tool.tool_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20654
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "工装夹具资源表-类型编码 -> 工装夹具类型表-工装夹具类型ID",
        "id": 28209,
        "tableAssociationName": "md_workstation_tool.tool_type_code to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20655
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "工装夹具资源表-类型名称 -> 工装夹具类型表-工装夹具类型ID",
        "id": 28210,
        "tableAssociationName": "md_workstation_tool.tool_type_name to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 20656
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "工装夹具清单表-工装夹具类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28211,
        "tableAssociationName": "tm_tool.tool_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 21687
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "工装夹具清单表-工装夹具类型编码 -> 工装夹具类型表-工装夹具类型ID",
        "id": 28212,
        "tableAssociationName": "tm_tool.tool_type_code to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 21688
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "工装夹具清单表-工装夹具类型名称 -> 工装夹具类型表-工装夹具类型ID",
        "id": 28213,
        "tableAssociationName": "tm_tool.tool_type_name to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 21689
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "库存记录表-物料类型ID -> 工装夹具类型表-工装夹具类型ID",
        "id": 28214,
        "tableAssociationName": "wm_material_stock.item_type_id to tm_tool_type.tool_type_id",
        "masterColumn": {
            "id": 21934
        },
        "slaveColumn": {
            "id": 21706
        }
    },
    {
        "remark": "装箱单表-条码ID -> 条码清单表-条码ID",
        "id": 28215,
        "tableAssociationName": "wm_package.barcode_id to wm_barcode.barcode_id",
        "masterColumn": {
            "id": 21971
        },
        "slaveColumn": {
            "id": 21726
        }
    },
    {
        "remark": "参数配置表-参数主键 -> 条码配置-配置ID",
        "id": 28216,
        "tableAssociationName": "sys_config.config_id to wm_barcode_config.config_id",
        "masterColumn": {
            "id": 21485
        },
        "slaveColumn": {
            "id": 21744
        }
    },
    {
        "remark": "生产领料单行表-领料单ID -> 生产领料单头表-领料单ID",
        "id": 28217,
        "tableAssociationName": "wm_issue_line.issue_id to wm_issue_header.issue_id",
        "masterColumn": {
            "id": 21796
        },
        "slaveColumn": {
            "id": 21761
        }
    },
    {
        "remark": "设备维修单行-行ID -> 生产领料单行表-行ID",
        "id": 28218,
        "tableAssociationName": "dv_repair_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 生产领料单行表-行ID",
        "id": 28219,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 生产领料单行表-行ID",
        "id": 28220,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 生产领料单行表-行ID",
        "id": 28221,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 生产领料单行表-行ID",
        "id": 28222,
        "tableAssociationName": "qc_defect_record.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 生产领料单行表-行ID",
        "id": 28223,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 生产领料单行表-行ID",
        "id": 28224,
        "tableAssociationName": "qc_iqc_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 生产领料单行表-行ID",
        "id": 28225,
        "tableAssociationName": "qc_oqc_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 生产领料单行表-行ID",
        "id": 28226,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 生产领料单行表-行ID",
        "id": 28227,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 生产领料单行表-行ID",
        "id": 28228,
        "tableAssociationName": "wm_package_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 生产领料单行表-行ID",
        "id": 28229,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 生产领料单行表-行ID",
        "id": 28230,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 生产领料单行表-行ID",
        "id": 28231,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 生产领料单行表-行ID",
        "id": 28232,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 生产领料单行表-行ID",
        "id": 28233,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 生产领料单行表-行ID",
        "id": 28234,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 生产领料单行表-行ID",
        "id": 28235,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 生产领料单行表-行ID",
        "id": 28236,
        "tableAssociationName": "wm_transfer_line.line_id to wm_issue_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 21795
        }
    },
    {
        "remark": "计划班组表-流水号 -> 物料消耗记录表-记录ID",
        "id": 28237,
        "tableAssociationName": "cal_plan_team.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "班组排班表-流水号 -> 物料消耗记录表-记录ID",
        "id": 28238,
        "tableAssociationName": "cal_teamshift.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "点检设备表-流水号 -> 物料消耗记录表-记录ID",
        "id": 28239,
        "tableAssociationName": "dv_check_machinery.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "点检项目表-流水号 -> 物料消耗记录表-记录ID",
        "id": 28240,
        "tableAssociationName": "dv_check_subject.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "设备资源表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28241,
        "tableAssociationName": "md_workstation_machine.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28242,
        "tableAssociationName": "md_workstation_tool.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "人力资源表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28243,
        "tableAssociationName": "md_workstation_worker.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28244,
        "tableAssociationName": "pro_feedback.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28245,
        "tableAssociationName": "pro_route_process.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "产品制程-记录ID -> 物料消耗记录表-记录ID",
        "id": 28246,
        "tableAssociationName": "pro_route_product.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28247,
        "tableAssociationName": "pro_route_product_bom.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28248,
        "tableAssociationName": "pro_task_issue.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28249,
        "tableAssociationName": "pro_trans_consume.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 物料消耗记录表-记录ID",
        "id": 28250,
        "tableAssociationName": "pro_user_workstation.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28251,
        "tableAssociationName": "pro_workrecord.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 物料消耗记录表-记录ID",
        "id": 28252,
        "tableAssociationName": "qc_defect_record.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28253,
        "tableAssociationName": "qc_template_index.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 物料消耗记录表-记录ID",
        "id": 28254,
        "tableAssociationName": "qc_template_product.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 物料消耗记录表-记录ID",
        "id": 28255,
        "tableAssociationName": "wm_item_consume_line.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "产品产出记录表-入库单ID -> 物料消耗记录表-记录ID",
        "id": 28256,
        "tableAssociationName": "wm_product_produce.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 22032
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 物料消耗记录表-记录ID",
        "id": 28257,
        "tableAssociationName": "wm_product_produce_line.record_id to wm_item_consume.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 21823
        }
    },
    {
        "remark": "设备维修单行-行ID -> 物料消耗记录行表-行ID",
        "id": 28258,
        "tableAssociationName": "dv_repair_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 物料消耗记录行表-行ID",
        "id": 28259,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 物料消耗记录行表-行ID",
        "id": 28260,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 物料消耗记录行表-行ID",
        "id": 28261,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 物料消耗记录行表-行ID",
        "id": 28262,
        "tableAssociationName": "qc_defect_record.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 物料消耗记录行表-行ID",
        "id": 28263,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 物料消耗记录行表-行ID",
        "id": 28264,
        "tableAssociationName": "qc_iqc_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 物料消耗记录行表-行ID",
        "id": 28265,
        "tableAssociationName": "qc_oqc_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28266,
        "tableAssociationName": "wm_issue_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28267,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 物料消耗记录行表-行ID",
        "id": 28268,
        "tableAssociationName": "wm_package_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28269,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28270,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28271,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28272,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28273,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 物料消耗记录行表-行ID",
        "id": 28274,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 物料消耗记录行表-行ID",
        "id": 28275,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 物料消耗记录行表-行ID",
        "id": 28276,
        "tableAssociationName": "wm_transfer_line.line_id to wm_item_consume_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 21847
        }
    },
    {
        "remark": "物料入库单行表-入库单ID -> 物料入库单表-入库单ID",
        "id": 28277,
        "tableAssociationName": "wm_item_recpt_line.recpt_id to wm_item_recpt.recpt_id",
        "masterColumn": {
            "id": 21906
        },
        "slaveColumn": {
            "id": 21875
        }
    },
    {
        "remark": "产品入库录表-入库单ID -> 物料入库单表-入库单ID",
        "id": 28278,
        "tableAssociationName": "wm_product_recpt.recpt_id to wm_item_recpt.recpt_id",
        "masterColumn": {
            "id": 22083
        },
        "slaveColumn": {
            "id": 21875
        }
    },
    {
        "remark": "产品入库记录表行表-入库记录ID -> 物料入库单表-入库单ID",
        "id": 28279,
        "tableAssociationName": "wm_product_recpt_line.recpt_id to wm_item_recpt.recpt_id",
        "masterColumn": {
            "id": 22115
        },
        "slaveColumn": {
            "id": 21875
        }
    },
    {
        "remark": "设备维修单行-行ID -> 物料入库单行表-行ID",
        "id": 28280,
        "tableAssociationName": "dv_repair_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 物料入库单行表-行ID",
        "id": 28281,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 物料入库单行表-行ID",
        "id": 28282,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 物料入库单行表-行ID",
        "id": 28283,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 物料入库单行表-行ID",
        "id": 28284,
        "tableAssociationName": "qc_defect_record.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 物料入库单行表-行ID",
        "id": 28285,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 物料入库单行表-行ID",
        "id": 28286,
        "tableAssociationName": "qc_iqc_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 物料入库单行表-行ID",
        "id": 28287,
        "tableAssociationName": "qc_oqc_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 物料入库单行表-行ID",
        "id": 28288,
        "tableAssociationName": "wm_issue_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 物料入库单行表-行ID",
        "id": 28289,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 物料入库单行表-行ID",
        "id": 28290,
        "tableAssociationName": "wm_package_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 物料入库单行表-行ID",
        "id": 28291,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 物料入库单行表-行ID",
        "id": 28292,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 物料入库单行表-行ID",
        "id": 28293,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 物料入库单行表-行ID",
        "id": 28294,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 物料入库单行表-行ID",
        "id": 28295,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 物料入库单行表-行ID",
        "id": 28296,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 物料入库单行表-行ID",
        "id": 28297,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 物料入库单行表-行ID",
        "id": 28298,
        "tableAssociationName": "wm_transfer_line.line_id to wm_item_recpt_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 21905
        }
    },
    {
        "remark": "生产领料单行表-库存ID -> 库存记录表-事务ID",
        "id": 28299,
        "tableAssociationName": "wm_issue_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 21797
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "物料消耗记录行表-库存ID -> 库存记录表-事务ID",
        "id": 28300,
        "tableAssociationName": "wm_item_consume_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 21849
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "装箱明细表-库存记录ID -> 库存记录表-事务ID",
        "id": 28301,
        "tableAssociationName": "wm_package_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22003
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "产品入库记录表行表-库存记录ID -> 库存记录表-事务ID",
        "id": 28302,
        "tableAssociationName": "wm_product_recpt_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22116
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "产品销售出库行表-库存记录ID -> 库存记录表-事务ID",
        "id": 28303,
        "tableAssociationName": "wm_product_salse_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22178
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "生产退料单行表-库存ID -> 库存记录表-事务ID",
        "id": 28304,
        "tableAssociationName": "wm_rt_issue_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22231
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "供应商退货行表-库存记录ID -> 库存记录表-事务ID",
        "id": 28305,
        "tableAssociationName": "wm_rt_vendor_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22336
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "库存事务表-库存记录ID -> 库存记录表-事务ID",
        "id": 28306,
        "tableAssociationName": "wm_transaction.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22438
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "转移单行表-库存记录ID -> 库存记录表-事务ID",
        "id": 28307,
        "tableAssociationName": "wm_transfer_line.material_stock_id to wm_material_stock.material_stock_id",
        "masterColumn": {
            "id": 22484
        },
        "slaveColumn": {
            "id": 21933
        }
    },
    {
        "remark": "装箱明细表-装箱单ID -> 装箱单表-装箱单ID",
        "id": 28308,
        "tableAssociationName": "wm_package_line.package_id to wm_package.package_id",
        "masterColumn": {
            "id": 22002
        },
        "slaveColumn": {
            "id": 21967
        }
    },
    {
        "remark": "设备维修单行-行ID -> 装箱明细表-明细行ID",
        "id": 28309,
        "tableAssociationName": "dv_repair_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 装箱明细表-明细行ID",
        "id": 28310,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 装箱明细表-明细行ID",
        "id": 28311,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 装箱明细表-明细行ID",
        "id": 28312,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 装箱明细表-明细行ID",
        "id": 28313,
        "tableAssociationName": "qc_defect_record.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 装箱明细表-明细行ID",
        "id": 28314,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 装箱明细表-明细行ID",
        "id": 28315,
        "tableAssociationName": "qc_iqc_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 装箱明细表-明细行ID",
        "id": 28316,
        "tableAssociationName": "qc_oqc_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 装箱明细表-明细行ID",
        "id": 28317,
        "tableAssociationName": "wm_issue_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 装箱明细表-明细行ID",
        "id": 28318,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 装箱明细表-明细行ID",
        "id": 28319,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 装箱明细表-明细行ID",
        "id": 28320,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 装箱明细表-明细行ID",
        "id": 28321,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 装箱明细表-明细行ID",
        "id": 28322,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 装箱明细表-明细行ID",
        "id": 28323,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 装箱明细表-明细行ID",
        "id": 28324,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 装箱明细表-明细行ID",
        "id": 28325,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 装箱明细表-明细行ID",
        "id": 28326,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 装箱明细表-明细行ID",
        "id": 28327,
        "tableAssociationName": "wm_transfer_line.line_id to wm_package_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22001
        }
    },
    {
        "remark": "计划班组表-流水号 -> 产品产出记录表-入库单ID",
        "id": 28328,
        "tableAssociationName": "cal_plan_team.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20176
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "班组排班表-流水号 -> 产品产出记录表-入库单ID",
        "id": 28329,
        "tableAssociationName": "cal_teamshift.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20233
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "点检设备表-流水号 -> 产品产出记录表-入库单ID",
        "id": 28330,
        "tableAssociationName": "dv_check_machinery.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20252
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "点检项目表-流水号 -> 产品产出记录表-入库单ID",
        "id": 28331,
        "tableAssociationName": "dv_check_subject.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20286
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "设备资源表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28332,
        "tableAssociationName": "md_workstation_machine.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20637
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "工装夹具资源表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28333,
        "tableAssociationName": "md_workstation_tool.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20652
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "人力资源表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28334,
        "tableAssociationName": "md_workstation_worker.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20667
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "生产报工记录表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28335,
        "tableAssociationName": "pro_feedback.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20682
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "工艺组成表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28336,
        "tableAssociationName": "pro_route_process.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20764
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "产品制程-记录ID -> 产品产出记录表-入库单ID",
        "id": 28337,
        "tableAssociationName": "pro_route_product.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20787
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "产品制程物料BOM表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28338,
        "tableAssociationName": "pro_route_product_bom.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20806
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "生产任务投料表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28339,
        "tableAssociationName": "pro_task_issue.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20866
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28340,
        "tableAssociationName": "pro_trans_consume.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20891
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "用户工作站绑定关系-记录ID -> 产品产出记录表-入库单ID",
        "id": 28341,
        "tableAssociationName": "pro_user_workstation.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 20951
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "上下工记录表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28342,
        "tableAssociationName": "pro_workrecord.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 21017
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "检验单缺陷记录表-缺陷ID -> 产品产出记录表-入库单ID",
        "id": 28343,
        "tableAssociationName": "qc_defect_record.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 21049
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "检测模板-检测项表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28344,
        "tableAssociationName": "qc_template_index.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 21287
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "检测模板-产品表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28345,
        "tableAssociationName": "qc_template_product.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 21309
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "物料消耗记录表-记录ID -> 产品产出记录表-入库单ID",
        "id": 28346,
        "tableAssociationName": "wm_item_consume.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 21823
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "物料消耗记录行表-消耗记录ID -> 产品产出记录表-入库单ID",
        "id": 28347,
        "tableAssociationName": "wm_item_consume_line.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 21848
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "产品产出记录表行表-产出记录ID -> 产品产出记录表-入库单ID",
        "id": 28348,
        "tableAssociationName": "wm_product_produce_line.record_id to wm_product_produce.record_id",
        "masterColumn": {
            "id": 22057
        },
        "slaveColumn": {
            "id": 22032
        }
    },
    {
        "remark": "设备维修单行-行ID -> 产品产出记录表行表-行ID",
        "id": 28349,
        "tableAssociationName": "dv_repair_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 产品产出记录表行表-行ID",
        "id": 28350,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 产品产出记录表行表-行ID",
        "id": 28351,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 产品产出记录表行表-行ID",
        "id": 28352,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 产品产出记录表行表-行ID",
        "id": 28353,
        "tableAssociationName": "qc_defect_record.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 产品产出记录表行表-行ID",
        "id": 28354,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 产品产出记录表行表-行ID",
        "id": 28355,
        "tableAssociationName": "qc_iqc_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 产品产出记录表行表-行ID",
        "id": 28356,
        "tableAssociationName": "qc_oqc_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28357,
        "tableAssociationName": "wm_issue_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28358,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28359,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 产品产出记录表行表-行ID",
        "id": 28360,
        "tableAssociationName": "wm_package_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28361,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28362,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28363,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28364,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 产品产出记录表行表-行ID",
        "id": 28365,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 产品产出记录表行表-行ID",
        "id": 28366,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 产品产出记录表行表-行ID",
        "id": 28367,
        "tableAssociationName": "wm_transfer_line.line_id to wm_product_produce_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22056
        }
    },
    {
        "remark": "物料入库单表-入库单ID -> 产品入库录表-入库单ID",
        "id": 28368,
        "tableAssociationName": "wm_item_recpt.recpt_id to wm_product_recpt.recpt_id",
        "masterColumn": {
            "id": 21875
        },
        "slaveColumn": {
            "id": 22083
        }
    },
    {
        "remark": "物料入库单行表-入库单ID -> 产品入库录表-入库单ID",
        "id": 28369,
        "tableAssociationName": "wm_item_recpt_line.recpt_id to wm_product_recpt.recpt_id",
        "masterColumn": {
            "id": 21906
        },
        "slaveColumn": {
            "id": 22083
        }
    },
    {
        "remark": "产品入库记录表行表-入库记录ID -> 产品入库录表-入库单ID",
        "id": 28370,
        "tableAssociationName": "wm_product_recpt_line.recpt_id to wm_product_recpt.recpt_id",
        "masterColumn": {
            "id": 22115
        },
        "slaveColumn": {
            "id": 22083
        }
    },
    {
        "remark": "设备维修单行-行ID -> 产品入库记录表行表-行ID",
        "id": 28371,
        "tableAssociationName": "dv_repair_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 产品入库记录表行表-行ID",
        "id": 28372,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 产品入库记录表行表-行ID",
        "id": 28373,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 产品入库记录表行表-行ID",
        "id": 28374,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 产品入库记录表行表-行ID",
        "id": 28375,
        "tableAssociationName": "qc_defect_record.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 产品入库记录表行表-行ID",
        "id": 28376,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 产品入库记录表行表-行ID",
        "id": 28377,
        "tableAssociationName": "qc_iqc_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 产品入库记录表行表-行ID",
        "id": 28378,
        "tableAssociationName": "qc_oqc_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28379,
        "tableAssociationName": "wm_issue_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28380,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28381,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 产品入库记录表行表-行ID",
        "id": 28382,
        "tableAssociationName": "wm_package_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28383,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28384,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28385,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28386,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 产品入库记录表行表-行ID",
        "id": 28387,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 产品入库记录表行表-行ID",
        "id": 28388,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 产品入库记录表行表-行ID",
        "id": 28389,
        "tableAssociationName": "wm_transfer_line.line_id to wm_product_recpt_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22114
        }
    },
    {
        "remark": "产品销售出库行表-出库记录ID -> 销售出库单表-出库单ID",
        "id": 28390,
        "tableAssociationName": "wm_product_salse_line.salse_id to wm_product_salse.salse_id",
        "masterColumn": {
            "id": 22177
        },
        "slaveColumn": {
            "id": 22146
        }
    },
    {
        "remark": "设备维修单行-行ID -> 产品销售出库行表-行ID",
        "id": 28391,
        "tableAssociationName": "dv_repair_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 产品销售出库行表-行ID",
        "id": 28392,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 产品销售出库行表-行ID",
        "id": 28393,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 产品销售出库行表-行ID",
        "id": 28394,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 产品销售出库行表-行ID",
        "id": 28395,
        "tableAssociationName": "qc_defect_record.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 产品销售出库行表-行ID",
        "id": 28396,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 产品销售出库行表-行ID",
        "id": 28397,
        "tableAssociationName": "qc_iqc_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 产品销售出库行表-行ID",
        "id": 28398,
        "tableAssociationName": "qc_oqc_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 产品销售出库行表-行ID",
        "id": 28399,
        "tableAssociationName": "wm_issue_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 产品销售出库行表-行ID",
        "id": 28400,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 产品销售出库行表-行ID",
        "id": 28401,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 产品销售出库行表-行ID",
        "id": 28402,
        "tableAssociationName": "wm_package_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 产品销售出库行表-行ID",
        "id": 28403,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 产品销售出库行表-行ID",
        "id": 28404,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 产品销售出库行表-行ID",
        "id": 28405,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 产品销售出库行表-行ID",
        "id": 28406,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 产品销售出库行表-行ID",
        "id": 28407,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 产品销售出库行表-行ID",
        "id": 28408,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 产品销售出库行表-行ID",
        "id": 28409,
        "tableAssociationName": "wm_transfer_line.line_id to wm_product_salse_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22176
        }
    },
    {
        "remark": "生产退料单行表-退料单ID -> 生产退料单头表-退料单ID",
        "id": 28410,
        "tableAssociationName": "wm_rt_issue_line.rt_id to wm_rt_issue.rt_id",
        "masterColumn": {
            "id": 22230
        },
        "slaveColumn": {
            "id": 22204
        }
    },
    {
        "remark": "产品销售退货单表-退货单ID -> 生产退料单头表-退料单ID",
        "id": 28411,
        "tableAssociationName": "wm_rt_salse.rt_id to wm_rt_issue.rt_id",
        "masterColumn": {
            "id": 22257
        },
        "slaveColumn": {
            "id": 22204
        }
    },
    {
        "remark": "产品销售退货行表-退货单ID -> 生产退料单头表-退料单ID",
        "id": 28412,
        "tableAssociationName": "wm_rt_salse_line.rt_id to wm_rt_issue.rt_id",
        "masterColumn": {
            "id": 22287
        },
        "slaveColumn": {
            "id": 22204
        }
    },
    {
        "remark": "供应商退货表-退货单ID -> 生产退料单头表-退料单ID",
        "id": 28413,
        "tableAssociationName": "wm_rt_vendor.rt_id to wm_rt_issue.rt_id",
        "masterColumn": {
            "id": 22314
        },
        "slaveColumn": {
            "id": 22204
        }
    },
    {
        "remark": "供应商退货行表-退货单ID -> 生产退料单头表-退料单ID",
        "id": 28414,
        "tableAssociationName": "wm_rt_vendor_line.rt_id to wm_rt_issue.rt_id",
        "masterColumn": {
            "id": 22335
        },
        "slaveColumn": {
            "id": 22204
        }
    },
    {
        "remark": "设备维修单行-行ID -> 生产退料单行表-行ID",
        "id": 28415,
        "tableAssociationName": "dv_repair_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 生产退料单行表-行ID",
        "id": 28416,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 生产退料单行表-行ID",
        "id": 28417,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 生产退料单行表-行ID",
        "id": 28418,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 生产退料单行表-行ID",
        "id": 28419,
        "tableAssociationName": "qc_defect_record.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 生产退料单行表-行ID",
        "id": 28420,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 生产退料单行表-行ID",
        "id": 28421,
        "tableAssociationName": "qc_iqc_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 生产退料单行表-行ID",
        "id": 28422,
        "tableAssociationName": "qc_oqc_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 生产退料单行表-行ID",
        "id": 28423,
        "tableAssociationName": "wm_issue_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 生产退料单行表-行ID",
        "id": 28424,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 生产退料单行表-行ID",
        "id": 28425,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 生产退料单行表-行ID",
        "id": 28426,
        "tableAssociationName": "wm_package_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 生产退料单行表-行ID",
        "id": 28427,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 生产退料单行表-行ID",
        "id": 28428,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 生产退料单行表-行ID",
        "id": 28429,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 生产退料单行表-行ID",
        "id": 28430,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 生产退料单行表-行ID",
        "id": 28431,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 生产退料单行表-行ID",
        "id": 28432,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 生产退料单行表-行ID",
        "id": 28433,
        "tableAssociationName": "wm_transfer_line.line_id to wm_rt_issue_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22229
        }
    },
    {
        "remark": "生产退料单头表-退料单ID -> 产品销售退货单表-退货单ID",
        "id": 28434,
        "tableAssociationName": "wm_rt_issue.rt_id to wm_rt_salse.rt_id",
        "masterColumn": {
            "id": 22204
        },
        "slaveColumn": {
            "id": 22257
        }
    },
    {
        "remark": "生产退料单行表-退料单ID -> 产品销售退货单表-退货单ID",
        "id": 28435,
        "tableAssociationName": "wm_rt_issue_line.rt_id to wm_rt_salse.rt_id",
        "masterColumn": {
            "id": 22230
        },
        "slaveColumn": {
            "id": 22257
        }
    },
    {
        "remark": "产品销售退货行表-退货单ID -> 产品销售退货单表-退货单ID",
        "id": 28436,
        "tableAssociationName": "wm_rt_salse_line.rt_id to wm_rt_salse.rt_id",
        "masterColumn": {
            "id": 22287
        },
        "slaveColumn": {
            "id": 22257
        }
    },
    {
        "remark": "供应商退货表-退货单ID -> 产品销售退货单表-退货单ID",
        "id": 28437,
        "tableAssociationName": "wm_rt_vendor.rt_id to wm_rt_salse.rt_id",
        "masterColumn": {
            "id": 22314
        },
        "slaveColumn": {
            "id": 22257
        }
    },
    {
        "remark": "供应商退货行表-退货单ID -> 产品销售退货单表-退货单ID",
        "id": 28438,
        "tableAssociationName": "wm_rt_vendor_line.rt_id to wm_rt_salse.rt_id",
        "masterColumn": {
            "id": 22335
        },
        "slaveColumn": {
            "id": 22257
        }
    },
    {
        "remark": "设备维修单行-行ID -> 产品销售退货行表-行ID",
        "id": 28439,
        "tableAssociationName": "dv_repair_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 产品销售退货行表-行ID",
        "id": 28440,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 产品销售退货行��-行ID",
        "id": 28441,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 产品销售退货行表-行ID",
        "id": 28442,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 产品销售退货行表-行ID",
        "id": 28443,
        "tableAssociationName": "qc_defect_record.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 产品销售退货行表-行ID",
        "id": 28444,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 产品销售退货行表-行ID",
        "id": 28445,
        "tableAssociationName": "qc_iqc_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 产品销售退货行表-行ID",
        "id": 28446,
        "tableAssociationName": "qc_oqc_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 产品销售退货行表-行ID",
        "id": 28447,
        "tableAssociationName": "wm_issue_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 产品销售退货行表-行ID",
        "id": 28448,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 产品销售退货行表-行ID",
        "id": 28449,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 产品销售退货行表-行ID",
        "id": 28450,
        "tableAssociationName": "wm_package_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 产品销售退货行表-行ID",
        "id": 28451,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 产品销售退货行表-行ID",
        "id": 28452,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 产品销售退货行表-行ID",
        "id": 28453,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 产品销售退货行表-行ID",
        "id": 28454,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 产品销售退货行表-行ID",
        "id": 28455,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 产品销售退货行表-行ID",
        "id": 28456,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 产品销售退货行表-行ID",
        "id": 28457,
        "tableAssociationName": "wm_transfer_line.line_id to wm_rt_salse_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22286
        }
    },
    {
        "remark": "生产退料单头表-退料单ID -> 供应商退货表-退货单ID",
        "id": 28458,
        "tableAssociationName": "wm_rt_issue.rt_id to wm_rt_vendor.rt_id",
        "masterColumn": {
            "id": 22204
        },
        "slaveColumn": {
            "id": 22314
        }
    },
    {
        "remark": "生产退料单行表-退料单ID -> 供应商退货表-退货单ID",
        "id": 28459,
        "tableAssociationName": "wm_rt_issue_line.rt_id to wm_rt_vendor.rt_id",
        "masterColumn": {
            "id": 22230
        },
        "slaveColumn": {
            "id": 22314
        }
    },
    {
        "remark": "产品销售退货单表-退货单ID -> 供应商退货表-退货单ID",
        "id": 28460,
        "tableAssociationName": "wm_rt_salse.rt_id to wm_rt_vendor.rt_id",
        "masterColumn": {
            "id": 22257
        },
        "slaveColumn": {
            "id": 22314
        }
    },
    {
        "remark": "产品销售退货行表-退货单ID -> 供应商退货表-退货单ID",
        "id": 28461,
        "tableAssociationName": "wm_rt_salse_line.rt_id to wm_rt_vendor.rt_id",
        "masterColumn": {
            "id": 22287
        },
        "slaveColumn": {
            "id": 22314
        }
    },
    {
        "remark": "供应商退货行表-退货单ID -> 供应商退货表-退货单ID",
        "id": 28462,
        "tableAssociationName": "wm_rt_vendor_line.rt_id to wm_rt_vendor.rt_id",
        "masterColumn": {
            "id": 22335
        },
        "slaveColumn": {
            "id": 22314
        }
    },
    {
        "remark": "设备维修单行-行ID -> 供应商退货行表-行ID",
        "id": 28463,
        "tableAssociationName": "dv_repair_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 供应商退货行表-行ID",
        "id": 28464,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 供应商退货行表-行ID",
        "id": 28465,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 供应商退货行表-行ID",
        "id": 28466,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 供应商退货行表-行ID",
        "id": 28467,
        "tableAssociationName": "qc_defect_record.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 供应商退货行表-行ID",
        "id": 28468,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 供应商退货行表-行ID",
        "id": 28469,
        "tableAssociationName": "qc_iqc_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 供应商退货行表-行ID",
        "id": 28470,
        "tableAssociationName": "qc_oqc_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 供应商退货行表-行ID",
        "id": 28471,
        "tableAssociationName": "wm_issue_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 供应商退货行表-行ID",
        "id": 28472,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 供应商退货行表-行ID",
        "id": 28473,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 供应商退货行表-行ID",
        "id": 28474,
        "tableAssociationName": "wm_package_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 供应商退货行表-行ID",
        "id": 28475,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 供应商退货行表-行ID",
        "id": 28476,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 供应商退货行表-行ID",
        "id": 28477,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 供应商退货行表-行ID",
        "id": 28478,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 供应商退货行表-行ID",
        "id": 28479,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 供应商退货行表-行ID",
        "id": 28480,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "转移单行表-明细行ID -> 供应商退货行表-行ID",
        "id": 28481,
        "tableAssociationName": "wm_transfer_line.line_id to wm_rt_vendor_line.line_id",
        "masterColumn": {
            "id": 22482
        },
        "slaveColumn": {
            "id": 22334
        }
    },
    {
        "remark": "工作站表-库位ID -> 库位表-库位ID",
        "id": 28482,
        "tableAssociationName": "md_workstation.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 20624
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "生产领料单头表-库位ID -> 库位表-库位ID",
        "id": 28483,
        "tableAssociationName": "wm_issue_header.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 21781
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "生产领料单行表-库位ID -> 库位表-库位ID",
        "id": 28484,
        "tableAssociationName": "wm_issue_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 21811
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "物料消耗记录行表-库位ID -> 库位表-库位ID",
        "id": 28485,
        "tableAssociationName": "wm_item_consume_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 21863
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "物料入库单表-库位ID -> 库位表-库位ID",
        "id": 28486,
        "tableAssociationName": "wm_item_recpt.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 21891
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "物料入库单行表-库位ID -> 库位表-库位ID",
        "id": 28487,
        "tableAssociationName": "wm_item_recpt_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 21920
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "库存记录表-库位ID -> 库位表-库位ID",
        "id": 28488,
        "tableAssociationName": "wm_material_stock.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 21947
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "装箱明细表-库位ID -> 库位表-库位ID",
        "id": 28489,
        "tableAssociationName": "wm_package_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22019
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "产品产出记录表行表-库位ID -> 库位表-库位ID",
        "id": 28490,
        "tableAssociationName": "wm_product_produce_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22071
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "产品入库录表-库位ID -> 库位表-库位ID",
        "id": 28491,
        "tableAssociationName": "wm_product_recpt.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22100
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "产品入库记录表行表-库位ID -> 库位表-库位ID",
        "id": 28492,
        "tableAssociationName": "wm_product_recpt_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22133
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "销售出库单表-库位ID -> 库位表-库位ID",
        "id": 28493,
        "tableAssociationName": "wm_product_salse.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22162
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "产品销售出库行表-库位ID -> 库位表-库位ID",
        "id": 28494,
        "tableAssociationName": "wm_product_salse_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22192
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "生产退料单头表-库位ID -> 库位表-库位ID",
        "id": 28495,
        "tableAssociationName": "wm_rt_issue.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22215
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "生产退料单行表-库位ID -> 库位表-库位ID",
        "id": 28496,
        "tableAssociationName": "wm_rt_issue_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22245
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "产品销售退货单表-库位ID -> 库位表-库位ID",
        "id": 28497,
        "tableAssociationName": "wm_rt_salse.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22271
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "产品销售退货行表-库位ID -> 库位表-库位ID",
        "id": 28498,
        "tableAssociationName": "wm_rt_salse_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22301
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "供应商退货行表-库位ID -> 库位表-库位ID",
        "id": 28499,
        "tableAssociationName": "wm_rt_vendor_line.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22350
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "库存事务表-库位ID -> 库位表-库位ID",
        "id": 28500,
        "tableAssociationName": "wm_transaction.area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22427
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "转移单行表-移出库位ID -> 库位表-库位ID",
        "id": 28501,
        "tableAssociationName": "wm_transfer_line.from_area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22500
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "转移单行表-移入库位ID -> 库位表-库位ID",
        "id": 28502,
        "tableAssociationName": "wm_transfer_line.to_area_id to wm_storage_area.area_id",
        "masterColumn": {
            "id": 22509
        },
        "slaveColumn": {
            "id": 22379
        }
    },
    {
        "remark": "工作站表-库区ID -> 库区表-库区ID",
        "id": 28503,
        "tableAssociationName": "md_workstation.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 20621
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "生产领料单头表-库区ID -> 库区表-库区ID",
        "id": 28504,
        "tableAssociationName": "wm_issue_header.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 21778
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "生产领料单行表-库区ID -> 库区表-库区ID",
        "id": 28505,
        "tableAssociationName": "wm_issue_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 21808
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "物料消耗记录行表-库区ID -> 库区表-库区ID",
        "id": 28506,
        "tableAssociationName": "wm_item_consume_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 21860
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "物料入库单表-库区ID -> 库区表-库区ID",
        "id": 28507,
        "tableAssociationName": "wm_item_recpt.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 21888
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "物料入库单行表-库区ID -> 库区表-库区ID",
        "id": 28508,
        "tableAssociationName": "wm_item_recpt_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 21917
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "库存记录表-库区ID -> 库区表-库区ID",
        "id": 28509,
        "tableAssociationName": "wm_material_stock.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 21944
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "装箱明细表-库区ID -> 库区表-库区ID",
        "id": 28510,
        "tableAssociationName": "wm_package_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22016
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "产品产出记录表行表-库区ID -> 库区表-库区ID",
        "id": 28511,
        "tableAssociationName": "wm_product_produce_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22068
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "产品入库录表-库区ID -> 库区表-库区ID",
        "id": 28512,
        "tableAssociationName": "wm_product_recpt.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22097
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "产品入库记录表行表-库区ID -> 库区表-库区ID",
        "id": 28513,
        "tableAssociationName": "wm_product_recpt_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22130
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "销售出库单表-库区ID -> 库区表-库区ID",
        "id": 28514,
        "tableAssociationName": "wm_product_salse.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22159
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "产品销售出库行表-库区ID -> 库区表-库区ID",
        "id": 28515,
        "tableAssociationName": "wm_product_salse_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22189
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "生产退料单头表-库区ID -> 库区表-库区ID",
        "id": 28516,
        "tableAssociationName": "wm_rt_issue.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22212
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "生产退料单行表-库区ID -> 库区表-库区ID",
        "id": 28517,
        "tableAssociationName": "wm_rt_issue_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22242
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "产品销售退货单表-库区ID -> 库区表-库区ID",
        "id": 28518,
        "tableAssociationName": "wm_rt_salse.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22268
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "产品销售退货行表-库区ID -> 库区表-库区ID",
        "id": 28519,
        "tableAssociationName": "wm_rt_salse_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22298
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "供应商退货行表-库区ID -> 库区表-库区ID",
        "id": 28520,
        "tableAssociationName": "wm_rt_vendor_line.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22347
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "库位表-库区ID -> 库区表-库区ID",
        "id": 28521,
        "tableAssociationName": "wm_storage_area.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22382
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "库存事务表-库区ID -> 库区表-库区ID",
        "id": 28522,
        "tableAssociationName": "wm_transaction.location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22424
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "转移单行表-移出库区ID -> 库区表-库区ID",
        "id": 28523,
        "tableAssociationName": "wm_transfer_line.from_location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22497
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "转移单行表-移入库区ID -> 库区表-库区ID",
        "id": 28524,
        "tableAssociationName": "wm_transfer_line.to_location_id to wm_storage_location.location_id",
        "masterColumn": {
            "id": 22506
        },
        "slaveColumn": {
            "id": 22398
        }
    },
    {
        "remark": "转移单行表-装箱单ID -> 转移单表-转移单ID",
        "id": 28525,
        "tableAssociationName": "wm_transfer_line.transfer_id to wm_transfer.transfer_id",
        "masterColumn": {
            "id": 22483
        },
        "slaveColumn": {
            "id": 22456
        }
    },
    {
        "remark": "设备维修单行-行ID -> 转移单行表-明细行ID",
        "id": 28526,
        "tableAssociationName": "dv_repair_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 20364
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "生产任务投料表-行ID -> 转移单行表-明细行ID",
        "id": 28527,
        "tableAssociationName": "pro_task_issue.source_line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 20873
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "物料消耗记录表-被消耗单据行ID -> 转移单行表-明细行ID",
        "id": 28528,
        "tableAssociationName": "pro_trans_consume.source_line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 20902
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "生产工单BOM组成表-行ID -> 转移单行表-明细行ID",
        "id": 28529,
        "tableAssociationName": "pro_workorder_bom.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 20999
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "检验单缺陷记录表-检验单行ID -> 转移单行表-明细行ID",
        "id": 28530,
        "tableAssociationName": "qc_defect_record.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21052
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "过程检验单行表-记录ID -> 转移单行表-明细行ID",
        "id": 28531,
        "tableAssociationName": "qc_ipqc_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21123
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "来料检验单行表-记录ID -> 转移单行表-明细行ID",
        "id": 28532,
        "tableAssociationName": "qc_iqc_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21186
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "出货检验单行表-记录ID -> 转移单行表-明细行ID",
        "id": 28533,
        "tableAssociationName": "qc_oqc_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21249
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "生产领料单行表-行ID -> 转移单行表-明细行ID",
        "id": 28534,
        "tableAssociationName": "wm_issue_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21795
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "物料消耗记录行表-行ID -> 转移单行表-明细行ID",
        "id": 28535,
        "tableAssociationName": "wm_item_consume_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21847
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "物料入库单行表-行ID -> 转移单行表-明细行ID",
        "id": 28536,
        "tableAssociationName": "wm_item_recpt_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 21905
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "装箱明细表-明细行ID -> 转移单行表-明细行ID",
        "id": 28537,
        "tableAssociationName": "wm_package_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22001
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "产品产出记录表行表-行ID -> 转移单行表-明细行ID",
        "id": 28538,
        "tableAssociationName": "wm_product_produce_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22056
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "产品入库记录表行表-行ID -> 转移单行表-明细行ID",
        "id": 28539,
        "tableAssociationName": "wm_product_recpt_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22114
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "产品销售出库行表-行ID -> 转移单行表-明细行ID",
        "id": 28540,
        "tableAssociationName": "wm_product_salse_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22176
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "生产退料单行表-行ID -> 转移单行表-明细行ID",
        "id": 28541,
        "tableAssociationName": "wm_rt_issue_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22229
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "产品销售退货行表-行ID -> 转移单行表-明细行ID",
        "id": 28542,
        "tableAssociationName": "wm_rt_salse_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22286
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "供应商退货行表-行ID -> 转移单行表-明细行ID",
        "id": 28543,
        "tableAssociationName": "wm_rt_vendor_line.line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22334
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "库存事务表-单据行ID -> 转移单行表-明细行ID",
        "id": 28544,
        "tableAssociationName": "wm_transaction.source_doc_line_id to wm_transfer_line.line_id",
        "masterColumn": {
            "id": 22437
        },
        "slaveColumn": {
            "id": 22482
        }
    },
    {
        "remark": "工作站表-线边库ID -> 仓库表-仓库ID",
        "id": 28545,
        "tableAssociationName": "md_workstation.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 20618
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "生产领料单头表-仓库ID -> 仓库表-仓库ID",
        "id": 28546,
        "tableAssociationName": "wm_issue_header.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 21775
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "生产领料单行表-仓库ID -> 仓库表-仓库ID",
        "id": 28547,
        "tableAssociationName": "wm_issue_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 21805
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "物料消耗记录行表-仓库ID -> 仓库表-仓库ID",
        "id": 28548,
        "tableAssociationName": "wm_item_consume_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 21857
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "物料入库单表-仓库ID -> 仓库表-仓库ID",
        "id": 28549,
        "tableAssociationName": "wm_item_recpt.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 21885
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "物料入库单行表-仓库ID -> 仓库表-仓库ID",
        "id": 28550,
        "tableAssociationName": "wm_item_recpt_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 21914
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "库存记录表-仓库ID -> 仓库表-仓库ID",
        "id": 28551,
        "tableAssociationName": "wm_material_stock.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 21941
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "装箱明细表-仓库ID -> 仓库表-仓库ID",
        "id": 28552,
        "tableAssociationName": "wm_package_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22013
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "产品产出记录表行表-仓库ID -> 仓库表-仓库ID",
        "id": 28553,
        "tableAssociationName": "wm_product_produce_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22065
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "产品入库录表-仓库ID -> 仓库表-仓库ID",
        "id": 28554,
        "tableAssociationName": "wm_product_recpt.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22094
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "产品入库记录表行表-仓库ID -> 仓库表-仓库ID",
        "id": 28555,
        "tableAssociationName": "wm_product_recpt_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22127
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "销售出库单表-仓库ID -> 仓库表-仓库ID",
        "id": 28556,
        "tableAssociationName": "wm_product_salse.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22156
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "产品销售出库行表-仓库ID -> 仓库表-仓库ID",
        "id": 28557,
        "tableAssociationName": "wm_product_salse_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22186
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "生产退料单头表-仓库ID -> 仓库表-仓库ID",
        "id": 28558,
        "tableAssociationName": "wm_rt_issue.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22209
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "生产退料单行表-仓库ID -> 仓库表-仓库ID",
        "id": 28559,
        "tableAssociationName": "wm_rt_issue_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22239
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "产品销售退货单表-仓库ID -> 仓库表-仓库ID",
        "id": 28560,
        "tableAssociationName": "wm_rt_salse.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22265
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "产品销售退货行表-仓库ID -> 仓库表-仓库ID",
        "id": 28561,
        "tableAssociationName": "wm_rt_salse_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22295
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "供应商退货行表-仓库ID -> 仓库表-仓库ID",
        "id": 28562,
        "tableAssociationName": "wm_rt_vendor_line.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22344
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "库区表-仓库ID -> 仓库表-仓库ID",
        "id": 28563,
        "tableAssociationName": "wm_storage_location.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22401
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "库存事务表-仓库ID -> 仓库表-仓库ID",
        "id": 28564,
        "tableAssociationName": "wm_transaction.warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22421
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "转移单表-移出仓库ID -> 仓库表-仓库ID",
        "id": 28565,
        "tableAssociationName": "wm_transfer.from_warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22465
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "转移单表-移入仓库ID -> 仓库表-仓库ID",
        "id": 28566,
        "tableAssociationName": "wm_transfer.to_warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22468
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "转移单行表-移出仓库ID -> 仓库表-仓库ID",
        "id": 28567,
        "tableAssociationName": "wm_transfer_line.from_warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22494
        },
        "slaveColumn": {
            "id": 22526
        }
    },
    {
        "remark": "转移单行表-移入仓库ID -> 仓库表-仓库ID",
        "id": 28568,
        "tableAssociationName": "wm_transfer_line.to_warehouse_id to wm_warehouse.warehouse_id",
        "masterColumn": {
            "id": 22503
        },
        "slaveColumn": {
            "id": 22526
        }
    }
]