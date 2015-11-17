package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import android.content.Context;

import dagger.Component;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.ActivityModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;

/**
 * A Dagger component that expose the Context of an Activity to some sub-components.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    // Expose to sub-component
    Context context();
}
