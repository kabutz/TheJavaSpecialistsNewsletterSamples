package eu.javaspecialists.tjsn.issue304;

import java.io.*;
import java.util.*;

public class NoLinkedListFilter implements ObjectInputFilter {
    @Override
    public Status checkInput(FilterInfo filterInfo) {
        return filterInfo.serialClass() == LinkedList.class ?
                Status.REJECTED : Status.UNDECIDED;
    }
}
