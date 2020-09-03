package github.hotstu.ezuniplugin.base;

/**
 * 对回调方式进行抽象
 */
public interface ResultFuture {
    Object resolve(Object result);

    Object reject(Object error);
}
