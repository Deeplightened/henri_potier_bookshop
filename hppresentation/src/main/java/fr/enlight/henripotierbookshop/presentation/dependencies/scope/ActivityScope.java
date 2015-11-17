package fr.enlight.henripotierbookshop.presentation.dependencies.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An annotation specifying the scope of a component is the lifetime of the associated activity.
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {}
