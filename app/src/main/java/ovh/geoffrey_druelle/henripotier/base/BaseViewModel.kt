package ovh.geoffrey_druelle.henripotier.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        super.onCleared()
    }
}