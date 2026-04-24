package cn.cchh.hotel.controller;

import cn.cchh.hotel.dto.RegisterDTO;
import cn.cchh.hotel.dto.Result;
import cn.cchh.hotel.entity.User;
import cn.cchh.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 无条件查询列表数据
     */
    @GetMapping("/test")
    public Result testGetUserList(){
        return Result.success("查询完成",userService.selectUserList());
    }

    /**
     * 分页查询-无条件
     */
    @GetMapping("/page")
    public Result<Page<User>> getUserPage(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> page = userService.getUserPage(current, size);
        System.out.println("分页数："+page.getPages());
        return Result.success("success",page);
    }

    /**
     * 模糊查询
     */
    @GetMapping("/userPage")
    public Result<Page<User>> getUserNamePage(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String username) {
        Page<User> page = userService.getUserNamePage(current, size,username);
        System.out.println("分页数："+page.getPages());
        return Result.success("success",page);
    }

    /**
     * 根据ID获取用户信息（个人中心专用）
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return Result.success("查询成功", user);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户信息修改
     */
    @PostMapping("/edit")
    public Result userUpdate(Long id,@RequestBody RegisterDTO userDto) {
        if (userDto==null){
            return Result.error("用户为空，请查看用户信息");
        }
        try {
            User user = new User();
            user.setId(id);
            user.setNickname(userDto.getNickname());
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setPhone(userDto.getPhone());
            boolean result = userService.updateUser(user);
            if (result) {
                return Result.success("用户修改成功");
            } else {
                return Result.error("用户修改失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    /**
     * 用户密码修改
     */
    @PostMapping("/userPasswordUpdate   ")
    public Result userUpdate(@RequestParam Long id,@RequestParam String password) {
        try {
            boolean result = userService.updateUserPassword(id,password);
            if (result) {
                return Result.success("用户密码修改成功");
            } else {
                return Result.error("用户密码修改失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 逻辑删除(数据库)
     */
    @DeleteMapping("/delete")
    public Result deleteUserById(@RequestParam Long userid) {
        try {
            boolean result = userService.deleteUser(userid);
            if (result) {
                return Result.success("用户删除成功");
            } else {
                return Result.error("用户删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 业务删除
     * @param userid
     * @return
     */
    @DeleteMapping("/editDeleted")
    public Result updateUserById(@RequestParam Long userid) {
        try {
            boolean result = userService.updateUserDeleted(userid);
            if (result) {
                return Result.success("用户删除成功");
            } else {
                return Result.error("用户删除失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
