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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import br.com.keepinshape.domain.exercise.Exercise;

/**
 * A <code>Activity</code> representa uma atividade dentro do dominio, assim
 * essa atividade contem uma lista de exercicio e seu respectivo dia de
 * execucao.
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

	private final String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "activity_exercise", 
	joinColumns = @JoinColumn(name = "activities_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "exercises_id", referencedColumnName = "id"))
	private final List<Exercise> exercises;

	private Weekday weekday;

	@Transient
	private Long allPoints;

	private Activity(final String name) {
		this.name = validateName(name);
		this.exercises = new ArrayList<>();
	}

	@Deprecated
	private Activity() {
		this.name = null;
		this.exercises = null;
	}

	public static Activity valueOf(final String name) {
		final Activity activities = new Activity(name);
		return activities;
	}

	public Exercise addExercise(final Exercise exercise) {
		if (exercise != null && (!exercises.contains(exercise))) {
			this.exercises.add(exercise);
			return exercise;
		}
		throw new IllegalArgumentException("Already exists exercise or exercise invalid");
	}

	public void removeExercise(final Exercise exercise) {
		if (exercise != null && exercises.contains(exercise)) {
			this.exercises.remove(exercise);
		} else {
			throw new IllegalArgumentException("No exists exercise or exercise invalid");
		}
	}

	public Long totalPoints() {
		return exercises.stream().mapToLong(Exercise::getPoints).sum();

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

	private String validateName(final String name) {
		if (name != null) {
			return name;
		}
		throw new NullPointerException("Name is null");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (allPoints ^ (allPoints >>> 32));
		result = prime * result + ((exercises == null) ? 0 : exercises.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((weekday == null) ? 0 : weekday.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (allPoints != other.allPoints)
			return false;
		if (exercises == null) {
			if (other.exercises != null)
				return false;
		} else if (!exercises.equals(other.exercises))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (weekday != other.weekday)
			return false;
		return true;
	}
}
