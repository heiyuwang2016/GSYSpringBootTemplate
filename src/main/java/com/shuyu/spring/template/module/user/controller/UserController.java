package com.shuyu.spring.template.module.user.controller;


import com.shuyu.spring.template.annotation.LoginUser;
import com.shuyu.spring.template.config.GlobalConfig;
import com.shuyu.spring.template.module.user.entity.User;
import com.shuyu.spring.template.module.user.service.IUserService;
import com.shuyu.spring.template.utils.ResponseUtil;
import com.shuyu.spring.template.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author shuyu
 * @since 2019-01-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public IUserService userService;

    @GetMapping("/info")
    public Object getInfo(@LoginUser @Valid User user) {
        return ResponseUtil.ok(user);
    }

    @PostMapping("/add")
    @RequiresRoles({"admin"})
    public Object add(@RequestBody @Valid User user) {
        User db = userService.getByAccount(user.getAccount());
        if (db != null) {
            return ResponseUtil.badArgument("用户已存在");
        }
        UserUtils.initCreateUser(user);
        userService.save(user);
        return ResponseUtil.ok();
    }

}

