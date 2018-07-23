package com.example.news.Controller;

import com.example.news.Entity.User;
import com.example.news.Repository.UserRepository;
import com.example.news.Util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "UserController", description = "用户接口")
@RestController
@CrossOrigin
public class UserController {
    private static final org.apache.commons.logging.Log log = LogFactory.getLog(UserController.class);
    @Autowired
    private UserRepository userRepository;


    @ApiOperation(value="用户注册", notes="根据url的id来获取用户详细信息")
    @RequestMapping(value = "/user/register")
    public ApiResult getAllStudent(String name, String phoneNumber, String password){
        log.info("========== name: "+name+"  phoneNumber:"+phoneNumber+"  password :"+password);
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(phoneNumber)  || StringUtils.isEmpty(password))
        {
            return ApiResult.error("数据不能为空！");
        }
        if(null != userRepository.getByPhoneNumber(phoneNumber) ){
            return ApiResult.error("该手机号已经被注册！");
        }
        User user = new User();
        user.setUserName(name);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        userRepository.save(user);
        return ApiResult.ok("注册成功！");
    }

    @RequestMapping(value="/user/login")
    public ApiResult login(String phoneNumber,String password){
        if(StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(password)){
            return ApiResult.error("数据不能为空 ！");
        }
        User user = userRepository.getByPhoneNumber(phoneNumber);
        if(null == user){
            return ApiResult.error("账号不存在！");
        }else if(!password.equals(user.getPassword()) ){
            return ApiResult.error("密码错误！");
        }
        return ApiResult.ok(user);
    }

    @RequestMapping(value = "/user/findOne")
    public ApiResult  findOne(String userKeyId){
        if(StringUtils.isEmpty(userKeyId)){
            return ApiResult.error("数据不能为空！");
        }
        User user = userRepository.getOneByKeyId(Integer.parseInt(userKeyId));
        if(null == user){
            return ApiResult.error("没有该用户！");
        }
        return ApiResult.ok(user);
    }

    @RequestMapping(value = "/user/update")
    public ApiResult update(String userKeyId,String name,String phoneNumber,String password){
        if(StringUtils.isEmpty(userKeyId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(password))
        {
            return ApiResult.error("数据不能为空！");
        }
        User user  = userRepository.getOneByKeyId(Integer.parseInt(userKeyId));
        if(null == user){
            return ApiResult.error("不存在的用户！");
        }
        user.setUserName(name);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user = userRepository.save(user);
        return ApiResult.ok(user);
    }
}
