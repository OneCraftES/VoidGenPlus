# Keep your main class
-keep class * extends org.bukkit.plugin.java.JavaPlugin {
    public void onEnable();
    public void onDisable();
}

# Keep event handlers
-keep,allowobfuscation class * extends org.bukkit.event.Listener {
    @org.bukkit.event.EventHandler <methods>;
}

# Keep chunk generators
-keep class * implements org.bukkit.generator.ChunkGenerator {
    *;
}

# Basic options
-dontshrink
-dontoptimize
-dontusemixedcaseclassnames
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep all module info
-keep class module-info
-keep class **.module-info
-keepattributes Module*
-dontwarn module-info

# Keep enums
-keep enum * {
    *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep serializable classes
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Ignore warnings about missing classes
-dontwarn javax.**
-dontwarn java.**
-dontwarn sun.**
-dontwarn com.sun.**
-dontwarn org.bukkit.**
-dontwarn org.spigotmc.**

# Keep all ChunkGenVersion related stuff
-keep class de.xtkq.voidgen.ChunkGenVersion {
    *;
}