package org.nexu.spaceinvader.domain;

import org.nexu.spaceinvader.data.domain.ShapeDescriptor;
import org.nexu.spaceinvader.events.EventBus.Subcriber;
import org.nexu.spaceinvader.events.ScreenDimensionChanged;

import android.graphics.Canvas;

public interface Shape extends Subcriber<ScreenDimensionChanged> {

	void doDraw(Canvas canvas);

	boolean isOutOfScreen(float width, float height);

    Point[] getCollisionBoxes();

    ShapeDescriptor.TypeShape getTypeShape();

    boolean isHidden();



}
