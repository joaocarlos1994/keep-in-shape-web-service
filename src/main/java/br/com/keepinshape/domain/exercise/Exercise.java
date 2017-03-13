/*
 * @(#)Exercise.java 1.0 06/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.domain.exercise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import br.com.keepinshape.domain.activities.Activity;

/**
 * A <code>Exercise</code> representa um
 * exercicio dentro da aplicacao.
 *
 * @author Joao Batista
 * @version 1.0 06/02/2017
 */
@Entity
public class Exercise {
	
	@Id
	@Column(name = "id", columnDefinition = "serial")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	private final String name;
	
	private double weight;
	
	private int quantity;
	
	private double points;
	
	@ManyToMany(mappedBy = "exercises")
	private final List<Activity> activities;
	
	private Exercise(final Builder builder) {
		this.name = builder.name;
		this.weight = builder.weight;
		this.quantity = builder.quantity;
		this.points = builder.points;
		this.activities = new ArrayList<>();
	}
	
	private Exercise() {
		this.name = null;
		this.activities = null;
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

	public double getWeight() {
		return weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPoints() {
		return points;
	}
	
	public void addActivity(final Activity activity) {
		if (activity != null) {
			this.activities.add(activity);
		}
	}

	public static class Builder {
		
		private final String name;
		private double weight;
		private int quantity;
		private double points;
			
		public Builder(final String name) {
			if (name == null) {
				throw new NullPointerException("Name is null");
			}
			this.name = name;
		}		
		
		public Builder weight(final double weight) {
			this.weight = weight;
			return this;
		}
		
		public Builder quantity(final int quantity) {
			this.quantity = quantity;
			return this;
		}
		
		
		public Builder points(final double points) {
			this.points = points;
			return this;
		}
		
		public Exercise build() {
			return new Exercise(this);
		}	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Exercise other = (Exercise) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
