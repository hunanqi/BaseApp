package base.net;

import io.reactivex.subscribers.DisposableSubscriber;
import utils.L;

import static base.net.NetExceptionHandler.ERROR.NETERROR;

/**
 * 封装订阅网络返回Subsrciber
 */

public abstract class NetSubsrciber<T> extends DisposableSubscriber<NetResponse<T>> {


    @Override
    public void onNext(NetResponse<T> mResponse) {
        if (mResponse.getCode() == NetExceptionHandler.ERROR.REQUEST_OK) {
            OnSuccess(mResponse.getData());
        }
        else {
            //处理服务器返回错误
            String errorMessage = NetExceptionHandler.handleServerErrorCode(mResponse.getCode(),mResponse.getMsg());
            OnFailue(mResponse.getCode(),errorMessage);
        }

    }

    @Override
    public void onError(Throwable t) {
        OnFailue(NETERROR,t.getMessage());
        L.d(t.getMessage());

    }

    @Override
    public void onComplete() {

    }


    public abstract void OnSuccess(T t);

    public abstract void OnFailue(int code,String msg);

}
