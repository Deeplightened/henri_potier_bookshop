package fr.enlight.henripotierbookshop.presentation.dependencies.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by enlight on 19/11/2015.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface BookScope {
}
