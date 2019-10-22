package ovh.geoffrey_druelle.henripotier.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
//        compositeDisposable = CompositeDisposable()
        compositeDisposable.clear()
    }
}