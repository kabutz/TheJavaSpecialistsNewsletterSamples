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
