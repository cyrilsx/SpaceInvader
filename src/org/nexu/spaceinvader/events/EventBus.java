package org.nexu.spaceinvader.events;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
	
	public interface Subcriber<T> {
		void onEvent(T event);
	}
	
	private Map<Class<?>, List<Subcriber<?>>> subcribers = new IdentityHashMap<Class<?>, List<Subcriber<?>>>();
	
	public <T> void subscribe(Class<T> clazz, Subcriber<T> subcriber) {
		List<Subcriber<?>> subs = subcribers.get(clazz);
		if(subs == null) {
			subs = new ArrayList<EventBus.Subcriber<?>>();
			subcribers.put(clazz, subs);
		}
		subs.add(subcriber);
	}
	
	public <T> void unsubscribe(Class<T> clazz, Subcriber<T> subcriber) {
		List<Subcriber<?>> subs = subcribers.get(clazz);
		if(subs == null) {
			return;
		}
		subs.remove(subcriber);
	}
	
	public <T> void publish(T event) {
		List<Subcriber<?>> subs  = subcribers.get(event.getClass());
		for(Subcriber<?> sub : subs) {
			((Subcriber<T>)sub).onEvent(event);
		}
	}
	

}
