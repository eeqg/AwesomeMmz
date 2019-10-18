package com.example.wp.awesomemmz.skill.aspect;

import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wp.awesomemmz.APP;
import com.example.wp.awesomemmz.common.MainHelper;
import com.example.wp.resource.utils.LogUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;

/**
 * 处理切面的类
 * 例如做日志埋点，性能监控，动态权限控制
 * <p>
 * Created by wp on 2019/10/7.
 * <p>
 * execution语法示例:
 * execution(* com.howtodoinjava.EmployeeManager.*( .. ))
 * 匹配EmployeeManger接口中所有的方法
 * execution(* EmployeeManager.*( .. ))
 * 当切面方法和EmployeeManager接口在相同的包下时，匹配EmployeeManger接口中所有的方法
 * execution(public * EmployeeManager.*(..))
 * 当切面方法和EmployeeManager接口在相同的包下时，匹配EmployeeManager接口的所有public方法
 * execution(public EmployeeDTO EmployeeManager.*(..))
 * 匹配EmployeeManager接口中权限为public并返回类型为EmployeeDTO的所有方法。
 * execution(public EmployeeDTO EmployeeManager.*(EmployeeDTO, ..))
 * 匹配EmployeeManager接口中权限为public并返回类型为EmployeeDTO，第一个参数为EmployeeDTO类型的所有方法。
 * execution(public EmployeeDTO EmployeeManager.*(EmployeeDTO, Integer))
 * 匹配EmployeeManager接口中权限为public、返回类型为EmployeeDTO，参数明确定义为EmployeeDTO,Integer的所有方法。
 * "execution(@com.xyz.service.BehaviorTrace * *(..))"
 * 匹配注解为"@com.xyz.service.BehaviorTrace"，返回值为任意类型，任意包名下的任意方法
 * <p>
 * advice 通知类型:
 * Before	在方法执行之前执行要插入的代码
 * After	在方法执行之后执行要插入的代码
 * AfterReturning	在方法执行后，返回一个结果再执行，如果没结果，用此修辞符修辞是不会执行的
 * AfterThrowing	在方法执行过程中抛出异常后执行，也就是方法执行过程中，如果抛出异常后，才会执行此切面方法。
 * Around	在方法执行前后和抛出异常时执行（前面几种通知的综合）
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
     * <p>
     * around() 完全替换函数(可以手动再调用原函数)
     * <p>
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
            } catch (Exception e) {
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
    //-------------------------------------aop 埋点---------------------------------------------------

    @Pointcut("execution(* android.app.Activity+.onCreate(..))")//切入点
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
        // Object target = joinPoint.getTarget();
        // String className = target.getClass().getName();
        // Log.d(TAG, "-----aroundJoinPageOpenPoint: "+className);
        Log.d(TAG, "-----aroundJoinPageOpenPoint: " + joinPoint.getThis()
                + "\n" + joinPoint.getSignature().toLongString());

        joinPoint.proceed();
    }

    @Around("activityOnDestroyPointcut() || fragmentOnDestroyPointcut() || fragmentV4OnDestroyPointcut()")
    public void aroundJoinPageClosePoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        // Object target = joinPoint.getTarget();
        // String className = target.getClass().getName();
        // Log.d(TAG, "-----aroundJoinPageClosePoint: " + className);
        Log.d(TAG, "-----aroundJoinPageClosePoint: " + joinPoint.getThis()
                + "\n" + joinPoint.getSignature().toLongString());

        joinPoint.proceed();
    }

    /**
     * e.g: 监控用户行为
     */
    @Around("execution(* com.example.wp.resource.utils.LaunchUtil.launchActivity(..))")
    public void monitorBehavior(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "------launch activity : " + joinPoint.getSignature().toString());
        joinPoint.proceed();
    }

    @Pointcut("execution(@com.example.wp.awesomemmz.skill.aspect.NeedMonitor  * *(..)) && @annotation(monitor)")
    public void monitorTest(NeedMonitor monitor) {
    }

    @Around("monitorTest(monitor)")
    public void monitorTest2(ProceedingJoinPoint proceedingJoinPoint, NeedMonitor monitor) throws Throwable {
        //方法执行前
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        NeedMonitor behaviorTrace = methodSignature.getMethod().getAnnotation(NeedMonitor.class); // 拿到注解
        long begin = System.currentTimeMillis();
        Log.i(TAG, "拿到需要切的方法啦，执行前");

        proceedingJoinPoint.proceed(); // 执行被切的方法

        //方法执行完成
        long end = System.currentTimeMillis();
        // Log.i(TAG, behaviorTrace.value() + "(" + behaviorTrace.type() + ")" + " 耗时：" +  (end - begin) + "ms");
        Log.i(TAG, monitor.value() + "(" + monitor.type() + ")" + " 耗时：" + (end - begin) + "ms");
    }

    //------------------------------------------------------------------------
    //-------------------------------------性能检测-----------------------------------
    //------可以在项目的任何一个方法中加上@DebugTrace注解，监控性能状况-------------
    private static final String POINTCUT_METHOD =
            "execution(@com.example.wp.awesomemmz.skill.aspect.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.example.wp.awesomemmz.skill.aspect.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {
    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法信息对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前对象
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        //获取当前对象，通过反射获取类别详细信息
        //String className2 = joinPoint.getThis().getClass().getName();

        //开始监听
        long startTime = System.currentTimeMillis();
        //调用原方法的执行。
        Object result = joinPoint.proceed();
        //监听结束
        long endTime = System.currentTimeMillis();

        Log.d(TAG, buildLogMessage(methodName, endTime - startTime));

        return result;
    }

    /**
     * Create a log message.
     *
     * @param methodName     A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("Gintonic --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }

    /**
     * 收集抛出异常信息
     *
     * @param throwable
     */
    @AfterThrowing(pointcut = "call(* *..*(..))", throwing = "throwable")  // "throwable"必须和下面参数名称一样
    public void anyFuncThrows(Throwable throwable) {
        Log.e(TAG, "-----throwable--->" + throwable);
    }

    /**
     * 获取返回值
     *
     * @param height
     */
    @AfterReturning(pointcut = "call(* com.example.wp.awesomemmz.skill.AspectTestActivity.getHeight())", returning = "height")
    public void getHeight(int height) {  // height必须和上面"height"一样
        Log.d(TAG, "-----height:" + height);
    }

    //------- check permission ------
    @Pointcut("execution(@com.example.wp.awesomemmz.skill.aspect.CheckPermission * *(..)) && @annotation(checkPermission)")
    public void checkPermission(CheckPermission checkPermission) {
    }

    @Around("checkPermission(checkPermission)")
    public void aroundCheckPermission(ProceedingJoinPoint joinPoint, CheckPermission checkPermission) throws Throwable {
        //从注解信息中获取声明的权限。
        String[] permissions = checkPermission.value();
        Log.d(TAG, joinPoint.toShortString());
        StringBuilder stringBuilder = new StringBuilder();
        for (String permission : permissions) {
            stringBuilder.append(", ");
            stringBuilder.append(permission);
        }
        Log.d(TAG, "needed permission is " + stringBuilder.toString());
        ArrayList<String> permissionsList = MainHelper.getInstance().checkPermission(APP.INSTANCE, permissions);
        if (!permissionsList.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            // String[] strings = new String[permissionsList.size()];
            for (String permission : permissionsList) {
                builder.append(", ");
                builder.append(permission);
            }
            Log.d(TAG, "no permission is " + builder.toString());
            APP.toast("缺少权限");
            // requestPermissions(permissionsList.toArray(strings), CODE_PERMISSION);
            // ActivityCompat.requestPermissions(permissions, 1);
        } else {
            joinPoint.proceed();
        }
    }
    //------- check permission ------
}
