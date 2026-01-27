package demo.github.controllers;

import java.security.Principal;
import java.util.Map;

import demo.github.Issue;
import demo.github.NewIssue;
import demo.github.service.IssueService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IssuesController {
	final IssueService issues;

	final String githubOrg;

	final String githubRepo;

	public IssuesController(IssueService issues, @Value("${github.org}") String githubOrg, @Value("${github.repo}") String githubRepo) {
		this.issues = issues;
		this.githubOrg = githubOrg;
		this.githubRepo = githubRepo;
	}

	@GetMapping("/")
	String index() {
		return "redirect:/issues";
	}

	@GetMapping("/issues")
	String issuesForm(@ModelAttribute NewIssue issue, Map<String, Object> model) {
		model.put("issue", issue);
		return "issues/form";
	}

	@PostMapping("/issues")
	String issue(@ModelAttribute NewIssue issue, RedirectAttributes redirect) {
		Issue result = this.issues.create(this.githubOrg, this.githubRepo,
				issue);
		redirect.addAttribute("number", result.number());
		return "redirect:/issues/{number}";
	}

	@GetMapping("/issues/{number}")
	String view(@PathVariable int number, Map<String, Object> model) {
		model.put("number", number);
		return "issues/view";
	}

	@ModelAttribute
	void username(Principal p, Map<String, Object> model) {
		String username = (p == null) ? null : p.getName();
		model.put("username", username);
		model.put("organization", this.githubOrg);
		model.put("repository", this.githubRepo);
	}

}
