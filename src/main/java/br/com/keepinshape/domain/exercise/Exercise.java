/*
 * @(#)Exercise.java 1.0 06/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.domain.exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * A <code>Exercise</code> representa um exercicio dentro da aplicacao.
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

	private Long weight;

	private Integer quantity;

	private Long points;

	private Exercise(final Builder builder) {
		this.name = builder.name;
		this.weight = builder.weight;
		this.quantity = builder.quantity;
		this.points = builder.points;
	}

	@Deprecated
	private Exercise() {
		this.name = null;
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

	public Long getWeight() {
		return weight;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Long getPoints() {
		return points;
	}

	public static class Builder {

		private final String name;
		private Long weight;
		private Integer quantity;
		private Long points;

		public Builder(final String name) {
			if (name == null) {
				throw new NullPointerException("Name is null");
			}
			this.name = name;
		}

		public Builder weight(final Long weight) {
			if (weight.longValue() <= 0) {
				throw new IllegalArgumentException("Weight is invalid");
			}
			this.weight = weight;
			return this;
		}

		public Builder quantity(final Integer quantity) {
			if (quantity.intValue() <= 0) {
				throw new IllegalArgumentException("Quantity is invalid");
			}
			this.quantity = quantity;
			return this;
		}

		public Builder points(final Long points) {
			if (points.longValue() <= 0) {
				throw new IllegalArgumentException("Points is invalid");
			}
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
}
