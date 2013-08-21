package org.nexu.spaceinvader.events;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.nexu.spaceinvader.domain.Point;
import org.nexu.spaceinvader.domain.Shape;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GUIEventManager implements OnTouchListener {
	
	public interface UserEvent {
		void onTouch(Class<?> source, View v);
	}
	
	private ShapeComparator shapeComparator = new ShapeComparator();
	private Queue<PriorityShapeWrapper> shapeLayers = new PriorityQueue<PriorityShapeWrapper>(10, shapeComparator);
	private Map<Shape, UserEvent> events = new ConcurrentHashMap<Shape, UserEvent>();
	
	private Map<Shape, PriorityShapeWrapper> shapeCache = new IdentityHashMap<Shape, PriorityShapeWrapper>();
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		for(PriorityShapeWrapper priorityShapeWrapper : shapeLayers) {
			Shape shape = priorityShapeWrapper.getShape();
			if(shape.contains(new Point(event.getX(), event.getY()))) {
				events.get(shape).onTouch(shape.getClass(), v);
				return true;
			}
		}
		return false;
	}
	
	public void subscribeShape(Shape pShape, UserEvent userEvent, int priority) {
		PriorityShapeWrapper priorityShapeWrapper = new PriorityShapeWrapper(pShape, priority);
		shapeCache.put(pShape, priorityShapeWrapper);
		events.put(pShape, userEvent);
		shapeLayers.offer(priorityShapeWrapper);
	}
	
	public void unsubcribe(Shape pShape) {
		PriorityShapeWrapper priorityShapeWrapper = shapeCache.remove(pShape);
		shapeLayers.remove(priorityShapeWrapper);
		events.remove(priorityShapeWrapper.getShape());
	}
	
	public void clear() {
		shapeLayers.clear();
		events.clear();
		shapeCache.clear();
	}
	
	
	
	private static final class PriorityShapeWrapper {
		private final Shape shape;
		private final int priority;
		
		private PriorityShapeWrapper(Shape shape, int priority) {
			this.shape = shape;
			this.priority = priority;
		}
		
		private Shape getShape() {
			return shape;
		}
		
		private int getPriority() {
			return priority;
		}
		
	}
	
	private static final class ShapeComparator implements Comparator<PriorityShapeWrapper> {

		@Override
		public int compare(PriorityShapeWrapper lhs, PriorityShapeWrapper rhs) {
			return lhs.getPriority() - rhs.getPriority();
		}
		
	}
	

}
