/*
 * @(#)ActivitiesController.java 1.0 09/02/2017
 *
 * Copyright (c) 2017, Embraer. All rights reserved.
 * Embraer S/A proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.keepinshape.applicationlayer.ActivityApplicationLayer;
import br.com.keepinshape.domain.activities.Activity;
import br.com.keepinshape.restapi.wrapper.ActivityWrapper;

/**
 * A <code>ActivityController</code> tem por objetivo
 * disponibilizar todos recursos da aplicacao relacionado
 * a <code>Activity</code>.
 *
 * @author Joao Batista
 * @version 1.0 09/02/2017
 */
@RestController
@RequestMapping("/rest")
public class ActivityController {
	
	private final ActivityApplicationLayer activityApplicationLayer;

	@Autowired
	public ActivityController(@Qualifier("activityApplicationLayerImpl") final ActivityApplicationLayer activityApplicationLayer) {
		super();
		this.activityApplicationLayer = activityApplicationLayer;
	}
	
	/**
	 * Este metodo tem por responsabilidade disponibilizar um end-point para salvar uma <code>Activity</code>
	 * onde a mesma recebe a <code>ActivityWrapper</code> que encapsula a <code>Activity</code>. Apos
	 * receber este objeto <code>Activity</code> o mesmo sera encaminhando para <code>ActivityApplicationLayer</code>,
	 * seguindo assim os conceitos propostos pro Evans.
	 * 
	 * @param activityWrapper objeto que contem o activity.
	 * @return ResponseEntity<ActivityWrapper> com um status http e um json em seu body.
	 * */
	@PostMapping(value = "/activity")
	public ResponseEntity<ActivityWrapper> save(@RequestBody final ActivityWrapper activityWrapper) {
		final Activity activity = activityApplicationLayer.saveActivity(activityWrapper.getActivity());
		return new ResponseEntity<>(new ActivityWrapper(activity), HttpStatus.CREATED);
	}
	
	/**
	 * Este metodo tem por responsabilidade disponibilizar um end-point para buscar de uma
	 * <code>Activity</code> salva no banco de dados.
	 * 
	 * @param id objeto que deseja buscar.
	 * @return ResponseEntity<ActivityWrapper> com um status http e um json em seu body.
	 * */
	@GetMapping(value = "/activity/{id}")
	public ResponseEntity<ActivityWrapper> get(@PathVariable("id") final Long id) {
		return new ResponseEntity<>(new ActivityWrapper(activityApplicationLayer.findById(id)), HttpStatus.OK);
	}
	
	
	/**
	 * Este metodo tem por responsabilidade disponibilizar um end-point para deletar uma
	 * <code>Activity</code> salva no banco de dados.
	 * 
	 * @param id objeto que deseja deletar.
	 * @return ResponseEntity<ActivityWrapper> com um status http.
	 * */
	@DeleteMapping(value = "/activity/{id}")
	public ResponseEntity<ActivityWrapper> delete(@PathVariable("id") final Long id) {
		activityApplicationLayer.deleteActivity(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * Este metodo tem por responsabilidade disponibilizar um end-point para deletar um exercise
	 * de uma respectiva <code>Activity</code>.
	 * 
	 * @param id Activity que deseja buscar.
	 * @param idExercise Exercise que deseja excluir.
	 * @return ResponseEntity<ActivityWrapper> com um status http.
	 * */
	@DeleteMapping(value = "/activity/{id}/exercise/{idExercise}")
	public ResponseEntity<ActivityWrapper> deleteExercise(@PathVariable("id") final Long id, @PathVariable("idExercise") final Long idExercise) {
		activityApplicationLayer.deleteActivityExercise(id, idExercise);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
