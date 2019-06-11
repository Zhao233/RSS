package com.example.demo.controller;

import com.example.demo.domain.user.Admin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class consoleController {
    @RequestMapping("/admin/getMenu")
    public ModelAndView getMenu(){
        return new ModelAndView("console/menu");
    }

    /**
     * 活动管理
     * */
    @RequestMapping("/admin/index")
    public ModelAndView toIndex(){
        return new ModelAndView("console/index");
    }

    /**
     * 活动管理
     * */
    @RequestMapping("/admin/activityManagement")
    public ModelAndView toActivityManagement(){
        return new ModelAndView("console/activityManagement/activityManagement");
    }

    /**
     * 菜单管理
     * */

    @RequestMapping("/admin/foodManagement")
    public ModelAndView toFoodManagement(){
        return new ModelAndView("console/foodManagement/foodManagement");
    }

    @RequestMapping("/admin/menuManagement")
    public ModelAndView toMenuManagement(){
        return new ModelAndView("console/foodManagement/menuManagement");
    }

    /**
     * 人员管理
     * */
    @RequestMapping("/admin/cookerManagement")
    public ModelAndView toCookerManagement(){
        return new ModelAndView("console/peopleManagement/cookerManagement");
    }
    @RequestMapping("/admin/cookerDetail")
    public ModelAndView toCookerDetail(){
        return new ModelAndView("console/peopleManagement/cookerDetail");
    }


    @RequestMapping("/admin/userManagement")
    public ModelAndView toUserManagement(){
        return new ModelAndView("console/peopleManagement/userManagement");
    }
    @RequestMapping("/admin/userDetail")
    public ModelAndView toUserDetail(){
        return new ModelAndView("console/peopleManagement/userDetail");
    }

    @RequestMapping("/admin/waiterManagement")
    public ModelAndView toWaiterMenuManagement(){
        return new ModelAndView("console/peopleManagement/waiterManagement");
    }
    @RequestMapping("/admin/waiterDetail")
    public ModelAndView toWaiterDetail(){
        return new ModelAndView("console/peopleManagement/waiterDetail");
    }


    @RequestMapping("/logged")
    public String logged(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (admin != null) {
            admin.setPassword(null);
            request.getSession().setAttribute("admin", admin);
        }

        return "redirect:/login";

//        if(user.getRole().equals("PM"))
//        {
//            return "redirect:/console/employee_management";
//        }else if(user.getRole().equals("S")){
//            return "redirect:/console/employee_management";
//        }else if(user.getRole().equals("C")){
//            return "redirect:/console/employee_management";
//        }else if(user.getRole().equals("A")){
//            return "redirect:/console/employee_management";
//        }else {
//            return "redirect:/console";
//        }

    }

    @RequestMapping(value = {"/", "/login"})
    public ModelAndView toHome(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin)request.getSession().getAttribute("admin");


        if (admin != null) {
            return new ModelAndView("console/index");
        }

        return new ModelAndView("login");
    }

}
