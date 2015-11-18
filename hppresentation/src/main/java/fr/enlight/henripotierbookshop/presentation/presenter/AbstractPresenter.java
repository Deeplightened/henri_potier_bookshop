package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;

/**
 * Created by enlight on 17/11/2015.
 */
public interface AbstractPresenter {

    void resume();

    void pause();

    void create();

    void destroy();

    interface PresentableView {

        Context getContext();

        void hideLoadingView();

        void showLoadingView();

        void onLoadingFailed(String errorMessage);
    }
}
