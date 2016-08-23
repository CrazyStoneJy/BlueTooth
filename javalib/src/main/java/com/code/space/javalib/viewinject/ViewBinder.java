package com.code.space.javalib.viewinject;

/**
 * Created by shangxuebin on 16-5-18.
 */
public class ViewBinder {

    private static IBinderRouter binderRouter;
    static {
        try {
            Class<?> clazz = Class.forName(InjectProcessor.ROUTER_FULL_NAME);
            binderRouter = (IBinderRouter) clazz.getConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException("ViewBinder 异常");
        }
    }

    public static void bind(Object target)   {
        bind(target,target);
    }

    public static void bind(Object target, Object viewHolder){
        IViewFinder finder = binderRouter.getViewFinder(target);
        if(finder!=null)finder.handleObject(target,viewHolder);
    }

}
