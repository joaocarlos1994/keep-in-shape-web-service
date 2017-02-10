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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 06/02/2017
 */
@Entity
public class Activities {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private final String name;
	@OneToMany(mappedBy = "activities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private final List<Exercise> exercises;
	private Weekday weekday;
	@Transient
	private double allPoints;
	
	private Activities(final String name, final Weekday weekday) {
		this.name = name;
		this.weekday = weekday;
		this.exercises = new ArrayList<>();
	}
	
	public Activities valueOf(final String name, final Weekday weekday) {
		final Activities activities = new Activities(name, weekday);
		return activities;
	}
	
	public void addExercise(final Exercise exercise) {
		if (exercise != null) {
			this.addExercise(exercise);
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
		for (Exercise exercise : exercises) {
			points += exercise.getPoints();
		}
		return points;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Weekday getWeekday() {
		return weekday;
	}

	public List<Exercise> getExercises() {
		return Collections.unmodifiableList(exercises);
	}
}
