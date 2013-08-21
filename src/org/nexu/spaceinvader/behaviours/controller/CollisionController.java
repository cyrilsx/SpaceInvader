package org.nexu.spaceinvader.behaviours.controller;

import org.nexu.spaceinvader.activities.LifeCycle;
import org.nexu.spaceinvader.behaviours.CollisionListener;
import org.nexu.spaceinvader.data.domain.ShapeDescriptor;
import org.nexu.spaceinvader.domain.Circle;
import org.nexu.spaceinvader.domain.Point;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.elements.Element;
import org.nexu.spaceinvader.utils.MathUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CollisionController implements LifeCycle {
	
    private Set<Shape> manageShape;
    private final Map<Shape, TargetHolder> collisionListener = new ConcurrentHashMap<Shape, TargetHolder>();

    private InFieldEnemyRegistry mInFieldEnemyRegistry ;

    private int mScreenWidth;
    private int mScreenHeight;

    private final Random mRandom = new Random();

    private int nbObjectDraw = 0;

    private final Map<ShapeDescriptor.TypeShape, Map<ShapeDescriptor.TypeShape, CollisionHandler>> collisionHandlers =
            new EnumMap<ShapeDescriptor.TypeShape, Map<ShapeDescriptor.TypeShape, CollisionHandler>>(ShapeDescriptor.TypeShape.class);

    interface CollisionHandler {
        boolean areOverLapping(Shape shapeA, Shape shapeB);
        boolean containsPoint(Shape shapeA, Point point);
    }

	
	public CollisionController(InFieldEnemyRegistry inFieldEnemyRegistry) {
        manageShape = new HashSet<Shape>();;
        mInFieldEnemyRegistry = inFieldEnemyRegistry;
	}

    private void init() {
        final PolygonCollisionHandler polygonCollisionHandler = new PolygonCollisionHandler();
        final PolygonCircleCollisionHandler circleCollisionHandler = new PolygonCircleCollisionHandler();
        final PureCircleCollisionHandler pureCircleCollisionHandler = new PureCircleCollisionHandler();

        for(ShapeDescriptor.TypeShape type : ShapeDescriptor.TypeShape.values()) {
            collisionHandlers.put(type, new EnumMap<ShapeDescriptor.TypeShape, CollisionHandler>(ShapeDescriptor.TypeShape.class));
        }
        collisionHandlers.get(ShapeDescriptor.TypeShape.RECT).put(ShapeDescriptor.TypeShape.RECT, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.RECT).put(ShapeDescriptor.TypeShape.CUSTOM, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.RECT).put(ShapeDescriptor.TypeShape.DRAWABLE, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.RECT).put(ShapeDescriptor.TypeShape.CIRCLE, circleCollisionHandler);

        collisionHandlers.get(ShapeDescriptor.TypeShape.CUSTOM).put(ShapeDescriptor.TypeShape.RECT, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.CUSTOM).put(ShapeDescriptor.TypeShape.CUSTOM, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.CUSTOM).put(ShapeDescriptor.TypeShape.DRAWABLE, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.CUSTOM).put(ShapeDescriptor.TypeShape.CIRCLE, circleCollisionHandler);

        collisionHandlers.get(ShapeDescriptor.TypeShape.DRAWABLE).put(ShapeDescriptor.TypeShape.RECT, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.DRAWABLE).put(ShapeDescriptor.TypeShape.CUSTOM, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.DRAWABLE).put(ShapeDescriptor.TypeShape.DRAWABLE, polygonCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.DRAWABLE).put(ShapeDescriptor.TypeShape.CIRCLE, circleCollisionHandler);

        collisionHandlers.get(ShapeDescriptor.TypeShape.CIRCLE).put(ShapeDescriptor.TypeShape.RECT, circleCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.CIRCLE).put(ShapeDescriptor.TypeShape.CUSTOM, circleCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.CIRCLE).put(ShapeDescriptor.TypeShape.DRAWABLE, circleCollisionHandler);
        collisionHandlers.get(ShapeDescriptor.TypeShape.CIRCLE).put(ShapeDescriptor.TypeShape.CIRCLE, pureCircleCollisionHandler);
    }


    @Override
    public List<Shape> onLoad(int sWitdh, int sHeight) {
        this.mScreenHeight = sHeight;
        this.mScreenWidth = sWitdh;
        return Collections.EMPTY_LIST;
    }


    public void register(Shape srcShape, Shape dstShape) {
        TargetHolder targetHolder = collisionListener.get(srcShape);
        if(targetHolder == null) {
            targetHolder = new TargetHolder();
            collisionListener.put(srcShape, targetHolder);
        }

        targetHolder.add(dstShape);
    }

    public void register(Shape srcShape, InFieldEnemyRegistry.Group group) {
        TargetHolder targetHolder = collisionListener.get(srcShape);
        if(targetHolder == null) {
            targetHolder = new TargetHolder();
            collisionListener.put(srcShape, targetHolder);
        }
        targetHolder.add(group);
    }

    public void clean() {
        collisionListener.clear();
    }
	

	public void checkForCollision() {
        for(Map.Entry<Shape, TargetHolder> shape : collisionListener.entrySet()) {
            Shape shapeKey = shape.getKey();
            for(Shape dstShape : shape.getValue().getTargets()) {
                if(collisionHandlers.get(shapeKey.getTypeShape()).get(dstShape.getTypeShape()).areOverLapping(shapeKey, dstShape)) {
                        if(shapeKey instanceof Element && dstShape instanceof Element) {
                            Element shapeKeyElement = (Element) shapeKey;
                            Element dstShapeElement = (Element) dstShape;

                            shapeKeyElement.onCollision(dstShapeElement);
                            dstShapeElement.onCollision(shapeKeyElement);
                        }
                }
            }
        }



        if(nbObjectDraw == manageShape.size()) {
            nbObjectDraw = 0;
        }
    }

    private Point[] getCloserPoint(Point gravityP, Point[] shape) {
        return MathUtil.isCloser(gravityP, 2, shape);
    }


    /**
     *
     * @param segPointA
     * @param segPointB
     * @param segPointI
     * @param segPointJ
     * @return -1 when seg are parallel, 0 when they are crossing, 1 when they are not.
     */
    private int isIntersectSegment(Point segPointA,Point segPointB,Point segPointI,Point segPointJ) {
        Point vecAB = new Point(segPointB.getX() - segPointA.getX(), segPointB.getY() - segPointA.getY());
        Point vecIP = new Point(segPointJ.getX() - segPointI.getX(), segPointJ.getY() - segPointI.getY());

        double denom = vecAB.getX()*vecIP.getY() - vecAB.getY()*vecIP.getX();
        if (denom==0)
            return -1;
        double t = - (segPointA.getX()*vecIP.getY()-segPointI.getX()*vecIP.getY()-vecIP.getX()*segPointA.getY()+vecIP.getX()*segPointI.getY()) / denom;
        if (t<0 || t>=1)
            return 0;
        double u = - (-vecAB.getX()*segPointA.getY()+vecAB.getX()*segPointI.getY()+vecAB.getY()*segPointA.getX()-vecAB.getY()*segPointI.getX()) / denom;
        if (u<0 || u>=1)
            return 0;
        return 1;
    }



    private final boolean isPointInside(Point[] polygon, Point point) {
        Point infinite = new Point(mScreenHeight + mRandom.nextInt(100) + 100, mScreenHeight + mRandom.nextInt(100) + 100);
        int nbIntersection = 0;
        for(int i = 0; i < polygon.length; i++) {
              Point a = polygon[i];
              Point b = null;
              if(i == polygon.length-1) {
                b = polygon[0];
              } else {
                  b = polygon[i+1];
              }
            int intersection = isIntersectSegment(a,b, infinite, point);
            if(intersection == -1) {
                isPointInside(polygon, point);
            }
            nbIntersection += intersection;
        }
        return nbIntersection % 2 == 1;
    }

    private final class PolygonCollisionHandler implements CollisionHandler {

        @Override
        public boolean areOverLapping(Shape shapeA, Shape shapeB) {
            for(Point point : shapeB.getCollisionBoxes()) {
                if(isPointInside(shapeA.getCollisionBoxes(), point)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean containsPoint(Shape shapeA, Point point) {
            return isPointInside(shapeA.getCollisionBoxes(), point);
        }
    }

    private class PolygonCircleCollisionHandler implements CollisionHandler {

        @Override
        public boolean areOverLapping(Shape shapeA, Shape shapeB) {
            Shape polygon = shapeA;
            Circle circle = (Circle) shapeB;
            if(shapeB.getTypeShape() != ShapeDescriptor.TypeShape.CIRCLE) {
                  polygon = shapeB;
                    circle = (Circle) shapeA;
            }
            for(Point point : polygon.getCollisionBoxes()) {
                if(MathUtil.isPointInsideCircle(circle.getRadius(), circle.getCenter(), point)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean containsPoint(Shape shapeA, Point point) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private class PureCircleCollisionHandler implements CollisionHandler {

        @Override
        public boolean areOverLapping(Shape shapeA, Shape shapeB) {
            if(shapeA.getTypeShape() != ShapeDescriptor.TypeShape.CIRCLE || shapeB.getTypeShape() == ShapeDescriptor.TypeShape.CIRCLE){
                return false;
            }

            Circle circleA = (Circle) shapeA;
            Circle circleB = (Circle) shapeB;
            return MathUtil.isPointInsideCircle(circleA.getRadius() + circleB.getRadius(), circleA.getCenter(), circleB.getCenter());
        }

        @Override
        public boolean containsPoint(Shape shapeA, Point point) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }


    private class TargetHolder  {

        private Set<InFieldEnemyRegistry.Group> groups = new HashSet<InFieldEnemyRegistry.Group>();
        private Set<Shape> shapes = new HashSet<Shape>();
        private Set<Shape> cache = new HashSet<Shape>();

        private boolean modelChange = false;


        void add(InFieldEnemyRegistry.Group group) {
            groups.add(group);
            modelChange = true;
        }


        void add(Shape shape) {
            shapes.add(shape);
            modelChange = true;
        }


        Set<Shape> getTargets() {
            if(modelChange) {
                reloadCache();
            }
            return cache;
        }

        private void reloadCache() {
            cache = new HashSet<Shape>(shapes);
            for(InFieldEnemyRegistry.Group group : groups) {
                cache.addAll(mInFieldEnemyRegistry.getShapeFromGroup(group));
            }
        }

    }


}
