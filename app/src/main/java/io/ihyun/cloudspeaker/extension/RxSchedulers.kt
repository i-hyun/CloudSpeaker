package io.ihyun.cloudspeaker.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.onToMain() = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())!!

fun <T> Single<T>.onToMain() = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())!!

fun Completable.onToMain() = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())!!