package com.challenge.todo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.todo.model.File;
import com.challenge.todo.model.Task;
import com.challenge.todo.model.User;
import com.challenge.todo.service.FileService;
import com.challenge.todo.service.TaskService;
import com.challenge.todo.service.UserService;
import com.challenge.todo.util.FileUploadUtil;

/**
 * Controller utilizado para gerenciar as Tasks
 *
 * @author Arthur Cruz	
 * @version	1.0
*/ 

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FileService fileService;
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  

    @PostMapping(value = "/saveTask")
    @Transactional(readOnly = false)
    public ModelAndView saveTask(@Valid Task task, BindingResult bindingResult, @RequestParam("file") MultipartFile mtpfile ) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        File file = null;
        if(mtpfile != null && !mtpfile.isEmpty()) {
        	String filename = StringUtils.cleanPath(mtpfile.getOriginalFilename());
        	file = new File(filename, mtpfile.getContentType(), mtpfile.getBytes());
        	file = fileService.saveFile(file);
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        if(task.getId() != null) {
	        Task taskExists = taskService.findById(task.getId());
	        if (taskExists != null) {
	            bindingResult
	                    .rejectValue("email", "error.user",
	                            "Já existe um usuário registrado com o email informado");
	        }
        }
        
        
        task.setUser(user);
        task.setCreatedDate(new Date());
        if(file != null)
        	task.setFile(file);
        taskService.saveTask(task);
        modelAndView.addObject("successMessage", "Task cadastrada com sucesso");
         
        
        modelAndView.addObject("task", new Task());
        modelAndView.addObject("tasks", taskService.findByUser(user));
        modelAndView.setViewName("admin/home");
        
        return modelAndView;
    }
   

    @PostMapping(value = "/updateTask")
    public ModelAndView updateTask(@Valid Task task, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        Task taskExists = taskService.findById(task.getId());
        if(task.getId() != null) {
	        if (taskExists == null) {
	        	modelAndView.addObject("errorMessage", "Task não existe");
	        }
        }
        
        if (taskExists != null) {
            task.setUser(user);
            taskService.updateTask(task);
            modelAndView.addObject("successMessage", "Task atualizada com sucesso");
        } 
        
        modelAndView.addObject("task", new Task());
        modelAndView.addObject("tasks", taskService.findByUser(user));
        modelAndView.setViewName("admin/home");
        
        return modelAndView;
    }
    
    @GetMapping(value = "/deleteTask/{id}")
    public ModelAndView deleteTask(@PathVariable("id") int taskId) {
        ModelAndView modelAndView = new ModelAndView();
        Task task = taskService.findById(taskId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        
        if (task != null && task.getUser().equals(user)) {
            taskService.deleteTask(task);
            modelAndView.addObject("successMessage", "Task apagada com sucesso");
        } else {
            modelAndView.addObject("errorMessage", "Task não existe");
        }
        
        modelAndView.addObject("task", new Task());
        modelAndView.addObject("tasks", taskService.findByUser(user));
        modelAndView.setViewName("admin/home");
        
        return modelAndView;
    }
    
    @GetMapping(value = "/editTask/{id}")
    public ModelAndView editTask(@PathVariable("id") int taskId) {
    	ModelAndView modelAndView = new ModelAndView();
    	Task task = taskService.findById(taskId);
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findUserByEmail(auth.getName());
    	
    	if(task != null && task.getUser().equals(user)) {
    		modelAndView.addObject("task", task);
    		modelAndView.setViewName("admin/edit");
    	} else {
    		modelAndView.addObject("errorMessage", "Task não existe");
    	}
    	
    	return modelAndView;
    	
    	
    }

}
