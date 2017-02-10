/*
 * @(#)Exercise.java 1.0 06/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.domain.exercise;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import br.com.keepinshape.domain.activities.Activities;

/**
 * Class comments go here...
 *
 * @author Joao Batista
 * @version 1.0 06/02/2017
 */
@Entity
public class Exercise {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private final String name;
	private double weight;
	private int quantity;
	private double points;
	@ManyToOne
	private Activities activities;
	
	private Exercise(final Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.weight = builder.weight;
		this.quantity = builder.quantity;
		this.points = builder.points;
	}
	
	private Exercise() {
		this.name = null;
	}
	
	
	public Long getId() {
		return id;
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


	public static class Builder {
		
		private Long id;
		private final String name;
		private double weight;
		private int quantity;
		private double points;
		
		public Builder(final String name) {
			this.name = name;
		}
		
		public Builder id(final Long id) {
			this.id = id;
			return this;
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
