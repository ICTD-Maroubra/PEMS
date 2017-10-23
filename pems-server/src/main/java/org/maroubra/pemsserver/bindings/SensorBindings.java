package org.maroubra.pemsserver.bindings;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;
import org.maroubra.pemsserver.monitoring.Sensor;
import org.maroubra.pemsserver.monitoring.annotations.FactoryClass;
import org.maroubra.pemsserver.monitoring.nordic.Thingy52Sensor;
import org.maroubra.pemsserver.monitoring.sensortag.SensortagSensor;
import org.maroubra.pemsserver.monitoring.utsapi.WebSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public class SensorBindings extends AbstractModule {

    private static Logger log = LoggerFactory.getLogger(SensorBindings.class);

    @Override
    protected void configure() {
        final MapBinder<String, Sensor.Factory<? extends Sensor>> sensorMapBinder = MapBinder.newMapBinder(binder(), TypeLiteral.get(String.class), new TypeLiteral<Sensor.Factory<? extends Sensor>>() {});

        installSensor(sensorMapBinder, SensortagSensor.class);
        installSensor(sensorMapBinder, Thingy52Sensor.class);
        installSensor(sensorMapBinder, WebSensor.class);
    }

    private <T extends Sensor> void installSensor(MapBinder<String, Sensor.Factory<? extends Sensor>> sensorMapBinder,
                                                  Class<T> target) {

        final Class<? extends Sensor.Factory<? extends Sensor>> factoryClass =
                (Class<? extends Sensor.Factory<? extends Sensor>>)
                        findInnerClassAnnotatedWith(FactoryClass.class, target, Sensor.Factory.class);

        if (factoryClass == null) {
            log.error("Unable to find an inner class annotated with @FactoryClass in transport {}. This transport will not be available!",
                    target);
            return;
        }

        install(new FactoryModuleBuilder().implement(Sensor.class, target).build(factoryClass));
        sensorMapBinder.addBinding(target.getCanonicalName()).to(Key.get(factoryClass));
    }

    @Nullable
    private Class<?> findInnerClassAnnotatedWith(Class<? extends Annotation> annotationClass,
                                                   Class<?> containingClass,
                                                   Class<?> targetClass) {
        final Class<?>[] declaredClasses = containingClass.getDeclaredClasses();
        Class<?> annotatedClass = null;
        for (final Class<?> declaredClass : declaredClasses) {
            if (!declaredClass.isAnnotationPresent(annotationClass)) {
                continue;
            }
            if (targetClass.isAssignableFrom(declaredClass)) {
                if (annotatedClass != null) {
                    log.error("Multiple annotations for {} found in {}. This is invalid.", annotatedClass.getSimpleName(), containingClass);
                    return null;
                }
                annotatedClass = declaredClass;
            } else {
                log.error("{} annotated as {} is not extending the expected {}. Did you forget to implement the correct interface?",
                        declaredClass, annotationClass.getSimpleName(), targetClass);
                return null;
            }
        }
        return annotatedClass;
    }

}
