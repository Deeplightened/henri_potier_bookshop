package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import dagger.Subcomponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BookCartModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;
import fr.enlight.henripotierbookshop.presentation.views.activities.BookCartActivity;

/**
 * Created by enlight on 19/11/2015.
 */
@ActivityScope
@Subcomponent(modules = BookCartModule.class)
public interface BookCartComponent {

    void inject(BookCartActivity activity);
}
