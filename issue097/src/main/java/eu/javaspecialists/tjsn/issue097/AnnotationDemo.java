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

package eu.javaspecialists.tjsn.issue097;

import org.dom4j.*;
import org.dom4j.io.*;

import javax.swing.*;
import java.io.*;

public class AnnotationDemo extends JFrame {
    private JSlider slider = new JSlider();

    public AnnotationDemo(Element sliderDef) {
        new ComponentSlider(slider, sliderDef);
        // HK: did you notice that you don't have to say:
        // getContentPane().add(...) in JDK 5 anymore?
        add(slider);
    }

    private static Element loadSliderXMLFile(String filename)
            throws FileNotFoundException, DocumentException {
        // The slider definition as XML
        Element sliderDef = null;
        // A reusable SAX parser
        SAXReader xmlReader = new SAXReader();
        xmlReader.setIgnoreComments(true);
        xmlReader.setMergeAdjacentText(true);
        xmlReader.setStripWhitespaceText(true);
        File file = new File(filename);
        if (file.exists() && file.canRead()) {
            Document doc = xmlReader.read(new FileInputStream(file));
            sliderDef = doc.getRootElement();
        }
        if (sliderDef == null) {
            throw new IllegalArgumentException(
                    "Could not find XML declaration");
        }
        return sliderDef;
    }

    public static void main(String args[]) throws Exception {
        Element sliderDef = loadSliderXMLFile(args[0]);
        AnnotationDemo frame = new AnnotationDemo(sliderDef);
        frame.setSize(500, 100);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center the frame
        frame.setVisible(true);
    }
}
