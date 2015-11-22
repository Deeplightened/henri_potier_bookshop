package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;

/**
 * Defines the base of all Presenter in this application.
 * A Presenter is a class charged to prepare the data the view will use. It can download through Interactor
 * , compute some results or ask the view to change state (for loading view for example).
 * The Presenter notify the view through the listener PresentableView, an interface the concerned view must implements.
 */
public interface AbstractPresenter {

    void resume();

    void pause();

    void create();

    void destroy();

    void refresh();

    /**
     * Defines the base of all PresentableView. A view must implements this interface and register to the Presenter
     * to be notify with the wanted data or state evolutions.
     */
    interface PresentableView {

        Context getContext();

        void hideLoadingView();

        void showLoadingView();

        void onLoadingFailed(String errorMessage);
    }
}
