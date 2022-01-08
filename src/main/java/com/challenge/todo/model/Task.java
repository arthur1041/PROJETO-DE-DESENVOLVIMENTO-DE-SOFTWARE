package com.challenge.todo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


/**
 * Entidade que representa uma task
 *
 * @author Guilherme Câmara	
 * @version	1.0
*/
@Entity
@Table(name = "task")
public class Task {

	public Task() {
		
	}
	
    public Task(String description) {
        this.description = description;
    }
    

    
    public Task(Integer id, @NotEmpty(message = "*Please provide your task") String description, Date createdDate,
			Date dueDate, int priority, User user) {
		super();
		this.id = id;
		this.description = description;
		this.createdDate = createdDate;
		this.dueDate = dueDate;
		this.priority = priority;
		this.user = user;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;
    
    @Column(name = "description")
    @NotEmpty(message = "*Please provide your task")
    private String description;
    
    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "due_date")
    private Date dueDate;
    
    @Column(name = "priority")
    private int priority;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id")
    private File file;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
    
}
