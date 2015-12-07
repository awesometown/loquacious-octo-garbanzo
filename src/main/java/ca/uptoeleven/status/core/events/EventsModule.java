package ca.uptoeleven.status.core.events;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventsModule extends AbstractModule {
	@Override
	protected void configure() {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		EventBus eventBus = new AsyncEventBus(executor);
		bind(EventBus.class).toInstance(eventBus);

		//Automatically bind any event listeners (methods annotated with @Subscribe)
		bindListener(Matchers.any(), new TypeListener() {
			public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
				typeEncounter.register((InjectionListener<I>) i -> {
					eventBus.register(i);
				});
			}
		});
	}
}
