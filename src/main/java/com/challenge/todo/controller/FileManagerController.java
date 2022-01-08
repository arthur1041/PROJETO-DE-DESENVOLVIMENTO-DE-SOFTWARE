package com.challenge.todo.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.challenge.todo.model.File;
import com.challenge.todo.service.FileService;

/**
 * Controller utilizado para recuperar a URL de arquivos.	
 *
 * @author Arthur Cruz	
 * @version	1.0
*/ 

@Controller
public class FileManagerController {

	@Autowired
	private FileService fileService;

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable int id, @PathParam("salvar") String salvar) {
		File file = fileService.findById(id);

		String text = (salvar == null || salvar.equals("true")) ? "attachment; filename=\"" + file.getName() + "\""
				: "inline; filename=\"" + file.getName() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, text).body(new ByteArrayResource(file.getFileBytes()));
	}
}
