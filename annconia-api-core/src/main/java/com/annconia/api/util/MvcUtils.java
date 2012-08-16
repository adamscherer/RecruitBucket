package com.annconia.api.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;

import com.annconia.util.ObjectUtils;

abstract public class MvcUtils {

	static final Map<Integer, AnnotationWrapper> annotationCache = new HashMap<Integer, AnnotationWrapper>();

	static public Object getController(Object handler) {
		if (handler instanceof HandlerMethod) {
			handler = ((HandlerMethod) handler).getBean();
		}

		if (isControllerInstance(handler)) {
			return handler;
		} else {
			return null;
		}
	}

	static public Class<?> getControllerType(Object handler) {
		if (handler == null)
			return null;

		Class<?> clazz = null;

		if (handler instanceof HandlerMethod) {
			clazz = ((HandlerMethod) handler).getBeanType();
		} else if (handler instanceof Method) {
			clazz = ((Method) handler).getDeclaringClass();
		} else if (handler instanceof Class<?>) {
			clazz = (Class<?>) handler;
		} else {
			clazz = ClassUtils.getUserClass(handler.getClass());
		}

		if (safeFindAnnotation(clazz, Controller.class) != null) {
			return clazz;
		} else {
			return null;
		}
	}

	static public Method getMethod(Object obj) {
		if (obj == null)
			return null;

		if (obj instanceof Method) {
			return (Method) obj;
		} else if (obj instanceof HandlerMethod) {
			return ((HandlerMethod) obj).getMethod();
		} else {
			return null;
		}
	}

	static public <A extends Annotation> A findAnnotationOnMethodOrController(Object handler, Class<A> annotationType) {
		{ // method
			A annotation = findAnnotationOnMethod(handler, annotationType);

			if (annotation != null) {
				return annotation;
			}
		}

		{ // controller
			A annotation = findAnnotationOnController(handler, annotationType);

			if (annotation != null) {
				return annotation;
			}
		}

		return null;
	}

	static public <A extends Annotation> A findAnnotationOnControllerOrMethod(Object handler, Class<A> annotationType) {
		{ // controller
			A annotation = findAnnotationOnController(handler, annotationType);

			if (annotation != null) {
				return annotation;
			}
		}

		{ // method
			A annotation = findAnnotationOnMethod(handler, annotationType);

			if (annotation != null) {
				return annotation;
			}
		}

		return null;
	}

	static public boolean isControllerInstance(Object handler) {
		if (handler == null) {
			return false;
		}

		return safeFindAnnotation(ClassUtils.getUserClass(handler.getClass()), Controller.class) != null;
	}

	static public <A extends Annotation> A findAnnotationOnController(Object handler, Class<A> annotationType) {
		if (handler == null) {
			return null;
		}

		return safeFindAnnotation(getControllerType(handler), annotationType);
	}

	static public <A extends Annotation> A findAnnotationOnMethod(Object handler, Class<A> annotationType) {
		if (getMethod(handler) == null) {
			return null;
		}

		return safeFindAnnotation(getMethod(handler), annotationType);
	}

	static private <A extends Annotation> A safeFindAnnotation(Class<?> classType, Class<A> annotationType) {
		if (classType == null || annotationType == null) {
			return null;
		}

		int key = (classType.hashCode() * 17) + annotationType.hashCode();
		if (annotationCache.containsKey(key)) {
			return ObjectUtils.castOrNull(annotationCache.get(key).getAnnotation(), annotationType);
		}

		A annotation = AnnotationUtils.findAnnotation(classType, annotationType);
		annotationCache.put(key, new AnnotationWrapper(annotation));

		return annotation;
	}

	static private <A extends Annotation> A safeFindAnnotation(Method method, Class<A> annotationType) {
		if (method == null || annotationType == null) {
			return null;
		}

		int key = (method.hashCode() * 17) + annotationType.hashCode();
		if (annotationCache.containsKey(key)) {
			return ObjectUtils.castOrNull(annotationCache.get(key).getAnnotation(), annotationType);
		}

		A annotation = AnnotationUtils.findAnnotation(method, annotationType);
		annotationCache.put(key, new AnnotationWrapper(annotation));

		return annotation;
	}

	private static class AnnotationWrapper {

		private Annotation annotation;

		AnnotationWrapper(Annotation annotation) {
			this.annotation = annotation;
		}

		public Annotation getAnnotation() {
			return annotation;
		}
	}

}
