package org.maroubra.pemsserver.utilities;

import rx.Completable;
import rx.Observable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RxUtils {

    public static <T> CompletableFuture<List<T>> fromObservable(Observable<T> observable) {
        final CompletableFuture<List<T>> future = new CompletableFuture<>();
        observable
                .doOnError(future::completeExceptionally)
                .toList()
                .forEach(future::complete);
        return future;
    }

    public static CompletableFuture fromCompletable(Completable completable) {
        final CompletableFuture future = new CompletableFuture();
        completable
                .doOnError(future::completeExceptionally)
                .toObservable()
                .single()
                .forEach(future::complete);
        return future;
    }
}
