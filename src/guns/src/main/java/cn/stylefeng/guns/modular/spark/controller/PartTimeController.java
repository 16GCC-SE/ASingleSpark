package cn.stylefeng.guns.modular.spark.controller;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import cn.stylefeng.guns.core.common.constant.factory.PageFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.spark.model.PartTime;
import cn.stylefeng.guns.modular.spark.service.IPartTimeService;

import java.util.Date;
import java.util.List;

/**
 * 兼职详情数据操作控制器
 *
 * @author fengshuonan
 * @Date 2018-12-13 17:30:19
 */
@Controller
@RequestMapping("/spark/partTime")
public class PartTimeController extends BaseController {

    private String PREFIX = "/spark/partTime/";

    @Autowired
    private IPartTimeService partTimeService;

    /**
     * 跳转到兼职详情数据操作首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "partTime.html";
    }

    /**
     * 跳转到添加兼职详情数据操作
     */
    @RequestMapping("/partTime_add")
    public String partTimeAdd() {
        return PREFIX + "partTime_add.html";
    }

    /**
     * 跳转到修改兼职详情数据操作
     */
    @RequestMapping("/partTime_update/{partTimeId}")
    public String partTimeUpdate(@PathVariable Integer partTimeId, Model model) {
        PartTime partTime = partTimeService.selectById(partTimeId);
        model.addAttribute("item",partTime);
        LogObjectHolder.me().set(partTime);
        return PREFIX + "partTime_edit.html";
    }

    /**
     * 获取（和查找）兼职详情数据操作列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        if(ToolUtil.isNotEmpty(condition)){
            EntityWrapper<PartTime> partTimeDetailEntityWrapper = new EntityWrapper<>();
            Wrapper<PartTime> type = partTimeDetailEntityWrapper.like("part_time_type","%"+condition+"%");
            List<PartTime> partTime = partTimeService.selectList(type);
            return partTime;
        }else {
            List<PartTime> partTimeDetails = partTimeService.selectList(null);
            return partTimeDetails;
        }
    }

    /**
     * 分页获取（和查找）兼职详情数据操作列表
     */
    @RequestMapping(value = "/page")
    @ResponseBody
    public Object page() {
        Page<PartTime> page = new PageFactory<PartTime>().defaultPage();
        return partTimeService.selectPage(page);
    }

    /**
     * 新增兼职详情数据操作
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PartTime partTime) {
//        if(partTime != null){
//            partTime.setGmtCreate(new Date());
//        }
        partTimeService.insert(partTime);
        return SUCCESS_TIP;
    }

    /**
     * 删除兼职详情数据操作
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer partTimeId) {
        partTimeService.deleteById(partTimeId.longValue());
        return SUCCESS_TIP;
    }

    /**
     * 修改兼职详情数据操作
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PartTime partTime) {
        partTimeService.updateById(partTime);
        return SUCCESS_TIP;
    }

    /**
     * 兼职详情数据操作详情
     */
    @RequestMapping(value = "/detail/{partTimeId}")
    @ResponseBody
    public Object detail(@PathVariable("partTimeId") Integer partTimeId) {
        return partTimeService.selectById(partTimeId);
    }
}
