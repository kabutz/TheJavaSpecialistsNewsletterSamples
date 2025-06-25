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

package eu.javaspecialists.tjsn.issue010;

import java.awt.*;

public class WildLayoutManager implements LayoutManager {
    // these are the constraints possible with the WildLayoutManager
    public static final String LEFT = "Left";
    public static final String RIGHT = "Right";
    public static final String MIDDLE = "Middle";

    // We keep handles to three components, left, right and middle
    private Component left;
    private Component right;
    private Component middle;

    // we need to be able to add components.  if two components are added
    // with the same constraint we keep the last one
    public void addLayoutComponent(String name, Component comp) {
        if (LEFT.equals(name)) {
            left = comp;
        } else if (RIGHT.equals(name)) {
            right = comp;
        } else if (MIDDLE.equals(name)) {
            middle = comp;
        } else {
            throw new IllegalArgumentException(
                    "cannot add to layout: unknown constraint: " + name);
        }
    }

    // here we remove the component - first find it!
    public void removeLayoutComponent(Component comp) {
        if (comp == left) {
            left = null;
        } else if (comp == right) {
            right = null;
        } else if (comp == middle) {
            middle = null;
        }
    }

    // The minimum dimension we're happy with is the preferred size
    // this could be more fancy by using the minimum sizes of each component
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    // Here we work out the preferred size of the component, which is used
    // by methods such as pack() to work out how big the window should be
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        // get widest preferred width for left && right
        // get highest preferred height for left && right
        // add preferred width of middle
        int widestWidth = 0;
        int highestHeight = 0;
        if ((left != null) && left.isVisible()) {
            widestWidth = Math.max(widestWidth, left.getPreferredSize().width);
            highestHeight =
                    Math.max(highestHeight, left.getPreferredSize().height);
        }
        if ((right != null) && right.isVisible()) {
            widestWidth = Math.max(widestWidth, right.getPreferredSize().width);
            highestHeight =
                    Math.max(highestHeight, right.getPreferredSize().height);
        }
        dim.width = widestWidth * 2;
        dim.height = highestHeight;
        if ((middle != null) && middle.isVisible()) {
            dim.width += middle.getPreferredSize().width;
            dim.height = Math.max(dim.height, middle.getPreferredSize().height);
        }

        Insets insets = parent.getInsets();
        dim.width += insets.left + insets.right;
        dim.height += insets.top + insets.bottom;

        return dim;
    }

    // this is the brain of the layout manager, albeit rather small.
    // I told you this is straightforward...
    public void layoutContainer(Container target) {
        // these variables hold the position where we can draw components
        // taking into account insets
        Insets insets = target.getInsets();
        int north = insets.top;
        int south = target.getSize().height - insets.bottom;
        int west = insets.left;
        int east = target.getSize().width - insets.right;

        // we first find the width of the left and right components
        int widestWidth = 0;
        if ((left != null) && left.isVisible()) {
            widestWidth = Math.max(widestWidth, left.getPreferredSize().width);
        }
        if ((right != null) && right.isVisible()) {
            widestWidth = Math.max(widestWidth, right.getPreferredSize().width);
        }
        if ((middle != null) && middle.isVisible()) {
            widestWidth = Math.max(widestWidth,
                    (east - west - middle.getPreferredSize().width) / 2);
        }

        // next we set the size of the left component equal to the widest width
        // and whole height, and we set the bounds from North-West corner
        if ((left != null) && left.isVisible()) {
            left.setSize(widestWidth, south - north);
            left.setBounds(west, north, widestWidth, south - north);
        }
        // next we set the size of right component equal to the widest width
        // and whole height, and we set the bounds from North-East corner
        if ((right != null) && right.isVisible()) {
            right.setSize(widestWidth, south - north);
            right.setBounds(east - widestWidth, north, widestWidth, south - north);
        }
        // lastly we set the size of the middle component equals to the
        // remaining width, which should be equal to the middle object's
        // preferred width and we set the height equal to the middle object's
        // preferred height
        if ((middle != null) && middle.isVisible()) {
            middle.setSize(east - west - widestWidth * 2,
                    middle.getPreferredSize().height);
            middle.setBounds(
                    west + widestWidth,
                    north + (south - north - middle.getPreferredSize().height) / 2,
                    east - west - widestWidth * 2,
                    middle.getPreferredSize().height);
        }
    }
}