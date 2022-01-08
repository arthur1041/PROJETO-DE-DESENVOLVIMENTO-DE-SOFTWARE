package com.challenge.todo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.todo.model.File;
import com.challenge.todo.repository.FileRepository;

/**
 * Classe service utilizada como meio de campo com os repositories dos arquivos
 * Serve para proteger os dados por meio de manipulações dos mesmos nas consultas e inserções
 *
 * @author Carlos França
 * @version	1.0
*/
@Service
@Transactional
public class FileService {

	@Autowired
	private FileRepository fileRepository;
	
	public List<File> findAllFiles() {
		return fileRepository.findAll();
	}

	public File findById(int id) {
		return fileRepository.findById(id).orElse(null);
	}

	public File saveFile(File task) {
		return fileRepository.save(task);
	}

	public void deleteFile(File task) {
		fileRepository.delete(task);
	}

	
}
