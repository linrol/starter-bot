package com.alinkeji.bot.utils;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

  private static ApplicationContext ctx;

  private static class ApplicationContextSetter {

    static void setContext(final ApplicationContext ctx) {
      SpringContextUtils.ctx = ctx;
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextSetter.setContext(applicationContext);
  }

  public static ApplicationContext getApplicationContext() {
    return ctx;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) throws BeansException {
    return (T) ctx.getBean(name);
  }

  public static <T> T getBean(Class<T> clazz) {
    return ctx.getBean(clazz);
  }

  /**
   * 根据接口类以及泛型获取bean
   *
   * @param interfaceClazz 接口类
   * @param <I>            接口定义
   * @return
   * @throws BeansException
   */
  public static <I> Map<String, I> getBeans(Class<I> interfaceClazz)
      throws BeansException {
    return ctx.getBeansOfType(interfaceClazz);
  }
}
