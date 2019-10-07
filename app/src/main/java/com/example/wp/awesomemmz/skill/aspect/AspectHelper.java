package com.example.wp.awesomemmz.skill.aspect;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 处理切面的类
 * <p>
 * Created by wp on 2019/10/7.
 */
@Aspect
public class AspectHelper {
    private final String TAG = AspectHelper.class.getSimpleName();

    private static final Long FILTER_TIME = 1000L;

    private boolean enableDoubleClick = false;
    private long lastClickTime = 0;

    public AspectHelper() {
        Log.d(TAG, "-----AspectHelper constructor");
    }

    /**
     * before() 在切入点之前操作
     */
    @Before("execution(@com.example.wp.awesomemmz.skill.aspect.DoubleClick  * *(..))")
    public void enableDoubleClick(JoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "-----enableDoubleClick");
        enableDoubleClick = true;
    }

    /**
     * 防止重复点击事件
     *
     * around() 完全替换函数(可以手动再调用原函数)
     *
     * "execution(* android.view.View.OnClickListener.onClick(..))"对应Pointcuts,即用一个类似正则表达式来告诉控制器你需要hook哪些函数(方法)
     * <p>
     * execution:表示hook的流程是函数执行过程(Join Points有很多种,execution只是其中一种,具体可参见AspectJ官方文档)
     * <p>
     * android.view.View.OnClickListener.onClick(..)):表示android.view.View.OnClickListener该类(或接口)下的所有名为onClick,参数个数未知,参数类型未知的函数
     */
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClickHook(ProceedingJoinPoint joinPoint) throws Throwable {

        Object target = joinPoint.getTarget();
        if (target != null) {
            Log.d(TAG, "-----target: " + target.getClass().getName());
        }
        Object[] args = joinPoint.getArgs();
        if (args.length >= 1 && args[0] instanceof View) {
            View view = (View) args[0];
            int id = view.getId();
            try {
                String entryName = view.getResources().getResourceEntryName(id);
                // TrackPoint.onClick(className, entryName);
                Log.d(TAG, "-----entryName: " + entryName);
            } catch (Exception e){
                Log.e(TAG, "Unable to find resource by ID");
            }
        }

        Log.d(TAG, "-----enableDoubleClick: " + enableDoubleClick);
        if (enableDoubleClick || System.currentTimeMillis() - lastClickTime >= FILTER_TIME) {
            joinPoint.proceed();//执行原来的代码
            lastClickTime = System.currentTimeMillis();
            enableDoubleClick = false;
        } else {
            //hook click.
            Log.d(TAG, "-----onClickHook");
        }
    }

    //----------------------------------------------------------------------------------------
    //-------------------------------------test 埋点---------------------------------------------------

    @Pointcut("execution(* android.app.Activity+.onCreate(..))")
    public void activityOnCreatePointcut() {
    }

    @Pointcut("execution(* android.app.Activity+.onDestroy(..))")
    public void activityOnDestroyPointcut() {
    }

    @Pointcut("execution(* android.app.Fragment+.onCreate(..))")
    public void fragmentOnCreatePointcut() {
    }

    @Pointcut("execution(* android.support.v4.app.Fragment+.onCreate(..))")
    public void fragmentV4OnCreatePointcut() {
    }

    @Pointcut("execution(* android.app.Fragment+.onDestroy(..))")
    public void fragmentOnDestroyPointcut() {
    }

    @Pointcut("execution(* android.support.v4.app.Fragment+.onDestroy(..))")
    public void fragmentV4OnDestroyPointcut() {
    }

    @Around("activityOnCreatePointcut() || fragmentOnCreatePointcut() || fragmentV4OnCreatePointcut()")
    public void aroundJoinPageOpenPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String className = target.getClass().getName();
        Log.d(TAG, "-----aroundJoinPageOpenPoint: "+className);

        joinPoint.proceed();
    }

    @Around("activityOnDestroyPointcut() || fragmentOnDestroyPointcut() || fragmentV4OnDestroyPointcut()")
    public void aroundJoinPageClosePoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String className = target.getClass().getName();
        Log.d(TAG, "-----aroundJoinPageClosePoint: "+className);

        joinPoint.proceed();
    }
}
