/*
 * @(#)Activities.java 1.0 06/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.domain.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>Activity</code> representa uma
 * atividade dentro do dominio, assim essa 
 * atividade contem uma lista de exercicio
 * e seu respectivo dia de execucao.
 *
 * @author Joao Batista
 * @version 1.0 06/02/2017
 */
@Entity
public class Activity {
	
	@Id
	@Column(name = "id", columnDefinition = "serial")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@NotNull
	private final String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private final List<Exercise> exercises;
	
	private Weekday weekday;
	
	@Transient
	private double allPoints;
	
	private Activity(final String name) {
		this.name = name;
		this.exercises = new ArrayList<>();
	}
	
	private Activity() {
		this.name = null;
		this.exercises = null;
	}
	
	public static Activity valueOf(final String name) {
		final Activity activities = new Activity(name);
		return activities;
	}
	
	public void addExercise(final Exercise exercise) {
		if (exercise != null) {
			this.exercises.add(exercise);
		}
	}
	
	public void removeExercise(final Exercise exercise) {
		if (exercise != null) {
			this.exercises.remove(exercise);
		}
	}
	
	public void changeWeekDay(final Weekday weekday) {
		if (weekday != null) {
			this.weekday = weekday;
		}
	}
	
	public double totalPoints() {
		double points = 0;
		for (final Exercise exercise : exercises) {
			points += exercise.getPoints();
		}
		return points;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Weekday getWeekday() {
		return weekday;
	}
	
	public void setWeekday(final Weekday weekday) {
		this.weekday = weekday;
	}

	public List<Exercise> getExercises() {
		return Collections.unmodifiableList(exercises);
	}
}
