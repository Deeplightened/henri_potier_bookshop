package fr.enlight.hpdata.interactors;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;

/**
 * Defines an Interactor class. An Interactor is an execution unit made to realize a process, combining several call to
 * long process, data manipulation or storage, network data download...
 *
 * This class is a direct implementation of the RxJava logic. The execution result transits through a Subscriber which
 * want to 'observe' the wanted result of the process.
 */
public abstract class Interactor<D> {

    private Subscription subscription = Subscriptions.empty();

    private Scheduler subscriptionScheduler;

    public Interactor(Scheduler subscriptionScheduler) {
        this.subscriptionScheduler = subscriptionScheduler;
    }

    /**
     * Execute the process of this Interactor and send its result to the Subscriber.
     *
     * @param subscriber the subscriber that will receive the interactor's processed result.
     */
    public void execute(Subscriber<D> subscriber){
        subscription = buildInteractorObservable()
                .subscribeOn(subscriptionScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Unsubscribe the current subscription.
     */
    public void unsubscribeCurrentSubscription(){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    /**
     * Builds a base Observable used when executing this Interactor. This Observable will contains
     * the business' core of the execution.
     *
     * @return the concerned Observable
     */
    protected abstract Observable<D> buildInteractorObservable();
}
