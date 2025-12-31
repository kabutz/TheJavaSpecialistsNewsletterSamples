import module java.base;

public sealed interface InvocationTargetSantizier
        permits InstanceOfCastSanitizer,
        InstanceOfPatternMatchingSanitizer,
        SwitchPatternMatchingSanitizer, TryThrowSanitizer {
    void sanitize(InvocationTargetException e);
}
