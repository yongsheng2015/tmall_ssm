package com.sheng.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sheng.tmall.pojo.User;
import com.sheng.tmall.service.UserService;
import com.sheng.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> us=userService.list();
        int total = (int) new PageInfo<>(us).getTotal();
        page.setTotal(total);

        model.addAttribute("us", us);
        model.addAttribute("page", page);
        return "admin/listUser";
    }

    @RequestMapping("loginAdmin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password,
                        Model model, HttpSession session) {
        HashMap<String, String> map = new HashMap<>();
        map.put("路飞", "Sheng2018.");

        name = HtmlUtils.htmlEscape(name);
        User admin = userService.get(name, password);
        if (null == admin || map.get(name) == null || !map.get(name).equals(password)) {
            model.addAttribute("msg", "管理员账号密码错误！");
            return "admin/login";
        }
        session.setAttribute("admin", admin);

        return "redirect:/admin_category_list";
    }
}
