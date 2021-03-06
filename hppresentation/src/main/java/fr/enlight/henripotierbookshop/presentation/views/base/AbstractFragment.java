package fr.enlight.henripotierbookshop.presentation.views.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.presenter.AbstractPresenter;

/**
 * The base of all fragment in the application
 */
public abstract class AbstractFragment extends Fragment implements AbstractPresenter.PresentableView {

    @Bind(R.id.progress_error_textview)
    TextView progressErrorTextView;

    @Bind(R.id.progress_error_container)
    ViewGroup progressErrorContainer;

    @Bind(R.id.progress_container)
    ViewGroup progressContainer;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    private OnRetryListener retryListener;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Init loading view
        ViewGroup loadingView = (ViewGroup) inflater.inflate(R.layout.fragment_progress_content, container, false);

        View contentView = inflater.inflate(getLayoutInflateId(), loadingView, false);
        loadingView.addView(contentView, 0);

        ButterKnife.bind(this, loadingView);
        initViews(contentView);

        return loadingView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnRetryListener){
            retryListener = (OnRetryListener) context;
        }
    }

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
     * Method called when the user has asked to retry loading.
     * This method can be redefined to handle retry operations.
     */
    protected void onLoadRetry(){
        if(retryListener != null){
            retryListener.onRetry(getClass());
        }
    }

    /**
     * @param retryListener the retry listener this fragment must use
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.retryListener = retryListener;
    }

    /**
     * @return the R.layout id of the layout this fragment must inflate
     */
    protected abstract int getLayoutInflateId();


    @Override
    public void showLoadingView() {
        progressContainer.setVisibility(View.VISIBLE);
        progressErrorContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        progressContainer.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingFailed(String errorMessage) {
        progressContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressErrorTextView.setText(errorMessage);
        progressErrorContainer.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.progress_retry_button)
    public void onRetryClicked(){
        onLoadRetry();
    }

    /**
     * Defines a class listening to retry events, sended by the containing fragment.
     */
    public interface OnRetryListener{

        /**
         * Notify a retry event.
         *
         * @param fragmentOrigin the fragment from where the retry event come from
         */
        void onRetry(Class<? extends AbstractFragment> fragmentOrigin);
    }
}