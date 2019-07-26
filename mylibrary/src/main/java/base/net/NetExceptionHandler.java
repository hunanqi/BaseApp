package base.net;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * 网络异常处理
 */

public final class NetExceptionHandler {


    /**
     * 处理一般网络问题
     */
    public final ResponseException handleException( Throwable e) {

        ResponseException ex;

        if(e instanceof HttpException) {

            ex = new ResponseException(e);
            int code = ((HttpException)e).code();
            ex.setMessage(code == ERROR.UNAUTHORIZED?"我们的访问被服务器拒绝啦~(" + code + ")"
                    :(code == ERROR.FORBIDDEN?"服务器资源不可用(" + code + ")"
                    :(code == ERROR.NOT_FOUND?"我们好像迷路了，找不到服务器(" + code + ")"
                    :(code == ERROR.REQUEST_TIMEOUT?"糟糕，我们的请求超时了，请检查网络连接后重试(" + code + ")"
                    :(code == ERROR.GATEWAY_TIMEOUT?"糟糕，我们的请求超时了，请检查网络连接后重试(" + code+ ")"
                    :(code == ERROR.INTERNAL_SERVER_ERROR?"服务器正在开小差，请稍后重试(" + code+ ")"
                    :(code == ERROR.BAD_GATEWAY?"服务器正在开小差，请稍后重试(" + code + ")"
                    :(code == ERROR.SERVICE_UNAVAILABLE?"服务器可能正在维护，请稍后重试(" + code + ")"
                    :"网络异常，请检查网络连接后重试(" + code + ")"))))))));
            return ex;
        } else if(!(e instanceof JsonParseException) && !(e instanceof JSONException) && !(e instanceof ParseException)) {
            if(e instanceof ConnectException) {
                ex = new NetExceptionHandler.ResponseException(e);
                ex.setMessage("连接失败，网络连接可能存在异常，请检查网络后重试(" + ERROR.NETWORK_ERROR+ ")");
                return ex;
            } else if(e instanceof SSLHandshakeException) {
                ex = new NetExceptionHandler.ResponseException(e);
                ex.setMessage("证书验证失败(" + ERROR.SSL_ERROR + ")");
                return ex;
            } else if(e instanceof UnknownHostException) {
                ex = new NetExceptionHandler.ResponseException(e);
                ex.setMessage("无法连接到服务器，请检查你的网络或稍后重试(" + ERROR.HOST_ERROR + ")");
                return ex;
            } else if (e instanceof NullPointerException){
                ex = new NetExceptionHandler.ResponseException(e);
                ex.setMessage("无法连接到服务器，请检查你的网络或稍后重试(" + ERROR.NUll_ERROR + ")");
                return ex;
            } else {
                ex = new NetExceptionHandler.ResponseException(e);
                ex.setMessage("出现了未知的错误，提交一个反馈给我们吧~(" + ERROR.UNKNOWN + ")");
                return ex;
            }
        } else {
            ex = new NetExceptionHandler.ResponseException(e);
            ex.setMessage("数据解析错误，这可能是一个bug，欢迎提交反馈(" + ERROR.PARSE_ERROR + ")");
            return ex;
        }
    }


    /**
     * 处理服务器返回的错误码
     */
    public static String handleServerErrorCode(int code,String msg)
    {
        String errorMessage = "";
        switch (code)
        {
            case 51: errorMessage = "短信请求失败";
                break;
            case 52: errorMessage = "无短信记录";
                break;
            case 54: errorMessage = "验证码已过期";
                break;
            case 55: errorMessage = "验证码错误";
                break;
            case 56: errorMessage = "同一个手机号同一个验证码类型，每30秒只能获取一条";
                break;
            case 57: errorMessage = "同一个手机号验证码内容，每小时最多能获取3条";
                break;
            case 58: errorMessage = "同一个手机号验证码内容，24小时内最多能获取到10条";
                break;
            case 71: errorMessage = "旧密码错误";
                break;
            case 72: errorMessage = msg;
                break;
            case 73: errorMessage = "两次交易密码输入不一致";
                break;
            case 74: errorMessage = "新密码和旧密码相同";
                break;
            case 75: errorMessage = "错误输入支付密码已达到每日限制，请明日再试";
                break;
            case 250: errorMessage = "您在投单黑名单之中，请联系客服";
                break;
            case 251: errorMessage = "您未支付的订单超过限制，请支付或者取消未支付的订单后再继续投单";
                break;
            case 252: errorMessage = "您今日已取消的订单超过限制，不能再投放订单，请明日再试";
                break;
            case 253: errorMessage = "您选择的门店中有部分在您所选时间段停播日期前已与平台解约，请重新选择门店";
                break;
            case 254: errorMessage =msg;
                break;
            case 255: errorMessage = "该门店未上线，请上线后投放";
                break;
            case 260: errorMessage = "订单取消失败";
                break;
            case 270: errorMessage = "付款类型有误";
                break;
            case 271: errorMessage = "支付未完成";
                break;
            case 272: errorMessage = "签名失败";
                break;
            case 280: errorMessage = "提交发票信息失败";
                break;
            case 281: errorMessage = "发票地址删除失败，请重试";
                break;
            case 350: errorMessage = "订单已被取消";
                break;
            case 401: errorMessage = "设备码错误，非平台设备";
                break;
            case 402: errorMessage = "非门店下的设备";
                break;
            case 651: errorMessage = "银行卡已存在";
                break;
            default:errorMessage="网络错误,请稍后重试";
                break;
        }
        return errorMessage;
    }


    public static final class ERROR {

        public static final int NETERROR = -1; // 泛指网络错误

        private static final int UNAUTHORIZED = 401;
        private static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        private static final int REQUEST_TIMEOUT = 408;
        private static final int INTERNAL_SERVER_ERROR = 500;
        private static final int BAD_GATEWAY = 502;
        private static final int SERVICE_UNAVAILABLE = 503;
        private static final int GATEWAY_TIMEOUT = 504;

        private static final int UNKNOWN = 1000;
        private static final int PARSE_ERROR = 1001;
        private static final int NETWORK_ERROR = 1002;
        private static final int HTTP_ERROR = 1003;
        private static final int SSL_ERROR = 1005;
        private static final int HOST_ERROR = 1006;
        private static final int NUll_ERROR = 5555;

        public static final int REQUEST_OK = 0; // 请求成功
        public static final int CHAT_SERVER_ERROR = 600; //聊天服务器错误
        public static final int NOT_LOGIN = 5; //  Token失效
        public static final int NOT_FOUND_RESOURCE = 2404; // 资源不存在
        public static final int NOT_ENOUNGH = 1015; // 积分不足
        public static final int NOT_VIP = 1040; // 不是vip用户

    }


    public static final class ResponseException extends Exception {

        private String message;

        public String getMessage() {
            return this.message;
        }

        public void setMessage( String var1) {
            this.message = var1;
        }

        public ResponseException(Throwable throwable) {
            super(throwable);
        }
    }

    public static class  HttpResponseFunc<T> implements Function<Throwable,Flowable<T>>
    {
        @Override
        public Flowable<T> apply(@NonNull Throwable throwable) throws Exception {
            return Flowable.error(new NetExceptionHandler().handleException(throwable));
        }
    }

}

