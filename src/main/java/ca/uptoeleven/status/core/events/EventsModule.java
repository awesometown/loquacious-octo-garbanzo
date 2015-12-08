package ca.uptoeleven.status.core.events;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.*;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
public class EventsModule extends AbstractModule {

	private EventBus instance;

	private List<Object> listenerCache = new ArrayList<>();

	public EventsModule() {
	}

	@Override
	protected void configure() {
		//Automatically bind any event listeners (methods annotated with @Subscribe)
		bindListener(Matchers.any(), new TypeListener() {
			public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
				typeEncounter.register((InjectionListener<I>) i -> {
					if (instance != null) {
						instance.register(i);
					} else {
						/*
						If the eventbus hasn't been initialized yet, save this guy for later and we'll register him then.
						This is here to deal with needing to set the listeners up during Guice init but not being able
						to create an EventBus instance until later because we need the Dropwizard Environment.
						Maybe there's a better way?
						 */
						listenerCache.add(i);
					}
				});
			}
		});
	}

	@Provides
	public EventBus provideEventBus(Environment environment) {
		if (instance == null) {
			ExecutorService executor = environment.lifecycle().executorService("mailjob-%d").build();
			instance = new AsyncEventBus(executor);
			for(Object obj : listenerCache) {
				instance.register(obj);
			}
			listenerCache.clear();
		}
		return instance;
	}
}
