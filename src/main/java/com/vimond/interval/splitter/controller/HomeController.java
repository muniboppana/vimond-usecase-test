package com.vimond.interval.splitter.controller;

import com.vimond.interval.splitter.Models.Interval;
import com.vimond.interval.splitter.service.FizzBuzzService;
import com.vimond.interval.splitter.service.IntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController implements ErrorController {
	@Autowired
	IntervalService intervalService;
	@Autowired
	FizzBuzzService fizzBuzzService;
	@PostMapping("/interval")
	public String intervalSubmit(@ModelAttribute Interval interval, BindingResult bindingResult) throws Exception {
		
		if(bindingResult.hasErrors()){
			return "interval";
		}
		String result = intervalService.ReadAndExecute(interval);
		interval.setOutputIntervals(result);
		interval.setIncludeIntervals(interval.getIncludeIntervals());
		interval.setExcludeIntervals(interval.getExcludeIntervals());
		
		if (result == null) {
			return "error";
		}
		return "OutPutInterval";
	}

	@GetMapping("/interval")
	public String intervalForm(Model model) {
		model.addAttribute("interval", new Interval());
		return "interval";
	}

	@RequestMapping("/fizzBuzz")
	public String fizzBuzz(Model model) {
		String result = fizzBuzzService.Execute();
		model.addAttribute("fizzBuzz", result);
		return "fizzBuzz";
	}

		
	@Override
    public String getErrorPath() {
        return "/error";
	}
	
	
/*Generic Exception Handler for input intervals */
	@ExceptionHandler()
	@RequestMapping(value = "/error")
	public String handleError(Model model, Exception ex){
		model.addAttribute("exception", ex.getMessage());
		return "error";
	}

}