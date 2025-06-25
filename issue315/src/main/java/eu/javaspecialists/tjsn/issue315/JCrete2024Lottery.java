package eu.javaspecialists.tjsn.issue315;

import java.util.*;
import java.util.concurrent.*;

public class JCrete2024Lottery {
    public static void main(String... args) {
        pickRandom(2, "AS", "AVV", "CS", "DP", "FZ", "IK",
                "IV", "J-HS", "JM", "LP", "MB", "MD", "MG",
                "MM", "NB", "SB", "SW");

        pickRandom(10, "AdRe", "AdWo", "AlFe", "AlKe",
                "AlSh", "ÁnÁl", "AnLä", "AnNi", "AnPa",
                "ArGa", "ChPa", "DaDa", "DaSa", "DaSc",
                "DaVl", "DiKa", "EmGk", "EmTz", "EnIb",
                "GeKa", "GüKo", "InBu", "IsJa", "JoVi",
                "MaAl", "MaBü", "MaHa", "MuNu", "NaBa",
                "OlNe", "OtCa", "PaNu", "PaPi", "RaAr",
                "RaSt", "RaTh", "RiDe", "ShMu", "SiBr",
                "TaPa", "TiMi", "VeYa", "ViTa");
    }

    private static void pickRandom(int seats, String... initials) {
        System.out.println(STR."Picking \{seats} attendees:");
        System.out.println(STR."""
            Chance is \{seats} in \{initials.length}, \
         thus \{seats * 100 / initials.length}%\
         """);
        var lottery = new ArrayList<>(List.of(initials));
        Collections.shuffle(lottery, ThreadLocalRandom.current());
        lottery.stream().limit(seats).forEach(System.out::println);
    }
}
