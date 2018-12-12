/**
 * 初始化兼职详情详情对话框
 */
var PartTimeDetailInfoDlg = {
    partTimeDetailInfoData : {}
};

/**
 * 清除数据
 */
PartTimeDetailInfoDlg.clearData = function() {
    this.partTimeDetailInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PartTimeDetailInfoDlg.set = function(key, val) {
    this.partTimeDetailInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PartTimeDetailInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PartTimeDetailInfoDlg.close = function() {
    parent.layer.close(window.parent.PartTimeDetail.layerIndex);
}

/**
 * 收集数据
 */
PartTimeDetailInfoDlg.collectData = function() {
    this
    .set('id')
    .set('partTimeTitle')
    .set('partTimeContent')
    .set('partTimeType')
    .set('recruiterNumber')
    .set('workType')
    .set('workStartTime')
    .set('workEndTime')
    .set('workTimeRange')
    .set('genderRequirement')
    .set('workWelfare')
    .set('workPlace')
    .set('settlementCycle')
    .set('publishEmail')
    .set('publishPhone')
    .set('gmtCreate')
    .set('gmtModified');
}

/**
 * 提交添加
 */
PartTimeDetailInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/spark/partTimeDetail/add", function(data){
        Feng.success("添加成功!");
        window.parent.PartTimeDetail.table.refresh();
        PartTimeDetailInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.partTimeDetailInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PartTimeDetailInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/spark/partTimeDetail/update", function(data){
        Feng.success("修改成功!");
        window.parent.PartTimeDetail.table.refresh();
        PartTimeDetailInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.partTimeDetailInfoData);
    ajax.start();
}

$(function() {

});
