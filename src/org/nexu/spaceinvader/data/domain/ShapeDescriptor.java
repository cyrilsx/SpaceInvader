package org.nexu.spaceinvader.data.domain;

import android.graphics.Bitmap;

public class ShapeDescriptor {
	
	private final int id;
	private final int witdh;
	private final int height;
	private final Bitmap drawable;
	private final int color;
	private final TypeShape typeShape;
	private final int radius;
	

	public ShapeDescriptor(int id, int witdh, int height, Bitmap drawable, int color, int radius, int shape) {
		this.id = id;
		this.witdh = witdh;
		this.height = height;
		this.drawable = drawable;
		this.color = color;
		this.typeShape = TypeShape.convert(shape);
		this.radius = radius;
	}
	
	public int getId() {
		return id;
	}

	public int getWitdh() {
		return witdh;
	}

	public int getHeight() {
		return height;
	}

	public Bitmap getDrawable() {
		return drawable;
	}

	public int getColor() {
		return color;
	}
	
	public TypeShape getTypeShape() {
		return typeShape;
	}
	
	public int getRadius() {
		return radius;
	}
	
	
	public enum MetaData {
		_TABLE_NAME("shape"),
		_ID("id"),
		_SHAPE_TYPE("shape_type"),
		_RADIUS("radius"),
		_WIDTH("width"),
		_HEIGHT("height"),
		_COLOR("color_argb"),
		_DRAWING("drawing");
		
		private final String value;

		private MetaData(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		
	}

	public enum TypeShape {
		RECT(0),
		CIRCLE(1),
		DRAWABLE(2),
		CUSTOM(3);
		
		private final int id;
		private TypeShape(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
		
		static final TypeShape convert(int id) {
			for(TypeShape shape : TypeShape.values()) {
				if(shape.getId() == id) {
					return shape;
				}
			}
			return null;
		}
		
	}
	
	

}
