/**
 * 兼职详情管理初始化
 */
var PartTimeDetail = {
    id: "PartTimeDetailTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PartTimeDetail.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '兼职标题', field: 'partTimeTitle', visible: true, align: 'center', valign: 'middle'},
            {title: '兼职内容', field: 'partTimeContent', visible: true, align: 'center', valign: 'middle'},
            {title: '兼职类型', field: 'partTimeType', visible: true, align: 'center', valign: 'middle'},
            {title: '兼职招聘人数', field: 'recruiterNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '工作种类 0 短招 1长招', field: 'workType', visible: true, align: 'center', valign: 'middle'},
            {title: '工作开始时间', field: 'workStartTime', visible: true, align: 'center', valign: 'middle'},
            {title: '工作结束时间', field: 'workEndTime', visible: true, align: 'center', valign: 'middle'},
            {title: '工作时间范围，一天可能有多个非连续时间段，用‘ ，’分割', field: 'workTimeRange', visible: true, align: 'center', valign: 'middle'},
            {title: '性别要求（1：男 2：女 3: 男+女）', field: 'genderRequirement', visible: true, align: 'center', valign: 'middle'},
            {title: '工作福利，每项福利用逗号分隔', field: 'workWelfare', visible: true, align: 'center', valign: 'middle'},
            {title: '工作地点', field: 'workPlace', visible: true, align: 'center', valign: 'middle'},
            {title: '结算周期 1.日结 2.周结 3.月结 4.完工结', field: 'settlementCycle', visible: true, align: 'center', valign: 'middle'},
            {title: '发布兼职者的个人邮箱', field: 'publishEmail', visible: true, align: 'center', valign: 'middle'},
            {title: '发布兼职者的电话号码', field: 'publishPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '数据创建时间', field: 'gmtCreate', visible: true, align: 'center', valign: 'middle'},
            {title: '数据修改时间', field: 'gmtModified', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PartTimeDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PartTimeDetail.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加兼职详情
 */
PartTimeDetail.openAddPartTimeDetail = function () {
    var index = layer.open({
        type: 2,
        title: '添加兼职详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/spark/partTimeDetail/partTimeDetail_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看兼职详情详情
 */
PartTimeDetail.openPartTimeDetailDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '兼职详情详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/spark/partTimeDetail/partTimeDetail_update/' + PartTimeDetail.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除兼职详情
 */
PartTimeDetail.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/spark/partTimeDetail/delete", function (data) {
            Feng.success("删除成功!");
            PartTimeDetail.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("partTimeDetailId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询兼职详情列表
 */
PartTimeDetail.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    PartTimeDetail.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PartTimeDetail.initColumn();
    var table = new BSTable(PartTimeDetail.id, "/spark/partTimeDetail/list", defaultColunms);
    table.setPaginationType("client");
    PartTimeDetail.table = table.init();
});
