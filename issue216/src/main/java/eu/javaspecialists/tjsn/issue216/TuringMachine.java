package eu.javaspecialists.tjsn.issue216;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class TuringMachine {
  private static final Pattern statesPattern = Pattern.compile(
      "states \\{ (.*) \\} initial \"(\\w+)\""
  );

  private static final Pattern alphabetPattern = Pattern.compile(
      "alphabet \\{ *(.*) *\\} blank '(.)'"
  );

  private static final Pattern transPattern = Pattern.compile(
      "trans from \"(.*)\" to \"(.*)\" on \\('(.)'\\) " +
          "write '(.)' move (right|left)"
  );

  private static final State HALT = new State("Halt");

  private static class State {
    private final String name;
    private final Map<Character, Transition> transitions =
        new HashMap<>();

    State(String name) {
      this.name = name;
    }

    public String toString() {
      return name;
    }

    public void addTransition(Transition transition) {
      if (transition.from != this) {
        throw new IllegalArgumentException();
      }
      transitions.put(transition.read, transition);
    }

    public Transition getTransition(char read) {
      return transitions.get(read);
    }
  }

  private static enum Move {
    left, right;
  }

  private static class Transition {
    private final State from, to;
    private final char read;
    private final char write;
    private final Move move;

    Transition(State from, State to, char read, char write,
               Move move) {
      this.from = from;
      this.to = to;
      if (this.from == null) throw new NullPointerException();
      if (this.to == null) throw new NullPointerException();
      this.read = read;
      this.write = write;
      this.move = move;
    }

    public String toString() {
      return "trans from \"" + from + "\" to \"" + to + "\" " +
          "on ('" + read + "') write '" + write + "' " +
          "move " + move;
    }
  }

  private final State initial;
  private final char blank;

  private TuringMachine(State initial, char blank) {
    this.initial = initial;
    this.blank = blank;
  }

  public CharSequence execute(CharSequence input) {
    State state = initial;
    StringBuilder tape = new StringBuilder(input);
    int pos = 0;
    while (state != HALT) {
      System.out.println(">>> " + tape);
      System.out.println(space(pos + 4) + "^" +
          space(Math.max(50 - pos, 10)) + state);

      char read = tape.charAt(pos);
      Transition transition = state.getTransition(read);
      if (transition == null) {
        throw new IllegalStateException(
            "No valid transition defined for state " + state +
                " and symbol '" + read + "'");
      }
      tape.setCharAt(pos, transition.write);
      if (transition.move == Move.left) {
        if (pos == 0) {
          tape.insert(0, blank);
        } else {
          pos--;
        }
      } else {
        pos++;
        if (pos >= tape.length()) {
          tape.append(blank);
        }
      }
      state = transition.to;
    }
    return tape;
  }

  private String space(int length) {
    char[] spaces = new char[length];
    Arrays.fill(spaces, ' ');
    return new String(spaces);
  }

  public static TuringMachine create(String program)
      throws IOException {
    BufferedReader in = new BufferedReader(
        new StringReader(program)
    );
    String line;
    Map<String, State> states = new HashMap<>();
    states.put("Halt", HALT);
    State initial = null;
    Character blank = null;
    Set<Character> alphabet = new LinkedHashSet<>();

    while ((line = in.readLine()) != null) {
      Matcher matcher = statesPattern.matcher(line);
      if (matcher.matches()) {
        String[] stateStrings = matcher.group(1).replaceAll(
            "\"", "").split(" ");
        String initialString = matcher.group(2);
        for (String s : stateStrings) {
          State state = new State(s);
          states.put(s, state);
        }
        initial = states.get(initialString);
        continue;
      }
      matcher = alphabetPattern.matcher(line);
      if (matcher.matches()) {
        String chars = matcher.group(1);
        boolean openingquoteseen = false;
        for (int i = 0; i < chars.length(); i++) {
          if (openingquoteseen) {
            alphabet.add(chars.charAt(i));
            openingquoteseen = false;
            i++;
          } else if (chars.charAt(i) == '\'') {
            openingquoteseen = true;
          } else {
            // skip.
          }
        }
        blank = matcher.group(2).charAt(0);
        continue;
      }
      matcher = transPattern.matcher(line);
      if (matcher.matches()) {
        State from = states.get(matcher.group(1));
        if (from == null) {
          throw new IllegalArgumentException(
              "undefined state \"" + matcher.group(1) + "\"");
        }
        State to = states.get(matcher.group(2));
        if (to == null) {
          throw new IllegalArgumentException(
              "undefined state \"" + matcher.group(2) + "\"");
        }
        char read = matcher.group(3).charAt(0);
        if (!alphabet.contains(read)) {
          throw new IllegalArgumentException(
              "alphabet does not contain '" + read + "'");
        }
        char write = matcher.group(4).charAt(0);
        if (!alphabet.contains(write)) {
          throw new IllegalArgumentException(
              "alphabet does not contain '" + write + "'");
        }
        String move = matcher.group(5);
        Transition transition = new Transition(from, to,
            read, write, Move.valueOf(move));
        from.addTransition(transition);
        continue;
      }
      if (!line.isEmpty() && !line.startsWith("\\s*//")) {
        throw new IllegalStateException(
            "Unrecognized line: " + line);
      }
    }
    return new TuringMachine(initial, blank);
  }
}
