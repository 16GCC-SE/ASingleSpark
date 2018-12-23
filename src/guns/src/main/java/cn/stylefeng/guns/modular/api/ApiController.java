/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.spark.model.Enroll;
import cn.stylefeng.guns.modular.spark.model.PartTime;
import cn.stylefeng.guns.modular.spark.service.IEnrollService;
import cn.stylefeng.guns.modular.spark.service.IPartTimeService;
import cn.stylefeng.guns.modular.system.dao.UserMapper;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 接口控制器提供
 *
 * @author stylefeng
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/gunsApi")
public class ApiController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IEnrollService enrollService;

    @Autowired
    private IPartTimeService iPartTimeService;


    /**
     * api登录接口，通过账号密码获取token
     */
    @RequestMapping("/auth")
    public Object auth(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());

        //获取数据库中的账号密码，准备比对
        User user = userMapper.getByAccount(username);

        String credentials = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(), credentials, credentialsSalt, "");

        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
                usernamePasswordToken, simpleAuthenticationInfo);

        if (passwordTrueFlag) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("token", JwtTokenUtil.generateToken(String.valueOf(user.getId())));
            return result;
        } else {
            return new ErrorResponseData(500, "账号密码错误！");
        }
    }

    /**
     * 测试接口是否走鉴权
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object test(@RequestAttribute String jwt_user_id) {
        return SUCCESS_TIP;
    }

    /**
     * 测试接口是否走鉴权
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public Object getUserInfo(@RequestAttribute String jwt_user_id) {
        User user=userMapper.selectById(jwt_user_id);
        return new SuccessResponseData(user);
    }

    /**
     * 新增用户报名
     */
    @RequestMapping(value = "/enroll/add")
    @ResponseBody
    public Object add(Long partTime,Integer user) {
        Enroll enroll=new Enroll();
        enroll.setGmtCreate(new Date());
        enroll.setGmtModified(new Date());
        enroll.setPartTimeId(partTime);
        enroll.setSysUserId(user);
        enroll.setStatus(1);
        enrollService.insert(enroll);
        return SUCCESS_TIP;
    }

    /**
     * 获取用户报名列表
     */
    @RequestMapping(value = "/enroll/list")
    @ResponseBody
    public Object list(Integer user) {
        Map map=new HashMap<String,Object>();
        map.put("sys_user_id",user);
        List<Enroll> enrollList=enrollService.selectByMap(map);
        List<PartTime> partTimes=new ArrayList<>();
        for(Enroll enroll:enrollList){
            PartTime partTime=iPartTimeService.selectById(enroll.getPartTimeId());
            if(partTime!=null){
                partTimes.add(partTime);
            }

        }
        return  partTimes;
    }

}

