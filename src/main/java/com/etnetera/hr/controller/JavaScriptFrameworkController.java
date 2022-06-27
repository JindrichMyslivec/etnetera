package com.etnetera.hr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@GetMapping("/frameworks/{id}")
    public JavaScriptFramework get(@PathVariable Long id) {
        return repository.findById(id).get();
    }

	@GetMapping("/frameworks/search/{name}")
    public Iterable<JavaScriptFramework> search(@PathVariable String name) {
		List<JavaScriptFramework> list = new ArrayList<JavaScriptFramework>();
		if (name == null || name.isEmpty()) return list;
		for (JavaScriptFramework jsf : frameworks()) {
			if (name.equals(jsf.getName())) {
				list.add(jsf);
			}
		}
		return list;
    }
	
	@PostMapping("/frameworks")
    public JavaScriptFramework save(@RequestBody JavaScriptFramework framework) {
        return repository.save(framework);
    }

	@PutMapping("/frameworks/{id}")
    public JavaScriptFramework update(@PathVariable Long id, @RequestBody JavaScriptFramework framework) {
		repository.findById(id).get();
		framework.setId(id);
        return repository.save(framework);
    }

	@DeleteMapping("/frameworks/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

	@DeleteMapping("/frameworks")
    public void delete() {
        repository.deleteAll();
    }
	
	public long count() {
		return repository.count();
	}

}
