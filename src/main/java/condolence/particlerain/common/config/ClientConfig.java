package condolence.particlerain.common.config;

import condolence.particlerain.common.ParticleRain;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static final Config CLIENT_CONFIG;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        final Pair<Config, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Config::new);
        CLIENT_CONFIG = clientSpecPair.getLeft();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

    // Config loading
    public static int particleDensity;
    public static int spawnRadius;

    public static void bakeConfig() {
        particleDensity = CLIENT_CONFIG.particleDensity.get();
        spawnRadius = CLIENT_CONFIG.spawnRadius.get();
    }

    public static void onModConfigEvent(ModConfig.ModConfigEvent event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            ParticleRain.LOGGER.info("Reloading config...");
            bakeConfig();
        }
    }

    // Config class
    private static class Config {
        public final ConfigValue<Integer> particleDensity;
        public final ConfigValue<Integer> spawnRadius;

        public Config(ForgeConfigSpec.Builder builder) {
            builder.push("particle");
            particleDensity = builder
                    .comment("The number of particles to spawn every tick",
                            "Higher value = more particles, lower FPS")
                    .define("particleDensity", 30);

            spawnRadius = builder
                    .comment("The radius that particles should be spawned in",
                            "Higher value = particles spawn in a larger area, lower FPS")
                    .define("spawnRadius", 20);

            builder.pop();
        }
    }
}
