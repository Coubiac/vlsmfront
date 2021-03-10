package com.begr.vlsmfront.controller;

import com.begr.vlsmfront.model.request.NetworkDetailRequestModel;
import com.begr.vlsmfront.model.response.SubnetResponseModel;
import com.begr.vlsmfront.proxies.VlsmProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final VlsmProxy vlsmProxy;

    public HomeController(VlsmProxy vlsmProxy) {
        this.vlsmProxy = vlsmProxy;
    }


    @GetMapping("/")
    public ModelAndView showForm(){
        String viewName= "accueil";
        Map<String,Object> model = new HashMap<>();
        model.put("network", new NetworkDetailRequestModel());
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/")
    public String handleForm(@Valid @ModelAttribute NetworkDetailRequestModel networkDetailRequestModel, BindingResult result, Model model, RedirectAttributes redirAttrs){

        
        if(result.hasErrors()){
            StringBuilder message = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                message.append(error.getDefaultMessage()).append(" ");
            }
            redirAttrs.addFlashAttribute("error", message.toString());
            return "redirect:/";
        }
        
        List<SubnetResponseModel> subnetResponseModels;
        try{
            subnetResponseModels = this.vlsmProxy.sendRequest(networkDetailRequestModel);
        }catch(Exception e){
            redirAttrs.addFlashAttribute("error", e.getLocalizedMessage());
            return "redirect:/";
        }

        model.addAttribute("subnets", subnetResponseModels);

        return "networks";
    }

    
}
