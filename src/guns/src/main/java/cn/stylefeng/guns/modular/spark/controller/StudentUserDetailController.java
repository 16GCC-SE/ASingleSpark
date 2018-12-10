package cn.stylefeng.guns.modular.spark.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.spark.model.StudentUserDetail;
import cn.stylefeng.guns.modular.spark.service.IStudentUserDetailService;

/**
 * 学生认证控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 09:58:52
 */
@Controller
@RequestMapping("/spark/studentUserDetail")
public class StudentUserDetailController extends BaseController {

    private String PREFIX = "/spark/studentUserDetail/";

    @Autowired
    private IStudentUserDetailService studentUserDetailService;

    /**
     * 跳转到学生认证首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "studentUserDetail.html";
    }

    /**
     * 跳转到添加学生认证
     */
    @RequestMapping("/studentUserDetail_add")
    public String studentUserDetailAdd() {
        return PREFIX + "studentUserDetail_add.html";
    }

    /**
     * 跳转到修改学生认证
     */
    @RequestMapping("/studentUserDetail_update/{studentUserDetailId}")
    public String studentUserDetailUpdate(@PathVariable Integer studentUserDetailId, Model model) {
        StudentUserDetail studentUserDetail = studentUserDetailService.selectById(studentUserDetailId);
        model.addAttribute("item",studentUserDetail);
        LogObjectHolder.me().set(studentUserDetail);
        return PREFIX + "studentUserDetail_edit.html";
    }

    /**
     * 获取学生认证列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return studentUserDetailService.selectList(null);
    }

    /**
     * 新增学生认证
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(StudentUserDetail studentUserDetail) {
        studentUserDetailService.insert(studentUserDetail);
        return SUCCESS_TIP;
    }

    /**
     * 删除学生认证
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer studentUserDetailId) {
        studentUserDetailService.deleteById(studentUserDetailId);
        return SUCCESS_TIP;
    }

    /**
     * 修改学生认证
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(StudentUserDetail studentUserDetail) {
        studentUserDetailService.updateById(studentUserDetail);
        return SUCCESS_TIP;
    }

    /**
     * 学生认证详情
     */
    @RequestMapping(value = "/detail/{studentUserDetailId}")
    @ResponseBody
    public Object detail(@PathVariable("studentUserDetailId") Integer studentUserDetailId) {
        return studentUserDetailService.selectById(studentUserDetailId);
    }
}
