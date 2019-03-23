package com.example.wp.awesomemmz.frame;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.wp.resource.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wp on 2019/3/23.
 */
public class TestViewModel extends ViewModel {
	private final CompositeDisposable mDisposable = new CompositeDisposable();
	private MutableLiveData<Long> countLiveData = new MutableLiveData<>();
	
	public LiveData<Long> getCountLiveData() {
		return countLiveData;
	}
	
	public void startCount() {
		Disposable disposable = Observable.interval(1, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						LogUtils.d("----" + aLong);
						countLiveData.postValue(aLong);
					}
				});
		mDisposable.add(disposable);
	}
	
	@Override
	protected void onCleared() {
		super.onCleared();
		LogUtils.d("-----onCleared()-----");
		// mDisposable.clear();
		mDisposable.dispose();
	}
}
