package fr.enlight.henripotierbookshop.presentation.presenter;

/**
 * Created by enlight on 17/11/2015.
 */
public interface AbstractPresenter {



    interface PresenterView {

        void hideLoadingView();

        void showLoadingView();

        void onLoadingFailed(String errorMessage);
    }
}
