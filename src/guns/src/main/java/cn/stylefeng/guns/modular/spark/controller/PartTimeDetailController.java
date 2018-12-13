package cn.stylefeng.guns.modular.spark.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.spark.model.PartTimeDetail;
import cn.stylefeng.guns.modular.spark.service.IPartTimeDetailService;

import java.util.Date;
import java.util.List;

/**
 * 兼职详情控制器
 *
 * @author fengshuonan
 * @Date 2018-12-12 13:16:46
 */
@Controller
@RequestMapping("/spark/partTimeDetail")
public class PartTimeDetailController extends BaseController {

    private String PREFIX = "/spark/partTimeDetail/";

    @Autowired
    private IPartTimeDetailService partTimeDetailService;

    /**
     * 跳转到兼职详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "partTimeDetail.html";
    }

    /**
     * 跳转到添加兼职详情
     */
    @RequestMapping("/partTimeDetail_add")
    public String partTimeDetailAdd() {
        return PREFIX + "partTimeDetail_add.html";
    }

    /**
     * 跳转到修改兼职详情
     */
    @RequestMapping("/partTimeDetail_update/{partTimeDetailId}")
    public String partTimeDetailUpdate(@PathVariable Integer partTimeDetailId, Model model) {
        PartTimeDetail partTimeDetail = partTimeDetailService.selectById(partTimeDetailId);
        model.addAttribute("item",partTimeDetail);
        LogObjectHolder.me().set(partTimeDetail);
        return PREFIX + "partTimeDetail_edit.html";
    }

    /**
     * 获取兼职详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        if(ToolUtil.isNotEmpty(condition)){
            EntityWrapper<PartTimeDetail> partTimeDetailEntityWrapper = new EntityWrapper<>();
            Wrapper<PartTimeDetail> type = partTimeDetailEntityWrapper.like("part_time_type","%"+condition+"%");
            List<PartTimeDetail> partTimeDetails = partTimeDetailService.selectList(type);
            return partTimeDetails;
        }else {
            List<PartTimeDetail> partTimeDetails = partTimeDetailService.selectList(null);
            return partTimeDetails;
        }

    }

    /**
     * 新增兼职详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PartTimeDetail partTimeDetail) {
        if(partTimeDetail != null){
            partTimeDetail.setGmtCreate(new Date());
        }
        partTimeDetailService.insert(partTimeDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除兼职详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer partTimeDetailId) {
        partTimeDetailService.deleteById(partTimeDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改兼职详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PartTimeDetail partTimeDetail) {
        partTimeDetailService.updateById(partTimeDetail);
        return SUCCESS_TIP;
    }

    /**
     * 兼职详情详情
     */
    @RequestMapping(value = "/detail/{partTimeDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("partTimeDetailId") Integer partTimeDetailId) {
        return partTimeDetailService.selectById(partTimeDetailId);
    }
}
