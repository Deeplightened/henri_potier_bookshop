package fr.enlight.henripotierbookshop.presentation.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import fr.enlight.henripotierbookshop.presentation.presenter.AbstractPresenter;

/**
 * The base of all fragment in the application
 */
public abstract class AbstractFragment extends Fragment implements AbstractPresenter.PresenterView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutInflateId(), container, false);
        ButterKnife.bind(this, view);

        initViews(view);

        return view;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        // Put content view for loading panel
//        setContentView(R.layout.fragment_progress_content);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Method to initialize the view configuration.
     * This method has to be redefined by subclasses.
     *
     * @param view the root view of this fragment
     */
    protected void initViews(View view){
        // To be redefined
    }

    /**
     * @return the R.layout id of the layout this fragment must inflate
     */
    protected abstract int getLayoutInflateId();


    @Override
    public void hideLoadingView() {
//        setContentShown(false);
    }

    @Override
    public void showLoadingView() {
//        setContentShown(true);
//        setContentEmpty(false);
    }

    @Override
    public void onLoadingFailed(String errorMessage) {
//        setEmptyText(errorMessage);
//        setContentShown(false);
//        setContentEmpty(true);
    }
}