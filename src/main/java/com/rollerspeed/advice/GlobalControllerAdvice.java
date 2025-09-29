
package com.rollerspeed.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            System.out.println("✅ Usuario logueado: " + auth.getName());
            model.addAttribute("user", auth.getName());
        } else {
            System.out.println("❌ No hay usuario logueado");
            model.addAttribute("user", null);
        }
    }
}