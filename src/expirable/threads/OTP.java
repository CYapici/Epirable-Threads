package expirable.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class OTP extends Observable<String> {

  private ConnectableObservable<String> activeObservable;

  private final Consumer<? super Throwable> onError = (error) -> {
    if ((error instanceof TimeoutException)) {
      onTimeOut(error);
    }
  };

  private OTPFactory container;

  private Disposable instance;

  public OTP(OTPFactory container) {

    this.container = container;

    activeObservable = this.subscribeOn(Schedulers.io()).publish();

    activeObservable.connect();

    instance = activeObservable.timeout(OTPFactory.OBJECT_LIFE_TIME, TimeUnit.SECONDS)
        .subscribe(authResult -> {
        }, onError);
  }

  protected ConnectableObservable<String> getActiveObservable() {
    return activeObservable;
  }

  protected void onTimeOut(Throwable error) {
    debug(">>>> disposed ");
    container.remove(this);
    dispose();
  }

  @Override
  protected void subscribeActual(Observer<? super String> arg0) {
    debug("<<< executing on thread:" + Thread.currentThread().getName());
  }

  public void dispose() {
    instance.dispose();
  }

  private void debug(String message) {
    System.out.println(message);
  }
}
