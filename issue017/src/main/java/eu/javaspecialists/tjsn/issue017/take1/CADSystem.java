/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.javaspecialists.tjsn.issue017.take1;

public class CADSystem implements Constants {
  public void draw(Shape shape) {
    switch(shape.type) {
      case TRIANGLE_SHAPE: // some code which draws a triangle
        System.out.println("Triangle with color " + shape.color);
        break;
      case RECTANGLE_SHAPE: // some code which draws a rectangle
        System.out.println("Rectangle with color " +shape.color);
        break;
      case CIRCLE_SHAPE: // some code which draws a circle
        System.out.println("Circle with color " + shape.color);
        break;
      default: // error only found at runtime
        throw new IllegalArgumentException(
          "Shape has illegal type " + shape.type);
    }
  }
}
