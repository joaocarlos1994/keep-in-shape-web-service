/*
 * @(#)ApplicationContextHolder.java 1.0 22/02/2016
 *
 * Copyright (c) 2016, Embraer. All rights reserved. Embraer S/A
 * proprietary/confidential. Use is subject to license terms.
 */

package br.com.keepinshape.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A classe <code>ApplicationContextHolder</code> simplesmente armazena o
 * contexto do Spring na aplicacao sendo executada, para que beans Spring possam
 * ser recuperados em objetos que nao sao beans do Spring
 *
 * @author Roberto Perillo
 * @version 1.0 22/02/2016
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(final ApplicationContext ctx) {
        context = ctx;
    }

    public static <T> T getBean(final Class<T> clazz) {
        return context.getBean(clazz);
    }
}